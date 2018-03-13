# Android
### Android 四种加载或启动模式（launchMode）
1、standard 模式（默认）
        每次激活的Activity是都会创建Activity并放入任务栈中，
        使用场景：Activity  
2、SingleTop 模式
        如果在任务栈的栈顶存在改Activity实例，则重用该实例，否则 就会创建新的实例放入改栈顶
        即使该activity的实例已经存在（只要不在栈顶）
        使用场景：新闻类或者阅读类App的内容页面  
3、SingleInstance 模式:  
在一个新栈中 创建该activity的实例，并让多个应用共享该栈中的实例。
        一旦该模式的activity实例已经存在某个栈中，任何应用再激活activity时会重用该栈中的实例
        （会调用该栈中的onNewInstance）
        使用场景：闹钟提醒  
4、SingleTask 模式
        如果该栈中已经存在该activity的实例，就重用改实例（会调用该实例的OnNewInstance）
        重用时会让该实例回到栈顶，在它上面的栈都会被移出，如果栈中不存在该实例，则会创建新的实例放入栈中
        使用场景：浏览器，MainActivity中
### View的绘制流程
measure过程
layout过程
ondraw过程
### Android多线程实现更新UI的几种方式
> 1、RunOnUIThread  
> 2、handle的post方法  
> 3、handle的sendMessage  
> 4、view自身的post  
> 5、view自身的postDelay() 
### Handle
Android主线程中只有一个looper 一个MessageQueue,
    在ActivityThread.main中创建于looper
    在New HandleThread 可创建Looper 
    上述二者不用
    
Android如何保证一个线程中最多只有一个looper
    ThreadLocal 位于java.lang包中
    ThreadLocal实现了线程本地存储，所有线程共享一个ThreadLocal对象，不同线程新能访问
    与其线程相关的值，一个线程修改ThreadLocal对象对其他线程没有影响。ThreadLocal 类
    
**Android通信机制中Message、Handler、MessageQueen、Looper之间的关系**
    
1、首先是这个MessageQueue，MessageQueue是一个消息队列，它可以存储Handler发送过来的消息，其内部提供了进队
       和出队的方法来管理消息队列，其出队和进队的原理是采用单链表的数据结构你进行插入和删除的，即enqueueMessage()
       方法和next()方法、这里的Message，其实就是一个Bean对象，里面的属性用来记录Message的各种信息。
       
2、然后是这个Looper，Looper是一个循环器，它可以循环的取出MessageQueen中的Message，其内部提供了Looper的
       初始化和循环出去的Message的方法，即prepare()方法和loop()方法。
       在prepare()方法中，Looper会关联一个MessageQueue，而且将Looper存进一个ThreadLocal中，在loop()方法中
       通过ThreadLocal取出Looper，使用MessageQueue的next()方法取出Message后，判断Message是否为空，如果是则
       Looper阻塞，如果不是，则通过dispatchMessage()方法分发该Message到Handler中，而Handler执行
       handlerMessage(),由于handlerMessage()方法是个空方法，这也是为什么需要在Handler中重写handlerMessage()
       方法的原因。这里需要注意的是Looper只能在一个线程中只能存一个。这里提到的ThreadLocal，其实就是一个对象，
       用来在不同线程中存放对应线程的Looper。
       
3、最后是这个Handler，Handler是Looper和MessageQueue的桥梁，Handler内部提供了发送Message的一系列方法，
       最终会通过MessageQueue的enqueueMessage()方法将Message存进MessageQueue中，我们平时可以直接在主线程中
       使用handler，那是因为在应用程序启动时在入口ActivityThread.main方法中已经默认我们创建好了Looper。
       
    
### 多线程实现方法

实现方法

        1、Activity.RunOnUIThread(Runable)
        2、View.post(View) View.postDelay(Runable,Long)
        3、Handle
        4、AsyncTask
        5、java线程池
多线程的本质异步处理，直观上说是为了不让用户感到卡顿
    
多线程创建实现方法：implement Runable 或 extend Thread
    
    常见线程创建方式有两种：
        1、继承Thread类，重写Thread的run()方法
        2、实现Runnable接口，重写Runnable的run()方法，并将其作为参数实例化Thread
        第三种方式：
        3、通过实现Callable接口来创建线程（参考链接：https://juejin.im/post/5a7b2c8b6fb9a0633a70eb54）
        
    以上两者的联系：
        1、Thread类实现了Runnable接口
        2、都需要重写里面的run方法
        
    两者的区别：
        1、实现Runnable的类更具有健壮性，避免了单继承的局限
        2、Runable更容易实现资源共享，能多个线程同时处理一个资源
    
    多线程的核心机制：Handler
