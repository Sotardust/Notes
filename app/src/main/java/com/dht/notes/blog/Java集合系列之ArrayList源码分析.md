### 数组(Array)
数组指的就是一组相关类型的变量集合，并且这些变量可以按照统一的方式进行操作，数组数据引用数据类型，在堆中进行内存分配，在内存中是连续存在，大小固定的。
### ArrayList
ArrayList可以算是数组的加强版，其继承AbstractList接口，实现了List，RandomAccess，Cloneable接口，可序列化。在存储方面 数组可以包含基本类型和对象类型，比如：int[],Object[]，ArrayList只能包含对象类型；在空间方面，数组的空间大小是固定的，空间不够时不能再次申请，所以需要事前确定合适的空间大小。ArrayList的空间是动态增长的，如果空间不足，它会创建一个1.5倍大的新数组，然后把所有元素复制到新数组，而且每次添加新的元素时会检测内部数组的空间是否足够。
### 源码解析
#### 变量

```
    //默认初始容量	
    private static final int DEFAULT_CAPACITY = 10;
    //空数组	
    private static final Object[] EMPTY_ELEMENTDATA = {};

    //空数组与EMPTY_ELEMENTDATA 区别在于添加第一个元素时，扩充多少
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    //实例数组对象
    transient Object[] elementData; // non-private to simplify nested class access

    //数组大小
    private int size;
```
#### 构造函数
 ArrayList的构造方法有三种：
```
//自定义初始容量
public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
	    //初始化容量大小	
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
	    //容量初始值不能 < 0 小于零会抛出异常
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }

//常用无参构造函数，默认数组大小为 10
public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

//创建一个包含collection的ArrayList
public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
		//构造大小为size的Object[]数组赋值给elementData
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // 替换空数组
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

```
#### 常用函数(方法)
##### add函数
ArrayList调用add()方法添加函数，源码为
```
//在数组尾部添加元素	
public boolean add(E e) {
	//长度+1，也就是修改次数+1 确保内部容量
        ensureCapacityInternal(size + 1);  // Increments modCount!!
	//数组下标+1 并赋值
        elementData[size++] = e;
        return true;
    }

private void ensureCapacityInternal(int minCapacity) {
	//若数组元素为空，取最小容量，与默认容量的最大值做为最小容量
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
	//明确ArrayList的最小容量
        ensureExplicitCapacity(minCapacity);
    }
//用于内部优化确保空间资源不被浪费
private void ensureExplicitCapacity(int minCapacity) {
	//修改统计数+1，主要用来实现fail-fast机制
        modCount++;

        // 防止溢出，保证最小容量 > 数组缓冲区当前长度
        if (minCapacity - elementData.length > 0)
	    //增加容量
            grow(minCapacity);
    }

```
```
 //增加容量以确保它至少可以容纳最小容量参数指定的元素数量。
 private void grow(int minCapacity) {
        // 元素长度为旧容量大小
        int oldCapacity = elementData.length;
	// 新容量= 旧容量 + 旧容量右移一位（旧容量/2）
        int newCapacity = oldCapacity + (oldCapacity >> 1);
	//判断新容量与最小容量大小 
        if (newCapacity - minCapacity < 0)
	    //最小容量>新容量 ，则新容量为最小容量
            newCapacity = minCapacity;
	//若新容量 > 最大容量 ，对新容量重新计算
        if (newCapacity - MAX_ARRAY_SIZE > 0)
	    //重新计算新容量
            newCapacity = hugeCapacity(minCapacity);
        // 扩容并赋值数组元素
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

private static int hugeCapacity(int minCapacity) {
	//若最小容量 < 0 则抛出异常
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
	//最小容量大于 最大数组长度，则返回int最大值作为容量大小
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }

```
查看复制数组方法
```
Arrays.copyOf(elementData, newCapacity)

//Arrays.java类中的方法
public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    
//复制指定的数组，截断或使用null填充（如果需要），以便副本具有指定的长度。
// 对于在原始数组和副本中均有效的所有索引，两个数组将包含相同的值
public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        @SuppressWarnings("unchecked")
        T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
	//从指定的源数组开始复制数组，从指定的位置开始到目标数组的指定位置
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }

//内部调用System.arraycopy方法
@FastNative
public static native void arraycopy(Object src,  int  srcPos,
                                        Object dest, int destPos,
                                        int length);
```
```
在对应下标出添加数据元素
public void add(int index, E element) {
	//超出数据长度或 小于0 ，则抛出数组越界异常
        if (index > size || index < 0) 
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	///增加容量以确保它至少可以容纳最小容量参数指定的元素数量，修改次数+1
        ensureCapacityInternal(size + 1);  // Increments modCount!!
	//数组拷贝赋值
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);
        elementData[index] = element;
        size++;
    }
//添加数组集合
public boolean addAll(Collection<? extends E> c) {
	//转化数据
        Object[] a = c.toArray();
        int numNew = a.length;
	//修改次数+1
        ensureCapacityInternal(size + numNew);  // Increments modCount
	//数组拷贝赋值
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

```
通过分析add方法可以发现ArrayList内部是调用System.arraycopy方法复制数组。
##### set函数
```
//对应下标设置对应的数组元素，原来的元素被替换掉并返回替换的数组元素
public E set(int index, E element) {
	//下标 >数组长度， 则抛出数组越界异常
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	//查找索引对应数组元素
        E oldValue = (E) elementData[index];
	//数组元素重新赋值
        elementData[index] = element;
        return oldValue;
    }

```
##### get函数
```

public E get(int index) {
	//下标 >数组长度， 则抛出数组越界异常
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	//根据下标直接返回对应数组元素，查找速度快
        return (E) elementData[index];
    }
```
#####  remove函数  
  
