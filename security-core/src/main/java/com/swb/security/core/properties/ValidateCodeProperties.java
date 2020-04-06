package com.swb.security.core.properties;

import lombok.Data;

/**
 * @author swb
 * 时间  2020-04-06 17:00
 * 文件  ValidateCodeProperties
 */
@Data
public class ValidateCodeProperties {
    private ImageCodeProperties imageCode=new ImageCodeProperties();
}
