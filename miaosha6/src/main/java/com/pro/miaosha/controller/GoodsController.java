package com.pro.miaosha.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.alibaba.druid.util.StringUtils;
import com.pro.miaosha.domain.MiaoshaUser;
import com.pro.miaosha.redis.GoodsKey;
import com.pro.miaosha.redis.RedisService;
import com.pro.miaosha.result.Result;
import com.pro.miaosha.service.GoodsService;
import com.pro.miaosha.service.MiaoshaUserService;
import com.pro.miaosha.vo.GoodsDetailVo;
import com.pro.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	MiaoshaUserService miaoshaUserService;
	
	@Autowired
	RedisService redisService;

	@Autowired
	GoodsService goodsService;
	
	@Autowired
	ThymeleafViewResolver thymeleafViewResolver;

	
	@RequestMapping(value="to_list",produces="text/html")
	@ResponseBody
	public String list(Model model,MiaoshaUser user,HttpServletRequest request,HttpServletResponse response){
		model.addAttribute("user",user);
		List<GoodsVo> listGoodsVo = goodsService.listGoodsVo();
		model.addAttribute("goodsList",listGoodsVo);
		//return "goods_list";
		
		//从缓存里面先取出来
	    String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
	    //如果不为空就直接返回
			if(!StringUtils.isEmpty(html)){
					return html;
	    }
	    //否则需要手动渲染
			
		WebContext ctx = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap()); 
		//String goods_list_html = thymeleafViewResolver.getTemplateEngine().process("/email/VerifyCode.html", ctx); 
	    //SpringWebContext ctx = new SpringWebContext(request, response, request. getServletContext(),request.getLocale(), model.asMap(), applicationContext);
	    html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
	    if(!StringUtils.isEmpty(html)){
	      redisService.set(GoodsKey.getGoodsList,"",html);
	    }
	    return html;
	}
//	
//	@RequestMapping("to_list")
//	public String toLogin(Model model,MiaoshaUser user){
//		model.addAttribute("user",user);
//		return "goods_list";
//	}
//	
//	//现在
//	@ResponseBody
//	@RequestMapping(value = "/to_detail/{goodsId}")
//	// String -> Result<GoodDetailVo>
//	public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model,MiaoshaUser user,
//    		@PathVariable("goodsId")long goodsId) {
//		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//		long startAt = goods.getStartDate().getTime();
//		long endAt = goods.getEndDate().getTime();
//		long now = System.currentTimeMillis();
//		int miaoshaStatus = 0;
//		int remainSeconds = 0;
//
//		if (now < startAt) {
//			miaoshaStatus = 0;
//			remainSeconds = (int) ((startAt - now) / 1000);
//		} else if (now > endAt) {
//			miaoshaStatus = 2;
//			remainSeconds = -1;
//		} else {
//			miaoshaStatus = 1;
//			remainSeconds = 0;
//		}
//		// 增加这一部分
//		GoodsDetailVo vo = new GoodsDetailVo();
//		vo.setGoods(goods);
//		vo.setUser(user);
//		vo.setRemainSeconds(remainSeconds);
//		vo.setMiaoshaStatus(miaoshaStatus);
//
//		return Result.success(vo);
//	}
	@RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model,MiaoshaUser user,
    		@PathVariable("goodsId")long goodsId) {
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	long startAt = goods.getStartDate().getTime();
    	long endAt = goods.getEndDate().getTime();
    	long now = System.currentTimeMillis();
    	int miaoshaStatus = 0;
    	int remainSeconds = 0;
    	if(now < startAt ) {//秒杀还没开始，倒计时
    		miaoshaStatus = 0;
    		remainSeconds = (int)((startAt - now )/1000);
    	}else  if(now > endAt){//秒杀已经结束
    		miaoshaStatus = 2;
    		remainSeconds = -1;
    	}else {//秒杀进行中
    		miaoshaStatus = 1;
    		remainSeconds = 0;
    	}
    	GoodsDetailVo vo = new GoodsDetailVo();
    	vo.setGoods(goods);
    	vo.setUser(user);
    	vo.setRemainSeconds(remainSeconds);
    	vo.setMiaoshaStatus(miaoshaStatus);
    	return Result.success(vo);
    }
}
	
















