package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.ScoreType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 个人竞赛表实体
 */
@Entity
@Table(name="ylrc_individual_competition")
@EntityListeners(AuditingEntityListener.class)
public class IndividualCompetition extends BaseEntity{

    public static final int IN_EXAMINATION_AND_APPROVAL  = 0;//审批中
    public static final int APPROVE_IS_PASSED = 1;//审批通过
    public static final int APPROVE_NOT_PASSED = 2;//审批未通过

    public static final int PRELIMINARIES_IS_PASSED = 1;//初赛通过
    public static final int PRELIMINARIES_NOT_PASSED = 0;//初赛未通过

    public static final int SECOND_ROUND_IS_PASSED = 1;//复赛通过
    public static final int SECOND_ROUND_NOT_PASSED = 0;//复赛未通过

    @JoinColumn(name = "student_id")
    @ManyToOne
    public Student student;//学生

    @JoinColumn(name = "competition_id")
    @ManyToOne
    public Competition competition;//竞赛

    @Column(name="apply_status")
    public int applyStatus = IN_EXAMINATION_AND_APPROVAL;//报名审批状态、默认审批中

    @Column(name="preliminaries_status")
    public int preliminariesStatus = PRELIMINARIES_NOT_PASSED;//初赛状态、默认未通过

    @Column(name="secondRound_status")
    public int secondRoundStatus = SECOND_ROUND_NOT_PASSED;//复赛状态、默认未通过

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


    public int getPreliminariesScore() {
        return preliminariesScore;
    }

    public void setPreliminariesScore(int preliminariesScore) {
        this.preliminariesScore = preliminariesScore;
    }

    public int getSecondRoundScore() {
        return secondRoundScore;
    }

    public void setSecondRoundScore(int secondRoundScore) {
        this.secondRoundScore = secondRoundScore;
    }

    public int getFinalsScore() {
        return finalsScore;
    }

    public void setFinalsScore(int finalsScore) {
        this.finalsScore = finalsScore;
    }

    public int getPreliminariesScoreStatus() {
        return preliminariesScoreStatus;
    }

    public void setPreliminariesScoreStatus(int preliminariesScoreStatus) {
        this.preliminariesScoreStatus = preliminariesScoreStatus;
    }

    public int getSecondRoundScoreStatus() {
        return secondRoundScoreStatus;
    }

    public void setSecondRoundScoreStatus(int secondRoundScoreStatus) {
        this.secondRoundScoreStatus = secondRoundScoreStatus;
    }

    public int getFinalsScoreStatus() {
        return finalsScoreStatus;
    }

    public void setFinalsScoreStatus(int finalsScoreStatus) {
        this.finalsScoreStatus = finalsScoreStatus;
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

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
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

    @Override
    public String toString() {
        return "IndividualCompetition{" +
                "student=" + student +
                ", competition=" + competition +
                ", applyStatus=" + applyStatus +
                ", preliminariesStatus=" + preliminariesStatus +
                ", secondRoundStatus=" + secondRoundStatus +
                ", preliminariesScore=" + preliminariesScore +
                ", preliminariesScoreStatus=" + preliminariesScoreStatus +
                ", secondRoundScore=" + secondRoundScore +
                ", secondRoundScoreStatus=" + secondRoundScoreStatus +
                ", finalsScore=" + finalsScore +
                ", finalsScoreStatus=" + finalsScoreStatus +
                '}';
    }
}
