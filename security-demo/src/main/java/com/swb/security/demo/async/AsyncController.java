package com.swb.security.demo.async;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @author swb
 * 时间  2020-03-27 21:55
 * 文件  AsyncController
 */
@RestController
@Slf4j
@AllArgsConstructor
public class AsyncController {

    private MockQueue mockQueue;

    private DeferredResultHolder deferredResultHolder;

    @GetMapping("/order")
    public Callable<String> order() {
        log.info("主线程开始");
        Callable<String> callable = () -> {
            log.info("副线程开始");
            Thread.sleep(1000L);
            log.info("副线程结束");
            return "order";
        };
        log.info("主线程结束");
        return callable;
    }

    @GetMapping("/order2")
    public DeferredResult<String> order2() {
        log.info("主线程开始");
        String orderNumber = RandomStringUtils.randomNumeric(10);
        mockQueue.setPlaceOrder(orderNumber);
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);
        log.info("主线程结束");
        return result;
    }
}
