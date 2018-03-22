### 四大组件是什么与它们的生命周期（及fragment）
四大组件：`Activity`,`Service`,`BroadcastReceiver`, `ContentProvider`

|Activity生命周期|Fragment生命周期|service生命周期|
|-|-|-|-|
|onCreate()|onAttach()|onCreate()|onCreate()|
           |onCreate()|
           |onCreateView()|
           |onActivityCreated()|
|onStart() |onStart()|
|onRestart()|onResume()|onStartCommand()|onBind()|
|onResume()|onPause()|                  |onUnbind()|
|onPause() |onStop()|
|onStop()  |onDestroyView()|
|onDestroy()|onDestroy()|onDestroy()|onDestroy|
           |onDetach()|