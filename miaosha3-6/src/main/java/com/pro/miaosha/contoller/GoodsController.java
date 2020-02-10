package com.pro.miaosha.contoller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.util.StringUtils;
import com.pro.miaosha.domain.MiaoshaUser;
import com.pro.miaosha.redis.RedisService;
import com.pro.miaosha.service.MiaoshaUserService;
import com.pro.miaosha.service.UserService;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	MiaoshaUserService miaoshaUserService;
	@Autowired
	RedisService redisService;

	@RequestMapping("to_list")
	public String toLogin(HttpServletResponse response,Model model,
			@CookieValue(value=MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,//token放到cookie中
			@RequestParam(value=MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String paramToken//token放到request的参数中
			){
		if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)) {
			return "login";
		}
		//优先param
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		MiaoshaUser user = miaoshaUserService.getByToken(response,token);
		model.addAttribute("user",user);
		return "goods_list";
	}
}
