package com.swb.security.demo.service.impl;

import com.swb.security.demo.entity.Rider;
import com.swb.security.demo.entity.RiderExample;
import com.swb.security.demo.entity.Test;
import com.swb.security.demo.entity.TestExample;
import com.swb.security.demo.mapper.RiderMapper;
import com.swb.security.demo.mapper.TestMapper;
import com.swb.security.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * (Test)表服务实现类
 *
 * @author makejava
 * @since 2020-04-11 01:17:10
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Autowired
    private RiderMapper riderMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Test queryTestById(Integer id) {
        return this.testMapper.selectByPrimaryKey(id);
    }

    @Override
    public Rider queryRiderById(Integer id) {
        return this.riderMapper.selectByPrimaryKey(id);
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
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public Test insertTest(Test test) {
        this.testMapper.insert(test);
        return test;
    }

    /**
     * 新增数据
     *
     * @param rider 实例对象
     * @return 实例对象
     */
    @Override
    public Rider insertRider(Rider rider) {
        this.riderMapper.insert(rider);
        return rider;
    }

    @Override
    public Rider insert(Rider rider) {
        this.riderMapper.insert(rider);
        return null;
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
        return this.queryTestById(test.getId());
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

    @Override
    public Map<String, Object> testThread(Integer id) {
        long l=System.currentTimeMillis();
        ExecutorService pool= Executors.newFixedThreadPool(3);
        Callable testCallable=()->{
            Test test = testMapper.selectByPrimaryKey(id);
            return test;
        };
        Callable riderCallable=()->{
            RiderExample riderExample=new RiderExample();
            riderExample.createCriteria().andTestIdEqualTo(id);
            List<Rider> riders = riderMapper.selectByExample(riderExample);
            return riders;
        };
        Future<Test> testFuture=pool.submit(testCallable);
        Future<List> riderFuture=pool.submit(riderCallable);
        pool.shutdown();
        Map<String,Object> map=new HashMap<>(2);
        try {
            map.put("test",testFuture.get(1000, TimeUnit.MILLISECONDS));
            map.put("rider",riderFuture.get(1000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        log.info("多线程总共运行时间"+(System.currentTimeMillis()-l));
        return map;
    }

    @Override
    public Map<String, Object> testNoThread(Integer id) {
        long l=System.currentTimeMillis();
        Map<String,Object> map=new HashMap<>(2);
        map.put("test",testMapper.selectByPrimaryKey(id));
        RiderExample riderExample=new RiderExample();
        riderExample.createCriteria().andTestIdEqualTo(id);
        map.put("rider",riderMapper.selectByExample(riderExample));
        log.info("单线程总共运行时间"+(System.currentTimeMillis()-l));
        return map;
    }

    @Override
    public List<Test> selectTestByDate(LocalDateTime dateTime) {
        TestExample testExample=new TestExample();
        testExample.createCriteria().andCreateDateBetween(dateTime,LocalDateTime.now()).andNameLike("zi-o").andNameNotLike("鬼");
        List<Test> tests = testMapper.selectByExample(testExample);
        return tests;
    }
}