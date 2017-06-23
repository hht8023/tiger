package com.xyz.tiger.ms.service;

import com.xyz.tiger.ms.domain.User;

public interface IUserService {

	boolean addUser(String username, String password);
	
	User addUserWithBackId(String username, String password);
    
	User findById(String id);
}
