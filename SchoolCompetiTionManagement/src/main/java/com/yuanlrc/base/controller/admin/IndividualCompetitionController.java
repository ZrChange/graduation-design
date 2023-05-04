package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.*;
import com.yuanlrc.base.service.admin.*;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 个人竞赛表Controller层
 */
@Controller
@RequestMapping("/admin/individualCompetition")
public class IndividualCompetitionController {

    @Autowired
    private IndividualCompetitionService individualCompetitionService;

    @Autowired
    private CompetitionService competitionService;


    @Autowired
    private TeamCompetitionService teamCompetitionService;

    @Autowired
    private IndividualAccessoryService individualAccessoryService;

    /**
     * 查询
     * @param model
     * @param pageBean
     * @param competitionTitle
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<IndividualCompetition> pageBean,
                       @RequestParam(name="competitionTitle",required=false) String competitionTitle){
        Student loginedStudent = SessionUtil.getLoginedStudent();
        model.addAttribute("title","个人参赛列表");
        model.addAttribute("competitionTitle",competitionTitle);
        model.addAttribute("pageBean",individualCompetitionService.findList(pageBean,competitionTitle,loginedStudent.getId()));
        return "admin/individualCompetition/list";
    }

    /**
     * 判断报名是否通过
     * @param id
     * @return
     */
    @RequestMapping(value = "/applyStatus",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> applyStatus(@RequestParam(name="id",required=true)Long id){
        IndividualCompetition individualCompetition = individualCompetitionService.find(id);
        if(individualCompetition.getApplyStatus() != IndividualCompetition.APPROVE_IS_PASSED){
            return Result.error(CodeMsg.APPROVE_NOT_PASSED);
        }
        return Result.success(true);
    }

    /**
     * 根据个人竞赛表id查询
     * @param id
     * @return
     */
    @RequestMapping("/showScore")
    public String showScore(Model model,@RequestParam(name="id",required=true)Long id){
        IndividualCompetition individualCompetition = individualCompetitionService.find(id);
        Student loginedStudent = SessionUtil.getLoginedStudent();
        if(individualCompetition.getStudent().getId().longValue() != loginedStudent.getId().longValue()){
            return "redirect:/admin/individualCompetition/list";
        }

        model.addAttribute("individualCompetition",individualCompetition);
        return "/admin/individualCompetition/score";

    }

    /**
     * 报名
     * @param id 竞赛id
     * @return
     */
    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> apply(@RequestParam(name="id",required=true)Long id){
        Competition competition = competitionService.find(id);
        if(competition == null || competition.getId() == null){
            return Result.error(CodeMsg.COMPETITION_IS_EMPTY);
        }
        if(competition.getCompetitionType() == CompetitionType.TEAM.getCode()){
            return Result.error(CodeMsg.COMPETITION_NOT_PERSONAL);
        }
        Student loginedStudent = SessionUtil.getLoginedStudent();
        if(competition.getRaceType() == RaceType.PROFESSIONAL.getCode()){
            if(loginedStudent.getClazz().getProfessional().getId().longValue() != competition.getProfessional().getId().longValue()){
                return Result.error(CodeMsg.STUDENT_PROFESSIONAL_ERROR);
            }
        }
        Date date = new Date();
        if(date.getTime() < competition.getEnrollStartTime().getTime() || date.getTime() > competition.getEnrollEndTime().getTime()){
            return Result.error(CodeMsg.ENROLL_TIME_ERROR);
        }
        //判断是否已报名
        if(individualCompetitionService.isApply(loginedStudent.getId(),competition.getId())){
            return Result.error(CodeMsg.IS_APPLY_ERROR);
        }
        //判断报名人数是否已满
        if(competition.getSignedUp() == competition.getEnrollmentNumber()){
            return Result.error(CodeMsg.ENROLLMENT_NUBER_IS_FULL);
        }
        IndividualCompetition individualCompetition = new IndividualCompetition();
        individualCompetition.setStudent(loginedStudent);
        individualCompetition.setCompetition(competition);

        if(individualCompetitionService.save(individualCompetition,competition)){
            return Result.error(CodeMsg.APPLY_ERROR);
        }
        return Result.success(true);
    }

    /*@RequestMapping(value = "/competitionType",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> competitionType(@RequestParam(value = "id",required = true)Long id){

        return Result.success(true);
    }*/


    /**
     * 查看个人竞赛展示页面
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/viewPersionSignUp", method = RequestMethod.GET)
    public String viewPersionSignUp(Model model,IndividualCompetition personalCompetition,PageBean<IndividualCompetition>pageBean,@RequestParam(name = "type",required = true)int type) {
        PageBean<IndividualCompetition> personList = individualCompetitionService.findByCompetition(pageBean, personalCompetition.getId(), type);
        Competition competition = competitionService.find(personalCompetition.getId());
        model.addAttribute("pageBean",personList);
        model.addAttribute("competition",competition);
        model.addAttribute("comType",type);
        return "admin/competition/view_persion_sign_up";
    }


    /**
     * 上传附件
     */
    @RequestMapping(value = "upload_accessory", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> uploadAccessory(Long id, String resocre)
    {
        IndividualCompetition individualCompetition = individualCompetitionService.find(id);
        if(resocre.trim().length() == 0)
        {
            return Result.error(CodeMsg.ADMIN_UPLOAD_ACCESSORY_ERROR);
        }

        Student loginStudent = SessionUtil.getLoginedStudent();
        Student student = individualCompetition.getStudent();
        if(loginStudent.getId().longValue() !=  student.getId().longValue())
        {
            return Result.error(CodeMsg.ADMIN_ACCESSORY_ERROR);
        }

        IndividualAccessory individualAccessory = individualAccessoryService.findByIndividualCompetitionId(individualCompetition.getId());

        //判断是否报名通过
        if(individualCompetition.getApplyStatus() != IndividualCompetition.APPROVE_IS_PASSED)
        {
            return Result.error(CodeMsg.ADMIN_APPLY_STATUS_ERROR);
        }

        Competition competition = individualCompetition.getCompetition();

        //判断比赛是否开始
        Date date = new Date();
        if(competition.getPreliminariesStartTime().getTime() > date.getTime())
        {
            return Result.error(CodeMsg.ADMIN_ACCESSORY_START_TIME_ERROR);
        }

        //判断是否在初赛时间段
        if (date.getTime() > competition.getPreliminariesStartTime().getTime() &&
                date.getTime() < competition.getPreliminariesEndTime().getTime()) {

            //如果上传过了
            if(individualAccessory != null)
            {
                return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }

            //没有上传
            individualAccessory = new IndividualAccessory();
            individualAccessory.setIndividualCompetition(individualCompetition);
            individualAccessory.setPreliminariesAccessory(resocre);
        }
        else if (date.getTime() > competition.getSecondRoundStartTime().getTime() &&
                date.getTime() < competition.getSecondRoundEndTime().getTime()) {
            //判断是否在复赛时间段
            //判断初赛是否通过
            if(individualCompetition.getPreliminariesStatus() != IndividualCompetition.PRELIMINARIES_IS_PASSED)
            {
                return Result.error(CodeMsg.ADMIN_PRELIMINARIES_NOT_PASS_ERROR);
            }

            //如果初赛没有上传过附件
            if(individualAccessory == null) {
               individualAccessory = new IndividualAccessory();
                individualAccessory.setIndividualCompetition(individualCompetition);
            }else{
                //如果复赛上传附件了
                if(individualAccessory.getSecondRoundStatus() == AccessoryType.SUBMITTED.getCode())
                    return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }

            individualAccessory.setSecondRoundAccessory(resocre);
            individualAccessory.setSecondRoundStatus(AccessoryType.SUBMITTED.getCode());

        }
        else if (date.getTime() > competition.getFinalsStartTime().getTime() &&
                date.getTime() < competition.getFinalsEndTime().getTime()) {
            //判断是否在决赛时间段

            //判断复赛是否通过
            if(individualCompetition.getSecondRoundStatus() != IndividualCompetition.SECOND_ROUND_IS_PASSED)
            {
                return Result.error(CodeMsg.ADMIN_SECOND_ROUND_NOT_PASS_ERROR);
            }

            //如果初赛复赛未上传
            if(individualAccessory == null)
            {
                individualAccessory = new IndividualAccessory();
                individualAccessory.setIndividualCompetition(individualCompetition);
            }else{
                //如果已经上传过了
                //如果复赛上传附件了
                if(individualAccessory.getFinalsStatus() == AccessoryType.SUBMITTED.getCode())
                    return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }

            individualAccessory.setFinalsAccessory(resocre);
            individualAccessory.setFinalsStatus(AccessoryType.SUBMITTED.getCode());
        }
        else
        {
            return Result.error(CodeMsg.ADMIN_UPLOAD_ACCESSORY_NOT_FOUND_ERROR);
        }

        if(individualAccessoryService.save(individualAccessory) == null) {
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
        IndividualCompetition individualCompetition = individualCompetitionService.find(id);

        IndividualAccessory individualAccessory = individualAccessoryService.findByIndividualCompetitionId(individualCompetition.getId());

        //报名是否通过
        if(individualCompetition.getApplyStatus() != IndividualCompetition.APPROVE_IS_PASSED)
        {
            return Result.error(CodeMsg.ADMIN_APPLY_STATUS_ERROR);
        }

        Competition competition = individualCompetition.getCompetition();

        //判断比赛是否开始
        Date date = new Date();
        if(competition.getPreliminariesStartTime().getTime() > date.getTime())
        {
            return Result.error(CodeMsg.ADMIN_ACCESSORY_START_TIME_ERROR);
        }

        //判断是否在初赛时间段
        if (date.getTime() > competition.getPreliminariesStartTime().getTime() &&
                date.getTime() < competition.getPreliminariesEndTime().getTime()) {

            //如果上传过了
            if(individualAccessory != null)
            {
                return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }
        }
        else if (date.getTime() > competition.getSecondRoundStartTime().getTime() &&
                date.getTime() < competition.getSecondRoundEndTime().getTime()) {
            //判断是否在复赛时间段
            //判断初赛是否通过
            if(individualCompetition.getPreliminariesStatus() != IndividualCompetition.PRELIMINARIES_IS_PASSED)
            {
                return Result.error(CodeMsg.ADMIN_PRELIMINARIES_NOT_PASS_ERROR);
            }

            //如果初赛没有上传过附件
            if(individualAccessory != null) {
                //如果复赛上传附件了
                if(individualAccessory.getSecondRoundStatus() == AccessoryType.SUBMITTED.getCode())
                    return Result.error(CodeMsg.ADMIN_ACCESSORY_UPLOAD_ERROR);
            }
        }
        else if (date.getTime() > competition.getFinalsStartTime().getTime() &&
                date.getTime() < competition.getFinalsEndTime().getTime()) {
            //判断是否在决赛时间段

            //判断复赛是否通过
            if(individualCompetition.getSecondRoundStatus() != IndividualCompetition.SECOND_ROUND_IS_PASSED)
            {
                return Result.error(CodeMsg.ADMIN_SECOND_ROUND_NOT_PASS_ERROR);
            }

            //如果初赛复赛未上传
            if(individualAccessory != null) {
                //如果复赛上传附件了
                if(individualAccessory.getFinalsStatus() == AccessoryType.SUBMITTED.getCode())
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
        IndividualCompetition individualCompetition = individualCompetitionService.find(id);

        IndividualAccessory individualAccessory = individualAccessoryService.findByIndividualCompetitionId(individualCompetition.getId());
        if(individualAccessory == null)
        {
            return Result.error(CodeMsg.ADMIN_DOWNLOAD_ACCESSORY_ERROR);
        }

        //报名是否通过
        if(individualCompetition.getApplyStatus() != IndividualCompetition.APPROVE_IS_PASSED)
        {
            return Result.error(CodeMsg.ADMIN_APPLY_STATUS_ERROR);
        }

        Competition competition = individualCompetition.getCompetition();

        //判断比赛是否开始
        Date date = new Date();
        if(competition.getPreliminariesStartTime().getTime() > date.getTime())
        {
            return Result.error(CodeMsg.ADMIN_ACCESSORY_START_TIME_ERROR);
        }

        return Result.success(true);
    }

}
