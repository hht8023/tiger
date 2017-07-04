package com.xyz.tiger.running.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.xyz.tiger.base.util.log.LogUtil;
import com.xyz.tiger.base.util.spring.SpringContextHolder;
import com.xyz.tiger.cache.redis.util.RedisDaoTemplate;
import com.xyz.tiger.cache.redis.util.callback.RedisCallback;
import com.xyz.tiger.dao.base.datasource.annotation.WriteDataSource;
import com.xyz.tiger.running.dao.UserMapper;
import com.xyz.tiger.running.domain.User;
import com.xyz.tiger.running.service.IUserService;
import com.xyz.tiger.service.impl.BaseService;

@Service
public class UserServiceImpl extends BaseService<User, String> implements IUserService {
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
    private RedisDaoTemplate redisDaoTemplate;
	
	@Override
	@Transactional
	public void insertUser(User user) {
		userMapper.insert(user);// 该方法后，主键已经设置到user中了
		try {
			redisDaoTemplate.execute(new RedisCallback<String>() {
				public String doInRedis(Jedis jedis) throws Exception {
					String key = User.class + user.getId();
					jedis.set(key, JSON.toJSONString(user));
					LogUtil.info("===将数据插入到redis缓存===");
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("将数据插入到redis缓存失败");
		}
	}

	@Override
	public User findById(String id) {	
        try {
			return redisDaoTemplate.execute(new RedisCallback<User>() {
				@Override
				public User doInRedis(Jedis jedis) throws Exception {
					String key = User.class + id;
					String userInfoJson = jedis.get(key);
					if (userInfoJson != null){
						return JSON.parseObject(userInfoJson, User.class);
					}else{
						User user = userMapper.findById(id);
						return user;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
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
	
	private UserServiceImpl getService(){
		return SpringContextHolder.getBean(this.getClass());
	}
}
