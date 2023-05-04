package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.TeamApplyDao;
import com.yuanlrc.base.dao.admin.TeamMemberDao;
import com.yuanlrc.base.entity.admin.TeamAccessory;
import com.yuanlrc.base.entity.admin.TeamApply;
import com.yuanlrc.base.entity.admin.TeamCompetition;
import com.yuanlrc.base.entity.admin.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author  zhong
 * @date 团队申请表
 */
@Service
public class TeamApplyService {

    @Autowired
    private TeamApplyDao teamApplyDao;

    @Autowired
    private TeamMemberDao teamMemberDao;

    public TeamApply find(Long id)
    {
        return teamApplyDao.find(id);
    }

    public TeamApply save(TeamApply teamApply)
    {
        return teamApplyDao.save(teamApply);
    }

    @Transactional
    public boolean save(TeamMember teamMember, TeamApply teamApply)
    {
        try
        {
            teamApply.setStatus(TeamApply.PASS);
            teamMemberDao.save(teamMember);
            teamApplyDao.save(teamApply);
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    public void delete(Long id)
    {
        teamApplyDao.deleteById(id);
    }

    public List<TeamApply> findAll()
    {
        return teamApplyDao.findAll();
    }

    public PageBean<TeamApply> findList(TeamApply teamApply, PageBean<TeamApply> pageBean)
    {

        ExampleMatcher withMatcher = ExampleMatcher.matching()
                .withMatcher("student.name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("status", "student.status", "student.sex");

        Example<TeamApply> example = Example.of(teamApply, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<TeamApply> findAll = teamApplyDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    public PageBean<TeamApply> findListByTeamCompetitionId(PageBean<TeamApply> pageBean, String name, Long competition)
    {
        Specification<TeamApply> specification = new Specification<TeamApply>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<TeamApply> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("student").get("name"), name == null ? "%%" : "%"+name+"%");
                Predicate eq = criteriaBuilder.equal(root.get("teamCompetition"), competition);

                predicate = criteriaBuilder.and(predicate, eq);
                return predicate;
            }
        };

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<TeamApply> findAll = teamApplyDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    public List<TeamApply> findByTeamCompetitionId(Long id)
    {
        return teamApplyDao.findByTeamCompetitionId(id);
    }
}
