package com.swb.security.browser.config;

import com.swb.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author swb
 * 时间  2020-03-29 14:04
 * 文件  BrowserSecurityConfig
 */
@Configuration
@Slf4j
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    @Qualifier("swbSecurityAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    @Qualifier("swbSecurityAuthenticationFailureHandler")
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("BrowserSecurityConfig.configure");
//        指定身份认证的方式 表单登录
        http.formLogin()
                //指定登录页面
                .loginPage("/authentication/require")
                //自定义登录请求路径,默认是post方式的login
                .loginProcessingUrl("/authentication/form")
                //登录成功自定义处理注入
                .successHandler(successHandler)
                //登录失败自定义处理注入
                .failureHandler(failureHandler)
//        basic方式认证
//        http.httpBasic()
                .and()
//                开始请求权限配置
                .authorizeRequests()
                //当前路径不需要认证
                .antMatchers("/authentication/require",securityProperties.getBrowser().getLoginPage())
                .permitAll()
//                下面2行是对http所有的请求必须通过授权认证才可以访问
//                对任何请求
                .anyRequest()
//                都需要身份验证
                .authenticated().and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 加密
        return new BCryptPasswordEncoder();
    }


}
