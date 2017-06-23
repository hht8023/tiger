package com.xyz.tiger.dao.base.sql.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.xyz.tiger.dao.base.sql.conditions.Condition;
import com.xyz.tiger.dao.base.util.SqlUtils;

/**
 * 排序条件项
 * 
 * @author Hanht
 * @date 2017年5月11日
 */
public class OrderItem implements Condition, OrderBy {
	/** 字段名 */
	private String proName;
	/** 排序方向 */
	private String direction;// asc,desc

	private List<OrderItem> orders;
	
	public OrderItem() {
		orders = new ArrayList<OrderItem>();
	}

	public OrderItem(String proName, String direction) {
		this.proName = proName;
		this.direction = direction;
		orders = new ArrayList<OrderItem>();
	}

	@Override
	public OrderBy asc(String fieldName) {
		orders.add(new OrderItem(fieldName, "ASC"));
		return this;
	}

	@Override
	public OrderBy desc(String fieldName) {
		orders.add(new OrderItem(fieldName, "DESC"));
		return this;
	}
	
	@Override
	public String toSql(Class<?> domain, Map<String, String> currentFieldColumnNames) {
		StringBuilder sql = new StringBuilder();
		for(int i = 0,len = orders.size();i < len; i++){
			String columnName = currentFieldColumnNames.get(orders.get(i).proName);
			if (StringUtils.isBlank(columnName)) {
				throw new RuntimeException("实体类[ " + domain.getName() + " ]及其父类中没有名为[ " + this.proName + " ]的字段");
			}
			if(i == 0){
				sql.append(" ORDER BY ");
			}
			sql.append(columnName).append(SqlUtils.SPACE).append(orders.get(i).direction).append(SqlUtils.SPACE);
			if(i != len - 1){
				sql.append(",");
			}
		}
		return sql.toString();
	}
}
