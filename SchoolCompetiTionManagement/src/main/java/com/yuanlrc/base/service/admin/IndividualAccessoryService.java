package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.dao.admin.IndividualAccessoryDao;
import com.yuanlrc.base.entity.admin.IndividualAccessory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人竞赛附件表
 */
@Service
public class IndividualAccessoryService {

    @Autowired
    private IndividualAccessoryDao individualAccessoryDao;

    /**
     * 根据个人竞赛表id查询
     * @param individualCompetitionId
     * @return
     */
    public IndividualAccessory findByIndividualCompetitionId(Long individualCompetitionId) {
        return individualAccessoryDao.findByIndividualCompetition_Id(individualCompetitionId);
    }

    /**
     * 保存
     * @param individualAccessory
     * @return
     */
    public IndividualAccessory save(IndividualAccessory individualAccessory){
        return individualAccessoryDao.save(individualAccessory);
    }

    /**
     * 查询全部
     * @return
     */
    public List<IndividualAccessory> findAll(){
        return individualAccessoryDao.findAll();
    }

}
