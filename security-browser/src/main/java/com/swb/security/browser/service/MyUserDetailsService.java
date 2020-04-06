package com.swb.security.browser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author swb
 * 时间  2020-03-29 16:00
 * 文件  MyUserDetailsService
 */
@Component(value = "myUserDetailsService")
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    /**
     * 注入持久层信息 例如 mybatis的mapper, SpringDataJPA的Repository
     */

    /**
     * 输入用户名，从数据库中获取数据，这里是模拟获取
     * User对象为org.springframework.security.core.userdetails包下的，实现了UserDetails接口
     * 参数，用户名，密码，权限List<GrantedAuthority>
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("MyUserDetailsService.loadUserByUsername");
        log.info("登录名为:"+username);
        String password = encoder.encode("123456");
        log.info("密码为:"+password);
        return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
