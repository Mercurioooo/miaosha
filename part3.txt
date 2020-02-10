1.秒杀数据库设计
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品的图片',
  `goods_detail` longtext COMMENT '商品的详情介绍',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

INSERT INTO `goods` VALUES (1,'iphoneX','Apple iPhone X (A1865) 64GB 银色 移动联通电信4G手机','/img/iphonex.png','Apple iPhone X (A1865) 64GB 银色 移动联通电信4G手机',8765.00,10000),(2,'华为Meta9','华为 Mate 9 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待','/img/meta10.png','华为 Mate 9 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待',3212.00,-1);

CREATE TABLE `miaosha_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品Id',
  `miaosha_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

INSERT INTO `miaosha_goods` VALUES (1,1,0.01,4,'2017-11-05 15:18:00','2017-11-13 14:00:18'),(2,2,0.01,9,'2017-11-12 14:00:14','2017-11-13 14:00:24');


CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收获地址ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `order_channel` tinyint(4) DEFAULT '0' COMMENT '1pc，2android，3ios',
  `status` tinyint(4) DEFAULT '0' COMMENT '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `miaosha_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

public class Goods {
	private Long id;
	private String goodsName;
	private String goodsTitle;
	private String goodsImg;
	private String goodsDetail;
	private Double goodsPrice;
	private Integer goodsStock;
}
public class MiaoshaGoods {
	private Long id;
	private Long goodsId;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;
}
public class OrderInfo {
	private Long id;
	private Long userId;
	private Long goodsId;
	private Long  deliveryAddrId;
	private String goodsName;
	private Integer goodsCount;
	private Double goodsPrice;
	private Integer orderChannel;
	private Integer status;
	private Date createDate;
	private Date payDate;
}
public class MiaoshaOrder {
	private Long id;
	private Long userId;
	private Long  orderId;
	private Long goodsId;
}

public class GoodsVo extends Goods{

	private Double miaoshaPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;
}


2.商品列表
<div class="panel panel-default">
  <div class="panel-heading">秒杀商品列表</div>
  <table class="table" id="goodslist">
  	<tr><td>商品名称</td><td>商品图片</td><td>商品原价</td><td>秒杀价</td><td>库存数量</td><td>详情</td></tr>
  	<tr  th:each="goods,goodsStat : ${goodsList}">  
                <td th:text="${goods.goodsName}"></td>  
                <td ><img th:src="@{${goods.goodsImg}}" width="100" height="100" /></td>  
                <td th:text="${goods.goodsPrice}"></td>  
                <td th:text="${goods.miaoshaPrice}"></td>  
                <td th:text="${goods.stockCount}"></td>
                <td><a th:href="'/goods/to_detail/'+${goods.id}">详情</a></td>  
     </tr>  
  </table>
</div>
3.商品详情
<div class="panel panel-default">
  <div class="panel-heading">秒杀商品详情</div>
  <div class="panel-body">
  	<span> 您还没有登录，请登陆后再操作<br/></span>
  	<span>没有收货地址的提示。。。</span>
  </div>
  <table class="table" id="goodslist">
  	<tr>  
        <td>商品名称</td>  
        <td colspan="3"></td> 
     </tr>  
     <tr>  
        <td>商品图片</td>  
        <td colspan="3"><img th:src="" width="200" height="200" /></td>  
     </tr>
     <tr>  
        <td>秒杀开始时间</td>  
        <td ></td>
        <td id="miaoshaTip">	
        	<span>秒杀已经结束</span>
        </td>
        <td>
        	<form id="miaoshaForm" method="post" action="/miaosha/do_miaosha">
        		<button class="btn btn-primary btn-block" type="submit" id="buyButton">立即秒杀</button>
        		<input type="hidden" name="goodsId" th:value="${goods.id}" />
        	</form>
        </td>
     </tr>
     <tr>  
        <td>商品原价</td>  
        <td colspan="3"></td>  
     </tr>
      <tr>  
        <td>秒杀价</td>  
        <td colspan="3"></td>  
     </tr>
     <tr>  
        <td>库存数量</td>  
        <td colspan="3"></td>  
     </tr>
  </table>
</div>

<td th:text="${#dates.format(goods.startDate, ''yyyy-MM-dd HH:mm:ss)}"></td>

4.订单详情

<div class="panel panel-default">
  <div class="panel-heading">秒杀订单详情</div>
  <table class="table" id="goodslist">
        <tr>  
        <td>商品名称</td>  
        <td th:text="" colspan="3"></td> 
     </tr>  
     <tr>  
        <td>商品图片</td>  
        <td colspan="2"><img th:src="@{}" width="200" height="200" /></td>  
     </tr>
      <tr>  
        <td>订单价格</td>  
        <td colspan="2"></td>  
     </tr>
     <tr>
     	<td>下单时间</td>  
        	<td th:text="" colspan="2"></td>  
     </tr>
     <tr>
     	<td>订单状态</td>  
        <td>
        </td>  
        <td>
        	<button class="btn btn-primary btn-block" type="submit" id="payButton">立即支付</button>
        </td>
     </tr>
      <tr>
     	<td>收货人</td>  
        	<td colspan="2">XXX  18812341234</td>  
     </tr>
     <tr>
     	<td>收货地址</td>  
        	<td colspan="2">北京市昌平区回龙观龙博一区</td>  
     </tr>
  </table>
</div>

<td th:text="${#dates.format(orderInfo.createDate, 'yyyy-MM-dd HH:mm:ss')}" colspan="2"></td>  