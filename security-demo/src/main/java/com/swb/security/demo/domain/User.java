package com.swb.security.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.swb.security.demo.validator.MyValidatorAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author swb
 * 时间  2020-03-24 21:50
 * 文件  User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonView(UserSimpleView.class)
    private String id;
    @MyValidatorAnnotation
    @JsonView(UserSimpleView.class)
    private String username;
    @NotBlank(message = "密码不能为空")
    @JsonView(UserDetailView.class)
    private String password;
    @JsonView(UserSimpleView.class)
    @Range(max = 100, min = 10, message = "年龄不在10-100之内")
    private int age;
    @JsonView(UserSimpleView.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss sss")
    private Date birthday;

    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }


}
