package com.swb.security.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .param("username", "冴岛钢牙")
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

    @Test
    public void whenCreateUser() throws Exception {
        Date date = new Date();
        System.out.println(date.getTime());
        String content = "{\"username\":\"冴岛雷牙\",\"password\":1234567489,\"birthday\":" + date.getTime() + "}";
        MockHttpServletResponse response = mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        System.out.println("TestResponse=" + response.getContentAsString());

    }

    @Test
    public void whenUploadSuccess() throws Exception {
        String result = mvc.perform(fileUpload("/files")
                .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello upload".getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }
}
