package com.yuanlrc.base.entity.admin;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 团队申请表
 */
@Table(name = "ylrc_team_apply")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TeamApply extends BaseEntity {

    public static final Integer NOT_PASS = 0; //未通过
    public static final Integer PASS = 1; //已通过

    @JoinColumn(name = "student_id")
    @ManyToOne
    private Student student;

    @JoinColumn(name = "team_competition_id")
    @ManyToOne
    private TeamCompetition teamCompetition;

    @Column(name = "stauts")
    private Integer status = NOT_PASS;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TeamCompetition getTeamCompetition() {
        return teamCompetition;
    }

    public void setTeamCompetition(TeamCompetition teamCompetition) {
        this.teamCompetition = teamCompetition;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeamApply{" +
                "student=" + student +
                ", teamCompetition=" + teamCompetition +
                ", status=" + status +
                '}';
    }
}