### 线程池ThreadPoolExecutor  
    为什么要引入线程池：
        new Thread()的缺点
        • 每次new Thread()耗费性能
        • 调用new Thread()创建的线程缺乏管理，被称为野线程，而且可以无限制创建，之间相互竞争，
          会导致过多占用系统资源导致系统瘫痪
        • 不利于扩展，比如如定时执行、定期执行、线程中断
        
    采用线程池的优点 
    • 重用存在的线程，减少对象创建、消亡的开销，性能佳 
    • 可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竞争，避免堵塞 
    • 提供定时执行、定期执行、单线程、并发数控制等功能    
    
### ANR （ Application not Responding ）应用程序无响应
在Android中Activity的执行时间5秒，BroadCastReceiver最长执行时间为10秒
引发ANR的原因 ：
    1、在5秒内没有相应输入事件（按键按下，屏幕触摸）
    2、BroadCastReceiver 在10秒内没有执行完毕
    造成上述两点的原因：
        1、在主线程中做了耗时的操作（如下载，io异常，网络请求，数据库操作，高耗时操作）
        2、service在特定时间内无法完成（20s）
            
如何避免ANR：
    1、减少在主线程中做耗时操作，尽量在子线程中去执行耗时任务，比如使用Handler+message
    2、采用asynTask（异步任务的方式，底层是Handler+Message）有所区别线程池
        
如何分析ANR：
    1、log日志 ->查看cpu负载与cpu使用率
    2、trace.txt文件（存储ANR信息 db 文件）
    3、查看代码        
### service
后台执行，组件可以与service绑定 并与之交互
甚至是跨进程通信（IPC）
    
service两种启动形式
    1、startService() 方法启动一个service     //需要手动停止
    2、bindService()  其他组件调用bindService()绑定一个service多个组件可以与一个service绑定 //自动停止
        
    回调方法执行 
        startService回调 onStartCommand()   
        bindService回调onBind()方法
        
        某组件调用startService() 需要手动停止服务 stopSelf() stopService()
        某组件调用bindService() 绑定service(系统不会回调onstartCommand()方法，会调用onBind()方法)
        只要组件与Service处于绑定状态，service会一直运行下去当service不在于组件绑定咋改service将被销毁destroy
        
        IntentService（一次性服务） 当请求处理完成后自动停止service
        
    前台Service
        前台service 用于动态通知消息，如天气预报播放音乐
### 性能优化
一、内存优化
1、尽量在使用service时才让其处于运行状态，尽量使用IntentService能节省系统资源
   （IntentService在内部都是用过线程和Handler来实现的，当有新的Intent到来时，会创建线程
   并处理这个Intent处理完之后自动销毁自身）
   
2、内存紧张时释放资源（例如UI隐藏时释放资源）
        
3、避免Bitmap的浪费，应尽量去适配屏幕设备，尽量使用成熟的图片框架。比如Picasso，fresco，glide等
        
4、使用优化的容器 例如sparseArray
        
5、避免内存泄漏（本应被回收的对象未被回收）一旦App的内部短时间内快速增长或者GC非常频繁的时候
   就应该考虑是否是内存泄漏导致的
           
二、布局优化
    1、使用include标签 复用相同布局
    2、尽量使用Relativelayout 减少视图层级 
        
什么情况下会导致内存泄漏：
     1、资源释放问题：
            长期保持某些资源，如content，cursor，IO流的引用资源得不到释放造成内存泄漏
     2、对象内存过大问题：
            保存了多个耗用内存过大的对象（如Bitmap，xml文件）造成内存超出限制
     3、static关键字的使用：
            使用static修饰的变量生命周期比较长
     4、线程导致内存溢出：
             线程产生内存泄漏的主要原因在于线程生命周期的不可控
             
避免OOM的常见方法：
1、App资源中尽量少使用大图 使用bitmap时要注意按照比例缩小图片并注意bitmap回收
        
2、结合生命周期去释放资源
        
3、I/O流，数据库游标，使用完成后应及时释放掉
        
4、listview中使用viewHolder缓存convertview
        
5、页面切换尽量去传递（复用一些对象）
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
    
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        
    