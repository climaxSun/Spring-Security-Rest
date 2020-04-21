package com.swb.security.core.validate;

import com.swb.security.core.validate.code.ImageCode;
import com.swb.security.core.validate.code.ValidateCode;
import com.swb.security.core.validate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author swb
 * 时间  2020-04-05 16:38
 * 文件  ValidataCodeController
 */
@RestController
public class ValidateCodeController {

    @Autowired
    @Qualifier(value = "validateCodeGenerator")
    private ValidateCodeGenerator validateCodeGenerator;

    @Autowired
    @Qualifier(value = "smsCodeSender")
    private SmsCodeSender smsCodeSender;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode= validateCodeGenerator.generateImageCode(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request), AppConst.IMAGE_CODE, imageCode);
        imageCode.write(response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public String createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        ValidateCode smsCode = validateCodeGenerator.generateSmsCode(mobile);
        sessionStrategy.setAttribute(new ServletWebRequest(request), AppConst.SMS_CODE, smsCode);
        smsCodeSender.send(mobile,smsCode.getCode());
        return smsCode.getCode();
    }

}
