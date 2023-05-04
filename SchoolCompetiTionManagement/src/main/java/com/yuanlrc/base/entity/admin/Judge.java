package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.entity.common.Sex;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * 评委实体类
 */
@Entity
@Table(name="ylrc_judge")
@EntityListeners(AuditingEntityListener.class)
public class Judge extends BaseEntity{

    public static final int JUDGE_STATUS_ENABLE = 1;//评委状态正常可用
    public static final int JUDGE_STATUS_UNABLE = 0;//评委状态不可用

    @ValidateEntity(required=false)
    @Column(name="head_pic",length=128)
    private String headPic;//评委头像

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=18,errorRequiredMsg="评委姓名不能为空!",
            errorMinLengthMsg="评委姓名长度需大于2!",errorMaxLengthMsg="评委姓名长度不能大于18!")
    @Column(name="name",nullable=false,length=18)
    private String name;//评委姓名

    @ManyToOne
    @JoinColumn(name="college_id")
    private College college;//评委所属学院

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;//评委所属角色

    @ValidateEntity(required=false)
    @Column(name="mobile",length=12)
    private String mobile;//评委手机号

    @ValidateEntity(required=true,requiredLeng=true,minLength=4,maxLength=32,
            errorRequiredMsg="密码不能为空！",errorMinLengthMsg="密码长度需大于4!",errorMaxLengthMsg="密码长度不能大于32!")
    @Column(name="password",nullable=false,length=32)
    private String password;//登录密码

    @ValidateEntity(required=false)
    @Column(name="status",length=1)
    private int status = JUDGE_STATUS_ENABLE;//评委状态,默认可用

    @ValidateEntity(required=false)
    @Column(name="sex",length=1)
    private int sex ;///评委性别

    @ManyToMany
    @Column(name="competition_id")
    private List<Competition> competitions;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    @Override
    public String toString() {
        return "Judge{" +
                "name='" + name + '\'' +
                ", college=" + college +
                ", role=" + role +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", sex=" + sex +
                ", competitions=" + competitions +
                '}';
    }
}
