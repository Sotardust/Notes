### 前言
 我们知道在使用handler进行消息传递时，需要创建Looper.prepare(),以及执行Looper.loop()方法
不了解的可以看下[Handler消息机制源码分析](https://www.jianshu.com/p/30db5ca77e27)。
查看Looper.java$prepare方法源码
```
  private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }

   private Looper(boolean quitAllowed) {
        //创建MessageQueue 这也是为什么一个Looper中只有一个MessageQueue的原因
        mQueue = new MessageQueue(quitAllowed);
        mThread = Thread.currentThread();
    }

```
可以看到是sThreadLocal调用了set方法创建Looper  ，查看Looper.java代码发现Looper是存储在ThreadLocal里面的
```
  static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
```
ThreadLocal是什么呢 ，**ThreadLocal是存储当前线程数据的数据存储类。**
我们知道Looper.loop()方法中调用myLooper()获取Looper对象，查看myLooper方法源码
```
  public static @Nullable Looper myLooper() {
        return sThreadLocal.get();
    }
```
发现是直接通过调用ThreadLocal类中的get方法获取存储在ThreadLocal中的Looper。
### 源码分析
这里先分析ThreadLocal的set方法查看其源码
```
    public void set(T value) {
        //获取当前线程
        Thread t = Thread.currentThread();
        // ThreadLocalMap为ThreadLocal的静态内部类，后面会对该类进行分析
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
```
先看ThreadLocal类的createMap方法
```
   void createMap(Thread t, T firstValue) {
        //t.threadLocals是一个ThreadLocalMap对象
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }
//查看Thread类
ThreadLocal.ThreadLocalMap threadLocals = null;
```
每个线程通过ThreadLocal.ThreadLocalMap与ThreadLocal进行绑定，确保每个线程访问到的thread local variable都是该线程的。

现在分析ThreadLocal类的get方法
```
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null)
                return (T)e.value;
        }
        return setInitialValue();
    }
```
先看setInitialValue()方法源码
```
   private T setInitialValue() {
        //为thread local 初始化值
        T value = initialValue();
        //下面则与ThreadLocal的set方法源码相同
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }
```
从ThreadLocal的get方法中可以看到该方法调用了ThreadLocalMap中的set方法和getEntry方法，接下来分析ThreadLocalMap的实现。
我们先看ThreadLocalMap的介绍：是一个自定义的哈希映射表用于维护线程本地值。查看ThreadLocalMap的数据结构：
```
 //Entry 为ThreadLocalMap的静态内部类，继承弱引用的ThreadLocal
 static class Entry extends WeakReference<ThreadLocal> {
           // 实际保存的对象值
            Object value;
            
            Entry(ThreadLocal k, Object v) {
                super(k);
                value = v;
            }
        }

       //Entry[] 的初始大小，其值必须是2的n次幂
        private static final int INITIAL_CAPACITY = 16;

        //实际存放对象的容器，其大小必须是2的n次幂
        private Entry[] table;

        //Entry表元素的数量
        private int size = 0;

        //哈希表的扩容阈值默认值为0
        private int threshold; // Default to 0

```
查看ThreadLocalMap构造函数
```
      ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
            table = new Entry[INITIAL_CAPACITY];
            //用firstKey的threadLocalHashCode与初始值取模获取元素放置的下标位置
            int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
            //初始化该节点
            table[i] = new Entry(firstKey, firstValue);
            size = 1;
            //设置阈值
            setThreshold(INITIAL_CAPACITY);
        }
```
firstKey 为ThreadLocal对象，ThreadLocalHashCode在被ThreadLocal创建的时候就生成了
```
 // 相当于ThreadLocal的ID
 private final int threadLocalHashCode = nextHashCode();
```
接下来查看ThreadLocalMap类中的getEntry方法源码
```
   private Entry getEntry(ThreadLocal<?> key) {
            //根据key获取索引
            int i = key.threadLocalHashCode & (table.length - 1);
            Entry e = table[i];
            //如果对应的Entry存在且未失效则返回
            if (e != null && e.get() == key)
                return e;
            else
                //使用线性探测，继续查找目标Entry
                return getEntryAfterMiss(key, i, e);
        }
```
查看getEntryAfterMiss方法源码
```
      private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
            Entry[] tab = table;
            int len = tab.length;
            // 基于线性探测法不断向后探测直到遇到空entry。
            while (e != null) {
                ThreadLocal<?> k = e.get();
                if (k == key)
                    return e;
                if (k == null)
                    //k为空则说明Entry中对应的ThreadLocal已经被回收，调用该方法来清理无效的entry
                    expungeStaleEntry(i);
                else
                    i = nextIndex(i, len);
                e = tab[i];
            }
            return null;
        }
```
查看expungeStaleEntry方法源码
```
 // 该函数是ThreadLocal中的核心清理函数，从staleSlot开始遍历
 //将对应Entry中的Vaule值置为空
 private int expungeStaleEntry(int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;

            // 将staleSlot对应的Entry已经Entry中的Value置为null
            tab[staleSlot].value = null;
            tab[staleSlot] = null;
            size--;

            // Rehash until we encounter null
            Entry e;
            int i;
            //从staleSlot开始遍历
            for (i = nextIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();
                // 清理对应ThreadLocal已经被回收的entry
                if (k == null) {
                    e.value = null;
                    tab[i] = null;
                    size--;
                } else {
                    int h = k.threadLocalHashCode & (len - 1);
                    if (h != i) {
                        tab[i] = null;

                        // Unlike Knuth 6.4 Algorithm R, we must scan until
                        // null because multiple entries could have been stale.
                         // 从计算出来的哈希位开始往后查找，找到一个适合它的空位
                        while (tab[h] != null)
                            h = nextIndex(h, len);
                        tab[h] = e;
                    }
                }
            }
            return i;
        }
```
查看ThreadLocalMap的set方法源码
```
    private void set(ThreadLocal<?> key, Object value) {

            // We don't use a fast path as with get() because it is at
            // least as common to use set() to create new entries as
            // it is to replace existing ones, in which case, a fast
            // path would fail more often than not.

            Entry[] tab = table;
            int len = tab.length;
            int i = key.threadLocalHashCode & (len-1);
            //线性探测
            for (Entry e = tab[i];
                 e != null;
                 e = tab[i = nextIndex(i, len)]) {
                ThreadLocal<?> k = e.get();
                //通过key查找到对应的Entry
                if (k == key) {
                    e.value = value;
                    return;
                }
                //替换失效的Entry
                if (k == null) {
                    //如果entry里对应的key为null的话，表明此entry为 旧的，就将其替换为当前的key和value
                    replaceStaleEntry(key, value, i);
                    return;
                }
            }

            tab[i] = new Entry(key, value);
            int sz = ++size;
            if (!cleanSomeSlots(i, sz) && sz >= threshold)
                //清除失效entry并进行扩容
                rehash();
        }
```
接着查看replaceStaleEntry方法源码
```
 private void replaceStaleEntry(ThreadLocal<?> key, Object value,
                                       int staleSlot) {
            Entry[] tab = table;
            int len = tab.length;
            Entry e;
            int slotToExpunge = staleSlot;
            //向前扫描，查找最前的一个无效slot
            for (int i = prevIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = prevIndex(i, len))
                if (e.get() == null)
                    slotToExpunge = i;

            //向后遍历table
            for (int i = nextIndex(staleSlot, len);
                 (e = tab[i]) != null;
                 i = nextIndex(i, len)) {
                ThreadLocal<?> k = e.get();

                if (k == key) {
                    e.value = value;
                    tab[i] = tab[staleSlot];
                    tab[staleSlot] = e;

                    // Start expunge at preceding stale entry if it exists
                    if (slotToExpunge == staleSlot)
                        slotToExpunge = i;
                    // 清理无效slot
cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
                    return;
                }

                // 如果当前的slot已经无效，并且向前扫描过程中没有无效slot，
                // 则更新slotToExpunge为当前位置
                if (k == null && slotToExpunge == staleSlot)
                    slotToExpunge = i;
            }

            // key没找到就创建一个新的entry
            tab[staleSlot].value = null;
            tab[staleSlot] = new Entry(key, value);

            // 如果运行中有其他无效的slot则删除它们对应的entry
            if (slotToExpunge != staleSlot)
                cleanSomeSlots(expungeStaleEntry(slotToExpunge), len);
        }
```
查看cleanSomeSlots方法源码
```
     private boolean cleanSomeSlots(int i, int n) {
            boolean removed = false;
            Entry[] tab = table;
            int len = tab.length;
            do {
                i = nextIndex(i, len);
                Entry e = tab[i];
                if (e != null && e.get() == null) {
                    n = len;
                    removed = true;
                    i = expungeStaleEntry(i);
                }
            } while ( (n >>>= 1) != 0);
            return removed;
        }
```
清除无效的slot发现最后还是调用的expungeStaleEntry方法。

查看ThreadLocalMap的rehash方法源码
```
private void rehash() {
            //清除table中所有的失效的Entry
            expungeStaleEntries();

            // Use lower threshold for doubling to avoid hysteresis
            if (size >= threshold - threshold / 4)
                //对table的容量进行2倍扩容
                resize();
        }
//继续查看
 private void resize() {
            Entry[] oldTab = table;
            int oldLen = oldTab.length;
            //以原来表容量的2倍进行扩容
            int newLen = oldLen * 2;
            Entry[] newTab = new Entry[newLen];
            int count = 0;

            for (int j = 0; j < oldLen; ++j) {
                Entry e = oldTab[j];
                if (e != null) {
                    ThreadLocal<?> k = e.get();
                    if (k == null) {
                        e.value = null; // Help the GC
                    } else {
                        int h = k.threadLocalHashCode & (newLen - 1);
                        while (newTab[h] != null)
                            h = nextIndex(h, newLen);
                        newTab[h] = e;
                        count++;
                    }
                }
            }

            setThreshold(newLen);
            size = count;
            table = newTab;
        }
```
分析结束。





