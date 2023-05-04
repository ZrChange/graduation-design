package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.TeamApply;
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
public interface TeamApplyDao extends JpaRepository<TeamApply, Long>, JpaSpecificationExecutor<TeamApply> {

    @Query("select t from TeamApply t where t.id = :id")
    TeamApply find(@Param("id")Long id);

    List<TeamApply> findByTeamCompetitionId(Long id);
}
