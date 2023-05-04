package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.College;
import com.yuanlrc.base.entity.admin.Judge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评委Dao层
 */
@Repository
public interface JudgeDao extends JpaRepository<Judge, Long> {

    /**
     * 根据学院id查询评委
     *
     * @param collegeId
     * @return
     */
    List<Judge> findByCollege_Id(@Param("collegeId") Long collegeId);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Query("select j from Judge j where id = :id")
    Judge find(@Param("id") Long id);

    /**
     * 根据手机号查询评委
     *
     * @param mobile
     * @return
     */
    public Judge findByMobile(String mobile);

}
