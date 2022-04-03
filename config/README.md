# CONFIG
 基于 spring cloud config

 默认采用本地文件方式

 通过 `-Dspring.profiles.active=git` 进行切换到git;

 通过 `-Dspring.profiles.active=svn` 进行切换到svn;

 本地启动时在pom.xml中设置账号密码

 部署到生产或其它环境时，通过参数传入账号密码即可`-Dfcf.config.git.password=123`
 