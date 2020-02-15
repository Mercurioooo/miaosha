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

