package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * 年级实体类
 */
@Entity
@Table(name = "ylrc_grade")
@EntityListeners(AuditingEntityListener.class)
public class Grade extends BaseEntity {

    @ValidateEntity(required = true, requiredLeng = true, minLength = 4, maxLength = 18, errorRequiredMsg = "年级名称不能为空!", errorMinLengthMsg = "年级名称长度需大于4!", errorMaxLengthMsg = "年级名称长度不能大于18!")
    @Column(name = "name", nullable = false, length = 18, unique = true)

    private String name;//年级名称

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name="competition_id")
    private List<Competition> competitions;//竞赛

    public List<Competition> getCompetitions() {
        return competitions;
    }
    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "name='" + name + '\'' +
                '}';
    }
}
