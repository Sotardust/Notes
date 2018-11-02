### SystemServer 概述
SystemServer是Android java 的两大支柱。一个是专门负责孵化Java 进程的 Zygote，
而SystemServer进程是有Zygote进程孵化出来的。
其进程名为：system_server 在DDMS中看其进程名为：system_process。

SystemServer的核心逻辑入口为main函数。


dumpsys 这个命令用于打印系统中指定服务的信息

dumpsys diskstats :打印系统中内部存储设备的使用情况
[Android dumpsys命令详细使用](https://www.cnblogs.com/JianXu/p/5376642.html)