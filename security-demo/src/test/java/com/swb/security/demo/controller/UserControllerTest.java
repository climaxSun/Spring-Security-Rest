package com.swb.security.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author swb
 * 时间  2020-03-24 21:20
 * 文件  UserControllerTest
 */
@RunWith(SpringRunner.class)//使用SpringRunner执行测试用例
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;//伪造的mvc环境

    /**
     * Before注解在每次测试前都会执行
     */
    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();//构建mvc的环境
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username","冴岛钢牙")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //对上面请求结果的期望， 希望返回状态码是200
                .andExpect(MockMvcResultMatchers.status().isOk())
                //返回一个集合，长度为3
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

    @Test
    public void whenGenInfoSuccess() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //判断是否返回的json的username是否是  冴岛大河
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("冴岛大河"))
                .andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        String json = response.getContentAsString();
        System.out.println(json);
    }

    @Test
    public void whenGenInfoSuccess2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
