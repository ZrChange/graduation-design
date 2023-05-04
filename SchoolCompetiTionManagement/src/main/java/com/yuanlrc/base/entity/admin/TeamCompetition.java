package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.ScoreType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * 团队竞赛
 * @Author zhong
 * @Date 2020-12-24
 */

@Table(name = "ylrc_team_competition")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TeamCompetition  extends BaseEntity{

    public static final Integer NOT_PASS = 0; //未通过
    public static final Integer PASS = 1; //已通过
    public static final Integer CHECK_CENTER = 2; //审批中
    public static final Integer NOT_CHECK_CENTER = 3; //未报名

    @Column(name = "name", nullable = false)
    @ValidateEntity(required = true)
    private String name; //团队名称

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student; //队长

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition; //竞赛

    @Column(name = "status")
    private Integer status = NOT_CHECK_CENTER;  //报名状态 未通过 已通过 审批中 未报名

    @Column(name = "preliminaries_status")
    private Integer preliminariesStatus = NOT_PASS;//初赛状态、默认未通过

    @Column(name = "second_round_status")
    private Integer secondRoundStatus = NOT_PASS;//复赛状态、默认未通过

    @ValidateEntity(requiredMaxValue=true,requiredMinValue = true,minValue = 0,maxValue = 100,errorMinValueMsg = "分数最低为0",errorMaxValueMsg="分数最高为100!")
    @Column(name = "preliminaries_score")
    private int preliminariesScore;//初赛分数

    @Column(name = "preliminaries_score_status")
    private int preliminariesScoreStatus = ScoreType.SCORE_NOT_HAS.getCode();//初赛评分状态,默认未评分

    @ValidateEntity(requiredMaxValue=true,requiredMinValue = true,minValue = 0,maxValue = 100,errorMinValueMsg = "分数最低为0",errorMaxValueMsg="分数最高为100!")
    @Column(name = "secondRound_score")
    private int secondRoundScore = 0;//复赛分数

    @Column(name = "secondRound_score_status")
    private int secondRoundScoreStatus = ScoreType.SCORE_NOT_HAS.getCode();//复赛评分状态,默认未评分

    @ValidateEntity(requiredMaxValue=true,requiredMinValue = true,minValue = 0,maxValue = 100,errorMinValueMsg = "分数最低为0",errorMaxValueMsg="分数最高为100!")
    @Column(name = "finals_score")
    private int finalsScore = 0;//决赛分数

    @Column(name = "finals_score_status")
    private int finalsScoreStatus = ScoreType.SCORE_NOT_HAS.getCode();//决赛评分状态,默认未评分

    @Transient
    private List<TeamMember> teamMembers;

    public void setPreliminariesStatus(Integer preliminariesStatus) {
        this.preliminariesStatus = preliminariesStatus;
    }

    public void setSecondRoundStatus(Integer secondRoundStatus) {
        this.secondRoundStatus = secondRoundStatus;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getPreliminariesStatus() {
        return preliminariesStatus;
    }

    public void setPreliminariesStatus(int preliminariesStatus) {
        this.preliminariesStatus = preliminariesStatus;
    }

    public int getSecondRoundStatus() {
        return secondRoundStatus;
    }

    public void setSecondRoundStatus(int secondRoundStatus) {
        this.secondRoundStatus = secondRoundStatus;
    }

    public int getPreliminariesScore() {
        return preliminariesScore;
    }

    public void setPreliminariesScore(int preliminariesScore) {
        this.preliminariesScore = preliminariesScore;
    }

    public int getPreliminariesScoreStatus() {
        return preliminariesScoreStatus;
    }

    public void setPreliminariesScoreStatus(int preliminariesScoreStatus) {
        this.preliminariesScoreStatus = preliminariesScoreStatus;
    }

    public int getSecondRoundScore() {
        return secondRoundScore;
    }

    public void setSecondRoundScore(int secondRoundScore) {
        this.secondRoundScore = secondRoundScore;
    }

    public int getSecondRoundScoreStatus() {
        return secondRoundScoreStatus;
    }

    public void setSecondRoundScoreStatus(int secondRoundScoreStatus) {
        this.secondRoundScoreStatus = secondRoundScoreStatus;
    }

    public int getFinalsScore() {
        return finalsScore;
    }

    public void setFinalsScore(int finalsScore) {
        this.finalsScore = finalsScore;
    }

    public int getFinalsScoreStatus() {
        return finalsScoreStatus;
    }

    public void setFinalsScoreStatus(int finalsScoreStatus) {
        this.finalsScoreStatus = finalsScoreStatus;
    }

    @Override
    public String toString() {
        return "TeamCompetition{" +
                "name='" + name + '\'' +
                ", student=" + student +
                ", competition=" + competition +
                ", status=" + status +
                ", preliminariesStatus=" + preliminariesStatus +
                ", secondRoundStatus=" + secondRoundStatus +
                ", preliminariesScore=" + preliminariesScore +
                ", preliminariesScoreStatus=" + preliminariesScoreStatus +
                ", secondRoundScore=" + secondRoundScore +
                ", secondRoundScoreStatus=" + secondRoundScoreStatus +
                ", finalsScore=" + finalsScore +
                ", finalsScoreStatus=" + finalsScoreStatus +
                ", teamMembers=" + teamMembers +
                '}';
    }
}