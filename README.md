# 火车抢票系统的实现

## 介绍

Java实现的火车抢票系统，基于Spring Boot 2.X。

## 技术栈

- SpringBoot2.0.1 + MyBatis + MySQL
- Redis + RabbitMQ
- BootStrap + Thymeleaf
- JSR303参数校验 + 全局异常处理器
- 分布式Session

## QuickStart

- clone源码

```
git clone git@github.com:Mercurioooo/miaosha.git
```

- 在Intelij IDEA/eclipse里导入根路径下的pom.xml 等待maven依赖下载完毕 详细操作：

**如果是IDEA**，先IDEA | File | Open...，选择根路径下的pom文件, Open as project以导入根项目。

操作菜单栏 View | Tool Windows | Maven Projects。 点击"+"， 添加pom。

此时Maven Projects下面有根项目miaosha。

**如果是Eclipse**, import导入maven项目，勾选pom文件即可。

- 修改application.properties里面的自己的Redis,MySQL,RabbitMQ的连接配置
- 右键MainApplication.java--run as--Java Application

## 原理

1. 页面缓存 + URL缓存 + 对象缓存，减少数据库的访问
2. 页面静态化，前后端分离
3. 静态资源优化 **秒杀接口优化**
4. RabbitMQ队列缓冲异步下单
5. Redis预减库存
6. 内存标记 **安全优化**
7. 地址隐藏
8. 数学公式验证码缓冲
9. 接口的防刷限流

## 具体的技术点

1. [火车抢票系统的实现(一)搭建项目框架](https://blog.csdn.net/Mercuriooo/article/details/104331179)
2. [火车抢票系统的实现(二)实现登录功能](https://blog.csdn.net/Mercuriooo/article/details/104331322)
3. [火车抢票系统的实现(三)秒杀功能开发及管理后台](https://blog.csdn.net/Mercuriooo/article/details/104332117)
4. [火车抢票系统的实现(四)页面级高并发秒杀优化](https://blog.csdn.net/Mercuriooo/article/details/104332117)
5. [火车抢票系统的实现(五)服务级高并发秒杀优化](https://blog.csdn.net/Mercuriooo/article/details/104332912)

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
│   │       │   │   ├── fonts
│   │       │   │   └── js
│   │       │   ├── goods_detail.htm
│   │       │   ├── img
│   │       │   ├── jquery-validation
│   │       │   ├── js
│   │       │   ├── layer
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
```
