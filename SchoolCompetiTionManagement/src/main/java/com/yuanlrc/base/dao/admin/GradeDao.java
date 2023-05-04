package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author zhong
 * @Date 2020-12-23
 */
@Repository
public interface GradeDao extends JpaRepository<Grade, Long> {

    @Query("select g from Grade  g where g.id = :id")
    Grade find(@Param("id")Long id);

    Grade findByName(String name);
}
