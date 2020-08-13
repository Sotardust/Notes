![](https://img.hacpai.com/bing/20181008.jpg?imageView2/1/w/960/h/540/interlace/1/q/100)

### ThreadPoolExecutor构造函数
```
 public ThreadPoolExecutor(int corePoolSize, //核心线程数
                              int maximumPoolSize, //最大线程数
                              long keepAliveTime, //线程存活时间
                              TimeUnit unit, //时间类型
                              BlockingQueue<Runnable> workQueue, //阻塞队列
                              ThreadFactory threadFactory,  //线程工厂
                              RejectedExecutionHandler handler) { //拒绝策略
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
```
创建线程池实例
```
ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 
            10, 
            1000,
            TimeUnit.MILLISECONDS, 
            new LinkedBlockingDeque<Runnable>(9), 
            new ThreadFactory() {
           	    @Override
	            public Thread newThread (Runnable r) {
	            	return new Thread(r);
	            }
    });
```
执行execute
```
executor.execute(new Runnable() {
                @Override
                public void run () {
	            //执行任务
                    Log.d(TAG, "executor() called i = " );
                }
            });
```
分析ThreadPoolExecutor的execute方法

```
 public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
     	/*
         * 1. 如果当前运行的线程数<核心线程数,创建一个新的线程执行任务,
         *    调用addWorker方法原子性地检查运行状态和线程数,通过返回false防止不需要的时候添加线程。
         *    
         * 2. 如果一个任务能够成功的入队,仍然需要双重检查,
         *    因为我们添加了一个线程(有可能这个线程在上次检查后就已经死亡了)	
         *    或者进入此方法的时候调用了shutdown,所以需要重新检查线程池的状态,如果必要的话,
         *    当停止的时候要回滚入队操作,或者当线程池为空的话创建一个新的线程
         *    
         * 3. 如果不能入队,尝试着开启一个新的线程,如果开启失败,说明线程池已经是shutdown状态或饱和了,
         *    所以拒绝执行该任务
         */
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }
```
