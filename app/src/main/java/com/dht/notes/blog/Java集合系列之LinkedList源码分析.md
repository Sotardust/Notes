![](https://img.hacpai.com/bing/20190212.jpg?imageView2/1/w/960/h/540/interlace/1/q/100)

### 前言
LinkedList是基于双向链表实现的，除了可以当链表来操作，它还可以当做栈，队列以及双端队列来使用，且是非线程安全。
```
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```
LinkedList继承了AbstractSequentialList类实现了List集合，Deque队列，Cloneable支持克隆，Serializable支持序列化

### 类的属性
```
//集合长度
transient int size = 0;
//链表头节点
transient Node<E> first;
//链表尾节点
transient Node<E> last;
```
### 内部核心类 Node
```
	
    private static class Node<E> {
        E item;//节点对应元素
        Node<E> next; //当前节点的后一个节点的引用
        Node<E> prev;//当前节点的前一个节点的引用

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
```
### 构造函数
LinkedList 共两个构造函数
```
   //无参构造空链表
   public LinkedList() {
    }

   //构造一个包含指定集合的元素的List
   public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }

```
### LinkedList操作函数

#### add(E e)
向链表尾部添加数据
```
   public boolean add(E e) {
	//向链表尾部添加元素
        linkLast(e);
        return true;
    }

   void linkLast(E e) {
	//把尾结点赋值给新建节点l
        final Node<E> l = last;
	//创建新节点
        final Node<E> newNode = new Node<>(l, e, null);
	//新节点赋值给尾结点
        last = newNode;
	//判断l节点是否为空
        if (l == null)
	    //若为空则把新节点赋值给头结点
            first = newNode;
        else
	    //若不为空则把新节点赋值给尾结点的下一节点
            l.next = newNode;
	//长度+1	
        size++;
	//修改次+1
        modCount++;
    }

```
向链表中添加一个集合
```
public boolean addAll(Collection<? extends E> c) {
	//在链表长度的基础上添加数据集合
        return addAll(size, c);
    }
```

```
public boolean addAll(int index, Collection<? extends E> c) {
	//检查链表长度是否越界，越界则抛出异常，索引可以小于等于链表长度 index <= size
        checkPositionIndex(index);
	//参数转换成数组
        Object[] a = c.toArray();
        int numNew = a.length;
	//判断数组长度为0 则返回false 添加失败
        if (numNew == 0)
            return false;
        Node<E> pred, succ;//创建节点
	//如果插入索引与链表长度相等，则直接在链表尾部插入数据
        if (index == size) {
            succ = null;
            pred = last; //把尾结点赋值给新建节点pred
        } else {
	    //查找索引对应的节点并赋值给succ	
            succ = node(index);
	    //succ上一节点赋值给pred
            pred = succ.prev;
        }
        for (Object o : a) {
            @SuppressWarnings("unchecked") E e = (E) o;
 	    //创建新节点设置e的头结点为pred	
            Node<E> newNode = new Node<>(pred, e, null);
            //判断头结点是否为空，为空则把新建节点赋值给first作为头结点	
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;//不为空则pred.next指向新节点
            pred = newNode;//赋值新节点
        }
        if (succ == null) {//为空，则直接插到尾部
            last = pred; //赋值添加后的链表
        } else {
	    //插入的结点的下一节点指向分开后的节点
            pred.next = succ;
	    //分开后的上一节点指向插入后的节点
            succ.prev = pred;
        }
        size += numNew;//链表长度增加
        modCount++; //修改次数+1
        return true;
    }

```
node()方法返回查找对应的节点
```
Node<E> node(int index) {
        // assert isElementIndex(index);
	//判断插入位置是否小于链表长度的一半
        if (index < (size >> 1)) { //插入位置是在链表前半段
            Node<E> x = first;  //头结点赋值给x
            for (int i = 0; i < index; i++) //遍历链表节点
                x = x.next;
            return x;// 返回对应节点
        } else { //插入位置是在链表前后段
            Node<E> x = last; //尾结点赋值给x
            for (int i = size - 1; i > index; i--) //遍历链表节点
                x = x.prev;
            return x; // 返回对应节点
        }
    }

```
####  get(int index) 
获取对应下标节点对应元素值

```
   public E get(int index) {
	//检查元素是否越界 该索引不包含链表长度 index < size
        checkElementIndex(index);
	//通过node方法查找对应节点所对应的元素值
        return node(index).item;
    }
```
####  set(int index, E element) 
在对应下标设置元素值
```
    public E set(int index, E element) {
	//检测索引是否越界
        checkElementIndex(index);
	//通过node方法查找对应下标节点
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal; //返回对应节点值
    }
```
#### remove(int index)
删除对应下标节点
```
 public E remove(int index) {
	//检查索引是否越界
        checkElementIndex(index);
	//通过node方法查找出对应节点	
        return unlink(node(index));
    }
```
```
 E unlink(Node<E> x) {
        // assert x != null;
	//新建对象元素，以及首尾节点
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;
	
        if (prev == null) { //若头节点为空，则把尾结点赋值给first节点
            first = next;
        } else {
            prev.next = next; //x的上一节点的指向的下一元素为x的下一节点
            x.prev = null;//释放节点的前一个元素
        }

        if (next == null) { //若尾结点为空,则头节点等于尾节点
            last = prev; 
        } else { //尾节点不为空
            next.prev = prev;  
            x.next = null; // 释放节点的后一个元素
        }
        x.item = null;//元素值 置为null
        size--;//链表长度-1
        modCount++; //修改次数+1
        return element; //返回删除的元素值 
    }
```

上篇分析了ArrayList 的源码，总结下二者的区别
### ArrayList与LinkedList区别
> 1、两者都是继承List、Collection接口
2、ArrayList是基于动态数据的数据结构，LinkedList是基于循环双向链表的数据结构
3、ArrayList存储地址是内存连续，所以通过索引查询速度快，但插入、删除数据需要向前或向后移动数组速度慢
4、LinkedList是链表结构，存储地址是不连续的，查找需要遍历链表速度比较慢，插入或删除则只需要移动指针调整前后元素的引用即可，效率比较高。

### 总结 
以上主要分析了LinkedList的常用的add、set、remove等方法的源码。
上篇分析了ArrayList的源码，对二者做比较并总结了二者之间的区别，后续会继续分析其他集合的源码。

> 相关文章阅读
[Java集合系列之HashMap源码分析](https://www.sotardust.cn/articles/2019/09/16/1568600871079.html)
[Java集合系列之ArrayList源码分析](https://www.sotardust.cn/articles/2019/10/24/1571910917492.html)

#### Android 源码解析系列分析

> [自定义View绘制过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419491078.html)
[ViewGroup绘制过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419461969.html)
[ThreadLocal 源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419434677.html)
[Handler消息机制源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419402891.html)
[Android 事件分发机制源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419369662.html)
[Activity启动过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419276652.html)
[Activity中View创建到添加在Window窗口上到显示的过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419224191.html)

