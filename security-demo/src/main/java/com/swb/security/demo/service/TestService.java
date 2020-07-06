package com.swb.security.demo.service;

import com.swb.security.demo.entity.Rider;
import com.swb.security.demo.entity.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * (Test)表服务接口
 *
 * @author makejava
 * @since 2020-04-11 00:05:03
 */
public interface TestService {

    /**
     * 通过ID查询单条Test数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Test queryTestById(Integer id);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Rider queryRiderById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Test> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param test 实例对象
     * @return 实例对象
     */
    Test insertTest(Test test);

    /**
     * 新增数据
     *
     * @param rider 实例对象
     * @return 实例对象
     */
    Rider insertRider(Rider rider);

    /**
     * 新增数据
     *
     * @param test 实例对象
     * @return 实例对象
     */
    Rider insert(Rider test);

    /**
     * 修改数据
     *
     * @param test 实例对象
     * @return 实例对象
     */
    Test update(Test test);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Map<String,Object> testThread(Integer id);

    Map<String,Object> testNoThread(Integer id);


    List<Test> selectTestByDate(LocalDateTime dateTime);

}