####  前言
![Handler消息传递机制.png](https://upload-images.jianshu.io/upload_images/4954278-00663bb943e00375.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
[图片来源](https://github.com/guoxiaoxing/android-interview/blob/master/doc/Android%E9%9D%A2%E8%AF%95%E9%A2%98%E9%9B%86.md)

####  源码分析
首先创建Handler
```
Handler handler  = new Handler();
```
查看Handler.java$handler()构造方法的核心源码
```
    public Handler() {
        this(null, false);
    }
   public Handler(Callback callback, boolean async) {
        
        ...... //代码省略

        // mLooper是一个Looper对象，获取Looper
        mLooper = Looper.myLooper();
        //在主线程中可以直接创建Handler，原因下面会分析，
        //在子线程中需要先初始化Looper也就是调用Looper.prepare()方法,否则就会报下面异常
        if (mLooper == null) {
            throw new RuntimeException(
                "Can't create handler inside thread that has not called Looper.prepare()");
        }

        //mQueue是一个MessageQueue对象
        mQueue = mLooper.mQueue;
        mCallback = callback;
        mAsynchronous = async;
    }
```
上面说到在主线程中可以直接创建Handler，其原因是因为应用程序的入口为ActivityThread类的main方法。

查看ActivityThread.java$main方法的核心源码
```
 public static void main(String[] args) {
        
        ...... //代码省略

        //创建主线程的Looper
        Looper.prepareMainLooper();

        ActivityThread thread = new ActivityThread();
        thread.attach(false);

        if (sMainThreadHandler == null) {
            sMainThreadHandler = thread.getHandler();
        }

        if (false) {
            Looper.myLooper().setMessageLogging(new
                    LogPrinter(Log.DEBUG, "ActivityThread"));
        }

        //对MessageQueue消息进行循环将取出的Message交付给相应的Handler ，后面会对其源码进行分析
        Looper.loop();

        throw new RuntimeException("Main thread loop unexpectedly exited");
    }
```
上面解释了在主线程创建Handler是因为在程序创建的时候已经创建了主线程的Looper 。
 
我们知道Handler要发送消息的话需要调用sendMessage方法
那我们接着查看Handler.java$sendMessage方法源码
```
 public final boolean sendMessage(Message msg)
    {
        return sendMessageDelayed(msg, 0);
    }
//继续查看
public final boolean sendMessageDelayed(Message msg, long delayMillis)
    {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
    }
//继续查看
public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }
//继续查看
private boolean enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis) {
        //msg.target是一个Handler对象，this指的就是Handler因为该方法在Handler类中
        msg.target = this;
        if (mAsynchronous) {
            msg.setAsynchronous(true);
        }
        //这里调用的是MessageQueue消息队列的enqueueMessage方法
        return queue.enqueueMessage(msg, uptimeMillis);
    }
```
上面说到msg.target是handler 我们查看Message.target源码
```
      Handler target;
```
发现target确实是Handler对象，其作用是为了记录所发消息对应的handler,也是为了把消息分发到对应的Handler

继续分析MessageQueue消息队列enqueueMessage方法源码
```
  boolean enqueueMessage(Message msg, long when) {
        if (msg.target == null) {
            throw new IllegalArgumentException("Message must have a target.");
        }
        if (msg.isInUse()) {
            throw new IllegalStateException(msg + " This message is already in use.");
        }

        synchronized (this) {
             //判断是否调用了quit()方法如果调用了则无法发送消息
            if (mQuitting) {
                IllegalStateException e = new IllegalStateException(
                        msg.target + " sending message to a Handler on a dead thread");
                Log.w("MessageQueue", e.getMessage(), e);
                msg.recycle();
                return false;
            }

            msg.markInUse();
            msg.when = when;
            // 把传进来的message按照延迟时间的先后添加到mMessage中
            Message p = mMessages;
            boolean needWake;
            if (p == null || when == 0 || when < p.when) {
                // New head, wake up the event queue if blocked.
                msg.next = p;
                mMessages = msg;
                needWake = mBlocked;
            } else {
                // Inserted within the middle of the queue.  Usually we don't have to wake
                // up the event queue unless there is a barrier at the head of the queue
                // and the message is the earliest asynchronous message in the queue.
                needWake = mBlocked && p.target == null && msg.isAsynchronous();
                Message prev;
                for (;;) {
                    prev = p;
                    p = p.next;
                    if (p == null || when < p.when) {
                        break;
                    }
                    if (needWake && p.isAsynchronous()) {
                        needWake = false;
                    }
                }
                msg.next = p; // invariant: p == prev.next
                prev.next = msg;
            }

            //如果Looper.loop()是休眠状态则执行nativeWake方法唤醒Looper
            if (needWake) {
                nativeWake(mPtr);
            }
        }
        return true;
    }
```
在Looper.prepare()的同时，总会执行looper.loop()语句与之对应。
接着查看Looper.java$loop方法的源码
```
 public static void loop() {
        //获取looper对象确保Looper的唯一性
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        final MessageQueue queue = me.mQueue;

        // Make sure the identity of this thread is that of the local process,
        // and keep track of what that identity token actually is.
        Binder.clearCallingIdentity();
        final long ident = Binder.clearCallingIdentity();
        //循环并分发message
        for (;;) {
            Message msg = queue.next(); // might block
            if (msg == null) {
                // No message indicates that the message queue is quitting.
                return;
            }

            // This must be in a local variable, in case a UI event sets the logger
            final Printer logging = me.mLogging;
            if (logging != null) {
                logging.println(">>>>> Dispatching to " + msg.target + " " +
                        msg.callback + ": " + msg.what);
            }

            final long traceTag = me.mTraceTag;
            if (traceTag != 0 && Trace.isTagEnabled(traceTag)) {
                Trace.traceBegin(traceTag, msg.target.getTraceName(msg));
            }
            try {
                //分发msg给给对应的Handler msg.target为handler对象
                msg.target.dispatchMessage(msg);
            } finally {
                if (traceTag != 0) {
                    Trace.traceEnd(traceTag);
                }
            }

            if (logging != null) {
                logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
            }

            // Make sure that during the course of dispatching the
            // identity of the thread wasn't corrupted.
            final long newIdent = Binder.clearCallingIdentity();
            if (ident != newIdent) {
                Log.wtf(TAG, "Thread identity changed from 0x"
                        + Long.toHexString(ident) + " to 0x"
                        + Long.toHexString(newIdent) + " while dispatching to "
                        + msg.target.getClass().getName() + " "
                        + msg.callback + " what=" + msg.what);
            }
            //message数据回收
            msg.recycleUnchecked();
        }
    }
```

Looper.loop()方法作用主要是 for循环不断从MessageQueue队列中获取Message，并分发给对应target的Handler。
先查看MessageQueue$next方法的核心代码
```
   Message next() {
       
        ......//代码省略

        //0为出队状态，-1为等待状态
        int nextPollTimeoutMillis = 0;
        for (;;) {
            if (nextPollTimeoutMillis != 0) {
                Binder.flushPendingCommands();
            }

            nativePollOnce(ptr, nextPollTimeoutMillis);

            synchronized (this) {
                // Try to retrieve the next message.  Return if found.
                final long now = SystemClock.uptimeMillis();
                Message prevMsg = null;
                Message msg = mMessages;
                if (msg != null && msg.target == null) {
                    // Stalled by a barrier.  Find the next asynchronous message in the queue.
                    do {
                        prevMsg = msg;
                        msg = msg.next;
                    } while (msg != null && !msg.isAsynchronous());
                }
                //按时间顺序将message取出
                if (msg != null) {
                    if (now < msg.when) {
                        // Next message is not ready.  Set a timeout to wake up when it is ready.
                        nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
                    } else {
                        // Got a message.
                        mBlocked = false;
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        if (DEBUG) Log.v(TAG, "Returning message: " + msg);
                        msg.markInUse();
                        return msg;
                    }
                } else {
                    // 消息队列中没有信息,则将nextPollTimeoutMillis 设置为1，下次循环时消息队列则处于等待状态
                    nextPollTimeoutMillis = -1;
                }

           ...... //代码省略
    }
```
接着查看Handler.java$dispatchMessage方法源码
```
   public void dispatchMessage(Message msg) {
        //Message对象的callback不为空(runnable)，交给callback处理，
        //这种大多使用post方法传入runnable对象时会调用
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            //handler的callback不为空，交给callback处理，callback
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            //前两种都没有的情况下交给handlerMessage处理 
            //也就是我们在代码中重写的handlerMessage方法
            handleMessage(msg);
        }
    }
```
以上就是我对handler消息传递机制的理解。

最后如果有理解错误之处欢迎指正。




