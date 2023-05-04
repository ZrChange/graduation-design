package com.yuanlrc.base.controller.admin;


import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.dao.admin.ProfessionalDao;
import com.yuanlrc.base.entity.admin.Professional;
import com.yuanlrc.base.service.admin.CollegeService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.admin.ProfessionalService;
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
@RequestMapping("/admin/professional")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private CollegeService collegeService;


    @RequestMapping("/list")
    public String list(Model model, Professional professional, PageBean<Professional> pageBean)
    {
        model.addAttribute("title", "专业管理");
        model.addAttribute("pageBean", professionalService.findList(professional, pageBean));
        model.addAttribute("name", professional.getName());

        return "admin/professional/list";
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result<Boolean> delete(Long id)
    {
        try {
            professionalService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_PROFESSIONAL_DELETE_ERROR);
        }

        operaterLogService.add("专业删除成功:" + id);
        return Result.success(true);
    }

    @RequestMapping("/add")
    public String add(Model model)
    {
        model.addAttribute("colleges", collegeService.findAll());
        return "admin/professional/add";
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Boolean> add(Professional professional)
    {
        CodeMsg codeMsg = ValidateEntityUtil.validate(professional);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
        {
            return Result.error(codeMsg);
        }

        if(professionalService.isRepetition(professional))
        {
            return Result.error(CodeMsg.ADMIN_PROFESSIONAL_NAME_ERROR);
        }

        if(professional.getCollege() == null || professional.getCollege().getId() == null)
        {
            return Result.error(CodeMsg.ADMIN_PROFESSIONAL_COLLEGE_NULL_ERROR);
        }

        if(professionalService.save(professional) == null)
        {
            return Result.error(CodeMsg.ADMIN_PROFESSIONAL_ADD_ERROR);
        }

        return Result.success(true);
    }


    @RequestMapping("/edit")
    public String edit(Model model, Long id)
    {
        model.addAttribute("professional", professionalService.find(id));
        model.addAttribute("colleges", collegeService.findAll());
        return "admin/professional/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Professional professional)
    {
        CodeMsg codeMsg = ValidateEntityUtil.validate(professional);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
        {
            return Result.error(codeMsg);
        }

        if(professionalService.isRepetition(professional))
        {
            return Result.error(CodeMsg.ADMIN_PROFESSIONAL_NAME_ERROR);
        }

        if(professional.getCollege() == null || professional.getCollege().getId() == null)
        {
            return Result.error(CodeMsg.ADMIN_PROFESSIONAL_COLLEGE_NULL_ERROR);
        }

        Professional find = professionalService.find(professional.getId());
        if(find == null)
        {
            return Result.error(CodeMsg.ADMIN_PROFESSIONAL_EDIT_ERROR);
        }

        find.setCollege(professional.getCollege());
        find.setName(professional.getName());

        if(professionalService.save(find) == null)
        {
            return Result.error(CodeMsg.ADMIN_PROFESSIONAL_EDIT_ERROR);
        }

        return Result.success(true);
    }
}
