package com.xyz.tiger.dao.base.sql.conditions;

import java.util.Map;

/**
 * 
 * @Description 
 * 获得 WHERE 条件。其中也可以包括 ORDER BY 和 GROUP BY </br>
 * 老老实实的将你返回的字符串拼接在 WHERE 后面
 * <p>
 * 默认提供给你 Cnds 类，便于你快速构建你的条件语句。
 * <p>
 * @author Hanht
 * @date 2016年8月9日 下午5:58:49
 */
public interface Condition {

    /**
     * 生成一个条件，这个条件就是 SQL 'WHERE' 关键字后面的那部分。
     * <p>
     * 当然你返回的字符串如果不是以 WHERE 或者 ORDER BY 开头，将会被 加上 WHERE。
     * <p>
     * 你的字符串前后的空白会被截除
     * 
     * @param entity
     *            实体
     * @return 条件字符串
     */
    String toSql(Class<?> domain,Map<String, String> currentFieldColumnNames);

}
