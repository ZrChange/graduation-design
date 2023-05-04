package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.ProfessionalDao;
import com.yuanlrc.base.entity.admin.Grade;
import com.yuanlrc.base.entity.admin.Professional;
import com.yuanlrc.base.entity.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhong
 * @Date 2020-12-23
 */
@Service
public class ProfessionalService {

    @Autowired
    private ProfessionalDao professionalDao;

    public Professional find(Long id)
    {
        return professionalDao.find(id);
    }

    public Professional findByName(String name)
    {
        return professionalDao.findByName(name);
    }

    public Professional save(Professional professional)
    {
        return professionalDao.save(professional);
    }

    public void delete(Long id)
    {
        professionalDao.deleteById(id);
    }

    public List<Professional> findAll()
    {
        return professionalDao.findAll();
    }

    public List<Professional> findByCollegeId(Long id)
    {
        if(id==-1){
            return professionalDao.findAll();
        }else{
            return professionalDao.findByCollegeId(id);
        }
    }

    public PageBean<Professional> findList(Professional professional, PageBean<Professional> pageBean)
    {

        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Professional> example = Example.of(professional, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Professional> findAll = professionalDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    /**
     * 是否重复
     * @param professional
     * @return
     */
    public boolean isRepetition(Professional professional)
    {
        //添加
        Professional find = findByName(professional.getName());
        if(find != null) {
            //编辑
            if (professional.getId() != null && find.getId() != professional.getId()) {
                return true;
            }else if (professional.getId() == null) {
                return true;
            }
        }

        return false; //不重复
    }
}
