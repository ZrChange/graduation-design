package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 学院实体类
 */
@Entity
@Table(name="ylrc_college")
@EntityListeners(AuditingEntityListener.class)
public class College extends BaseEntity{

    @ValidateEntity(required=true,requiredLeng=true,minLength=4,maxLength=18,errorRequiredMsg="学院名称不能为空!",errorMinLengthMsg="学院名称长度需大于4!",errorMaxLengthMsg="学院名称长度不能大于18!")
    @Column(name="name",nullable=false,length=18,unique=true)
    private String name;//学院名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "College{" +
                "name='" + name + '\'' +
                '}';
    }
}
