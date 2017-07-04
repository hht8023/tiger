/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xyz.tiger.base.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xyz.tiger.base.common.constant.GlobalConstant;

/**
 * @Description: HttpServletResponse帮助类
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年7月4日
 */

public final class ResponseUtils {

	public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);  
	
	private ResponseUtils() {
	}

	/** 
     * 发送内容。使用UTF-8编码。 
     *  
     * @param response 
     * @param contentType 
     * @param text 
     */  
    public static void render(HttpServletResponse response, String contentType,String text) {  
    	response.setCharacterEncoding(GlobalConstant.DEFAULT_CHARSET);
        response.setContentType(contentType);  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        PrintWriter pWriter = null;  
        try {  
            pWriter = response.getWriter();  
            pWriter.write(text);  
            pWriter.flush();  
        } catch (IOException e) {  
            log.error(e.getMessage(), e);  
        }finally{  
            IOUtils.closeQuietly(pWriter);  
        }  
    }  

	public static void renderJSON(HttpServletResponse response, String text) {
		render(response, GlobalConstant.JSON_CONTENT_TYPE_VALUE, text);
	}

	public static void renderXml(HttpServletResponse response, String text) {
		render(response, GlobalConstant.XML_CONTENT_TYPE_VALUE, text);
	}

	public static void renderText(HttpServletResponse response, String text) {
		render(response, GlobalConstant.TEXT_CONTENT_TYPE_VALUE, text);
	}
}
