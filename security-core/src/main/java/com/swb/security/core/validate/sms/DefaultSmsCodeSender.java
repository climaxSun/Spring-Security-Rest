package com.swb.security.core.validate.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * @author swb
 * 时间  2020-04-18 16:48
 * 文件  DefaultSmsCodeSender
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender{

    @Override
    public void send(String mobile, String code) {
        log.info("向手机"+mobile+"发送验证码:"+code);
    }
}
