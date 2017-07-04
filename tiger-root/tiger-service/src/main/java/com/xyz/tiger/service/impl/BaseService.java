package com.xyz.tiger.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyz.tiger.base.util.log.LogUtil;
import com.xyz.tiger.dao.base.IBaseDao;
import com.xyz.tiger.dao.base.sql.conditions.Cnds;
import com.xyz.tiger.dao.base.util.page.Pagination;
import com.xyz.tiger.service.IBaseService;

/**
 * 业务层基础接口实现
 * 
 * @author Hanht
 * @date 2016年8月10日 下午3:21:09
 */
@Service
public class BaseService<T extends Serializable, PK extends Serializable> implements IBaseService<T, PK> {

	@Resource
	private IBaseDao<T, PK> baseDao;

	@Override
	@Transactional
	public int insert(T po) {
		return baseDao.insert(po);
	}

	@Override
	@Transactional
	public int insertOfBatch(List<T> tList) {
		return baseDao.insertOfBatch(tList);
	}

	@Override
	@Transactional
	public int deleteByPk(T t, PK id) {
		return baseDao.deleteByPk(t, id);
	}

	@Override
	@Transactional
	public int deleteByparm(Cnds cnds) {
		return baseDao.deleteByparm(cnds);
	}

	@Override
	public void updateByPK(T t, PK id) {
		baseDao.updateByPK(t, id);
	}

	@Override
	public int updateByParams(Cnds cnds) {
		return baseDao.updateByParams(cnds);
	}

	@Override
	public T selectOne(T t, PK id) {
		LogUtil.info(" 我是父类的 selectOne");
		return baseDao.selectOne(t, id);
	}
	
	@Override
	public Map<String, Object> selectOneM(T t, PK id) {
		return baseDao.selectOneM(t, id);
	}

	@Override
	public T selectOne(Cnds cnds) {
		return baseDao.selectList(cnds).get(0);
	}

	@Override
	public Map<String, Object> selectOneM(Cnds cnds) {
		return baseDao.selectListM(cnds).get(0);
	}

	@Override
	public List<T> selectList(Cnds cnds) {
		return baseDao.selectList(cnds);
	}

	@Override
	public List<Map<String, Object>> selectListM(Cnds cnds) {
		return baseDao.selectListM(cnds);
	}
	
	@Override
	public Pagination selectPagination(Cnds cnds, int page, int rows) {
		long records = baseDao.countByParams(cnds);
		int offset = (page - 1) * rows;
		List<T> list = baseDao.selectPagination(cnds, offset, rows);
		return new Pagination(page, rows, records, list);
	}

	@Override
	public Pagination selectPaginationM(Cnds cnds, int page, int rows) {
		long records = baseDao.countByParams(cnds);
		int offset = (page - 1) * rows;
		List<Map<String, Object>> list = baseDao.selectPaginationM(cnds, offset, rows);
		return new Pagination(page, rows, records, list);
	}
	
	@Override
	public List<Map<String, Object>> selectBySql(String sql) {
		return baseDao.selectBySql(sql);
	}

}
