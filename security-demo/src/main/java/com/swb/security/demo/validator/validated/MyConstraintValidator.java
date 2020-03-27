package com.swb.security.demo.validator.validated;

import com.swb.security.demo.validator.MyValidatorAnnotation;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author swb
 * 时间  2020-03-25 22:53
 * 文件  MyConstraintValidator
 */
@Slf4j
public class MyConstraintValidator implements ConstraintValidator<MyValidatorAnnotation, String> {

    //可以直接使用 @Autowired 注解，而不需要在类上加  @Component

    private String prefix;

    @Override
    public void initialize(MyValidatorAnnotation constraintAnnotation) {
        log.info("MyConstraintValidator.initialize");
        log.info("自定义注解校验类开始初始化");
        //这里获取注解的信息  例如Range的min， max。在本类中添加相关的字段max,min，然后获取constraintAnnotation信息，赋值
        prefix = constraintAnnotation.prefix();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("MyConstraintValidator.isValid");
        log.info("自定义注解开始校验");
        System.out.println(value);
        if ("".equals(prefix)) {
            return true;
        }
        if (value == null || "".equals(value)) {
            return false;
        }
        return value.indexOf(prefix) == 0;
    }
}
