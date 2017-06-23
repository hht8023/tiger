package com.xyz.tiger.dao.base.sql.conditions;

import java.util.LinkedList;
import java.util.List;

import com.xyz.tiger.dao.base.sql.order.OrderItem;
import com.xyz.tiger.dao.base.sql.where.C;
import com.xyz.tiger.dao.base.sql.where.SqlExpressionGroup;
import com.xyz.tiger.dao.base.sql.where.WhereSet;

/**
 * @Description 方便以OO的形式，快速构建sql查询，减少程序员的代码书写量。
 * 
 *              <h4>在 Dao 接口中使用</h4><br>
 * 
 *              比如一个通常的查询:
 *              <p>
 *              Cnds cnds = Cnds.me(Test.class);
 * 
 *              <h4>链式赋值示例</h4><br>
 *              Cnds.where("id", C.EQ, 34).and("name",C.LIKE,"T%").asc("name");
 *              <br>
 *              相当于<br>
 *              WHERE id>34 AND name LIKE 'T%' ORDER BY name ASC<br>
 * 
 *              <h4>orderBy示例</h4><br>
 *              Cnds.orderBy().desc("id"); <br>
 *              相当于<br>
 *              ORDER BY id DESC
 * 
 *              <h4>组合条件查询</h4><br>
 *              SqlExpressionGroup seg = cnds.whereGroup("name", C.EQ,
 *              "wendal").and("age", C.GE, "4").or("age", C.GE, "18");<br>
 *              cnds.whereGroup(seg);<br>
 *              相当于 where (name="wendal" and age = 4 or age > 18)
 * 
 *              <h4>BETWEEN示例</h4><br>
 *              Cnds.where("age", "between", new Object[]{19,29})<br>
 *              相当于 where age BETWEEN 19 AND 29
 * 
 * 
 *              <h4 style=color:red>你还需要知道的是:</h4><br>
 *              <ul>
 *              <li>你设置的字段名，是 java 的字段名 -- 如果字段有@TableColumn注解，那么获取注解值作为数据库字段名
 *              <li>如果你设置Java字段名不存在@TableColumn注解，则被认为当前字段名是数据库字段名，将直接使用
 *              <li>你的值，如果是字符串，或者其他类字符串对象（某种 CharSequence），那么在转换成 SQL时，会正确被单引号包裹
 *              <li>你的值如果是不可理解的自定义对象，会被转化成字符串处理
 *              </ul>
 * @author Hanht
 * @date 2016年8月11日 下午5:55:21
 */

public class Cnds {

	private Class<?> domain;// domain的class

	private List<Condition> cndsList = new LinkedList<Condition>();

	public static Cnds me(Class<?> domain) {
		Cnds Cndss = new Cnds();
		Cndss.domain = domain;
		return Cndss;
	}

	/**
	 * 生成一个新的Cnds实例
	 * 
	 * @param fieldName
	 *            java属性
	 * @param op
	 *            操作符 {@link com.lk.manage.core.sql.where.C}
	 * @param value
	 *            参数值. 如果操作符是between,参数值需要是new Object[]{12,39}形式
	 * @return Cnds对象，便于链式操作
	 */
	public Cnds where(String fieldName, C op, Object value) {
		WhereSet ws = new WhereSet(C.getSqlWhere(C.WHERE), fieldName, C.getSqlWhere(op), value);
		cndsList.add(ws);
		return this;
	}

	/**
	 * 生成一个新的Cnds实例
	 * 
	 * @param fieldName
	 *            java属性
	 * @param op
	 *            操作符 {@link com.lk.manage.core.sql.where.C}
	 * @return Cnds对象，便于链式操作
	 */
	public Cnds where(String fieldName, C op) {
		WhereSet ws = new WhereSet(C.getSqlWhere(C.WHERE), fieldName, C.getSqlWhere(op));
		cndsList.add(ws);
		return this;
	}

	/**
	 * 将一个条件表达式封装为条件表达式组
	 * 
	 * @param fieldName
	 *            java属性
	 * @param op
	 *            操作符 {@link com.lk.manage.core.sql.where.C}
	 * @param value
	 *            参数值. 如果操作符是between,参数值需要是new Object[]{12,39}形式
	 * @return SqlExpressionGroup对象，便于链式操作
	 */
	public SqlExpressionGroup whereGroup(String fieldName, C op, Object value) {
		SqlExpressionGroup seg = new SqlExpressionGroup();
		seg.group(C.getSqlWhere(C.WHERE)).where(fieldName, op, value);
		return seg;
	}

	/**
	 * 将一个条件表达式封装为条件表达式组
	 * 
	 * @param seg
	 *            条件表达式组
	 * @return Cnds 返回类型
	 */
	public Cnds whereGroup(SqlExpressionGroup seg) {
		cndsList.add(seg);
		return this;
	}

