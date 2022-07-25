![](https://img.hacpai.com/bing/20180507.jpg?imageView2/1/w/960/h/540/interlace/1/q/100)

### ThreadPoolExecutor
线程池其实就是容纳多个线程的容器，其中的线程可以反复使用，无需反复创建线程而消耗过多资源。

#### ThreadPoolExecutor构造方法
ThreadPoolExecutor 有多个重载方法，但最终都是调用了
```
  public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
```
构造方法中共7个参数：  
* **corePoolSize**：线程池的核心线程数，默认情况下，核心线程会在线程池中一直存活，即使它们处于闲置状态。如果将ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，那么闲置的核心线程在等待新任务到来时会有超时策略，这个时间间隔由keepAliveTime所指定，当等待时间超过keepAliveTime所指定的时长后，核心线程就会被终止。
* **maximumPoolSize**：线程池索能容纳的最大的线程数，当活动线程数达到这个最大数值后，新runnable会添加到阻塞队列中。

* **keepAliveTime**：非核心线程闲置时的超时时长，超过这个时长，非核心线程就会被回收。当ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true时，keepAliveTime同样会作用于核心线程。
* **unit**：用于指定KeepAliveTime参数的时间单位，从 天（DAYS）到纳秒（NANOSECONDS）
* **workQueue**：线程池中的任务阻塞队列，通过线程池的execute方法提交的Runnable对象会存储在这个参数中。
* **threadFactory**：线程工厂为线程池提供创建新线程的功能
* **handler**：当线程池无法执行新任务时，这可能是由于任务队列已满或者是无法成功执行任务，这个时候ThreadPoolExecutor会调用handler的rejectedExecution方法来通知调用者
#### 线程池的优点
1. 重用线程池中的线程，避免因为线程的创建和销毁带来的性能开销；
2. 有效控制线程池中的最大并发数，避免大量线程之间因为相互抢占资源而导致的阻塞现象；
3. 能对线程进行简单的管理，可提供定时执行和按照指定时间间隔循环执行等功能；
### 四大线程池类型
android中有四种不同功能的线程池为：
`FixedThreadPool`,`CachedThreadPool`,`ScheduledThreadPool`,`SingleThreadExecutor`

#### FixedThreadPool
```
   public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads,
				      nThreads,
                                      0L, 
				      TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
```
FixedThreadPool是一个重用固定数量线程的线程池，从构造方法来看其特点为：核心线程和非核心线程数相等（即没有非核心线程），没有超时机制，任务大小也没有线程，数量固定，即使在空闲状态，线程也不会被回收，除非调用shundown方法关闭。
任务队列采用了无界的阻塞队列LinkedBlockingQueue，执行executor方法的时候，运行的线程没有达到corPoolSize就创建核心线程执行任务，否则就添加到阻塞队列中，有空闲线程的时候去取任务执行，由于该线程池线程数固定，且不被回收，线程与线程池的声明周期同步，所以适用于任务量比较固定但耗时长的任务。
LinkedBlockingQueue阻塞队列容量默认最大为Integer_MAX_VALUE
```
   public LinkedBlockingQueue() {
        this(Integer.MAX_VALUE);
    }

```
#### CachedThreadPool
```
public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, 
                                      Integer.MAX_VALUE,
                                      60L, 
                                      TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }
```
CachedThreadPool线程池根据需要创建新线程，如果有先前可以利用的线程则会重用它们。
从构造方法来看其特点为：
没有核心线程，其最大线程数为Integer.MAX_VALUE,线程中的空闲线程都有超时机制，时长是60秒，超过60秒空闲线程就会被回收。
当线程池都处于空闲时，线程池中的线程都会因为超时而被回收，所以几乎不会占用什么系统资源。
任务队列采用的是SynchronousQueue同步队列，这个队列是无法插入任务的，一有任务立即执行，所以CachedThreadPool比较适合任务量大但耗时少的任务。

#### ScheduledThreadPool
```
 public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }
```
```
public ScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize, 
		Integer.MAX_VALUE,
                DEFAULT_KEEPALIVE_MILLIS, 
		MILLISECONDS,
                new DelayedWorkQueue());
    }
```
从构造方法来看，内部是调用 ScheduledThreadPoolExecutor类，该类继承ThreadPoolExecutor 并调用并实现 ScheduledExecutorService 接口，
```
public class ScheduledThreadPoolExecutor
        extends ThreadPoolExecutor
        implements ScheduledExecutorService {
	......
	}
```
调用父类ThreadPoolExecutor构造方法
```
public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
             Executors.defaultThreadFactory(), defaultHandler);
    }
```
其特点为：
核心线程数量固定，非核心线程是没有限制的（即最大值为Integer.MAX_VALUE），默认空闲线程存活时间DEFAULT_KEEPALIVE_MILLIS为 10毫秒，核心线程是不会被回收的。
任务队列使用DelayedWorkQueue，DelayedWorkQueue会将任务排序，按新建一个非核心线程顺序执行，执行完线程就回收，然后循环。任务队列采用的DelayedWorkQueue是个无界的队列，延时执行队列任务。
该线程池适用于定时任务和具体固定周期的重复任务。

#### SingleThreadPool
```
    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1,
                                    1,
                                    0L, 
                                    TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }

```
```
static class FinalizableDelegatedExecutorService
        extends DelegatedExecutorService {
        FinalizableDelegatedExecutorService(ExecutorService executor) {
            super(executor);
        }
        protected void finalize() {
            super.shutdown();
        }
    }
```
FinalizableDelegatedExecutorService 继承DelegatedExecutorService 类并增加了一个finalize方法，finalize方法会在虚拟机利用垃圾回收清理对象是被调用，换言之，FinalizableDelegatedExecutorService的实例即使不手动调用shutdown方法关闭现称池，虚拟机也会帮你完成此任务。
```
static class DelegatedExecutorService extends AbstractExecutorService {}
```
DelegatedExecutorService类是线程池的一个代理模式的实现，对它所有方法的调用其实是被委托到它持有的目标线程池上，不过它的功能是被阉割的， 因为他只实现并委托了部分方法，真实线程池存在的那些未被委托的方法在这里将无法使用。
再看其ThreadPoolExecutor构造方法，其特点为：
该线程池内部只有一个核心线程，非核心线程个数为0，它确保所有的任务都在同一个线程中按顺序执行，也可以与周期线程池结合使用其构造方法如下
```
   public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
        return new DelegatedScheduledExecutorService
            (new ScheduledThreadPoolExecutor(1));
    }
```
其任务队列是LinkedBlockingQueue，是个无界的阻塞队列，因为只有一个线程，就不需要处理线程同步的问题。
这类线程池适用于多个任务循序执行的场景。

### 总结
* FixedThreadPool ：核心线程固定，非核心线程固定
* CachedThreadPool：核心线程数为0，非核心线程数无限制（Integer.MAX_VALUE）
* ScheduledThreadPool:核心线程数固定，非核心线程数无限制，可执行定时任务
* SingleThreadExecutor：核心线程数为1，非核心线程数唯一，也可执行定时任务

