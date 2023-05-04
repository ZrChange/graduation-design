package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.TeamAccessoryDao;
import com.yuanlrc.base.entity.admin.Student;
import com.yuanlrc.base.entity.admin.TeamAccessory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamAccessoryService {

    @Autowired
    private TeamAccessoryDao teamAccessoryDao;

    public TeamAccessory find(Long id)
    {
        return teamAccessoryDao.find(id);
    }

    public TeamAccessory save(TeamAccessory teamAccessory)
    {
        return teamAccessoryDao.save(teamAccessory);
    }

    public void delete(Long id)
    {
        teamAccessoryDao.deleteById(id);
    }

    public List<TeamAccessory> findAll()
    {
        return teamAccessoryDao.findAll();
    }


    public TeamAccessory findByTeamCompetitionId(Long id)
    {
        return teamAccessoryDao.findByTeamCompetitionId(id);
    }

    public PageBean<TeamAccessory> findList(TeamAccessory student, PageBean<TeamAccessory> pageBean)
    {

        ExampleMatcher withMatcher = ExampleMatcher.matching()
                .withIgnorePaths("status");

        Example<TeamAccessory> example = Example.of(student, withMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize());
        Page<TeamAccessory> findAll = teamAccessoryDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }
}
