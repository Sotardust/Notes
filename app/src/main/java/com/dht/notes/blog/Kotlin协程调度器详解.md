### 什么是协程
>官方描述：协程通过将复杂性放入库来简化异步编程。程序的逻辑可以在协程中顺序地表达，而底层库会为我们解决其异步性。该库可以将用户代码的相关部分包装为回调、订阅相关事件、在不同线程（甚至不同机器）上调度执行，而代码则保持如同顺序执行一样简单。

#### 线程和协程的目的差异

* 线程的目的是提高CPU资源使用率， 使多个任务得以并行的运行，是为了服务于机器的.
* 协程的目的是为了让多个任务之间更好的协作，主要体现在代码逻辑上，是为了服务开发者 (能提升资源的利用率, 但并不是原始目的)

#### 线程和协程的调度差异

+ 线程的调度是系统完成的，一般是抢占式的，根据优先级来分配
+ 协程的调度是开发者根据程序逻辑指定好的，在不同的时期把资源合理的分配给不同的任务.

#### 协程与线程的关系

协程并不是取代线程，而且抽象于线程之上，线程是被分割的CPU资源，协程是组织好的代码流程，协程需要线程来承载运行，线程是协程的资源

#### 协程的核心竞争力
Kotlin 协程的核心竞争力在于：它能简化异步并发任务。

#### 协程上下文 CoroutineContext
 + 协程总是运行在一些以 CoroutineContext 类型为代表的上下文中 ，协程上下文是各种不同元素的集合
 + 集合内部的元素Element是根据key去对应（Map特点），但是不允许重复（Set特点）
 + Element之间可以通过+号进行组合
 + Element有如下四类，共同组成了CoroutineContext
    + Job：协程的唯一标识，用来控制协程的生命周期(new、active、completing、completed、cancelling、cancelled)
    + CoroutineDispatcher：指定协程运行的线程(IO、Default、Main、Unconfined)
    + CoroutineName: 指定协程的名称，默认为coroutine
    + CoroutineExceptionHandler: 指定协程的异常处理器，用来处理未捕获的异常

#### CoroutineDispatcher 作用

 + 用于指定协程的运行线程
 + kotlin已经内置了CoroutineDispatcher的4个实现，分别为 Dispatchers的Default、IO、Main、Unconfined字段
```
public actual object Dispatchers {

    @JvmStatic
    public actual val Default: CoroutineDispatcher = createDefaultDispatcher()
    
    @JvmStatic
    public val IO: CoroutineDispatcher = DefaultScheduler.IO
    
    @JvmStatic
    public actual val Unconfined: CoroutineDispatcher = kotlinx.coroutines.Unconfined
    
    @JvmStatic
    public actual val Main: MainCoroutineDispatcher get() = MainDispatcherLoader.dispatcher
}

```
#### Dispatchers.Default
Default根据useCoroutinesScheduler属性（默认为true） 去获取对应的线程池
+ DefaultScheduler ：Kotlin内部自己实现的线程池逻辑
+ CommonPool：Java类库中的Executor实现的线程池逻辑
```
internal actual fun createDefaultDispatcher(): CoroutineDispatcher =
    if (useCoroutinesScheduler) DefaultScheduler else CommonPool
internal object DefaultScheduler : ExperimentalCoroutineDispatcher() {
    .....
}

open class ExperimentalCoroutineDispatcher(
    private val corePoolSize: Int,
    private val maxPoolSize: Int,
    private val idleWorkerKeepAliveNs: Long,
    private val schedulerName: String = "CoroutineScheduler"
) : ExecutorCoroutineDispatcher() {
    constructor(
        corePoolSize: Int = CORE_POOL_SIZE,
        maxPoolSize: Int = MAX_POOL_SIZE,
        schedulerName: String = DEFAULT_SCHEDULER_NAME
    ) : this(corePoolSize, maxPoolSize, IDLE_WORKER_KEEP_ALIVE_NS, schedulerName)

    ......
}
//java类库中的Executor实现线程池逻辑
internal object CommonPool : ExecutorCoroutineDispatcher() {}

```
如果想使用java类库中的线程池该如何使用呢？也就是修改useCoroutinesScheduler属性为false

