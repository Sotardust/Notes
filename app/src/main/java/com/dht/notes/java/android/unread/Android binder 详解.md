## Android Binder
Binder是Android系统中进程间通讯（IPC）的一种方式，也是Android系统中最重要的特性之一。
Android中的四大组件Activity，Service，Broadcast，ContentProvider，不同的App等都运行在不同的进程中，它是这些进程间通讯的桥梁。

Android内核是基于Linux系统，而Linux现存多种进程间IPC 方式：管道，消息队列，共享内存，套接字，信号量，信号。

然而考虑到性能与安全性的问题，使用Binder是安卓的最佳解决方案。

![Binder通信机制](https://upload-images.jianshu.io/upload_images/3272986-36cdc1f97a327992.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/486)

![Binder IPC原理](https://upload-images.jianshu.io/upload_images/3272986-2e6aa79ac8306d0c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)
Binder通信采用C/S架构，从组件视角来说，包含Client、Server、ServiceManager以及binder驱动，其中ServiceManager用于管理系统中的各种服务。
Binder 在 framework 层进行了封装，通过 JNI 技术调用 Native（C/C++）层的 Binder 架构，Binder 在 Native 层以 ioctl 的方式与 Binder 驱动通讯。

> Client：用户需要实现的代码，如 AIDL 自动生成的接口类
> Binder Driver：在内核层实现的 Driver
> Server：这个 Server 就是 Service 中 onBind 返回的 IBinder 对象

![Binder驱动](https://upload-images.jianshu.io/upload_images/3272986-83282f057825e119.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)
用户空间中 binder_open(), binder_mmap(), binder_ioctl() 这些方法通过 system call 来调用内核空间 Binder 驱动中的方法。
内核空间与用户空间共享内存通过 copy_from_user(), copy_to_user() 内核方法来完成用户空间与内核空间内存的数据传输。
Binder驱动中有一个全局的 binder_procs 链表保存了服务端的进程信息。