	/**
	 * 生成一个新的Cnds实例
	 * 
	 * @param fieldName
	 *            java属性
	 * @param op
	 *            操作符 {@link com.lk.manage.core.sql.where.C}
	 * @param value
	 *            参数值. 如果操作符是between,参数值需要是new Object[]{12,39}形式
	 * @return Cnds对象，便于链式操作
	 */
	public Cnds and(String fieldName, C op, Object value) {
		WhereSet ws = new WhereSet(C.getSqlWhere(C.AND), fieldName, C.getSqlWhere(op), value);
		cndsList.add(ws);
		return this;
	}

	/**
	 * 将一个条件表达式封装为条件表达式组
	 * 
	 * @param fieldName
	 *            java属性
	 * @param op
	 *            操作符 {@link com.lk.manage.core.sql.where.C}
	 * @param value
	 *            参数值. 如果操作符是between,参数值需要是new Object[]{12,39}形式
	 * @return SqlExpressionGroup对象，便于链式操作
	 */
	public SqlExpressionGroup andGroup(String fieldName, C op, Object value) {
		SqlExpressionGroup seg = new SqlExpressionGroup();
		seg.group(C.getSqlWhere(C.AND)).where(fieldName, op, value);
		return seg;
	}

	/**
	 * 将一个条件表达式封装为条件表达式组
	 * 
	 * @param seg
	 *            条件表达式组
	 * @return Cnds 返回类型
	 */
	public Cnds andGroup(SqlExpressionGroup seg) {
		cndsList.add(seg);
		return this;
	}

	/**
	 * 生成一个新的Cnds实例
	 * 
	 * @param fieldName
	 *            java属性
	 * @param op
	 *            操作符 {@link com.lk.manage.core.sql.where.C}
	 * @param value
	 *            参数值. 如果操作符是between,参数值需要是new Object[]{12,39}形式
	 * @return Cnds对象，便于链式操作
	 */
	public Cnds or(String fieldName, C op, Object value) {
		WhereSet ws = new WhereSet(C.getSqlWhere(C.OR), fieldName, C.getSqlWhere(op), value);
		cndsList.add(ws);
		return this;
	}

	/**
	 * 将一个条件表达式封装为条件表达式组
	 * 
	 * @param fieldName
	 *            java属性
	 * @param op
	 *            操作符 {@link com.lk.manage.core.sql.where.C}
	 * @param value
	 *            参数值. 如果操作符是between,参数值需要是new Object[]{12,39}形式
	 * @return SqlExpressionGroup对象，便于链式操作
	 */
	public SqlExpressionGroup orGroup(String fieldName, C op, Object value) {
		SqlExpressionGroup seg = new SqlExpressionGroup();
		seg.group(C.getSqlWhere(C.OR)).where(fieldName, op, value);
		return seg;
	}

	/**
	 * 将一个条件表达式封装为条件表达式组
	 * 
	 * @param seg
	 *            条件表达式组
	 * @return Cnds 返回类型
	 */
	public Cnds orGroup(SqlExpressionGroup seg) {
		cndsList.add(seg);
		return this;
	}

	/**
	 * 生成一个新的Cnds实例
	 * 
	 * @param fieldName
	 *            java属性
	 * @param value
	 *            参数值. 如果操作符是between,参数值需要是new Object[]{12,39}形式
	 * @return Cnds对象，便于链式操作
	 */
	public Cnds whereBetween(String fieldName, Object[] values) {
		WhereSet ws = new WhereSet(C.getSqlWhere(C.WHERE), fieldName, C.getSqlWhere(C.BETWEEN), values);
		cndsList.add(ws);
		return this;
	}

	/**
	 * 生成一个新的Cnds实例
	 * 
	 * @param fieldName
	 *            java属性
	 * @param value
	 *            参数值. 如果操作符是between,参数值需要是new Object[]{12,39}形式
	 * @return Cnds对象，便于链式操作
	 */
	public Cnds andBetween(String fieldName, Object[] values) {
		WhereSet ws = new WhereSet(C.getSqlWhere(C.AND), fieldName, C.getSqlWhere(C.BETWEEN), values);
		cndsList.add(ws);
		return this;
	}
	
	/**
	 * 多个排序条件
	 * 
	 * @param orders
	 * @return Cnds    返回类型
	 */
	public Cnds order(OrderItem orders){
		cndsList.add(orders);
		return this;
	}
	
	/**
	 * 按照给定的字段名升序排列
	 * 
	 * @param fieldName
	 *            字段名
	 * @return orderby对象，方便链式操作
	 * 
	 * @date 2016年8月11日 下午5:34:26
	 */
	public Cnds asc(String fieldName){
		OrderItem order = new OrderItem();
		order.asc(fieldName);
		cndsList.add(order);
		return this;
	}
	
	/**
	 * 安州给定的字段降序排列
	 * 
	 * @param fieldName
	 *            字段名
	 * @return orderby对象，方便链式操作
	 * 
	 * @date 2016年8月11日 下午5:34:26
	 */
	public Cnds desc(String fieldName){
		OrderItem order = new OrderItem(fieldName,"DESC");
		cndsList.add(order);
		return this;
	}

	
	public Class<?> getDomain() {
		return domain;
	}

	/**
	 * @return cndsList
	 */
	public List<Condition> getCndsList() {
		return cndsList;
	}
	
}
