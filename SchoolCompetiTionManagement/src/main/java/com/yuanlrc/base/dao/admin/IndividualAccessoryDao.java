package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.IndividualAccessory;
import com.yuanlrc.base.entity.admin.IndividualCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 个人竞赛附件Dao层
 */
@Repository
public interface IndividualAccessoryDao extends JpaRepository<IndividualAccessory,Long> {

    /**
     * 根据id查询个人竞赛附件
     * @param id
     * @return
     */
    @Query("select i from IndividualAccessory i where id = :id")
    IndividualAccessory find(@Param("id") Long id);

    /**
     * 根据个人竞赛表id查询
     * @param individualCompetitionId
     * @return
     */
    IndividualAccessory findByIndividualCompetition_Id(Long individualCompetitionId);

}
