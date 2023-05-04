package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.CompetitionProcess;
import com.yuanlrc.base.bean.CompetitionStatus;
import com.yuanlrc.base.bean.CompetitionType;
import com.yuanlrc.base.bean.RaceType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 竞赛实体
 */
@Entity
@Table(name = "ylrc_competition")
@EntityListeners(AuditingEntityListener.class)
public class Competition extends BaseEntity {

    @ValidateEntity(required = true, requiredLeng = true, minLength = 4, maxLength = 18, errorRequiredMsg = "竞赛标题不能为空!", errorMinLengthMsg = "竞赛标题长度不能小于4!", errorMaxLengthMsg = "竞赛标题长度不能大于18!")
    @Column(name = "title", nullable = false, length = 32)
    private String title;//标题

    @Column(name = "competition_type", nullable = false, length = 2)
    private int competitionType=CompetitionType.PERSONAL.getCode(); //参赛类型

    @Column(name = "raceType", nullable = false, length = 2)
    private int raceType=RaceType.OPENNESS.getCode();//竞赛类型

    @ManyToOne
    @JoinColumn(name="college_id",referencedColumnName="id")
    private College college;//学院

    @ManyToOne
    @JoinColumn(name="professional_id",referencedColumnName="id")
    private Professional professional;//专业

    @ManyToMany
    @Column(name="grades")
    private List<Grade> grades;//年级list
    
    @ManyToMany
    @Column(name="judges")
    private List<Judge> judges;//评委list

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "enroll_start_time", nullable = false)
    private Date enrollStartTime;//报名开始时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "enroll_end_time", nullable = false)
    private Date enrollEndTime;//报名结束时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "preliminaries_start_time", nullable = false)
    private Date preliminariesStartTime;//初赛开始时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "preliminaries_end_time", nullable = false)
    private Date preliminariesEndTime;//初赛结束时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "second_round_start_time", nullable = false)
    private Date secondRoundStartTime;//复赛开始时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "second_round_end_time", nullable = false)
    private Date secondRoundEndTime;//复赛结束时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "finals_start_time", nullable = false)
    private Date finalsStartTime;//决赛开始时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "finals_end_time", nullable = false)
    private Date finalsEndTime;//决赛结束时间

    @Column(name = "competition_status", nullable = false, length = 2)
    private int competitionStatus=CompetitionProcess.SIGNUP.getCode();//竞赛状态

    @Column(name = "enrollment_number", nullable = false, length = 5)
    private int enrollmentNumber;//可报名人数或团队

    @Column(name = "team_size", nullable = false, length = 3)
    private int teamSize;//团队人数

    @Column(name = "signed_up",nullable = false,length = 3)
    private int signedUp;//已报名人数

    @ValidateEntity(required = true,minLength = 20,errorRequiredMsg = "竞赛介绍不能为空",errorMinLengthMsg = "竞赛介绍至少为20个字")
    @Column(name = "detailed_introduction", nullable = false, length = 500)
    private String detailedIntroduction;//竞赛介绍

    @Column(name = "status",nullable = false,length = 3)
    private int status= CompetitionStatus.NOT_OVERDUE.getCode();//竞赛开放状态

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(int competitionType) {
        this.competitionType = competitionType;
    }

    public int getRaceType() {
        return raceType;
    }

    public void setRaceType(int raceType) {
        this.raceType = raceType;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Judge> getJudges() {
        return judges;
    }

    public void setJudges(List<Judge> judges) {
        this.judges = judges;
    }

    public Date getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(Date enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public Date getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(Date enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public Date getPreliminariesStartTime() {
        return preliminariesStartTime;
    }

    public void setPreliminariesStartTime(Date preliminariesStartTime) {
        this.preliminariesStartTime = preliminariesStartTime;
    }

    public Date getPreliminariesEndTime() {
        return preliminariesEndTime;
    }

    public void setPreliminariesEndTime(Date preliminariesEndTime) {
        this.preliminariesEndTime = preliminariesEndTime;
    }

    public Date getSecondRoundStartTime() {
        return secondRoundStartTime;
    }

    public void setSecondRoundStartTime(Date secondRoundStartTime) {
        this.secondRoundStartTime = secondRoundStartTime;
    }

    public Date getSecondRoundEndTime() {
        return secondRoundEndTime;
    }

    public void setSecondRoundEndTime(Date secondRoundEndTime) {
        this.secondRoundEndTime = secondRoundEndTime;
    }

    public Date getFinalsStartTime() {
        return finalsStartTime;
    }

    public void setFinalsStartTime(Date finalsStartTime) {
        this.finalsStartTime = finalsStartTime;
    }

    public Date getFinalsEndTime() {
        return finalsEndTime;
    }

    public void setFinalsEndTime(Date finalsEndTime) {
        this.finalsEndTime = finalsEndTime;
    }

    public int getCompetitionStatus() {
        return competitionStatus;
    }

    public void setCompetitionStatus(int competitionStatus) {
        this.competitionStatus = competitionStatus;
    }

    public int getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(int enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public String getDetailedIntroduction() {
        return detailedIntroduction;
    }

    public void setDetailedIntroduction(String detailedIntroduction) {
        this.detailedIntroduction = detailedIntroduction;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public int getSignedUp() {
        return signedUp;
    }

    public void setSignedUp(int signedUp) {
        this.signedUp = signedUp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "title='" + title + '\'' +
                ", competitionType=" + competitionType +
                ", raceType=" + raceType +
                ", college=" + college +
                ", professional=" + professional +
                ", enrollStartTime=" + enrollStartTime +
                ", enrollEndTime=" + enrollEndTime +
                ", preliminariesStartTime=" + preliminariesStartTime +
                ", preliminariesEndTime=" + preliminariesEndTime +
                ", secondRoundStartTime=" + secondRoundStartTime +
                ", secondRoundEndTime=" + secondRoundEndTime +
                ", finalsStartTime=" + finalsStartTime +
                ", finalsEndTime=" + finalsEndTime +
                ", competitionStatus=" + competitionStatus +
                ", enrollmentNumber=" + enrollmentNumber +
                ", teamSize=" + teamSize +
                ", detailedIntroduction='" + detailedIntroduction + '\'' +
                '}';
    }
}
