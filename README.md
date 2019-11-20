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
│   ├── exception
│   └── util
├── domain 《领域层》
│   ├── DemoUser.java
│   └── DemoUserRepository.java
└── infrastructure 《基础设施层》
│   ├── config
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
* ```用户接口层```（API）主要暴露微服务的接口，它处理接受来自前端的请求（XXRequestDto），对数据进行基本的校验（e.g：字段非空、格式规则校验等），将Dto传递给
应用层，获取应用服务处理后的返回数据，进行简单组装（XXResponseDto），返回给前端 - 这应该是非常薄的一层。

* ```应用层``` 负责处理业务用例的执行顺序和结果组装，主要做一些组合、编排和转发等

* ```领域层``` 负责表达业务概念，业务状态信息以及业务规则。

* ```基础设施层```  向其他层提供通用的技术能力， 为应用层传递消息，为领域层提供持久化机制，为用户界面层绘制屏幕组件等


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
