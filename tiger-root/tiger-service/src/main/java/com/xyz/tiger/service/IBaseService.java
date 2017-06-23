package com.xyz.tiger.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xyz.tiger.dao.base.sql.conditions.Cnds;
import com.xyz.tiger.dao.base.util.page.Pagination;

/**
 * 业务层基础接口
 * 
 * @author Hanht
 * @date 2016年8月10日 下午3:20:38
 */
public interface IBaseService<T extends Serializable, PK extends Serializable> {

	/**
	 * 插入一个实体（在数据库INSERT一条记录） <br>
	 * 根据实体类中设置的字段属性值 忽略没有设置值的字段
	 * 
	 * @param t
	 *            当前实体类
	 * @return 插入的行数
	 */
	int insert(T t);

	/**
	 * 批量插入多个实体 <br>
	 * 根据实体类中设置的字段属性值 忽略没有设置值的字段
	 * 
	 * @param tList
	 * @return int 插入的行数
	 */
	int insertOfBatch(List<T> tList);

	/**
	 * 根据主键进行删除记录
	 * 
	 * @param id
	 *            主键
	 * @return 删除的对象个数，正常情况=1
	 */
	int deleteByPk(T t, PK id);

	/**
	 * 删除符合条件的记录
	 * <p>
	 * <strong>此方法一定要慎用，如果条件设置不当，可能会删除有用的记录！</strong>
	 * </p>
	 * 
	 * @param cnds
	 *            构造where条件
	 * @return 删除的对象个数
	 */
	int deleteByparm(Cnds cnds);

	/**
	 * 根据主键修改一个实体对象（UPDATE一条记录）
	 * 
	 * @param t
	 *            实体对象
	 * @param id
	 *            主键
	 */
	void updateByPK(T t, PK id);

	/**
	 * 修改符合条件的记录
	 * <p>
	 * 此方法特别适合于一次性把多条记录的某些字段值设置为新值（定值）的情况，比如修改符合条件的记录的状态字段
	 * </p>
	 * <p>
	 * 此方法的另一个用途是把一条记录的个别字段的值修改为新值（定值），此时要把条件设置为该记录的主键
	 * </p>
	 * 
	 * @param cnds
	 *            用于产生SQL的参数值，包括WHERE条件、目标字段和新值等
	 * @return 修改的记录个数，用于判断修改是否成功
	 */
	int updateByParams(Cnds cnds);

	/**
	 * 按主键取记录
	 * 
	 * @param id
	 *            主键值
	 * @return 记录实体对象，如果没有符合主键条件的记录，则返回null
	 */
	T selectOne(T t, PK id);

	/**
	 * 按主键取记录
	 * 
	 * @param t
	 *            实体对象
	 * @param id
	 *            主键值
	 * @return Map<String,Object> 返回类型
	 */
	Map<String, Object> selectOneM(T t, PK id);
	
	/**
	 * <p>
	 * 根据查询条件获取记录
	 * </p>
	 *
	 * @param cnds
	 *            条件表达式
	 * @return 全部记录实体对象的List
	 */
	List<T> selectList(Cnds cnds);

	/**
	 * <p>
	 * 根据查询条件获取记录
	 * </p>
	 *
	 * @param cnds
	 *            条件表达式
	 * @return 全部记录List<Map<String, Object>>
	 */
	List<Map<String, Object>> selectListM(Cnds cnds);
	
	/**
	 * 按条件查询记录，并处理成分页结果
	 * 
	 * @param cnds
	 *            查询条件参数，包括WHERE条件、分页条件、排序条件
	 * @param page
	 *            当前页数
	 * @param rows
	 *            每页显示的条数
	 * @return 分页模型
	 */
	Pagination selectPagination(Cnds cnds, int page, int rows);
	
	/**
	 * 按条件查询记录，并处理成分页结果
	 * 
	 * @param cnds
	 *            查询条件参数，包括WHERE条件、分页条件、排序条件
	 * @param page
	 *            当前页数
	 * @param rows
	 *            每页显示的条数
	 * @return 分页模型
	 */
	Pagination selectPaginationM(Cnds cnds, int page, int rows);

	/**
	 * 自定义sql查询
	 * 
	 * @param po
	 *            用于封装返回结果的Bean
	 * @param sql
	 *            用于执行查询的Sql
	 * @param args
	 *            Sql占位付对应的参数
	 * @return 结果集合
	 */
	List<Map<String, Object>> selectBySql(String sql);
}
