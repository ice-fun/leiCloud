<p align="center">
 <img src="https://img.shields.io/badge/Spring%20Cloud-2020-blue.svg" alt="Coverage Status">
 <img src="https://img.shields.io/badge/Spring%20Boot-2.5-blue.svg" alt="Downloads">
</p>

## 系统说明

- 基于 Spring Cloud 2020 、Spring Boot 2.5、Spring security 、 Spring aop、消息队列、Spring cloud stream实现的登录、鉴权、日志搜集系统
- 提供一个适合多样web项目开发的spring cloud 分布式起步系统，开箱即用，且扩展性强。

### 核心功能

| 功能                   | 说明          |
| ---------------------- | ------------- |
| 登录            |基于Spring security 5 实现，提供了账号密码、微信等几种登录方法，并且可以根据实际需求扩展 |
| 鉴权           | 基于Spring security、JWT，访问控制，游客放行   |
| 日志     | 基于AOP、Spring cloud stream实现，记录每个接口的访问参数、结果信息，支持单机数据库直接存储，也支持消息队列异步存储|
| 分布式          | Spring cloud部分组件   |

### 模块说明

```
common -- 公共工具类、实体类
eureka -- 注册服务
gateway-api -- api的统一入口
security-auth -- 登录、鉴权模块
service-article -- 服务
service-news -- 服务
system-log -- 日志模块
log-mq -- 消息队列存储日志服务（选择性开启）
```

## 快速开始

- 1.clone仓库
- 2.新建数据库并导入doc文件夹下的db.sql
- 3.修改各个yml文件的配置参数（数据库密码等）
- 4.启动本地的zookeeper和kafka服务（如果不需要，别启动log-mq服务）
- 5.启动各个服务（最先启动eureka，最后启动gateway-api）

## 开源共建

### 开源协议

leirancloud项目以 [Apache 2.0 协议](https://www.apache.org/licenses/LICENSE-2.0.html) 开源，详情请阅读协议内容，欢迎参与。

