package com.swb.security.demo.controller;

import com.swb.security.demo.entity.Rider;
import com.swb.security.demo.entity.Test;
import com.swb.security.demo.service.TestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * (Test)表控制层
 *
 * @author swb
 * @since 2020-04-11 00:05:04
 */
@RestController
@RequestMapping("test")
public class TestController {
    /**
     * 服务对象
     */
    @Autowired
    private TestService testService;

    @PostMapping("/test")
    public String addTest(@RequestBody Test test){
        test.setCreateDate(LocalDateTime.now());
        testService.insertTest(test);
        return "ok";
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/test/{id:\\d+}")
    public Test getTest(@PathVariable Integer id){
        Test test = testService.queryTestById(id);
        return test;
    }

    @PostMapping("/rider")
    public String addRider(@RequestBody Rider rider){
        testService.insertRider(rider);
        return "ok";
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/rider/{id:\\d+}")
    public Rider getRider(@PathVariable Integer id){
        Rider rider = testService.queryRiderById(id);
        return rider;
    }

    @ApiOperation("测试时间LocalDatetime")
    @GetMapping("testDate")
    public List<Test> testDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime){
        return testService.selectTestByDate(dateTime);
    }

    @ApiOperation("测试时间LocalDatetime")
    @PostMapping("testDateBody")
    public Test testDateBody(@RequestBody Test test){
        test.setId(1);
        return test;
    }

    @GetMapping("/multithreading")
    public Map<String,Object> testMultithread(){
        return testService.testThread(1);
    }

    @GetMapping("/singleThread")
    public Map<String,Object> testSingleThread(){
        return testService.testNoThread(1);
    }

}