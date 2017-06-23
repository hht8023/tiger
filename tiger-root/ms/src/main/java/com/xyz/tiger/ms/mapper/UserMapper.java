package com.xyz.tiger.ms.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xyz.tiger.ms.domain.User;

public interface UserMapper {

	public int insertUser(@Param("username") String username, @Param("password") String password);

	/**
	 * 插入用户，并将主键设置到user中 注意：返回的是数据库影响条数，即1
	 */
	public int insertUserWithBackId(User user);
	
	@Select("SELECT * FROM t_user WHERE id = #{id}")
	public User findById(String id);
}
