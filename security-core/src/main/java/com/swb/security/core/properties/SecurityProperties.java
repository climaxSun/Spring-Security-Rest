package com.swb.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author swb
 * 时间  2020-03-29 21:58
 * 文件  SecurityProperties
 */
@Component
@ConfigurationProperties(prefix = "swb.security")
@Data
public class SecurityProperties {
    private BrowserProperties browser=new BrowserProperties();
}
