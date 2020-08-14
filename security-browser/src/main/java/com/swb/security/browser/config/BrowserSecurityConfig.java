package com.swb.security.browser.config;

import com.swb.security.browser.logout.BrowserLogoutSuccessHandler;
import com.swb.security.browser.strategy.BrowserSecuritySessionExpiredStrategy;
import com.swb.security.core.authentication.AbstractChannelSecurityConfig;
import com.swb.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.swb.security.core.properties.SecurityConstants;
import com.swb.security.core.properties.SecurityProperties;
import com.swb.security.core.validate.filter.SmsCodeFilter;
import com.swb.security.core.validate.filter.ValidateCodeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    @Qualifier("swbSecurityAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    @Qualifier("swbSecurityAuthenticationFailureHandler")
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("BrowserSecurityConfig.configure");
        applyPasswordAuthenticationConfig(http);
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(failureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(failureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();
//      指定身份认证的方式 表单登录
        http.apply(smsCodeAuthenticationSecurityConfig)
                //配置记住我
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
//        basic方式认证
//        http.httpBasic()
                .and()
                .sessionManagement()
                .invalidSessionUrl(SecurityConstants.DEFAULT_SESSION_INVALID_URL)
                //最大数量
                .maximumSessions(1)
                //达到最大登录数时，不允许登录
                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(new BrowserSecuritySessionExpiredStrategy())
                .and()
                .and()
                //退出
                .logout()
                //指定退出的路径
//                .logoutUrl("/signOut")
                //退出成功后跳转的url
//                .logoutSuccessUrl(SecurityConstants.DEFAULT_LOGIN_PAGE_URL)
                //指定退出成功的处理
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "SESSION")
                .and()
//                开始请求权限配置
                .authorizeRequests()
                //当前路径不需要认证
                .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        SecurityConstants.DEFAULT_LOGIN_PAGE_URL,
                        SecurityConstants.DEFAULT_SESSION_INVALID_URL,
                        SecurityConstants.FILE_TEST,
                        SecurityConstants.TEST,
                        securityProperties.getBrowser().getLoginPage())
                .permitAll()
//                下面2行是对http所有的请求必须通过授权认证才可以访问
//                对任何请求
                .anyRequest()
//                都需要身份验证
                .authenticated()
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 BCrypt 加密
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
