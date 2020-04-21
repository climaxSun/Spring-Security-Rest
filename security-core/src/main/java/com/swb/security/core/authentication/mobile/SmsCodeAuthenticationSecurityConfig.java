package com.swb.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author swb
 * 时间  2020-04-19 21:36
 * 文件  SmsCodeAuthenticationSecurityConfig
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    @Qualifier(value = "swbSecurityAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    @Qualifier(value = "swbSecurityAuthenticationFailureHandler")
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    @Qualifier(value = "myUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //创建过滤器filter
        SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
        //filter设置AuthenticationManager
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //filter设置成功和失败的处理器  Autowired注入  在browser中写过，app中可以重写
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        //配置Provider
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
