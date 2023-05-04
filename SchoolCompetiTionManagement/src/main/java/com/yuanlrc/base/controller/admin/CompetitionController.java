package com.yuanlrc.base.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.*;
import com.yuanlrc.base.schedule.admin.BackUpSchedule;
import com.yuanlrc.base.service.admin.*;
import com.yuanlrc.base.util.DatesUtil;
import com.yuanlrc.base.util.ExportExcelUtil;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 竞赛Controller层
 */
@Controller
@RequestMapping("/admin/competition")
public class CompetitionController {

    private Logger logger = LoggerFactory.getLogger(CompetitionController.class);


    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private IndividualCompetitionService individualCompetitionService;

    @Autowired
    private TeamCompetitionService teamCompetitionService;


    /**
     * 竞赛列表
     *
     * @param model
     * @param competition
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, Competition competition, PageBean<Competition> pageBean) {
        int loginType = (int) SessionUtil.get("type");
        model.addAttribute("headTitle", "竞赛列表");
        PageBean<Competition> competitonList = competitionService.findList(competition, pageBean, loginType);
        model.addAttribute("title", competition.getTitle() == null ? "" : competition.getTitle());
        model.addAttribute("raceType",competition.getRaceType());
        model.addAttribute("pageBean", competitonList);
        return "admin/competition/list";
    }

    /**
     * 竞赛添加页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("competitionType", CompetitionType.values());
        model.addAttribute("RaceType", RaceType.values());
        model.addAttribute("gradeList", gradeService.findAll());
        model.addAttribute("collegeList", collegeService.findAll());
        return "admin/competition/add";
    }

    /**
     * 竞赛添加保存到数据库里
     *
     * @param competition
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Competition competition) {
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(competition);
        if (validate.getCode() != CodeMsg.SUCCESS.getCode()) {
            return Result.error(validate);
        }
        if (!DatesUtil.checkDateTime(competition.getEnrollStartTime(), competition.getEnrollEndTime())) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_ENROLLTIME_ERROR);
        }
        if (!DatesUtil.checkDateTime(competition.getPreliminariesStartTime(), competition.getPreliminariesEndTime())) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_PRELIMINARIESTIME_ERROR);
        }
        if (!DatesUtil.checkDateTime(competition.getSecondRoundEndTime(), competition.getSecondRoundEndTime())) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_SECONDROUND_ERROR);
        }
        if (!DatesUtil.checkDateTime(competition.getFinalsStartTime(), competition.getFinalsEndTime())) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_FINAL_ERROR);
        }
        //保存
        if (competitionService.save(competition) == null) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_SAVE_ERROR);
        }
        operaterLogService.add("添加竞赛，竞赛标题：" + competition.getTitle());
        return Result.success(true);
    }


    /**
     * 竞赛编辑页面
     *
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam(name = "id", required = true) Long id) {
        Competition competition = competitionService.find(id);
        if (competition == null) {
            return "/admin/competition/list";
        }
        //该竞赛的评委和年级查询出来
        List<Grade> grades = competition.getGrades();
        List<Judge> judges = competition.getJudges();
        String gradesList = JSONArray.toJSONString(grades);
        String judgesList = JSONArray.toJSONString(judges);
        //专业查出来
        Professional professional = competition.getProfessional();
        model.addAttribute("professionalId", professional == null ? -1l : professional.getId());
        model.addAttribute("competition", competition);
        model.addAttribute("competitionType", CompetitionType.values());
        model.addAttribute("RaceType", RaceType.values());
        model.addAttribute("gradeList", gradeService.findAll());
        model.addAttribute("collegeList", collegeService.findAll());
        model.addAttribute("gradesList", gradesList);
        model.addAttribute("judgesList", judgesList);
        return "admin/competition/edit";
    }

    /**
     * 竞赛编辑操作
     *
     * @param competition
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Competition competition) {
        Competition competition1 = competitionService.find(competition.getId());
        if (competition1 == null) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_NOTEMPTY_ERROR);
        }
        Long cid = competition1.getId();
        //检测是否有人参加该竞赛
        if (competition1.getCompetitionType() == CompetitionType.PERSONAL.getCode()) {
            //表示个人
            List<IndividualCompetition> individualCompetition = individualCompetitionService.findByCompetitionId(cid);
            long count = individualCompetition.stream().count();
            if (count > 0) {
                return Result.error(CodeMsg.ADMIN_COMPETITION_TAKE_PART_ERROR);
            }
        } else {
            //表示团队
            List<TeamCompetition> teamCompetitions = teamCompetitionService.findByCompetitionId(cid);
            long count = teamCompetitions.stream().count();
            if (count > 0) {
                return Result.error(CodeMsg.ADMIN_COMPETITION_TAKE_PART_ERROR);
            }
        }
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(competition);
        if (validate.getCode() != CodeMsg.SUCCESS.getCode()) {
            return Result.error(validate);
        }
        if (!DatesUtil.checkDateTime(competition.getEnrollStartTime(), competition.getEnrollEndTime())) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_ENROLLTIME_ERROR);
        }
        if (!DatesUtil.checkDateTime(competition.getPreliminariesStartTime(), competition.getPreliminariesEndTime())) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_PRELIMINARIESTIME_ERROR);
        }
        if (!DatesUtil.checkDateTime(competition.getSecondRoundEndTime(), competition.getSecondRoundEndTime())) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_SECONDROUND_ERROR);
        }
        if (!DatesUtil.checkDateTime(competition.getFinalsStartTime(), competition.getFinalsEndTime())) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_FINAL_ERROR);
        }
        ///讲提交的用户信息指定字段复制到已存在的competition对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(competition, competition1, "id", "createTime", "updateTime");
        //进行数据库编辑
        if (competitionService.save(competition1) == null) {
            return Result.error(CodeMsg.COLLEGE_ADD_ERROR);
        }
        operaterLogService.add("编辑竞赛，竞赛标题：" + competition.getTitle());
        return Result.success(true);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id) {
        try {
            competitionService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_DELETE_ERROR);
        }
        operaterLogService.add("删除竞赛，竞赛Id：" + id);
        return Result.success(true);
    }


    /**
     * 查看竞赛页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/viewComption")
    public String viewCompetition(@RequestParam("id") Long id, Model model) {
        Competition competition = competitionService.find(id);
        model.addAttribute("competition", competition);
        model.addAttribute("competitionType", CompetitionType.values());
        model.addAttribute("competitionProcess", CompetitionProcess.values());
        model.addAttribute("RaceType", RaceType.values());
        return "admin/competition/view_competition";
    }

    /**
     * 查看个人初赛、复赛、决赛名单
     * @return
     */
    @RequestMapping("/viewPersionMatch")
    public String viewPersionMatch(Model model,IndividualCompetition personalCompetition,PageBean<IndividualCompetition>pageBean,@RequestParam(name = "compeType",required = true)int compeType){
        PageBean<IndividualCompetition> personList = individualCompetitionService.findByCompetition(pageBean, personalCompetition.getId(), compeType);
        Competition competition = competitionService.find(personalCompetition.getId());

        int timeType = TimeType.TRUE.getCode();

        Date date = new Date();
        //如果是初赛页面
        if(compeType == CompetitionProcess.PRELIMINARIES.getCode()){
            //如果在复赛开始之后或者在初赛开始之前
            if(date.after(competition.getSecondRoundStartTime()) || date.before(competition.getPreliminariesStartTime())){
                timeType = TimeType.ERROR.getCode();
            }
        }else if(compeType == CompetitionProcess.SECONDROUND.getCode()){
            if(date.after(competition.getFinalsStartTime()) || date.before(competition.getSecondRoundStartTime())){
                timeType = TimeType.ERROR.getCode();
            }
        }else{
            if(date.before(competition.getFinalsStartTime())){
                timeType = TimeType.ERROR.getCode();
            }
        }

        model.addAttribute("timeType",timeType);

        model.addAttribute("pageBean",personList);
        model.addAttribute("competition",competition);
        model.addAttribute("compeType",compeType);
        return "admin/competition/view_persion_match";
    }

