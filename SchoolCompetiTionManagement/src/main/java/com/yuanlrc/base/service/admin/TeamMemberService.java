package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.CompetitionDao;
import com.yuanlrc.base.dao.admin.TeamApplyDao;
import com.yuanlrc.base.dao.admin.TeamCompetitionDao;
import com.yuanlrc.base.dao.admin.TeamMemberDao;
import com.yuanlrc.base.entity.admin.Competition;
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

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberDao teamMemberDao;

    @Autowired
    private TeamCompetitionService teamCompetitionService;

    @Autowired
    private TeamApplyDao teamApplyDao;

    @Autowired
    private CompetitionDao competitionDao;

    public TeamMember find(Long id)
    {
        return teamMemberDao.find(id);
    }

    public TeamMember save(TeamMember teamMember)
    {
        return teamMemberDao.save(teamMember);
    }

    public void delete(Long id)
    {
        teamMemberDao.deleteById(id);
    }

    public List<TeamMember> findAll()
    {
        return teamMemberDao.findAll();
    }

    public PageBean<TeamMember> findList(TeamMember teamMember, PageBean<TeamMember> pageBean)
    {
        ExampleMatcher withMatcher = ExampleMatcher.matching()
                .withMatcher("student.name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("student.status", "student.sex");

        Example<TeamMember> example = Example.of(teamMember, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<TeamMember> findAll = teamMemberDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }


    public PageBean<TeamMember> findListByStudentId(PageBean<TeamMember> pageBean, String title, Long id) {
        Specification<TeamMember> specification = new Specification<TeamMember>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<TeamMember> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("teamCompetition").get("competition").get("title"), "%" + (title == null ? "" : title) + "%");
                Predicate equal1 = criteriaBuilder.equal(root.get("student"),id);

                predicate = criteriaBuilder.and(predicate,equal1);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(), sort);
        Page<TeamMember> findAll = teamMemberDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    //根据团队竞赛表ID查出团队成员
    public List<TeamMember> findByTeamCompetitionId(Long id)
    {
        return teamMemberDao.findByTeamCompetitionId(id);
    }

    //根据团队竞赛表ID查出我是否在这个团队里面
    public TeamMember findByTeamCompetitionIdAndStudentId(Long teamCompetitionId, Long studentId)
    {
        return teamMemberDao.findByTeamCompetitionIdAndStudentId(teamCompetitionId, studentId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteList(List<TeamMember> members, TeamCompetition teamCompetition, List<TeamApply> teamApplies) {
        boolean flag = true;
        try {
            for (TeamMember item: members) {
                delete(item.getId());
            }
            teamCompetitionService.delete(teamCompetition.getId());

            for (TeamApply teamApply : teamApplies) {
                teamApplyDao.deleteById(teamApply.getId());
            }

            Competition competition = teamCompetition.getCompetition();
            competition.setSignedUp(competition.getSignedUp());
            competitionDao.save(competition);

        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            flag = false;
        }

        return flag;
    }

    //根据学生ID和赛项ID查出有没有参加过这个比赛
    public Integer findByTeamCompetitionCompetitionAndStudentIdAndStatus(Long competitionId, Long studentId)
    {
        return teamMemberDao.findByTeamCompetitionCompetitionAndStudentIdAndStatus(competitionId, studentId);
    }
}
