####  Android 插件化框架类别

1、**DroidPlugin** ( 2015 年 8 月)

2、**Small** ( 2015 年底)

3、[VirtualAPK](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2Fdidi%2FVirtualAPK) (滴滴 2017年 6 月 )

4、[RePlugin](https://links.jianshu.com/go?to=https%3A%2F%2Fgithub.com%2FQihoo360%2FRePlugin) (360 2017 年 7 月)

5、[Shadow](https://github.com/Tencent/Shadow) （腾讯  2017年）

#### 1、DroidPlugin插件化框架

##### 介绍

DroidPlugin 是 360 手机助手实现的一种插件化框架，它可以直接运行第三方的独立 APK 文件，完全不需要对 APK 进行修改或安装。一种新的插件机制，一种免安装的运行机制，是一个沙箱（但是不完全的沙箱。就是对于使用者来说，并不知道他会把 apk 怎么样）， 是模块化的基础。

##### 优点： 

+ 支持 Android 四大组件，而且插件中的组件不需要在宿主 APK 中注册。
+ 支持 Android 2.3 及以上系统，支持所有的系统 API。
+ 插件与插件之间，插件与宿主之间的代码和资源完全隔阂。
+ 实现了进程管理，插件的空进程会被及时回收，占用内存低。

##### 缺点：

+ 插件 APK 中不支持自定义资源的 Notification，通知栏限制。
+ 插件 APK 中无法注册具有特殊的 IntentFilter 的四大组件。
+ 缺乏对 Native 层的 Hook 操作，对于某些带有 Native 代码的插件 APK 支持不友好，可能无法正常运行。
+ 由于插件与插件，插件与宿主之间的代码完全隔离，因此，插件与插件，插件与宿主之间的通信只能通过 Android 系统级别的通信方式。
+ 安全性担忧（可以修改，hook一些重要信息）。
+ 机型适配（不是所有机器上都能行，因为大量用反射相关，如果rom厂商深度定制了framework层，反射的方法或者类不在，容易插件运用失败）

#### 2、**Small**插件化框架

##### 介绍

Small 是一种实现轻巧的跨平台插件化框架，是基于“轻量、透明、极小化、跨平台”的理念。

##### 优点：

+ 所有插件支持内置宿主包中。
+ 插件的编码和资源文件的使用与普通开发应用没有差别。
+ 通过设定 URI ，宿主以及 Native 应用插件，Web 插件，在线网页等能够方便进行通信。
+ 支持 Android 、 iOS 、和 Html5 ，三者可以通过同一套 Javascript 接口实现通信 。

##### 缺点

暂不支持 Service 的动态注册，不过这个可以通过将 Service 预先注册在宿主的 AndroidManifest.xml 文件中进行规避，因为 Service 的更新频率通常非常低。


#### 3、VirtualAPK插件化矿建

##### 介绍

VirtualAPK 是滴滴开源的一套插件化框架，支持几乎所有的 Android 特性，四大组件方面。

VirtualAPK 对插件没有额外的约束，原生的 apk 即可作为插件。插件工程编译生成 apk后，即可通过宿主 App 加载，每个插件 apk 被加载后，都会在宿主中创建一个单独的 LoadedPlugin 对象。

##### 主要特性

四大组件均不需要在宿主manifest中预注册，每个组件都有完整的生命周期。

+ Activity：支持显示和隐式调用，支持Activity的theme和LaunchMode，支持透明主题；
+ Service：支持显示和隐式调用，支持Service的start、stop、bind和unbind，并支持跨进程bind插件中的Service；
+ Receiver：支持静态注册和动态注册的Receiver；
+ ContentProvider：支持provider的所有操作，包括CRUD和call方法等，支持跨进程访问插件中的Provider；
+ 自定义View：支持自定义 View，支持自定义属性和style，支持动画；
+ PendingIntent：支持PendingIntent以及和其相关的Alarm、Notification和AppWidget；
+ 支持插件Application以及插件manifest中的meta-data，支持插件中的so；
+ 兼容市面上几乎所有的Android手机，这一点已经在滴滴出行客户端中得到验证。
+ 资源方面适配小米、Vivo、Nubia 等，对未知机型采用自适应适配方案。
+ 极少的 Binder Hook，目前仅仅 hook了两个Binder：AMS和IContentProvider，hook 过程做了充分的兼容性适配。
+ 插件运行逻辑和宿主隔离，确保框架的任何问题都不会影响宿主的正常运行。
  

##### [github官网链接](https://github.com/didi/VirtualAPK)

##### 支持情况

三年前年前已经停止更新

#### 4、RePlugin插件化矿建

##### 介绍

RePlugin是一套完整的、稳定的、适合全面使用的，占坑类插件化方案，由360手机卫士的RePlugin Team研发，也是业内首个提出”全面插件化“（全面特性、全面兼容、全面使用）的方案。

与其他插件相比，主要优势：

- **极其灵活**：主程序无需升级（无需在Manifest中预埋组件），即可支持新增的四大组件，甚至全新的插件
- **非常稳定**：Hook点**仅有一处（ClassLoader），无任何Binder Hook**！如此可做到其**崩溃率仅为“万分之一”，并完美兼容市面上近乎所有的Android ROM**
- **特性丰富**：支持近乎所有在“单品”开发时的特性。**包括静态Receiver、Task-Affinity坑位、自定义Theme、进程坑位、AppCompat、DataBinding等**
- **易于集成**：无论插件还是主程序，**只需“数行”就能完成接入**
- **管理成熟**：拥有成熟稳定的“插件管理方案”，支持插件安装、升级、卸载、版本管理，甚至包括进程通讯、协议版本、安全校验等
- **数亿支撑**：有360手机卫士庞大的**数亿**用户做支撑，**三年多的残酷验证**，确保App用到的方案是最稳定、最适合使用的

##### 支持特性

| 特性                     | 描述                                     |
| ------------------------ | ---------------------------------------- |
| 组件                     | **四大组件（含静态Receiver）**           |
| 升级无需改主程序Manifest | **完美支持**                             |
| Android特性              | **支持近乎所有（包括SO库等）**           |
| TaskAffinity & 多进程    | **支持（\*坑位方案\*）**                 |
| 插件类型                 | **支持自带插件（\*自识别\*）、外置插件** |
| 插件间耦合               | **支持Binder、Class Loader、资源等**     |
| 进程间通讯               | **支持同步、异步、Binder、广播等**       |
| 自定义Theme & AppComat   | **支持**                                 |
| DataBinding              | **支持**                                 |
| 安全校验                 | **支持**                                 |
| 资源方案                 | **独立资源 + Context传递（相对稳定）**   |
| Android 版本             | **API Level 9+ （2.3及以上）**           |

##### [github官网链接](https://github.com/Qihoo360/RePlugin)

##### 支持情况

一年前已经停止更新

####  5、Shadow插件化框架

##### 介绍

Shadow是一个腾讯自主研发的Android插件框架，经过线上亿级用户量检验。

与其他插件相比，有以下几个特点

- **复用独立安装App的源码**：插件App的源码原本就是可以正常安装运行的。
- **零反射无Hack实现插件技术**：从理论上就已经确定无需对任何系统做兼容开发，更无任何隐藏API调用，和Google限制非公开SDK接口访问的策略完全不冲突。
- **全动态插件框架**：一次性实现完美的插件框架很难，但Shadow将这些实现全部动态化起来，使插件框架的代码成为了插件的一部分。插件的迭代不再受宿主打包了旧版本插件框架所限制。
- **宿主增量极小**：得益于全动态实现，真正合入宿主程序的代码量极小（15KB，160方法数左右）。
- **Kotlin实现**：core.loader，core.transform核心代码完全用Kotlin实现，代码简洁易维护。

##### 支持特性

- 四大组件
- Fragment（代码添加和Xml添加）
- DataBinding（无需特别支持，但已验证可正常工作）
- 跨进程使用插件Service
- 自定义Theme
- 插件访问宿主类
- So加载
- 分段加载插件（多Apk分别加载或多Apk以此依赖加载）
- 一个Activity中加载多个Apk中的View

##### [github官网链接](https://github.com/Tencent/Shadow)

##### 支持情况 

截止到现在还在更新文档

