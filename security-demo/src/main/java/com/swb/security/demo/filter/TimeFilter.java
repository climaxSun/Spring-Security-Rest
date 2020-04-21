package com.swb.security.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author swb
 * 时间  2020-03-26 13:36
 * 文件  TimeFilter
 */
//@Component
@Slf4j
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TimeFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("TimeFilter.doFilter");
        log.info("start");
        long startTime = System.currentTimeMillis();
        String width= ServletRequestUtils.getStringParameter(request,"width","默认值");
        System.out.println(width);
        chain.doFilter(request, response);
        log.info("filter time:" + (System.currentTimeMillis() - startTime) + "ms");
        log.info("end");
    }

    @Override
    public void destroy() {
        log.info("TimeFilter.destroy");
    }
}
