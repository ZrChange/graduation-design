package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.IndividualCompetition;
import com.yuanlrc.base.entity.admin.TeamCompetition;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhong
 */
@Repository
public interface TeamCompetitionDao extends JpaRepository <TeamCompetition, Long>, JpaSpecificationExecutor<TeamCompetition> {

    @Query("select t from TeamCompetition t where t.id = :id")
    TeamCompetition find(@Param("id")Long id);


    //查询这个竞赛下所有通过的团队
    List<TeamCompetition> findByCompetitionIdAndStatus(Long competition, Integer status);

    /**
     * 根据竞赛id查询是否有团队已参加
     * @param cid
     * @return
     */
    List<TeamCompetition>findByCompetitionId(@Param("cid")Long cid);

    TeamCompetition findByCompetitionIdAndStudentIdAndStatusNot(Long competitionId, Long studentId, Integer status);

    /**
     * 决赛分数前10
     * @param cid
     * @return
     */
    @Query(value = "select * from ylrc_team_competition where competition_id = :cid and finals_score_status = :scoreType\n" +
            "ORDER BY finals_score DESC limit 10", nativeQuery = true)
    List<TeamCompetition> findTopTen(@Param("scoreType")int scoreType, @Param("cid")Long cid);

}
