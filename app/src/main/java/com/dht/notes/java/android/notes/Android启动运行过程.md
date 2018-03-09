[Android应用程序进程启动过程的源代码分析](http://blog.csdn.net/luoshengyang/article/details/6747696)
[Android应用程序启动过程源代码分析](http://blog.csdn.net/luoshengyang/article/details/6689748)

### MainActivity的启动过程

step1 ：Launcher.startActivitySafely

在Android系统中应用程序都是由Launcher启动起来的，其实，launcher本身也是一个应用程序，其他的应用程序安装后，就会
在launcher的界面上出现一个相应的图标，点击这个图标对应的应用程序就会启动起来。

step2 ：Activity.startActivity

launcher继承于Activity类，而Activity类实现了startActivity函数。因此，这里就调用了Activity.startActivity函数。

step3  ：Activity.startActivityForResult 
        
step4  ：Instrumentation.execStartActivity

step5  ：ActivityManageProxy.startActivity 通过binder驱动进去到ActivityManagerService.startActivity

step6  : ActivityManagerService.startActivity 通过简单操作转发给ActivityStack.startActivityMayWait函数

step7  ：ActivityStack.startActivityMayWait

step8  ：ActivityStack.startActivityLocked

step9  : ActivityStack.startActivityUncheckedLocked

step10 : Activity.resumeTopActivityLocked

step11 : ActivityStack.startPausingLocked

Step 12. ApplicationThreadProxy.schedulePauseActivity

Step 13. ApplicationThread.schedulePauseActivity

Step 14. ActivityThread.queueOrSendMessage

Step 15. H.handleMessage

Step 16. ActivityThread.handlePauseActivity

Step 17. ActivityManagerProxy.activityPaused

Step 18. ActivityManagerService.activityPaused

Step 19. ActivityStack.activityPaused

Step 20. ActivityStack.completePauseLocked

Step 21. ActivityStack.resumeTopActivityLokced

Step 22. ActivityStack.startSpecificActivityLocked

Step 23. ActivityManagerService.startProcessLocked

Step 24. ActivityThread.main

Step 25. ActivityManagerProxy.attachApplication

Step 26. ActivityManagerService.attachApplication

Step 27. ActivityManagerService.attachApplicationLocked

Step 28. ActivityStack.realStartActivityLocked

Step 29. ApplicationThreadProxy.scheduleLaunchActivity

Step 30. ApplicationThread.scheduleLaunchActivity

Step 31. ActivityThread.queueOrSendMessage

Step 32. H.handleMessage

Step 33. ActivityThread.handleLaunchActivity

Step 34. ActivityThread.performLaunchActivity

Step 35. MainActivity.onCreate


整个应用程序的启动过程要执行很多步骤，但是整体来看，主要分为以下五个阶段：

一. Step1 - Step 11：Launcher通过Binder进程间通信机制通知ActivityManagerService，它要启动一个Activity；

二. Step 12 - Step 16：ActivityManagerService通过Binder进程间通信机制通知Launcher进入Paused状态；

三. Step 17 - Step 24：Launcher通过Binder进程间通信机制通知ActivityManagerService，它已经准备就绪进入Paused状态，于是ActivityManagerService就创建一个新的进程，用来启动一个ActivityThread实例，即将要启动的Activity就是在这个ActivityThread实例中运行；

四. Step 25 - Step 27：ActivityThread通过Binder进程间通信机制将一个ApplicationThread类型的Binder对象传递给ActivityManagerService，以便以后ActivityManagerService能够通过这个Binder对象和它进行通信；

五. Step 28 - Step 35：ActivityManagerService通过Binder进程间通信机制通知ActivityThread，现在一切准备就绪，它可以真正执行Activity的启动操作了。































