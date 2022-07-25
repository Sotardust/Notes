### Android动态加载之ClassLoader详解
Android平台上虚拟机运行的是Dex字节码,一种对class文件优化的产物,传统Class文件是一个Java源码文件会生成一个.class文件，
而Android是把所有Class文件进行合并，优化，然后生成一个最终的class.dex,目的是把不同class文件重复的东西只需保留一份,
如果我们的Android应用不进行分dex处理,最后一个应用的apk只会有一个dex文件。

![Android平台的ClassLoader](https://upload-images.jianshu.io/upload_images/1437930-bb9d359f4c7e9935.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700)

Android中 类加载器有 `BootClassLOader`,`URLClassLoader `,`PathClassLoader`,`DexClassLoader`,`BaseDexClassLoader` 等都是最终继承自 `Java.lang.ClassLoader`
