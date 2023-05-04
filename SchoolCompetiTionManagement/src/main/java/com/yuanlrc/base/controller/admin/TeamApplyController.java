package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.CompetitionType;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.*;
import com.yuanlrc.base.service.admin.CompetitionService;
import com.yuanlrc.base.service.admin.TeamApplyService;
import com.yuanlrc.base.service.admin.TeamCompetitionService;
import com.yuanlrc.base.service.admin.TeamMemberService;
import com.yuanlrc.base.util.SessionUtil;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author  zhong
 * @date 2020-12-25
 */
@Controller
@RequestMapping("/admin/competition/team_apply")
public class TeamApplyController {

    @Autowired
    private TeamCompetitionService teamCompetitionService;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private TeamApplyService teamApplyService;

    @Autowired
    private CompetitionService competitionService;

    /**
     * 团队赛的团队信息
     * @param model
     * @param name
     * @param pageBean
     * @param competitionId 赛项ID
     * @return
     */
    @RequestMapping("/competition_team")
    public String competitionTeam(Model model, PageBean<TeamCompetition> pageBean, Long competitionId, String name)
    {
        pageBean = teamCompetitionService.findByCompetitionIdList(pageBean,competitionId, name);

        for (TeamCompetition competition : pageBean.getContent())
        {
            competition.setTeamMembers(teamMemberService.findByTeamCompetitionId(competition.getId()));
        }

        Student login = SessionUtil.getLoginedStudent();


        TeamCompetition teamCompetition = teamCompetitionService.findByCompetitionIdAndStudentIdAndStatusNot(competitionId, login.getId(), TeamCompetition.NOT_PASS);
        if(teamCompetition == null)
            model.addAttribute("applyButton", 0);
        else
            model.addAttribute("applyButton", 1);


        model.addAttribute("name", name);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("title", "赛项团队");
        model.addAttribute("competitionId", competitionId);


        return "admin/team_apply/competition_team";
    }


    /**
     * 加入一个团队时
     * @param id
     * @return
     */
    @RequestMapping(value = "/join_team", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> joinTeam(Long id)
    {
        Student student = SessionUtil.getLoginedStudent();

        TeamCompetition teamCompetition = teamCompetitionService.find(id);

        Competition competition = teamCompetition.getCompetition();


        //是否加入了团队
        Integer count = teamMemberService.findByTeamCompetitionCompetitionAndStudentIdAndStatus(teamCompetition.getCompetition().getId(), student.getId());
        if(count != 0)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_JOIN_TEAM_ERROR);
        }

        Date date = new Date();
        //判断是否在报名时间之内
        if(date.getTime() < competition.getEnrollStartTime().getTime() || date.getTime() > competition.getEnrollEndTime().getTime()){
            return Result.error(CodeMsg.ENROLL_TIME_ERROR);
        }

        //查看你是否满员了
        List<TeamMember> teamMembers = teamMemberService.findByTeamCompetitionId(id);
        if(teamMembers.size() >= competition.getTeamSize())
        {
            return Result.error(CodeMsg.ADMIN_COMPETITION_FULL_ERROR);
        }

        TeamApply teamApply = new TeamApply();
        teamApply.setStudent(student);
        teamApply.setTeamCompetition(teamCompetition);

        if(teamApplyService.save(teamApply) == null)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_JOIN_TEAM_ADD_ERROR);
        }

        return Result.success(true);
    }


    //创建团队
    @RequestMapping(value = "/create_team", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> createTeam(@RequestParam("id") Long competitionId, String name)
    {
        Competition competition = competitionService.find(competitionId);

        //判断竞赛是否存在
        if(competition == null || competition.getId() == null)
        {
            return Result.error(CodeMsg.ADMIN_COMPETITION_NOT_FOUNT_ERROR);
        }

        Student loginStudent = SessionUtil.getLoginedStudent();

        //判断是不是团队赛
        if(competition.getCompetitionType() != CompetitionType.TEAM.getCode()){
            return Result.error(CodeMsg.ADMIN_COMPETITION_NOT_TEAM_ERROR);
        }

        Date date = new Date();
        //判断是否在报名时间之内
        if(date.getTime() < competition.getEnrollStartTime().getTime() || date.getTime() > competition.getEnrollEndTime().getTime()){
            return Result.error(CodeMsg.ENROLL_TIME_ERROR);
        }

        //判断是否参加这个赛项的团队
        if(teamMemberService.findByTeamCompetitionCompetitionAndStudentIdAndStatus(competitionId, loginStudent.getId()) > 0)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_JOIN_TEAM_ERROR);
        }

        //判断报名人数是否已满
        if(competition.getSignedUp() == competition.getEnrollmentNumber()){
            return Result.error(CodeMsg.ENROLLMENT_NUBER_IS_FULL);
        }

        TeamCompetition teamCompetition = new TeamCompetition();
        teamCompetition.setName(name);
        teamCompetition.setCompetition(competition);
        teamCompetition.setStudent(loginStudent);

        //保存团队
        if(teamCompetitionService.saveTeam(teamCompetition, competition))
        {
            return Result.error(CodeMsg.ADMIN_CREATE_TEAM_ERROR);
        }


        return Result.success(true);
    }
}
