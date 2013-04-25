android-app
===========

本项目采用 GPL 授权协议，欢迎大家在这个基础上进行改进，并与大家分享。


# **eoe社区 Android 客户端项目简析** #

*注：本文假设你已经有Android开发环境*


启动Eclipse，导入Android客户端项目，请确保你当前的Android SDK是最新版。
如果编译出错，请修改项目根目录下的 project.properties 文件。
推荐使用Android 4.0 以上版本的SDK：

target=android-15
## **一、工程目录结构** ##
根目录<br>
>├ src <br>
>├ libs <br>
>├ res <br>
>├ AndroidManifest.xml <br>
>├ LICENSE.txt <br>
>├ proguard.cfg <br>
>└ project.properties <br>

**1、src目录** <br>
src目录用于存放工程的包及java源码文件。

下面是src目录的子目录：

> src <br>

>├ cn.eoe.app <br>
>├ cn.eoe.app.adapter <br>
>├ cn.eoe.app.adapter.base <br>
>├ cn.eoe.app.biz <br>
>├ cn.eoe.app.config <br>
>├ cn.eoe.app.db <br>
>├ cn.eoe.app.db.biz <br>
>├ cn.eoe.app.entity <br>
>├ cn.eoe.app.entity.base <br>
>├ cn.eoe.app.https <br>
>├ cn.eoe.app.indicator <br>
>├ cn.eoe.app.slidingmenu <br>
>├ cn.eoe.app.ui <br>
>├ cn.eoe.app.ui.base <br>
>├ cn.eoe.app.utils <br>
>├ cn.eoe.app.view <br>
>├ cn.eoe.app.widget <br>

 
**2、libs目录** <br>
libs目录用于存放项目引用的第三方jar包。

libs目录里的jar包文件：

libs
>|- android-support-v4.jar <br>
jackson-all-1.9.2.jar <br>
umeng_sdk.jar <br>

**3、res目录** <br>
res目录存放工程用到的图片、布局、样式等资源文件。<br>
res目录的子目录：<br>

res <br>
>├ anim <br>
>├ color <br>
>├ drawable <br>
>├ drawable-hdpi <br>
>├ drawable-ldpi <br>
>├ drawable-mdpi <br>
>├ drawable-xhdpi <br>
>├ interpolator
>├ layout <br>
>├ menu <br>
>├ raw <br>
>├ values <br>
>└ values-zh <br>

4、AndroidManifest.xml
AndroidManifest.xml用于设置应用程序的版本、主题、用户权限及注册Activity等组件及其他配置。

## **二、程序功能流程** ##
1、APP启动流程

AndroidManifest.xml注册的启动Activity是"cn.eoe.app.ui.SplashActivity"，然后进入到主界面，对应的

Activity是“cn.eoe.app.ui.MainActivity”


2、APP访问流程

 

# **参与贡献** #
android-app项目设置2-3名管理者，目前的管理者是
- com360
- Iceskysl

每个人都可以fork一份代码，在自己的分支上修改，完成相关的功能后可以给 `eoecn/android-app` 发起一个pull request,管理者收到pull request后会评估合并提交的功能和代码
