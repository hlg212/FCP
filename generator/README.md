# Generator
 代码生成服务
 
 根据表快速生成基础功能(增删改查导入导出)；
 
 生成代码 —— 仅生成代码，新模块时采用
 
 生成服务 —— 不仅生成代码，还生成服务目录结构，只需要导入到idea就可以开始开发

 生成系统 —— 一个系统多个服务，一般都是项目开始时生成，参考 [FCF项目结构]()

# 接口
![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/generator_show.jpg)

## 生成代码
仅生成代码，手动将代码拷贝到服务中

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/generator_code.jpg)

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/generator_code_down.jpg)

##### 参数
```json
{
  "author": "huanglg",
  "codePackage": "io.github.hlg212.fcf_examples.curdie",
  "db": {
      "dbUrl": "jdbc:p6spy:h2:file:./generator",
      "password": "dev_generator",
      "username": "dev_generator"
    }
}
```

## 生成服务
生成一个完整的服务，目录接口都存在

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/generator_project.jpg)

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/generator_project_dir.jpg)



##### 参数
```json
{
  "codeGeneratorBo": {
    "author": "huanglg",
    "codePackage": "io.github.hlg212.fcf_examples.curdie",
    "db": {
      "dbUrl": "jdbc:p6spy:h2:file:./generator",
      "password": "dev_generator",
      "username": "dev_generator"
    }
  },
  "codePackage": "io.github.hlg212.fcf_examples.curdie",
  "frameVersion": "0.1.0-SNAPSHOT",
  "mavenPackage": "io.github.hlg212.fcf_examples",
  "parentCode": "fcf_examples",
  "parentMavenPackage": "io.github.hlg212",
  "parentVersion": "0.1.0-SNAPSHOT",
  "projectCode": "curdie",
  "projectName": "例子-增删改查导入导出"
}
```
## 生成系统
一般是项目开始时生成，可以一次性生成多个服务；

![image](https://raw.githubusercontent.com/hlg212/fcf-examples/master/images/generator_projectGourp.png)

##### 参数
```json
{
  "frameVersion": "0.1.0-SNAPSHOT",
  "groupCode": "fcf_examples",
  "mavenPackage": "io.github.hlg212",
  "parentCode": "parent",
  "parentMavenPackage": "io.github.hlg212",
  "parentVersion": "0.1.0-SNAPSHOT",
  "projects": [
    {
      "codeGeneratorBo": {
        "author": "huanglg",
        "codePackage": "io.github.hlg212.fcf_examples.curdie",
        "db": {
          "dbUrl": "jdbc:p6spy:h2:file:./generator",
          "password": "dev_generator",
          "username": "dev_generator"
        }
      },
      "codePackage": "io.github.hlg212.fcf_examples.curdie",
      "frameVersion": "0.1.0-SNAPSHOT",
      "mavenPackage": "io.github.hlg212.fcf_examples",
      "parentCode": "fcf_examples",
      "parentMavenPackage": "io.github.hlg212",
      "parentVersion": "0.1.0-SNAPSHOT",
      "projectCode": "curdie",
      "projectName": "例子-增删改查导入导出"
    },
    {
      "codeGeneratorBo": {
        "author": "huanglg",
        "codePackage": "io.github.hlg212.fcf_examples.curdie2",
        "db": {
          "dbUrl": "jdbc:p6spy:h2:file:./generator",
          "password": "dev_generator",
          "username": "dev_generator"
        }
      },
      "codePackage": "io.github.hlg212.fcf_examples.curdie2",
      "frameVersion": "0.1.0-SNAPSHOT",
      "mavenPackage": "io.github.hlg212.fcf_examples",
      "parentCode": "fcf_examples",
      "parentMavenPackage": "io.github.hlg212",
      "parentVersion": "0.1.0-SNAPSHOT",
      "projectCode": "curdie2",
      "projectName": "例子-增删改查导入导出2"
    }
  ],
  "version": "1.0.0"
}
```