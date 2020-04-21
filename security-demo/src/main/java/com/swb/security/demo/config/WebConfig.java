package com.swb.security.demo.config;

import com.swb.security.demo.filter.TimeFilter;
import com.swb.security.demo.interceptor.TimeInterceptor;
import com.swb.security.demo.interceptor.TimeInterceptor2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author swb
 * 时间  2020-03-26 14:22
 * 文件  WebConfig
 */
@Configuration
@ComponentScan(basePackages = { "com.swb.security.browser","com.swb.security.core"})
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TimeInterceptor timeInterceptor;

    @Autowired
    private TimeInterceptor2 timeInterceptor2;

    @Value("${filePath}")
    private String filePath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(timeInterceptor)
                //指定顺序  越小越先执行
                .order(1)
                //指定路径
                .addPathPatterns("/user");
        registry.addInterceptor(timeInterceptor2).order(2).addPathPatterns("/user*/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射图片保存地址
        registry.addResourceHandler("/file/**").
                addResourceLocations("file:" + filePath);
    }

    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean bean=new FilterRegistrationBean();

        TimeFilter filter=new TimeFilter();
        bean.setFilter(filter);

        //过滤路径 多个
        List<String> urls=new ArrayList<>();
        urls.add("/user");
        bean.setUrlPatterns(urls);
        return bean;
    }
}
