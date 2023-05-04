package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 班级实体类
 */
@Entity
@Table(name="ylrc_clazz")
@EntityListeners(AuditingEntityListener.class)
public class Clazz extends BaseEntity{

    @ValidateEntity(required=true,requiredLeng=true,minLength=4,maxLength=18,errorRequiredMsg="班级名称不能为空!",errorMinLengthMsg="班级名称长度需大于4!",errorMaxLengthMsg="班级名称长度不能大于18!")
    @Column(name="name",nullable=false,length=18)
    private String name;//班级名称

    @ManyToOne
    @JoinColumn(name="grade_id")
    private Grade grade;//班级所属年级

    @ManyToOne
    @JoinColumn(name="professional_id")
    private Professional professional;//班级所属专业

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                ", professional=" + professional +
                '}';
    }
}
