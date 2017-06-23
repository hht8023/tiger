package com.xyz.tiger.ms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyz.tiger.ms.database.ReadOnlyConnection;
import com.xyz.tiger.ms.domain.User;
import com.xyz.tiger.ms.mapper.UserMapper;
import com.xyz.tiger.ms.service.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserMapper userMapper;

	
	@Override
	public boolean addUser(String username, String password) {
		return userMapper.insertUser(username, password) == 1 ? true : false;
	}

	@Override
	public User addUserWithBackId(String username, String password) {
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		userMapper.insertUserWithBackId(user);// 该方法后，主键已经设置到user中了
		return user;
	}

	@Override
	@ReadOnlyConnection
	@Transactional
	public User findById(String id) {
		System.out.println("数据源"+userMapper);
        User user = userMapper.findById(id);
        System.out.println("从DB中获取了信息并插入缓存"+user.getName());
        return user;
	}
}
