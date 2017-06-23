package com.xyz.tiger.dao.base.sql.where;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.xyz.tiger.dao.base.sql.conditions.Condition;
import com.xyz.tiger.dao.base.util.SqlUtils;

/**
 * @Description where条件的集合
 * @author Hanht
 * @date 2016年8月12日
 */
public class WhereSet implements Condition{

	/** where条件的中的静态字符串：AND、OR */
	public String joinCnd;
	/** java属性 */
	public String fieldName;
	/** 操作符 */
	public String op;
	/** 字段值 */
	public Object value;
	/** 多个字段值 可用于 between and*/
	public Object[] values;
	
	public WhereSet(String joinCnd, String fieldName, String op) {
		this.joinCnd = joinCnd;
		this.fieldName = fieldName;
		this.op = op;
	}

	public WhereSet(String joinCnd, String fieldName, String op, Object value) {
		this.joinCnd = joinCnd;
		this.fieldName = fieldName;
		this.op = op;
		this.value = value;
	}
	
	public WhereSet(String joinCnd, String fieldName, String op, Object[] values) {
		this.joinCnd = joinCnd;
		this.fieldName = fieldName;
		this.op = op;
		this.values = values;
	}

	@Override
	public String toSql(Class<?> domain,Map<String, String> currentFieldColumnNames) {
		StringBuilder sql = new StringBuilder();
		String columnName = currentFieldColumnNames.get(this.fieldName);
		if (StringUtils.isBlank(columnName)) {
			throw new RuntimeException("实体类[ " + domain.getName() + " ]及其父类中没有名为[ " + this.fieldName + " ]的字段");
		}
		if(C.getSqlWhere(C.BETWEEN).equals(this.op)){
			sql.append(SqlUtils.appendSpace(this.joinCnd)).append(columnName)
				.append(SqlUtils.appendSpace(this.op)).append(SqlUtils.handleValue(this.values[0]))
				.append(SqlUtils.appendSpace(C.getSqlWhere(C.AND))).append(SqlUtils.handleValue(this.values[1]));
		}
		sql.append(SqlUtils.appendSpace(this.joinCnd)).append(columnName)
			.append(SqlUtils.appendSpace(this.op));
		if(this.value != null){
			sql.append(SqlUtils.handleValue(this.value));
		}
		return sql.append(SqlUtils.SPACE).toString();
	}

}