```
internal const val COROUTINES_SCHEDULER_PROPERTY_NAME = "kotlinx.coroutines.scheduler"

internal val useCoroutinesScheduler = systemProp(COROUTINES_SCHEDULER_PROPERTY_NAME).let { value ->
    when (value) {
        null, "", "on" -> true
        "off" -> false
        else -> error("System property '$COROUTINES_SCHEDULER_PROPERTY_NAME' has unrecognized value '$value'")
    }
}

internal actual fun systemProp(
    propertyName: String
): String? =
    try {
       //获取系统属性
        System.getProperty(propertyName)
    } catch (e: SecurityException) {
        null
    }

```
从源码中可以看到,使用过获取系统属性拿到的值，  那我们就可以通过修改系统属性 去改变useCoroutinesScheduler的值,
具体修改方法为
```
 val properties = Properties()
 properties["kotlinx.coroutines.scheduler"] = "off"
 System.setProperties(properties)
```

DefaultScheduler的主要实现都在其父类 ExperimentalCoroutineDispatcher 中

```
open class ExperimentalCoroutineDispatcher(
    private val corePoolSize: Int,
    private val maxPoolSize: Int,
    private val idleWorkerKeepAliveNs: Long,
    private val schedulerName: String = "CoroutineScheduler"
) : ExecutorCoroutineDispatcher() {
    public constructor(
        corePoolSize: Int = CORE_POOL_SIZE,
        maxPoolSize: Int = MAX_POOL_SIZE,
        schedulerName: String = DEFAULT_SCHEDULER_NAME
    ) : this(corePoolSize, maxPoolSize, IDLE_WORKER_KEEP_ALIVE_NS, schedulerName)

    constructor(
        corePoolSize: Int = CORE_POOL_SIZE,
        maxPoolSize: Int = MAX_POOL_SIZE
    ) : this(corePoolSize, maxPoolSize, IDLE_WORKER_KEEP_ALIVE_NS)
    
    override val executor: Executor
       get() = coroutineScheduler

    private var coroutineScheduler = createScheduler()
    
    //创建CoroutineScheduler实例
    private fun createScheduler() = CoroutineScheduler(corePoolSize, maxPoolSize, idleWorkerKeepAliveNs, schedulerName)
    
    override val executor: Executorget() = coroutineScheduler

    override fun dispatch(context: CoroutineContext, block: Runnable): Unit =
        try {
            //dispatch方法委托到CoroutineScheduler的dispatch方法
            coroutineScheduler.dispatch(block)
        } catch (e: RejectedExecutionException) {
            ....
        }

    override fun dispatchYield(context: CoroutineContext, block: Runnable): Unit =
        try {
            //dispatchYield方法委托到CoroutineScheduler的dispatchYield方法
            coroutineScheduler.dispatch(block, tailDispatch = true)
        } catch (e: RejectedExecutionException) {
            ...
        }
    
	internal fun dispatchWithContext(block: Runnable, context: TaskContext, tailDispatch: Boolean) {
        try {
            //dispatchWithContext方法委托到CoroutineScheduler的dispatchWithContext方法
            coroutineScheduler.dispatch(block, context, tailDispatch)
        } catch (e: RejectedExecutionException) {
            ....
        }
    }
    override fun close(): Unit = coroutineScheduler.close()
    //实现请求阻塞
    public fun blocking(parallelism: Int = BLOCKING_DEFAULT_PARALLELISM): CoroutineDispatcher {
        require(parallelism > 0) { "Expected positive parallelism level, but have $parallelism" }
        return LimitingDispatcher(this, parallelism, null, TASK_PROBABLY_BLOCKING)
    }
	//实现并发数量限制
    public fun limited(parallelism: Int): CoroutineDispatcher {
        require(parallelism > 0) { "Expected positive parallelism level, but have $parallelism" }
        require(parallelism <= corePoolSize) { "Expected parallelism level lesser than core pool size ($corePoolSize), but have $parallelism" }
        return LimitingDispatcher(this, parallelism, null, TASK_NON_BLOCKING)
    }
    
    ....
}

```
实现请求数量限制是调用 LimitingDispatcher 类，其类实现为
```
private class LimitingDispatcher(
    private val dispatcher: ExperimentalCoroutineDispatcher,
    private val parallelism: Int,
    private val name: String?,
    override val taskMode: Int
) : ExecutorCoroutineDispatcher(), TaskContext, Executor {
    //同步阻塞队列
    private val queue = ConcurrentLinkedQueue<Runnable>()
    //cas计数
    private val inFlightTasks = atomic(0)
    
    override fun dispatch(context: CoroutineContext, block: Runnable) = dispatch(block, false)

    private fun dispatch(block: Runnable, tailDispatch: Boolean) {
        var taskToSchedule = block
        while (true) {

            if (inFlight <= parallelism) {
                //LimitingDispatcher的dispatch方法委托给了DefaultScheduler的dispatchWithContext方法
                dispatcher.dispatchWithContext(taskToSchedule, this, tailDispatch)
                return
            }
            ......
        }
    }
}

```

