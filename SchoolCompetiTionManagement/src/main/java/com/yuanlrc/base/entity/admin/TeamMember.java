package com.yuanlrc.base.entity.admin;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

/**
 * 团队成员表
 * @author zhong
 * @date 2020-12-24
 */

@Entity
@Table(name="ylrc_team_member")
@EntityListeners(AuditingEntityListener.class)
public class TeamMember extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="team_competition_id")
    private TeamCompetition teamCompetition;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    //是否是队长 0 不是 1 是
    @Transient
    private Integer isCaptain = 0;

    public TeamCompetition getTeamCompetition() {
        return teamCompetition;
    }

    public void setTeamCompetition(TeamCompetition teamCompetition) {
        this.teamCompetition = teamCompetition;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(Integer isCaptain) {
        this.isCaptain = isCaptain;
    }

    @Override
    public String toString() {
        return "TeamMember{" +
                "teamCompetition=" + teamCompetition +
                ", student=" + student +
                ", isCaptain=" + isCaptain +
                '}';
    }
}
