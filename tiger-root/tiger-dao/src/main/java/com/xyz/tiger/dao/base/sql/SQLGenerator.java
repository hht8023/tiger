package com.xyz.tiger.dao.base.sql;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xyz.tiger.dao.base.IBaseDao;
import com.xyz.tiger.dao.base.annotation.PUBVALUE;
import com.xyz.tiger.dao.base.sql.conditions.Cnds;
import com.xyz.tiger.dao.base.sql.conditions.Condition;
import com.xyz.tiger.dao.base.util.ReflectionUtils;
import com.xyz.tiger.dao.base.util.SqlUtils;

/**
 * 
 * @Description 生产SQL的工具类
 * @author Hanht
 * @date 2016年8月9日 下午3:04:21
 * @param <T>
 *            实体类对象
 */
public class SQLGenerator<T extends Serializable, PK extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** 实体类的所有属性 数据库列名称-实体类属性名称 */
	private Map<String, String> currentColumnFieldNames;
	/** 数据库表属性集合 */
	private Collection<String> columns;
	/** 实体类属性集合 */
	private Set<String> fields;
	/** 表名 */
	private String tableName;
	/** 主鍵名称 */
	private String pkName;
	/** 主键值 */
	private Object pkValue;
	
	
	IBaseDao<T, PK> baseDao;

	public SQLGenerator(Map<String, String> currentColumnFieldNames, String tableName, String pkName) {
		super();
		this.currentColumnFieldNames = currentColumnFieldNames;
		this.columns = currentColumnFieldNames.values();
		this.fields = currentColumnFieldNames.keySet();
		this.tableName = tableName;
		this.pkName = pkName;
	}

	/**
	 * 生成新增的SQL 只处理字段属性值不为空的情况
	 */
	public String insert(T t, Object pkValue) {
		this.pkValue = pkValue;
		Map<String,Object> cloumnValues = getFieldValuesNotNull(t);
		
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("INSERT INTO ").append(tableName)
			.append(" (").append(StringUtils.join(cloumnValues.keySet(), ",")).append(")")
			.append(" values (").append(StringUtils.join(cloumnValues.values(), ",")).append(")");

		logger.info("生成的INSERT_SQL为: " + sql_build.toString());

		return sql_build.toString();
	}

	/**
	 * 生成根据ID删除的SQL
	 */
	public String deleteByPk(PK primaryKey) {
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("DELETE FROM ").append(this.tableName)
			.append(" WHERE ").append(pkName).append(" = ")
			.append(SqlUtils.handleValue(primaryKey));

		logger.info("生成的SQL为: " + sql_build.toString());

		return sql_build.toString();
	}
	
	/**
	 * 生成根据where条件集合的删除的SQL
	 */
	public String deleteByparm(Cnds cnds) {
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("DELETE FROM ").append(this.tableName)
			.append(this.getWhereByCnds(cnds));
		
		logger.info("生成的SQL为: " + sql_build.toString());
		
		return sql_build.toString();
	}
	
	/**
	 * 生成更新的SQL 只处理字段属性值不为空的情况
	 */
	public String updateByPK(T t, PK primaryKey) {
		List<String> values = obtainColumnVals(t);
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("UPDATE ").append(tableName).append(" SET ")
        	.append(StringUtils.join(values, ",")).append(" WHERE ")
        	.append(pkName).append(" = ").append(SqlUtils.handleValue(primaryKey));

		logger.info("生成的SQL为: " + sql_build.toString());

		return sql_build.toString();
	}
	
	/**
	 * 生成更新的SQL 根据cnds对象生成where条件进行更新
	 */
	public String updateByParams(Object obj, Cnds cnds) {
		List<String> values = obtainColumnVals(obj);
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("UPDATE ").append(tableName).append(" SET ")
		.append(StringUtils.join(values, ","))
		.append(this.getWhereByCnds(cnds));
		
		logger.info("生成的SQL为: " + sql_build.toString());
		
		return sql_build.toString();
	}
	
	/**
	 * 生成查询单个对象SQL 根据主键查询对象
	 */
	public String selectOne(PK id){
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT * FROM ").append(tableName).append(" WHERE ")
			.append(this.pkName).append(" = ").append(SqlUtils.handleValue(id));
		
		logger.info("生成的SQL为: " + sql_build.toString());
		
		return sql_build.toString(); 
	}
	
	/**
	 * 生成查询全表SQL
	 */
	public String selectList(Cnds cnds){
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT * FROM ").append(tableName).append(this.getWhereByCnds(cnds));

		logger.info("生成的SQL为: " + sql_build.toString());
		
		return sql_build.toString(); 
	}
	
	public String count(){
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT count(*) FROM ").append(tableName);
		
		logger.info("生成的SQL为: " + sql_build.toString());
		
		return sql_build.toString(); 
	}
	
	/**
	 * 根据查询条件获取分页Sql
	 */
	public String selectPagination(Cnds cnds, int offset, int rows){
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT * FROM ").append(tableName).append(this.getWhereByCnds(cnds));
		sql_build.append(" limit ").append(offset).append(", ").append(rows);
		
		logger.info("生成的SQL为: " + sql_build.toString());
		
		return sql_build.toString(); 
	}
	
	public String countByParams(Cnds cnds){
		StringBuilder sql_build = new StringBuilder();
		sql_build.append("SELECT count(*) FROM ").append(tableName).append(this.getWhereByCnds(cnds));
		
		logger.info("生成的SQL为: " + sql_build.toString());
		
		return sql_build.toString(); 
	}
	
	/**
	 * @Description 
	 *		提供给生成新增SQL 使用<br>
	 *		获取实体类字段值不为空的属性
	 * @param t
	 * @return Map<String,Object>    返回类型
	 */
	private Map<String,Object> getFieldValuesNotNull(T t) {
		Map<String,Object> resMap = new LinkedHashMap<String, Object>();
		for (String field : fields) {
			if("gmtModified".equals(field)){
				continue;
			}
			Object value = null;
			//处理主键
			if(StringUtils.equalsIgnoreCase(field, pkName)){
				if(pkValue == PUBVALUE.PkRule.INCREMENT){
					continue;
				}
				value = ReflectionUtils.getFieldValue(t, field);
				value = SqlUtils.handleValue(pkValue);
			}else if("gmtCreate".equals(field)){
				value = SqlUtils.handleValue(new Date());
			}else{
				value = ReflectionUtils.getFieldValue(t, field);
				if(value == null || "".equals(value)){
					continue;
				}
				value = SqlUtils.handleValue(value);
			}
			
			resMap.put(ReflectionUtils.humpToLine(field), value);
		}
		return resMap;
	}
	
	/**
	 * 根据cnds对象组装Where条件
	 * @return String    where条件
	 */
	private String getWhereByCnds(Cnds cnds){
		StringBuilder whereSql = new StringBuilder();
		List<Condition> list = cnds.getCndsList();
		for(Condition con : list){
			whereSql.append(con.toSql(cnds.getDomain().getClass(), currentColumnFieldNames));
		}
		return whereSql.toString();
	}
	
	/**
     * 提供给生成更新SQL使用
     */
    private List<String> obtainColumnVals(Object obj) {
        List<String> colVals = new LinkedList<String>();
        for (String column : columns) {
        	if("gmt_create".equals(column)){
        		continue;
        	}
        	if("gmt_modified".equals(column)){
        		colVals.add(column + "=" + SqlUtils.handleValue(new Date()));
        	}else{
        		Object value = ReflectionUtils.getFieldValue(obj,ReflectionUtils.lineToHump(column));
                if (value != null && !StringUtils.equalsIgnoreCase(column, pkName)) {
                    colVals.add(column + "=" + SqlUtils.handleValue(value));
                }
        	}
        }
        return colVals;
    }
}
