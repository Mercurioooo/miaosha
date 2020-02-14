package com.pro.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.miaosha.domain.MiaoshaUser;
import com.pro.miaosha.domain.OrderInfo;
import com.pro.miaosha.redis.RedisService;
import com.pro.miaosha.result.CodeMsg;
import com.pro.miaosha.result.Result;
import com.pro.miaosha.service.GoodsService;
import com.pro.miaosha.service.MiaoshaUserService;
import com.pro.miaosha.service.OrderService;
import com.pro.miaosha.vo.GoodsVo;
import com.pro.miaosha.vo.OrderDetailVo;

@Controller
//@RequestMapping("/order")
public class OrderController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	GoodsService goodsService;
	
    @RequestMapping("/order/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(MiaoshaUser user,
    		@RequestParam("orderId") long orderId) {
    	if(user == null) {
    		System.out.println("user=null");
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		System.out.println("order=null");
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
