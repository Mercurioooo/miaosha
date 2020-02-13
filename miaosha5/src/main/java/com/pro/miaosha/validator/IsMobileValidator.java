package com.pro.miaosha.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.alibaba.druid.util.StringUtils;
import com.pro.miaosha.util.ValidatorUtil;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{
	private boolean required = false;

	public void initialize(IsMobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (required) {//必须的
			return ValidatorUtil.isMobile(value);
		}
		else {//没有也可以
			if (StringUtils.isEmpty(value)) {
				return true;//没有没关系
			} else {
				return ValidatorUtil.isMobile(value);//只要有了就一定要是真的
			}
		}
	}
}
