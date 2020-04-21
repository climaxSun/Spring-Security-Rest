package com.swb.security.demo.controller;

import com.swb.security.demo.entity.Test;
import com.swb.security.demo.service.TestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * (Test)表控制层
 *
 * @author makejava
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

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Test selectOne(Integer id) {
        return this.testService.queryById(id);
    }

    @PostMapping("/add")
    public String add(@RequestBody Test test){
        test.setCreateDate(LocalDateTime.now());
        testService.insert(test);
        return "ok";
    }

    @GetMapping("/{id:\\d+}")
    public Test get(@PathVariable Integer id){
        Test test = testService.queryById(id);
        return test;
    }

    @ApiOperation("测试时间LocalDatetime")
    @GetMapping("testDate")
    public String testDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime){
        return dateTime.toString();
    }

    @ApiOperation("测试时间LocalDatetime")
    @PostMapping("testDateBody")
    public Test testDateBody(@RequestBody Test test){
        test.setId(1);
        return test;
    }

}