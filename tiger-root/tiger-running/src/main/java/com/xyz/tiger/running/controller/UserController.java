package com.xyz.tiger.running.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.tiger.controller.BaseController;
import com.xyz.tiger.running.domain.User;
import com.xyz.tiger.running.service.IUserService;
import com.xyz.tiger.util.JsonResult;
import com.xyz.tiger.util.ResultCode;
import com.xyz.tiger.utils.log.LogUtil;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, String>{
	
	@Autowired
    private IUserService userService;
	
	@RequestMapping(value = "/index")
	public Map<String, Object> Index() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", "123");
		return map;
	}
	
	@RequestMapping("/selectOne/{id}")
	public User selectOne(@PathVariable String id){
		return getBaseService().selectOne(new User(), id);
	}
	
	@RequestMapping("/save")
	public JsonResult save(User user){
		LogUtil.info(user.getUserName()+"---"+user.getPassword());
		getBaseService().insert(user);
		return new JsonResult(ResultCode.SUCCESS,"",user);
	}
	
	@RequestMapping("/mysave")
	public JsonResult mysave(User user){
		LogUtil.info(user.getUserName()+"---"+user.getPassword());
		this.userService.insertUser(user);
		return new JsonResult(ResultCode.SUCCESS,"",user);
	}
	
	@RequestMapping("/wirteAndRead")
	public JsonResult wirteAndRead(User user){
		LogUtil.info(user.getUserName()+"---"+user.getPassword());
		userService.wirteAndRead(user);
		return new JsonResult(ResultCode.SUCCESS);
	}
}
