[Android系统进程Zygote启动过程源码分析](http://blog.csdn.net/luoshengyang/article/details/6768304)
1. 系统启动时init进程会创建Zygote进程，Zygote进程负责后续Android应用程序框架层的其它进程的创建和启动工作。

2. Zygote进程会首先创建一个SystemServer进程，SystemServer进程负责启动系统的关键服务，
如包管理服务PackageManagerService和应用程序组件管理服务ActivityManagerService。
    
3. 当我们需要启动一个Android应用程序时，ActivityManagerService会通过Socket进程间通信机制，
    通知Zygote进程为这个应用程序创建一个新的进程