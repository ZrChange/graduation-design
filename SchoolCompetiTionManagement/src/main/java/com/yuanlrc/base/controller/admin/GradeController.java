package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Grade;
import com.yuanlrc.base.service.admin.GradeService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhong
 * @Date 2020-12-23
 */
@Controller
@RequestMapping("/admin/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private OperaterLogService operaterLogService;

    @RequestMapping("/list")
    public String list(Model model, Grade grade, PageBean<Grade> pageBean)
    {
        model.addAttribute("title", "年级管理");
        model.addAttribute("pageBean", gradeService.findList(grade, pageBean));
        model.addAttribute("name", grade.getName());
        return "admin/grade/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id)
    {
        try
        {
            gradeService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_GRADE_DELETE_ERROR);
        }

        operaterLogService.add("删除年级成功:" + id);
        return Result.success(true);
    }


    @RequestMapping("/add")
    public String add(Model model)
    {

        return "admin/grade/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Grade grade)
    {
        CodeMsg codeMsg = ValidateEntityUtil.validate(grade);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
        {
            return Result.error(codeMsg);
        }

        if(gradeService.isRepetition(grade))
        {
            return Result.error(CodeMsg.ADMIN_GRADE_NAME_ERROR);
        }

        if(gradeService.save(grade) == null)
        {
            return Result.error(CodeMsg.ADMIN_GRADE_ADD_ERROR);
        }

        operaterLogService.add("添加年级成功:" + grade.getName());
        return Result.success(true);
    }


    @RequestMapping("/edit")
    public String edit(Model model, Long id)
    {
        model.addAttribute("grade", gradeService.find(id));
        return "admin/grade/edit";
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Grade grade)
    {
        CodeMsg codeMsg = ValidateEntityUtil.validate(grade);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
        {
            return Result.error(codeMsg);
        }

        if(gradeService.isRepetition(grade))
        {
            return Result.error(CodeMsg.ADMIN_GRADE_NAME_ERROR);
        }

        Grade find = gradeService.find(grade.getId());
        if(find == null)
        {
            return Result.error(CodeMsg.ADMIN_GRADE_EDIT_ERROR);
        }

        find.setName(grade.getName());
        if(gradeService.save(find) == null)
        {
            return Result.error(CodeMsg.ADMIN_GRADE_EDIT_ERROR);
        }


        return Result.success(true);
    }
}
