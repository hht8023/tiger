package com.xyz.tiger.running.dao;

import org.apache.ibatis.annotations.Mapper;

import com.xyz.tiger.running.domain.User;


@Mapper
public interface UserMapper {

	public int insert(User user);

	public User findById(String id);
}
