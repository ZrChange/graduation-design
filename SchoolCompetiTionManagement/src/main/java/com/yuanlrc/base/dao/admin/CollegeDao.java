package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.College;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 学院dao层
 */
@Repository
public interface CollegeDao extends JpaRepository<College,Long> {

    /**
     * 根据名称查询学院
     * @param name
     * @return
     */
    public College findByName(String name);

    /**
     * 根据id查询学院
     * @param id
     * @return
     */
    @Query("select c from College c where id = :id")
    public College find(@Param("id") Long id);

}
