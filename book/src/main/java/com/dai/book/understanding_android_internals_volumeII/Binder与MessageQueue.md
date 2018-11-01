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




