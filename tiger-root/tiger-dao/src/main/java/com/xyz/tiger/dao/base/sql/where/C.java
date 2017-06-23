package com.xyz.tiger.dao.base.sql.where;

/**
 * <h4>拼接SQL的操作符</h4>
 * 操作符为以下之一：<br>
 * <ul>
 *      <li>EQ 等于，对应sql中的'='
 *      <li>LT 小于，对应sql中'<' 
 *      <li>GT 大于，对应sql中'>' 
 *      <li>NE 不等于，对应sql中的'<>' 
 *      <li>LE 小于等于，对应sql中的'<=' 
 *      <li>GE 大于等于，对应sql中的'>='
 *      <li>NULL 为空，对应sql中的'is null'
 *      <li>NOTNULL 不为空，对应sql中的'is not null'
 *      <li>IN  包含多值，值需要类型拼装，如字符串为：'v1','v2','v3';数字为:1,2,3
 *      <li>NOTIN   不包含多值，值需要类型拼装，如字符串为：'v1','v2','v3';数字为:1,2,3
 *      <li>LIKE 对应sql中的'like'
 * 		<li>NOTLIKE 对应sql中的'not like'
 *      <li>OR  或，多条件或查询
 *      <li>BETWEEN  选取介于两个值之间的数据范围
 * </ul>
 * @author Hanht
 * @date 2016年8月12日
 */
public enum C {

	EQ, NE, GT, GE, LT, LE, NULL, NOTNULL, IN, NOTIN, LIKE, NOTLIKE, BETWEEN,
	WHERE, AND, OR;

	public static String getSqlWhere(C c) {
		switch (c) {
		case EQ:
			return "=";
		case NE:
			return "<>";
		case GT:
			return ">";
		case GE:
			return ">=";
		case LT:
			return "<";
		case LE:
			return "<=";
		case NULL:
			return "IS NULL";
		case NOTNULL:
			return "IS NOT NULL";
		case IN:
			return "IN";
		case NOTIN:
			return "NOT IN";
		case LIKE:
			return "LIKE";
		case NOTLIKE:
			return "NOT LIKE";
		case WHERE:
			return "WHERE";
		case AND:
			return "AND";
		case OR:
			return "OR";
		case BETWEEN:
			return "BETWEEN";
		default:
			return "=";
		}
	}
}
