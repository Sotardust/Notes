![](https://img.hacpai.com/bing/20171231.jpg?imageView2/1/w/960/h/540/interlace/1/q/100) 



### APK打包过程  
* 打包资源文件，生成R.java文件，使用appt（Android Asset Package Tool）打包资源文件并生成R.java文件

* 处理aidl文件，生成相应的Java文件

* 编译工程源代码，生成相应的class文件

* 转换所有class文件，生成classes.dex文件

* 打包生成apk

* 对apk文件进行签名

* 对签名后的apk文件进行处理

![apk打包流程图](https://www.sotardust.cn/images/apk_packaging_process.png)
---
### 具体步骤
##### 1.打包资源文件，生成R.java文件。
【输入】Resource文件（就是工程中res中的文件）、Assets文件（相当于另外一种资源，这种资源Android系统并不像对res中的文件那样优化它）、AndroiMainfest.xml文件(包名就是从这里读取的，因为生产个R.java文件需要包名)、Android基础类库（Android.jar文件）
【工具】aapt工具（Android Asset Package Tool）
【输出】打包好的资源（bin目录中的resources.ap_文件）、R.java文件（gen目录中）
##### 2. 处理aidl文件，生成相应的Java文件
【输入】源码文件、aidl文件、framework.aidl文件
【工具】aidl工具
【输出】对应的.java文件
对于名没有使用到aidl的android工程，这不直接跳过，aidl工具解析解扣子定义文件并生成相应的java文件供程序调用。
##### 3. 编译工程源码，生成相应的Class文件
【输入】源码文件（包括R.java和AIDL生生成的.java 文件）、库文件（.jar文件）【工具】javac工具
【输出】.class 文件

##### 4. 转换所有的class文件，生成classes.dex文件
【输入】.class文件（包括AIDL生成的.class文件，R生成的.class文件，源文件生成的.class文件），库文件（.jar文件）
【工具】javac工具
【输出】.dex文件
android系统dalvik虚拟机的可执行文件为dex格式，程序运行所需的classes.dex文件就是在这一步生成的，使用的工具为dx，dx工具主要的工作是将Java字节码转换为dalvik字节码、压缩常量池、消除冗余信息等。
##### 5. 打包生成 apk
【输入】打包后的资源文件，打包后类文件（.dex文件）、libs文件（包括.so文件，）
【工具】apkbuilder工具
【输出】未签名的.apk文件

##### 6.对apk文件进行签名
【输入】未签名的.apk文件
【工具】jarsiger
【输出】签名的.apk文件

##### 7. 对签名后的apk文件进行对齐处理
【输入】签名后的.apk文件
【工具】zipalign工具
【输入】对齐后的.apk文件



---
参考文章：
[android Apk打包过程概述](https://blog.csdn.net/jason0539/article/details/44917745)

