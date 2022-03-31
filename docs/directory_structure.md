# 项目工程结构
整个平台按照一种方便构建，其它服务方便引用，方便集成的方式进行设计;

## 完整的目录结构
```
├─系统
│  ├─服务
│  │  ├─接口
│  │  ├─实现
│  │  ├─自动集成
├─fcp
│  ├─basic
│  │  ├─facade-basic
│  │  ├─service-basic
│  │  ├─starter-basic
```

### 系统
系统 级别一般只是一个pom.xml

一般是按照业务与功能模块进行划分，用于定于整个系统的一些环境与依赖信息.如:框架版本,容器等;

一般情况下也不会过多的去修改该文件

### 服务
服务 级别一般也仅仅是一个pom.xml

当执行构建打包时，通常是在该层运行，使服务的多个模块版本都能统一一致

```
# 到服务目录
cd curdie 
# 或 deploy
mvn install 
cd service-curdie/target
# 运行
java -jar service-curdie-exec.jar
```

#### 接口
该模块仅存放服务中的数据结构定义与接口定义

不要包含业务开发逻辑与代码

#### 实现
业务的实现模块

该模块构建会生成两个jar包
```text
# 会被上传到私服库
1. service-curdie.jar
# 不会上传，可以java -jar运行 
2. service-curdie-exec.jar
```

#### 自动集成
基于spring autoconfiguration 可以将基于FCF框架的不同微服务集成打包




