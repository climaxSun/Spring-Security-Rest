package com.swb.security.core.validate;

import com.swb.security.core.properties.ImageCodeProperties;
import com.swb.security.core.properties.SecurityProperties;
import com.swb.security.core.properties.SmsCodeProperties;
import com.swb.security.core.validate.code.ImageCode;
import com.swb.security.core.validate.code.ValidateCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.ServletRequest;

/**
 * @author swb
 * 时间  2020-04-18 16:20
 * 文件  ValidateCodeGenerator
 */
@Component(value = "validateCodeGenerator")
public class ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    public ImageCode generateImageCode(ServletRequest request){
        ImageCodeProperties codeProperties = securityProperties.getValidateCode().getImageCode();
        int width= ServletRequestUtils.getIntParameter(request,"width", codeProperties.getWidth());
        int height= ServletRequestUtils.getIntParameter(request,"height", codeProperties.getHeight());
        ImageCode imageCode = new ImageCode(width, height, codeProperties.getLength(),
                codeProperties.getInterfereCount(),
                codeProperties.getExpireIn());
        return imageCode;
    }

    public ValidateCode generateSmsCode(String mobile){
        SmsCodeProperties sms = securityProperties.getValidateCode().getSms();
        String code = RandomStringUtils.randomNumeric(sms.getLength());
        ValidateCode validateCode=new ValidateCode(code,mobile,sms.getExpireIn());
        return validateCode;
    }
}
