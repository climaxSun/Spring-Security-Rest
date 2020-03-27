package com.swb.security.demo.async;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author swb
 * 时间  2020-03-27 22:18
 * 文件  MockQueue
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Component
public class MockQueue {

    private String placeOrder;

    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            log.info("接到下单请求:" + placeOrder);
            try {
                Thread.sleep(1000L);
                this.completeOrder = placeOrder;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("下单请求:" + placeOrder + "处理完毕");
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
