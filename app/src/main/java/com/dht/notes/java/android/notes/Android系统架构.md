### Android系统架构
Android系统架构分为五层，从上到下依次是应用层、应用框架层、系统运行层、硬件抽象层、和Linux内核层。

![Android的系统体系架构](https://images0.cnblogs.com/blog/432441/201310/26133735-b9747c9f0d364527977bc278199aea98.jpg)


#### 应用层（Application）

系统内置的应用程序以及非系统级的应用程序都属于应用层，负责与用户进行直接交互，通常都是用java进行开发的

#### 应用框架层（Application Framework）

应用框架层为开发人员提供了可以开发应用程序所需要的API，我们平常开发应用程序都是调用的这一层所提供的API，
当然也包括系统的应用。这一层的是由Java代码编写的，可以称为Java Framework。下面来看这一层所提供的主要的组件。

| 名称 |功能描述|
| - | - | 
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

系统运行库层分为两部分，分别是C/C++程序库和Android运行时库。

libraries  + Android Runtime


### Linux内核层

Android 的核心系统服务基于Linux 内核，在此基础上添加了部分Android专用的驱动。
系统的安全性、内存管理、进程管理、网络协议栈和驱动模型等都依赖于该内核。