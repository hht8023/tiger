package com.xyz.tiger.running.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyz.tiger.dao.base.datasource.annotation.WriteDataSource;
import com.xyz.tiger.running.dao.UserMapper;
import com.xyz.tiger.running.domain.User;
import com.xyz.tiger.running.service.IUserService;
import com.xyz.tiger.service.impl.BaseService;
import com.xyz.tiger.utils.spirng.SpringContextHolder;

@Service
public class UserService extends BaseService<User, String> implements IUserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional
	public int insertUser(User user) {
		return userMapper.insert(user);// 该方法后，主键已经设置到user中了
	}

	@Override
	public User findById(String id) {
        User user = userMapper.findById(id);
        return user;
	}
	
	/**
	 * 写事务里面调用读
	 * @param u
	 */
	@Transactional
	@WriteDataSource
	public void wirteAndRead(User u){
		getService().insertUser(u);//这里走写库，那后面的读也都要走写库
		//这是刚刚插入的
		User uu = getService().findById(u.getId());
		System.out.println("==读写混合测试中的读(刚刚插入的)====id="+uu.getId()+",  user_name=" + uu.getUserName());
		//为了测试,3个库中id=1的user_name是不一样的
		User uuu = getService().findById("1");
		System.out.println("==读写混合测试中的读====id=1,  user_name=" + uuu.getUserName());
		
	}
	
	private UserService getService(){
		return SpringContextHolder.getBean(this.getClass());
	}
}
