package com.swb.security.core.validate.sms;

/**
 * @author swb
 * 时间  2020-04-18 16:46
 * 文件  SmsCodeSender
 */
public interface SmsCodeSender {

    void send(String mobile,String code);
}
