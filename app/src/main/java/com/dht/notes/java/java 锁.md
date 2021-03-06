
#### Java锁的种类
　　在笔者面试过程时，经常会被问到各种各样的锁，如乐观锁、读写锁等等，非常繁多，在此做一个总结。介绍的内容如下：

乐观锁/悲观锁
独享锁/共享锁
互斥锁/读写锁
可重入锁
公平锁/非公平锁
分段锁
偏向锁/轻量级锁/重量级锁
自旋锁
　　以上是一些锁的名词，这些分类并不是全是指锁的状态，有的指锁的特性，有的指锁的设计，下面总结的内容是对每个锁的名词进行一定的解释。

###### 乐观锁/悲观锁
　　乐观锁与悲观锁并不是特指某两种类型的锁，是人们定义出来的概念或思想，主要是指看待并发同步的角度。

　　乐观锁：顾名思义，就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号等机制。乐观锁适用于多读的应用类型，这样可以提高吞吐量，在Java中java.util.concurrent.atomic包下面的原子变量类就是使用了乐观锁的一种实现方式CAS(Compare and Swap 比较并交换)实现的。

　　悲观锁：总是假设最坏的情况，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会阻塞直到它拿到锁。比如Java里面的同步原语synchronized关键字的实现就是悲观锁。

　　悲观锁适合写操作非常多的场景，乐观锁适合读操作非常多的场景，不加锁会带来大量的性能提升。

　　悲观锁在Java中的使用，就是利用各种锁。

　　乐观锁在Java中的使用，是无锁编程，常常采用的是CAS算法，典型的例子就是原子类，通过CAS自旋实现原子操作的更新。

###### 乐观锁
　　乐观锁总是认为不存在并发问题，每次去取数据的时候，总认为不会有其他线程对数据进行修改，因此不会上锁。但是在更新时会判断其他线程在这之前有没有对数据进行修改，一般会使用“数据版本机制”或“CAS操作”来实现。

   数据版本机制
　　实现数据版本一般有两种，第一种是使用版本号，第二种是使用时间戳。以版本号方式为例。

　　版本号方式：一般是在数据表中加上一个数据版本号version字段，表示数据被修改的次数，当数据被修改时，version值会加一。当线程A要更新数据值时，在读取数据的同时也会读取version值，在提交更新时，若刚才读取到的version值为当前数据库中的version值相等时才更新，否则重试更新操作，直到更新成功。
核心SQL代码：

```
update table set xxx=#{xxx}, version=version+1 where id=#{id} and version=#{version};
``` 
###### CAS操作
　　CAS（Compare and Swap 比较并交换），当多个线程尝试使用CAS同时更新同一个变量时，只有其中一个线程能更新变量的值，而其它线程都失败，失败的线程并不会被挂起，而是被告知这次竞争中失败，并可以再次尝试。

　　CAS操作中包含三个操作数——需要读写的内存位置(V)、进行比较的预期原值(A)和拟写入的新值(B)。如果内存位置V的值与预期原值A相匹配，那么处理器会自动将该位置值更新为新值B，否则处理器不做任何操作。

###### 悲观锁
　　悲观锁认为对于同一个数据的并发操作，一定会发生修改的，哪怕没有修改，也会认为修改。因此对于同一份数据的并发操作，悲观锁采取加锁的形式。悲观的认为，不加锁并发操作一定会出问题。

　　在对任意记录进行修改前，先尝试为该记录加上排他锁（exclusive locking）。

　　如果加锁失败，说明该记录正在被修改，那么当前查询可能要等待或者抛出异常。具体响应方式由开发者根据实际需要决定。

　　如果成功加锁，那么就可以对记录做修改，事务完成后就会解锁了。

　　期间如果有其他对该记录做修改或加排他锁的操作，都会等待我们解锁或直接抛出异常。

###### 独享锁/共享锁
　　独享锁是指该锁一次只能被一个线程所持有。

　　共享锁是指该锁可被多个线程所持有。

　　对于Java ReentrantLock而言，其是独享锁。但是对于Lock的另一个实现类ReadWriteLock，其读锁是共享锁，其写锁是独享锁。

　　读锁的共享锁可保证并发读是非常高效的，读写，写读，写写的过程是互斥的。

　　独享锁与共享锁也是通过AQS来实现的，通过实现不同的方法，来实现独享或者共享。

　　对于Synchronized而言，当然是独享锁。

###### 互斥锁/读写锁
　　上面讲的独享锁/共享锁就是一种广义的说法，互斥锁/读写锁就是具体的实现。

　　互斥锁在Java中的具体实现就是ReentrantLock。

　　读写锁在Java中的具体实现就是ReadWriteLock。

###### 可重入锁
　　可重入锁又名递归锁，是指在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。说的有点抽象，下面会有一个代码的示例。

　　对于Java ReetrantLock而言，从名字就可以看出是一个重入锁，其名字是Re entrant Lock 重新进入锁。

　　对于Synchronized而言，也是一个可重入锁。可重入锁的一个好处是可一定程度避免死锁。

```
1 synchronized void setA() throws Exception{
2 　　Thread.sleep(1000);
3 　　setB();
4 }
5 
6 synchronized void setB() throws Exception{
7 　　Thread.sleep(1000);
8 }
```

　　上面的代码就是一个可重入锁的一个特点。如果不是可重入锁的话，setB可能不会被当前线程执行，可能造成死锁。

