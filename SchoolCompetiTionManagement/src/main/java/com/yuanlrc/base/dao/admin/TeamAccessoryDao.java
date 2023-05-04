package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.TeamAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamAccessoryDao  extends JpaRepository<TeamAccessory, Long> {

    @Query("select t from TeamAccessory  t where t.id = :id")
    TeamAccessory find(@Param("id")Long id);

    TeamAccessory findByTeamCompetitionId(Long id);
}
