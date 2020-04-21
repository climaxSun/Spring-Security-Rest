package com.swb.security.core.authentication.mobile;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author swb
 * 时间  2020-04-19 20:53
 * 文件  SmsCodeAuthenticationProvider
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 身份验证逻辑
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken= (SmsCodeAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        //将电话传入方法获取用户信息
        UserDetails user=userDetailsService.loadUserByUsername("逢魔Zi-O手机号："+mobile);
        if (user==null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        SmsCodeAuthenticationToken resultAuthenticationToken=new SmsCodeAuthenticationToken(user,user.getAuthorities());
        resultAuthenticationToken.setDetails(resultAuthenticationToken.getDetails());
        return resultAuthenticationToken;
    }

    /**
     * AuthenticationManager判断执行那个provider
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        //判断传进来的是否是SmsCodeAuthenticationToken类型
        //SmsCodeAuthenticationToken 继承 AbstractAuthenticationToken 实现 Authentication
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
