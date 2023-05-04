package com.yuanlrc.base.entity.admin;


import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.AccessoryType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author zhong
 * 团队附件表
 */
@Entity
@Table(name = "ylrc_team_accessory")
@EntityListeners(AuditingEntityListener.class)
public class TeamAccessory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="team_competition_id")
    private TeamCompetition teamCompetition;

    @ValidateEntity(required = false)
    @Column(name = "preliminaries_accessory")
    private String preliminariesAccessory;//初赛附件

    @ValidateEntity(required = false)
    @Column(name = "secondRound_accessory")
    private String secondRoundAccessory;//复赛附件


    @ValidateEntity(required = false)
    @Column(name = "finals_accessory")
    private String finalsAccessory;//决赛附件

    @Column(name = "secondRound_status")
    private Integer secondRoundStatus = AccessoryType.NOT_SUBMITTED.getCode(); //决赛附件状态

    @Column(name = "finals_status")
    private Integer finalsStatus = AccessoryType.NOT_SUBMITTED.getCode(); //决赛附件状态

    public TeamCompetition getTeamCompetition() {
        return teamCompetition;
    }

    public void setTeamCompetition(TeamCompetition teamCompetition) {
        this.teamCompetition = teamCompetition;
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
}
