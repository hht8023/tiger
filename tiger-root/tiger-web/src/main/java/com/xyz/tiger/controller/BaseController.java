package com.xyz.tiger.controller;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyz.tiger.dao.base.util.GenericsUtils;
import com.xyz.tiger.service.IBaseService;
import com.xyz.tiger.utils.date.DateEditor;

/**
 * @Description 控制层基类
 * @author Hanht
 * @date 2016年8月10日 下午4:07:05
 */
public abstract class BaseController<T extends Serializable, PK extends Serializable> {

	/**
	 * ThreadLocal确保高并发下每个请求的request，response都是独立的
	 */
	private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<ServletRequest>();
	private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<ServletResponse>();

	@Resource
	IBaseService<T, PK> baseService;

	private Class<T> entityClass;
	private T po;

	@SuppressWarnings("unchecked")
	public BaseController() {
		this.entityClass = (Class<T>) GenericsUtils.getSuperClassGenricType(this.getClass());
		try {
			po = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@ModelAttribute
	public void init(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		currentRequest.set(request);
		currentResponse.set(response);
		String base = request.getContextPath();
		String fullPath = request.getScheme() + "://" + request.getServerName() + base;
		model.addAttribute("base", base);
		model.addAttribute("fullPath", fullPath);
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	public T load(PK id) {
		T t = baseService.selectOne(po, id);
		request().setAttribute("M", t);
		return t;
	}
	
	public IBaseService<T, PK> getBaseService() {
		return this.baseService;
	}
	
	public HttpServletRequest request() {
		return (HttpServletRequest) currentRequest.get();
	}

	public HttpServletResponse response() {
		return (HttpServletResponse) currentResponse.get();
	}
}
