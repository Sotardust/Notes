面试总结：  
1、形参，实参  
2、String a = new String("1"+"2"); 共几个对象  
**共两个对象 一个是 “12”，一个是new创建的s对象指向a**

3. final，finalize ，finizedly（try{
    return 1}catch（）{
    return2 ）finally{
    return3}  
final 用于声明属性，方法和类，分别表示属性不可变，方法不可覆盖，类不可继承。  
finally 是异常处理语句结构的一部分，表示总是执行。 最后结果然后 `return 3` 
finalize是Object类的一个方法，在垃圾收集器执行的时候会调用被回收对象的此方法，可以覆盖此方法提供垃圾收集时的其他资源回收，例如关闭文件等
4、syn代码块，syn object  voliate
一、当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。 
二、然而，当一个线程访问object的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。 
三、尤其关键的是，当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞。
[ Java并发——线程同步Volatile与Synchronized详解](http://blog.csdn.net/seu_calvin/article/details/52370068)
1. volatile修饰的变量具有可见性  
    volatile是变量修饰符，其修饰的变量具有可见性。
    可见性也就是说一旦某个线程修改了该被volatile修饰的变量，它会保证修改的值会立即被更新到主存，当有其他线程需要读取时，可以立即获取修改之后的值。  
2. volatile禁止指令重排 
总结
（1）从而我们可以看出volatile虽然具有可见性但是并不能保证原子性。

（2）性能方面，synchronized关键字是防止多个线程同时执行一段代码，就会影响程序执行效率，而volatile关键字在某些情况下性能要优于synchronized。

但是要注意volatile关键字是无法替代synchronized关键字的，因为volatile关键字无法保证操作的原子性。


5、gc
6、Sharedreference.Editor的apply与commit方法异同
这两个方法的区别在于： 
1. apply没有返回值而commit返回boolean表明修改是否提交成功 
2. apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, 而commit是同步的提交到硬件磁盘，因此，在多个并发的提交commit的时候，
   他们会等待正在处理的commit保存到磁盘后在操作，从而降低了效率。而apply只是原子的提交到内容，后面有调用apply的函数的将会直接覆盖前面的内存数据，这样从一定程度上提高了很多效率。 
3. apply方法不会提示任何失败的提示。 
由于在一个进程中，sharedPreference是单实例，一般不会出现并发冲突，如果对提交的结果不关心的话，建议使用apply，当然需要确保提交成功且有后续操作的话，还是需要用commit的。
### java中的形参和实参的区别以及传值调用和传值引用调用
#### 基础数据类型（传值调用）
       int a = 2;
       int b = 3;
       change(a, b);  //传值调用，值改变的是形参，不会作用到实参 ，a，b值不变
       System.out.println("a = " + a);
       System.out.println("b = " + b);
    
        private void change(int m, int n) {
            int temp = m;
            m = n;
            n = temp;
        }
        
        输出结果：
        
        a = 2
        b = 3
        
#### 引用数据类型（引用调用）
传引用，方法体内改变形参引用，不会改变实参引用，但有可能改变实参对象的属性值
###### 方法体内改变形参引用，但不会改变实参引用 ，实参值不变。
    public class TestFun2 {  
    public static void testStr(String str){  
    str="hello";//型参指向字符串 “hello”  
    }  
    public static void main(String[] args) {  
    String s="1" ;  
    TestFun2.testStr(s);  
    System.out.println("s="+s); //实参s引用没变，值也不变  
    }  
    }
    
    输出结果：
        s=1
###### 方法体内，通过引用改变了实际参数对象的内容，注意是“内容”，引用还是不变的。
    public class TestFun4 {  
    public static void testStringBuffer(StringBuffer sb){  
    sb.append("java");//改变了实参的内容  
    }  
    public static void main(String[] args) {  
    StringBuffer sb= new StringBuffer("my ");  
    new TestFun4().testStringBuffer(sb);  
    System.out.println("sb="+sb.toString());//内容变化了  
    }  
    }
    
    输出：sb=my java 