package com.swb.security.browser.config;

import com.swb.security.core.properties.SecurityProperties;
import com.swb.security.core.validate.filter.ValidateCodeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("BrowserSecurityConfig.configure");
        ValidateCodeFilter filter=new ValidateCodeFilter();
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setSecurityProperties(securityProperties);
        filter.afterPropertiesSet();
//      指定身份认证的方式 表单登录
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).formLogin()
                //指定登录页面
                .loginPage("/authentication/require")
                //自定义登录请求路径,默认是post方式的login
                .loginProcessingUrl("/authentication/form")
                //登录成功自定义处理注入
                .successHandler(successHandler)
                //登录失败自定义处理注入
                .failureHandler(failureHandler)
                //配置记住我
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
//        basic方式认证
//        http.httpBasic()
                .and()
//                开始请求权限配置
                .authorizeRequests()
                //当前路径不需要认证
                .antMatchers("/authentication/require",
                        "/code/image",
                        "/browserLogin.html",
                        securityProperties.getBrowser().getLoginPage())
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

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
