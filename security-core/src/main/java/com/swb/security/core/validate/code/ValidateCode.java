package com.swb.security.core.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;

/**
 * @author swb
 * 时间  2020-04-05 16:30
 * 文件  ImageCode
 */
@Data
public class ValidateCode {

    protected String code;

    protected LocalDateTime expireTime;

    protected String mobile;

    public boolean isExpried() {
        return LocalDateTime.now().compareTo(expireTime) >= 0;
    }

    public boolean verify(String codeRequest,String mobileRequest) {
        boolean flag=StringUtils.equals(mobile,mobileRequest);
        if(flag){
            flag=StringUtils.equals(code,codeRequest);
        }
        return flag;
    }

    protected boolean verify(String code){
        return StringUtils.equals(code,this.code);
    }

    public ValidateCode(String code,String mobile, int expire) {
        this.code = code;
        this.mobile = mobile;
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }
}
