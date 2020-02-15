# 秒杀系统的实现

## 介绍

Java实现的秒杀网站，基于Spring Boot 2.X。

## 技术栈

- SpringBoot2.0.1 + MyBatis + MySQL + Durid
- Redis + RabbitMQ
- BootStrap + Thymeleaf
- JSR303参数校验 + 全局异常处理器
- 分布式Session

## QuickStart

- clone源码

```
git clone git@github.com:Mercurioooo/miaosha.git
```

- 在Intelij IDEA/eclipse里导入根路径下的pom.xml，再导入文件夹jseckill-backend下面的pom.xml, 等待maven依赖下载完毕 详细操作：

**如果是IDEA**，先IDEA | File | Open...，选择jseckill根路径下的pom文件, Open as project以导入根项目jseckill。

操作菜单栏 View | Tool Windows | Maven Projects。 点击"+"， 添加jseckill-backend下面的pom。

此时Maven Projects下面有根项目jseckill和jseckill-backend。

**如果是Eclipse**, import导入maven项目，勾选jseckil和jseckill-backend下面共两个pom文件即可。

- 修改application.properties里面的自己的Redis,MySQL,Zookeeper,RabbitMQ的连接配置
- 右键JseckillBackendApp.java--run as--Java Application

## 原理

1. 页面缓存 + URL缓存 + 对象缓存，减少数据库的访问
2. 页面静态化，前后端分离
3. 静态资源优化 **秒杀接口优化**
4. RabbitMQ队列缓冲异步下单
5. Redis预减库存
6. 内存标记 **安全优化**
7. 秒杀地址隐藏
8. 数学公式验证码缓冲
9. 接口的防刷限流

## 具体的技术点

