package com.xyz.tiger.base.util;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Description: api工具类
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月29日
 */
public class ApiTools {

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if(ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(44));
        }
        return ip;
    }

    public static String getRandomUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    public static long getTimeStamp(){
        return System.currentTimeMillis()/1000;
    }
}
