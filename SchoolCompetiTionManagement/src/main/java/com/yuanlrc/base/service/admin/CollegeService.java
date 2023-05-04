package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.CollegeDao;
import com.yuanlrc.base.entity.admin.College;
import com.yuanlrc.base.entity.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学院Service层
 */
@Service
public class CollegeService {

    @Autowired
    private CollegeDao collegeDao;

    /**
     * 根据id查询学院信息
     * @param id
     * @return
     */
    public College find(Long id){
        return collegeDao.find(id);
    }

    /**
     * 根据name查询学院信息
     * @param name
     * @return
     */
    public College findByName(String name){
        return collegeDao.findByName(name);
    }

    /**
     * 分页查询学院
     * @param college
     * @param pageBean
     * @return
     */
    public PageBean<College> findList(College college,PageBean<College> pageBean){
        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<College> example = Example.of(college, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<College> findAll = collegeDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 学院保存、编辑
     * @param college
     * @return
     */
    public College save(College college){
        return collegeDao.save(college);
    }

    /**
     * 判断学院名称是否存在
     * @param name
     * @param id
     * @return
     */
    public boolean isExistName(String name,Long id){
        College college = collegeDao.findByName(name);
        if(college != null){
            //学院名称存在时，判断是否是本身
            if(college.getId().longValue() != id.longValue()){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据id删除学院信息
     * @param id
     */
    public void delete(Long id){
        collegeDao.deleteById(id);
    }

    /**
     * 查询全部学院信息
     * @return
     */
    public List<College> findAll() {
        return collegeDao.findAll();
    }
}
