#### 前言
分析完Activity启动过程的源码后根据自己的理解作出Activity启动的源码流程图，算是进一步加深对Activity启动过程的理解。
![Activity启动源码流程图.png](https://upload-images.jianshu.io/upload_images/4954278-c74faeb5c9abdee4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
> 1.  Instrumentation : 监控应用与系统相关的交互行为
> 2. ActivityManagerService(AMS)：负责系统中四大组件的启动、切换、调度及应用程序的管理和调度等工作。
> 3. ActivityStackSupervisior : 管理任务栈
> 4. ActivityStack ：管理任务栈中的Activity
> 5. ActivityThread : Activity、Service、BroadcastReceiver的启动、切换、调度等各种操作都在这个类里完成。



#### 源码分析
查看Activity.java$startActivity源码
 ```
@Override
 public void startActivity(Intent intent) {
        this.startActivity(intent, null);
    }
// 如果在代码中直接调用该方法api level 要求最低为16
  @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        //  -1 为 requestCode
        if (options != null) {
            startActivityForResult(intent, -1, options);
        } else {
            startActivityForResult(intent, -1);
        }
    }
```
startActivity方法最终调用的是startActivityForResult方法

查看Activity.java$startAcitivityForResult源码
```
  public void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, null);
    }
//继续查看
 public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        //mParent 指的是Activity  赋值是在内部API调用setParent方法
        //   final void setParent(Activity parent) {
        //      mParent = parent;
        //    }
        if (mParent == null) {
            //Instrumentation为工具类ActivityResult为其静态内部类  工具类调用执行开始Activity方法execStartActity 
            //  mMainThread为ActivityThread ，getApplicationThread()方法获取的是ApplicationThread实例
            //ApplicationThread是ActivityThread的内部类  该类继承ApplicationThreadNative抽象类，
            //而ApplicationThreadNative继承Binder类并实现IApplicationThread接口 
            //IApplictionThread继承了IInterface接口
            // Binder类继承IBinder接口，这就是为什么execStartActivity方法的第二个参数定义为IBinder
            Instrumentation.ActivityResult ar =
                mInstrumentation.execStartActivity(
                    this, mMainThread.getApplicationThread(), mToken, this,
                    intent, requestCode, options);
            //ar创建后 mMainThread调用sendActivityResult方法后面会分析源码
            if (ar != null) {
               // ar不为空说明Activity启动成功 执行sendActivityResult方法
                mMainThread.sendActivityResult(
                    mToken, mEmbeddedID, requestCode, ar.getResultCode(),
                    ar.getResultData());
            }
            if (requestCode >= 0) {
                // If this start is requesting a result, we can avoid making
                // the activity visible until the result is received.  Setting
                // this code during onCreate(Bundle savedInstanceState) or onResume() will keep the
                // activity hidden during this time, to avoid flickering.
                // This can only be done when a result is requested because
                // that guarantees we will get information back when the
                // activity is finished, no matter what happens to it.
                mStartedActivity = true;
            }
            // mWindow 指的是Window  该对象是在attach方法中创建 后面会分析attach方法
            final View decor = mWindow != null ? mWindow.peekDecorView() : null;
            if (decor != null) {
                decor.cancelPendingInputEvents();
            }
            // TODO Consider clearing/flushing other event sources and events for child windows.
        } else {
          // mParent 不为null时调用startActivityFromChild方法
            if (options != null) {
                mParent.startActivityFromChild(this, intent, requestCode, options);
            } else {
                // Note we want to go through this method for compatibility with
                // existing applications that may have overridden it.
                mParent.startActivityFromChild(this, intent, requestCode);
            }
        }
        if (options != null && !isTopOfTask()) {
            mActivityTransitionState.startExitOutTransition(this, options);
        }
    }
```
先分析mParent不为null时由上面源码可知最终调用的是startActivityFromChild方法

查看Activity.java$startActivityFromChild源码
```
public void startActivityFromChild(@NonNull Activity child, Intent intent,
            int requestCode) {
        startActivityFromChild(child, intent, requestCode, null);
    }
//继续查看
public void startActivityFromChild(@NonNull Activity child, Intent intent,
            int requestCode, @Nullable Bundle options) {
        Instrumentation.ActivityResult ar =
            mInstrumentation.execStartActivity(
                this, mMainThread.getApplicationThread(), mToken, child,
                intent, requestCode, options);
        if (ar != null) {
            //mToken是一个IBinder对象，ar.getResultData()返回Intent
            mMainThread.sendActivityResult(
                mToken, child.mEmbeddedID, requestCode,
                ar.getResultCode(), ar.getResultData());
        }
    }
```
查看startActivityFormChild源码方法发现该方法中执行的方法与当mParent为null时执行的方法相同，都调用了 Instrumentation的execStartActivity方法以及 ActivityThread的sendActivityResult方法

查看Instrumentation.java$execStartActivity源码
```
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        //IApplicationThread是一个接口继承IInterface接口
        IApplicationThread whoThread = (IApplicationThread) contextThread;
        //mActivityMonitors是一个ActivityMonitor的list列表
        if (mActivityMonitors != null) {
            //同步执行 mSync为一个Object对象
            synchronized (mSync) {
                final int N = mActivityMonitors.size();
                for (int i=0; i<N; i++) {
                   //ActivityMonitor为Activity的监测器是Instrumentation的静态内部类，
                   //当一个Activity启动时将会被检测
                    final ActivityMonitor am = mActivityMonitors.get(i);
                    if (am.match(who, null, intent)) {
                        am.mHits++;
                        if (am.isBlocking()) {
                            return requestCode >= 0 ? am.getResult() : null;
                        }
                        break;
                    }
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData();
            intent.prepareToLeaveProcess();
            // result 为启动Activity返回的状态码，下面将对ActivityManagerNative.getDefault()源码进行分析
            int result = ActivityManagerNative.getDefault()
                .startActivity(whoThread, who.getBasePackageName(), intent,
                        intent.resolveTypeIfNeeded(who.getContentResolver()),
                        token, target != null ? target.mEmbeddedID : null,
                        requestCode, 0, null, options);
            //检查Activity是否启动成功若未启动成功则抛出对应异常
            checkStartActivityResult(result, intent);
        } catch (RemoteException e) {
        }
        return null;
    }
```
这里先看Activity启动成功后执行的sendActivityResult方法
查看ActivityThread.java$sendActivityResult源码
```
    public final void sendActivityResult(
            IBinder token, String id, int requestCode,
            int resultCode, Intent data) {
        if (DEBUG_RESULTS) Slog.v(TAG, "sendActivityResult: id=" + id
                + " req=" + requestCode + " res=" + resultCode + " data=" + data);
        ArrayList<ResultInfo> list = new ArrayList<ResultInfo>();
        list.add(new ResultInfo(id, requestCode, resultCode, data));
        //mAppThread 为 ApplicationThread对象
        mAppThread.scheduleSendResult(token, list);
    }

    public final void scheduleSendResult(IBinder token, List<ResultInfo> results) {
            ResultData res = new ResultData();
            res.token = token;
            res.results = results;
            //H 为ActivityThread 内部类继承Handler
            sendMessage(H.SEND_RESULT, res);
        }

  private void sendMessage(int what, Object obj) {
        sendMessage(what, obj, 0, 0, false);
    }

    private void sendMessage(int what, Object obj, int arg1, int arg2, boolean async) {
        if (DEBUG_MESSAGES) Slog.v(
            TAG, "SCHEDULE " + what + " " + mH.codeToString(what)
            + ": " + arg1 + " / " + obj);
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        if (async) {
            msg.setAsynchronous(true);
        }
       // mH为H对象，
        mH.sendMessage(msg);
    }
```
可知mMainThread 发送ActivityResult 最终是通过Handler发送消息

现在查看 ActivityManagerNative.getDefault().startActivity方法源码，先看ActivityManagerNative其为抽象类getDefault方法
ActivityManagerNative.java$ActivityManagerNative.getDefault()源码：
```
//返回的是IActivityManager，其为接口并继承IInterface接口
    static public IActivityManager getDefault() {
        return gDefault.get();
    }
//通过gDefault.get()返回的IActivityManager，gDefault为一个Singleton对象，内部通过单例模式一个T对象
// 这里的T对象为IActivityManager  然后调用create方法创建IActivityManager
 private static final Singleton<IActivityManager> gDefault = new Singleton<IActivityManager>() {
        protected IActivityManager create() {
            //获取一个关联系统服务ActivityManagerService的Binder对象
            IBinder b = ServiceManager.getService("activity");
            if (false) {
                Log.v("ActivityManager", "default service binder = " + b);
            }
            //返回一个IActivityManager的代理对象
            IActivityManager am = asInterface(b);
            if (false) {
                Log.v("ActivityManager", "default service = " + am);
            }
            return am;
        }
    };
    
    static public IActivityManager asInterface(IBinder obj) {
        //代码省略
        ......
        return new ActivityManagerProxy(obj);
    }
```
 ActivityManagerNative.getDefault()调用startActivity方法，其实质是调用ActivityManagerProxy类中的startActivity，而ActivityManagerProxy是ActivityManagerNative中的一个代理方法

```
   // 通过aidl来进行进程间通信
    public int startActivity(IApplicationThread caller, String callingPackage, Intent intent,
            String resolvedType, IBinder resultTo, String resultWho, int requestCode,
            int startFlags, ProfilerInfo profilerInfo, Bundle options) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeInterfaceToken(IActivityManager.descriptor);
        data.writeStrongBinder(caller != null ? caller.asBinder() : null);
        data.writeString(callingPackage);
        intent.writeToParcel(data, 0);
        data.writeString(resolvedType);
        data.writeStrongBinder(resultTo);
        data.writeString(resultWho);
        data.writeInt(requestCode);
        data.writeInt(startFlags);
        if (profilerInfo != null) {
            data.writeInt(1);
            profilerInfo.writeToParcel(data, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        } else {
            data.writeInt(0);
        }
        if (options != null) {
            data.writeInt(1);
            options.writeToParcel(data, 0);
        } else {
            data.writeInt(0);
        }
        mRemote.transact(START_ACTIVITY_TRANSACTION, data, reply, 0);
        reply.readException();
        int result = reply.readInt();
        reply.recycle();
        data.recycle();
        return result;
    }

public abstract class ActivityManagerNative extends Binder implements IActivityManager{}
```
上面发现ActivityManagerNavtive是抽象类它继承Binder类并实现IActivityManager接口，ActivityManagerService(AMS)为其子类，AMS实现了IActivityManager接口中的方法，而ActivityManagerNative.getDefault是一个IActivityManager类型的Binder对象，所以它的具体实现是在AMS类中。

查看ActivityManagerService.java$startActivity核心代码
```
    @Override
    public final int startActivity(IApplicationThread caller, String callingPackage,
            Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode,
            int startFlags, ProfilerInfo profilerInfo, Bundle options) {
        return startActivityAsUser(caller, callingPackage, intent, resolvedType, resultTo,
            resultWho, requestCode, startFlags, profilerInfo, options,
            UserHandle.getCallingUserId());
    }

    @Override
    public final int startActivityAsUser(IApplicationThread caller, String callingPackage,
            Intent intent, String resolvedType, IBinder resultTo, String resultWho, int requestCode,
            int startFlags, ProfilerInfo profilerInfo, Bundle options, int userId) {
        enforceNotIsolatedCaller("startActivity");
        userId = handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), userId,
                false, ALLOW_FULL_ONLY, "startActivity", null);
        // TODO: Switch to user app stacks here.
        //mStackSupervisor 为ActivityStackSupervisor对象可视为Activity栈管理者
        return mStackSupervisor.startActivityMayWait(caller, -1, callingPackage, intent,
                resolvedType, null, null, resultTo, resultWho, requestCode, startFlags,
                profilerInfo, null, null, options, userId, null, null);
    }
```
查看ActivityStackSupervisor.java$startActivityMayWait方法源码
```
 final int startActivityMayWait(IApplicationThread caller, int callingUid,
            String callingPackage, Intent intent, String resolvedType,
            IVoiceInteractionSession voiceSession, IVoiceInteractor voiceInteractor,
            IBinder resultTo, String resultWho, int requestCode, int startFlags,
            ProfilerInfo profilerInfo, WaitResult outResult, Configuration config,
            Bundle options, int userId, IActivityContainer iContainer, TaskRecord inTask) {

        //代码省略
          ......

        // 对Intent参数进行解析获取Activity的相关信息，并把数据保存在aInfo变量中
        ActivityInfo aInfo = resolveActivity(intent, resolvedType, startFlags,
                profilerInfo, userId);

        //代码省略
          ......

            int res = startActivityLocked(caller, intent, resolvedType, aInfo,
                    voiceSession, voiceInteractor, resultTo, resultWho,
                    requestCode, callingPid, callingUid, callingPackage,
                    realCallingPid, realCallingUid, startFlags, options,
                    componentSpecified, null, container, inTask);

        //代码省略
          ......

            return res;
        }
    }
// 查看startActivityLocked方法核心代码
  final int startActivityLocked(IApplicationThread caller,
            Intent intent, String resolvedType, ActivityInfo aInfo,
            IVoiceInteractionSession voiceSession, IVoiceInteractor voiceInteractor,
            IBinder resultTo, String resultWho, int requestCode,
            int callingPid, int callingUid, String callingPackage,
            int realCallingPid, int realCallingUid, int startFlags, Bundle options,
            boolean componentSpecified, ActivityRecord[] outActivity, ActivityContainer container,
            TaskRecord inTask) {

       //代码省略
          ......

        //  把将要启动的Activity的相关信息保存在变量r中
        ActivityRecord r = new ActivityRecord(mService, callerApp, callingUid, callingPackage,
                intent, resolvedType, aInfo, mService.mConfiguration, resultRecord, resultWho,
                requestCode, componentSpecified, this, container, options);

      //代码省略
          ......

        doPendingActivityLaunchesLocked(false);

        err = startActivityUncheckedLocked(r, sourceRecord, voiceSession, voiceInteractor,
                startFlags, true, options, inTask);

       //代码省略
          ......

        return err;
    }
//查看 doPendingActivityLaunchesLocked 方法源码
 final void doPendingActivityLaunchesLocked(boolean doResume) {
        while (!mPendingActivityLaunches.isEmpty()) {
            PendingActivityLaunch pal = mPendingActivityLaunches.remove(0);
            startActivityUncheckedLocked(pal.r, pal.sourceRecord, null, null, pal.startFlags,
                    doResume && mPendingActivityLaunches.isEmpty(), null, null);
        }
    }

//查看startActivityUncheckedLocked方法核心代码
final int startActivityUncheckedLocked(ActivityRecord r, ActivityRecord sourceRecord,
            IVoiceInteractionSession voiceSession, IVoiceInteractor voiceInteractor, int startFlags,
            boolean doResume, Bundle options, TaskRecord inTask) {
        final Intent intent = r.intent;
        final int callingUid = r.launchedFromUid;

        // In some flows in to this function, we retrieve the task record and hold on to it
        // without a lock before calling back in to here...  so the task at this point may
        // not actually be in recents.  Check for that, and if it isn't in recents just
        // consider it invalid.
        if (inTask != null && !inTask.inRecents) {
            Slog.w(TAG, "Starting activity in task not in recents: " + inTask);
            inTask = null;
        }
       // Activity的四种不同的启动模式 默认为standard模式
        final boolean launchSingleTop = r.launchMode == ActivityInfo.LAUNCH_SINGLE_TOP;
        final boolean launchSingleInstance = r.launchMode == ActivityInfo.LAUNCH_SINGLE_INSTANCE;
        final boolean launchSingleTask = r.launchMode == ActivityInfo.LAUNCH_SINGLE_TASK;
        //获取intent的标志值并保存在launchFlags变量中
        int launchFlags = intent.getFlags();
        if ((launchFlags & Intent.FLAG_ACTIVITY_NEW_DOCUMENT) != 0 &&
                (launchSingleInstance || launchSingleTask)) {
            // We have a conflict between the Intent and the Activity manifest, manifest wins.
            Slog.i(TAG, "Ignoring FLAG_ACTIVITY_NEW_DOCUMENT, launchMode is " +
                    "\"singleInstance\" or \"singleTask\"");
            launchFlags &=
                    ~(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        } else {
            switch (r.info.documentLaunchMode) {
                case ActivityInfo.DOCUMENT_LAUNCH_NONE:
                    break;
                case ActivityInfo.DOCUMENT_LAUNCH_INTO_EXISTING:
                    launchFlags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
                    break;
                case ActivityInfo.DOCUMENT_LAUNCH_ALWAYS:
                    launchFlags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
                    break;
                case ActivityInfo.DOCUMENT_LAUNCH_NEVER:
                    launchFlags &= ~Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
                    break;
            }
        }

       //代码省略
          ......
       // 判断当前要启动的Activity是否就是当前栈顶的Activity，如果是在一些情况下就不在启动
       if ((launchFlags & Intent.FLAG_ACTIVITY_SINGLE_TOP) != 0
                            || launchSingleTop || launchSingleTask) {
                            ActivityStack.logStartActivity(EventLogTags.AM_NEW_INTENT, top,
                                    top.task);
                            // For paranoia, make sure we have correctly
                            // resumed the top activity.
                            topStack.mLastPausedActivity = null;
                            if (doResume) {
                                resumeTopActivitiesLocked();
                            }
                            ActivityOptions.abort(options);
            }
        //代码省略
        ......
      
        return ActivityManager.START_SUCCESS;
    }
//继续查看
boolean resumeTopActivitiesLocked() {
        return resumeTopActivitiesLocked(null, null, null);
    }

    boolean resumeTopActivitiesLocked(ActivityStack targetStack, ActivityRecord target,
            Bundle targetOptions) {

       //代码省略
       ......

       result = targetStack.resumeTopActivityLocked(target, targetOptions);

        return result;
    }
//继续查看
final boolean resumeTopActivityLocked(ActivityRecord prev, Bundle options) {
        if (mStackSupervisor.inResumeTopActivity) {
            // Don't even start recursing.
            return false;
        }
        boolean result = false;
        try {
    
           //代码省略
            ......

            result = resumeTopActivityInnerLocked(prev, options);
        } finally {
            mStackSupervisor.inResumeTopActivity = false;
        }
        return result;
    }
//继续查看resumeTopActivityInnerLocked核心代码
 final boolean resumeTopActivityInnerLocked(ActivityRecord prev, Bundle options) {

        //代码省略
        ......

        //表示当前的Activity正在运行
        if (mResumedActivity != null) {
            if (DEBUG_STATES) Slog.d(TAG, "resumeTopActivityLocked: Pausing " + mResumedActivity);
            //让当前运行的Activity进入pause状态
            pausing |= startPausingLocked(userLeaving, false, true, dontWaitForPause);
        }
    
       //代码省略
       ......

       mStackSupervisor.startSpecificActivityLocked(next, true, true);
        return true;
    }
```
查看ActivityStackSupervisor.java$startSpecificActivityLockedf方法的核心代码：
```
final boolean realStartActivityLocked(ActivityRecord r, ProcessRecord app,
            boolean andResume, boolean checkConfig) throws RemoteException {

     //代码省略
       ......
     //app 为一个ProcessRecord对象记录进程信息
      //app.thread 为IApplicationThread对象
            app.thread.scheduleLaunchActivity(new Intent(r.intent), r.appToken,
                    System.identityHashCode(r), r.info, new Configuration(mService.mConfiguration),
                    new Configuration(task.mOverrideConfig), r.compat, r.launchedFromPackage,
                    task.voiceInteractor, app.repProcState, r.icicle, r.persistentState, results,
                    newIntents, !andResume, mService.isNextTransitionForward(), profilerInfo);

        //代码省略
       ......

        return true;
    }
```
app.thread 是一个IApplicationThread对象，抽象类ActivityManagerNative实现IApplicationThread接口，而ApplicationThread是 ActivityThread类的内部类，其继承了ActivityManagerNative抽象类，并在该类中实现了scheduleLaunchActivity方法， 通过Binder机制调用了ApplicationThread类中的scheduleLaunchActivity方法。

查看ActivityThread.java$ApplicationThread类中的scheduleLaunchActivity方法核心代码
```
  @Override
        public final void scheduleLaunchActivity(Intent intent, IBinder token, int ident,
                ActivityInfo info, Configuration curConfig, Configuration overrideConfig,
                CompatibilityInfo compatInfo, String referrer, IVoiceInteractor voiceInteractor,
                int procState, Bundle state, PersistableBundle persistentState,
                List<ResultInfo> pendingResults, List<ReferrerIntent> pendingNewIntents,
                boolean notResumed, boolean isForward, ProfilerInfo profilerInfo) {

            updateProcessState(procState, false);
            //记录启动Activity需要的参数值
            ActivityClientRecord r = new ActivityClientRecord();

            r.token = token;
            r.ident = ident;
            r.intent = intent;
            r.referrer = referrer;
            r.voiceInteractor = voiceInteractor;
            r.activityInfo = info;
            r.compatInfo = compatInfo;
            r.state = state;
            r.persistentState = persistentState;

            r.pendingResults = pendingResults;
            r.pendingIntents = pendingNewIntents;

            r.startsNotResumed = notResumed;
            r.isForward = isForward;

            r.profilerInfo = profilerInfo;

            r.overrideConfig = overrideConfig;
            updatePendingConfiguration(curConfig);
            //H为其内部类继承Handler
            sendMessage(H.LAUNCH_ACTIVITY, r);
        }

 //继续查看sendMessage方法源
 private void sendMessage(int what, Object obj) {
        sendMessage(what, obj, 0, 0, false);
    }

    private void sendMessage(int what, Object obj, int arg1, int arg2, boolean async) {
        if (DEBUG_MESSAGES) Slog.v(
            TAG, "SCHEDULE " + what + " " + mH.codeToString(what)
            + ": " + arg1 + " / " + obj);
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        if (async) {
            msg.setAsynchronous(true);
        }
       // mH为H对象，
        mH.sendMessage(msg);
    }
```
所以 启动Activity的方法放在了处理消息H$handlerMessage方法中
查看H$handlerMessage核心代码
```
  public void handleMessage(Message msg) {
            switch (msg.what) {
                case LAUNCH_ACTIVITY: {
     Trace.traceBegin(Trace.TRACE_TAG_ACTIVITY_MANAGER, "activityStart");
                    final ActivityClientRecord r = (ActivityClientRecord) msg.obj;
                    r.packageInfo = getPackageInfoNoCheck(
                            r.activityInfo.applicationInfo, r.compatInfo);
                    //处理发起Activity
                    handleLaunchActivity(r, null, "LAUNCH_ACTIVITY");
                    Trace.traceEnd(Trace.TRACE_TAG_ACTIVITY_MANAGER);
                } break;
       
         //代码省略
         ......

        }
```
查看handlerLaunchActivity方法源码
```
private void handleLaunchActivity(ActivityClientRecord r, Intent customIntent, String reason) {
        // If we are getting ready to gc after going to the background, well
        // we are back active so skip it.
        unscheduleGcIdler();
        mSomeActivitiesChanged = true;

        if (r.profilerInfo != null) {
            mProfiler.setProfiler(r.profilerInfo);
            mProfiler.startProfiling();
        }

        // Make sure we are running with the most recent config.
        handleConfigurationChanged(null, null);

        if (localLOGV) Slog.v(
            TAG, "Handling launch of " + r);

        // Initialize before creating the activity
        WindowManagerGlobal.initialize();
         //调用该方法启动Activity
        Activity a = performLaunchActivity(r, customIntent);
       
        if (a != null) {
            r.createdConfig = new Configuration(mConfiguration);
            reportSizeConfigurations(r);
            Bundle oldState = r.state;
            //a不为空说明 该方法调用Activity的onResume方法
            handleResumeActivity(r.token, false, r.isForward,
                    !r.activity.mFinished && !r.startsNotResumed, r.lastProcessedSeq, reason);

         //代码省略
         ......

        } else {
            // If there was an error, for any reason, tell the activity manager to stop us.
            try {
                ActivityManagerNative.getDefault()
                    .finishActivity(r.token, Activity.RESULT_CANCELED, null,
                            Activity.DONT_FINISH_TASK_WITH_ACTIVITY);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }
    }
```
接着查看performLaunchActivity方法的核心代码
```
 private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
        //获取记录的要启动的Activity信息
        ActivityInfo aInfo = r.activityInfo;
        if (r.packageInfo == null) {
            r.packageInfo = getPackageInfo(aInfo.applicationInfo, r.compatInfo,
                    Context.CONTEXT_INCLUDE_CODE);
        }
        //获取记录的包名以及类名信息
        ComponentName component = r.intent.getComponent();
        if (component == null) {
            component = r.intent.resolveActivity(
                mInitialApplication.getPackageManager());
            r.intent.setComponent(component);
        }

        if (r.activityInfo.targetActivity != null) {
            component = new ComponentName(r.activityInfo.packageName,
                    r.activityInfo.targetActivity);
        }

        Activity activity = null;
        try {
            //根据包信息获取类加载器
            java.lang.ClassLoader cl = r.packageInfo.getClassLoader();
            //调用Instrumentation的newActivity方法创建Activity对象
            activity = mInstrumentation.newActivity(
                    cl, component.getClassName(), r.intent);
            
         //代码省略
         ......

        try {
            //创建Application对象
            Application app = r.packageInfo.makeApplication(false, mInstrumentation);

            //代码省略
            ......

            if (activity != null) {
                //创建Activity的上下文环境
                Context appContext = createBaseContextForActivity(r, activity);

               //代码省略
                ......

                //调用ActivityThread 的attach方法
                activity.attach(appContext, this, getInstrumentation(), r.token,
                        r.ident, app, r.intent, r.activityInfo, title, r.parent,
                        r.embeddedID, r.lastNonConfigurationInstances, config,
                        r.referrer, r.voiceInteractor, window);

                   //代码省略
                   ......
  
                    mInstrumentation.callActivityOnCreate(activity, r.state);
       
               //代码省略
                ......

        return activity;
    }
```
在Instrumentation类中调用newActivity方法创建Activity其源码为：
```
  public Activity newActivity(ClassLoader cl, String className,
            Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return (Activity)cl.loadClass(className).newInstance();
    }
```
内部通过类加载器创建Activity实例

查看LoaderApk.java$makeApplication方法源码
```
 public Application makeApplication(boolean forceDefaultAppClass,
            Instrumentation instrumentation) {
        //这也是一个Android应用中只有一个Application的原因
        if (mApplication != null) {
            return mApplication;
        }

      //代码省略
       ......

        Application app = null;

        //代码省略
        ......
        
        //  当Application为null时 创建Application 创建过程
        //与创建Activity类似，都是通过类加载器ClassLoader类创建Application实例的
        app = mActivityThread.mInstrumentation.newApplication(
                    cl, appClass, appContext);

         //代码省略
         ......

        return app;
    }
```
然后activity通过调用attach方法初始化数据

查看Activity.java$attach方法源码
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
        //创建窗口 PhoneWindow为Window的具体实现类
        mWindow = new PhoneWindow(this, window);
        mWindow.setWindowControllerCallback(this);
        mWindow.setCallback(this);
        mWindow.setOnWindowDismissedCallback(this);
        mWindow.getLayoutInflater().setPrivateFactory(this);
        if (info.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            mWindow.setSoftInputMode(info.softInputMode);
        }
        if (info.uiOptions != 0) {
            mWindow.setUiOptions(info.uiOptions);
        }
        mUiThread = Thread.currentThread();

        mMainThread = aThread;
        mInstrumentation = instr;
        mToken = token;
        mIdent = ident;
        mApplication = application;
        mIntent = intent;
        mReferrer = referrer;
        mComponent = intent.getComponent();
        mActivityInfo = info;
        mTitle = title;
        mParent = parent;
        mEmbeddedID = id;
        mLastNonConfigurationInstances = lastNonConfigurationInstances;
        if (voiceInteractor != null) {
            if (lastNonConfigurationInstances != null) {
                mVoiceInteractor = lastNonConfigurationInstances.voiceInteractor;
            } else {
                mVoiceInteractor = new VoiceInteractor(voiceInteractor, this, this,
                        Looper.myLooper());
            }
        }

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
查看Instrumentation.java$callActivityOnCreate方法源码
```
public void callActivityOnCreate(Activity activity, Bundle icicle) {
        prePerformCreate(activity);
        //调用Activity方法中的 performCreate方法
        activity.performCreate(icicle);
        postPerformCreate(activity);
    }
//继续查看Activity中的performCreate方法源码
final void performCreate(Bundle icicle) {
        restoreHasCurrentPermissionRequest(icicle);
        // 调用Activity中的onCreate方法
        onCreate(icicle);
        mActivityTransitionState.readState(icicle);
        performCreateCommon();
    }
```
最后调用onCreate方法启动Activity。










