package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.CompetitionProcess;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.ScoreType;
import com.yuanlrc.base.dao.admin.CompetitionDao;
import com.yuanlrc.base.dao.admin.TeamApplyDao;
import com.yuanlrc.base.dao.admin.TeamCompetitionDao;

import com.yuanlrc.base.dao.admin.TeamMemberDao;
import com.yuanlrc.base.entity.admin.Competition;
import com.yuanlrc.base.entity.admin.IndividualCompetition;
import com.yuanlrc.base.entity.admin.TeamCompetition;
import com.yuanlrc.base.entity.admin.TeamMember;
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

@Service
public class TeamCompetitionService {

    @Autowired
    private TeamCompetitionDao teamCompetitionDao;

    @Autowired
    private CompetitionDao competitionDao;

    @Autowired
    private TeamMemberDao teamMemberDao;

    public TeamCompetition find(Long id)
    {
        return teamCompetitionDao.find(id);
    }

    public TeamCompetition save(TeamCompetition teamCompetition)
    {
        return teamCompetitionDao.save(teamCompetition);
    }

    @Transactional
    public boolean saveAndCompetition(TeamCompetition teamCompetition, Competition competition)
    {
        try
        {
            teamCompetitionDao.save(teamCompetition);
            competition.setSignedUp(competition.getSignedUp() + 1);
            competitionDao.save(competition);
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Transactional
    public boolean saveStatus(TeamCompetition teamCompetition, Competition competition){
        Boolean flage = false;
        try{
            teamCompetitionDao.save(teamCompetition);
            competitionDao.save(competition);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            flage = true;
        }
        return flage;
    }

    public void delete(Long id)
    {
        teamCompetitionDao.deleteById(id);
    }

    public List<TeamCompetition> findAll()
    {
        return teamCompetitionDao.findAll();
    }

    public PageBean<TeamCompetition> findList(TeamCompetition teamApply, PageBean<TeamCompetition> pageBean)
    {

        ExampleMatcher withMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("status", "preliminariesStatus", "secondRoundStatus");

        Example<TeamCompetition> example = Example.of(teamApply, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<TeamCompetition> findAll = teamCompetitionDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }


    /**
     * 查出所有这个竞赛报名通过的团队
     * @return
     */
    public List<TeamCompetition> findByCompetitionIdAndStatusAll(Long competition, Integer status)
    {
        return teamCompetitionDao.findByCompetitionIdAndStatus(competition, status);
    }


    /**
     * 查出所有这个竞赛报名通过的团队 分页
     * @param pageBean
     * @param competitionId
     * @return
     */
    public PageBean<TeamCompetition> findByCompetitionIdAndStatusList( PageBean<TeamCompetition> pageBean, Long competitionId)
    {
        Specification<TeamCompetition> specification = new Specification<TeamCompetition>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<TeamCompetition> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("competition"), competitionId);
                Predicate status = criteriaBuilder.equal(root.get("status"), 1);
                predicate = criteriaBuilder.and(predicate, status);
                return predicate;
            }
        };

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<TeamCompetition> findAll = teamCompetitionDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }


    /**
     * 查询出所有
     * @param pageBean
     * @param competitionId
     * @param name
     * @return
     */
    public PageBean<TeamCompetition> findByCompetitionIdList(PageBean<TeamCompetition> pageBean, Long competitionId, String name)
    {
        Specification<TeamCompetition> specification = new Specification<TeamCompetition>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<TeamCompetition> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("competition"), competitionId);
                Predicate like = criteriaBuilder.like(root.get("name"), name != null ? "%" + name + "%" : "%%");
                Predicate status = criteriaBuilder.notEqual(root.get("status"),TeamCompetition.NOT_PASS);

                predicate = criteriaBuilder.and(predicate, like, status);

