package com.yuanlrc.base.entity.admin;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 专业实体类
 */
@Entity
@Table(name="ylrc_professional")
@EntityListeners(AuditingEntityListener.class)
public class Professional extends BaseEntity {

    @ValidateEntity(required=true,requiredLeng=true,minLength=4,maxLength=18,errorRequiredMsg="专业名称不能为空!",errorMinLengthMsg="专业名称长度需大于4!",errorMaxLengthMsg="专业名称长度不能大于18!")
    @Column(name="name",nullable=false,length=18,unique=true)
    private String name;//专业名称

    @ManyToOne
    @JoinColumn(name="college_id")
    private College college;//专业所属学院

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @Override
    public String toString() {
        return "Professional{" +
                "name='" + name + '\'' +
                ", college=" + college +
                '}';
    }

}
