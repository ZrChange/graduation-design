package com.yuanlrc.base.dao.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yuanlrc.base.entity.admin.User;

/**
 * 用户数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface UserDao extends JpaRepository<User, Long>{
	
	/**
	 * 按照用户名查找用户信息
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);

	/**
	 * 根据用户id查询
	 * @param id
	 * @return
	 */
	@Query("select u from User u where id = :id")
	public User find(@Param("id")Long id);

	/**
	 * 根据手机号查询用户
	 * @param mobile
	 * @return
	 */
	public User findByMobile(@Param("mobile")String mobile);
}