#### Dispatchers.IO
先看下 Dispatchers.IO 的定义
```
    /**
     *This dispatcher shares threads with a [Default][Dispatchers.Default] dispatcher, so using
     * `withContext(Dispatchers.IO) { ... }` does not lead to an actual switching to another thread &mdash;
     * typically execution continues in the same thread.
     */
    @JvmStatic
    public val IO: CoroutineDispatcher = DefaultScheduler.IO
    
    
    Internal object DefaultScheduler : ExperimentalCoroutineDispatcher() {
    val IO = blocking(systemProp(IO_PARALLELISM_PROPERTY_NAME, 64.coerceAtLeast(AVAILABLE_PROCESSORS)))
    
    ......
    
    }
    
```
IO在DefaultScheduler中的实现 是调用blacking()方法，而blacking（）方法最终实现是LimitingDispatcher类，
所以 从源码可以看出 Dispatchers.Default和IO 是在同一个线程中运行的，也就是共用相同的线程池。

而Default和IO 都是共享CoroutineScheduler线程池 ，kotlin内部实现了一套线程池两种调度策略，主要是通过dispatch方法中的Mode区分的

|Type|Mode|
|-|-|
|Default|NON_BLOCKING|
|IO|PROBABLY_BLOCKING|

```
internal enum class TaskMode {

    //执行CPU密集型任务
    NON_BLOCKING,

    //执行IO密集型任务
    PROBABLY_BLOCKING,
}
fun dispatch(block: Runnable, taskContext: TaskContext = NonBlockingContext, tailDispatch: Boolean = false) {
......
     if (task.mode == TaskMode.NON_BLOCKING) {
            signalCpuWork() //Dispatchers.Default
     } else {
            signalBlockingWork() // Dispatchers.IO
     }
}

```
|Type|处理策略|适合场景|
|-|-|-|
|Default|CoroutineScheduler最多有corePoolSize个线程被创建，corePoolSize它的取值为max(2, CPU核心数)，
即它会尽量的等于CPU核心数|1.CPU密集型任务的特点是执行任务时CPU会处于忙碌状态，任务会消耗大量的CPU资源2.复杂计算、视频解码等，如果此时线程数太多，超过了CPU核心数，那么这些超出来的线程是得不到CPU的执行的，只会浪费内存资源3.因为线程本身也有栈等空间，同时线程过多，频繁的线程切换带来的消耗也会影响线程池的性能4.对于CPU密集型任务，线程池并发线程数等于CPU核心数才能让CPU的执行效率最大化|
|IO|创建比corePoolSize更多的线程来运行IO型任务，但不能大于maxPoolSize1.公式：max(corePoolSize, min(CPU核心数 * 128, 2^21 - 2))，即大于corePoolSize，小于2^21 - 22.2^21 - 2是一个很大的数约为2M，但是CoroutineScheduler是不可能创建这么多线程的，所以就需要外部限制提交的任务数3.Dispatchers.IO构造时就通过LimitingDispatcher默认限制了最大线程并发数parallelism为max(64, CPU核心数)，即最多只能提交parallelism个任务到CoroutineScheduler中执行，剩余的任务被放进队列中等待。|1.IO密集型任务的特点是执行任务时CPU会处于闲置状态，任务不会消耗大量的CPU资源2.网络请求、IO操作等，线程执行IO密集型任务时大多数处于阻塞状态，处于阻塞状态的线程是不占用CPU的执行时间3.此时CPU就处于闲置状态，为了让CPU忙起来，执行IO密集型任务时理应让线程的创建数量更多一点，理想情况下线程数应该等于提交的任务数，对于这些多创建出来的线程，当它们闲置时，线程池一般会有一个超时回收策略，所以大部分情况下并不会占用大量的内存资源4.但也会有极端情况，所以对于IO密集型任务，线程池并发线程数应尽可能地多才能提高CPU的吞吐量，这个尽可能地多的程度并不是无限大，而是根据业务情况设定，但肯定要大于CPU核心数。|

