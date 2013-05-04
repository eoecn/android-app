android-app
===========

本项目采用 GPL 授权协议，欢迎大家在这个基础上进行改进，并与大家分享。

如您感觉本项目中有不妥之处或者有不爽的地方，欢迎提交问题或更改方案，项目小组会
及时的对您提交的修改给予反馈。希望能为开发者提供一款开源好用的Android版客户端产品。
一款好产品需要大家共同努力，大家共勉！

# **eoe社区 Android 客户端项目简析** #

*注：本文假设你已经有Android开发环境*

本文以eclipse为例<br>
启动Eclipse，导入Android客户端项目，请确保你当前的Android SDK是最新版。
如果编译出错，请修改项目根目录下的 project.properties 文件。
推荐使用Android 4.0 以上版本的SDK：<br>

target=android-14
## **一、工程目录结构** ##
根目录<br>
>├ source  <br> 
>├ LICENCE.txt <br>
>├ README.md <br>

目录简要解释<br>
根目录<br>
>├ source --源代码 <br> 
>├ LICENCE.txt --开源协议 <br>
>├ README.md --项目帮助及项目信息 <br>

## **二、源代码目录结构** ##
source<br>
>├ src  <br> 
>├ libs <br>
>├ res <br>
>├ AndroidManifest.xml <br>
>├ proguard-project.txt <br>
>└ project.properties <br>

**1、src目录** <br>
src目录用于存放工程的包及java源码文件。

下面是src目录的子目录：

> src <br>

>├ cn.eoe.app --存放程序全局性类的包<br>
>├ cn.eoe.app.adapter --存放适配器的实现类的包 <br>
>├ cn.eoe.app.adapter.base --存放适配器基类的包<br>
>├ cn.eoe.app.biz --存放ＤＡＯ类的包<br>
>├ cn.eoe.app.config －－存放常量，配置和api接口等类的包<br>
>├ cn.eoe.app.db --关于sqlite操作相关的类的包<br>
>├ cn.eoe.app.db.biz --详细的增删改查类的包，暂时仅有一个类<br>
>├ cn.eoe.app.entity --实体类包<br>
>├ cn.eoe.app.entity.base --实体类基类包<br>
>├ cn.eoe.app.https --网络访问相关类的包<br>
>├ cn.eoe.app.indicator --导航相关的类包<br>
>├ cn.eoe.app.slidingmenu --滑动菜单相关类包<br>
>├ cn.eoe.app.ui --界面相关的包，activity的类<br>
>├ cn.eoe.app.ui.base --activity相关的基类包<br>
>├ cn.eoe.app.utils --工具类包<br>
>├ cn.eoe.app.view --Fragment相关类的包<br>
>├ cn.eoe.app.widget --自定义view组件包<br>
>
>├ com.google.zxing.camera --第三方定义，控制摄像头包<br>
>├ com.google.zxing.decoding -- 二维码图像解码包<br>
>├ com.google.zxing.view -- 自定义View，控制拍摄取景框和动画等<br>

 
**2、libs目录** <br>
libs目录用于存放项目引用的第三方jar包。

libs目录里的jar包文件：

libs
>├  android-support-v4.jar --v4兼容包<br>
>├ jackson-all-1.9.2.jar --解析json的包<br>
>├ umeng_sdk.jar --友盟的sdk<br>
>├ zxing-1.6.jar --二维码处理的包<br>

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
>├ interpolator<br>
>├ layout <br>
>├ menu <br>
>├ raw <br>
>├ values <br>
>└ values-zh <br>

**4、AndroidManifest.xml**<br>
AndroidManifest.xml用于设置应用程序的版本、主题、用户权限及注册Activity等组件及其他配置。

## **三、程序功能流程** ##
**1、APP启动流程**

AndroidManifest.xml注册的启动Activity是"cn.eoe.app.ui.SplashActivity"，然后进入到主界面，对应的

Activity是“cn.eoe.app.ui.MainActivity”


**2.程序功能**<br>
 (1)社区精选<br>
 (2)新闻资讯<br>
 (3)学习教程<br>
 (4)社区博客<br>


 

# **参与贡献** #
android-app项目设置2-3名管理者，目前的管理者是
- cuijie(ghlimbrother)
- com360
- Iceskysl

每个人都可以fork一份代码，在自己的分支上修改，完成相关的功能后可以给 `eoecn/android-app` 发起一个pull request,管理者收到pull request后会评估合并提交的功能和代码

ps. 
详细的协作步骤请参考Iceskysl写的[基于Github参与eoe的开源项目指南](http://my.eoe.cn/iceskysl/archive/3195.html)文章
