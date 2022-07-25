### 前言
因为ViewGroup继承View，View中的measure方法由于是final型不能重载，onMeasure方法也没有重写。ViewGroup的measure情况分为两种，一是measure自己，二是measure子View。但是ViewGroup并没有定义具体measure自己的过程，其其测量过程onMeasure需要有其子类实现，因为不同的子类有其不同的布局特性，比如LinearLayout，Relativelayout以及自定义的ViewGroup子类。

### 源码分析
##### 1. ViewGroup的Measure过程
ViewGroup的measure入口依然是从View的measure()方法开始。
在View的measure()方法中调用onMeasure()方法，onMeasure方法需要重写
```
@Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  

        //用于存放测量后的View宽/高的值
        int widthMeasure ;
        int heightMeasure ;

        // 遍历所有子View
        measureChildren(widthMeasureSpec, heightMeasureSpec)；

        // 合并所有子View的尺寸大小，最终得到ViewGroup父视图的测量值,需要自己实现
         measureMerge()

        // 存储测量后View宽/高的值与单一View过程类似
        setMeasuredDimension(widthMeasure,  heightMeasure);  
  }

   //查看ViewGroup.java$measureChildren方法源码
   protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        final int size = mChildrenCount;
        final View[] children = mChildren;
        for (int i = 0; i < size; ++i) {
            final View child = children[i];
            if ((child.mViewFlags & VISIBILITY_MASK) != GONE) {
                //遍历子View 测量其宽/高值
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

 //继续查看ViewGroup.java$measureChild方法源码
  protected void measureChild(View child, int parentWidthMeasureSpec,
            int parentHeightMeasureSpec) {

        //获取子View自身的layoutParams参数
        final LayoutParams lp = child.getLayoutParams();

        //传入View的measure方法的宽/高的测量规格为
        //父视图的MeasureSpec值 + View自身的layoutParams参数
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                mPaddingLeft + mPaddingRight, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                mPaddingTop + mPaddingBottom, lp.height);
        //这里调用视图View自身的measure方法
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

//查看getChildMeasureSpec方法源码
public static int getChildMeasureSpec(int spec, int padding, int childDimension) {

        //父视图的测量模式
        int specMode = MeasureSpec.getMode(spec);
        //父视图的测量大小
        int specSize = MeasureSpec.getSize(spec);

        int size = Math.max(0, specSize - padding);

        int resultSize = 0;
        int resultMode = 0;

        switch (specMode) {
        // 当父视图的测量模式为EXACTLY时，父视图赋值给子视图确切的值
        case MeasureSpec.EXACTLY:
            //当子View的layoutParmas>0，即有确切值
            if (childDimension >= 0) {
                //子View大小为子View自身的值，模式为EXACTLY
                resultSize = childDimension;
                resultMode = MeasureSpec.EXACTLY;

            // 当子view的LayoutParams为MATCH_PARENT时
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                //子view大小为父视图的大小，模式为EXACTLY  
                resultSize = size;
                resultMode = MeasureSpec.EXACTLY;

            // 当子view的LayoutParams为WRAP_CONTENT时
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                //子view决定自己的大小，但最大不能超过父view，模式为AT_MOST  
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            }
            break;

        // 当父视图的模式为AT_MOST时，父视图会给子view一个最大的值。
        case MeasureSpec.AT_MOST:
            if (childDimension >= 0) {
                // Child wants a specific size... so be it
                resultSize = childDimension;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size, but our size is not fixed.
                // Constrain child to not be bigger than us.
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            }
            break;

        // 当父视图的模式为UNSPECIFIED时，父视图不对子View进行限制
        case MeasureSpec.UNSPECIFIED:
            if (childDimension >= 0) {
                // Child wants a specific size... let him have it
                resultSize = childDimension;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size... find out how big it should
                // be
                resultSize = View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                resultMode = MeasureSpec.UNSPECIFIED;
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size.... find out how
                // big it should be
                resultSize = View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                resultMode = MeasureSpec.UNSPECIFIED;
            }
            break;
        }
        //返回子View的宽/高的MeasureSpec值
        return MeasureSpec.makeMeasureSpec(resultSize, resultMode);
    }
```
ViewGroup的绘制流程图
![ViewGroup的measure过程.png](https://upload-images.jianshu.io/upload_images/4954278-1ecc15004bd42d7d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 2. ViewGroup的Layout过程