##### Dispatchers.Unconfined
任务执行在默认的启动线程。之后由调用resume的线程决定恢复协程的线程
```
internal object Unconfined : CoroutineDispatcher() {
    //为false为不需要dispatch
    override fun isDispatchNeeded(context: CoroutineContext): Boolean = false

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        // 只有当调用yield方法时，Unconfined的dispatch方法才会被调用
        // yield() 表示当前协程让出自己所在的线程给其他协程运行
        val yieldContext = context[YieldContext]
        if (yieldContext != null) {
            yieldContext.dispatcherWasUnconfined = true
            return
        }
        throw UnsupportedOperationException("Dispatchers.Unconfined.dispatch function can only be used by the yield function. " +
            "If you wrap Unconfined dispatcher in your code, make sure you properly delegate " +
            "isDispatchNeeded and dispatch calls.")
    }
}

```
每一个协程都有对应的Continuation实例，其中的resumeWith用于协程的恢复，存在于DispatchedContinuation

```
public abstract class CoroutineDispatcher :
    AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
    ......
    
    public final override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
        DispatchedContinuation(this, continuation)
        
    ......
    
}
```

重点看resumeWith的实现以及类委托

```
internal class DispatchedContinuation<in T>(
    @JvmField val dispatcher: CoroutineDispatcher,
    @JvmField val continuation: Continuation<T>//协程suspend挂起方法产生的Continuation
) : DispatchedTask<T>(MODE_UNINITIALIZED), CoroutineStackFrame, Continuation<T> by continuation {
    .....
    override fun resumeWith(result: Result<T>) {
        val context = continuation.context
        val state = result.toState()
        if (dispatcher.isDispatchNeeded(context)) {
            _state = state
            resumeMode = MODE_ATOMIC
            dispatcher.dispatch(context, this)
        } else {
            executeUnconfined(state, MODE_ATOMIC) {
                withCoroutineContext(this.context, countOrElement) {
                    continuation.resumeWith(result)
                }
            }
        }
    }
    ....
}

```
通过isDispatchNeeded（是否需要dispatch，Unconfined=false，default，IO=true）判断做不同处理
 + true：调用协程的CoroutineDispatcher的dispatch方法
 + false：调用executeUnconfined方法

```
private inline fun DispatchedContinuation<*>.executeUnconfined(
    contState: Any?, mode: Int, doYield: Boolean = false,
    block: () -> Unit
): Boolean {
    assert { mode != MODE_UNINITIALIZED }
    val eventLoop = ThreadLocalEventLoop.eventLoop
    if (doYield && eventLoop.isUnconfinedQueueEmpty) return false
    return if (eventLoop.isUnconfinedLoopActive) {
        _state = contState
        resumeMode = mode
        eventLoop.dispatchUnconfined(this)
        true
    } else {
        runUnconfinedEventLoop(eventLoop, block = block)
        false
    }
}

```
从threadlocal中取出eventLoop（eventLoop和当前线程相关的），判断是否在执行Unconfined任务

1. 如果在执行则调用EventLoop的dispatchUnconfined方法把Unconfined任务放进EventLoop中
2. 如果没有在执行则直接执行
```
internal inline fun DispatchedTask<*>.runUnconfinedEventLoop(
    eventLoop: EventLoop,
    block: () -> Unit
) {
    eventLoop.incrementUseCount(unconfined = true)
    try {
        block()
        while (true) {
            if (!eventLoop.processUnconfinedEvent()) break
        }
    } catch (e: Throwable) {
        handleFatalException(e, null)
    } finally {
        eventLoop.decrementUseCount(unconfined = true)
    }
}

```
1. 执行block()代码块，即上文提到的resumeWith()
2. 调用processUnconfinedEvent()方法实现执行剩余的Unconfined任务，知道全部执行完毕跳出循环

