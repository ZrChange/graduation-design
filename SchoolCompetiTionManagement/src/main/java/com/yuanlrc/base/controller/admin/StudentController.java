package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.constant.SessionConstant;
import com.yuanlrc.base.entity.admin.Clazz;
import com.yuanlrc.base.entity.admin.Professional;
import com.yuanlrc.base.entity.admin.Role;
import com.yuanlrc.base.entity.admin.Student;
import com.yuanlrc.base.service.admin.*;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import com.yuanlrc.base.util.ZrStringUtil;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhong
 * @date 2020-12-23
 */
@Controller
@RequestMapping("/admin/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private ProfessionalService professionalService;


    @RequestMapping("/list")
    public String list(Model model, Student student, PageBean<Student> pageBean)
    {
        model.addAttribute("title", "学生管理");
        model.addAttribute("pageBean", studentService.findList(student, pageBean));
        model.addAttribute("name", student.getName());

        return "admin/student/list";
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result<Boolean> delete(Long id)
    {
        try
        {
            studentService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_DELETE_ERROR);
        }

        operaterLogService.add("删除学生成功:" + id);
        return Result.success(true);
    }

    @RequestMapping("/add")
    public String add(Model model)
    {
        model.addAttribute("colleges", collegeService.findAll());
        return "admin/student/add";
    }

    @RequestMapping("/edit")
    public String edit(Model model,Long id)
    {
        model.addAttribute("student", studentService.find(id));
        return "admin/student/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result<Boolean> add(Student student)
    {
        CodeMsg codeMsg = ValidateEntityUtil.validate(student);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
        {
            return Result.error(codeMsg);
        }

        if(student.getClazz() == null || student.getClazz().getId() == null)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_CLAZZ_NULL_ERROR);
        }

        if(studentService.findByStudentNumber(student.getStudentNumber()) != null)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_NUMBER_EXSITER_ERROR);
        }

        //验证手机号
        //手机号验证
        String chkphone = "^1[3|4|5|7|8]\\d{9}$";
        Pattern r = Pattern.compile(chkphone);
        if (student.getPhone() == null){
            return Result.error(CodeMsg.ADMIN_STUDENT_PHONE_NULL_ERROR);
        }

        Matcher m = r.matcher(student.getPhone());
        if (!m.matches()){
            return Result.error(CodeMsg.ADMIN_STUDENT_PHONE_FORMAT_ERROR);
        }

        //判断手机号是否注册过了
        Student findByPhone = studentService.findByPhone(student.getPhone());
        if(findByPhone != null)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_PHONE_REPETITION_ERROR);
        }

        if(!(student.getAge() > 0 && student.getAge() < 200))
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_AGE_VALUE_ERROR);
        }

        student.setRole(studentService.getRole());


        if(studentService.save(student) == null)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_ADD_ERROR);
        }


        return Result.success(true);
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result<Boolean> edit(Student student)
    {
        Student find = studentService.find(student.getId());
        if(find == null)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_EDIT_ERROR);
        }

        find.setStatus(student.getStatus());
        if(studentService.save(find) == null)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_EDIT_ERROR);
        }

        return Result.success(true);
    }


    /**
     * 导出
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/export")
    public void export(HttpServletResponse response) throws Exception
    {
        //查出所有的学生信息
        List<Student> studentList = studentService.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("学生信息表");

        //设置要导出的文件的名字
        String fileName = "studentInfo"+".xlsx";

        //新增数据行,并且设置单元格数据
        int rowNum = 1;
        //headers表示excel表中第一行的表头
        String[] headers = {"学号","姓名","性别","年龄","班级","手机号"};
        HSSFRow row = sheet.createRow(0);

        for (int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (Student student : studentList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(student.getStudentNumber());
            row1.createCell(1).setCellValue(student.getName());
            row1.createCell(2).setCellValue(studentService.getSex(student.getSex()));
            row1.createCell(3).setCellValue(student.getAge());
            row1.createCell(4).setCellValue(student.getClazz().getName());
            row1.createCell(5).setCellValue(student.getPhone());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @RequestMapping(value = "/lead", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> lead(@RequestParam("file") MultipartFile file)throws Exception
    {
        HashMap<String, Student> studentHashMap = new HashMap<>();
        HashMap<String, Student> studentHashMapById = new HashMap<>();

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        InputStream ins = file.getInputStream();

        //判断是否是xlsx文件或xls
        if (!suffix.equals("xlsx")&&!suffix.equals("xls")){
            return Result.error(CodeMsg.STUDENT_IMPORT_ADD_ERROR);
        }

        Workbook workbook = new HSSFWorkbook(ins);


        //获取Excel
        Sheet sheet = workbook.getSheetAt(0);
        if(sheet == null)
            return Result.error(CodeMsg.STUDENT_IMPORT_ADD_ERROR);


        String number = "";
        String name = "";
        String sex = "";
        String age = "";
        String clazz = "";
        String phone = "";

        for (int line = 1;line<=sheet.getLastRowNum();line++){
            Student student = new Student();
            Row row = sheet.getRow(line);

            //如果没有这一行跳过
            if(row == null)
                continue;

            try
            {
                row.getCell(0).setCellType(CellType.STRING);
                number = row.getCell(0).getStringCellValue();
                row.getCell(1).setCellType(CellType.STRING);
                name = row.getCell(1).getStringCellValue();
                row.getCell(2).setCellType(CellType.STRING);
                sex = row.getCell(2).getStringCellValue();
                row.getCell(3).setCellType(CellType.STRING);
                age = row.getCell(3).getStringCellValue();
                row.getCell(4).setCellType(CellType.STRING);
                clazz = row.getCell(4).getStringCellValue();
                row.getCell(5).setCellType(CellType.STRING);
                phone = row.getCell(5).getStringCellValue();

            }catch (Exception e)
            {
                return Result.error(CodeMsg.STUDENT_IMPORT_EXCEL_ERROR);
            }

            if(ZrStringUtil.isEmpty(name) || ZrStringUtil.isEmpty(sex)
                    || ZrStringUtil.isEmpty(age) || ZrStringUtil.isEmpty(clazz)
            || ZrStringUtil.isEmpty(phone) || ZrStringUtil.isEmpty(number))
                return Result.error(CodeMsg.STUDENT_IMPORT_EXCEL_NULL_ERROR);

            //手机号验证
            String chkphone = "^1[3|4|5|7|8]\\d{9}$";
            Pattern r = Pattern.compile(chkphone);
            Matcher m = r.matcher(phone);
            if (!m.matches()){
                return Result.error(CodeMsg.ADMIN_STUDENT_PHONE_FORMAT_ERROR);
            }

            //判断手机号
            Student findByPhone = studentService.findByPhone(phone);
            if(findByPhone != null)
                return Result.error(CodeMsg.ADMIN_STUDENT_PHONE_REPETITION_ERROR);


            Clazz clazz1 = clazzService.findByName(clazz);
            if(clazz1 == null)
            {
                return Result.error(CodeMsg.ADMIN_IMPORT_CLAZZ_NOT_FOUND_ERROR);
            }

            //学号验证
            if(studentService.findByStudentNumber(number) != null)
            {
                return Result.error(CodeMsg.ADMIN_STUDENT_NUMBER_EXSITER_ERROR);
            }

            //
            try
            {
                Long studentNumber = Long.valueOf(number);
            }catch (Exception e)
            {
                return Result.error(CodeMsg.ADMIN_STUDENT_IMPORT_NUMBER_ERROR);
            }

            student.setStudentNumber(number);
            student.setName(name.trim());
            student.setSex(studentService.getSex(sex.trim()));
            student.setPhone(phone.trim());
            student.setClazz(clazz1);
            try
            {
                student.setAge(Integer.valueOf(age.trim()));
            }catch (Exception e)
            {
                return Result.error(CodeMsg.ADMIN_STUDENT_AGE_VALUE_ERROR_2);
            }

            student.setPassword("123456");
            student.setRole(studentService.getRole());

            //判断手机号是否重复
            Student hashFindByPhone = studentHashMap.get(student.getPhone());
            if(hashFindByPhone != null)
                return Result.error(CodeMsg.ADMIN_STUDENT_PHONE_REPETITION_ERROR);

            //判断学号是否重复
            Student hashFindByNumber = studentHashMapById.get(student.getStudentNumber());
            if(hashFindByNumber != null)
                return Result.error(CodeMsg.ADMIN_STUDENT_NUMBER_EXSITER_ERROR);

            //放入hashmap
            CodeMsg validate = ValidateEntityUtil.validate(student);
            if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
                return Result.error(validate);
            }

            studentHashMap.put(student.getPhone(), student);
            studentHashMapById.put(student.getStudentNumber(), student);
        }
        if(!studentService.saveList(studentHashMap))
        {
            return Result.error(CodeMsg.STUDENT_EXCEL_IMPORT_ERROR);
        }

        return Result.success(true);
    }


    /**
     * 个人信息展示
     * @return
     */
    @RequestMapping("/info")
    public String info(Model model)
    {
        Student stu = SessionUtil.getLoginedStudent();
        model.addAttribute("student", stu);

        return "admin/student/info";
    }

    /**
     * 修改个人信息
     */
    @RequestMapping("/editInfo")
    @ResponseBody
    public Result<Boolean> editInfo(Student student)
    {

        Student findByID = studentService.find(student.getId());
        if(findByID == null)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_EDIT_ERROR);
        }

        findByID.setName(student.getName());
        findByID.setAge(student.getAge());
        findByID.setHeadPic(student.getHeadPic());
        findByID.setSex(student.getSex());

        CodeMsg codeMsg = ValidateEntityUtil.validate(findByID);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
        {
            return Result.error(codeMsg);
        }

        if(studentService.save(findByID) == null)
        {
            return Result.error(CodeMsg.ADMIN_STUDENT_EDIT_ERROR);
        }

        Student login = SessionUtil.getLoginedStudent();
        login.setName(student.getName());
        login.setAge(student.getAge());
        login.setHeadPic(student.getHeadPic());
        login.setSex(student.getSex());
        SessionUtil.set(SessionConstant.SESSION_STUDENT_LOGIN_KEY, login);

        return Result.success(true);
    }


    /**
     * 根据学院ID选择专业
     * @param collegeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/find_professional", method = RequestMethod.POST)
    public Result findProfessional(@RequestParam("id") Long collegeId)
    {

        List<Professional> professionals = (ArrayList<Professional>) professionalService.findByCollegeId(collegeId);
        if(professionals == null)
            professionals = new ArrayList<>();

        return Result.success(professionals);
    }

    @ResponseBody
    @RequestMapping(value = "/find_clazz", method = RequestMethod.POST)
    public Result findClazz(@RequestParam("id")Long professionalId)
    {
        List<Clazz> clazzes = clazzService.findByProfessionalId(professionalId);
        if(clazzes == null)
            clazzes = new ArrayList<>();
        return Result.success(clazzes);
    }
}
