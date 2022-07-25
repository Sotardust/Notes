### java层Binder架构
 对于代表客户端的BinderProxy来说，Java层的BinderProxy在Native层对应一个BpBinder对象。
 凡是从java层发出的请求，首先从java层的BinderProxy传递到Native层的BpBinder，继而由BpBinder将请求
 发送到Binder驱动。
 对于代表服务端的Service来说，Java层的Binder在Native层有一个JavaBBinder对象。 所有Java层的Binder在Native层都应为
 JavaBBinder，而JavaBBinder仅起到中转作用，即把来自客户端的请求从Native层传递到Java层。
 系统中依然只有一个Native的ServiceManager

### MessageQueue 消息队列

MessageQueue类封装了与消息队列有关的操作，消息队列与消息处理循环
从Android 2.3开始，MessageQueue的核心部分下移至Native层。

***Native层中的Looper虽然它的类名和Java层中的Looper类一样，但此二者并无任何关系。***


epoll机制提供了Linux平台上最高效的I/O复用机制。

##### epoll的效率比select高的原因
其中一个原因为调用方法，每次调用select时需要吧感兴趣的事情都复制到内核中，而epoll只在epll_ctl进行加入操作的时候才复制一次。
另外epoll内部用于保存数据结构使用的是红黑树，查找速度很快。而select 采用数组保存信息，不但一次能 等待的句柄个数有限，
并且查找速度慢（等待的事件较多时会这样）。当等待的数量较少时二者效率相差很小。



### MessageQueue 处理流程

1、MessageQueue继续支持来自java层的Message消息，也就是说早期的Message加Handler的处理方式，
2、MessageQueue在Native层的代表NativeMessageQueue 支持赖在Native层的Message，是通过Native的Message和MessageHandler处理的
3、NativeMessageQueue还处理通过addFd添加的Request。
4、从处理逻辑上看，先是处理Native的Message，然后是处理Native的Request，最后才是处理Java层的Message






