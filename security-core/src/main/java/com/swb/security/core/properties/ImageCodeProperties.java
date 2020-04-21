package com.swb.security.core.properties;

import lombok.Data;

/**
 * @author swb
 * 时间  2020-04-06 16:57
 * 文件  ImageCodeProperties
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties {
    private int width=200;
    private int height=100;
    private int interfereCount=20;
    public ImageCodeProperties(){
        setLength(4);
    }
}
