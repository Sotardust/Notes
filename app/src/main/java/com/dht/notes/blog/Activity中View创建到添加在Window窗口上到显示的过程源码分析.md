### 前言
分析`WindowManager`之前先了解一下 `Window`。
Window也就是窗口，它是一个抽象类，其具体实现类是PhoneWindow。
>Window有三种类型：应用Window，子Window以及系统Window

|Window|应用|层级|
|-|-|-|
|应用Window|Activity|1~99|
|子Window|Dialog|1000~1999|
|系统Window|系统状态栏、Toast|2000~2999|
这些层级对应WindowManager.LayoutParams的type参数，
#### Window和WindowManger
WindowManager顾名思义指的是Window的管理者，Window展示的内容是由View承载的也就是DecorView，创建一个Window需要通过WindowManager来协助完成。而WindowManager的具体实现类是WindowManagerImpl。
WindowManager是外界访问Window的入口，它是一个接口并继承ViewManager接口
```
public interface ViewManager
{
    //添加View
    public void addView(View view, ViewGroup.LayoutParams params);
    //更新View
    public void updateViewLayout(View view, ViewGroup.LayoutParams params);
    //删除View
    public void removeView(View view);
}

//查看WindowManger的具体实现类WindowManagerImpl源码
@Override
public void addView(@NonNull View view, @NonNull ViewGroup.LayoutParams params) {
        applyDefaultToken(params);
        //mParentWindow为一个Window对象
        mGlobal.addView(view, params, mContext.getDisplay(), mParentWindow);
}

@Override
public void updateViewLayout(@NonNull View view, @NonNull ViewGroup.LayoutParams params) {
        applyDefaultToken(params);
        mGlobal.updateViewLayout(view, params);
}

@Override
public void removeView(View view) {
        mGlobal.removeView(view, false);
}
```
查看WindowManagerImpl源码发现addView方法实际是调用mGlobal的addView方法
而mGlobal是一个WindowManagerGlobal单例对象，也就是说WindowManagerGlobal实现了WindowManagerImpl的功能
查看WindowMangerGlobal.java$addView方法核心源码
```
 public void addView(View view, ViewGroup.LayoutParams params,
            Display display, Window parentWindow) {

        ......//代码省略
        //参数类型转换
        final WindowManager.LayoutParams wparams = (WindowManager.LayoutParams) params;
       
        ......//代码省略
        
        // ViewRootImpl是顶级View的管理者是final类，其实现ViewParent接口，ViewParent是处理视图 与父级交互的API
        ViewRootImpl root;
        View panelParentView = null;
        synchronized (mLock) {
            ......//代码省略

            root = new ViewRootImpl(view.getContext(), display);

            view.setLayoutParams(wparams);

            // private final ArrayList<View> mViews = new ArrayList<View>();
            // private final ArrayList<ViewRootImpl> mRoots = new ArrayList<ViewRootImpl>();
            // private final ArrayList<WindowManager.LayoutParams> mParams = new ArrayList<WindowManager.LayoutParams>();
            mViews.add(view);
            mRoots.add(root);
            mParams.add(wparams);
        }

        try {
            //添加视图View
            root.setView(view, wparams, panelParentView);
        } catch (RuntimeException e) {
            //运行异常则移除View
            synchronized (mLock) {
                final int index = findViewLocked(view, false);
                if (index >= 0) {
                    removeViewLocked(index, true);
                }
            }
            throw e;
        }
    }
```
查看ViewRootImpl.java$setView方法核心源码
```
   public void setView(View view, WindowManager.LayoutParams attrs, View panelParentView) {
        synchronized (this) {
            if (mView == null) {
                mView = view;
                int res; /* = WindowManagerImpl.ADD_OKAY; */

                //在添加到窗口之前设置布局，确保在接收到系统中的任何其他事件之前执行重新布局
                requestLayout();
               //mWindowSession为IWindowSession,它是一个Binder对象，真正的实现类是Session，内部实现IPC通信
               //mWindow是一个W对象，W为ViewRootImpl静态内部类继承IWindow.Stub
               res = mWindowSession.addToDisplay(mWindow, mSeq, mWindowAttributes,
                     getHostVisibility(), mDisplay.getDisplayId(),
                     mAttachInfo.mContentInsets, mAttachInfo.mStableInsets,
                            mAttachInfo.mOutsets, mInputChannel);
            
            }
        }
    }

    //查看Session.java$addToDisplay方法源码
    @Override
    public int addToDisplay(IWindow window, int seq, WindowManager.LayoutParams attrs,
            int viewVisibility, int displayId, Rect outContentInsets, Rect outStableInsets,
            Rect outOutsets, InputChannel outInputChannel) {
        //mService 为WindowMangerService对象，其实质是调用了WMS的addWindow方法(WMS分析待续 )
        return mService.addWindow(this, window, seq, attrs, viewVisibility, displayId,
                outContentInsets, outStableInsets, outOutsets, outInputChannel);
    }
```
分析完WindowManager的addView方法后,可以知道Window的添加过程:
![Window添加过程.png](https://upload-images.jianshu.io/upload_images/4954278-b88b334824332a28.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### Activity添加View的过程分析
Activity添加View的过程也就是把View添加到Window上的过程，添加View方法需要从setContentView开始
查看Activity.java$setContentView源码
```
  public void setContentView(@LayoutRes int layoutResID) {
        //getWindow返回的是一个Window对象，Window的具体实现类为PhoneWindow下面分析其setContentView方法
        getWindow().setContentView(layoutResID);
        //初始化ActionBar
        initWindowDecorActionBar();
    }
    //查看PhoneWindow.java$setContentView方法源码
   @Override
    public void setContentView(int layoutResID) {
       
        // mContentParent为ViewGroup对象，若其为空则初始化DecorView，
        if (mContentParent == null) {
            //安装DecorView
            installDecor();
        //mContentParent已经加载过且不需要动画移除所有View
        } else if (!hasFeature(FEATURE_CONTENT_TRANSITIONS)) {
            mContentParent.removeAllViews();
        }
        //若设置FEATURE_CONTENT_TRANSITIONS（过渡动画）则添加Scene来过渡启动
        if (hasFeature(FEATURE_CONTENT_TRANSITIONS)) {
            final Scene newScene = Scene.getSceneForLayout(mContentParent, layoutResID,
                    getContext());
            transitionTo(newScene);
        } else {
            //通过添加资源文件把View添加到mContentParent中,后面会给出分析
            mLayoutInflater.inflate(layoutResID, mContentParent);
        }
        mContentParent.requestApplyInsets();
        final Callback cb = getCallback();
        if (cb != null && !isDestroyed()) {
            cb.onContentChanged();
        }
        mContentParentExplicitlySet = true;
    }

 //继续查看installDecor方法
 private void installDecor() {
        mForceDecorInstall = false;
        if (mDecor == null) {
            //生成DecorView
            mDecor = generateDecor(-1);
            mDecor.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            mDecor.setIsRootNamespace(true);
            if (!mInvalidatePanelMenuPosted && mInvalidatePanelMenuFeatures != 0) {
                mDecor.postOnAnimation(mInvalidatePanelMenuRunnable);
            }
        } else {
            //mDecor设置Window窗口
            mDecor.setWindow(this);
        }
        if (mContentParent == null) {
            //生成mContentParent对象
            mContentParent = generateLayout(mDecor);

            // 设置UI在适当的情况下忽略合适的系统窗口
            mDecor.makeOptionalFitsSystemWindows();
            } else {
                mTitleView = (TextView) findViewById(R.id.title);
                //设置title
                if (mTitleView != null) {
                    if ((getLocalFeatures() & (1 << FEATURE_NO_TITLE)) != 0) {
                        final View titleContainer = findViewById(R.id.title_container);
                        if (titleContainer != null) {
                            titleContainer.setVisibility(View.GONE);
                        } else {
                            mTitleView.setVisibility(View.GONE);
                        }
                        mContentParent.setForeground(null);
                    } else {
                        mTitleView.setText(mTitle);
                    }
                }
            }

            if (mDecor.getBackground() == null && mBackgroundFallbackResource != 0) {
                //设置DecorView背景
                mDecor.setBackgroundFallback(mBackgroundFallbackResource);
            }
            ......//代码省略
            }
        }
    }
```
查看PhoneWindow.java$generateDecor方法核心源码
```
 protected DecorView generateDecor(int featureId) {
        ......//代码省略
        //通过直接创建DecorView对象并返回，DecorView继承FrameLayout
        return new DecorView(context, featureId, this, getAttributes());
    }
```
查看PhoneWindow.java$generatelayout方法核心源码
```
protected ViewGroup generateLayout(DecorView decor) {
         //获取当前主题并设置其属性
        TypedArray a = getWindowStyle();

        ......//省略其设置属性代码

        ......//省略其资源加载代码
     

        //通过用户设置的Feature来创建相应的布局主题。
        //这也是在代码中设置主题或者requestFeature的时候必须要在setContentView之前的原因
        int layoutResource;
        int features = getLocalFeatures();
        // System.out.println("Features: 0x" + Integer.toHexString(features));
        if ((features & (1 << FEATURE_SWIPE_TO_DISMISS)) != 0) {
            layoutResource = R.layout.screen_swipe_dismiss;
        } else if ((features & ((1 << FEATURE_LEFT_ICON) | (1 << FEATURE_RIGHT_ICON))) != 0) {
            if (mIsFloating) {
                TypedValue res = new TypedValue();
                getContext().getTheme().resolveAttribute(
                        R.attr.dialogTitleIconsDecorLayout, res, true);
                layoutResource = res.resourceId;
            } else {
                layoutResource = R.layout.screen_title_icons;
            }
            // XXX Remove this once action bar supports these features.
            removeFeature(FEATURE_ACTION_BAR);
            // System.out.println("Title Icons!");
        } else if ((features & ((1 << FEATURE_PROGRESS) | (1 << FEATURE_INDETERMINATE_PROGRESS))) != 0
                && (features & (1 << FEATURE_ACTION_BAR)) == 0) {

           ......//根绝Feature设置布局文件
        }

        mDecor.startChanging();
        mDecor.onResourcesLoaded(mLayoutInflater, layoutResource);

        //DecorView是顶级View，它包括状态栏，导航栏，内容区等，而contentParent指的是屏幕显示的内容区，
        //通常我们设置的布局文件则是contentParent中的子元素
        ViewGroup contentParent = (ViewGroup)findViewById(ID_ANDROID_CONTENT);
        if (contentParent == null) {
            throw new RuntimeException("Window couldn't find content container view");
        }

        if ((features & (1 << FEATURE_INDETERMINATE_PROGRESS)) != 0) {
            ProgressBar progress = getCircularProgressBar(false);
            if (progress != null) {
                progress.setIndeterminate(true);
            }
        }

        if ((features & (1 << FEATURE_SWIPE_TO_DISMISS)) != 0) {
            registerSwipeCallbacks();
        }

        // Remaining setup -- of background and title -- that only applies
        // to top-level windows.
        if (getContainer() == null) {
            final Drawable background;
            if (mBackgroundResource != 0) {
                background = getContext().getDrawable(mBackgroundResource);
            } else {
                background = mBackgroundDrawable;
            }
            //给DecorView设置窗口背景色
            mDecor.setWindowBackground(background);

            ......//DecorView属性设置

         }

        mDecor.finishChanging();

        return contentParent;
    }
```
在查看LayoutInflate.java$inflate方法源码之前先看下LayoutInflate类，其为抽象类，该类的作用是，将xml布局文件实例化为相应的View对象，在PhoneWindow类中在其构造函数中通过调用layoutInflate的from方法创建LayoutInflate对象，查看LayoutInflate.java$from方法源码
```
   public static LayoutInflater from(Context context) {
        //通过getSystemService函数使用Context.LAYOUT_INFLATER_SERVICE标志位转换成LayoutInflate服务对象
        LayoutInflater LayoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (LayoutInflater == null) {
            throw new AssertionError("LayoutInflater not found.");
        }
        return LayoutInflater;
    }

   //接下来查看LayoutInflate.java$inflate方法源码
   public View inflate(@LayoutRes int resource, @Nullable ViewGroup root) {
          return inflate(resource, root, root != null);
    }

   public View inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        final Resources res = getContext().getResources();
        if (DEBUG) {
            Log.d(TAG, "INFLATING from resource: \"" + res.getResourceName(resource) + "\" ("
                    + Integer.toHexString(resource) + ")");
        }
        //XmlResourceParser为xml资源解析器是一个接口其继承XmlPullParser,AttributeSet,AutoCloseable
        final XmlResourceParser parser = res.getLayout(resource);
        try {
            return inflate(parser, root, attachToRoot);
        } finally {
            parser.close();
        }
    }
```
查看LayoutI.java$inflate方法源码
```
 public View inflate(XmlPullParser parser, @Nullable ViewGroup root, boolean attachToRoot) {
        synchronized (mConstructorArgs) {
            Trace.traceBegin(Trace.TRACE_TAG_VIEW, "inflate");

            final Context inflaterContext = mContext;
            //获取xml的属性集合
            final AttributeSet attrs = Xml.asAttributeSet(parser);
            Context lastContext = (Context) mConstructorArgs[0];
            mConstructorArgs[0] = inflaterContext;
            View result = root;

            try {
                ......//查找根节点
            
                final String name = parser.getName();

                if (TAG_MERGE.equals(name)) {
                    if (root == null || !attachToRoot) {
                        throw new InflateException("<merge /> can be used only with a valid "
                                + "ViewGroup root and attachToRoot=true");
                    }
                    //如果xml布局文件中使用到merge标签将执行这一步
                    rInflate(parser, root, inflaterContext, attrs, false);
                } else {
                    //使用提供的属性集从标签名称创建临时根视图
                    final View temp = createViewFromTag(root, name, inflaterContext, attrs);

                    ViewGroup.LayoutParams params = null;

                    if (root != null) {
                        if (DEBUG) {
                            System.out.println("Creating params from root: " +
                                    root);
                        }
                        //创建与root相匹配的布局参数（如果提供）
                        params = root.generateLayoutParams(attrs);
                        if (!attachToRoot) {
                            //如果没有与root有联系则为 temp设置布局参数，如果有联系将会使用下面的addView
                            temp.setLayoutParams(params);
                        }
                    }

                    if (DEBUG) {
                        System.out.println("-----> start inflating children");
                    }

                    // 该方法调用的是rInflate方法最终使用递归方法用于降低xml层次结构并实例化子视图
                    rInflateChildren(parser, temp, attrs, true);

                    if (DEBUG) {
                        System.out.println("-----> done inflating children");
                    }

                    // We are supposed to attach all the views we found (int temp)
                    // to root. Do that now.
                    if (root != null && attachToRoot) {
                        root.addView(temp, params);
                    }

                    // Decide whether to return the root that was passed in or the
                    // top view found in xml.
                    if (root == null || !attachToRoot) {
                        result = temp;
                    }
                }

            } catch (XmlPullParserException e) {
                final InflateException ie = new InflateException(e.getMessage(), e);
                ie.setStackTrace(EMPTY_STACK_TRACE);
                throw ie;
            } catch (Exception e) {
                final InflateException ie = new InflateException(parser.getPositionDescription()
                        + ": " + e.getMessage(), e);
                ie.setStackTrace(EMPTY_STACK_TRACE);
                throw ie;
            } finally {
                //清除在上下文中存留的静态引用
                mConstructorArgs[0] = lastContext;
                mConstructorArgs[1] = null;

                Trace.traceEnd(Trace.TRACE_TAG_VIEW);
            }

            return result;
        }
    }
```
上面分析了Activity调用setContentView方法，该方法的作用为创建DecorView和加载Xml布局。
问题来了上面只说了视图View的创建与加载，那么View是在哪一步添加到Window中呢，又在哪一步显示呢？
这就涉及到Activity的启动过程，如果不了解[Activity启动过程](https://www.jianshu.com/p/86b405129385)的话可以去了解一下。

在分析[Activity启动过程](https://www.jianshu.com/p/86b405129385)中可以了解到Activity的Window创建是发生在attach方法中，系统会创建Activity所属的Window对象并为其设置回调接口其源码为
```
 final void attach(Context context, ActivityThread aThread,
            Instrumentation instr, IBinder token, int ident,
            Application application, Intent intent, ActivityInfo info,
            CharSequence title, Activity parent, String id,
            NonConfigurationInstances lastNonConfigurationInstances,
            Configuration config, String referrer, IVoiceInteractor voiceInteractor,
            Window window) {
        attachBaseContext(context);

        mFragments.attachHost(null /*parent*/);
        //创建Window窗口
        mWindow = new PhoneWindow(this, window);
        mWindow.setWindowControllerCallback(this);
        mWindow.setCallback(this);
        mWindow.setOnWindowDismissedCallback(this);
        
        //设置WindowManager
        mWindow.setWindowManager(
                (WindowManager)context.getSystemService(Context.WINDOW_SERVICE),
                mToken, mComponent.flattenToString(),
                (info.flags & ActivityInfo.FLAG_HARDWARE_ACCELERATED) != 0);
        if (mParent != null) {
            mWindow.setContainer(mParent.getWindow());
        }
        mWindowManager = mWindow.getWindowManager();
        mCurrentConfig = config;
    }
```
这里我们直接从ActivityThread.java$handleLaunchActivity方法分析，**下面只分析与View添加到Window中有关代码**
```
private void handleLaunchActivity(ActivityClientRecord r, Intent customIntent, String reason) {

 Activity a = performLaunchActivity(r, customIntent);

 if (a != null) {
            r.createdConfig = new Configuration(mConfiguration);
            reportSizeConfigurations(r);
            Bundle oldState = r.state;
            handleResumeActivity(r.token, false, r.isForward,
                    !r.activity.mFinished && !r.startsNotResumed, r.lastProcessedSeq, reason);
      }

  }

 //继续查看ActivityThread.java$performLaunchActivity方法核心源码
 private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
      
        Activity activity = null;
        //创建Activity实例
        activity = mInstrumentation.newActivity(
               cl, component.getClassName(), r.intent);

        //调用Activity的attach方法上面分析过该方法中创建Window并初始化
        activity.attach(appContext, this, getInstrumentation(), r.token,
                        r.ident, app, r.intent, r.activityInfo, title, r.parent,
                        r.embeddedID, r.lastNonConfigurationInstances, config,
                        r.referrer, r.voiceInteractor, window);

       
        return activity;
    }
//继续查看handleResumeActivity方法源码
  final void handleResumeActivity(IBinder token,
            boolean clearHide, boolean isForward, boolean reallyResume, int seq, String reason) {
        //根据token获取对应Activity的记录信息
        ActivityClientRecord r = mActivities.get(token);

        ......//代码省略

        //执行onResume方法
        r = performResumeActivity(token, clearHide, reason);

        if (r != null) {
            final Activity a = r.activity;

            ......//代码省略

          
            if (r.window == null && !a.mFinished && willBeVisible) {
                r.window = r.activity.getWindow();
                //获取DecorView对象
                View decor = r.window.getDecorView();
                //将其设置为不可见状态
                decor.setVisibility(View.INVISIBLE);
                ViewManager wm = a.getWindowManager();
                WindowManager.LayoutParams l = r.window.getAttributes();
                a.mDecor = decor;
                l.type = WindowManager.LayoutParams.TYPE_BASE_APPLICATION;
                l.softInputMode |= forwardBit;
                if (r.mPreserveWindow) {
                    a.mWindowAdded = true;
                    r.mPreserveWindow = false;
                    
                    ViewRootImpl impl = decor.getViewRootImpl();
                    if (impl != null) {
                        impl.notifyChildRebuilt();
                    }
                }

                if (a.mVisibleFromClient && !a.mWindowAdded) {
                    a.mWindowAdded = true;
                    //执行到这一步才真正和我们上面分析的WindowManager.addView相连，
                    //即把View通过WindowManager添加到Window上
                    wm.addView(decor, l);
                }
            //如果窗口已添加，但在恢复期间，我们开始了另一个活动，但窗口尚未可见。
            } else if (!willBeVisible) {
                if (localLOGV) Slog.v(
                    TAG, "Launch " + r + " mStartedActivity set");
                r.hideForNow = true;
            }

            // Get rid of anything left hanging around.
            cleanUpPendingRemoveWindows(r, false /* force */);

            // The window is now visible if it has been added, we are not
            // simply finishing, and we are not starting another activity.
            if (!r.activity.mFinished && willBeVisible
                    && r.activity.mDecor != null && !r.hideForNow) {
              
                ......//代码省略

                WindowManager.LayoutParams l = r.window.getAttributes();
                if ((l.softInputMode
                        & WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION)
                        != forwardBit) {
                    l.softInputMode = (l.softInputMode
                            & (~WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION))
                            | forwardBit;
                    if (r.activity.mVisibleFromClient) {
                        ViewManager wm = a.getWindowManager();
                        View decor = r.window.getDecorView();
                        //经过一系列判断更新View视图
                        wm.updateViewLayout(decor, l);
                    }
                }
                r.activity.mVisibleFromServer = true;
                mNumVisibleActivities++;
                if (r.activity.mVisibleFromClient) {
                    //调用Activity的makeVisible方法使视图View可见
                    r.activity.makeVisible();
                }
            }

           ......//代码省略
    }
```
在未执行Activity的makeVisibale方法之前View是不可见状态，也就是在Activity的生命周期的onResume方法中View才可见。
查看Activity.java$makeVisible方法源码
```
  void makeVisible() {
        //若View已经未添加到Window上则执行该代码块
        if (!mWindowAdded) {
            ViewManager wm = getWindowManager();
            wm.addView(mDecor, getWindow().getAttributes());
            mWindowAdded = true;
        }
        //设置视图View可见
        mDecor.setVisibility(View.VISIBLE);
    }
```
到这里就**把Activity从创建视图View -> 视图View添加到Window窗口中 -> 显示View的过程**分析完了。
> 即：View的在Activity中的创建、添加、显示过程。
### 总结
1. Activity在onCreate方法之前调用attach方法，该方法中创建window对象。
2. 用户在onCreate方法中调用setContentView方法，在该方法中检查DecorView是否存在，若不存在则创建DecorView对象，然后通过LayoutInflate的inflate方法把布局文件实例化为相应的View对象添加到DecorView中。setContentView的作用就是完成View的创建与加载。
3. 在Activity生命周期onResume方法执行完后，把View添加到Window中去调用的是WindowManager的addView方法，最后调用ViewRootImpl的setView方法把View 添加到Window上，最终调用WindowManagerService的addWindow方法添加Window。
4. 在View添加到Window上后执行Activity的makeVisible方法显示View视图。







