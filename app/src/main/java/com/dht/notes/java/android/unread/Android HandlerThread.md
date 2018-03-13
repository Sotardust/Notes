### HandlerThread

参考文章：[为什么要使用HandlerThread](http://blog.csdn.net/tianmaxingkong_/article/details/52039952)

上面就是子Thread如何通过Handler与UIThread通信的过程，即Handler的机制，那么问题来了，
上面的过程从始至终都是子线程向UIThread发送信息，并没有反过来的过程，也就是UIThread如何给子线程发送Handler信息


使用HandlerThread的原因：
一般情况下，我们会经常用Handler在子线程中更新UI线程，那是因为在主线程中有Looper循环，而HandlerThread新建拥有Looper的子线程又有什么用呢？ 
必然是执行耗时操作。举个例子，数据实时更新，我们每10秒需要切换一下显示的数据，如果我们将这种长时间的反复调用操作放到UI线程中，虽说可以执行，但是这样的操作多了之后，很容易会让UI线程卡顿甚至崩溃。 
于是，就必须在子线程中调用这些了。 

HandlerThread继承自Thread，一般适应的场景，便是集Thread和Handler之所长，适用于会长时间在后台运行，并且间隔时间内（或适当情况下）会调用的情况，比如上面所说的实时更新。