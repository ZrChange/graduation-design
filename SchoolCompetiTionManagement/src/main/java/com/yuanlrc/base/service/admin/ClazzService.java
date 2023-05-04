package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.ClazzDao;
import com.yuanlrc.base.entity.admin.Clazz;
import com.yuanlrc.base.entity.admin.Grade;
import com.yuanlrc.base.entity.admin.Judge;
import com.yuanlrc.base.entity.admin.Professional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班级Service层
 */
@Service
public class ClazzService {

    @Autowired
    private ClazzDao clazzDao;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Clazz find(Long id){
        return clazzDao.find(id);
    }

    /**
     * 分页查询班级信息
     * @param clazz
     * @param pageBean
     * @return
     */
    public PageBean<Clazz> findList(Clazz clazz, PageBean<Clazz> pageBean){
        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Clazz> example = Example.of(clazz, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<Clazz> findAll = clazzDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 保存
     * @param clazz
     * @return
     */
    public Clazz save(Clazz clazz){
        return clazzDao.save(clazz);
    }

    /**
     * 根据年级 专业判断该班级名称在当前年级 专业是否存在
     * @param name
     * @param grade
     * @param professional
     * @param id
     * @return
     */
    public boolean isExistName(String name, Grade grade, Professional professional,Long id){
        Clazz clazz = clazzDao.findByNameAndGrade_IdAndProfessional_Id(name, grade.getId(), professional.getId());
        if(clazz != null){
            //存在时，判断是否是本身
            if(clazz.getId().longValue() != id.longValue()){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(Long id){
        clazzDao.deleteById(id);
    }

    /**
     * 查询全部
     * @return
     */
    public List<Clazz> findAll()
    {
        return clazzDao.findAll();
    }

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    public Clazz findByName(String name)
    {
        return clazzDao.findByName(name);
    }

    /**
     * 根据专业id查询
     * @param id
     * @return
     */
    public List<Clazz> findByProfessionalId(Long id)
    {
        return clazzDao.findByProfessionalId(id);
    }
}
