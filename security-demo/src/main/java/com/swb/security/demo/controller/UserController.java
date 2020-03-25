package com.swb.security.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.swb.security.demo.domain.User;
import com.swb.security.demo.domain.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author swb
 * 时间  2020-03-24 21:45
 * 文件  UserController
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> queryUser(UserQueryCondition queryCondition,
                                @PageableDefault Pageable pageable){
        System.out.println(ReflectionToStringBuilder.toString(queryCondition, ToStringStyle.MULTI_LINE_STYLE));

        List<User> list=new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }

    @GetMapping(value = "/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getUserInfo(@PathVariable(name ="id") String id){
        User user = new User();
        user.setUsername("冴岛大河");
        user.setPassword("password");
        user.setAge(51);
        return user;
    }

    @PostMapping
    @JsonView(User.UserDetailView.class)
    public User create(@Valid @RequestBody User user ){
        user.setId("1");
        return user;
    }


}