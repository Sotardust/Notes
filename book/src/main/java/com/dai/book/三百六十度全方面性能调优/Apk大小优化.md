##### 1、对lib目录下的文件进行瘦身处理
so文件的优化： 只留 abiFilters "armeabi-v7a"

##### 2、优化res、assets文件大小
1、手动lint检查，删除无用资源       
2、使用tinypng等图片压缩工具对图片进行压缩       
3、大部分图片使用Webp格式替代       
4、尽量不要再项目中使用帧动画         
5、使用gradle开启shrinkResources（移除无用的resoure文件）

##### 3、减少classes.dex大小

classes.dex 中包含了所有的 java 代码，当你打包时，gradle 会将所有模板力的.class 文件转换成 classes.dex 文件，当然，如果方法数超过 64K，将要新增其他文件进 行存储。可以通过 multidexing 分多个文件，比如我这里的 chasses2.dex。换句话 说，就是减少代码量。
我们可以通过以下方法来实现：      
尽量减少第三方库的引用，这个在上面我们已经做过优化了。 避免使用枚举，这里特别去网上查了一下，具体可以参考下这篇文章 Android 中 的 Enum 到底占多少内存？该如何用？，得出的结论是，可能几十个枚举的内 存占有量才相当一张图片这样子，优化效果也不会特别明显。当然，如果你是个 追求极致的人，我不反对你用静态常量替代枚举。 如果你的 dex 文件太大，检查是否引入了重复功能的第三方库（图片加载库， glide,picasso,fresco,image_loader，如果不是你一个人单独开发完成的很容易出现 这种情况），尽量做到一个功能点一个库解决。 关于 classes.dex 文件大小分析可以参考这篇译文使用 APK Analyzer 分析你的 APK
