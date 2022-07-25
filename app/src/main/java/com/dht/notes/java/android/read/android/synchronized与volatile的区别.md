### volatile
    它所修饰的变量不保留拷贝，直接访问主内存中的。 
      
        在Java内存模型中，有main memory，每个线程也有自己的memory (例如寄存器)。
        为了性能，一个线程会在自己的memory中保持要访问的变量的副本。
        这样就会出现同一个变量在某个瞬间，在一个线程的memory中的值可能与另外一个线程memory中的值，
        或者main memory中的值不一致的情况。 
**一个变量声明为volatile，就意味着这个变量是随时会被其他线程修改的**，
        因此不能将它cache在线程memory中.
### synchronized
    当它用来修饰一个方法或者代码块的时候，能够保证在同一时刻最多只有一个线程执行该段代码
    一、当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，一个时间内只能有一个线程得到执行。
        另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
    
    二、然而，当一个线程访问object的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该
        object中的非synchronized(this)同步代码块。
    
    三、尤其关键的是，当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中
        所有其它synchronized(this)同步代码块的访问将被阻塞。
    
    四、当一个线程访问object的一个synchronized(this)同步代码块时，它就获得了这个object的对象锁。
        结果，其它线程对该object对象所有同步代码部分的访问都被暂时阻塞。

    五、以上规则对其它对象锁同样适用.
    
    区别：
        一、volatile是变量修饰符，而synchronized则作用于一段代码或方法。
        
        二、volatile只是在线程内存和“主”内存间同步某个变量的值；而synchronized通过锁定和解锁某个监视器同步所有变量的值。
            显然synchronized要比volatile消耗更多资源。
     