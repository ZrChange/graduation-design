package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.*;
import com.yuanlrc.base.service.admin.*;
import com.yuanlrc.base.util.CompetitionTimeUtil;
import com.yuanlrc.base.util.DatesUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 评委评分、审批Controller层
 */
@Controller
@RequestMapping("/admin/score")
public class ScoreController {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private IndividualCompetitionService individualCompetitionService;

    @Autowired
    private TeamCompetitionService teamCompetitionService;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private IndividualAccessoryService individualAccessoryService;

    @Autowired
    private TeamAccessoryService teamAccessoryService;

    /**
     * 审批报名
     * @param competitionId 竞赛id
     * @param id  参赛表id (个人或团队竞赛id)
     * @return
     */
    @RequestMapping(value = "/approve",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> approve(@RequestParam(name = "competitionId",required=true) Long competitionId,
                                   @RequestParam(name = "id",required=true) Long id,
                                   @RequestParam(name = "status",required=true)int status){
        Competition competition = competitionService.find(competitionId);
        Date date = new Date();
        //已经开始初赛便不能再审批报名
        if(date.getTime() >= competition.getPreliminariesStartTime().getTime()){
            return Result.error(CodeMsg.APPLY_TIME_ERROR);
        }
        //如果是个人赛
        if(competition.getCompetitionType() == CompetitionType.PERSONAL.getCode()){
            IndividualCompetition individualCompetition = individualCompetitionService.find(id);
            if(individualCompetition.getApplyStatus() != IndividualCompetition.IN_EXAMINATION_AND_APPROVAL){
                return Result.error(CodeMsg.APPLY_IS_APPROVE);
            }
            if(status == ApproveType.AGREE.getCode()){
                //如果同意
                individualCompetition.setApplyStatus(IndividualCompetition.APPROVE_IS_PASSED);
            }else{
                //如果拒绝
                individualCompetition.setApplyStatus(IndividualCompetition.APPROVE_NOT_PASSED);
                competition.setSignedUp(competition.getSignedUp()-1);
            }
            if(individualCompetitionService.saveApplyStatus(individualCompetition,competition)){
                return Result.error(CodeMsg.APPROVE_ERROR);
            }
        }else{
            TeamCompetition teamCompetition = teamCompetitionService.find(id);
            List<TeamMember> teamMemberList = teamMemberService.findByTeamCompetitionId(teamCompetition.getId());
            //判断该队人数是否满足竞赛要求
            if(teamMemberList.size() != competition.getTeamSize()){
                return Result.error(CodeMsg.NUMBER_IS_ERROR);
            }
            if(teamCompetition.getStatus() != TeamCompetition.CHECK_CENTER){
                return Result.error(CodeMsg.APPLY_IS_APPROVE);
            }
            if(status == ApproveType.AGREE.getCode()){
                //如果同意
                teamCompetition.setStatus(TeamCompetition.PASS);
            }else{
                //如果拒绝
                teamCompetition.setStatus(TeamCompetition.NOT_PASS);
                competition.setSignedUp(competition.getSignedUp()-1);
            }
            if(teamCompetitionService.saveStatus(teamCompetition,competition)){
                return Result.error(CodeMsg.APPROVE_ERROR);
            }
        }
        return Result.success(true);
    }

    /**
     * 评分
     * @param competitionId 竞赛id
     * @param participateId 可以是个人参赛表id  也可以是团队参赛表id
     * @return
     */
    @RequestMapping(value = "/score",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> judgeScore(@RequestParam(name = "competitionId",required = true) Long competitionId,
            @RequestParam(name = "participateId",required = true) Long participateId,
            @RequestParam(name = "scores",required = true) int scores,
            @RequestParam(name = "compeType",required = true)int compeType){
        Competition competition = competitionService.find(competitionId);
        Date date = new Date();
        //还没到初赛阶段，不能进行评分
        if(date.getTime() < competition.getPreliminariesStartTime().getTime()){
            return Result.error(CodeMsg.COMPETITION_STATUS_ERROR);
        }
        if(!CompetitionTimeUtil.competitionStatusTime(date,competition,compeType)){
            return Result.error(CodeMsg.TIME_ERROR);
        }

        if(scores < 0 || scores >100){
            return Result.error(CodeMsg.SCORE_VALUE_ERROR);
        }
        //判断竞赛类型
        //如果是个人
        if(competition.getCompetitionType() == CompetitionType.PERSONAL.getCode()){
            IndividualCompetition individualCompetition = individualCompetitionService.find(participateId);
            //判断该阶段是否已进行评分
            if(individualCompetitionService.isScores(date,competition, individualCompetition)){
                return Result.error(CodeMsg.SCORE_IS_NOT_EMPTY);
            }
            if(individualCompetitionService.saveScore(date,individualCompetition,competition,scores) == null){
                return Result.error(CodeMsg.SCORE_ERROR);
            }
        }else{
            //如果是团队
            TeamCompetition teamCompetition = teamCompetitionService.find(participateId);
            //判断该阶段是否已进行评分
            if(teamCompetitionService.isResult(date,competition,teamCompetition)){
                return Result.error(CodeMsg.SCORE_IS_NOT_EMPTY);
            }
            if(teamCompetitionService.saveScore(date,teamCompetition,competition,scores) == null){
                return Result.error(CodeMsg.SCORE_ERROR);
            }
        }
        return Result.success(true);
    }

    /**
     * 晋级操作
     * @param competitionId 竞赛id
     * @param id 参赛表id (个人或团队竞赛id)
     * @param
     * @return
     */
    @RequestMapping(value = "/promotion",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> promotion(@RequestParam(name = "competitionId",required=true) Long competitionId,
                                     @RequestParam(name = "id",required=true) Long id,
                                     @RequestParam(name = "compeType",required = true)int compeType){
        Competition competition = competitionService.find(competitionId);
        Date date = new Date();
        //还没到初赛阶段，不能进行晋级
        if(date.getTime() < competition.getPreliminariesStartTime().getTime()){
            return Result.error(CodeMsg.COMPETITION_TIME_ERROR);
        }
        if(compeType == CompetitionProcess.FINALS.getCode()){
            return Result.error(CodeMsg.COMPETITION_STATUS_FINLS_ERROR);
        }else{
            if(!CompetitionTimeUtil.competitionStatusTime(date,competition,compeType)){
                return Result.error(CodeMsg.TIME_ERROR);
            }
        }


        //如果是个人类别竞赛
        if(competition.getCompetitionType() == CompetitionType.PERSONAL.getCode()){
            IndividualCompetition individualCompetition = individualCompetitionService.find(id);
            //判断是否评分
            if(individualCompetitionService.canPromotion(date,individualCompetition,competition)){
                return Result.error(CodeMsg.PROMOTION_SCORE_ERROR);
            }
            //判断是否已晋级
            if(individualCompetitionService.hasPromotion(date,individualCompetition,competition)){
                return Result.error(CodeMsg.HAS_PROMOTION);
            }
            if(individualCompetitionService.savePromotion(date,individualCompetition,competition) == null){
                return Result.error(CodeMsg.PROMOTION_ERROR);
            }
        }else{
            TeamCompetition teamCompetition = teamCompetitionService.find(id);
            //判断是否评分
            if(teamCompetitionService.canPromotion(date,teamCompetition,competition)){
                return Result.error(CodeMsg.PROMOTION_SCORE_ERROR);
            }
            //判断是否已晋级
            if(teamCompetitionService.hasPromotion(date,teamCompetition,competition)){
                return Result.error(CodeMsg.HAS_PROMOTION);
            }
            if(teamCompetitionService.savePromotion(date,teamCompetition,competition) == null){
                return Result.error(CodeMsg.PROMOTION_ERROR);
            }
        }
        return Result.success(true);
    }

    //下载附件
    @RequestMapping(value = "/download",method = RequestMethod.POST)
    @ResponseBody
    public String download(@RequestParam(name = "competitionId",required=true) Long competitionId,
                                     @RequestParam(name = "id",required=true) Long id,
                                     @RequestParam(name = "compeType",required = true)int compeType){
        Competition competition = competitionService.find(competitionId);
        String filename = null;
        //个人类竞赛
        if(competition.getCompetitionType() == CompetitionType.PERSONAL.getCode()){
            IndividualAccessory individualAccessory = individualAccessoryService.findByIndividualCompetitionId(id);
            if(individualAccessory == null){
                return null;
            }
            if(compeType == CompetitionProcess.PRELIMINARIES.getCode()){
                filename = individualAccessory.getPreliminariesAccessory();
            }else if(compeType == CompetitionProcess.SECONDROUND.getCode()){
                filename = individualAccessory.getSecondRoundAccessory();
            }else{
                filename = individualAccessory.getFinalsAccessory();
            }

        }else{
            TeamAccessory teamAccessory = teamAccessoryService.findByTeamCompetitionId(id);
            if(teamAccessory == null){
                return null;
            }
            if(compeType == CompetitionProcess.PRELIMINARIES.getCode()){
                filename = teamAccessory.getPreliminariesAccessory();
            }else if(compeType == CompetitionProcess.SECONDROUND.getCode()){
                filename = teamAccessory.getSecondRoundAccessory();
            }else{
                filename = teamAccessory.getFinalsAccessory();
            }
        }
        return filename;
    }

}
