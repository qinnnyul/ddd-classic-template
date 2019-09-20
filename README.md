# DDD代码脚手架

## 代码规范
* 项目包名请使用单数
* 资源类名称请使用单数
* 资源URL名称请使用复数
* 变量和方法的命名请使用`驼峰规范`
* 请使用lombok，取代Getter，Setter和toString等
* 实体的ID生成器，可以使用工具类里面的生成器，`SnowflakeIdGenerator`或者`UUIDGenerator`
* 命名规范：
    * XXXController(资源接口)
    * XXXRequest（DTO请求对象）
    * XXXResponse(DTO返回对象)
    * XXXAppService（应用服务）
    * XXXRepository (资源库)
   

## Restful风格

| 场景 | 动作 | 路径 | 响应
| ------ | ------ | ------ | ------ |
|查询用户信息|GET| /demo-users/{userId} |200， 400， 401， 403， 5XX|
|创建用户|POST| /demo-users |201， 400， 401， 403， 5XX|
|修改用户|PUT|/demo-users/{userId} | 200， 400， 401， 403，5XX|
|删除用户|DELETE | /demo-users/{userId} |200,  400， 401， 403， 5XX|

## 分层架构


```
### 代码分层

├── Application.java
├── api  《接口层》
│   ├── DemoUserController.java
│   ├── assembler
│   └── dto
├── application 《应用层》
│   └── DemoUserAppService.java
├── common 《公用层》
│   ├── config
│   ├── exception
│   └── util
├── domain 《领域层》
│   ├── DemoUser.java
│   └── DemoUserRepository.java
└── infrastructure 《基础设施层》
    ├── DemoUserRepositoryImpl.java
    ├── dao
    └── po


### 单聚合
├── application 《应用层》
│   ├── api
│   │   ├── assembler
│   │   ├── request
│   │   └── response
│   └── service
├── domain 《领域层》
│   │   ├── entity
│   │   ├── repository -资源库接口
│   │   ├── service
│   │   ├── vo
│   │   ├── factory
│   │   └── external - 三方接口
├── infrastructure 《基础设施层》
│   ├── assembler
│   ├── config
│   ├── external - 三方服务接口实现
│   ├── persistence - 资源库实现
│   └── util


### 多聚合

├── bill （票据聚合）
│   ├── application
│   ├── domain
│   └── infrastructure
├── command （指令聚合）
│   ├── application
│   ├── domain
│   └── infrastructure
├── common （公共部分）
│   ├── config
│   ├── exception
│   └── util

```

## 分层职责和依赖关系
![分层职责.png](https://upload-images.jianshu.io/upload_images/12636540-909226d57da7eec7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 架构守护
`ARCHUNIT`是一个基于 Java 的测试库，用于检查代码的结构特性，如包和类的依赖关系、注解验证，还能检查代码分层是否一致，它可以在现有的测试环境中以单元测试的方式运⾏。
* 分层架构守护测试，如图：
![分层架构守护.png](https://upload-images.jianshu.io/upload_images/12636540-a116c227a749afbe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![分层架构守护.png](https://upload-images.jianshu.io/upload_images/12636540-ed79ed4ba80dc1e6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* 循环依赖检测，如图：
![分层依赖.png](https://upload-images.jianshu.io/upload_images/12636540-3a0aea31e6c0f488.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 聚合根原则
* 聚合：由一组相关的领域对象组成，需要通过聚合根来进行统一的导航。
* 一个聚合根对应一个资源库。
* 聚合根之间的引用必须通过ID，而不是对象引用。
* 实体不一定是聚合根，聚合根一定是实体。
* 不同聚合的Domain层不在一个包里，彼此的访问通过Application service转发，或者基于事件进行异步通信。
* 实现依赖Domain层，Domain层不依赖实现 - 依赖倒置。

## 本地强制检测
* Checkstyle编码风格。
* 提交日志检测。e.g:“[Your name] commit messages”。
* 测试覆盖率检测， 分支和语句覆盖率大于80%。
* Push前，本地强制运行mvn clean package。  
# ddd-classic-template
