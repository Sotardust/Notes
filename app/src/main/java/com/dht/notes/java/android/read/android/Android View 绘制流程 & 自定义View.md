[link](http://www.jianshu.com/p/f0bc39dbfa26)
### 如何对自定义View进行控制

    1. 如果想控制View在屏幕上的渲染效果，就在重写onDraw()方法，在里面进行相应的处理。
    
    2. 如果想要控制用户同View之间的交互操作，则在onTouchEvent()方法中对手势进行控制处理。
    
    3. 如果想要控制View中内容在屏幕上显示的尺寸大小，就重写onMeasure()方法中进行处理。
    
    4. 在 XML文件中设置自定义View的XML属性。
    
    5. 如果想避免失去View的相关状态参数的话，就在onSaveInstanceState() 和 onRestoreInstanceState()方法中保存有关View的状态信息。
### 自定义view的分类
    1、继承View重写onDraw方式 
    2、继承ViewGroup派生特殊的Layout 
    3、继承特定的View(比如TextView等)
    4、继承特定的ViewGroup(比如LinearLayout) 
### 自定义View要注意的问题
    1、让View支持wrap_content 
        直接继承View或ViewGroup的控件 都不支持wrap_content 方法 需要在onMeasure方法中设置
    2、如果有必要 支持padding 
        直接继承View的控件，如果不在onDraw方法中处理padding 那么padding属性是无法起作用的 直接继承ViewGroup的控件，
        需要处理padding和子元素的margin对其的影响，否则这两个属性也无效
    3、尽量不要在view中使用handler 因为view提供了post方法 
    4、view中有线程或者动画 要及时停止 
        这是为了防止内存泄漏，可以在onDetachedFromWindow方法中结束，这个方法回调的时机是 
        当View的Activity退出或者当前View被移除的时候 会调用 这时候是结束动画或者线程的好时机 
        另外还有一个对应的方法 onAttachedToWindow 这个方法调用的时机是在包含View的Activity启动时回调 
        回调在onDraw方法 之前
    5、有嵌套滑动效果时 注意处理滑动冲突 
    