    /**
     * 查看团队初赛、复赛、决赛名单
     * @return
     */
    @RequestMapping("/viewTeamMatch")
    public String viewTeamMatch(Model model,TeamCompetition teamCompetition,PageBean<TeamCompetition>pageBean,@RequestParam(name = "compeType",required = true)int compeType){
        PageBean<TeamCompetition> teamCompetitionList = teamCompetitionService.findByTeamCompetitionList(pageBean, teamCompetition.getId(), compeType);
        Competition competition = competitionService.find(teamCompetition.getId());

        int timeType = TimeType.TRUE.getCode();

        Date date = new Date();
        //如果是初赛页面
        if(compeType == CompetitionProcess.PRELIMINARIES.getCode()){
            //如果在复赛开始之后或者在初赛开始之前
            if(date.after(competition.getSecondRoundStartTime()) || date.before(competition.getPreliminariesStartTime())){
                timeType = TimeType.ERROR.getCode();
            }
        }else if(compeType == CompetitionProcess.SECONDROUND.getCode()){
            if(date.after(competition.getFinalsStartTime()) || date.before(competition.getSecondRoundStartTime())){
                timeType = TimeType.ERROR.getCode();
            }
        }else{
            if(date.before(competition.getFinalsStartTime())){
                timeType = TimeType.ERROR.getCode();
            }
        }

        model.addAttribute("timeType",timeType);
        model.addAttribute("pageBean",teamCompetitionList);
        model.addAttribute("competition",competition);
        model.addAttribute("compeType",compeType);
        return "admin/competition/view_team_match";
    }

