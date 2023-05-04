package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.LoginType;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.RaceType;
import com.yuanlrc.base.dao.admin.CompetitionDao;
import com.yuanlrc.base.dao.admin.CompetitionDao;
import com.yuanlrc.base.entity.admin.*;
import com.yuanlrc.base.entity.admin.Competition;
import com.yuanlrc.base.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 竞赛Service层
 */
@Service
public class CompetitionService {

    @Autowired
    private CompetitionDao competitionDao;

    /**
     * 根据id查询竞赛
     *
     * @param id
     * @return
     */
    public Competition find(Long id) {
        return competitionDao.find(id);
    }

    /**
     * 分页查询竞赛信息
     *
     * @param competition
     * @param pageBean
     * @return
     */
    public PageBean<Competition> findList(Competition competition, PageBean<Competition> pageBean, int loginType) {
        Student loginedStudent = SessionUtil.getLoginedStudent();
        Specification<Competition> specification = new Specification<Competition>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Competition> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("title"), "%" + (competition.getTitle() == null ? "" : competition.getTitle()) + "%");
                if (loginType == LoginType.STUDENT.getCode()) {
                    if(RaceType.OPENNESS.getCode()==competition.getRaceType()){
                        Predicate equal = criteriaBuilder.equal(root.get("raceType"), competition.getRaceType());
                        predicate = criteriaBuilder.and(predicate, equal);
                    }else{
                        ListJoin<Competition, Grade> join = root.join(root.getModel().getList("grades", Grade.class), JoinType.LEFT);
                        Predicate equal1 = criteriaBuilder.equal(join.get("id").as(Long.class), loginedStudent.getClazz().getGrade().getId());
                        predicate = criteriaBuilder.and(predicate, equal1);
                        Predicate equal2 = criteriaBuilder.equal(root.get("professional"), loginedStudent.getClazz().getProfessional().getId());
                        predicate = criteriaBuilder.and(predicate, equal2);
                    }
                } else if (loginType == LoginType.JUDGE.getCode()) {
                    Judge loginedJudge = SessionUtil.getLoginedJudge();
                    ListJoin<Competition, Judge> join = root.join(root.getModel().getList("judges", Judge.class), JoinType.LEFT);
                    Predicate equal6 = criteriaBuilder.equal(join.get("id").as(Long.class), loginedJudge.getId());
                    predicate = criteriaBuilder.and(predicate, equal6);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<Competition> findAll1 = competitionDao.findAll(specification, pageable);
        pageBean.setContent(findAll1.getContent());
        pageBean.setTotal(findAll1.getTotalElements());
        pageBean.setTotalPage(findAll1.getTotalPages());
        return pageBean;
    }

    /**
     * 保存/更新
     *
     * @param competition
     * @return
     */
    public Competition save(Competition competition) {
        return competitionDao.save(competition);
    }

    /**
     * 根据id删除竞赛
     *
     * @param id
     */
    public void delete(Long id) {
        competitionDao.deleteById(id);
    }

    /**
     * 查询所有的竞赛
     *
     * @return
     */
    public List<Competition> findAll() {
        return competitionDao.findAll();
    }

}
