### 前言
自定义View的绘制流程：onMeasure() -> onLayout() ->onDraw(),在分析源码之前需要了解一下MeasureSpec类。
> MeasureSpec代表一个32位int值，高2位代表SpecMode，低30位代表SpecSize，SpecMode是指测量模式，而specSize是指在某种测量模式下的规格大小。

其作用是测量一个视图View的宽/高。每个MeasureSpec代表一组宽高的测量规格。
 查看MeasureSpec类源码
```
 public static class MeasureSpec {

        //进位大小为2的30次方
        private static final int MODE_SHIFT = 30;
        private static final int MODE_MASK  = 0x3 << MODE_SHIFT;

        //测量模式specMode的三种类型：UNSPECIFIED ，EXACTLY，AT_MOST          
        //UNSPECIFIED的模式 0向左 进位30
        public static final int UNSPECIFIED = 0 << MODE_SHIFT;
        public static final int EXACTLY     = 1 << MODE_SHIFT;
        public static final int AT_MOST     = 2 << MODE_SHIFT;

        //根据提供的size和mode创建测量规格
        public static int makeMeasureSpec(@IntRange(from = 0, to = (1 << MeasureSpec.MODE_SHIFT) - 1) int size,
                                          @MeasureSpecMode int mode) {
            if (sUseBrokenMakeMeasureSpec) {
                return size + mode;
            } else {
                return (size & ~MODE_MASK) | (mode & MODE_MASK);
            }
        }

        //通过MeasureSpec获取测量模式mode
        @MeasureSpecMode
        public static int getMode(int measureSpec) {
            //noinspection ResourceType
            return (measureSpec & MODE_MASK);
        }
        //通过MeasureSpec获取测量大小size
        public static int getSize(int measureSpec) {
            return (measureSpec & ~MODE_MASK);
        }
        //根据size或mode调整测量规格
        static int adjust(int measureSpec, int delta) {
            final int mode = getMode(measureSpec);
            int size = getSize(measureSpec);
            if (mode == UNSPECIFIED) {
                // No need to adjust size for UNSPECIFIED mode.
                return makeMeasureSpec(size, UNSPECIFIED);
            }
            size += delta;
            if (size < 0) {
                Log.e(VIEW_LOG_TAG, "MeasureSpec.adjust: new size would be negative! (" + size +
                        ") spec: " + toString(measureSpec) + " delta: " + delta);
                size = 0;
            }
            return makeMeasureSpec(size, mode);
        }

    }
```
使用MeasureSpec的目的是减少对象内存分配

### 源码分析
##### 1. 单一View的onMeasure()过程

> measure： 计算View的宽/高

