package com.swb.security.core.properties;

import lombok.Data;

/**
 * @author swb
 * 时间  2020-04-06 16:57
 * 文件  ImageCodeProperties
 */
@Data
public class SmsCodeProperties {
    private int length=6;
    private int expireIn=600;
    private String url;
}
