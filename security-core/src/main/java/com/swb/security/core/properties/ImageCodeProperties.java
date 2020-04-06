package com.swb.security.core.properties;

import lombok.Data;

/**
 * @author swb
 * 时间  2020-04-06 16:57
 * 文件  ImageCodeProperties
 */
@Data
public class ImageCodeProperties {
    private int width=200;
    private int height=100;
    private int length=4;
    private int interfereCount=20;
    private int expireIn=600;
    private String url;
}
