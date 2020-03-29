package com.swb.security.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author swb
 * 时间  2020-03-26 22:44
 * 文件  TimeAspect
 */
@Aspect
@Component
@Slf4j
public class TimeAspect {

//    @Before //类似拦截器的preHandle
//    @After  //类似拦截器的postHandle
//    @AfterThrowing  //类似拦截器的afterCompletion


    /**
     * 包围，覆盖了前3种方式(RequestMapping包含GetMapping和PostMapping一样)定义了在什么方法上起作用，在什么时候起作用
     *
     * @param pjp 类似拦截器方法中的handler，包含拦截住的方法的相关信息
     * @return
     */
    @Around("execution(* com.swb.security.demo.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        log.info("TimeAspect.handleControllerMethod");
        Object[] args = pjp.getArgs();
        for (Object arg : args) {

        }
        //controller方法返回的对象
        Object o = pjp.proceed();
        return o;
    }
}
