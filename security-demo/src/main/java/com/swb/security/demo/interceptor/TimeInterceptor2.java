package com.swb.security.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author swb
 * 时间  2020-03-26 14:57
 * 文件  TimeInterceptor
 */
@Slf4j
@Component
public class TimeInterceptor2 implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //controller处理前调用的方法   返回false不执行controller，返回true执行
        log.info("TimeInterceptor2.preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("TimeInterceptor2.postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("TimeInterceptor2.afterCompletion");
        log.info("ex is =" + ex);
    }
}
