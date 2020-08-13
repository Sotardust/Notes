![](https://img.hacpai.com/bing/20181230.jpg?imageView2/1/w/960/h/540/interlace/1/q/100)  
### HashMap概述
官方文档中这样描述HashMap：
> Hash table based implementation of the <tt>Map</tt> interface.  This implementation provides all of the optional map operations, and permits <tt>null</tt> values and the <tt>null</tt> key.  (The <tt>HashMap</tt> class is roughly equivalent to <tt>Hashtable</tt>, except that it is unsynchronized and permits nulls.)  This class makes no guarantees as to the order of the map; in particular, it does not guarantee that the order will remain constant over time.

大致意思为：HashMap是基于哈希表的Map接口的实现。这个实现提供了map的所有可选操作，并且允许null值（可以多个）和一个null的key（仅限一个）。HashMap和HashTable十分相似，除了HashMap是非同步的且允许null元素。这个类不保证map里的顺序，特别是，随着时间的推移它不保证顺序一直不变。

###  原理     

HashMap是一种'“数组+链表+红黑树”的数据结构，在put操作中，通过内部定义算法对key进行hash计算，算出哈希值，再通过（n - 1) & hash）计算出数组下标，将数据直接放入此数组中，若通过算法得到的该数组元素已经存在（俗称哈希冲突，使用链表结构便为了解决哈希冲突问题，即拉链法）。将会把这个元素上的链表进行遍历，将新的数据放到链表末尾。若链表长度为8时则将链表转为红黑树。
![](https://www.sotardust.cn/images/hashmap_under_data_structure.png)

---

### 重要参数(变量)
HashMap中重要的参数（变量）有两个即：loadFactor（负载因子）与threshold（扩容阈值）
```
//负载因子* 
 final float loadFactor;
//默认负载因子为 0.75 ，这是权衡了时间复杂度与空间复杂度之后的最好取值
static final float DEFAULT_LOAD_FACTOR = 0.75f;

// 扩容阈值（threshold）：当哈希表中数据长度 ≥ 扩容阈值时，就会扩容哈希表（即扩充HashMap的容量） 
//  扩容 = 对哈希表进行resize操作（即重建内部数据结构），从而哈希表将具有大约两倍的桶数
//  扩容阈值 = 容量 * 负载因子
int threshold;

```
```
// 存储数据的Node类型 数组，长度 = 2的幂；
//数组的每个元素 = 1个单链表
transient Node<K,V>[] table;  

//默认初始容量为16
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

//最大容量为 2的30次方
static final int MAXIMUM_CAPACITY = 1 << 30;

//桶的树化阈值：即 链表转成红黑树的阈值，在存储数据时，当链表长度 > 该值时，
//则将链表转换成红黑树
static final int TREEIFY_THRESHOLD = 8;

//桶的链表还原阈值：即 红黑树转为链表的阈值，当在扩容（resize（））时
//（此时HashMap的数据存储位置会重新计算），在重新计算存储位置后，
//当原有的红黑树内数量 < 6时，则将 红黑树转换成链表
static final int UNTREEIFY_THRESHOLD = 6;

// 最小树形化容量阈值：即 当哈希表中的容量 > 该值时，才允许树形化链表 （即 将链表 转换成红黑树）
// 否则，若桶内元素太多时，则直接扩容，而不是树形化
// 为了避免进行扩容、树形化选择的冲突，这个值不能小于 4 * TREEIFY_THRESHOLD
static final int MIN_TREEIFY_CAPACITY = 64;

```

### 构造函数
HashMap共有四种构造函数

```
 //默认构造方法	负载因子，容量均为默认值 =0.75,16
 public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

 // 指定初始容量 负载因子 = 0.75
 public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

 public HashMap(int initialCapacity, float loadFactor) {
	//指定初始容量必须大于0否则报错
        if (initialCapacity < 0) 
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
	//hashMap的最大容量只能是MAXIMUM_CAPACITY，即使传入的值 > 最大容量
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
	//填入的负载因子必须为正
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
	//设置负载因子
        this.loadFactor = loadFactor;
	//设置扩充阈值，此值不是真正的阈值（后面会重新计算），此值为调用该构造方法的  
        //初始容量 = 传入的容量大小转化为：>传入容量大小的最小2次幂
        this.threshold = tableSizeFor(initialCapacity);
    }

    public HashMap(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
	//将传入的子Map中的全部元素逐个添加到HashMap中
        putMapEntries(m, false);
    }
```
```
     // 作用：将传入的容量大小转化为：>传入容量大小的最小的2的幂
     // eg: 8 = tableSizeFor(5);
    static final int tableSizeFor(int cap) {
     //这是为了防止，cap已经是2的幂。如果cap已经是2的幂，又没有执行这个减1操作，
     //则执行完后面的几条无符号右移操作之后，返回的capacity将是这个cap的2倍
     int n = cap - 1;
     //n分别与n无符号右移1,2,4,8,16位后进行或运算
     n |= n >>> 1;
     n |= n >>> 2;
     n |= n >>> 4;
     n |= n >>> 8;
     n |= n >>> 16;
     return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

```
我将以`HashMap<String,Integer> hashMap = new HashMap(5)`为例对HashMap整个存储，查找流程进行分析。
```
HashMap<String, Integer> hashMap = new HashMap<>(5);

        hashMap.put("Java", 1);
        hashMap.put("Kotlin", 2);
        hashMap.put("Android", 3);
        hashMap.put("Flutter", 4);
        hashMap.put("Python", 5);
        hashMap.put("C", 6);
        hashMap.put("C++", 7);
        hashMap.put("PHP", 8);
        hashMap.put("Objective-C", 9);
        hashMap.put("JavaScript", 10);
        hashMap.put("Mysql", 11);
        hashMap.put("Swift", 12);
        hashMap.put("Go", 13);

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            Log.d(TAG, "HashMap  = [" + entry.getKey() + " -> " + entry.getValue() + "]");
        }
```
输出结果为：
> HashMap  = [Java -> 1]
 HashMap  = [C++ -> 7]
 HashMap  = [C -> 6]
 HashMap  = [Go -> 13]
 HashMap  = [Kotlin -> 2]
 HashMap  = [Android -> 3]
 HashMap  = [JavaScript -> 10]
 HashMap  = [Mysql -> 11]
 HashMap  = [PHP -> 8]
 HashMap  = [Objective-C -> 9]
 HashMap  = [Flutter -> 4]
 HashMap  = [Swift -> 12]
 HashMap  = [Python -> 5]

其执行过程地址变换图
![](https://www.sotardust.cn/images/hashmap_source_code.execution_process_wm.png)

执行过程为图中所示，第一次扩容阈值threshold 为经过tableSizeFor(5)计算得出为8，也就是HashMap的实际容量初始值，后续threshold的值为 = 容量*负载因子，
当HashMap中数据长度大于扩容阈值threshold时，才会对HashMap进行扩容，capacity左移一位(capacity << 1) 变为原来容量的2倍。
### put函数
HashMap调用put方法对数据进行存储，该方法源码为
```
 // 输入的Key -Value	键值对
 public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

  //根据对Key值进行hash计算
  static final int hash(Object key) {
        int h;
	//此处说明key允许为空，若key不为空 则对key的HashCode的高16位与低16位进行异或操作
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
	//创建Node<K,V>数组tab 存放数据
        Node<K,V>[] tab; Node<K,V> p; int n, i;
	//若哈希表的table为空或者table长度为0则进行resize()操作新建table
	//所以初始化哈希表的时机是第一次调用put函数时，即调用resize()方法初始化创建	
        if ((tab = table) == null || (n = tab.length) == 0)
	    //table表长度即table容量capacity
            n = (tab = resize()).length; 
	//table不为空，计算插入存储的数组索引i，
        // 下标 i 计算方式  = （n-1）& hash 即 capacity -1和hash值进行按位与运算 得到下标 i，
        //再判断tab[i]是否为空，为空则创建Node<K,V>节点 赋值给tab[i]
        //不为空则代表存在hash冲突，及当前存储位置已存在节点
        if ((p = tab[i = (n - 1) & hash]) == null)
	    //创建Node 并赋值		
            tab[i] = new Node(hash, key, value, null);
        else {
	    //tab[i]不为空，说明该坐标下已经存在值
            Node<K,V> e; K k;
	    //如果tab[i]元素对应的Key与要插入的Key值相等，则直接把tab[i]赋值给e
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            //判断是否是红黑树，若是则进行插值
            else if (p instanceof TreeNode) 
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);  
            //判断是否为链表是则进行链表操作，插入新数据到链表尾部中
            else {
		//对链表进行遍历，并统计链表长度			
                for (int binCount = 0; ; ++binCount) {
                    //在链表尾部添加新的节点
                    if ((e = p.next) == null) {
 			//添加新节点
                        p.next = newNode(hash, key, value, null);
			//如果链表长度大于等于树化阈值，则把链表转为红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
		    //若当前链表包含要插入的key ，则跳出循环
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
		    //把链表的下一位赋值给p
                    p = e;
                }
            }
	     //e不为空 即对应key已经存在则把旧value更新为新value
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
		//put方法进来 onlyIfAbsent默认为false ,该方法则一直执行	
                if (!onlyIfAbsent || oldValue == null)
		    //新value 覆盖 旧value	
                    e.value = value;
                //该方法在LinkedHashMap中调用在HashMap中为空接口
                afterNodeAccess(e);
                return oldValue;
            }
        }
	//修改次数统计
        ++modCount;
	//判断实际存在的键值对是否大于扩充阈值，大于则进行resize()方法进行扩容
        if (++size > threshold)
            resize();
        //该方法在LinkedHashMap中调用在HashMap中为空接口
        afterNodeInsertion(evict);
        return null;
    }
```
分析完put方法源码后，可以知道其大致流程为
> 1. 先判断table 是否为空 ，为null 则resize()进行初始化
> 2. 通过 （(capacity-1) & hash ）计算出索引下标 i
> 3. 判断 i节点是否为null，为null添加节点 ,否则代表hash冲突
> 4. 若哈希冲突，则转为链表形式存在
> 5. 若链表长度超过树形阈值8 则转为红黑树
> 6. 若key已经存在则新value 覆盖旧value
---
其流程图为
![HashMap的put方法流程图](https://www.sotardust.cn/images/hashmap_put_flowchart.png)

### resize函数
从put方法中可以知道，创建HashMap对象的并没有进行初始化，只是在put第一个键值对的时候执行resize()方法初始化哈希表。 
在此方法中设置capacity以及threshold = capacity * loadfactor ，并对Node<K,V>[] table进行初始化 。resize()方法源码为：

```
//调用该方法 1、初始化哈希表 ，2、扩容
final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table; //扩容前的数据
        int oldCap = (oldTab == null) ? 0 : oldTab.length; //扩充前的数据的容量
        int oldThr = threshold;  //扩容前的数组的阈值
        int newCap, newThr = 0; // 新容量，新扩容阈值
        if (oldCap > 0) {  
	     //扩充容量 超过最大容量时，则不再扩充
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
	    //	若无超过容量最大值，就扩充为原来的2倍            
	    //判断新容量是否小于最大容量，大于默认容量16 ,为true则 新扩充阈值 =原来的2倍
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
		//左移一位值变为原来的2倍
                newThr = oldThr << 1; // double threshold
        }
	//初始化时执行该语句，扩容前阈值 > 0 则扩容阈值 = 新容量
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
	    // 容量和扩容阈值均为0时，也就是执行默认方法 ，capacity = 16，threshold = capacity * 0.75 =12 ；
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
	//新扩充阈值等于0时会重新计算扩充阈值
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
	// 赋值
        threshold = newThr;
	//创建扩容后的table
        @SuppressWarnings({"rawtypes","unchecked"})	
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
	//判断扩容前的表数据是否为空
        if (oldTab != null) {
	    //不为空则进行遍历，把每个oldtab移动到newtab表中
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
		//判断该节点是否为空
                if ((e = oldTab[j]) != null) { 
                    oldTab[j] = null; //清空oldtab[i] 
		     //判断节点下一位是否为空,为空则重新计算在newTab中对应的下标 并赋值给newTab
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
		    //判断该节点是否数据红黑树	
                    else if (e instanceof TreeNode)
			//后续再分析红黑树
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
		     //节点是链表结构
                    else { // preserve order
			//低位头结点，尾结点
                        Node<K,V> loHead = null, loTail = null;
			//高位头结点，尾结点
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;  
			    //这段决定了该结点被分到低位还是高位，依据算式是e.hash mod oldCap，由于oldCap是扩展前数组的大小，
			    //所以一定是2的指数次幂，所以bit一定只有一个高位是1其余全是0  
	                    //这个算式实际是判断e.hash新多出来的有效位是0还是1，若是0则分去低位链表，是1则分去高位链表
                            if ((e.hash & oldCap) == 0) { //等于0判断为低位（原索引）
				 //判断低位尾部是否为空
                                if (loTail == null) 
                                    loHead = e; //赋值给低位头结点
                                else
                                    loTail.next = e; //赋值给低位尾结点下一节点
                                loTail = e;//赋值给低位尾结点
                            }
                            else { //等于1判断为高位（原索引+oldCap）
                                if (hiTail == null)
                                    hiHead = e;//赋值给高位头结点
                                else
                                    hiTail.next = e; //赋值给高位尾结点下一节点
                                hiTail = e;//赋值给高位尾结点
                            }
                        } while ((e = next) != null); //遍历知道链表下一节点为空为止
                        if (loTail != null) {  //低位结点放在新表中原索引位置
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {//高位结点放在新表中（原索引+oldCap)位置
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab; 
    }
```
put方法和resize方法中用到的Node的源码为
```
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }
```
Node 是 HashMap的内部类，实现了Map.Entry接口，本质是 = 一个映射(键值对)
实现了getKey()、getValue()、equals(Object o)和hashCode()等方法。

### get函数
get方法源码为
```
    public V get(Object key) {
        Node<K,V> e;
	//对key进行hash计算 获取其哈希值
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

  final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
	//判断table是否为空，且通过(n-1) & hash 计算出的索引对应的tab不能为空 否则返回空值
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
	    //判断第一个结点的key是否与查找的key的值是否相等，相等直接返回
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;                        
	    //判断Node<key,value>[i]的下一节点是否为空
            if ((e = first.next) != null) { 
                if (first instanceof TreeNode)  //若是红黑树直接从树中查找
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
		//遍历链表节点 查找判断key是否与要查找的key的值相等（equals()）,若存在直接返回
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null); 
            }
        }
        return null;
    }
```
其get方法流程图为：
![](http://www.sotardust.cn/images/hashmap_get_flowchart.png)

### 总结
本文中主要分析了 HashMap的 get，put以及resize方法源码，后续会继续分析Java的集合源码。

> 相关文章阅读
[Java集合系列之ArrayList源码分析](https://www.sotardust.cn/articles/2019/10/24/1571910917492.html#b3_solo_h5_11)

#### Android 源码解析系列分析

> [自定义View绘制过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419491078.html)
[ViewGroup绘制过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419461969.html)
[ThreadLocal 源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419434677.html)
[Handler消息机制源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419402891.html)
[Android 事件分发机制源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419369662.html)
[Activity启动过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419276652.html)
[Activity中View创建到添加在Window窗口上到显示的过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419224191.html)


### 参考文章
[Java HashMap工作原理及实现](https://yikun.github.io/2015/04/01/Java-HashMap%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86%E5%8F%8A%E5%AE%9E%E7%8E%B0/)
[Java源码分析：HashMap 1.8 相对于1.7 到底更新了什么？](https://www.jianshu.com/p/8324a34577a0)
[Java集合之HashMap源码解析](https://segmentfault.com/a/1190000015213253)
