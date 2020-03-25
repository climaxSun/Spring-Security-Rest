package com.swb.security.demo.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author swb
 * 时间  2020-03-24 21:50
 * 文件  User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public interface UserSimpleView{};
    public interface UserDetailView extends  UserSimpleView{};

    @JsonView(UserSimpleView.class)
    private String username;
    @JsonView(UserDetailView.class)
    private String password;
    @JsonView(UserSimpleView.class)
    private int age;



}
