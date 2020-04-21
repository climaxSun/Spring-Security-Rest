package com.swb.security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author swb
 * 时间  2020-03-22 22:20
 * 文件  DemoApplication
 */
@SpringBootApplication()
@RestController
@EnableSwagger2
@MapperScan("com.swb.security.demo.mapper")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello Spring Security";
    }
}
