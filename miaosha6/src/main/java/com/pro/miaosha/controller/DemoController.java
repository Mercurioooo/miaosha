package com.pro.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.miaosha.domain.User;
import com.pro.miaosha.rabbitmq.MQSender;
import com.pro.miaosha.redis.RedisService;
import com.pro.miaosha.redis.UserKey;
import com.pro.miaosha.result.CodeMsg;
import com.pro.miaosha.result.Result;
import com.pro.miaosha.service.UserService;
import org.springframework.amqp.core.Queue;


@Controller

public class DemoController {
	@Autowired
	UserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	MQSender sender;
	
	@RequestMapping("/hello")
	@ResponseBody
	public Result<String> hello(){
		return Result.success("hello,ljn");
	}
	
	@RequestMapping("/helloError")
	@ResponseBody
	public Result<String> helloError(){
		return Result.error(CodeMsg.SERVER_ERROR);
	}
	
	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model){
		model.addAttribute("name","ljn");
		return "hello";//返回配置文件中的/templates/hello.html文件
	}
	
	@RequestMapping("/db/get")
	@ResponseBody
	public Result<User> dbGet(){
		User user = userService.getById(1);
		return Result.success(user);
	}
	
	@RequestMapping("/db/tx")
	@ResponseBody
	//肯定会报错,因为不可以重复嘛
	public Result<Boolean> dbTx(){
		userService.tx();
		return Result.success(true);
	}
	
	/*
	@RequestMapping("/mq")
	@ResponseBody
	public Result<String> mq(){
		sender.send("helloiloveu");
		return Result.success("helloworld");
	}
	
	@RequestMapping("/topic")
	@ResponseBody
	public Result<String> mqTopic(){
		sender.sendTopic("valentine");
		return Result.success("helloworld");
	}
	
	
	@RequestMapping("/fanout")
	@ResponseBody
	public Result<String> mqFanout(){
		sender.sendFanout("valentine");
		return Result.success("helloworld");
	}
	
	
	@RequestMapping("/header")
	@ResponseBody
	public Result<String> mqHeader(){
		sender.sendHeader("valentine");
		return Result.success("helloworld");
	}
	*/

////	@RequestMapping("/redis/get")
////	@ResponseBody
////	public Result<Long> redisGet(){
//////		Long value1 = redisService.get("key1",Long.class);
//////		return Result.success(value1);
////	}
//	
////	@RequestMapping("/redis/set")
////	@ResponseBody
////	public Result<Boolean> redisSet(){
////		User user = new User(1,"1111");
////		redisService.set(UserKey.getById,""+1, user);
////		return Result.success(true);
////	}
////	
////	@RequestMapping("/redis/set")
////	@ResponseBody
////	public Queue queue(){
////		return new org.springframework.amqp.core.Queue(QUEUE,true);
////	}
	
}
