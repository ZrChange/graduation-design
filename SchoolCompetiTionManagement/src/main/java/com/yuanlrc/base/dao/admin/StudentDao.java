package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author zhong
 * @date 2020-12-23
 */
@Repository
public interface StudentDao extends JpaRepository<Student, Long> {

    @Query("select s from Student s where s.id = :id")
    Student find(@Param("id") Long id);

    Student findByPhone(String phone);

    Student findByStudentNumber(String studentNumber);
}
