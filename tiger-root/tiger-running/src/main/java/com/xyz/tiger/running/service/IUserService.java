package com.xyz.tiger.running.service;

import com.xyz.tiger.running.domain.User;
import com.xyz.tiger.service.IBaseService;

public interface IUserService extends IBaseService<User, String>{

	int insertUser(User user);
	
	User findById(String id);
	
	void wirteAndRead(User user);
}
