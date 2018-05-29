### singleTop 中newIntent 调用问题
在singleTop ，singleInstance，singleTask中 若实例在各任务栈栈顶时 ，将会调用onNewIntent()方法而不会调用onCreate方法
若在singleTop模式下，且实例不在任务栈栈顶，则将会执行onCreate方法
### onSaveInstanceState与onRestoreInstanceState触发时机
onSaveInstanceState()方法的触发时机  
1. 当用户按下Home键时
2. 长按Home键选择运行其他的程序时
3. 按下电源键（关闭屏幕时）
4. 从当前一个Activity启动一个新的Activity时

5. 屏幕方向切换时

> 在前四种情况下，当前Activity的生命周期为:  
> onPause->onSaveInstanceState->onStop。

onRestoreInstanceState()方法的触发时机
1. onRestoreInstanceState()方法只有在Activity确实是被系统回收，重新创建Activity的情况下才会被调用
2. 在屏幕方向切换时 Activity的生命周期如下：
 ```
 onPause -> onSaveInstanceState -> onStop -> onDestroy -> onCreate -> onStart -> onRestoreInstanceState -> onResume
 
 ```   

### https
### 静态内部类的特点

静态内部类是指被声明为static的内部类，不能访问外部类的普通成员变量，只能访问外部类中的静态成员变量和静态方法
静态内部类作用：只是为了降低包的深度，方便类的引用，静态内部类适用于包含类中，但又不依赖与外在的类，不使用外在类的非静态属性和方法，只是为了方便管理类的结构而定义
，在创建静态内部类的时候，不需要外部类对象的引用。

非静态内部类有一个很大的优点：可以自由使用外部类的所有变量和方法

从字面上看，一个被称为静态嵌套类，一个被称为内部类。  
从字面的角度解释是这样的：  
什么是嵌套？嵌套就是我跟你没关系，自己可以完全独立存在，但是我就想借你的壳用一下，来隐藏一下我自己（真TM猥琐）。  
什么是内部？内部就是我是你的一部分，我了解你，我知道你的全部，没有你就没有我。（所以内部类对象是以外部类对象存在为前提的）  

### 双层ViewGroup 侧边滑动问题

在外层ViewGroup中 重写onInterceptTouchEvent方法 
把x坐标与屏幕宽进行差值对比相差50 则返回值设置为false 

### 硬件加速 绘制View 与程序绘制View的不同

### RecycleView是如何优化的
一、标准化了ViewHolder，使用Adapter适配器时，面向ViewHolder而不是单纯的View，直接把ViewHolder的实现封装起来，  
用户只要实现自己的ViewHolder就可以了，该组件会自动帮你回收并复用每一个item。  
不但变得更精简，也变得更加容易使用，而且更容易组合设计出自己需要的滑动布局。  
二、将Layout抽象成了一个LayoutManager，RecyclerView不负责子View的布局，
而是通过使用LayoutManager来实现不同的布局效果，如使用LinearLayoutManager来指定方向，
其默认是垂直，也可以设置为水平，当然你也可以自己来定义。

### 线程池
