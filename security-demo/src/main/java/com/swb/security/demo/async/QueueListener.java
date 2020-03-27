package com.swb.security.demo.async;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author swb
 * 时间  2020-03-27 22:51
 * 文件  QueueListener
 * 实现ApplicationListener接口,监听ContextRefreshedEvent(Spring 容器整个初始化完毕的事件)
 * 即当Spring容器初始化完毕后onApplicationEvent开始工作
 */

@Component
@Slf4j
@AllArgsConstructor
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private MockQueue mockQueue;

    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        new Thread(()->{
//            while (true){
//                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())){
//                    String orderNumber=mockQueue.getCompleteOrder();
//                    log.info("返回订单处理结果");
//                    //当调用setResult方法时，返回处理结果
//                    deferredResultHolder.getMap().get(orderNumber).setResult("order");
//                    mockQueue.setCompleteOrder(null);
//                }else{
//                    try {
//                        Thread.sleep(1000L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }
}
