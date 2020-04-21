package com.swb.security.demo.service.impl;

import com.swb.security.demo.entity.Test;
import com.swb.security.demo.mapper.TestMapper;
import com.swb.security.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * (Test)表服务实现类
 *
 * @author makejava
 * @since 2020-04-11 01:17:10
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Test queryById(Integer id) {
        ExecutorService pool= Executors.newFixedThreadPool(3);


        return this.testMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Test> queryAllByLimit(int offset, int limit) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param test 实例对象
     * @return 实例对象
     */
    @Override
    public Test insert(Test test) {
        this.testMapper.insert(test);
        return test;
    }

    /**
     * 修改数据
     *
     * @param test 实例对象
     * @return 实例对象
     */
    @Override
    public Test update(Test test) {
        this.testMapper.updateByPrimaryKey(test);
        return this.queryById(test.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.testMapper.deleteByExample(id) > 0;
    }
}