measure测量过程的入口为measure()
查看View.java$measure方法核心源码
```
  public final void measure(int widthMeasureSpec, int heightMeasureSpec) {

            ......//代码省略

            int cacheIndex = forceLayout ? -1 : mMeasureCache.indexOfKey(key);
            if (cacheIndex < 0 || sIgnoreMeasureCache) {
                //根据View的宽/高测量规格计算View的宽/高值 
                onMeasure(widthMeasureSpec, heightMeasureSpec);
                mPrivateFlags3 &= ~PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT;
            } 

            ......//代码省略

    }
```
该方法定义为final类，即子类中不能重写该方法
查看View.java$onMeasure方法源码
```
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

   //继续查看setMeasuredDimension方法源码
   protected final void setMeasuredDimension(int measuredWidth, int measuredHeight) {
        //View是否使用了视觉边界布局
        boolean optical = isLayoutModeOptical(this);
        //mParent为ViewParent对象，为该View的父类
        if (optical != isLayoutModeOptical(mParent)) {
            Insets insets = getOpticalInsets();
            int opticalWidth  = insets.left + insets.right;
            int opticalHeight = insets.top  + insets.bottom;

            measuredWidth  += optical ? opticalWidth  : -opticalWidth;
            measuredHeight += optical ? opticalHeight : -opticalHeight;
        }
        setMeasuredDimensionRaw(measuredWidth, measuredHeight);
    }

   //继续查看
   private void setMeasuredDimensionRaw(int measuredWidth, int measuredHeight) {
        // 将测量后View的宽 / 高值进行传递
        mMeasuredWidth = measuredWidth;
        mMeasuredHeight = measuredHeight;

        mPrivateFlags |= PFLAG_MEASURED_DIMENSION_SET;
    }

    //查看getDefaultSize方法源码
    public static int getDefaultSize(int size, int measureSpec) {
        //设置默认大小
        int result = size;
      // 获取View的宽/高测量规格模式mode，测量大小size
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
        //模式为UNSPECIFIED时，使用默认大小
        case MeasureSpec.UNSPECIFIED:
            result = size;
            break;
        //模式为AT_MOST，EXACTLY时，使用View测量后的宽/高值
        case MeasureSpec.AT_MOST:
        case MeasureSpec.EXACTLY:
            result = specSize;
            break;
        }
        return result;
    }

   //默认大小size = getSuggestedMinimumWidth()/getSuggestedMinimumHeight()
   //分析getSuggestedMinimumHeight()
   protected int getSuggestedMinimumHeight() {
        //若View无设置背景则View的高度为mMinHeight 
        //mMinHeight为android:minHeight属性值，若未设置则为0
        //若设置了View的背景，把背景图高度与mMinHeight相比取最大值，
        return (mBackground == null) ? mMinHeight : max(mMinHeight, mBackground.getMinimumHeight());

    }

  //继续查看Drawable.java$getMinimumHeight方法源码
  public int getMinimumHeight() {
        //获取背景图Drawable的原始高度
        final int intrinsicHeight = getIntrinsicHeight();
        return intrinsicHeight > 0 ? intrinsicHeight : 0;
    }
```
以上是单一View的onMeasure测量过程，下面给出其流程图。
![单一View的measure过程.jpg](https://upload-images.jianshu.io/upload_images/4954278-dbf9029fc17f394d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 2. 单一View的onLayout() 过程

> layout过程： 计算本身View的的位置（left、right、top和bottom）

计算View的位置入口为Layout方法 查看View.java$layout方法核心源码
```
 public void layout(int l, int t, int r, int b) {

        ......//代码省略
        
        //当前视图View的四个顶点
        int oldL = mLeft;
        int oldT = mTop;
        int oldB = mBottom;
        int oldR = mRight;

        //判断视图View大小和位置是否发生了变化
        //islayoutModeOptical判断是否使用View的视图边界布局
        //若使用则调用setOpticalFrame方法根据传入的值设置View的四个顶点位置
        boolean changed = isLayoutModeOptical(mParent) ?
                setOpticalFrame(l, t, r, b) : setFrame(l, t, r, b);

        if (changed || (mPrivateFlags & PFLAG_LAYOUT_REQUIRED) == PFLAG_LAYOUT_REQUIRED) {
            //若View大小和位置发生改变则调用该方法
            //在单一View中因无子View该方法为空方法
            onLayout(changed, l, t, r, b);
          
            ......//代码省略
        
         
    }

 //继续分析setFrame()方法核心源码
 protected boolean setFrame(int left, int top, int right, int bottom) {
        boolean changed = false;

        ......//代码省略
        
        //判断View位置是否发生改边
        if (mLeft != left || mRight != right || mTop != top || mBottom != bottom) {
            changed = true;

            ......//代码省略
        
            //设置View的四个顶点位置
            mLeft = left;
            mTop = top;
            mRight = right;
            mBottom = bottom;
            mRenderNode.setLeftTopRightBottom(mLeft, mTop, mRight, mBottom);

            ......//代码省略
        
        }
        return changed;
    }

  //查看setOpticalFrame方法源码
  private boolean setOpticalFrame(int left, int top, int right, int bottom) {
        Insets parentInsets = mParent instanceof View ?
                ((View) mParent).getOpticalInsets() : Insets.NONE;
        Insets childInsets = getOpticalInsets();
        //内部调用的是setFrame方法
        return setFrame(
                left   + parentInsets.left - childInsets.left,
                top    + parentInsets.top  - childInsets.top,
                right  + parentInsets.left + childInsets.right,
                bottom + parentInsets.top  + childInsets.bottom);
    }
//查看onLayout方法源码
protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    //该方法为空方法，单一View中在layout()中已经对自身View进行了位置计算
    }

```
layout的整个计算流程
![单一View的layout过程.png](https://upload-images.jianshu.io/upload_images/4954278-668de0d54ce969a7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 3. 单一View的onDraw过程
> draw过程： 仅绘制视图View本身

View的绘制入口为draw()方法 查看View.java$draw方法核心源码
```
  public void draw(Canvas canvas) {
      
        ......//代码省略

        // 步骤1：绘制View自身背景
        int saveCount;
        if (!dirtyOpaque) {
            drawBackground(canvas);
        }

        ......//代码省略

        // 步骤2：保存画布图层
        int solidColor = getSolidColor();
        if (solidColor == 0) {
            final int flags = Canvas.HAS_ALPHA_LAYER_SAVE_FLAG;
            if (drawTop) {
                canvas.saveLayer(left, top, right, top + length, null, flags);
            }
            if (drawBottom) {
                canvas.saveLayer(left, bottom - length, right, bottom, null, flags);
            }
            if (drawLeft) {
                canvas.saveLayer(left, top, left + length, bottom, null, flags);
            }
            if (drawRight) {
                canvas.saveLayer(right - length, top, right, bottom, null, flags);
            }
        } else {
            scrollabilityCache.setFadeColor(solidColor);
        }

        // 步骤3：绘制View内容
        if (!dirtyOpaque) onDraw(canvas);

        // 步骤4：绘制子View视图
        dispatchDraw(canvas);

        // 步骤5：绘制淡入淡出效果并还原图层
        final Paint p = scrollabilityCache.paint;
        final Matrix matrix = scrollabilityCache.matrix;
        final Shader fade = scrollabilityCache.shader;

        if (drawTop) {
            matrix.setScale(1, fadeHeight * topFadeStrength);
            matrix.postTranslate(left, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            canvas.drawRect(left, top, right, top + length, p);
        }

        if (drawBottom) {
            matrix.setScale(1, fadeHeight * bottomFadeStrength);
            matrix.postRotate(180);
            matrix.postTranslate(left, bottom);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            canvas.drawRect(left, bottom - length, right, bottom, p);
        }

        if (drawLeft) {
            matrix.setScale(1, fadeHeight * leftFadeStrength);
            matrix.postRotate(-90);
            matrix.postTranslate(left, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            canvas.drawRect(left, top, left + length, bottom, p);
        }

        if (drawRight) {
            matrix.setScale(1, fadeHeight * rightFadeStrength);
            matrix.postRotate(90);
            matrix.postTranslate(right, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            canvas.drawRect(right - length, top, right, bottom, p);
        }

        canvas.restoreToCount(saveCount);

        // Overlay is part of the content and draws beneath Foreground
        if (mOverlay != null && !mOverlay.isEmpty()) {
            mOverlay.getOverlayView().dispatchDraw(canvas);
        }

        // 步骤6：绘制装饰（比如前景色，滑动条）
        onDrawForeground(canvas);
    }

 //查看步骤1 drawBackground方法源码
 private void drawBackground(Canvas canvas) {
        //获取背景图
        final Drawable background = mBackground;
        if (background == null) {
            return;
        }
        //根据layout过程中得到的View的位置参数，来设置背景的边界
        setBackgroundBounds();

        ......// 代码省略
        
        
        final int scrollX = mScrollX;
        final int scrollY = mScrollY;
        if ((scrollX | scrollY) == 0) {
            //调用draw方法绘制背景
            background.draw(canvas);
        } else {
            //若scrollX或scrollY不为0 则对canvas的坐标进行旋转
            canvas.translate(scrollX, scrollY);
            background.draw(canvas);
            canvas.translate(-scrollX, -scrollY);
        }
    }

 //查看setBackgroundBounds方法
 void setBackgroundBounds() {
        if (mBackgroundSizeChanged && mBackground != null) {
            //根据layout计算获取的View的顶点位置，对背景图设置边界
            mBackground.setBounds(0, 0, mRight - mLeft, mBottom - mTop);
            mBackgroundSizeChanged = false;
            rebuildOutline();
        }
    }

 //查看步骤3 onDraw方法源码
 protected void onDraw(Canvas canvas) {

    //该方法为空实现需要复写该方法 绘制View自身内容

    }

 //查看步骤4 dispatchDraw方法源码
 protected void dispatchDraw(Canvas canvas) {

    //由于单一View无子View所以该方法为空实现
    //在ViewGroup中该方法为绘制子View

    }

 //查看步骤6 onDrawForeground方法源码
 public void onDrawForeground(Canvas canvas) {
        //绘制滑动bar
        onDrawScrollIndicators(canvas);
        onDrawScrollBars(canvas);

        final Drawable foreground = mForegroundInfo != null ? mForegroundInfo.mDrawable : null;
        if (foreground != null) {

            ......//代码省略

            //绘制前景色
            foreground.draw(canvas);
        }
    }

```
单一View的draw绘制过程
![单一View的draw过程.png](https://upload-images.jianshu.io/upload_images/4954278-b7d54ae45cd5bf06.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

考虑到分析ViewGroup源码会导致篇幅较长，将View和ViewGroup绘制过程分开分析。
下一篇为[ViewGroup绘制过程源码分析]()






