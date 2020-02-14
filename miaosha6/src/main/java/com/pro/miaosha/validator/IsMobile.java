package com.pro.miaosha.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented
@Constraint(validatedBy = IsMobileValidator.class)//会去找ismobilexxx
public @interface IsMobile {
    String message() default "手机号码格式有误";  
    Class<?>[] groups() default {};  
    Class<? extends Payload>[] payload() default {};  
    boolean required() default true;  //不允许为空
}



