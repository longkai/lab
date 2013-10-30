雨无声实验室
========================

### 参与者
感谢 孙中一，陆鹏，以及雨无声2013届技术部所有成员，雨无声网站，以及所有支持的同学:-)

### 说明
原来的项目经过大量重构，几乎是重写了，使用了挺多新的技术和自己的一些改进，算是大学写过的程序的一个总结吧:-)

这个项目在暑假基本就弄好了，但是一直没有同步上去，最近也一直没有碰，校招季呀...

很多事情不能自己一个人来，需要团队的一起努力。一个人很辛苦，也局限了自己。大家一起分享，一起思考，一起解决，一起进步，这应该是最漂亮的算法！

大学时代由于客观原因苦于找不到一些有共同志趣朋友一起来做些什么，所以后来有了做这个的想法。

希望能有更多的同学们能够感受到技术带来的魅力，对生活带来的方便，并且亲自尝试一下做些什么（尤其是西大的学弟妹们）大学时代能够做出一些实际的有积极意义的事情是一件非常愉快的事情:-)

最后，希望这个或多或少对大家有用:-)

### 文档更新时间
2013-10-30 longkai im.longkai@gmail.com

### 重要事项
实验室项目虽然已经正式上线，但是一些子项目目前仍在持续完善中，会有一些bug，请谅解:-)

本项目只包含服务端及html等试图代码，Android客户端和其他项目的代码可以在(htpps://github.com/longkai)找到:-)

## 已知问题（重要非紧急）
1. 在gradle中执行多项目测试会导致路径失败的问题（在ide中对子项目执行测试无问题）
2. 嵌入式数据库脚本必须放置在src/main目录下才可使用
3. web资源文件目前只能统一放到一个子项目中而不能放置在不同的子项目中
。。。

## 主要使用技术(java)
1. spring, springmvc
2. mybatis
3. gradle
4. bootstrap
5. ajax
6. restful service
7. Android(客户端，相关代码不包含在本项目内)

## 子项目结构说明
util 项目工具类
core 核心类
notty 校园信息应用
app=store 应用市场
webapp 存放web资源相关，静态文件，资源模版等

## 软件要求
1. jdk6+
2. gradle (请google，版本1.6+)
3. 推荐使用intellij ieaa或者eclipse作为IDE(版本越新越好，在idea 12.1及eclipse 3.7.2测试通过)
4. mysql(越新越好，测试版本为5.5.29)
5. 流畅的网络环境，方便下载第三方依赖（或者你本地有一个gradle或者maven包含项目所需依赖的仓库）

## 硬件要求
推荐4G内存运行本项目，妥妥的:-)
其他的要求，暂时不知。。。

## 重要的配置文件(!开头表示推荐修改的文件)
1. gradle.properties gradle配置文件，主要是第三方依赖的版本号
2. settings.gradle 实验室项目所包含的子项目
3. build.gradle 所有项目的gradle构建配置
4. !core/src/main/resources/db-config.json 数据库的配置，url，username等
5. !core/src/main/resources/logback.xml logback日志配置，在这里可以配置文件日志的存放路径，日志级别等内容
6. !core/src/main/resources/spring.xml spring核心配置文件，包含启动容器时执行嵌入的sql脚本以及Javaconfig的类
7. core/src/main/resources/db/ 项目的嵌入sql脚本（在运行时执行，见配置6）
8. webapp/src/main/resources/uri.json 静态uri与视图文件的匹配表
9. webapp/src/main/resources/freemarker.json freemarker模版配置（freemarker.properties的json格式:-)）
10. !webapp/src/main/resources/file-upload.json 文件上传的目录配置
11. webapp/build.gradle jetty配置（port与context path）
11. core/src/main/java/cn/newgxu/lab/core/config/SpringBeans.java spring javaconfig基本beans配置
12. core/src/main/java/cn/newgxu/lab/core/config/WebMvcConfig.java spring javaconfig web 相关配置(内容协商协议，视图等)

## 如何运行
1. 确保达到了软件要求（ide非必需）
2. mysql数据库与 core/src/main/resources/db-config.json 匹配
3. 修改你想要修改的配置文件内容（见重要的配置文件）
4. 运行（命令行，如果ide支持gradle，也可直接在ide中运行，请自行查阅文档）
    进入项目根目录，键入 `gradle clean jettyRun`，若无错误信息或者异常，那么浏览器地址栏输入http://localhost即可
5. 导入ide（可选）
    5.1 idea 如果使用intellij idea，那么命令行进入项目根目录，键入`gradle idea`, 成功之后在idea中将项目导入即可
    5.2 eclipse 如果使用eclipse，那么需要修改build.gradle文件的第三行，将idea换成eclipse即可，保存后命令行进入项目根目录，键入`gradle eclipse`，成功之后再eclipse中将项目导入即可

## 如何添加项目
1. 如果没有使用过gradle，请先查阅gradle的官方文档
2. 根项目下的`build.gradle`是项目的核心配置，一定要看过
3. 在settings.gradle中添加一个子项目的名字，参考原有的
4. 在根目录下添加你的项目（名字一定要和`settings.gradle`中一样），项目的文件结构可以自己手动建立，也可以使用gradle脚本
5. 如果有需要，配置项目的`build.gradle`，参考一下原有的项目配置
6. 注意事项，如果是web项目，不需要使用jetty，使用war插件就好，如果包含模板文件，请统一放置到webapp/src/main/webapp/WEB-INF/freemarker下（假设使用freemarker，使用jsp就是替换掉freemarker而已）
7. 如果包含静态文件（html，js，css）等，请同意放置在webapp/src/main/webapp/resources/下，统一放置在一个已自己项目命名目录下
8. 如果有任何问题，请先查看原有的项目（如notty的配置），如有疑问，请邮件im.longkai@gmail.com

=========================
本项目可能在配置上有一些复杂，但是如果你理解了为什么这样管理项目的依赖，如何使用，带来的好处是值得的:-)
