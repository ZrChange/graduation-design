package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Clazz;
import com.yuanlrc.base.entity.admin.Competition;
import com.yuanlrc.base.entity.admin.Grade;
import com.yuanlrc.base.entity.admin.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 竞赛Dao层
 */
@Repository
public interface CompetitionDao extends JpaRepository<Competition, Long>,JpaSpecificationExecutor<Competition> {

    /**
     * 根据id查询竞赛
     *
     * @param id
     * @return
     */
    @Query("select c from Competition c where id = :id")
    Competition find(@Param("id") Long id);

}