    /**
     * 根据竞赛id查询个人竞赛成绩信息
     * @param competitionId
     * @return
     */
    @RequestMapping("/showPersionTop")
    public String showPersionTop(@RequestParam(name = "competitionId",required = true)Long competitionId,Model model){
        model.addAttribute("topTenList",individualCompetitionService.findTopTen(competitionId));
        return "/admin/score/persion";
    }


    /**
     * 根据竞赛id查询团队竞赛成绩信息
     * @param competitionId
     * @return
     */
    @RequestMapping("/showTeamTop")
    public String showTeamTop(@RequestParam(name = "competitionId",required = true)Long competitionId,Model model){
        model.addAttribute("topTenList",teamCompetitionService.findTopTen(competitionId));
        return "/admin/score/team";
    }


    /**
     * 个人成绩
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/export_individual")
    public void exportIndividual(HttpServletResponse response,@RequestParam(name = "competitionId",required = true)Long competitionId) throws Exception
    {
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.setHeaders(new String[]{"排名","竞赛标题","学生学号","学生姓名","学生分数"});
        HSSFSheet sheet = exportExcelUtil.create(); //创建表格

        //设置Data
        List<IndividualCompetition> individualCompetitions = individualCompetitionService.findTopTen(competitionId);
        for(int i=0; i<individualCompetitions.size(); i++)
        {
            IndividualCompetition item = individualCompetitions.get(i);

            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(item.getCompetition().getTitle());
            row.createCell(2).setCellValue(item.getStudent().getStudentNumber());
            row.createCell(3).setCellValue(item.getStudent().getName());
            row.createCell(4).setCellValue(item.getFinalsScore());
        }

        //导出
        exportExcelUtil.export(response);
    }


    /**
     * 团队成绩
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/export_team")
    public void exportTeam(HttpServletResponse response,@RequestParam(name = "competitionId",required = true)Long competitionId) throws Exception
    {
        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.setHeaders(new String[]{"排名","竞赛标题","团队名称","队长学号","队长姓名","分数"});
        HSSFSheet sheet = exportExcelUtil.create(); //创建表格

        //设置Data
        List<TeamCompetition> individualCompetitions = teamCompetitionService.findTopTen(competitionId);
        for(int i=0; i<individualCompetitions.size(); i++)
        {
            TeamCompetition item = individualCompetitions.get(i);

            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(item.getCompetition().getTitle());
            row.createCell(2).setCellValue(item.getName());
            row.createCell(3).setCellValue(item.getStudent().getStudentNumber());
            row.createCell(4).setCellValue(item.getStudent().getName());
            row.createCell(5).setCellValue(item.getFinalsScore());
        }

        //导出
        exportExcelUtil.export(response);
    }
}
