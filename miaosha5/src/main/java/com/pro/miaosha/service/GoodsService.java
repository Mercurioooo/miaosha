package com.pro.miaosha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.miaosha.dao.GoodsDao;
import com.pro.miaosha.domain.MiaoshaGoods;
import com.pro.miaosha.vo.GoodsVo;

@Service
public class GoodsService {
	
	@Autowired
	GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	public void reduceStock(GoodsVo goods) {
		//减库存 
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		goodsDao.reduceStock(g);
	}
	
	
	
}
