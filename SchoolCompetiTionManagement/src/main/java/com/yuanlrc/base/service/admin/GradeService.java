package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.GradeDao;
import com.yuanlrc.base.entity.admin.Grade;
import com.yuanlrc.base.entity.admin.Professional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhong
 * @Date 2020-12-23
 */
@Service
public class GradeService {

    @Autowired
    private GradeDao gradeDao;


    public Grade find(Long id) {
        return gradeDao.find(id);
    }

    public Grade findByName(String name) {
        return gradeDao.findByName(name);
    }

    public Grade save(Grade grade) {
        return gradeDao.save(grade);
    }

    public void delete(Long id) {
        gradeDao.deleteById(id);
    }

    public List<Grade> findAll() {
        return gradeDao.findAll();
    }

    public PageBean<Grade> findList(Grade professional, PageBean<Grade> pageBean) {

        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("name",
                ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Grade> example = Example.of(professional, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<Grade> findAll = gradeDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    /**
     * 是否重复
     *
     * @param grade
     * @return
     */
    public boolean isRepetition(Grade grade) {
        //添加
        Grade find = findByName(grade.getName());
        if(find != null)
        {
            //编辑
            if(grade.getId() != null && find.getId() != grade.getId())
                return true;
            else if(grade.getId() == null)
                return true;
        }
        return false; //不重复
    }
}
