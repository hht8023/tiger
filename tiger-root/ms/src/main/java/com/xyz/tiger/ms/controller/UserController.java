package com.xyz.tiger.ms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.tiger.ms.domain.User;
import com.xyz.tiger.ms.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController{
	
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
		return userService.findById(id);
	}
	
	/*@RequestMapping("/save")
	public JsonResult save(User user){
		LogUtil.info(user.getName()+"---"+user.getPassword());
		userService.insert(user);
		return new JsonResult(ResultCode.SUCCESS);
	}*/
}
