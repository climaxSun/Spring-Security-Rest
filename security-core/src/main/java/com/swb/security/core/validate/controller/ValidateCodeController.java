package com.swb.security.core.validate.controller;

import com.swb.security.core.properties.ImageCodeProperties;
import com.swb.security.core.properties.SecurityProperties;
import com.swb.security.core.validate.AppConst;
import com.swb.security.core.validate.code.ImageCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private SecurityProperties securityProperties;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCodeProperties codeProperties = securityProperties.getValidateCode().getImageCode();
        int width= ServletRequestUtils.getIntParameter(request,"width", codeProperties.getWidth());
        int height= ServletRequestUtils.getIntParameter(request,"height", codeProperties.getHeight());
        ImageCode imageCode = new ImageCode(width, height, codeProperties.getLength(),
                codeProperties.getInterfereCount(),
                codeProperties.getExpireIn());
        sessionStrategy.setAttribute(new ServletWebRequest(request), AppConst.SESSION_KEY, imageCode);
        imageCode.write(response.getOutputStream());
    }

}
