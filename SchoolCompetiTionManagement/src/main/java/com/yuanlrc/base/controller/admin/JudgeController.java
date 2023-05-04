package com.yuanlrc.base.controller.admin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.constant.SessionConstant;
import com.yuanlrc.base.dao.admin.JudgeDao;
import com.yuanlrc.base.entity.admin.College;
import com.yuanlrc.base.entity.admin.Judge;
import com.yuanlrc.base.entity.admin.Professional;
import com.yuanlrc.base.service.admin.CollegeService;
import com.yuanlrc.base.service.admin.JudgeService;
import com.yuanlrc.base.service.admin.OperaterLogService;

import com.yuanlrc.base.service.admin.ProfessionalService;
import com.yuanlrc.base.util.SessionUtil;

import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评委Controller层
 */
@Controller
@RequestMapping("/admin/judge")
public class JudgeController {

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private ProfessionalService professionalService;

    /**
     * 评委列表
     * @param model
     * @param judge
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, Judge judge, PageBean<Judge> pageBean){
        model.addAttribute("title","评委列表");
        model.addAttribute("name",judge.getName());
        model.addAttribute("pageBean",judgeService.findList(judge,pageBean));
        return "admin/judge/list";
    }

    /**
     * 个人信息
     * @param model
     * @return
     */
    @RequestMapping("/self")
    public String self(Model model){
        model.addAttribute("title","个人信息");
        model.addAttribute("colleges",collegeService.findAll());
        model.addAttribute("judge",SessionUtil.getLoginedJudge());
        return "admin/judge/editSelf";
    }

    /**
     * 评委添加页面
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("collegeList",collegeService.findAll());
        return "admin/judge/add";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Judge judge){
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(judge);
        if(validate.getCode()!=CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(judge.getCollege() == null || judge.getCollege().getId() == null){
            return Result.error(CodeMsg.JUDGE_COLLEGE_EMPTY);
        }

        //判断手机号格式是否正确
        if(!StringUtil.isMobile(judge.getMobile())){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        //判断手机号是否存在
        if(judgeService.isExistMobile(judge.getMobile(),0l)){
            return Result.error(CodeMsg.JUDGE_MOBILE_EXIST);
        }
        //符合条件，数据库保存
        if(judgeService.save(judge) == null){
            return Result.error(CodeMsg.JUDGE_ADD_ERROR);
        }
        operaterLogService.add("添加评委，评委姓名："+judge.getName());
        return Result.success(true);
    }

    /**
     * 评委编辑页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model,@RequestParam(name="id",required=true)Long id){
        model.addAttribute("judge",judgeService.find(id));
        model.addAttribute("colleges",collegeService.findAll());
        return "admin/judge/edit";
    }

    /**
     * 编辑评委提交处理
     * @param judge
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Judge judge){
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(judge);
        if(validate.getCode()!=CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(judge.getCollege() == null || judge.getCollege().getId() == null){
            return Result.error(CodeMsg.JUDGE_COLLEGE_EMPTY);
        }
        if(judge.getId() == null || judge.getId().longValue() <= 0){
            return Result.error(CodeMsg.JUDGE_NO_EXIST);
        }
        //判断手机号格式是否正确
        if(!StringUtil.isMobile(judge.getMobile())){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        //判断手机号是否存在
        if(judgeService.isExistMobile(judge.getMobile(),judge.getId())){
            return Result.error(CodeMsg.JUDGE_MOBILE_EXIST);
        }
        Judge findById = judgeService.find(judge.getId());
        ///讲提交的用户信息指定字段复制到已存在的college对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(judge, findById, "id","createTime","updateTime");
        //符合条件，数据库保存
        if(judgeService.save(findById) == null){
            return Result.error(CodeMsg.JUDGE_ADD_ERROR);
        }
        operaterLogService.add("编辑评委，评委姓名："+judge.getName());
        return Result.success(true);
    }

    /**
     * 评委修改自己信息页面
     * @param model
     * @return
     */
    @RequestMapping("/editSelf")
    public String editSelf(Model model){
        model.addAttribute("judge",SessionUtil.getLoginedJudge());
        model.addAttribute("colleges",collegeService.findAll());
        return "admin/judge/editSelf";
    }

    /**
     * 评委个人信息修改提交处理
     * @param judge
     * @return
     */
    @RequestMapping(value = "/editSelf",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> editSelf(Judge judge){
        Judge loginedJudge = SessionUtil.getLoginedJudge();
        Judge findById = judgeService.find(judge.getId());
        judge.setPassword(findById.getPassword());

        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(judge);
        if(validate.getCode()!=CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        /*if(judge.getCollege() == null || judge.getCollege().getId() == null){
            return Result.error(CodeMsg.JUDGE_COLLEGE_EMPTY);
        }*/
        //判断手机号格式是否正确
        if(!StringUtil.isMobile(judge.getMobile())){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        //判断手机号是否存在
        if(judgeService.isExistMobile(judge.getMobile(),judge.getId())){
            return Result.error(CodeMsg.JUDGE_MOBILE_EXIST);
        }
        ///讲提交的用户信息指定字段复制到已存在的college对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(judge, findById, "id","createTime","updateTime","status","college");
        //符合条件，数据库保存
        if(judgeService.save(findById) == null){
            return Result.error(CodeMsg.JUDGE_ADD_ERROR);
        }
        operaterLogService.add("编辑评委，评委姓名："+judge.getName());
        //更新Session
        loginedJudge.setHeadPic(findById.getHeadPic());
        loginedJudge.setMobile(findById.getMobile());
        loginedJudge.setName(findById.getName());
        loginedJudge.setCollege(findById.getCollege());
        loginedJudge.setSex(findById.getSex());
        SessionUtil.set(SessionConstant.SESSION_JUDGE_LOGIN_KEY,loginedJudge);
        return Result.success(true);
    }


    /**
     * 根据id删除评委
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id){
        try{
            judgeService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.JUDGE_DELETE_ERROR);
        }
        operaterLogService.add("删除评委，评委ID："+id);
        return Result.success(true);
    }

    /**
     * 查询评委
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("/judgeList")
    public Result<List<Judge>>findALlJudge(@RequestParam(name = "collegeId",required = true)Long collegeId){
        List<Judge> judgesList = judgeService.findJudgeList(collegeId);
        professionalService.findByCollegeId(collegeId);
        return Result.success(judgesList);
    }


}
