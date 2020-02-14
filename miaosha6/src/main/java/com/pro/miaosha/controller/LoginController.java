package com.pro.miaosha.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.pro.miaosha.domain.User;
import com.pro.miaosha.redis.RedisService;
import com.pro.miaosha.redis.UserKey;
import com.pro.miaosha.result.CodeMsg;
import com.pro.miaosha.result.Result;
import com.pro.miaosha.service.MiaoshaUserService;
import com.pro.miaosha.util.ValidatorUtil;
import com.pro.miaosha.vo.LoginVo;


@Controller
@RequestMapping("/login")
public class LoginController {
	//要用sl4j的
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	MiaoshaUserService userService;
	@Autowired
	RedisService redisService;
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }
    @RequestMapping("/do_login")
    @ResponseBody
  //这里自动加上了参数校验,后面就不用了
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
    	System.out.println(loginVo.toString());
    	log.info(loginVo.toString());
    	userService.login(response, loginVo);
    	return Result.success(true);
    	
    }
	
}
