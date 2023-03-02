### 1、像素 注册广播 Intent.action_Screen_ON , 注册home键广播
IntentFilter
static final String SYSTEM_DIALOG_REASON_KEY = "reason";
static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

### 2、进程优先级
1、前台进程：
   1、处于正在与用户交互的activity
   2、与前台activity绑定的service
   3、正在执行OnCreate onResume的Activity或者service
   4、调用了startForeground（）方法的service

2、可视进程
   1、处于前台，但仍然可见（调用了onPause（）而还没调用onStop（））的activity
   2、可见activity绑定的service
 
3、服务进程
   已经启动service

4、后台进程
   不可见的activity（调用onstop（）之后的activity）

5、空进程

###  3、扩展函数 转换Java代码
扩展函数实际上就是一个对应 Java 中的静态函数，这个静态函数参数为接收者类型的对象，
然后利用这个对象就可以访问这个类中的成员属性和方法了，
并且最后返回一个这个接收者类型对象本身。这样在外部感觉和使用类的成员函数是一样的：

### 4、data 与普通类的区别 Component
两相对比，data class比class 多实现了 toString()、hashCode()、equals()、copy()、componentN()方法。
hashCode()、equals()是用来比较对象内容是否相同，多用于HashMap等容器中；
toString()是用来打印对象内容；
copy()实现了复制功能；
componentN()提供了快速访问元素的功能。

### 5、协城状态机

### 6、suspend 如何避免回调

### 4、handler target epoll机制
   nativePollOnce() 进入休眠状态，并释放CPU资源  调用nativeWake() 唤醒线程

### 5、Retrofit 如何停止正在执行的请求，cancel
   通过okhttp call.cancel()方法取消 如何传输一个cancel
   通过 取消socket连接（？）

### 6、Thread 线程如何停止运行
>>> 使用退出标志，使线程正常退出，也就是当run方法完成后线程终止；
   在run方法中 使用while循环 设置flag标志位。
   使用interrupt方法中断线程 ；
   使用stop方法强行终止（不推荐使用该方法，目前该方法已作废）。

线程池关闭的方法：
1、shutdown() 并不是立即关闭线程 执行的任务或者队列中的任务 都执行完后在关闭，如果有新的任务提交，则会直接拒绝。
2、isShutdown()

3、isTerminated() 判断执行线程是不是已经完全终止了。不仅仅是线程池关闭 也代表所有的任务也都执行完了。
4、shutdownNow() 是立即关闭的意思，在执行shutdownNow后会给所有线程发送interrupt终端信号。如果队列中还有任务
则会把任务存储到

当任务过多且线程池的任务队列已满时，此时就会执行线程池的拒绝策略，线程池的拒绝策略默认有以下 4 种：

AbortPolicy：中止策略，线程池会抛出异常并中止执行此任务；
CallerRunsPolicy：把任务交给添加此任务的（main）线程来执行；
DiscardPolicy：忽略此任务，忽略最新的一个任务；
DiscardOldestPolicy：忽略最早的任务，最先加入队列的任务。

### 8、启动一个线程 是否会anr +1
启动一个while 死循环 不会引发ANR ，按键或触摸事件在特定时间内无响应 后无响应，就会引发ANR
超时时间是在ActivityManagerService类中定义的。
// How long we wait until we timeout on key dispatching.
static final int KEY_DISPATCHING_TIMEOUT = 5*1000;

使用adb命令启动 service 是可以启动的

### 9、GCRoot 对象类型

##### 1、虚拟机栈（栈帧中的本地变量表）中引用的对象

a是栈帧中的本地变量，当 a= null 时，由于此时a充当了 GC Root对象，a 与原来指向的 实例 new Test() 断开了连接，所以对象会被回收

```agsl
publicclass Test {
    public static  void main(String[] args) {
    Test a = new Test();
    a = null;
    }
}`
```

##### 2、方法区中类静态属性引用的对象 static

##### 3、方法区中常量引用的对象 final

##### 4、本地方法栈中JNI（Native方法）引用的对象

####  Thread - 活着的线程