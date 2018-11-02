它负责系统中Package的管理，应用程序的安装、卸载、信息查询等。

PKMS（PackageManagerService简写）

PKMS的核心是main函数

main函数只有几行代码，但执行时间比较长，主要原因是PKMS在其构造函数中做了很多的操作
这是Android启动速度慢的主要原因之一。

#### PKMS构造函数的主要功能
扫描Android系统中几个目标文件夹中的APK,从而建立合适的数据结构以管理如package信息、
四大组件信息、权限信息等各种信息。

PKMS的工作流程相对简单，复杂的是其中用于保存各种信息的数据结构，和他们之间的关系
，以及影响最终结果的策略控制

### PKMS构造函数的工作流程
> 扫描目标文件夹之前的准备工作
> 扫描目标文件下
> 扫描之后的工作



Settings 中有一个mSharedUsers成员，该成员存储的是字符串与SharedUserSetting键值对
也就是说以字符串为key得到对应的SharedUserSetting对象

在xml中声明一个为android：sharedUserId 的属性，其值为“android.uid.system”。
sharedUserId和UID有关

1、两个或者多个声明了同一种sharedUserId的APK可共享彼此的数据，并且可运行在统一进程中

2、更重要的是，通过声明特定的sharedUserId，该APK所在进程将被赋予指定的UID

