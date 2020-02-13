package com.pro.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.miaosha.domain.MiaoshaOrder;
import com.pro.miaosha.domain.MiaoshaUser;
import com.pro.miaosha.domain.OrderInfo;
import com.pro.miaosha.redis.RedisService;
import com.pro.miaosha.result.CodeMsg;
import com.pro.miaosha.result.Result;
import com.pro.miaosha.service.GoodsService;
import com.pro.miaosha.service.MiaoshaService;
import com.pro.miaosha.service.MiaoshaUserService;
import com.pro.miaosha.service.OrderService;
import com.pro.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MiaoshaService miaoshaService;

//    @RequestMapping("/do_miaosha")
//    public String list(Model model,MiaoshaUser user,
//    		@RequestParam("goodsId")long goodsId) {
//    	model.addAttribute("user", user);
//    	if(user == null) {
//    		return "login";
//    	}
//    	//判断库存
//    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//    	int stock = goods.getStockCount();
//    	if(stock <= 0) {
//    		model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
//    		return "miaosha_fail";
//    	}
//    	//判断是否已经秒杀到了
//    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//    	if(order != null) {
//    		model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
//    		return "miaosha_fail";
//    	}
//    	//减库存 下订单 写入秒杀订单
//    	OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
//        
//    	model.addAttribute("orderInfo", orderInfo);
//    	model.addAttribute("goods", goods);
//        return "order_detail";
//    }
	@ResponseBody
	@RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
	public Result<OrderInfo> list(//Model model,
			MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		// 判断库存
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if (stock <= 0) {
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		// 判断是否已经秒杀到了
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
		if (order != null) {
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}
		// 减库存 下订单 写入秒杀订单
		OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
		return Result.success(orderInfo);
	}
}
