package com.swb.security.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author  swb
 * 时间  2020-02-09 13:56
 * 文件  IBaseDao
 */
public interface IBaseDao<T>  extends Mapper<T>, MySqlMapper<T> {
}
