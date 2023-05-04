package com.yuanlrc.base.entity.admin;


import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.entity.common.Sex;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author zhong
 * @date 2020-12-23
 */
@Entity
@Table(name="ylrc_student")
@EntityListeners(AuditingEntityListener.class)
public class Student extends BaseEntity {

    public static final Integer NORMAL = 0;//不可用

    public static final Integer ACTIVE = 1;

    @Column(name = "student_number", unique = true)
    @ValidateEntity(required = true,errorRequiredMsg = "请输入学号", minLength = 6, maxLength = 25, requiredLeng = true,
            errorMinLengthMsg = "学号必须6~25位以内", errorMaxLengthMsg = "学号必须6~25位以内")
    private String studentNumber;

    @Column(name="name",nullable=false,length=18)
    @ValidateEntity(required = true, requiredLeng = true, minLength = 2, maxLength = 18,
            errorRequiredMsg = "请输入姓名", errorMinLengthMsg = "姓名必须在2~18之间", errorMaxLengthMsg ="姓名必须在2~18之间" )
    private String name;

    @Column(name = "sex")
    @ValidateEntity(required = false)
    private Integer sex = Sex.UNKNOWN.getCode();

    @Column(name = "age")
    @ValidateEntity(required = false)
    private Integer age;

    @JoinColumn(name = "clazz_id")
    @ManyToOne
    private Clazz clazz;

    @Column(name = "phone")
    @ValidateEntity(required = false)
    private String phone;

    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;

    @Column(name = "password")
    @ValidateEntity(required=true,requiredLeng=true,minLength=4,maxLength=32,
            errorRequiredMsg="密码不能为空！",errorMinLengthMsg="密码长度需大于4!",errorMaxLengthMsg="密码长度不能大于32!")
    private String password;

    @Column(name = "status")
    @ValidateEntity(required = false)
    private Integer status = ACTIVE;

    @Column(name = "head_pic")
    @ValidateEntity(required = false)
    private String headPic;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNumber='" + studentNumber + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", clazz=" + clazz +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", headPic='" + headPic + '\'' +
                '}';
    }
}
