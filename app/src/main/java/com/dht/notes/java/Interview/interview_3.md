handler looper 原理
Activity的启动过程
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

activity ,service application 中的content的区别

