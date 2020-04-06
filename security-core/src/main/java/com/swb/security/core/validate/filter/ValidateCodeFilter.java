package com.swb.security.core.validate.filter;

import com.swb.security.core.properties.SecurityProperties;
import com.swb.security.core.validate.AppConst;
import com.swb.security.core.validate.code.ImageCode;
import com.swb.security.core.validate.exception.ValidateCodeException;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author swb
 * 时间  2020-04-05 17:45
 * 文件  ValidateCodeFilter
 */
@Data
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AntPathMatcher pathMatcher=new AntPathMatcher();

    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String url = securityProperties.getValidateCode().getImageCode().getUrl();
        if(!StringUtils.isEmpty(url)) {
//          Collections.addAll(urls, url.split(","));
            urls = Stream.of(url.split(",")).collect(Collectors.toSet());
        }
        urls.add(AppConst.LOGIN_PATH);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        //是登陆的post请求
        if (urls.stream().anyMatch(s->pathMatcher.match(s,uri))) {
            try {
                validate(new ServletWebRequest(request));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) {
        ImageCode codeSession = (ImageCode) sessionStrategy.getAttribute(request, AppConst.SESSION_KEY);
        String codeRequest = ServletRequestUtils.getStringParameter(request.getRequest(), AppConst.CODE_NAME, "");
        if (codeRequest.isEmpty()) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (codeSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeSession.isExpried()) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!codeSession.verify(codeRequest)) {
            throw new ValidateCodeException("验证码错误");
        }
        sessionStrategy.removeAttribute(request, AppConst.SESSION_KEY);
    }
}
