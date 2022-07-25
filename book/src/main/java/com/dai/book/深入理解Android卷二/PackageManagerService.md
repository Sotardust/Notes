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


PKMS 构造函数在第一阶段的工作：扫描并解析XML文件，将其中的信息保存到特定的数据结构中。

### 构造函数分析之扫描Package

PKMS 构造函数在第二阶段的工作：就是扫描系统中的APK文件，由于需要逐个扫描文件，因此手机上装的程序越多
，PKMS的工作量就越大，系统启动速度也就越慢。

1、系统库的dex优化

PKMS 将扫描一下几个目录：
1、/system/frameworks :该目录的文件都是系统库， 不过scanDirLI只扫描APK文件
2、/system/app:该目录下全是默认的系统应用。
3、/vendor/app:该目录中的文件有厂商提供，即厂商特定的APK文件，不过厂商都把自己的应用都放在了/system/app目录下

PackageManagerService类中的 scanDirLI 函数用于扫描APK文件

PackageParser 主要负责APK文件的解析，即解析APK文件中的AndroidManifest.xml代码

PKMS第三阶段的工作：将第二极端手机的信息再次集中整理一次 比如将有些信息保存到文件中。


### queryIntentActivities 分析

PKMS 除了负责Android系统中的Package的安装、升级、卸载外，还有一项很重要的职责，
即对外提供同意的信息查询功能，其中包括查询系统中匹配某Intent的Activities、BroadCastReceiver
或Services等，


### Intent 与IntentFilter

##### Intent
Intent可有两方面属性来衡量
1、 主要属性：包括Action和Data。其中Action用于表示该Intent所表达的动作意图，Data用于
表示该Action所操作的数据。
2、次要属性： 包括Category、type、Component和Extras。 其中Category表示类别，Type表示数据的MIME类型
，Component可用于指定特定的Intent响应者（例如指定广播接收者为某Package的某个BroadCastReceiver），
Extras用于承载其他的信息。

##### IntentFilter

Action：支持的Intent动作（和Intent中的Action对应）。
Category：支持的Intent种类（和Intent的Category对应）。
Data：支持Intent的数据（和Intent的Data对应包括URI和MIME类型）。

匹配步骤：
1、首先匹配IntentFilter的Action，如果Intent设置的Action不满足IntentFilter的Action，则匹配失败
。如果IntentFilter未设定Action，则匹配成功。
2、然后检查IntentFilter的Category，匹配方法同Action的匹配。唯一例外的是Category为CATEGORY_DEFAULT的情况
3、最后检查Data Data的匹配过程比较繁琐，因为它和IntentFilter设置的Data内容有关。

