```  
//移除数组下标对应的数组元素,返回删除的数组元素  
public E remove(int index) {  
	 //下标 >数组长度， 则抛出数组越界异常  
	 if (index >= size)  
		 throw new IndexOutOfBoundsException(outOfBoundsMsg(index));  
	 //修改次数统计 +1  
	 modCount++;  
	 //通过索引下标查找对应数组元素  
	 E oldValue = (E) elementData[index];  
	 //数组移动个数  
	 int numMoved = size - index - 1;  
	 if (numMoved > 0)  
		 //后续数组元素整体往前移动   
		 System.arraycopy(elementData, index+1, elementData, index,  
		 numMoved);  
	 //数组最后一位元素置空，且长度-1  
	 elementData[--size] = null; // clear to let GC do its work  
	 //返回移除对应数组元素  
	 return oldValue;  
 }  
```  
```  
//删除对应元素，如果删除成功返回true  
public boolean remove(Object o) {  
 	if (o == null) { //判断是否为空  
		 for (int index = 0; index < size; index++)  
			 if (elementData[index] == null) { //若数组下标对应元素为空则移除null元素  
				fastRemove(index);  
				return true;  
		}  
	 } else {//不为空  
		 for (int index = 0; index < size; index++)  
			 //判断要删除的对象在数组是否存在，存在则删除  
			 if (o.equals(elementData[index])) {   
				 fastRemove(index);  
				 return true;  
			 }  
	 }  
	 return false;  
 }  
  
  
 //私有方法快速删除数组元素，该方法与remove(int index)方法类似  
 private void fastRemove(int index) {  
	 modCount++;  
	 int numMoved = size - index - 1;  
	 if (numMoved > 0)  
		 System.arraycopy(elementData, index+1, elementData, index,  
		 numMoved);  
	 elementData[--size] = null; // clear to let GC do its work  
 }  
  
``` 
##### indexof函数
```
//查找数组元素对应下标 ，数组元素	
public int indexOf(Object o) {
        if (o == null) {  //判断是否为空
            for (int i = 0; i < size; i++)//遍历
		//数组元素为null返回对应下标
                if (elementData[i]==null) 
                    return i;
        } else {
            for (int i = 0; i < size; i++)
		//遍历存在对象o 则返回对应下标
                if (o.equals(elementData[i]))
                    return i;
        }
	//数组中不存在，则返回-1
        return -1;
    }
```
#####  subList方法
```
//从ArrayList中截取子列表集合
public List<E> subList(int fromIndex, int toIndex) {
	//子列表集合范围检测
        subListRangeCheck(fromIndex, toIndex, size);
	//返回值为一个SubList对象
        return new SubList(this, 0, fromIndex, toIndex);
    }
```
SubList为ArrayList的一个内部类
```
 private class SubList extends AbstractList<E> implements RandomAccess {
        private final AbstractList<E> parent;
        private final int parentOffset;
        private final int offset;
        int size;

        SubList(AbstractList<E> parent,
                int offset, int fromIndex, int toIndex) {
	    //该参数为父类ArrayList 把自身传了进来	
            this.parent = parent;
	    //开始截取的位置索引			
            this.parentOffset = fromIndex;
            this.offset = offset + fromIndex;
	    //截取后得到的ArrayList长度
            this.size = toIndex - fromIndex;
            this.modCount = ArrayList.this.modCount;
        }


       public void add(int index, E e) {
	    //检测索引是否越界，越界则抛出异常	
            rangeCheckForAdd(index);
	    //检测数组列表是否被修改
            checkForComodification();
            parent.add(parentOffset + index, e);
            this.modCount = parent.modCount;
            this.size++;
        }

        private void checkForComodification() {
	    //判断ArrayList的修改次数与子类的修改次数是否相等，否则抛出并发修改异常	
            if (ArrayList.this.modCount != this.modCount)
                throw new ConcurrentModificationException();
      	  }

	.....
}
```
举个例子：
```
 	ArrayList<String> list = new ArrayList<>();
        list.add("f1");
        list.add("f2");
        list.add("f3");
        
        List<String> subList =list.subList(0,2);
        subList.add("s1");
        subList.remove(1);
        System.out.println("list = " + list);
        System.out.println("subList = " + subList);
```
> 输出为： 
list = [f1, s1, f3]
subList = [f1, s1]

