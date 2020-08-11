package com.swb.security.browser.strategy;

import cn.hutool.json.JSONUtil;
import com.swb.security.browser.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>文件  BrowserSecuritySessionExpiredStrategy</p>
 * <p>时间  2020-08-11 17:19:00</p>
 *
 * @author swb
 */
@Slf4j
public class BrowserSecuritySessionExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        log.info("开始处理同一个用户不同机器多次登录逻辑");
        Object principal = event.getSessionInformation().getPrincipal();
        log.info(JSONUtil.toJsonStr(principal));
        log.info("逻辑处理结束");
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(
                JSONUtil.toJsonStr(new SimpleResponse("你的账号在别的地方登录，该设备已下线")));
    }
}
