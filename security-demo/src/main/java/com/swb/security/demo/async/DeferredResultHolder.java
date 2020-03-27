package com.swb.security.demo.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author swb
 * 时间  2020-03-27 22:29
 * 文件  DeferredResultHolder
 */
@Component
@Data
public class DeferredResultHolder {

    /**
     * map中的String 是订单号
     * DeferredResult<String>是处理结果
     */
    private Map<String, DeferredResult<String>> map = new HashMap<String, DeferredResult<String>>();
}
