package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.*;
import com.yuanlrc.base.service.admin.*;
import com.yuanlrc.base.entity.admin.Student;
import com.yuanlrc.base.entity.admin.TeamCompetition;
import com.yuanlrc.base.entity.admin.TeamMember;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/team")
public class TeamController {

    @Autowired
    private TeamCompetitionService teamCompetitionService;

    @Autowired
    private TeamMemberService teamMemberService;


    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private TeamApplyService teamApplyService;

    @Autowired
    private TeamAccessoryService teamAccessoryService;


    /**
     * 我曾经的团队比赛列表
     */
    @RequestMapping("/my_game_list")
    public String myGameList(Model model, PageBean<TeamMember> pageBean, String competitionTitle) {
        Student student = SessionUtil.getLoginedStudent();

        model.addAttribute("pageBean", teamMemberService.findListByStudentId(pageBean, competitionTitle, student.getId()));
        model.addAttribute("title", "团队参赛");
        model.addAttribute("competitionTitle", competitionTitle);

        return "admin/team/my_game_list";
    }

    /**
     * @return
     */
    @RequestMapping("/is_apply_pass")
    @ResponseBody
    public Result<Boolean> isApplyPass(Long id) {
        TeamCompetition teamCompetition = teamCompetitionService.find(id);

        if (teamCompetition.getStatus() != TeamCompetition.PASS) {
            return Result.error(CodeMsg.ADMIN_STUDENT_SHOW_APPLY_PASS_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 查看分数
     */
    @RequestMapping("/show_score")
    public String showScore(Model model, Long id) {
        TeamCompetition teamCompetition = teamCompetitionService.find(id);

        //审批没有通过的话
        if (teamCompetition.getStatus() != TeamCompetition.PASS) {
            return "redirect:my_game_list";
        }

        Student student = SessionUtil.getLoginedStudent();

        //我不在这个团队当中
        TeamMember teamMembers = teamMemberService.findByTeamCompetitionIdAndStudentId(id, student.getId());
        if (teamMembers == null) {
            return "redirect:my_game_list";
        }

        model.addAttribute("teamCompetition", teamCompetition);
        return "admin/team/show_score";
    }

    /**
     * 查看自己的这个赛项下的团队成员
     */
    @RequestMapping("/show_team_list")
    public String showTeamList(Model model, Long id) {
        TeamCompetition teamCompetition = teamCompetitionService.find(id);
        List<TeamMember> teamMembers = teamMemberService.findByTeamCompetitionId(id);
        Student student = teamCompetition.getStudent();

        //登录对象
        Student login = SessionUtil.getLoginedStudent();

        for (TeamMember item : teamMembers) {
            if (item.getStudent().getId() == student.getId()) {
                item.setIsCaptain(1); //找到队长了
                break;
            }
        }

        TeamMember teamMember = teamMemberService.findByTeamCompetitionIdAndStudentId(id, login.getId());
        if (teamMembers == null) {
            return "redirect:my_game_list";
        }

        Integer loginCaptain = login.getId().longValue() == student.getId().longValue() ? 1 : 0;

        if(teamCompetition.getStatus() == TeamCompetition.PASS)
            model.addAttribute("showButton", 1);
        else
            model.addAttribute("showButton", 0);


        model.addAttribute("teamMembers", teamMembers);
        model.addAttribute("loginCaptain", loginCaptain);
        model.addAttribute("teamcompetitionId", id);
        return "admin/team/show_team_list";
    }

    /**
     * 队长转让
     *
     * @param teamcompetitionId
     * @param id
     * @return
     */
    @RequestMapping(value = "/make_captain", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> makeCaptain(@RequestParam("teamId") Long teamcompetitionId, @RequestParam("id") Long id) {
        TeamCompetition teamCompetition = teamCompetitionService.find(teamcompetitionId);
        TeamMember teamMember = teamMemberService.find(id);

        //登录对象
        Student login = SessionUtil.getLoginedStudent();
        Student student = teamCompetition.getStudent();  //队长ID
        if (login.getId().longValue() != student.getId().longValue()) {
            return Result.error(CodeMsg.ADMIN_STUDENT_MAKE_CAPTAIN_ERROR);
        }

        //比赛已通过了无法转让
        if (teamCompetition.getStatus() != TeamCompetition.NOT_CHECK_CENTER) {
            return Result.error(CodeMsg.ADMIN_STUDENT_MAKE_CAPTAIN_ERROR_2);
        }

        if (teamMember.getStudent().getId() == student.getId()) {
            return Result.error(CodeMsg.ADMIN_STUDENT_MAKE_CAPTAIN_ERROR_3);
        }

        teamCompetition.setStudent(teamMember.getStudent());
        if (teamCompetitionService.save(teamCompetition) == null) {
            return Result.error(CodeMsg.ADMIN_STUDENT_MAKE_CAPTAIN_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 退出团队
     *
     * @param teamcompetitionId 团队竞赛ID
     * @param
     * @return
     */
    @RequestMapping(value = "/quit_team", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> quitTeam(@RequestParam("teamId") Long teamcompetitionId) {
        TeamCompetition teamCompetition = teamCompetitionService.find(teamcompetitionId); //团队竞赛
        Student login = SessionUtil.getLoginedStudent();
        if (teamCompetition.getStudent().getId() == login.getId()) {
            return Result.error(CodeMsg.ADMIN_STUDENT_QUIT_ERROR);
        }

        //已经报名了无法退出
        if (teamCompetition.getStatus() != TeamCompetition.NOT_CHECK_CENTER) {
            return Result.error(CodeMsg.ADMIN_STUDENT_QUIT_ERROR_3);
        }

        TeamMember teamMember = teamMemberService.findByTeamCompetitionIdAndStudentId(teamcompetitionId, login.getId());
        try {
            teamMemberService.delete(teamMember.getId());
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_STUDENT_QUIT_ERROR_2);
        }

        return Result.success(true);
    }

    /**
     * 解散团队
     *
     * @param teamcompetitionId
     * @return
     */
    @RequestMapping(value = "/dissolve_team", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> dissolveTeam(@RequestParam("teamId") Long teamcompetitionId) {
        TeamCompetition teamCompetition = teamCompetitionService.find(teamcompetitionId); //团队竞赛
        Student login = SessionUtil.getLoginedStudent();

        if (teamCompetition.getStudent().getId().longValue() != login.getId().longValue()) {
            return Result.error(CodeMsg.ADMIN_STUDENT_DISSOLVE_NOT_CAPTAIN_ERROR);
        }

        //是通过报名
        if (teamCompetition.getStatus() != TeamCompetition.NOT_CHECK_CENTER) {
            return Result.error(CodeMsg.ADMIN_STUDENT_DISSOLVE_ERROR_2);
        }

        List<TeamMember> teamMembers = teamMemberService.findByTeamCompetitionId(teamcompetitionId);

        List<TeamApply> teamApplies = teamApplyService.findByTeamCompetitionId(teamcompetitionId);

        if (!teamMemberService.deleteList(teamMembers, teamCompetition, teamApplies)) {
            return Result.error(CodeMsg.ADMIN_STUDENT_DISSOLVE_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 是否是队长
     * @param teamcompetitionId
     * @return
     */
    @RequestMapping(value = "/is_captain", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> isCaptain(@RequestParam("id") Long teamcompetitionId) {
        TeamCompetition teamCompetition = teamCompetitionService.find(teamcompetitionId);

        Student login = SessionUtil.getLoginedStudent();
        if (login.getId().longValue() != teamCompetition.getStudent().getId().longValue()) {
            return Result.error(CodeMsg.ADMIN_IS_CAPTAIN_ERROR);
        }

        //等于未报名，可以审批
        if (teamCompetition.getStatus() != TeamCompetition.NOT_CHECK_CENTER) {
            return Result.error(CodeMsg.ADMIN_CHECK_CENTER_ERROR);
        }

        Competition competition = teamCompetition.getCompetition();

        Date date = new Date();
        //判断是否在报名时间之内
        if (date.getTime() < competition.getEnrollStartTime().getTime() || date.getTime() > competition.getEnrollEndTime().getTime()) {
            return Result.error(CodeMsg.ENROLL_TIME_ERROR);
        }

        //判断是不是团队赛
        if (competition.getCompetitionType() != CompetitionType.TEAM.getCode()) {
            return Result.error(CodeMsg.ADMIN_COMPETITION_NOT_TEAM_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 查看申请记录
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/apply_list", method = RequestMethod.GET)
    public String applyList(Model model, PageBean<TeamApply> pageBean, String name, @RequestParam("id") Long id) {
        TeamCompetition teamCompetition = teamCompetitionService.find(id);

        Student login = SessionUtil.getLoginedStudent();
        if (login.getId().longValue() != teamCompetition.getStudent().getId().longValue()) {
            return "redirect:my_game_list";
        }

        if (teamCompetition.getStatus() != TeamCompetition.NOT_CHECK_CENTER) {
            return "redirect:my_game_list";
        }

        Competition competition = teamCompetition.getCompetition();

        Date date = new Date();
        //判断是否在报名时间之内
        if (date.getTime() < competition.getEnrollStartTime().getTime() || date.getTime() > competition.getEnrollEndTime().getTime()) {
            return "redirect:my_game_list";
        }

        //判断是不是团队赛
        if (competition.getCompetitionType() != CompetitionType.TEAM.getCode()) {
            return "redirect:my_game_list";
        }
        pageBean = teamApplyService.findListByTeamCompetitionId(pageBean, name, id);
        model.addAttribute("title", "申请记录");
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("name", name);
        model.addAttribute("id", id);

        return "admin/team/apply_list";
    }

    @ResponseBody
    @RequestMapping(value = "/apply_delete", method = RequestMethod.POST)
    public Result<Boolean> applyDelete(Long id) {
        try {
            teamApplyService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_APPLY_DELETE_ERROR);
        }
        return Result.success(true);
    }

    @ResponseBody
    @RequestMapping(value = "/apply_add", method = RequestMethod.POST)
    public Result<Boolean> applyAdd(Long id) {
        TeamApply teamApply = teamApplyService.find(id);

        Student student = teamApply.getStudent();

        TeamCompetition teamCompetition = teamApply.getTeamCompetition();

        Competition competition = teamCompetition.getCompetition();

        if (teamApply.getStatus() == TeamApply.PASS) {
            return Result.error(CodeMsg.ADMIN_APPLY_ADD_PASS_ERROR);
        }

        List<TeamMember> teamMembers = teamMemberService.findByTeamCompetitionId(teamCompetition.getId());

        //判断是否满员
        if (competition.getTeamSize() == teamMembers.size()) {
            return Result.error(CodeMsg.ADMIN_TEAM_FULL_ERROR);
        }

        //判断报名是否通过
        if (teamCompetition.getStatus() != TeamCompetition.NOT_CHECK_CENTER) {
            return Result.error(CodeMsg.ADMIN_APPLY_ADD_ERROR);
        }

        //判断报名时间是否已过
        Date date = new Date();

        //判断是否在报名时间之内
        if (date.getTime() < competition.getEnrollStartTime().getTime() || date.getTime() > competition.getEnrollEndTime().getTime()) {
            return Result.error(CodeMsg.ADMIN_NOT_TIME_RANGE_ERROR);
        }
        //判断是否参加过过这个赛项了
        if (teamMemberService.findByTeamCompetitionCompetitionAndStudentIdAndStatus(competition.getId(), student.getId()) > 0) {
            return Result.error(CodeMsg.ADMIN_STUDENT_JOIN_TEAM_ERROR_2);
        }
        TeamMember teamMember = new TeamMember();
        teamMember.setStudent(student);
        teamMember.setTeamCompetition(teamCompetition);
        if (!teamApplyService.save(teamMember, teamApply)) {
            return Result.error(CodeMsg.ADMIN_APPLY_ADD_ERROR_2);
        }
        return Result.success(true);
    }

    /**
     * 查看团队竞赛展示页面
     *
     * @param model
     * @param teamCompetition
     * @param pageBean
     * @param type
     * @return
     */
    @RequestMapping(value = "/viewTeamSignUp", method = RequestMethod.GET)
    public String viewTeamSignUp(Model model, TeamCompetition
            teamCompetition, PageBean<TeamCompetition> pageBean, @RequestParam(name = "type", required = true) int type) {
        PageBean<TeamCompetition> teamCompetitionList = teamCompetitionService.findByTeamCompetitionList(pageBean, teamCompetition.getId(), type);
        Competition competition = competitionService.find(teamCompetition.getId());
        model.addAttribute("pageBean", teamCompetitionList);
        model.addAttribute("comType", type);
        model.addAttribute("competition", competition);
        return "admin/competition/view_team_sign_up";
    }

    /**
     * 上传附件
     */
    @RequestMapping(value = "upload_accessory", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> uploadAccessory(Long id, String resocre)
    {
        TeamCompetition teamCompetition = teamCompetitionService.find(id);
        if(resocre.trim().length() == 0)
        {
            return Result.error(CodeMsg.ADMIN_UPLOAD_ACCESSORY_ERROR);
        }

        Student loginStudent = SessionUtil.getLoginedStudent(); //登录ID
        Student student = teamCompetition.getStudent(); //队长ID
        if(loginStudent.getId().longValue() !=  student.getId().longValue())
        {
            return Result.error(CodeMsg.ADMIN_ACCESSORY_ERROR);
        }

        TeamAccessory teamAccessory = teamAccessoryService.findByTeamCompetitionId(teamCompetition.getId());


        //判断是否报名通过
        if(teamCompetition.getStatus() != TeamCompetition.PASS)
        {
            return Result.error(CodeMsg.ADMIN_APPLY_STATUS_ERROR);
        }

        Competition competition = teamCompetition.getCompetition();

        //判断比赛是否开始
        Date date = new Date();
        if(competition.getPreliminariesStartTime().getTime() > date.getTime())
        {
            return Result.error(CodeMsg.ADMIN_ACCESSORY_START_TIME_ERROR);
        }


        //初赛
        if (date.getTime() > competition.getPreliminariesStartTime().getTime() &&
                date.getTime() < competition.getPreliminariesEndTime().getTime()) {

            //如果上传过了
            if(teamAccessory != null)
            {
                return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }

            //没有上传
            teamAccessory = new TeamAccessory();
            teamAccessory.setTeamCompetition(teamCompetition);
            teamAccessory.setPreliminariesAccessory(resocre);
        }
        else if (date.getTime() > competition.getSecondRoundStartTime().getTime() &&
                date.getTime() < competition.getSecondRoundEndTime().getTime()) {
            //判断是否在复赛时间段
            //判断初赛是否通过
            if(teamCompetition.getPreliminariesStatus() != IndividualCompetition.PRELIMINARIES_IS_PASSED)
            {
                return Result.error(CodeMsg.ADMIN_PRELIMINARIES_NOT_PASS_ERROR);
            }

            //如果初赛没有上传过附件
            if(teamAccessory == null) {
                teamAccessory = new TeamAccessory();
                teamAccessory.setTeamCompetition(teamCompetition);
            }else{
                //如果复赛上传附件了
                if(teamAccessory.getSecondRoundStatus() == AccessoryType.SUBMITTED.getCode())
                    return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }

            teamAccessory.setSecondRoundAccessory(resocre);
            teamAccessory.setSecondRoundStatus(AccessoryType.SUBMITTED.getCode());
        }
        else if (date.getTime() > competition.getFinalsStartTime().getTime() &&
                date.getTime() < competition.getFinalsEndTime().getTime()) {
            //判断是否在决赛时间段

            //判断复赛是否通过
            if(teamCompetition.getSecondRoundStatus() != IndividualCompetition.SECOND_ROUND_IS_PASSED)
            {
                return Result.error(CodeMsg.ADMIN_SECOND_ROUND_NOT_PASS_ERROR);
            }

            //如果初赛复赛未上传
            if(teamAccessory == null)
            {
                teamAccessory = new TeamAccessory();
                teamAccessory.setTeamCompetition(teamCompetition);
            }else{
                //如果已经上传过了
                //如果复赛上传附件了
                if(teamAccessory.getFinalsStatus() == AccessoryType.SUBMITTED.getCode())
                    return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }

            teamAccessory.setFinalsAccessory(resocre);
            teamAccessory.setFinalsStatus(AccessoryType.SUBMITTED.getCode());
        }
        else
        {
            return Result.error(CodeMsg.ADMIN_UPLOAD_ACCESSORY_NOT_FOUND_ERROR);
        }

        if(teamAccessoryService.save(teamAccessory) == null) {
            return Result.error(CodeMsg.ADMIN_UPLOAD_ACCESSORY_NOT_FOUND_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 是否可以上传
     * @param id
     * @return
     */
    @RequestMapping(value = "is_upload_accessory", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> isUploadAccessory(Long id)
    {
        TeamCompetition teamCompetition = teamCompetitionService.find(id);

        TeamAccessory teamAccessory = teamAccessoryService.findByTeamCompetitionId(teamCompetition.getId());

        //报名是否通过
        if(teamCompetition.getStatus() != TeamCompetition.PASS)
        {
            return Result.error(CodeMsg.ADMIN_APPLY_STATUS_ERROR);
        }

        Competition competition = teamCompetition.getCompetition();

        //判断比赛是否开始
        Date date = new Date();
        if(competition.getPreliminariesStartTime().getTime() > date.getTime())
        {
            return Result.error(CodeMsg.ADMIN_ACCESSORY_START_TIME_ERROR);
        }

        //初赛
        //初赛
        if (date.getTime() > competition.getPreliminariesStartTime().getTime() &&
                date.getTime() < competition.getPreliminariesEndTime().getTime()) {

            //如果上传过了
            if(teamAccessory != null)
            {
                return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }
        }
        else if (date.getTime() > competition.getSecondRoundStartTime().getTime() &&
                date.getTime() < competition.getSecondRoundEndTime().getTime()) {
            //判断是否在复赛时间段
            //判断初赛是否通过
            if(teamCompetition.getPreliminariesStatus() != IndividualCompetition.PRELIMINARIES_IS_PASSED)
            {
                return Result.error(CodeMsg.ADMIN_PRELIMINARIES_NOT_PASS_ERROR);
            }

            //如果初赛没有上传过附件
            if(teamAccessory != null){
                //如果复赛上传附件了
                if(teamAccessory.getSecondRoundStatus() == AccessoryType.SUBMITTED.getCode())
                    return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }

        }
        else if (date.getTime() > competition.getFinalsStartTime().getTime() &&
                date.getTime() < competition.getFinalsEndTime().getTime()) {
            //判断是否在决赛时间段

            //判断复赛是否通过
            if(teamCompetition.getSecondRoundStatus() != IndividualCompetition.SECOND_ROUND_IS_PASSED)
            {
                return Result.error(CodeMsg.ADMIN_SECOND_ROUND_NOT_PASS_ERROR);
            }

            //如果初赛复赛未上传
            if(teamAccessory != null)
            {
                //如果已经上传过了
                //如果复赛上传附件了
                if(teamAccessory.getFinalsStatus() == AccessoryType.SUBMITTED.getCode())
                    return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }
        }
        else
        {
            return Result.error(CodeMsg.ADMIN_UPLOAD_ACCESSORY_NOT_FOUND_ERROR);
        }

        return Result.success(true);
    }


    /**
     * 是否可以下载
     * @param id
     * @return
     */
    @RequestMapping(value = "is_download", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> isDownload(Long id)
    {
        TeamCompetition teamCompetition = teamCompetitionService.find(id);

        TeamAccessory teamAccessory = teamAccessoryService.findByTeamCompetitionId(teamCompetition.getId());
        if(teamAccessory == null)
        {
            return Result.error(CodeMsg.ADMIN_DOWNLOAD_ACCESSORY_ERROR);
        }

        //报名是否通过
        if(teamCompetition.getStatus() != TeamCompetition.PASS)
        {
            return Result.error(CodeMsg.ADMIN_APPLY_STATUS_ERROR);
        }

        Competition competition = teamCompetition.getCompetition();

        //判断比赛是否开始
        Date date = new Date();
        if(competition.getPreliminariesStartTime().getTime() > date.getTime())
        {
            return Result.error(CodeMsg.ADMIN_ACCESSORY_START_TIME_ERROR);
        }

        return Result.success(true);
    }


    /**
     * 报名
     */
    @RequestMapping(value = "check_apply", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> checkApply(Long id)
    {
        TeamCompetition teamCompetition = teamCompetitionService.find(id);
        Student loginStudent = SessionUtil.getLoginedStudent();
        if(teamCompetition.getStudent().getId().longValue() != loginStudent.getId().longValue())
        {
            return Result.error(CodeMsg.ADMIN_CHECK_APPLY_ERROR_3);
        }

        if(teamCompetition.getStudent().getId().longValue() != loginStudent.getId().longValue())
        {
            return Result.error(CodeMsg.ADMIN_CHECK_APPLY_ERROR);
        }

        //通过或者审批中无法报名
        if(teamCompetition.getStatus() == TeamCompetition.PASS || teamCompetition.getStatus() == TeamCompetition.CHECK_CENTER)
        {
            return Result.error(CodeMsg.ADMIN_CHECK_IS_PASS_AND_CHECK_ERROR);
        }

        //人数未满
        List<TeamMember> teamMembers = teamMemberService.findByTeamCompetitionId(id);
        Competition competition = teamCompetition.getCompetition();
        Integer size = competition.getTeamSize(); //团队人数

        if(size != teamMembers.size())
        {
            return Result.error(CodeMsg.ADMIN_TEAM_MEMBERS_SIZE_ERROR);
        }

        //判断报名人数是否已满
        if(competition.getSignedUp() == competition.getEnrollmentNumber()){
            return Result.error(CodeMsg.ENROLLMENT_NUBER_IS_FULL);
        }

        //是否在时间段内
        //判断报名时间是否已过
        Date date = new Date();

        //判断是否在报名时间之内
        if (date.getTime() < competition.getEnrollStartTime().getTime() || date.getTime() > competition.getEnrollEndTime().getTime()) {
            return Result.error(CodeMsg.ADMIN_NOT_TIME_RANGE_ERROR);
        }


        teamCompetition.setStatus(TeamCompetition.CHECK_CENTER);
        if(!teamCompetitionService.saveAndCompetition(teamCompetition, competition))
        {
            return Result.error(CodeMsg.ADMIN_CHECK_APPLY_ERROR_2);
        }

        return Result.success(true);
    }

    /**
     * 踢人
     * @param id 团队成员表ID
     * @param teamCompetitionId 比赛ID
     * @return
     */
    @RequestMapping(value = "kick_out", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> kickOut(Long id,@RequestParam("teamId") Long teamCompetitionId)
    {
        //判断登录是否是队长
        Student login = SessionUtil.getLoginedStudent();

        TeamCompetition teamCompetition = teamCompetitionService.find(teamCompetitionId);
        if(login.getId().longValue() != teamCompetition.getStudent().getId().longValue())
        {
            return Result.error(CodeMsg.ADMIN_IS_CAPTAIN_ERROR);
        }

        //判断是不是队长踢出自己
        TeamMember teamMember = teamMemberService.find(id);

        if(teamMember.getStudent().getId().longValue() == login.getId().longValue())
        {
            return Result.error(CodeMsg.ADMIN_KICK_OUT_ERROR);
        }

        //判断是否已经报名了
        if(teamCompetition.getStatus() != TeamCompetition.NOT_CHECK_CENTER)
        {
            return Result.error(CodeMsg.ADMIN_KICK_OUT_ERROR_2);
        }

        try
        {
            teamMemberService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_KICK_OUT_NOT_FOUNT_ERROR);
        }

        return Result.success(true);
    }
}
