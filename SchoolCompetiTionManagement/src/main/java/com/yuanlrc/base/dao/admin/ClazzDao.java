package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Clazz;
import com.yuanlrc.base.entity.admin.Grade;
import com.yuanlrc.base.entity.admin.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 班级dao层
 */
@Repository
public interface ClazzDao extends JpaRepository<Clazz,Long> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select c from Clazz c where id = :id")
    Clazz find(@Param("id")Long id);

    /**
     * 根据名称 年级 班级查询
     * @param name
     * @param gradeId
     * @param professionalId
     * @return
     */
    Clazz findByNameAndGrade_IdAndProfessional_Id(String name, Long gradeId, Long professionalId);


    Clazz findByName(String name);

    /**
     * 根据专业ID查出所有的班级
     * @param professionalId
     * @return
     */
    List<Clazz> findByProfessionalId(Long professionalId);
}