###### 公平锁/非公平锁
　　公平锁是指多个线程按照申请锁的顺序来获取锁。

　　非公平锁是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁。有可能，会造成优先级反转或者饥饿现象。

　　对于Java ReetrantLock而言，通过构造函数指定该锁是否是公平锁，默认是非公平锁。非公平锁的优点在于吞吐量比公平锁大。

　　对于Synchronized而言，也是一种非公平锁。由于其并不像ReentrantLock是通过AQS的来实现线程调度，所以并没有任何办法使其变成公平锁。

###### 分段锁
　　分段锁其实是一种锁的设计，并不是具体的一种锁，对于ConcurrentHashMap而言，其并发的实现就是通过分段锁的形式来实现高效的并发操作。

　　我们以ConcurrentHashMap来说一下分段锁的含义以及设计思想，ConcurrentHashMap中的分段锁称为Segment，它即类似于HashMap（JDK7和JDK8中HashMap的实现）的结构，即内部拥有一个Entry数组，数组中的每个元素又是一个链表；同时又是一个ReentrantLock（Segment继承了ReentrantLock）。

　　当需要put元素的时候，并不是对整个hashmap进行加锁，而是先通过hashcode来知道他要放在哪一个分段中，然后对这个分段进行加锁，所以当多线程put的时候，只要不是放在一个分段中，就实现了真正的并行的插入。

　　但是，在统计size的时候，可就是获取hashmap全局信息的时候，就需要获取所有的分段锁才能统计。

　　分段锁的设计目的是细化锁的粒度，当操作不需要更新整个数组的时候，就仅仅针对数组中的一项进行加锁操作。

###### 偏向锁/轻量级锁/重量级锁
　　这三种锁是指锁的状态，并且是针对Synchronized。在Java 5通过引入锁升级的机制来实现高效Synchronized。这三种锁的状态是通过对象监视器在对象头中的字段来表明的。

　　偏向锁是指一段同步代码一直被一个线程所访问，那么该线程会自动获取锁。降低获取锁的代价。

　　轻量级锁是指当锁是偏向锁的时候，被另一个线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，提高性能。

　　重量级锁是指当锁为轻量级锁的时候，另一个线程虽然是自旋，但自旋不会一直持续下去，当自旋一定次数的时候，还没有获取到锁，就会进入阻塞，该锁膨胀为重量级锁。重量级锁会让他申请的线程进入阻塞，性能降低。

###### 自旋锁
　　在Java中，自旋锁是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU。



![Java中的锁分类与使用](https://www.cnblogs.com/hustzzl/p/9343797.html)


![线程篇2：- sleep、wait、notify、join、yield -](https://www.imooc.com/article/298476)

#### synchronized和Lock区别
###### synchronized和Lock区别
1. synchronized是关键字，而Lock是接口
2. synchronized无法判断是否获取锁的状态，Lock可以判断是否获取到锁
3. synchronized自动释放锁（a线程执行完同步代码会自动释放锁，b线程执行过程中发生异常会释放锁）
lock需在finally中手动释放锁（unlock()方法释放锁），否则容易造成线程死锁。
4、synchronized关键字的两个线程1和线程2，若当前线程1获得锁，线程2等待，如果线程1阻塞，线程2会一直等待下去。
而lock锁不一定会等待下去，如果尝试获得不到锁，线程可以不用一直等待就结束了。

synchronized的锁可重入、不可中断、非公平。
lock锁可重入，可中断、可公平

lock锁适合大量同步的代码的同步问题，synchronized锁适合代码少量的同步问题
不可重入锁：自旋锁、wait()、notify()、notifyAll()
不可重入锁，即不可递归调用，递归调用会发生死锁
2、reentrantlock和synchronized区别
reentrantLock拥有synchronized相同的并发性和内存语义，此外还多列锁投票、定时锁等候和中断所等候
使用synchronized锁，A不释放，B将一直等待下去
使用reentrantlock锁，A不释放，B等待一段时间就会中断等待，而干别的事情。
synchronized是在JVM层面上实现的，不但可以通过一些监控工具监控synchronized的锁定，而且代码执行时出现异常，JVM会走到哪个释放锁定。但是Lock不行
在资源竞争不是很激烈的情况下，synchronized的性能优于reentrantlock锁，而竞争激烈的情况下，synchronized的性能下降几十倍，而reentrantlock的性能维持常态。

性能分析

synchronized会多次自旋，以获得锁，在这个过程中等待的线程不会被挂起，因而节省了挂起和唤醒的上下文切换的开销
而reentrantlock，不会自旋，而是直接挂起
因而在线程并发量不大的情况下，synchronized因为拥有自旋锁、偏向锁和轻量级锁的原因，不用将等待线程挂起，偏向锁甚至不用自旋，所以在这种情况下要比reenttrantlock高效。
默认情况下synchronized非公平锁；reentrantlock默认是非公平锁。

绑定多个条件

一个Reentrantlock对象可以同时绑定多个Condition对象，
而在synchronized中，锁对象的wait()和notify()或notifyAll()方法可以实现一个隐含的条件。
如果要和多余一个添加关联的时候，synchronized就不得不额外地添加一个锁，而Reentrantlock则无须这么做只需要多次调用new Condition()方法即可。

作者：Agoni_Soul
链接：https://juejin.cn/post/6844903984033972237
来源：掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

