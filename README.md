# FCP
 fast cloud platform(FCP) 是一个基于[FCF](https://github.com/hlg212/FCF) 实现的spring cloud 的快速开发平台。

 FCP 是基础开发平台的统称，提供了业务开发中需要的基础功能，如: 用户、机构、权限 等。

 提供微服务开发的基础组件，如： 网关、配置中心、注册中心 等。

 提供中间件服务相关的一些功能实现，如： 任务调度、网关路由、文件、推送 等。

 遵循分布式架构的规范，声明与实现分离，一般情况下开发者基于[FCF](https://github.com/hlg212/FCF) 框架接口进行开发与调用，无需关心具体实现着。

 ## Links

- [Documentation]
- [代码生成](https://github.com/hlg212/FCP/tree/master/generator)
- [例子](https://github.com/hlg212/fcf-examples)
- [工程结构](https://github.com/hlg212/FCP/tree/master/docs/directory_structure.md)
- [微服务模式部署](https://github.com/hlg212/fcf-examples)
- [集成单体模式部署](https://github.com/hlg212/FCP)

## 服务组件
|  名称   | 中文  |  说明  |
|  ----  | ----  | ----  |
| rc  | 注册中心 |  |
| config  | 配置中心 |  |
|   | 基础服务 |  |
| generator  | 代码生成 |  |
| gateway  | 权限网关 |  |
|   | 文件中心 |  |
|   | 日志中心 |  |
|   | 数据权限中心 |  |
|   | 认证中心 |  |
|   | 消息推送 |  |
|   | 辅助服务 |  |
|   | 网关管理 |  |

## 功能模块
```
├─系统管理
│  ├─应用管理
│  ├─字典管理
├─权限管理
│  ├─用户管理
│  ├─角色管理
│  ├─资源管理
│  └─租户管理
├─机构管理
│  ├─标准机构
│  ├─业务机构
├─文件管理
│  ├─文件管理
│  ├─
├─网关管理
│  ├─匿名资源管理
│  ├─路由管理
├─日志管理
│  ├─访问日志
│  ├─数据变动日志
├─任务调度
│  ├─任务管理
│  ├─调度日志
├─系统监控
│  ├─监控中心
│  │  ├─JVM信息
│  │  ├─日志级别
│  │  ├─缓存清空
│  │  ├─配置
│  │  ├─Dump
│  │  └─服务器信息
│  ├─在线用户
```
## 架构图


## 选型
|  名称   | 版本  |  说明  |
|  ----  | ----  | ----  |
| spring cloud gateway  | 单元格 | 单元格 |
| spring cloud config  | 单元格 | 单元格 |
| eureka  | 单元格 | 单元格 |
| xxl-job  | 单元格 | 单元格 |
| fdfs  | 单元格 | 单元格 |
| spring boot admin  | 单元格 | 单元格 |
| oauth2  | 单元格 | 单元格 |

## 交流反馈
* 交流请进群：
	* QQ： 643083402
	* 微信：
* 个人邮箱：xiaolaoban212@qq.com

## License

See the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) file for details.