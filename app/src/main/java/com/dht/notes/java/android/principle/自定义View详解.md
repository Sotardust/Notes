### Measure过程

    作用：测量View的宽/高
    
    MeasureSpec类

    onMeasure（）
    
    组成：
        测量规格（MeasureSpec） = 测量模式（mode）+测量大小（size）
        
        测量模式（mode）类型有三种：
                模式                          应用场景                                      备注
            UNSPECIFIED unspecified         系统内部（如ListView，SrollView）               一般自定义view中用不到（UNSPECIFIED模式适用于系统内部多次measure情况，很少用到）
            
            EXACTLY exactly            强制性是子视图的大小扩展至父视图大小相等                本质 = 利用父View的剩余空间，而父View剩余空间是确定的，故 该尺寸 = 确切的尺寸
                                       （如 match_parent） 具体数值（如100dp或100px）        View的最终大小即Spec指定的值 ，父控件可以通过MeasureSpec.getSize()直接得到子控件的尺寸
            AT_MOST at_most            自适应大小（wrap_content）                           该模式 = 自定义视图需实现测量逻辑的情况
            
![ViewGroup的measure过程](https://github.com/Sotardust/Notes/blob/master/app/assests/view/ViewGroup%E7%9A%84measure%E8%BF%87%E7%A8%8B.png)

### Layout过程

    作用:计算视图（view）的位置
        即：计算View的四个顶点位置：Left、Top、Right、Bottom
        
    单一View的layout过程 ：
    
        开始计算位置 --> layout() --> onLayout() --> 完成计算。
        
    ViewGroup的layout过程 ： 
    
![ViewGroup的Layout过程](https://github.com/Sotardust/Notes/blob/master/app/assests/view/ViewGroup%E7%9A%84Layout%E8%BF%87%E7%A8%8B.png)       
       
### getWidth() （ getHeight()）与 getMeasuredWidth() （getMeasuredHeight()）获取的宽 （高）有什么区别？
    
    getWidth()/getheight():获得View的最终的宽/高
    
    getMeasureWidth()/getMeasureHeight()：获得View的测量的宽/高
    
![二者的区别](https://github.com/Sotardust/Notes/blob/master/app/assests/view/%E4%BA%8C%E8%80%85%E7%9A%84%E5%8C%BA%E5%88%AB.png)

### Draw过程
    作用：绘制View视图
    
    单一View的Draw过程
    
    开始绘制 --> draw() --> drawBackground() --> onDraw() --> dispatchDraw() --> onDrawScrollBars() --> 绘制结束
             绘制自身VIew  绘制自身View的背景  绘制自身View的内容  空实现（因为没有子View） 绘制装饰


![ViewGroup的Draw过程](https://github.com/Sotardust/Notes/blob/master/app/assests/view/ViewGroup%E7%9A%84%E7%BB%98%E5%88%B6%E6%B5%81%E7%A8%8B.png)





























