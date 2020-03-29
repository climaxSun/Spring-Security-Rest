package com.swb.security.core.properties;

import lombok.Data;

/**
 * @author swb
 * 时间  2020-03-29 21:59
 * 文件  BrowserProperties
 */
@Data
public class BrowserProperties {
    private String loginPage = "/browserLogin.html";

    private LoginType loginType = LoginType.JSON;
}