                return predicate;
            }
        };


        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<TeamCompetition> findAll = teamCompetitionDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 保存报名
     * @param teamCompetition
     * @param competition
     * @return
     */
    @Transactional
    public boolean saveTeam(TeamCompetition teamCompetition, Competition competition) {
        Boolean flage = false;
        try{
            teamCompetitionDao.save(teamCompetition);
            competition.setSignedUp(competition.getSignedUp());
            competitionDao.save(competition);

            TeamMember teamMember = new TeamMember();
            teamMember.setStudent(teamCompetition.getStudent());
            teamMember.setTeamCompetition(teamCompetition);

            teamMemberDao.save(teamMember);
        }catch (Exception e){
            flage = true;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return  flage;
    }

    /**
     * 根据竞赛id查询是否有团队已参加
     * @param cid
     * @return
     */
    public List<TeamCompetition>  findByCompetitionId (Long cid){
        return teamCompetitionDao.findByCompetitionId(cid);
    }


    /**
     * 根据竞赛id分页查询团队参赛表
     * @param pageBean
     * @param competitionId
     * @param type
     * @return
     */
    public PageBean<TeamCompetition> findByTeamCompetitionList(PageBean<TeamCompetition> pageBean,Long competitionId,int type)
    {
        Specification<TeamCompetition> specification = new Specification<TeamCompetition>() {

            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<TeamCompetition> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("competition"), competitionId);
                if(type==CompetitionProcess.SIGNUP.getCode()){
                    //报名
                    Predicate equal1 = criteriaBuilder.equal(root.get("status"), TeamCompetition.CHECK_CENTER);
                    predicate = criteriaBuilder.and(predicate,equal1);
                    Predicate equal5 = criteriaBuilder.equal(root.get("status"), TeamCompetition.PASS);
                    predicate = criteriaBuilder.or(predicate, equal5);
                    Predicate equal6 = criteriaBuilder.equal(root.get("competition"), competitionId);
                    predicate = criteriaBuilder.and(predicate, equal6);
                }else if(type==CompetitionProcess.PRELIMINARIES.getCode()){
                    //初赛
                    Predicate equal2 = criteriaBuilder.equal(root.get("status"), TeamCompetition.PASS);
                    predicate = criteriaBuilder.and(predicate,equal2);
                }else if(type==CompetitionProcess.SECONDROUND.getCode()){
                    //复赛
                    Predicate equal3 = criteriaBuilder.equal(root.get("preliminariesStatus"), TeamCompetition.PASS);
                    predicate = criteriaBuilder.and(predicate,equal3);
                }else if(type==CompetitionProcess.FINALS.getCode()){
                    //决赛
                    Predicate equal4 = criteriaBuilder.equal(root.get("secondRoundStatus"), TeamCompetition.PASS);
                    predicate = criteriaBuilder.and(predicate,equal4);
                }
                return predicate;
            }
        };
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<TeamCompetition> findAll = teamCompetitionDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 保存晋级状态
     * @param teamCompetition
     * @param competition
     * @return
     */
    public TeamCompetition savePromotion(Date date,TeamCompetition teamCompetition, Competition competition) {
        if (date.getTime() < competition.getSecondRoundStartTime().getTime()) {
            //初赛晋级
            teamCompetition.setPreliminariesStatus(IndividualCompetition.PRELIMINARIES_IS_PASSED);
        } else {//如果是复赛
            //复赛晋级
            teamCompetition.setSecondRoundStatus(IndividualCompetition.SECOND_ROUND_IS_PASSED);
        }
        return teamCompetitionDao.save(teamCompetition);
    }


    public TeamCompetition findByCompetitionIdAndStudentIdAndStatusNot(Long competitionId, Long id, Integer notPass) {
        return teamCompetitionDao.findByCompetitionIdAndStudentIdAndStatusNot(competitionId, id, notPass);
    }


    /**
     * 判断是否已评分
     * @param date
     * @param competition
     * @param teamCompetition
     * @return
     */
    public boolean isResult(Date date,Competition competition, TeamCompetition teamCompetition) {
        if (date.getTime() < competition.getSecondRoundStartTime().getTime()) {
            //如果有成绩信息
            if (teamCompetition.getPreliminariesScoreStatus() == ScoreType.SCORE_IS_HAS.getCode()) {
                return true;
            }
        } else if (date.getTime() < competition.getFinalsStartTime().getTime()) {//如果是复赛
            //如果复赛评分状态为已评分
            if (teamCompetition.getSecondRoundScoreStatus() == ScoreType.SCORE_IS_HAS.getCode()) {
                return true;
            }
        } else {//决赛
            //如果决赛评分状态为已评分
            if (teamCompetition.getFinalsScoreStatus() == ScoreType.SCORE_IS_HAS.getCode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 保存分数
     * @param date
     * @param teamCompetition
     * @param competition
     * @param scores
     * @return
     */
    public TeamCompetition saveScore(Date date,TeamCompetition teamCompetition, Competition competition, int scores) {
        if (date.getTime() < competition.getSecondRoundStartTime().getTime()) {
            //初赛
            teamCompetition.setPreliminariesScore(scores);
            teamCompetition.setPreliminariesScoreStatus(ScoreType.SCORE_IS_HAS.getCode());
        } else if (date.getTime() < competition.getFinalsStartTime().getTime()) {//如果是复赛
            //复赛
            teamCompetition.setSecondRoundScore(scores);
            teamCompetition.setSecondRoundScoreStatus(ScoreType.SCORE_IS_HAS.getCode());
        } else {//决赛
            teamCompetition.setFinalsScore(scores);
            teamCompetition.setFinalsScoreStatus(ScoreType.SCORE_IS_HAS.getCode());
        }
        return teamCompetitionDao.save(teamCompetition);
    }

    /**
     * 判断是否已经评过分
     * @param date
     * @param teamCompetition
     * @param competition
     * @return
     */
    public boolean canPromotion(Date date, TeamCompetition teamCompetition, Competition competition) {
        //初赛晋级时间阶段
        if(date.getTime() < competition.getSecondRoundStartTime().getTime()){
            //如果为空 说明还没进行初赛评分
            if(teamCompetition.getPreliminariesScoreStatus() == ScoreType.SCORE_NOT_HAS.getCode()){
                return true;
            }
        }else if (date.getTime() < competition.getFinalsStartTime().getTime()) {//如果是复赛
            //如果复赛评分状态为未评分
            if (teamCompetition.getSecondRoundScoreStatus() == ScoreType.SCORE_NOT_HAS.getCode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否已晋级
     * @param date
     * @param teamCompetition
     * @param competition
     * @return
     */
    public boolean hasPromotion(Date date, TeamCompetition teamCompetition, Competition competition) {
        //初赛
        if(date.getTime() < competition.getSecondRoundStartTime().getTime()){
            if(teamCompetition.getPreliminariesStatus() == IndividualCompetition.PRELIMINARIES_IS_PASSED){
                return true;
            }
        }else if (date.getTime() < competition.getFinalsStartTime().getTime()) {//如果是复赛
            if (teamCompetition.getSecondRoundStatus() == IndividualCompetition.SECOND_ROUND_IS_PASSED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查询该竞赛决赛前10名
     * @return
     */
    public List<TeamCompetition> findTopTen(Long cid){
        return teamCompetitionDao.findTopTen(ScoreType.SCORE_IS_HAS.getCode(),cid);
    }

}
