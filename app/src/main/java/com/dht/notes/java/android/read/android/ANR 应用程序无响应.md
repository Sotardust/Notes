    在android中Activity的执行时间为5秒，BroadCastReceiver 最长执行时间为10秒
### 引发ANR的原因
    1、在五秒内没有响应输入事件（按键按下，屏幕触摸）
    2、BroadCastReceiver在10秒内没有执行完毕 
    造成上述两点的原因
    在主线程中做了耗时的操作，如下载（异常，网络请求） 数据库操作，高耗时的计算
    server在特定事件内无法完成（20s）
### 避免ANR
    1、减少在主线程中做耗时操作，尽量在子线程中去执行耗时任务，比如使用handler，message
    2、采用AsynTask（异步任务的凡是，底层是 handler+message有所区别线程池）
### 如何分析ANR
    1、查看log日志   查看cpu负载，与cpu使用率
    2、trace.text文件存在的ANR信息 （DB文件）
    3、查看代码
    4、仔细查看ANR的成因（iowait?block?memoryleak?）
    
    