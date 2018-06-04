### singleTop 中newIntent 调用问题
在singleTop ，singleInstance，singleTask中 若实例在各任务栈栈顶时 ，将会调用onNewIntent()方法而不会调用onCreate方法
若在singleTop模式下，且实例不在任务栈栈顶，则将会执行onCreate方法
### onSaveInstanceState与onRestoreInstanceState触发时机
onSaveInstanceState()方法的触发时机  
1. 当用户按下Home键时
2. 长按Home键选择运行其他的程序时
3. 按下电源键（关闭屏幕时）
4. 从当前一个Activity启动一个新的Activity时

5. 屏幕方向切换时

> 在前四种情况下，当前Activity的生命周期为:  
> onPause->onSaveInstanceState->onStop。

onRestoreInstanceState()方法的触发时机
1. onRestoreInstanceState()方法只有在Activity确实是被系统回收，重新创建Activity的情况下才会被调用
2. 在屏幕方向切换时 Activity的生命周期如下：
 ```
 onPause -> onSaveInstanceState -> onStop -> onDestroy -> onCreate -> onStart -> onRestoreInstanceState -> onResume
 
 ```   

### https
### 静态内部类的特点

静态内部类是指被声明为static的内部类，不能访问外部类的普通成员变量，只能访问外部类中的静态成员变量和静态方法
静态内部类作用：只是为了降低包的深度，方便类的引用，静态内部类适用于包含类中，但又不依赖与外在的类，不使用外在类的非静态属性和方法，只是为了方便管理类的结构而定义
，在创建静态内部类的时候，不需要外部类对象的引用。

非静态内部类有一个很大的优点：可以自由使用外部类的所有变量和方法

从字面上看，一个被称为静态嵌套类，一个被称为内部类。  
从字面的角度解释是这样的：  
什么是嵌套？嵌套就是我跟你没关系，自己可以完全独立存在，但是我就想借你的壳用一下，来隐藏一下我自己（真TM猥琐）。  
什么是内部？内部就是我是你的一部分，我了解你，我知道你的全部，没有你就没有我。（所以内部类对象是以外部类对象存在为前提的）  

### 双层ViewGroup 侧边滑动问题

在外层ViewGroup中 重写onInterceptTouchEvent方法 
把x坐标与屏幕宽进行差值对比相差50 则返回值设置为false 

### 硬件加速 绘制View 与程序绘制View的不同
[Android 4.0的图形硬件加速及绘制技巧](http://blog.51cto.com/zuiniuwang/721798)

### RecycleView是如何优化的
一、标准化了ViewHolder，使用Adapter适配器时，面向ViewHolder而不是单纯的View，直接把ViewHolder的实现封装起来，  
用户只要实现自己的ViewHolder就可以了，该组件会自动帮你回收并复用每一个item。  
不但变得更精简，也变得更加容易使用，而且更容易组合设计出自己需要的滑动布局。  
二、将Layout抽象成了一个LayoutManager，RecyclerView不负责子View的布局，
而是通过使用LayoutManager来实现不同的布局效果，如使用LinearLayoutManager来指定方向，
其默认是垂直，也可以设置为水平，当然你也可以自己来定义。

### 线程池
Android中的线程池的概念来源于Java中的Executor，Executor是一个接口，真正的线程池的实现为ThreadPoolExecutor，ThreadPoolExecutor提供了一系列参数来配置线程池，通过不同的参数可以创建不同的线程池。

优点：
> 1. 复用线程池中的线程，避免因为线程的创建和销毁所带来的性能开销。
> 2. 能够有效的控制线程池的最大并发数，避免大量的线程之间以互相抢占系统资源而导致的阻塞现象
> 3. 能够对线程进行简单的管理，并提供定时执行以及指定间隔循环执行等功能

** 线程池的构造方法 **
```
 public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                    BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory)
```
1. CorePoolSize :核心线程数
    默认情况下，核心线程数会在线程中一直存活，即使它们处于闲置状态。  
    如果将ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，那么核心线程就会存在超时策略，这个时间间隔有keepAliveTime所决定，当等待时间超过keepAliveTime所指定的时长后，核心线程就会被停止。
    
2. maximumPoolSize ：所能容纳最大线程数
    当活动线程数达到这个数值后，后续的新任务将会被阻塞。
    
3. ThreadPoolExecutor ： 构造方法
4. workQueue ：线程池中的任务队列
    线程池中的任务队列，通过线程池execute方法提交的Runnable对象会存储在这个参数中。
    这个任务队列是BlockQueue类型，属于阻塞队列，就是当队列为空的时候，此时取出任务的操作会被阻塞，等待任务加入队列中不为空的时候，才能进行取出操作，而在满队列的时候，添加操作同样被阻塞。
5. KeepAliveTime ：非核心线程池超时时长
    非核心线程闲置时的超时时长，超过这个时长，非核心线程就会被回收，当ThreadPoolExector的allowCoreThreadTimeOut属性设置为True时，keepAliveTime同样会作用于核心线程。
6. unit ： 超时的时间单位
    常用的有TimeUnit.MILLISECONDS（毫秒）、TimeUnit.SECONDS(秒)以及TimeUnit.MINUTES(分钟)等。
7. threadFactory 线程工程，为线程池提供新线程
    线程工厂，为线程池提供创建新线程的功能。ThreadFactory是一个接口，它只有一个方法，newThread（Runnable r），用来创建线程。
