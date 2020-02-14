package com.pro.miaosha.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.druid.util.StringUtils;
import com.pro.miaosha.domain.MiaoshaUser;
import com.pro.miaosha.redis.MiaoshaUserKey;
import com.pro.miaosha.service.MiaoshaUserService;

@Service //因为里面需要MiaoshaUserService
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

	@Autowired
	MiaoshaUserService miaoshaUserService;
	
	//如果参数是支持的类型返回true;
	public boolean supportsParameter(MethodParameter parameter) {
		//判断参数类型是不是秒杀user,如果是返回true
		Class<?> clazz = parameter.getParameterType();
		return clazz == MiaoshaUser.class;
	}

	
	//通过cookie/request中的token获取到user
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		//首先拿到response和request
		HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse nativeResponse = webRequest.getNativeResponse(HttpServletResponse.class);
		//从request中拿到token
		String paramToken = nativeRequest.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
		//或从request的cookie中拿到token
		String cookieToken = getCookieValue(nativeRequest,MiaoshaUserService.COOKIE_NAME_TOKEN);
		if(StringUtils.isEmpty(paramToken)&&StringUtils.isEmpty(cookieToken)) {
			return null;
		}
		//token二选一
		String tokenString = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		//通过token获取user
		return miaoshaUserService.getByToken(nativeResponse, tokenString);
	}
	private String getCookieValue(HttpServletRequest request,String name) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(name))
				return cookie.getValue();
		}
		return null;
	}
	
}
