## 实现了简单的商品秒杀功能的project(SecKill)
#### (该项目是为了学习spring boot技术以及高并发,所以只包含用户、商品、订单三个模块)
### 技术栈
* 前端：Thymeleaf渲染(部分接口前后端分离) + Bootstrap + jQuery(及其简单的页面,没有难度)
* 后端：Spring Boot 1.5.9RELEASE + Mybatis
* 数据库：MySQL + Redis + druid数据库连接池
* 消息队列：RabbitMQ
* 开发环境：Intellij IDEA 2017.2
* 构建工具：Maven 3.5
### 开始使用
* IDEA打开工程, 修改application.properties文件,按照本地主机的配置进行
* 本地MySQL创建用户:seckill,密码:seckill,执行seckill.sql文件
### 高并发优化的路线
* 页面优化路线：
     * 页面缓存
     * 页面静态化(前后端分离)
     * 静态资源优化(none)
     * CDN(none)
* 后端优化路线：
     * NoSQL缓存(Redis)
     * 读写分离
     * 异步消息处理(RabbitMQ)
     * 应用降级(关闭非必须功能)
     * 接口限流防刷(工程中使用自定义注解以及拦截器实现)

