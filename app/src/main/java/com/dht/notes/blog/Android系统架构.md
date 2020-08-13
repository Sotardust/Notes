![](https://img.hacpai.com/bing/20171219.jpg?imageView2/1/w/960/h/540/interlace/1/q/100) 

Android系统架构分为五层，从上到下依次是应用层、应用框架层、系统运行层、硬件抽象层、和Linux内核层。
![Android系统架构](http://www.sotardust.cn/images/android_system_framework.png)
[图片参考链接](https://www.jianshu.com/p/13da6c2e537f)
### 应用层（Application）
系统内置的应用程序以及非系统级的应用程序都属于应用层，负责与用户进行直接交互。

### 应用框架层（Application FrameWork）
应用框架层为开发人员提供了可以开发应用程序所需要的API，我们平常开发应用程序都是调用的这一层所提供的的API，当然也包括系统的应用。这一层的是由Java代码编写的，可以称为Java Framework 。下面为这一层所提供的的主要的组件。

| 名称 |功能描述|
| --- | --- | 
| Activity Manager(活动管理器) | 管理各个应用程序生命周期以及通常的导航回退功能 |
| Location Manager(位置管理器) | 提供地理位置以及定位功能服务 |
| Package Manager(包管理器) | 管理所有安装在Android系统中的应用程序 |
| Notification Manager(通知管理器) | 使得应用程序可以在状态栏中显示自定义的提示信息 | 
| Resource Manager（资源管理器） | 提供应用程序使用的各种非代码资源，如本地化字符串、图片、布局文件、颜色文件等 | 
| Telephony Manager(电话管理器) | 管理所有的移动设备功能 | 
| Package Manager(包管理器) | 管理所有安装在Android系统中的应用程序 | 
| Window Manager（窗口管理器） | 管理所有开启的窗口程序 | 
| Content Providers（内容提供器） | 使得不同应用程序之间可以共享数据 | 
| View System（视图系统 | 构建应用程序的基本组件 | 

### 系统运行库
系统运行库分为两部分，分别是C/C++程序库和Android运行时库 libraries+Android Runtime

### 硬件抽象层（HAL)
硬件抽象层是位于操作系统内核与硬件电路之间的接口层，其目的在于将硬件抽象化，为了保护硬件厂商的知识产权，它隐藏了特定平台的硬件接口细节，为操作系统提供虚拟硬件平台，使其具有硬件无关性，可在多种平台上进行移植。 从软硬件测试的角度来看，软硬件的测试工作都可分别基于硬件抽象层来完成，使得软硬件测试工作的并行进行成为可能。通俗来讲，就是将控制硬件的动作放在硬件抽象层中。

### Linux 内核层
Android的核心系统服务基于Linux内核，在此基础上添加了部分Android专用的驱动，系统的安全性、内存管理、进程管理、网络协议和驱动模型等都依赖于该内核
 

