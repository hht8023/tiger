package com.xyz.tiger.dao.base.sql.where;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.xyz.tiger.dao.base.sql.conditions.Condition;
import com.xyz.tiger.dao.base.util.SqlUtils;

/**
 * 组合一组表达式
 * @author Hanht
 * @date 2016年8月12日
 */

public class SqlExpressionGroup implements Condition{

	private List<SqlExpressionGroup> exps;

	/** where条件的中的静态字符串：AND、OR */
	public String joinCnd;
	/** java属性 */
	private String fieldName;
	/** 操作符 */
	private String op;
	/** 字段值 */
	private Object value;
	/** 分组的前置条件 */
	private String groupW;
	
	public SqlExpressionGroup() {
    }
	
	public SqlExpressionGroup(String joinCnd,String fieldName, String op, Object value) {
		this.joinCnd = joinCnd;
		this.fieldName = fieldName;
		this.op = op;
		this.value = value;
	}
	
	public SqlExpressionGroup group(String groupW){
		this.groupW = groupW;
		this.exps = new ArrayList<SqlExpressionGroup>();
		return this;
	}
	
	public SqlExpressionGroup where(String fieldName, C op, Object value) {
		this.exps.add(new SqlExpressionGroup("", fieldName, C.getSqlWhere(op), value));
		return this;
	}
	
	public SqlExpressionGroup and(String fieldName, C op, Object value) {
		this.exps.add(new SqlExpressionGroup(C.getSqlWhere(C.AND), fieldName, C.getSqlWhere(op), value));
		return this;
	}
	
	public SqlExpressionGroup or(String fieldName, C op, Object value) {
		this.exps.add(new SqlExpressionGroup(C.getSqlWhere(C.OR), fieldName, C.getSqlWhere(op), value));
		return this;
	}

	@Override
	public String toSql(Class<?> domain,Map<String, String> currentFieldColumnNames) {
		StringBuilder sql = new StringBuilder();
		sql.append(this.groupW).append(SqlUtils.SPACE).append("(");
		for(SqlExpressionGroup seg : this.exps){
			String columnName = currentFieldColumnNames.get(seg.fieldName);
			if (StringUtils.isBlank(columnName)) {
				throw new RuntimeException("实体类[ " + domain.getName() + " ]及其父类中没有名为[ " + seg.fieldName + " ]的字段");
			}
			sql.append(SqlUtils.appendSpace(seg.joinCnd)).append(columnName)
				.append(SqlUtils.appendSpace(seg.op)).append(SqlUtils.handleValue(seg.value));
		}
		return sql.append(")").append(SqlUtils.SPACE).toString();
	}
}
