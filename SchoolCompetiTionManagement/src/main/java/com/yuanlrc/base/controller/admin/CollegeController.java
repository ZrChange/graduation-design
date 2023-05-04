package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.College;
import com.yuanlrc.base.entity.admin.Professional;
import com.yuanlrc.base.service.admin.CollegeService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.admin.ProfessionalService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学院Controller层
 */
@Controller
@RequestMapping("/admin/college")
public class CollegeController {

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private ProfessionalService professionalService;

    /**
     * 学校列表
     * @param model
     * @param pageBean
     * @param college
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<College>pageBean,College college){
        model.addAttribute("title", "学院列表");
        model.addAttribute("name",college.getName());
        model.addAttribute("pageBean",collegeService.findList(college,pageBean));
        return "admin/college/list";
    }

    /**
     * 学院添加页面
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return "admin/college/add";
    }

    /**
     * 添加学院提交处理
     * @param college
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(College college){
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(college);
        if(validate.getCode()!=CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        //判断学院名称是否存在
        if(collegeService.isExistName(college.getName(),0l)){
            return Result.error(CodeMsg.COLLEGE_NAME_EXIST);
        }
        //进行数据库添加
        if(collegeService.save(college) == null){
            return Result.error(CodeMsg.COLLEGE_ADD_ERROR);
        }
        operaterLogService.add("添加学院，学院名称："+college.getName());
        return Result.success(true);
    }

    /**
     * 学院编辑页面
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model,@RequestParam(name="id",required=true)Long id){
        College college = collegeService.find(id);
        if(college != null){
            model.addAttribute("college",college);
            return "admin/college/edit";
        }
        return "admin/college/list";
    }

    /**
     * 编辑学院提交处理
     * @param college
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(College college){
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(college);
        if(validate.getCode()!=CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(college.getId() == null || college.getId().longValue()<= 0){
            return Result.error(CodeMsg.COLLEGE_NO_EXIST);
        }

        //判断学院名称是否存在
        if(collegeService.isExistName(college.getName(),college.getId())){
            return Result.error(CodeMsg.COLLEGE_NAME_EXIST);
        }

        College findById = collegeService.find(college.getId());
        ///讲提交的用户信息指定字段复制到已存在的college对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(college, findById, "id","createTime","updateTime");
        //进行数据库添加
        if(collegeService.save(findById) == null){
            return Result.error(CodeMsg.COLLEGE_ADD_ERROR);
        }
        operaterLogService.add("编辑学院，学院名称："+college.getName());
        return Result.success(true);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id){
        try{
            collegeService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.COLLEGE_DELETE_ERROR);
        }
        operaterLogService.add("删除学院，学院ID："+id);
        return Result.success(true);
    }

    /**
     * 根据学院查询专业
     * @param
     * @return
     */
    @ResponseBody
    @GetMapping("/majorList")
    public Result<List<Professional>>majorListByCollegeId(@RequestParam(name = "collegeId",required = true)Long collegeId){
        List<Professional> professionalsList = professionalService.findByCollegeId(collegeId);
        return Result.success(professionalsList);
    }
}
