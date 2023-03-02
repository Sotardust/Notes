### 1、ConcurrentHashMap CAS+ synchronized 

CAS 是 Compare And Swap 的缩写 意思为比较并交换，起作用是：
让cpu比较内存中的某个值是否和预期的值相同，如果相同则将这个值更新为新值，
不相同则不做更新，也就是CAS原子性的操作（读和写是同时具有原子性）

预期值 内存中的值，以及新值、

Java是在Unsafe(sun.misc.Unsafe).compareAndSwapObject(Object var1, long var2, Object var4, Object var5)
类实现CAS的操作

#### synchronized 

#### CAS缺点
###### 1、ABA问题带来的危害：
小明在提款机，提取了50元，因为提款机问题，有两个线程，同时把余额从100变为50
线程1（提款机）：获取当前值100，期望更新为50，
线程2（提款机）：获取当前值100，期望更新为50，
线程1成功执行，线程2某种原因block了，这时，某人给小明汇款50
线程3（默认）：获取当前值50，期望更新为100，
这时候线程3成功执行，余额变为100，
线程2从Block中恢复，获取到的也是100，compare之后，继续更新余额为50！！！
此时可以看到，实际余额应该为100（100-50+50），但是实际上变为了50（100-50+50-50）这就是ABA问题带来的成功提交。
解决方法：
在变量前面加上版本号，每次变量更新的时候变量的版本号都+1，即A->B->A就变成了1A->2B->3A。


###### 2、循环时间长开销大
如果CAS操作失败，就需要循环进行CAS操作(循环同时将期望值更新为最新的)，如果长时间都不成功的话，那么会造成CPU极大的开销。

这种循环也称为自旋

解决方法： 限制自旋次数，防止进入死循环。


###### 3、只能保证一个共享变量的原子操作

CAS的原子操作只能针对一个共享变量。

解决方法： 如果需要对多个共享变量进行操作，
可以使用加锁方式(悲观锁)保证原子性，或者可以把多个共享变量合并成一个共享变量进行CAS操作。

### 2、线程池 拒绝策略 
1、AbortPolicy：默认拒绝策略，丢弃提交的任务并抛出RejectedExecutionException，在该异常输出信息中，可以看到当前线程池状态
2、DiscardPolicy：丢弃新来的任务，但是不抛出异常
3、DiscardOldestPolicy：丢弃队列头部的旧任务，然后尝试重新执行，如果再次失败，重复该过程
4、CallerRunsPolicy：由调用线程处理该任务


### 3、并发处理事件
1、使用join()方法
2、使用协程 的await()方法
3、使用 CountDownLatch countDownLatch = new CountDownLatch() 
调用 countDownLatch.countDown() ;
this.downLatch.await();

3、IntentService , HandlerThread

4、标记清理算法 复制算法，标记整理算法 分代收集算法

5、垃圾回收器  CMS 并发标记清理 ，G1垃圾回收器



