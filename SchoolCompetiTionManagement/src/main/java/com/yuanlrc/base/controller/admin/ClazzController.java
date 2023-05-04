package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Clazz;
import com.yuanlrc.base.entity.admin.Professional;
import com.yuanlrc.base.service.admin.*;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 班级Controller层
 */
@Controller
@RequestMapping("/admin/clazz")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private OperaterLogService operaterLogService;

    /**
     *  班级列表
     * @param model
     * @param clazz
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, Clazz clazz, PageBean<Clazz> pageBean){
        model.addAttribute("title","班级列表");
        model.addAttribute("name",clazz.getName());
        model.addAttribute("pageBean",clazzService.findList(clazz,pageBean));
        return "admin/clazz/list";
    }

    /**
     * 班级添加页面
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("grades",gradeService.findAll());
        model.addAttribute("colleges",collegeService.findAll());
        return "admin/clazz/add";
    }

    /**
     * 根据学院id查询专业
     * @param id
     * @return
     */
    @RequestMapping(value = "/professional",method = RequestMethod.POST)
    @ResponseBody
    public List<Professional> professional(@RequestParam(name="id",required=true) Long id){
        return professionalService.findByCollegeId(id);
    }

    /**
     * 添加班级提交处理
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Clazz clazz){
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(clazz);
        if(validate.getCode()!=CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(clazz.getGrade() == null || clazz.getGrade().getId() == null){
            return Result.error(CodeMsg.CLAZZ_GRADE_EMPTY);
        }
        if(clazz.getProfessional() == null || clazz.getProfessional().getId() == null){
            return Result.error(CodeMsg.CLAZZ_PROFESSIONAL_EMPTY);
        }
        //判断该年级、该专业是否存在这个班级名称
        if(clazzService.isExistName(clazz.getName(),clazz.getGrade(),clazz.getProfessional(),0l)){
            return Result.error(CodeMsg.CLAZZ_NAME_EXIST);
        }
        if(clazzService.save(clazz) == null){
            return Result.error(CodeMsg.CLAZZ_ADD_ERROR);
        }
        operaterLogService.add("添加班级，班级名称："+clazz.getName());
        return Result.success(true);
    }

    /**
     * 编辑页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    private String edit(Model model,@RequestParam(name="id",required=true)Long id){
        model.addAttribute("clazz",clazzService.find(id));
        model.addAttribute("grades",gradeService.findAll());
        /*model.addAttribute("professionals",professionalService.findAll());*/
        model.addAttribute("colleges",collegeService.findAll());
        return "admin/clazz/edit";
    }

    /**
     * 班级编辑提交处理
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    private Result<Boolean> edit(Clazz clazz){
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(clazz);
        if(validate.getCode()!=CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(clazz.getGrade() == null || clazz.getGrade().getId() == null){
            return Result.error(CodeMsg.CLAZZ_GRADE_EMPTY);
        }
        if(clazz.getProfessional() == null || clazz.getProfessional().getId() == null){
            return Result.error(CodeMsg.CLAZZ_PROFESSIONAL_EMPTY);
        }
        if(clazz.getId() == null || clazz.getId().longValue() <= 0){
            return Result.error(CodeMsg.CLAZZ_NO_EXIST);
        }
        //判断该年级、该专业是否存在这个班级名称
        if(clazzService.isExistName(clazz.getName(),clazz.getGrade(),clazz.getProfessional(),clazz.getId())){
            return Result.error(CodeMsg.CLAZZ_NAME_EXIST);
        }
        Clazz findById = clazzService.find(clazz.getId());
        BeanUtils.copyProperties(clazz, findById, "id","createTime","updateTime");
        if(clazzService.save(findById) == null){
            return Result.error(CodeMsg.CLAZZ_ADD_ERROR);
        }
        operaterLogService.add("编辑班级，班级名称："+clazz.getName());
        return Result.success(true);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    private Result<Boolean> delete(@RequestParam(name = "id",required = true) Long id){
        try{
            clazzService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.CLAZZ_DELETE_ERROR);
        }
        operaterLogService.add("删除班级，班级ID："+id);
        return Result.success(true);
    }
}
