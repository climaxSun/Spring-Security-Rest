package com.swb.security.demo.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author swb
 * 时间  2020-03-22 22:41
 * 文件  IBaseDao
 */
public interface IBaseDao<T>  extends Mapper<T>, MySqlMapper<T> {
}
