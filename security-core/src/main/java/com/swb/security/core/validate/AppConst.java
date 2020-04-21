package com.swb.security.core.validate;

/**
 * @author swb
 * 时间  2020-04-05 18:02
 * 文件  AppConst
 */
public class AppConst {

    public static final String LOGIN_PATH = "/authentication/form";
    public static final String MOBILE_LOGIN_PATH = "/authentication/mobile";
    public static final String SESSION_KEY_PREFIX = "SESSION_KEY";
    public static final String IMAGE_CODE = SESSION_KEY_PREFIX + "_IMAGE_CODE";
    public static final String IMAGE_CODE_NAME = "imageCode";
    public static final String SMS_CODE = SESSION_KEY_PREFIX + "_SMS_CODE";
    public static final String SMS_CODE_NAME = "smsCode";
    public static final String MOBILE_NAME = "mobile";
}
