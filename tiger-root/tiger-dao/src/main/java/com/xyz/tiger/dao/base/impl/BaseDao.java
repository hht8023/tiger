package com.xyz.tiger.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xyz.tiger.dao.base.IBaseDao;
import com.xyz.tiger.dao.base.annotation.Ignore;
import com.xyz.tiger.dao.base.annotation.PUBVALUE;
import com.xyz.tiger.dao.base.annotation.PrimaryKey;
import com.xyz.tiger.dao.base.annotation.Table;
import com.xyz.tiger.dao.base.annotation.TableColumn;
import com.xyz.tiger.dao.base.sql.SQLGenerator;
import com.xyz.tiger.dao.base.sql.conditions.Cnds;
import com.xyz.tiger.dao.base.util.ReflectionUtils;
import com.xyz.tiger.dao.base.util.UUIDGenerator;

@Repository
public class BaseDao<T extends Serializable, PK extends Serializable> implements IBaseDao<T, PK> {

<<<<<<< HEAD
=======
	@Override
	public int insert(T t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertOfBatch(List<T> tList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPk(T t, PK id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByparm(Cnds cnds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateByPK(T t, PK id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int updateByParams(Cnds cnds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T selectOne(T t, PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectOneM(T t, PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> selectList(Cnds cnds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectListM(Cnds cnds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count(Class<T> cl) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> selectPagination(Cnds cnds, int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectPaginationM(Cnds cnds, int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countByParams(Cnds cnds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> selectBySql(String sql) {
		// TODO Auto-generated method stub
		return null;
	}/*
>>>>>>> 3ac410474fc9bf4f6d22ce2d95a733bf756752cb

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public SqlSessionTemplate sqlSessionTemplateASS;

	private Class<T> entityClass;

	// 实体类主键名称
	private String pkName;
	// 主键生成规则
	private PUBVALUE.PkRule pkRule;
	// 数据库表名称
	private String tableName;
	*//**
	 * 作cache 结构{T类的镜像,{数据库列名,实体字段名}}
	 *//*
	private static final Map<Class<?>, Map<String, String>> classFieldMap = new HashMap<Class<?>, Map<String, String>>();
	//缓存主键生成规则
	private static final Map<String, PUBVALUE.PkRule> pkRuleMap = new HashMap<String, PUBVALUE.PkRule>();
	//缓存表名、主键字段名称
	private static final Map<String, String> conMap = new HashMap<String, String>();
	// 实体类的所有属性 实体类属性名称-数据库列名称
	private Map<String, String> currentFieldColumnNames;
	
	private SQLGenerator<T,PK> sqlGenerator;

	@SuppressWarnings("unchecked")
	private void init(Class<?> entityClass){
		this.entityClass = (Class<T>)entityClass;

		currentFieldColumnNames = classFieldMap.get(entityClass);
		if (null == currentFieldColumnNames) {
			currentFieldColumnNames = new LinkedHashMap<String, String>();
			Table table = this.entityClass.getAnnotation(Table.class);
	        if (null == table) { throw new RuntimeException("类-"
	                + this.entityClass + ",未用@Table注解标识!!"); }
	        conMap.put(entityClass+"_tableName", table.value());
			// 列cache
			Field[] fields = this.entityClass.getDeclaredFields();

			String fieldName = null;
			String columnName = null;
			for (Field field : fields) {
				if (field.isAnnotationPresent(Ignore.class) || 
						"serialVersionUID".equals(field.getName())) {
					continue;
				}
				fieldName = field.getName();
				TableColumn tableColumn = field.getAnnotation(TableColumn.class);
				if (null != tableColumn) {
					columnName = tableColumn.value();
				} else {
					columnName = null;
				}
				// 如果未标识特殊的列名，默认取字段名
				columnName = (StringUtils.isEmpty(columnName) ? ReflectionUtils.humpToLine(fieldName) : columnName);
				currentFieldColumnNames.put(fieldName,columnName);
				if (field.isAnnotationPresent(PrimaryKey.class)) {
					conMap.put(entityClass+"_pkName", fieldName);
					PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
					pkRuleMap.put(entityClass+"_pkRule", primaryKey.pkRule());
				}
			}
			classFieldMap.put(entityClass, currentFieldColumnNames);
		}
		tableName = conMap.get(entityClass+"_tableName");
		pkName = conMap.get(entityClass+"_pkName");
		pkRule = pkRuleMap.get(entityClass+"_pkRule");
		sqlGenerator = new SQLGenerator<T,PK>(currentFieldColumnNames, 
				tableName, 
				pkName);
	}
	
	@Override
	public int insert(T t) {
		init(t.getClass());
		Object pkValue = getPkValueByRule(pkRule, tableName);
		int rowNum = sqlSessionTemplateASS.insert("com.xyz.tiger.dao.base.IBaseDao.insert", 
				sqlGenerator.insert(t, pkValue));
		//ReflectionUtils.setFieldValue(t, pkName, pkValue);
		return rowNum;
	}

	@Override
	public int insertOfBatch(List<T> tList) {
		if(null == tList || tList.isEmpty()){
			return 0;
		}
		int len = tList.size(), i = 0;
		for(;i < len;i++){
			T t = tList.get(i);
			this.init(t.getClass());
			Object pkValue = getPkValueByRule(pkRule, tableName);
			sqlSessionTemplateASS.insert("com.xyz.tiger.dao.base.IBaseDao.insertOfBatch", 
					sqlGenerator.insert(t, pkValue));
		}
		return tList.size();
	}
	
	@Override
	public int deleteByPk(T t,PK id){
		init(t.getClass());
		return sqlSessionTemplateASS.delete("com.xyz.tiger.dao.base.IBaseDao.deleteById", 
				sqlGenerator.deleteByPk(id));
	}
	
	@Override
	public int deleteByparm(Cnds cnds){
		init(cnds.getDomain());
		return sqlSessionTemplateASS.delete("com.xyz.tiger.dao.base.IBaseDao.deleteByparm", 
				sqlGenerator.deleteByparm(cnds));
	}
	
	@Override
	public void updateByPK(T t,PK id) {
		init(t.getClass());
		sqlSessionTemplateASS.update("com.xyz.tiger.dao.base.IBaseDao.update", 
				sqlGenerator.updateByPK(t, id));
	}
	
	@Override
	public int updateByParams(Cnds cnds) {
		init(cnds.getDomain());
		try {
			return sqlSessionTemplateASS.update("com.xyz.tiger.dao.base.IBaseDao.updateByParams", 
					sqlGenerator.updateByParams(Class.forName(cnds.getDomain().getName()).newInstance(), cnds));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public T selectOne(T t,PK id){
		return handleResult(this.selectOneM(t, id), entityClass);
	}
	
	
	@Override
	public Map<String, Object> selectOneM(T t, PK id) {
		init(t.getClass());
		Map<String, Object> result = sqlSessionTemplateASS.selectOne("com.xyz.tiger.dao.base.IBaseDao.selectOne", 
				sqlGenerator.selectOne(id));
		return result;
	}

	@Override
	public List<T> selectList(Cnds cnds){
		List<Map<String, Object>> result = this.selectListM(cnds);
		List<T> resultList = new LinkedList<T>();
		for(Map<String,Object> map : result){
			resultList.add(handleResult(map, entityClass));
		}
		return resultList;
	}
	
	@Override
	public List<Map<String, Object>> selectListM(Cnds cnds) {
		init(cnds.getDomain());
		List<Map<String, Object>> result = sqlSessionTemplateASS.selectList("com.xyz.tiger.dao.base.IBaseDao.selectList", 
				sqlGenerator.selectList(cnds));
		return result;
	}

	
	@Override
	public long count(Class<T> cl) {
		init(cl);
		List<Map<String, Object>> result = sqlSessionTemplateASS.selectList("com.xyz.tiger.dao.base.IBaseDao.selectCount", 
				sqlGenerator.count());
		return result.size();
	}
	
	@Override
	public List<T> selectPagination(Cnds cnds, int offset, int rows) {
		List<Map<String, Object>> result = this.selectPaginationM(cnds, offset, rows);
		List<T> resultList = new LinkedList<T>();
		for(Map<String,Object> map : result){
			resultList.add(handleResult(map, entityClass));
		}
		return resultList;
	}
	
	@Override
	public List<Map<String, Object>> selectPaginationM(Cnds cnds, int offset, int rows) {
		init(cnds.getDomain());
		List<Map<String, Object>> result = sqlSessionTemplateASS.selectList("com.xyz.tiger.dao.base.IBaseDao.selectPagination", 
				sqlGenerator.selectPagination(cnds, offset, rows));
		return result;
	}
	
	@Override
	public long countByParams(Cnds cnds) {
		init(cnds.getDomain());
		return sqlSessionTemplateASS.selectOne("com.xyz.tiger.dao.base.IBaseDao.selectCount", 
				sqlGenerator.countByParams(cnds));
	}
	
	protected T handleResult(Map<String, Object> resultMap, Class<T> tClazz) {
        T t = null;
        try {
            t = tClazz.newInstance();
        } catch (InstantiationException e) {
            logger.error("/********************************");
            logger.error("封装查询结果时，实例化对象(" + this.entityClass + ")时，出现异常!"
                    + e.getMessage());
            logger.error("/********************************");
        } catch (IllegalAccessException e) {
            logger.error("/********************************");
            logger.error("封装查询结果时，实例化对象(" + this.entityClass + ")时，出现异常!"
                    + e.getMessage());
            logger.error("/********************************");
        }
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            String key = entry.getKey();
            if(key != null){
            	key = ReflectionUtils.lineToHump(key);
            	 Object val = entry.getValue();
            	 ReflectionUtils.invokeSetterMethod(t, key, val);
            }
        }
        return t;
    }
	
	*//**
	 * @Description 根据主键生成规则获取主键值
	 * @param rule
	 *            生成规则
	 * @return String 主键值
<<<<<<< HEAD
	 */
	private Object getPkValueByRule(PUBVALUE.PkRule rule, String tableName) {
=======
	 *//*
	private String getPkValueByRule(PUBVALUE.PkRule rule, String tableName) {
>>>>>>> 3ac410474fc9bf4f6d22ce2d95a733bf756752cb
		switch (rule) {
		//直接返回空 不对主键设置值  利用数据库自增主键 真的Mysql
		case INCREMENT:
			return PUBVALUE.PkRule.INCREMENT;
		case UUID:
			return UUIDGenerator.getUUID();
		case SEQUENCE:
			return "";
		default:
			return UUIDGenerator.getUUID();
		}
	}

	@Override
	public List<Map<String, Object>> selectBySql(String sql) {
		// TODO Auto-generated method stub
		return null;
	}
	 
*/
	
}
