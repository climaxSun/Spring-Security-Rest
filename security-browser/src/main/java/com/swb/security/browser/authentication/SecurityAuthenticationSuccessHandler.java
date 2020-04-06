package com.swb.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swb.security.core.properties.LoginType;
import com.swb.security.core.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author swb
 * 时间  2020-03-29 23:35
 * 文件  SecurityAuthenticationSuccessHandler
 */
@Component("swbSecurityAuthenticationSuccessHandler")
@Slf4j
@AllArgsConstructor
public class SecurityAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private ObjectMapper objectMapper;

    private SecurityProperties securityProperties;

    /**
     * @param request
     * @param response
     * @param authentication security核心接口,封装了一些认证信息(包括请求的ip,session,UserDetails)
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("SecurityAuthenticationSuccessHandler.onAuthenticationSuccess");
        log.info(authentication.getName()+"登录成功");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            //将authentication转化为json返回
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
