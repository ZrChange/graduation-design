package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.TeamMember;
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
public interface TeamMemberDao extends JpaRepository<TeamMember, Long>, JpaSpecificationExecutor<TeamMember> {

    @Query("select t from TeamMember t where t.id = :id")
    TeamMember find(@Param("id")Long id);


    List<TeamMember> findByTeamCompetitionId(Long id);


    TeamMember findByTeamCompetitionIdAndStudentId(Long teamCompetitionId, Long studentId);


    /**
     * 根据学生ID和赛项ID查出这个学生有没有参加过这个比赛
     * @param competitionId
     * @param studentId
     * @return
     */
    @Query(value = "SELECT count(0) 'count' FROM `ylrc_team_competition` tm, ylrc_team_member t WHERE tm.id = t.team_competition_id\n" +
            "and tm.competition_id = :competitionId and tm.`status` != 0 and t.student_id = :studentId", nativeQuery = true)
    Integer findByTeamCompetitionCompetitionAndStudentIdAndStatus(@Param("competitionId") Long competitionId,@Param("studentId") Long studentId);
}
