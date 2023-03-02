Java相关 序号     问题        备注                得分
1线程线程状态多线程线程 池每个参数的含义 AsyncTask 如何停止线程
2：10
2
线程同步 Volatile Synchronized 并发编程三大特性  内存模型 死锁并举例

3
Java 四种引 简述 ReferenceQueue leakCanary  WeakHashMap

4
Jvm 垃圾回收 GcRoot 运行时内存分区 常量池

5
类加载过程 加载过程

双亲委托

什么是双亲委派机制
当某个类加载器需要加载某个.class文件时，它首先把这个任务委托给他的上级类加载器，
递归这个操作，如果上级的类加载器没有加载，自己才会去加载这个类。

双亲委派机制的作用
1、防止重复加载同一个.class。通过委托去向上面问一问，加载过了，就不用再加载一遍。保证数据安全。
2、保证核心.class不能被篡改。通过委托方式，不会去篡改核心.clas，即使篡改也不会去加载，即使加载也不会是同一个.class对象了。
不同的加载器加载同一个.class也不是同一个Class对象。这样保证了Class执行安全。

作者：秦时的明月夜
链接：https://www.jianshu.com/p/1e4011617650
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

Static 属性能否在声明前使用、赋值

ClassLoader

类加载器的类别
BootstrapClassLoader（启动类加载器）

c++编写，加载java核心库 java.*,构造ExtClassLoader和AppClassLoader。由于引导类加载器涉及到虚拟机本地实现细节，
开发者无法直接获取到启动类加载器的引用，所以不允许直接通过引用进行操作

ExtClassLoader （标准扩展类加载器）
java编写，加载扩展库，如classpath中的jre ，javax.*或者
java.ext.dir 指定位置中的类，开发者可以直接使用标准扩展类加载器。

AppClassLoader（系统类加载器）
java编写，加载程序所在的目录，如user.dir所在的位置的class

CustomClassLoader（用户自定义类加载器）
java编写,用户自定义的类加载器,可加载指定路径的class文件


类什么时候会加载

实例通过使用new()关键字创建或者使用class.forName()反射，但它有可能导致ClassNotFoundException。
类的静态方法被调用
类的静态域被赋值
静态域被访问，而且它不是常量
在顶层类中执行assert语句

6
Socket  不调用 close  方法如何通知对方消息发送完成 
定义一个协议 
Http  Tcp https

7
设计模式
生产者、消费者模式
工厂方法模式与抽象工厂模式区别
策略模式
命令模式
Android源码中用到了哪些设计模式
6大原则

8
动态代理
简述
Java 中 能否动态代理类
与静态代理的区别

9
算法
找到一个无序数组中第一次出现最多次数的元素
描述下快速排序的思想，时间复杂度？什么场景对应着最坏情况？

10
网络
简单描述Https的实现思路
TCP/IP四层模型，网络层和传输层有什么区别？网络层的主要工作是什么
socket.accept()函数对应着TCP三次握手中哪一次握手，哪个函数代表连接建立完毕，四次挥手的过程？


11 String 
equals 方法原理
intern 方法

12 泛型 
通配符的使用

13 抽象类与接口区别 

14 基础 
如何判断 A 是否是 B 的子类
如何判断 A 是否是 B 同类型或者父类 classParent.isAssignableFrom(childClass)
getMethods 与 getDeclaredMethods 的区别
Object.wait(millis) 含义
ArrayList 中 的 ConcurrentModificationException 为什么会有这个异常


Android 相关
序号
问题
备注
得分
1
Handler
Message
Looper
MessageQueue
之间的关系
Msg 能否被拦截
Msg 能否放在队首
获取 msg 的方式
Msg 数据结构
Msg 池的数量
为什么不会阻塞主线程
泄露机制
Lopper 生成时机
Lopper 保存方式
如何取消某个 handler 所有message
Looper消息机制，postDelay的Message怎么处理，Looper中的消息是同步还是异步？什么情况下会有异步消息
如何退出 looper，以及内部逻辑
如何监听 MessageQueue 变空
loop 方法内部的逻辑
messageQueue 的 next 方法返回 null 会怎么样
如何给 Message 设置 callback
handler.post 与 View.post 两个方法有什么区别
sendMessage 返回 boolean 值的含义
0：100
0
30
2
ThreadLocal
简单描述
ThreadLocalMap 描述及数据结构，Entry 描述，set 的过程
2： 0
3
HandlerThread
GetLooper 方法
Quit 方法
内部 looper 初始化时机
2：0
4
IntentService
描述
内部 handlerThread 创建时机
如何处理事件
2：20
5
LocalBroadcastManager
内部原理
与 BroadcastReceiver 区别
2: 0
6
进程分类
特点
 
2：10
7
第三方源码
RxJava
a)       操作符
b)      为什么只能第一次 subscribeOn 有效
c)       线程切换的原理
d)      RetryWhen repeatWhen 有什么区别
Retrofit
Eventbus
2.0 与 3.0 的区别
getMethods 与 getDeclaredMethods 的区别
订阅方法参数能否是0个或者2个及以上
对不同优先级的处理
对粘性事件的处理
Arouter
LeakCanary
Glide
Okhttp

8
跨进程
Binder机制的实现思想

9
自定义 View
1.android的屏幕刷新机制，怎么优化UI卡顿情况

10
Touch 事件传递过程
1.事件分发机制的源码

11
Activity A 到 B 的生命周期


12
Gradle 自定义任务


13
Kotlin
扩展函数、属性 及原理
为什么有的高阶函数可以用this，有的必须用 it
注解，
@JvmField、
@JvmStatic、
@JvmOverloads、
@JvmName
4. 如何在 java 中调用 kotlin 的静态方法
5. object 类
6. 数据类 data class
7. 内部类 inner
8. 单例 object
9. 智能转换 as? 加问号与不加问号 as String? 加问号与不加总号

14
优化
1.有做过哪些优化工作，卡顿优化，内存优化，启动优化，APK体积优化，每个做了哪些工作

15
Service
生命周期
stopSelfResult(id) 方法 id 的含义
如何在 Activity 中拿到 Service 的实例
bindService 是否每次都会执行 onBind

16
组件化，插件化，热修复
1.有没有使用过插件化框架，实现原理？

17 Sqlite 
是否支持事务

18 ANR 
条件
时长

19 BroadcastReceiver 
在 onReceiver 方法里开启线程的缺点

这就带来了一个问题：当响应一个广播信息的处理十分耗时的时候，那么就应该把这个处理放在一个单独的线程里去执行，
来保证主线程里的其他用户交互组件能够继续运行，而一旦这么做，当onReceive()唤起一个线程后就会马上返 回，这时就会把Receiver进程放到被终止的境地
。解决这个问题的方案是在onReceive()里开始一个Service，让这个Service去 做这件事情，那么系统就会认为这个进程里还有活动正在进行。
推荐使用IntentService。
