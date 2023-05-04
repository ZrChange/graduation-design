package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Competition;
import com.yuanlrc.base.entity.admin.IndividualCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

/**
 * 个人竞赛信息Dao层
 */
@Repository
public interface IndividualCompetitionDao extends JpaRepository<IndividualCompetition,Long>, JpaSpecificationExecutor<IndividualCompetition> {

    /**
     * 根据id查询个人竞赛信息
     * @param id
     * @return
     */
    @Query("select i from IndividualCompetition i where id = :id")
    IndividualCompetition find(@Param("id") Long id);

    List<IndividualCompetition> findByCompetition_IdAndApplyStatus(Long competitionId,int applyStatus);

    /**
     * 查询是否进行了报名且未被拒绝
     * @param studentId
     * @param competitionId
     * @param applyStatus
     * @return
     */
    List<IndividualCompetition> findByStudent_IdAndCompetition_IdAndApplyStatusNot(Long studentId,Long competitionId,int applyStatus);


    /**
     * 根据竞赛id查询该竞赛是否有人参加
     * @param cid
     * @return
     */
    List<IndividualCompetition> findByCompetitionId(@Param("cid")Long cid);

    /**
     * 决赛分数前10
     * @param cid
     * @return
     */
    @Query(value = "select * from ylrc_individual_competition where competition_id = :cid and finals_score_status = :scoreType\n" +
            "ORDER BY finals_score DESC limit 10", nativeQuery = true)
    List<IndividualCompetition> findTopTen(@Param("scoreType")int scoreType,@Param("cid")Long cid);
}
