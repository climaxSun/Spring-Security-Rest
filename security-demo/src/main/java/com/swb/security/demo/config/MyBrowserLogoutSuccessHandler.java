package com.swb.security.demo.config;

import cn.hutool.json.JSONUtil;
import com.swb.security.browser.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>文件  BrowserLogoutSuccessHandler</p>
 * <p>时间  2020-08-13 14:00:35</p>
 *
 * @author swb
 */
@Slf4j
@Component(value = "logoutSuccessHandler")
public class MyBrowserLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("MyBrowserLogoutSuccessHandler.onLogoutSuccess");
        log.info("退出登录");
        log.info(JSONUtil.toJsonStr(authentication));
        UsernamePasswordAuthenticationToken token=(UsernamePasswordAuthenticationToken)authentication;
//        token.setAuthenticated(false);
//        token.setDetails(null);
        token.eraseCredentials();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONUtil.toJsonStr(new SimpleResponse("退出登录")));
    }
}
