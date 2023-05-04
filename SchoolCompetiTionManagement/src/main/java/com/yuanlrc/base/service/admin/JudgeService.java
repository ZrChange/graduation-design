package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.JudgeDao;
import com.yuanlrc.base.dao.admin.RoleDao;
import com.yuanlrc.base.entity.admin.College;
import com.yuanlrc.base.entity.admin.Judge;
import com.yuanlrc.base.entity.admin.Role;
import com.yuanlrc.base.entity.admin.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评委Service层
 */
@Service
public class JudgeService {

    @Autowired
    private JudgeDao judgeDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Judge find(Long id) {
        return judgeDao.find(id);
    }

    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    public Judge findByMobile(String mobile){
        return judgeDao.findByMobile(mobile);
    }


    /**
     * 分页查询评委信息
     *
     * @param judge
     * @param pageBean
     * @return
     */
    public PageBean<Judge> findList(Judge judge, PageBean<Judge> pageBean) {
        ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        withMatcher = withMatcher.withIgnorePaths("status","sex");
        Example<Judge> example = Example.of(judge, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<Judge> findAll = judgeDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 保存
     *
     * @param judge
     * @return
     */
    public Judge save(Judge judge) {
        Role role = roleDao.find(RoleEnum.JUDGE.getCode());
        judge.setRole(role);
        return judgeDao.save(judge);
    }

    /**
     * 判断手机号是否存在
     *
     * @param mobile
     * @param id
     * @return
     */
    public Boolean isExistMobile(String mobile, Long id) {
        Judge byMobile = judgeDao.findByMobile(mobile);
        if (byMobile != null) {
            //手机号存在时，判断是否是本身
            if (byMobile.getId().longValue() != id.longValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据id删除信息
     *
     * @param id
     */
    public void delete(Long id) {
        judgeDao.deleteById(id);
    }

    /**
     * 查询评委
     *
     * @return
     */
    public List<Judge> findJudgeList(Long collegeId) {
        //查询所有
        if (collegeId == -1) {
            return judgeDao.findAll();
        } else {
            //根据学院查询评委
            return judgeDao.findByCollege_Id(collegeId);
        }
    }
}
