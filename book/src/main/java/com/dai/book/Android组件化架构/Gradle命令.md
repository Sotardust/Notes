### 命令
1、查看module的依赖树：
``` 
gradlew module_name ：dependencies 
```
```
+--- project :uiframeworkXX
|    +--- com.android.support:appcompat-v7:22.2.1
|    |    \--- com.android.support:support-v4:22.2.1 -> 26.1.0
|    |         +--- com.android.support:support-compat:26.1.0 -> 27.1.1
|    |         |    +--- com.android.support:support-annotations:27.1.1
|    |         |    \--- android.arch.lifecycle:runtime:1.1.0
|    |         |         +--- android.arch.lifecycle:common:1.1.0
|    |         |         \--- android.arch.core:common:1.1.0
|    |         +--- com.android.support:support-media-compat:26.1.0
|    |         |    +--- com.android.support:support-annotations:26.1.0 -> 27.1.1
|    |         |    \--- com.android.support:support-compat:26.1.0 -> 27.1.1 (*) （表示这个依赖被忽略）
|    |         +--- com.android.support:support-core-utils:26.1.0 -> 27.1.1
|    |         |    +--- com.android.support:support-annotations:27.1.1
|    |         |    \--- com.android.support:support-compat:27.1.1 (*)
|    |         +--- com.android.support:support-core-ui:26.1.0 -> 27.1.1
|    |         |    +--- com.android.support:support-annotations:27.1.1
|    |         |    +--- com.android.support:support-compat:27.1.1 (*)
|    |         |    \--- com.android.support:support-core-utils:27.1.1 (*)
|    |         \--- com.android.support:support-fragment:26.1.0 -> 27.1.1
|    |              +--- com.android.support:support-compat:27.1.1 (*)
|    |              +--- com.android.support:support-core-ui:27.1.1 (*)
|    |              +--- com.android.support:support-core-utils:27.1.1 (*)
|    |              +--- com.android.support:support-annotations:27.1.1
|    |              +--- android.arch.lifecycle:livedata-core:1.1.0
|    |              |    +--- android.arch.lifecycle:common:1.1.0
|    |              |    +--- android.arch.core:common:1.1.0
|    |              |    \--- android.arch.core:runtime:1.1.0
|    |              |         \--- android.arch.core:common:1.1.0
|    |              \--- android.arch.lifecycle:viewmodel:1.1.0
|    \--- com.android.support:support-v4:22.2.1 -> 26.1.0 (*)
+--- project :mvpx
+--- project :network
+--- project :FlycoTabLayout_Lib
|    \--- com.android.support:support-v4:22.1.0 -> 26.1.0 (*)
+--- project :MPChartLib
+--- project :selected_library
+--- org.greenrobot:eventbus:3.0.0

```
2、打印依赖树到txt中
gradlew dependencies > dependencies.txt 或者
gradlew project:dependencies > dependencies.txt

