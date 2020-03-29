package com.swb.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swb.security.core.properties.LoginType;
import com.swb.security.core.properties.SecurityProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author swb
 * 时间  2020-03-30 00:09
 * 文件  SecurityAuthenticationFailureHandler
 */
@Component("swbSecurityAuthenticationFailureHandler")
@Slf4j
@AllArgsConstructor
public class SecurityAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private ObjectMapper objectMapper;

    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("SecurityAuthenticationFailureHandler.onAuthenticationFailure:登录失败");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            //将authentication转化为json返回
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(exception));
        }else{
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
