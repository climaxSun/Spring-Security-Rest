package com.swb.security.core.validate;

import com.swb.security.core.validate.sms.DefaultSmsCodeSender;
import com.swb.security.core.validate.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author swb
 * 时间  2020-04-18 17:09
 * 文件  ValidateCodeConfig
 */
@Configuration
public class ValidateCodeConfig {

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "smsCodeSender")
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
