package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.StudentDao;
import com.yuanlrc.base.entity.admin.Professional;
import com.yuanlrc.base.entity.admin.Role;
import com.yuanlrc.base.entity.admin.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhong
 * @date 2020-12-23
 */
@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private RoleService roleService;

    public Student find(Long id)
    {
        return studentDao.find(id);
    }

    public Student save(Student student)
    {
        return studentDao.save(student);
    }

    public void delete(Long id)
    {
        studentDao.deleteById(id);
    }

    public List<Student> findAll()
    {
        return studentDao.findAll();
    }

    public PageBean<Student> findList(Student student, PageBean<Student> pageBean)
    {

        ExampleMatcher withMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("status", "sex");

        Example<Student> example = Example.of(student, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Student> findAll = studentDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    public Student findByPhone(String phone) {
        return studentDao.findByPhone(phone);
    }

    public String getSex(Integer sex)
    {
        if(sex == 0)
            return "未知";
        else if(sex == 1)
            return "男";
        else
            return "女";
    }

    public Integer getSex(String sex)
    {
        if(sex.trim().equals("男"))
            return 1;
        else if(sex.trim().equals("女"))
            return 2;
        else
            return 0;
    }

    //获取学生角色
    public Role getRole() {

        return roleService.find(10L);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveList(HashMap<String, Student> studentHashMap) {
        Boolean flag = true;
        try
        {
            for (String phone : studentHashMap.keySet())
            {
                studentDao.save(studentHashMap.get(phone));
            }
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            flag = false;
        }

        return flag;
    }

    /**
     * 根据学号
     * @param number
     * @return
     */
    public Student findByStudentNumber(String number)
    {
        return studentDao.findByStudentNumber(number);
    }
}
