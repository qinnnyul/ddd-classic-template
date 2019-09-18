# 电票上下文

## 代码规范
* 项目包名请使用单数
* 资源类名称请使用单数
* 资源URL名称请使用复数
* 变量和方法的命名请使用`驼峰规范`
* 请使用lombok，取代Getter，Setter和toString等
* 实体的ID生成器，可以使用工具类里面的生成器，`SnowflakeIdGenerator`或者`UUIDGenerator`
* 命名规范：
    * XXXApi(资源接口)
    * XXXAppService（应用服务）
    * XXXService（领域服务, 三方服务）
    * XXXServiceImpl(基础层服务实现)

## Restful风格

| 场景 | 动作 | 路径 | 响应
| ------ | ------ | ------ | ------ |
|查询银行承兑汇票票面信息|GET| /api/v1/bank-acceptance-bills/{billNo} |200， 400， 401， 403， 5XX|
|查询某个银行承兑汇票历史信息|GET| /api/v1/bank-acceptance-bills/{billNo}/histories |200， 400， 401， 403， 5XX|
|提交出票申请|POST|/api/v1/commands/issue-apply | 201， 400， 401， 403，5XX|
|提交背书申请|POST | /api/v1/commands/transfer-apply |201,  400， 401， 403， 5XX|
|提交批量出票申请| POST |/api/v1/commands/batch-issue-apply |201， 401， 403， 5XX|
|审批或者撤销申请|PUT| /api/v1/commands/{commandId}/action |200， 400， 401， 403， 5XX|
|查询某个申请详情|GET| /api/v1/commands/{commandId} |200， 400， 401， 403， 5XX|
|查询某个申请的指令历史记录|GET| /api/v1/commands/{commandId}/histories |200， 400， 401， 403， 5XX|

## 分层架构

```

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
* 测试覆盖率检测， 分支和语句覆盖率大于70%。
* Push前，本地强制运行mvn clean package。  
# ddd-classic-template