1. [商城秒杀系统的实现(一)搭建项目框架](https://editor.csdn.net/md/?articleId=104331179)
2. [商城秒杀系统的实现(二)实现登录功能](https://editor.csdn.net/md/?articleId=104331322)
3. [商城秒杀系统的实现(三)秒杀功能开发及管理后台](https://editor.csdn.net/md/?articleId=104332117)
4. [商城秒杀系统的实现(四)页面级高并发秒杀优化](https://editor.csdn.net/md/?articleId=104333300)
5. [商城秒杀系统的实现(五)服务级高并发秒杀优化](https://editor.csdn.net/md/?articleId=104332912)

## 目录
```
miaosha6
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── pro
│   │   │           └── miaosha
│   │   │               ├── MainApplication.java
│   │   │               ├── config
│   │   │               │   ├── UserArgumentResolver.java
│   │   │               │   └── WebConfig.java
│   │   │               ├── controller
│   │   │               │   ├── DemoController.java
│   │   │               │   ├── GoodsController.java
│   │   │               │   ├── LoginController.java
│   │   │               │   ├── MiaoshaController.java
│   │   │               │   └── OrderController.java
│   │   │               ├── dao
│   │   │               │   ├── GoodsDao.java
│   │   │               │   ├── MiaoshaUserDao.java
│   │   │               │   ├── OrderDao.java
│   │   │               │   └── UserDao.java
│   │   │               ├── domain
│   │   │               │   ├── Goods.java
│   │   │               │   ├── MiaoshaGoods.java
│   │   │               │   ├── MiaoshaOrder.java
│   │   │               │   ├── MiaoshaUser.java
│   │   │               │   ├── OrderInfo.java
│   │   │               │   └── User.java
│   │   │               ├── exception
│   │   │               │   ├── GlobalException.java
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               ├── rabbitmq
│   │   │               │   ├── MQConfig.java
│   │   │               │   ├── MQReceiver.java
│   │   │               │   ├── MQSender.java
│   │   │               │   └── MiaoshaMessage.java
│   │   │               ├── redis
│   │   │               │   ├── BasePrefix.java
│   │   │               │   ├── GoodsKey.java
│   │   │               │   ├── KeyPrefix.java
│   │   │               │   ├── MiaoshaKey.java
│   │   │               │   ├── MiaoshaUserKey.java
│   │   │               │   ├── OrderKey.java
│   │   │               │   ├── RedisConfig.java
│   │   │               │   ├── RedisPoolFactory.java
│   │   │               │   ├── RedisService.java
│   │   │               │   └── UserKey.java
│   │   │               ├── result
│   │   │               │   ├── CodeMsg.java
│   │   │               │   └── Result.java
│   │   │               ├── service
│   │   │               │   ├── GoodsService.java
│   │   │               │   ├── MiaoshaService.java
│   │   │               │   ├── MiaoshaUserService.java
│   │   │               │   ├── OrderService.java
│   │   │               │   └── UserService.java
│   │   │               ├── util
│   │   │               │   ├── MD5Util.java
│   │   │               │   ├── UUIDUtil.java
│   │   │               │   └── ValidatorUtil.java
│   │   │               ├── validator
│   │   │               │   ├── IsMobile.java
│   │   │               │   └── IsMobileValidator.java
│   │   │               └── vo
│   │   │                   ├── GoodsDetailVo.java
│   │   │                   ├── GoodsVo.java
│   │   │                   ├── LoginVo.java
│   │   │                   └── OrderDetailVo.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       │   ├── bootstrap
│   │       │   │   ├── css
│   │       │   │   │   ├── bootstrap-theme.css
│   │       │   │   │   ├── bootstrap-theme.css.map
│   │       │   │   │   ├── bootstrap-theme.min.css
│   │       │   │   │   ├── bootstrap-theme.min.css.map
│   │       │   │   │   ├── bootstrap.css
│   │       │   │   │   ├── bootstrap.css.map
│   │       │   │   │   ├── bootstrap.min.css
│   │       │   │   │   └── bootstrap.min.css.map
│   │       │   │   ├── fonts
│   │       │   │   │   ├── glyphicons-halflings-regular.eot
│   │       │   │   │   ├── glyphicons-halflings-regular.svg
│   │       │   │   │   ├── glyphicons-halflings-regular.ttf
│   │       │   │   │   ├── glyphicons-halflings-regular.woff
│   │       │   │   │   └── glyphicons-halflings-regular.woff2
│   │       │   │   └── js
│   │       │   │       ├── bootstrap.js
│   │       │   │       ├── bootstrap.min.js
│   │       │   │       └── npm.js
│   │       │   ├── goods_detail.htm
│   │       │   ├── img
│   │       │   │   ├── iphonex.png
│   │       │   │   └── meta10.png
│   │       │   ├── jquery-validation
│   │       │   │   ├── additional-methods.min.js
│   │       │   │   ├── jquery.validate.min.js
│   │       │   │   └── localization
│   │       │   │       └── messages_zh.min.js
│   │       │   ├── js
│   │       │   │   ├── common.js
│   │       │   │   ├── jquery.min.js
│   │       │   │   └── md5.min.js
│   │       │   ├── layer
│   │       │   │   ├── layer.js
│   │       │   │   ├── mobile
│   │       │   │   │   ├── layer.js
│   │       │   │   │   └── need
│   │       │   │   │       └── layer.css
│   │       │   │   └── skin
│   │       │   │       └── default
│   │       │   │           ├── icon-ext.png
│   │       │   │           ├── icon.png
│   │       │   │           ├── layer.css
│   │       │   │           ├── loading-0.gif
│   │       │   │           ├── loading-1.gif
│   │       │   │           └── loading-2.gif
│   │       │   └── order_detail.htm
│   │       └── templates
│   │           ├── goods_detail.html
│   │           ├── goods_list.html
│   │           ├── hello.html
│   │           ├── login.html
│   │           ├── miaosha_fail.html
│   │           └── order_detail.html
│   └── test
└── target
    ├── classes
    │   ├── META-INF
    │   │   ├── MANIFEST.MF
    │   │   └── maven
    │   │       └── com.pro.
    │   │           └── miaosha
    │   │               ├── pom.properties
    │   │               └── pom.xml
    │   ├── application.properties
    │   ├── com
    │   │   └── pro
    │   │       └── miaosha
    │   │           ├── MainApplication.class
    │   │           ├── config
    │   │           │   ├── UserArgumentResolver.class
    │   │           │   └── WebConfig.class
    │   │           ├── controller
    │   │           │   ├── DemoController.class
    │   │           │   ├── GoodsController.class
    │   │           │   ├── LoginController.class
    │   │           │   ├── MiaoshaController.class
    │   │           │   └── OrderController.class
    │   │           ├── dao
    │   │           │   ├── GoodsDao.class
    │   │           │   ├── MiaoshaUserDao.class
    │   │           │   ├── OrderDao.class
    │   │           │   └── UserDao.class
    │   │           ├── domain
    │   │           │   ├── Goods.class
    │   │           │   ├── MiaoshaGoods.class
    │   │           │   ├── MiaoshaOrder.class
    │   │           │   ├── MiaoshaUser.class
    │   │           │   ├── OrderInfo.class
    │   │           │   └── User.class
    │   │           ├── exception
    │   │           │   ├── GlobalException.class
    │   │           │   └── GlobalExceptionHandler.class
    │   │           ├── rabbitmq
    │   │           │   ├── MQConfig.class
    │   │           │   ├── MQReceiver.class
    │   │           │   ├── MQSender.class
    │   │           │   └── MiaoshaMessage.class
    │   │           ├── redis
    │   │           │   ├── BasePrefix.class
    │   │           │   ├── GoodsKey.class
    │   │           │   ├── KeyPrefix.class
    │   │           │   ├── MiaoshaKey.class
    │   │           │   ├── MiaoshaUserKey.class
    │   │           │   ├── OrderKey.class
    │   │           │   ├── RedisConfig.class
    │   │           │   ├── RedisPoolFactory.class
    │   │           │   ├── RedisService.class
    │   │           │   └── UserKey.class
    │   │           ├── result
    │   │           │   ├── CodeMsg.class
    │   │           │   └── Result.class
    │   │           ├── service
    │   │           │   ├── GoodsService.class
    │   │           │   ├── MiaoshaService.class
    │   │           │   ├── MiaoshaUserService.class
    │   │           │   ├── OrderService.class
    │   │           │   └── UserService.class
    │   │           ├── util
    │   │           │   ├── MD5Util.class
    │   │           │   ├── UUIDUtil.class
    │   │           │   └── ValidatorUtil.class
    │   │           ├── validator
    │   │           │   ├── IsMobile.class
    │   │           │   └── IsMobileValidator.class
    │   │           └── vo
    │   │               ├── GoodsDetailVo.class
    │   │               ├── GoodsVo.class
    │   │               ├── LoginVo.class
    │   │               └── OrderDetailVo.class
    │   ├── static
    │   │   ├── bootstrap
    │   │   │   ├── css
    │   │   │   │   ├── bootstrap-theme.css
    │   │   │   │   ├── bootstrap-theme.css.map
    │   │   │   │   ├── bootstrap-theme.min.css
    │   │   │   │   ├── bootstrap-theme.min.css.map
    │   │   │   │   ├── bootstrap.css
    │   │   │   │   ├── bootstrap.css.map
    │   │   │   │   ├── bootstrap.min.css
    │   │   │   │   └── bootstrap.min.css.map
    │   │   │   ├── fonts
    │   │   │   │   ├── glyphicons-halflings-regular.eot
    │   │   │   │   ├── glyphicons-halflings-regular.svg
    │   │   │   │   ├── glyphicons-halflings-regular.ttf
    │   │   │   │   ├── glyphicons-halflings-regular.woff
    │   │   │   │   └── glyphicons-halflings-regular.woff2
    │   │   │   └── js
    │   │   │       ├── bootstrap.js
    │   │   │       ├── bootstrap.min.js
    │   │   │       └── npm.js
    │   │   ├── goods_detail.htm
    │   │   ├── img
    │   │   │   ├── iphonex.png
    │   │   │   └── meta10.png
    │   │   ├── jquery-validation
    │   │   │   ├── additional-methods.min.js
    │   │   │   ├── jquery.validate.min.js
    │   │   │   └── localization
    │   │   │       └── messages_zh.min.js
    │   │   ├── js
    │   │   │   ├── common.js
    │   │   │   ├── jquery.min.js
    │   │   │   └── md5.min.js
    │   │   ├── layer
    │   │   │   ├── layer.js
    │   │   │   ├── mobile
    │   │   │   │   ├── layer.js
    │   │   │   │   └── need
    │   │   │   │       └── layer.css
    │   │   │   └── skin
    │   │   │       └── default
    │   │   │           ├── icon-ext.png
    │   │   │           ├── icon.png
    │   │   │           ├── layer.css
    │   │   │           ├── loading-0.gif
    │   │   │           ├── loading-1.gif
    │   │   │           └── loading-2.gif
    │   │   └── order_detail.htm
    │   └── templates
    │       ├── goods_detail.html
    │       ├── goods_list.html
    │       ├── hello.html
    │       ├── login.html
    │       ├── miaosha_fail.html
    │       └── order_detail.html
    └── test-classes
```
