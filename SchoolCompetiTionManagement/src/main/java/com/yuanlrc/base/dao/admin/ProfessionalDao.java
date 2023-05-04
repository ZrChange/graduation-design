package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zhong
 * @Date 2020-12-23
 */
@Repository
public interface ProfessionalDao extends JpaRepository<Professional, Long> {

    @Query("select p from Professional p where  p.id = :id")
    Professional find(@Param("id")Long id);

    Professional findByName(String name);



    List<Professional> findByCollegeId(Long id);
}
