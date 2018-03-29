handler looper 原理

Activity的启动过程  
[Android Activity的启动流程源码解析(8.0)](https://blog.csdn.net/pihailailou/article/details/78545391)

### java文件打包成apk过程
[androidApk打包过程概述_android是如何打包apk的](https://blog.csdn.net/jason0539/article/details/44917745)
1. 打包资源文件，生成R.java文件  
   使用appt（Android Asset Package Tool）打包资源文件并生成R.java文件
2. 处理aidl文件，生成相应java 文件  
3. 编译工程源代码，生成相应class 文件  
4. 转换所有class文件，生成classes.dex文件  
5. 打包生成apk  
6. 对apk文件进行签名  
7. 对签名后的apk文件进行对其处理
  
**第一部：打包资源文件，生成R.java文件。**  
【输入】Resource文件（就是工程中res中的文件）、Assets文件（相当于另外一种资源，这种资源Android系统并不像对res中的文件那样优化它）、AndroidManifest.xml文件（包名就是从这里读取的，因为生成R.java文件需要包名）、Android基础类库（Android.jar文件）  
【工具】aapt工具  
【输出】打包好的资源（bin目录中的resources.ap_文件）、R.java文件（gen目录中） 
    
**第二步：处理aidl文件，生成相应的java文件。**  
【输入】源码文件、aidl文件、framework.aidl文件  
【工具】aidl工具  
【输出】对应的.java文件  
对于没有使用到aidl的android工程，这一步可以跳过。aidl工具解析接口定义文件并生成相应的java代码供程序调用。

**第三步：编译工程源代码，生成下相应的class文件。**  
【输入】源码文件（包括R.java和AIDL生成的.java文件）、库文件（.jar文件）  
【工具】javac工具  
【输出】.class文件  

**第四步：转换所有的class文件，生成classes.dex文件。**  
【输入】 .class文件（包括Aidl生成.class文件，R生成的.class文件，源文件生成的.class文件），库文件（.jar文件）  
【工具】javac工具    
【输出】.dex文件  
  
**第五步：打包生成apk。**  
【输入】打包后的资源文件、打包后类文件（.dex文件）、libs文件（包括.so文件，当然很多工程都没有这样的文件，如果你不使用C/C++开发的话）  
【工具】apkbuilder工具  
【输出】未签名的.apk文件  

**第六步：对apk文件进行签名。**  
【输入】未签名的.apk文件  
【工具】jarsigner  
【输出】签名的.apk文件  

**第七步：对签名后的apk文件进行对齐处理。**  
【输入】签名后的.apk文件  
【工具】zipalign工具  
【输出】对齐后的.apk文件  


activity viewGroup view 之间的联系
setContentView(),inflate()  
[Android 带你彻底理解 Window和WindowManager](https://blog.csdn.net/yhaolpz/article/details/68936932)   
[Android应用程序窗口（Activity）的窗口对象（Window）的创建过程分析](https://blog.csdn.net/luoshengyang/article/details/8223770)

### activity ,service application 中的content的区别 
getApplication()和getApplicationContext() 两者的内存地址都是相同的，看来它们是同一个对象。其实这个结果也很好理解，因为前面已经说过了，Application本身就是一个Context，所以这里获取getApplicationContext()得到的结果就是Application本身的实例。那么问题来了，既然这两个方法得到的结果都是相同的，那么Android为什么要提供两个功能重复的方法呢？实际上这两个方法在作用域上有比较大的区别。getApplication()方法的语义性非常强，一看就知道是用来获取Application实例的，但是这个方法只有在Activity和Service中才能调用的到。那么也许在绝大多数情况下我们都是在Activity或者Service中使用Application的，但是如果在一些其它的场景，比如BroadcastReceiver中也想获得Application的实例，这时就可以借助getApplicationContext()方法了。

**正确使用Context**  
一般Context造成的内存泄漏，几乎都是当Context销毁的时候，却因为被引用导致销毁失败，而Application的Context对象可以理解为随着进程存在的，所以我们总结出使用Context的正确姿势：  
1. 当Application的Context能搞定的情况下，并且生命周期长的对象，优先使用Application的Context。
2. 不要让生命周期长于Activity的对象持有到Activity的引用。
3. 尽量不要在Activity中使用非静态内部类，因为非静态内部类会隐式持有外部类实例的引用，如果使用静态内部类，将外部实例引用作为弱引用持有。
                                    
### Dalvik和标准Java虚拟机之间的主要差别？
||Dalvik|JVM|
|-|-|-|
|数据存储处理|基于寄存器|基于栈|
|运行环境|同时运行多个Dalvik实例|只能运行一个JVM实例|
|数据存储处理|dex文件|class文件|
|数据存储处理|较高|较低|
