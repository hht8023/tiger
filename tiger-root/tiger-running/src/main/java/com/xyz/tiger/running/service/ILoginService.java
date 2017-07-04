/**
* Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
*/
package com.xyz.tiger.running.service;

import javax.servlet.http.HttpServletResponse;

import com.xyz.tiger.running.domain.User;

/**
 * @Description: 此处填写类说明
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月30日
 */

public interface ILoginService {
	
	User validateUser(String userName,String pwd);
	
	String getSuccessLoginResult(User user, String appkey, HttpServletResponse response);
}
