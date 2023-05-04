package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.AccessoryType;
import com.yuanlrc.base.bean.ScoreType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 个人竞赛附件实体
 */
@Entity
@Table(name="ylrc_individual_accessory")
@EntityListeners(AuditingEntityListener.class)
public class IndividualAccessory extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "individual_competition_id")
    private IndividualCompetition individualCompetition;//个人竞赛表

    @ValidateEntity(required = false)
    @Column(name = "preliminaries_accessory")
    private String preliminariesAccessory;//初赛附件

    @ValidateEntity(required = false)
    @Column(name = "secondRound_accessory")
    private String secondRoundAccessory;//复赛分数


    @ValidateEntity(required = false)
    @Column(name = "finals_accessory")
    private String finalsAccessory;//决赛分数

    @Column(name = "secondRound_status")
    private Integer secondRoundStatus = AccessoryType.NOT_SUBMITTED.getCode();

    @Column(name = "finals_status")
    private Integer finalsStatus = AccessoryType.NOT_SUBMITTED.getCode();

    public IndividualCompetition getIndividualCompetition() {
        return individualCompetition;
    }

    public void setIndividualCompetition(IndividualCompetition individualCompetition) {
        this.individualCompetition = individualCompetition;
    }

    public String getPreliminariesAccessory() {
        return preliminariesAccessory;
    }

    public void setPreliminariesAccessory(String preliminariesAccessory) {
        this.preliminariesAccessory = preliminariesAccessory;
    }

    public String getSecondRoundAccessory() {
        return secondRoundAccessory;
    }

    public void setSecondRoundAccessory(String secondRoundAccessory) {
        this.secondRoundAccessory = secondRoundAccessory;
    }

    public String getFinalsAccessory() {
        return finalsAccessory;
    }

    public void setFinalsAccessory(String finalsAccessory) {
        this.finalsAccessory = finalsAccessory;
    }

    public Integer getSecondRoundStatus() {
        return secondRoundStatus;
    }

    public void setSecondRoundStatus(Integer secondRoundStatus) {
        this.secondRoundStatus = secondRoundStatus;
    }

    public Integer getFinalsStatus() {
        return finalsStatus;
    }

    public void setFinalsStatus(Integer finalsStatus) {
        this.finalsStatus = finalsStatus;
    }

    @Override
    public String toString() {
        return "IndividualAccessory{" +
                "individualCompetition=" + individualCompetition +
                ", preliminariesAccessory='" + preliminariesAccessory + '\'' +
                ", secondRoundAccessory='" + secondRoundAccessory + '\'' +
                ", finalsAccessory='" + finalsAccessory + '\'' +
                ", secondRoundStatus=" + secondRoundStatus +
                ", finalsStatus=" + finalsStatus +
                '}';
    }
}
