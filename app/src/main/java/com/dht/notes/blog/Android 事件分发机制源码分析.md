### 前言
首先要了解事件分发机制的传递流程是在点击事件发生后，事件先传递到Activity，然后传递到ViewGroup，最终传递到View。
主要涉及的三个方法分别为：
> dispatchTouchEvent()：分发传递点击事件
> onInterceptTouchEvent() ：判断是否拦截了点击事件
> onTouchEvent() : 处理点击事件  
 
源码分析完后做出事件分发的流程图
![事件分发流程.png](https://upload-images.jianshu.io/upload_images/4954278-0642a79df745cf48.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### 源码分析
#####  1. Activity的事件传递机制
```
查看自定义Activity中的dispatchTouchEvent方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //系统默认点击空白处 super.dispatchTouchEvent(ev)=false
        //系统默认点击按钮处 super.dispatchTouchEvent(ev)=true
        //必须执行super.dispatchTouchEvent(ev) 方法 否则即使返回true或者false 都不会往下传递也不会执行onTouchEvent()
        return super.dispatchTouchEvent(ev);
    }
```
查看Activity.java$dispatchTouchEvent方法源码
```
   public boolean dispatchTouchEvent(MotionEvent ev) {
        //ev.getAction()获取屏幕的点击事件类型，ACTION_DOWN为按下事件
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 该方法为空，作用为实现屏幕保护功能
            onUserInteraction();
        }
        //getWindow()方法获取的是一个Window对象
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        //返回处理点击事件的结果
        return onTouchEvent(ev);
    }
```
因为Window是一个抽象类其具体实现类为PhoneWindow。
查看PhoneWindow.java$superDispatchTouchEvent方法源码
```
  @Override
    public boolean superDispatchTouchEvent(MotionEvent event) {
        return mDecor.superDispatchTouchEvent(event);
    }
```
mDecor是DecorView的一个对象，而DecorView是Window的顶层View也是我们看到的整个显示界面。DecorView继承FrameLayout类，而FrameLayout是ViewGroup子类。
查看DecorView.java$superDispatchTouchEvent方法源码
```
   public boolean superDispatchTouchEvent(MotionEvent event) {
        //上面说到DecorView是ViewGroup的间接子类，
        //这里调用的便是ViewGroup的dispatchTouchEvent方法，后面会分析ViewGroup的该方法
        return super.dispatchTouchEvent(event);
    }

```
查看自定义Activity中onTouchEvent方法
```
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //系统默认super.onTouchEvent(event) = false
        //无论true或者false 都会执行该方法中的代码
        return super.onTouchEvent(event);
    }
```
查看Activity.java$onTouchEvent方法源码
```
   public boolean onTouchEvent(MotionEvent event) {
        //处理发生在Window边界外的点击事件
        if (mWindow.shouldCloseOnTouch(this, event)) {
            finish();
            return true;
        }
  
        return false;
    }
//查看Window.java$shouldCloseTouch方法源码
public boolean shouldCloseOnTouch(Context context, MotionEvent event) {
        //判断点击事件以及event的x，y轴坐标是否在边界外
        if (mCloseOnTouchOutside && event.getAction() == MotionEvent.ACTION_DOWN
                && isOutOfBounds(context, event) && peekDecorView() != null) {
            //若事件在边界外则消费该事件返回true
            return true;
        }
        return false;
    }
```
#####  2. ViewGroup的事件分发机制
查看自定义ViewGroup的dispatchTouchEvent方法
```
   @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //系统默认super.dispatchTouchEvent(ev) = true
        //返回true继续往下分发 若返回false 停止事件分发
        //且onInterceptTouchEvent ，onTouchEvent将不会执行
        return super.dispatchTouchEvent(ev);
    }
```
查看ViewGroup.java$dispatchTouchEvent方法源码
```
 @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 对输入框的统一验证,不为null 则对输入框的点击事件进行处理
        if (mInputEventConsistencyVerifier != null) {
            mInputEventConsistencyVerifier.onTouchEvent(ev, 1);
        }

        // If the event targets the accessibility focused view and this is it, start
        // normal event dispatch. Maybe a descendant is what will handle the click.
        if (ev.isTargetAccessibilityFocus() && isAccessibilityFocusedViewOrHost()) {
            ev.setTargetAccessibilityFocus(false);
        }

        boolean handled = false;
        //以应用安全策略过滤触摸事件，返回true即继续分发事件
        if (onFilterTouchEventForSecurity(ev)) {
            //获取事件点击类型
            final int action = ev.getAction();
            final int actionMasked = action & MotionEvent.ACTION_MASK;

            //处理按下事件
            if (actionMasked == MotionEvent.ACTION_DOWN) {

                //取消并清除所有与触摸目标之间的联系
                cancelAndClearTouchTargets(ev);

                //重置所有触摸状态准备新的循环
                resetTouchState();
            }

            final boolean intercepted;

            // 对事件是否拦截进行处理
            if (actionMasked == MotionEvent.ACTION_DOWN
                    || mFirstTouchTarget != null) {
                //是否允许拦截
                final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
                //允许拦截
                if (!disallowIntercept) {
                    // 内部调用onInterceptTouchEvent事件拦截方法
                    intercepted = onInterceptTouchEvent(ev);
                    //恢复action防止事件发生变化
                    ev.setAction(action);
                } else {
                    //不允许拦截 intercept 设置为false
                    intercepted = false;
                }
            } else {
                //没有触摸目标 且ation没有初始化down事件
                //则ViewGroup将会继续拦截分发事件
                intercepted = true;
            }

            ......//代码省略

            TouchTarget newTouchTarget = null;
            boolean alreadyDispatchedToNewTouchTarget = false;
            // 如果没有取消触摸事件以及拦截点击事件，则继续分发
            if (!canceled && !intercepted) {

                 ......//代码省略

                if (actionMasked == MotionEvent.ACTION_DOWN
                        || (split && actionMasked == MotionEvent.ACTION_POINTER_DOWN)
                        || actionMasked == MotionEvent.ACTION_HOVER_MOVE) {
                    final int actionIndex = ev.getActionIndex(); // always 0 for down
                    final int idBitsToAssign = split ? 1 << ev.getPointerId(actionIndex)
                            : TouchTarget.ALL_POINTER_IDS;

                    //移除早期的触摸目标id防止它们不能同步
                    removePointersFromTouchTargets(idBitsToAssign);

                    //mChildrenCount为mChildren数组中有效的子元素数量
                    //mChildren为ViewGroup中的子视图View
                    final int childrenCount = mChildrenCount;
                    if (newTouchTarget == null && childrenCount != 0) {
                        final float x = ev.getX(actionIndex);
                        final float y = ev.getY(actionIndex);
                        // 查找收到触摸事件的View
                        // 返回触摸被分发的自定义排列视图
 
                         ......//代码省略

                        final View[] children = mChildren;
                        //遍历当前ViewGroup中的有效视图
                        for (int i = childrenCount - 1; i >= 0; i--) {
                            final int childIndex = getAndVerifyPreorderedIndex(
                                    childrenCount, i, customOrder);
                            final View child = getAndVerifyPreorderedView(
                                    preorderedList, children, childIndex);

                              ......//代码省略

                             //该方法处理子View的dispatchTouchEvent
                             if (dispatchTransformedTouchEvent(ev, false, child, idBitsToAssign)) {}
                        }
                    }

               ......//代码省略

        return handled;
    }

//继续查看dispatchTransformedTouchEvent方法核心源码
private boolean dispatchTransformedTouchEvent(MotionEvent event, boolean cancel,
            View child, int desiredPointerIdBits) {
        final boolean handled;

            ......//代码省略

            //调用View的dispatchTouchEvent（后面会分析View该方法）
            if (child == null) {
                handled = super.dispatchTouchEvent(event);
            } else {
                handled = child.dispatchTouchEvent(event);
            }
   
            ......//代码省略

        return handled;
    }
```

接着查看自定义ViewGroup的onInterceptTouchEvent方法
```
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //系统默认super.onInterceptTouchEvent(ev) = false
        //返回true则对事件进行拦截，且onTouchEvent事件继续执行
        return super.onInterceptTouchEvent(ev);
    }
//继续分析ViewGroup.java$onInterceptTouchEvent方法

//是否拦截事件
public boolean onInterceptTouchEvent(MotionEvent ev) {
        //返回true 即拦截事件，事件停止往下传递需要复写onInterceptTouchEvent()方法
        //系统默认返回false，不拦截事件
        return false;
    }
```
查看自定义ViewGroup的onTouchEvent方法
```
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //系统默认onTouchEvent() = true
        //返回false 只影响自身的onClickListener事件
        return super.onTouchEvent(event);
    }
```
因为ViewGroup是继承View，这里调用onTouchEvent方法也就是调用View类中的onTouchEvent方法（后面会分析该方法源码）。
##### 3. View的事件分发机制
首先查看自定义View的dispatchTouchEvent方法
```
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //系统默认super.dispatchTouchEvent(event) =true
        //若返回false 将不再分发事件也不会执行onTouchEvent事件
        return super.dispatchTouchEvent(event);
    }
 //继续查看View.java$dispatchTouchEvent方法核心源码
 public boolean dispatchTouchEvent(MotionEvent event) {
        
        ......//代码省略

        boolean result = false;

        ......//代码省略

        if (onFilterTouchEventForSecurity(event)) {
            //mViewFlags为视图标识记录视图状态
            //(mViewFlags & ENABLED_MASK) == ENABLED 判断
            //该点击控件是否是enabled 类型 默认该条件为true
            if ((mViewFlags & ENABLED_MASK) == ENABLED && handleScrollBarDragging(event)) {
                result = true;
            }
            //mListenerInfo 各类监听器信息
            ListenerInfo li = mListenerInfo;
            //条件1：li != null
            //条件2：li.mOnTouchListener != null
            //(mViewFlags & ENABLED_MASK) == ENABLED 默认为true
            //条件3：li.mOnTouchListener.onTouch(this, event)设置onTouch事件需要重写onTouch方法
            if (li != null && li.mOnTouchListener != null
                    && (mViewFlags & ENABLED_MASK) == ENABLED
                    && li.mOnTouchListener.onTouch(this, event)) {
                result = true;
            }
            //若onTouch方法返回false则执行onTouchEvent方法 
            if (!result && onTouchEvent(event)) {
                result = true;
            }
        }

        ......//代码省略

        return result;
    }

```
先分析条件2：li.mOnTouchListener != null查找mOnTouchListener的赋值代码
```
  public void setOnTouchListener(OnTouchListener l) {
        //若注册onTouch事件则mOnTouchListener 永不为null
        getListenerInfo().mOnTouchListener = l;
    }
//继续分析getListenerInfo方法
ListenerInfo getListenerInfo() {
        if (mListenerInfo != null) {
            return mListenerInfo;
        }
        //该方法为mListenerInfo赋值也就是说条件1：li != null一直为true
        mListenerInfo = new ListenerInfo();
        return mListenerInfo;
    }
``` 
查看自定义View类中的onTouchEvent方法
```
   @Override
    public boolean onTouchEvent(MotionEvent event) {
        //系统默认super.onTouchEvent() = true
        //返回false 则不再执行自身点击事件
        return super.onTouchEvent(event);
    }
 //查看View.java$onTouchEvent方法核心源码
 public boolean onTouchEvent(MotionEvent event) {

        ......//代码省略

        //若view可点击则进入switch语句判断该事件类型并执行对应代码
        if (((viewFlags & CLICKABLE) == CLICKABLE ||
                (viewFlags & LONG_CLICKABLE) == LONG_CLICKABLE) ||
                (viewFlags & CONTEXT_CLICKABLE) == CONTEXT_CLICKABLE) {
            switch (action) {
                //松开按下的view
                case MotionEvent.ACTION_UP:
                    boolean prepressed = (mPrivateFlags & PFLAG_PREPRESSED) != 0;
                 
                  ......//省略各种判断代码执行performClick方法

                  performClick();

                //按下View
                case MotionEvent.ACTION_DOWN:
                    mHasPerformedLongPress = false;

                    if (performButtonActionOnTouchDown(event)) {
                        break;
                    }

                    // Walk up the hierarchy to determine if we're inside a scrolling container.
                    boolean isInScrollingContainer = isInScrollingContainer();

                    // For views inside a scrolling container, delay the pressed feedback for
                    // a short period in case this is a scroll.
                    if (isInScrollingContainer) {
                        mPrivateFlags |= PFLAG_PREPRESSED;
                        if (mPendingCheckForTap == null) {
                            mPendingCheckForTap = new CheckForTap();
                        }
                        mPendingCheckForTap.x = event.getX();
                        mPendingCheckForTap.y = event.getY();
                        postDelayed(mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                    } else {
                        // Not inside a scrolling container, so show the feedback right away
                        setPressed(true, x, y);
                        checkForLongClick(0, x, y);
                    }
                    break;
                // 取消触摸事件
                case MotionEvent.ACTION_CANCEL:
                    setPressed(false);
                    removeTapCallback();
                    removeLongPressCallback();
                    mInContextButtonPress = false;
                    mHasPerformedLongPress = false;
                    mIgnoreNextUpEvent = false;
                    break;
                //滑动view
                case MotionEvent.ACTION_MOVE:
                    drawableHotspotChanged(x, y);

                    // Be lenient about moving outside of buttons
                    if (!pointInView(x, y, mTouchSlop)) {
                        // Outside button
                        removeTapCallback();
                        if ((mPrivateFlags & PFLAG_PRESSED) != 0) {
                            // Remove any future long press/tap checks
                            removeLongPressCallback();

                            setPressed(false);
                        }
                    }
                    break;
            }
            //若view可点击则返回true
            return true;
        }
        //若view不可点击则返回false
        return false;
    }
```
查看View.java$performClick方法源码
```
   public boolean performClick() {
        final boolean result;
        final ListenerInfo li = mListenerInfo;
        if (li != null && li.mOnClickListener != null) {
            playSoundEffect(SoundEffectConstants.CLICK);
            //调用onClick方法
            li.mOnClickListener.onClick(this);
            result = true;
        } else {
            result = false;
        }

        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
        return result;
    }
```
分析完View的dispatchTouchEvent方法可以发现该方法中先执行onTouch方法，若onTouch方法返回值为false，则执行OnTouchEvent方法，然后执行该方法中performClick方法，最后再调用onclick方法。
 ![view事件传递流程图.png](https://upload-images.jianshu.io/upload_images/4954278-97afeebb29761bb0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
[图片摘取地址](https://www.jianshu.com/p/38015afcdb58)








