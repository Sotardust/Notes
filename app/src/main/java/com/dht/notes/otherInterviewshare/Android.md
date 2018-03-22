[2018年android面试分享和学习总结](https://juejin.im/entry/5ab059d46fb9a028ba1f4ba0)
### 四大组件是什么与它们的生命周期（及fragment）
四大组件：`Activity`,`Service`,`BroadcastReceiver`, `ContentProvider`

|Activity生命周期|Fragment生命周期|service生命周期|service生命周期|
|-|-|-|-|
|           |onAttach()|||
|onCreate()|onCreate()|onCreate()|onCreate()|
|          |onCreateView()|||
|         |onActivityCreated()|||
|onStart() |onStart()|||
|onRestart()|onResume()|onStartCommand()|onBind()|
|onResume()|onPause()|                  |onUnbind()|
|onPause() |onStop()|||
|onStop()  |onDestroyView()|||
|onDestroy()|onDestroy()|onDestroy()|onDestroy|
|           |onDetach()|||
service 服务参见 notes/android笔记