从输出结果可以看出，截取后的subList是可以增删查找的，而list是跟随subList改变而改变的。原因是，在初始化SubList的时候直接把ArrayList 自身传了进去，在subList进行增删查找时相当于是对ArrayList自身操作。

那在subList 执行增删方法后还可以操作list增删吗？答案：是可以的，不过是有一个前提是 不能再对subList进行任何操作，包括输出subList对象。
紧接上面的例子
```
        subList.add("s2");
        list.add("f4");
        System.out.println("list = " + list);
        list.remove("s1");
        System.out.println("list = " + list);
```
> 输出结果为：
list = [f1, s1, f3]
subList = [f1, s1]
list = [f1, s1, s2, f3, f4]
list = [f1, s2, f3, f4]

如果对list进行操作后又对subList操作将会抛出`ConcurrentModificationException`,原因是ArrayList进行增删时修改了modCount ，而 SubList的modCount并没有被修改，检测的时候二者不相等所以抛出异常。


### 总结 
* 数组与ArrayList之间的区别
* 本文主要分析了ArrayList的常用add，remove等方法的源码，以及子类SubList的使用方法。

> 相关文章阅读
[Java集合系列之HashMap源码分析](https://www.sotardust.cn/articles/2019/09/16/1568600871079.html#b3_solo_h3_8)

#### Android 源码解析系列分析

> [自定义View绘制过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419491078.html)
[ViewGroup绘制过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419461969.html)
[ThreadLocal 源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419434677.html)
[Handler消息机制源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419402891.html)
[Android 事件分发机制源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419369662.html)
[Activity启动过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419276652.html)
[Activity中View创建到添加在Window窗口上到显示的过程源码分析](https://www.sotardust.cn/articles/2019/09/02/1567419224191.html)

