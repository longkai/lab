/*
 * The MIT License (MIT)
 * Copyright (c) 2013 newgxu.cn <the original author or authors>.
 * The software shall be used for good, not evil.
 */
package cn.newgxu.lab.support;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 通用的mybatis数据访问接口，包含通用的的增删改查列表和总记录数方法.
 *
 * <b>现在已不推荐使用，依照自己的定义来（组织好结构，别乱来）。</b>
 * @User longkai
 * @Date 13-8-1
 * @Mail im.longkai@gmail.com
 */
public interface CommonRepo<T> {

	/**
	 * 将一个实体持久化
	 *
	 * @param entity
	 */
	void insert(T entity);

	/**
	 * 通过主键查询一个实体
	 *
	 * @param pk 序列化类型的主键
	 * @return 查找到的实体对象
	 */
	T find(Serializable pk);

	/**
	 * 根据条件得到相关记录数
	 *
	 * @param from  optional
	 * @param where optional
	 * @return 记录数
	 */
	long count(@Param("from") String from, @Param("where") String where);

	/**
	 * 自定义更新语句
	 *
	 * @param update 更新的字段比如(set c1=v1, c2=v2)等
	 * @return 成功更新的记录数
	 * @aram where 可选
	 */
	int update(@Param("update") String update, @Param("where") String where);

	/**
	 * @param from
	 * @param where
	 * @return 成功删除的记录数
	 */
	int delete(@Param("from") String from, @Param("where") String where);

	/**
	 * 自定义询一个实体
	 *
	 * @param columns 需要检索的字段（null表示所有的字段, *表示懒加载，只加载关联的外键）
	 * @param from    optional，为null表示默认查询本实体而不会做表连接
	 * @param where   optional
	 * @return 查询到的实体，找不到则返回null
	 */
	T findOne(@Param("columns") String columns, @Param("from") String from, @Param("where") String where);

	/**
	 * 自定义查询，适用于存在关联的的较复杂的表（只需要提供各个字句的参数即可）
	 *
	 * @param columns 需要检索的字段（null表示所有的字段, *表示懒加载，只加载关联的外键）
	 * @param from    optional，为null表示默认查询本实体而不会做表连接
	 * @param where   optional
	 * @param groupBy optional
	 * @param having  optional
	 * @param orderBy optional
	 * @param limit   optional
	 * @return results
	 */
	List<T> query(@Param("columns") String columns, @Param("from") String from,
				  @Param("where") String where, @Param("groupBy") String groupBy,
				  @Param("having") String having, @Param("orderBy") String orderBy,
				  @Param("limit") String limit);
}
