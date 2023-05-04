package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.CompetitionProcess;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.ScoreType;
import com.yuanlrc.base.dao.admin.CompetitionDao;
import com.yuanlrc.base.dao.admin.IndividualCompetitionDao;
import com.yuanlrc.base.entity.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * 个人竞赛表Service层
 */
@Service
public class IndividualCompetitionService {

    @Autowired
    private IndividualCompetitionDao individualCompetitionDao;

    @Autowired
    private CompetitionDao competitionDao;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public IndividualCompetition find(Long id) {
        return individualCompetitionDao.find(id);
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<IndividualCompetition> findAll() {
        return individualCompetitionDao.findAll();
    }

    /**
     * 根据学生id查询报名过的个人类竞赛
     *
     * @param pageBean
     * @param title
     * @param id
     * @return
     */
    public PageBean<IndividualCompetition> findList(PageBean<IndividualCompetition> pageBean, String title, Long id) {
        Specification<IndividualCompetition> specification = new Specification<IndividualCompetition>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<IndividualCompetition> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("competition").get("title"), "%" + (title == null ? "" : title) + "%");
                Predicate equal1 = criteriaBuilder.equal(root.get("student"), id);
                predicate = criteriaBuilder.and(predicate, equal1);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<IndividualCompetition> findAll = individualCompetitionDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 报名审批保存
     *
     * @param individualCompetition
     * @return
     */
    @Transactional
    public boolean saveApplyStatus(IndividualCompetition individualCompetition, Competition competition) {
        Boolean flage = false;
        try {
            individualCompetitionDao.save(individualCompetition);
            competitionDao.save(competition);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            flage = true;
        }
        return flage;
    }

    /**
     * 报名保存
     *
     * @param individualCompetition
     * @return
     */
    @Transactional
    public boolean save(IndividualCompetition individualCompetition, Competition competition) {
        Boolean flage = false;
        try {
            individualCompetitionDao.save(individualCompetition);
            competition.setSignedUp(competition.getSignedUp() + 1);
            competitionDao.save(competition);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            flage = true;
        }
        return flage;
    }

    /**
     * 根据竞赛id分页查询个人参赛表
     *
     * @param competitionId
     * @return
     */
    public PageBean<IndividualCompetition> findByCompetition(PageBean<IndividualCompetition> pageBean, Long competitionId, int type) {
        Specification<IndividualCompetition> specification = new Specification<IndividualCompetition>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<IndividualCompetition> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("competition"), competitionId);
                if (type == CompetitionProcess.SIGNUP.getCode()) {
                    Predicate equal1 = criteriaBuilder.notEqual(root.get("applyStatus"), IndividualCompetition.APPROVE_NOT_PASSED);
                    predicate = criteriaBuilder.and(predicate, equal1);
                } else if (type == CompetitionProcess.PRELIMINARIES.getCode()) {
                    Predicate equa2 = criteriaBuilder.equal(root.get("applyStatus"), IndividualCompetition.APPROVE_IS_PASSED);
                    predicate = criteriaBuilder.and(predicate, equa2);
                } else if (type == CompetitionProcess.SECONDROUND.getCode()) {
                    Predicate equal3 = criteriaBuilder.equal(root.get("preliminariesStatus"), IndividualCompetition.PRELIMINARIES_IS_PASSED);
                    predicate = criteriaBuilder.and(predicate, equal3);
                } else if (type == CompetitionProcess.FINALS.getCode()) {
                    Predicate equal4 = criteriaBuilder.equal(root.get("secondRoundStatus"), IndividualCompetition.SECOND_ROUND_IS_PASSED);
                    predicate = criteriaBuilder.and(predicate, equal4);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<IndividualCompetition> findAll = individualCompetitionDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 判断是否已报名
     *
     * @param studentId
     * @param competitionId
     * @return
     */
    public boolean isApply(Long studentId, Long competitionId) {
        List<IndividualCompetition> individualCompetitionList = individualCompetitionDao.findByStudent_IdAndCompetition_IdAndApplyStatusNot(studentId, competitionId, IndividualCompetition.APPROVE_NOT_PASSED);
        if (individualCompetitionList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据竞赛id查询该竞赛是否有人参加
     *
     * @param cid
     * @return
     */
    public List<IndividualCompetition> findByCompetitionId(Long cid) {
        return individualCompetitionDao.findByCompetitionId(cid);
    }

    /**
     * 晋级保存
     *
     * @param individualCompetition
     * @param competition
     * @return
     */
    public IndividualCompetition savePromotion( Date date,IndividualCompetition individualCompetition, Competition competition) {
        if (date.getTime() < competition.getSecondRoundStartTime().getTime()) {
            //初赛晋级
            individualCompetition.setPreliminariesStatus(IndividualCompetition.PRELIMINARIES_IS_PASSED);
        } else {//如果是复赛
            //复赛晋级
            individualCompetition.setSecondRoundStatus(IndividualCompetition.SECOND_ROUND_IS_PASSED);
        }
        return individualCompetitionDao.save(individualCompetition);
    }

    /**
     * 判断该阶段是否已评分
     * @param competition
     * @param individualCompetition
     * @return
     */
    public boolean isScores(Date date,Competition competition, IndividualCompetition individualCompetition) {
        if (date.getTime() < competition.getSecondRoundStartTime().getTime()) {
            //如果有成绩信息
            if (individualCompetition.getPreliminariesScoreStatus() == ScoreType.SCORE_IS_HAS.getCode()) {
                return true;
            }
        } else if (date.getTime() < competition.getFinalsStartTime().getTime()) {//如果是复赛
            //如果复赛评分状态为已评分
            if (individualCompetition.getSecondRoundScoreStatus() == ScoreType.SCORE_IS_HAS.getCode()) {
                return true;
            }
        } else {//决赛
            //如果决赛评分状态为已评分
            if (individualCompetition.getFinalsScoreStatus() == ScoreType.SCORE_IS_HAS.getCode()) {
                return true;
            }
        }
        return false;

    }

    /**
     * 评分保存
     * @param individualCompetition
     * @param competition
     * @param scores
     * @return
     */
    public IndividualCompetition saveScore(Date date,IndividualCompetition individualCompetition, Competition competition, int scores) {
        if (date.getTime() < competition.getSecondRoundStartTime().getTime()) {
            //初赛
            individualCompetition.setPreliminariesScore(scores);
            individualCompetition.setPreliminariesScoreStatus(ScoreType.SCORE_IS_HAS.getCode());
        } else if (date.getTime() < competition.getFinalsStartTime().getTime()) {//如果是复赛
            //复赛
            individualCompetition.setSecondRoundScore(scores);
            individualCompetition.setSecondRoundScoreStatus(ScoreType.SCORE_IS_HAS.getCode());
        } else{//决赛
            individualCompetition.setFinalsScore(scores);
            individualCompetition.setFinalsScoreStatus(ScoreType.SCORE_IS_HAS.getCode());
        }
        return individualCompetitionDao.save(individualCompetition);
    }

    /**
     * 判断是否已经评过分，评过分才能晋级
     * @param date
     * @param
     * @param competition
     * @return
     */
    public boolean canPromotion(Date date, IndividualCompetition individualCompetition, Competition competition) {
        //初赛晋级时间阶段
        if(date.getTime() < competition.getSecondRoundStartTime().getTime()){
            //如果为空 说明还没进行初赛评分
            if(individualCompetition.getPreliminariesScoreStatus() == ScoreType.SCORE_NOT_HAS.getCode()){
                return true;
            }
        }else if (date.getTime() < competition.getFinalsStartTime().getTime()) {//如果是复赛
            //如果复赛评分状态为未评分
            if (individualCompetition.getSecondRoundScoreStatus() == ScoreType.SCORE_NOT_HAS.getCode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否已晋级
     * @param date
     * @param individualCompetition
     * @param competition
     * @return
     */
    public boolean hasPromotion(Date date, IndividualCompetition individualCompetition, Competition competition) {
        //初赛晋级时间阶段
        if(date.getTime() < competition.getSecondRoundStartTime().getTime()){
            if(individualCompetition.getPreliminariesStatus() == IndividualCompetition.PRELIMINARIES_IS_PASSED){
                return true;
            }
        }else if (date.getTime() < competition.getFinalsStartTime().getTime()) {//如果是复赛
            if (individualCompetition.getSecondRoundStatus() == IndividualCompetition.SECOND_ROUND_IS_PASSED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查询该竞赛决赛前10名
     * @return
     */
    public List<IndividualCompetition> findTopTen(Long cid){
        return individualCompetitionDao.findTopTen(ScoreType.SCORE_IS_HAS.getCode(),cid);
    }
}
