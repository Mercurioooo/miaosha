package com.pro.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pro.miaosha.domain.MiaoshaUser;
import com.pro.miaosha.domain.OrderInfo;
import com.pro.miaosha.vo.GoodsVo;

@Service
public class MiaoshaService {
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;

	@Transactional
	public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
		//减库存 
		goodsService.reduceStock(goods);
		//下订单 写入秒杀订单 order_info maiosha_order
		return orderService.createOrder(user, goods);
	}
	
}
