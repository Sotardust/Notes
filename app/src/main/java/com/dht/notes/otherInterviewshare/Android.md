[2018年android面试分享和学习总结](https://juejin.im/entry/5ab059d46fb9a028ba1f4ba0)
### 四大组件是什么与它们的生命周期（及fragment）
四大组件：`Activity`,`Service`,`BroadcastReceiver`, `ContentProvider`

|Activity生命周期|Fragment生命周期|service生命周期|service生命周期|
|-|-|-|-|
|           |onAttach()|||
|onCreate()|onCreate()|onCreate()|onCreate()|
|          |onCreateView()|||
|         |onActivityCreated()|||
|onStart() |onStart()|||
|onRestart()|onResume()|onStartCommand()|onBind()|
|onResume()|onPause()|                  |onUnbind()|
|onPause() |onStop()|||
|onStop()  |onDestroyView()|||
|onDestroy()|onDestroy()|onDestroy()|onDestroy|
|           |onDetach()|||

service 服务参见 notes/android笔记

### Activity的四种启动模式与特点
**1.Standard 模式** 特点：每次激活的Activity是都会创建Activity并放入任务栈中，使用场景：Activity  

**2.SingleTop模式**
特点：如果任务栈顶存在改Activity的实例，则重用该实例额，否则就会创建新的实例放入该栈顶，即使该Activity的实例已经存在（只要不在栈顶）
使用场景：新闻类或者阅读类App的内容页面  

**3.SingleInstance模式**  
特点：在一个新栈中，创建该Activity的实例，并让多个应用共享该栈中的实例
一旦该模式的 Activity实例已经存在某个栈中，任何应用再激活Activity是会重用该栈中的实例（会调用该栈中的``onNewInstance`）  
使用场景：闹钟模式  

**4.SingleTask模式**  
特点：如果该栈中已经存在该Activity的实例，就重用该实例（会调用改实例的OnNewInstance）重用是会让该实例回到栈顶，在它上面的实例将会被移出，如果栈中不存在该实例则会创建新的实例放入栈中  
使用场景：浏览器，MainActivity

### Activity 状态保存与恢复
**onSaveInstanceState()**  
onSaveInstanceState() 方法用来在Activity被强制销毁之前保存数据，onSaveInstanceState()方法会携带一个 Bundle类型的参数，Bundle提供了一系列的方法用于保存数据，比如可以使用 putString()方法保存字符串，使用 putInt()方法保存整型数据。每个保存方法需要传入两个参数，第一个参数是键，第二个参数是真正要保存的内容。  
 
**onRestoreInstanceState()**  
onSaveInstanceState() 方法用来取得之前在onSaveInstanceState() 保存的值。
另外，除了onRestoreInstanceState()可以取得onSaveInstanceState() 保存的值之外，onCreate()函数也可以取得保存的值，这些值就存在onCreate()函数的参数savedInstanceState里，在哪个函数取出这些值就要看具体的需求了。
### service和activity怎么进行数据交互。
>onCreate()方法是服务创建的时候调用的  
>onStartCommand()方法在每次启动服务的时候都会调用~  
>onDestory()方法在停止服务时候会调用~  

1.使用接口回调方式，activity实现相应的接口，service通过接口进行回调，比较灵活
2.使用广播



### 怎么保证service不被杀死。
**1、onStartCommand 返回START_STICKY**  

StartCommand 几个返回常量参数  
1、`START_STICKY`
在运行onStartCommand后service进程被kill后，那将保留在开始状态，但是不保留那些传入的intent。
不久后service就会再次尝试重新创建，因为保留在开始状态，在创建    
 service后将保证调用onstartCommand。如果没有传递任何开始命令给service，那将获取到null的intent。  

2、`START_NOT_STICKY`
在运行onStartCommand后service进程被kill后，并且没有新的intent传递给它。
Service将移出开始状态，并且直到新的明显的方法（startService）调用才重新创建。
因为如果没有传递任何未决定的intent那么service是不会启动，
也就是期间onstartCommand不会接收到任何null的intent。
  
3、`START_REDELIVER_INTENT`
在运行onStartCommand后service进程被kill后，系统将会再次启动service，
并传入最后一个intent给onstartCommand。直到调用stopSelf(int)才停止传递intent。
如果在被kill后还有未处理好的intent，那被kill后服务还是会自动启动。
因此onstartCommand不会接收到任何null的intent。  

**2、提升service优先级**
在AndroidManifest.xml文件中对于intent-filter可以通过android:priority = "1000"这个属性设置最高优先级，1000是最高值，如果数字越小则优先级越低，同时适用于广播
  目前看来，priority这个属性貌似只适用于broadcast，对于Service来说可能无效  
  
**3、提升service进程优先级**
Android中的进程是托管的，当系统进程空间紧张的时候，会依照优先级自动进行进程的回收Android将进程分为6个等级,它们按优先级顺序由高到低依次是:
>1.前台进程(FOREGROUND_APP)  
>2.可视进程(VISIBLE_APP)  
>3.次要服务进程(SECONDARY_SERVER )  
>4.后台进程 (HIDDEN_APP)  
>5.内容供应节点(CONTENT_PROVIDER)  
>6.空进程(EMPTY_APP)  

**4、onDestroy方法里重启service**  
service+broadcast方式，就是当service走onDestroy的时候，发送一个自定义的广播，
当收到广播的时候，重新启动service；

**5、Application加上Persistent属性**
看Android的文档知道，当进程长期不活动，或系统需要资源时，会自动清理门户，杀死一些Service，
和不可见的Activity等所在的进程。但是如果某个进程不想被杀死
（如数据缓存进程，或状态监控进程，或远程服务进程）

**6、监听系统广播判断Service状态**
通过系统的一些广播，比如：手机重启、界面唤醒、应用状态改变等等监听并捕获到，
然后判断我们的Service是否还存活，记得加权限。

**7、将APK安装到/system/app，变身系统级应用**

### 广播使用的方式和场景以及广播的几种分类。
广播的使用方法：静态注册，动态注册  
使用场景：  
1.同一app内部的同一组件内的消息通信（单个或多个线程之间）； 
2.同一app内部的不同组件之间的消息通信（单个进程）； 
3.同一app具有多个进程的不同组件之间的消息通信；   
4.不同app之间的组件之间消息通信；     
5.Android系统在特定情况下与App之间的消息通信。  
分类：  
>普通广播（Normal Broadcast）  
系统广播（System Broadcast）   
有序广播（Ordered Broadcast）按照Priority属性值从大-小排序；Priority属性相同者，动态注册的广播优先； 
粘性广播（Sticky Broadcast）    
App应用内广播（Local Broadcast）

### Intent的使用方法，可以传递哪些数据类型。
1.简单或基本数据类型  
2.传递一个Bundle   
3.传递Serializable对象  
4.传递Parcelable对象  
5.传递Intent


### Android两种序列化的区别和作用。

|区别|Serializable|Parcelable|
|-|-|-|
|所属API	|JAVA API|Android SDK API|
|原理|序列化和反序列化过程需要大量的I/O操作|序列化和反序列化过程不需要大量的I/O操作|
|开销|开销大|开销小|
|效率|低|很高|
|使用场景|序列化到本地或者通过网络传输|内存序列化|

作用：  
Serializable的作用是为了保存对象的属性到本地文件、数据库、网络流、rmi以方便数据传输，当然这种传输可以是程序内的也可以是两个程序间的。  
Android的Parcelable的设计初衷是因为Serializable效率过慢，为了在程序内不同组件间以及不同Android程序间(AIDL)高效的传输数据而设计，  
这些数据仅在内存中存在，Parcelable是通过IBinder通信的消息的载体。

### 内存泄漏和内存溢出区别
**内存泄露**（memory leak） 原理是，使用过的内存空间没有被及时释放，长时间占用内存，最终导致内存空间不足，而出现内存溢出。

**内存溢出** （out of memory）是指你的应用的内存已经不能满足正常使用了，堆栈已经达到系统设置的最大值，进而导致崩溃。
  
 内存溢出就是内存越界。内存越界有一种很常见的情况是调用栈溢出（即stackoverflow），虽然这种情况可以看成是栈内存不足的一种体现。但内存溢出并不一定跟内存分配有什么关系，因为还有一种情况是缓冲区溢出


**内存溢出的原因：**  
1、内存泄漏导致
由于长期保持某些资源（如Context）引用，垃圾回收器就无法回收它，导致该对象占用的内存就无法被使用，这就造成了内存泄漏。  

2、占用内存较多的对象
保存了多个耗用内存过大的对象（如Bitmap） 或加载单个超大的图片，造成内存超出限制


**内存泄露的原因：**  
1、资源对象没关闭  如cursor，file等资源  
2、使用Adapter时，没有使用系统缓存的convertView  
3、没有及时调用recycle()释放不再使用的bitmap  
4、使用application的content来替代Activity相关的context 不要让生命周期长于Activity的对象持有到Activity的引用  
5、广播注册没有取消造成内存泄漏  
6、Handler应该申明为静态对象，并在其内部类中保存一个对外部类的弱引用

**二者联系 内存泄漏最终会导致内存溢出**


































