### 1、activity 启动模式 ，新增了一种

singleInstancePerTask - 大屏幕上构建并排功能的一种优化模式

应用场景：多窗口并排功能的时候可以使用，例如：Chrome 浏览器的多窗口

### 2、HashMap key值可以为空

### 3、app启动流程
Launcher ->

###### 4、recycleView， listView 的区别
    1、缓存机制问题  recycleView 四级缓存 ，listView 二级缓存

    2、局部刷新
    RecyclerView更大的亮点在于提供了局部刷新的接口，通过局部刷新，就能避免调用许多无用的bindView。ListView和RecyclerView最大的区别在于数据源改变时的缓存的处理逻辑，ListView是"一锅端"，

    3、不支持瀑布流

5、LeakCanary