EventLoop是存放与threadlocal，所以是跟当前线程相关联的，而EventLoop也是CoroutineDispatcher的一个子类

```
internal abstract class EventLoop : CoroutineDispatcher() {
  	.....
    //双端队列实现存放Unconfined任务
    private var unconfinedQueue: ArrayQueue<DispatchedTask<*>>? = null
    //从队列的头部移出Unconfined任务执行
    public fun processUnconfinedEvent(): Boolean {
        val queue = unconfinedQueue ?: return false
        val task = queue.removeFirstOrNull() ?: return false
        task.run()
        return true
    }
    //把Unconfined任务放进队列的尾部
    public fun dispatchUnconfined(task: DispatchedTask<*>) {
        val queue = unconfinedQueue ?:
            ArrayQueue<DispatchedTask<*>>().also { unconfinedQueue = it }
        queue.addLast(task)
    }
    .....
}

```
内部通过双端队列实现存放Unconfined任务
1. EventLoop的dispatchUnconfined方法用于把Unconfined任务放进队列的尾部
2. rocessUnconfinedEvent方法用于从队列的头部移出Unconfined任务执行

##### Dispatchers.Main
kotlin在JVM上的实现 Android就需要引入kotlinx-coroutines-android库，它里面有Android对应的Dispatchers.Main实现，

```
   public actual val Main: MainCoroutineDispatcher get() = MainDispatcherLoader.dispatcher
   
     @JvmField
    val dispatcher: MainCoroutineDispatcher = loadMainDispatcher()

    private fun loadMainDispatcher(): MainCoroutineDispatcher {
        return try {
            val factories = if (FAST_SERVICE_LOADER_ENABLED) {
                FastServiceLoader.loadMainDispatcherFactory()
            } else {
                // We are explicitly using the
                // `ServiceLoader.load(MyClass::class.java, MyClass::class.java.classLoader).iterator()`
                // form of the ServiceLoader call to enable R8 optimization when compiled on Android.
                ServiceLoader.load(
                        MainDispatcherFactory::class.java,
                        MainDispatcherFactory::class.java.classLoader
                ).iterator().asSequence().toList()
            }
            factories.maxBy { it.loadPriority }?.tryCreateDispatcher(factories)
                ?: MissingMainCoroutineDispatcher(null)
        } catch (e: Throwable) {
            // Service loader can throw an exception as well
            MissingMainCoroutineDispatcher(e)
        }
    }
    
    internal fun loadMainDispatcherFactory(): List<MainDispatcherFactory> {
        val clz = MainDispatcherFactory::class.java
        if (!ANDROID_DETECTED) {
            return load(clz, clz.classLoader)
        }

        return try {
            val result = ArrayList<MainDispatcherFactory>(2)
            createInstanceOf(clz, "kotlinx.coroutines.android.AndroidDispatcherFactory")?.apply { result.add(this) }
            createInstanceOf(clz, "kotlinx.coroutines.test.internal.TestMainDispatcherFactory")?.apply { result.add(this) }
            result
        } catch (e: Throwable) {
            // Fallback to the regular SL in case of any unexpected exception
            load(clz, clz.classLoader)
        }
    }
```
 通过反射获取AndroidDispatcherFactory 然后根据加载的优先级 去创建Dispatcher
```
internal class AndroidDispatcherFactory : MainDispatcherFactory {

    override fun createDispatcher(allFactories: List<MainDispatcherFactory>) =
        HandlerContext(Looper.getMainLooper().asHandler(async = true), "Main")

    override fun hintOnError(): String? = "For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used"

    override val loadPriority: Int
        get() = Int.MAX_VALUE / 2
}
internal class HandlerContext private constructor(
    private val handler: Handler,
    private val name: String?,
    private val invokeImmediately: Boolean
) : HandlerDispatcher(), Delay {
   
    public constructor(
        handler: Handler,
        name: String? = null
    ) : this(handler, name, false)

   ......

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        handler.post(block)
    }

    ......
}
```
而createDispatcher调用HandlerContext 类 通过调用Looper.getMainLooper()获取handler ，最终通过handler来实现在主线程中运行


Dispatchers.Main 其实就是把任务通过Handler运行在Android的主线程

