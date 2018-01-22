### 参考链接：https://www.jianshu.com/p/2412d00a0ce4
### 属性动画出现的原因以及背景
    属性动画（property animation） 是在android3.0（API 11）后才提供的一种全新的动画模式
    是为了解决补间动画的缺陷
    Android 系统一开始提供两种实现动画的方式
    追帧动画（Frame Animation）
    补间动画（Tweened Animation）
### 逐帧动画和补间动画存在一定的缺陷
    作用对象局限：view
        即补间动画只能够作用在视图view上，即只可以对一个button 或者TextView或者LinearLayout 进行动画操作
        无法对非view的对象进行动画操作
    没有改变view的属性，只是改变了view的视觉效果
        将屏幕左上角的按钮通过补间动画 移动到屏幕的右下角，实际上按钮还停留在屏幕的左上角
    动画效果单一
        补间动画只能实现平移、旋转、缩放、透明度这些简单的动画需求
### 属性动画重要类
    valueAnimation类
    ObjectAnimation类
### ValueAnimation类
    实现动画的原理：通过不断控制值得变化，在不断手动赋给对象的属性，从而实现动画效果
    3个重要方法
    ValueAnimation.ofInt(int values) 作用：将初始值以整形数值的形式过渡到结束值
        插值器：interpolator 作用 设置值的变化模式：比如 匀速，加速等 
        估值器：IntEvaluator 作用 设置值的具体数值（系统默认）
    ValueAnimation.ofFloat(float values) （同上）
    ValueAnimation.ofObject(object values)
        估值器：IntEvaluator 作用 设置值的具体变化数值（自定义）
### ObjectAnimation类
    实现原理：直接对对象的属性值进行改变操作，从而实现动画效果
        直接改变view的alpha属性从而实现透明度的动画效果
        继承自ViewAnimation类，即底层的动画实现机制是基于ValueAnimation类
    本质原理：通过不断控制值的变化，在不断自动赋给对象的属性，从而实现动画效果
### ValueAnimation类与ObjectAnimation类的区别
    ValueAnimation类是先改变值，然后手动赋值给对象的属性从而实现动画；是间接对对象属性进行操作；
    ObjectAnimation类是先改变值，然后自动赋值给对象的属性从而实现动画；是直接对对象属性进行操作；
    

    