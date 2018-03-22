### 一、多线程交互方式
    1、activity.runOnUIThread(Runnable) 
    2、handler
    3、View.post(Runable)
    4、View.PostDelayed(Runnable,long)
    5、AsyncTask
### 多线程的实现方法
    1、继承Thread
    2、实现Runnable接口
### 二、handler执行过程图片展示
    handler 图片.png
### 三、Handler机制
[Android-Handler机制详解](http://www.cnblogs.com/dendai-05/p/6945159.html)
    handler用于解决多线程并发问题
    用法：
    1、传递Message 用于接收子线程发送的数据并更新UI
    2、传递Runnable 对象
    3、传递callback对象
### 四、原理
    Handler 封装了消息的发送
    1、Looper.loop()方法 for循环
    2、MessageQueue 消息队列 添加处理消息
    
    handler 发送消息 Looper负责接收handler发送的消息 并把消息传递会handler自己，MessageQueue是存储消息的容器
    一个线程中只有一个Looper实例，一个MessageQueue实例 可以有多个Handler
### 五、其他
    在ActivityThread.main中创建于looper
    在New HandlerThread 可创建Looper     两者不同
    
### 六、Android如何保证一个线程 中只有最多只有一个Looper？
    使用了ThreadLocal 其位于Java.lang 包中
    ThreadLocal 实现了线程本地存储所有线程共享一个ThreadLoacl对象，不同线程仅能访问与其线程相关的值，
    一个线程修改ThreadLocal 对其他线程没有影响
### ThreadLocal 工作原理
    ThreadLocal是一个线程内部的数据存储类 并不是线程
    [link](http://blog.csdn.net/imzoer/article/details/8262101)
