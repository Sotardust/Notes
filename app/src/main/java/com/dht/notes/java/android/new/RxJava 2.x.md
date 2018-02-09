### 响应式编程
    响应式标称是一种基于异步数据流概念的编程模式，数据流就像一条河
    ：他可以被观测，被过滤，被操作，或者违心的消费者与另一条流合并为一条新的流
    
    响应式编程的一个关键概念是事件。
        事件可以被等待，可以触发过程，也可以触发其他事件。
        
### 接口变化
    Publisher ：可以发出一系列的事件
    Subscriber ：负责处理这些事件
    Subscription
    Processor
    
### 背压
    背压是指在异步场景中，被观察者发送时间速度远快于观察者的处理速度的情况下，
    一种告诉上游的被观察者降低发送速度的策略
    简而言之 背压是流速控制的一种策略
    
### 观察者模式
    Observable（被观察者）/Observer(观察者)
    Flowable（被观察者）/Subscriber（观察者）  支持背压
### 操作符
    参考连接：https://www.jianshu.com/p/0cd258eecf60
    map：
        map 操作符可以将一个 Observable 对象通过某种关系转换为另一个Observable 对象。
        在 2.x 中和 1.x 中作用几乎一致，不同点在于：2.x 将 1.x 中的 Func1 和 Func2 改为了 Function 和 BiFunction。
        
    concat：
        concat 可以做到不交错的发射两个甚至多个 Observable 的发射事件，
        并且只有前一个 Observable 终止(onComplete) 后才会订阅下一个 Observable。 
    flatMap：
        实现多个网络请求依次依赖    
        flatMap与concatMap区别：前者无序后者有序
    zip ：
        实现多个接口数据共同更新UI
        zip 操作符可以将多个 Observable 的数据结合为一个数据源再发射出去。
    interval ：
        采用 interval 操作符实现心跳间隔任务
    distinct ：去除重复
        eg：
            Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                            .distinct()
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(@NonNull Integer integer) throws Exception {
                                    mRxOperatorsText.append("distinct : " + integer + "\n");
                                    Log.e(TAG, "distinct : " + integer + "\n");
                                }
                            });
    filter ：
        过滤器可以接受一个参数，让其过滤掉不符合我们条件的值
        eg：
           Observable.just(1, 20, 65, -5, 7, 19)
                           .filter(new Predicate<Integer>() {
                               @Override
                               public boolean test(@NonNull Integer integer) throws Exception {
                                   return integer >= 10;
                               }
                           }).subscribe(new Consumer<Integer>() {
                       @Override
                       public void accept(@NonNull Integer integer) throws Exception {
                           mRxOperatorsText.append("filter : " + integer + "\n");
                           Log.e(TAG, "filter : " + integer + "\n");
                       }
                   });
    buffer ：
        buffer 操作符接受两个参数，buffer（count，skip），作用是将Observable中的数据按skip（步长）
        分成做大不超过count的buffer，然后生成一个Observable。
        eg：
        Observable.just(1, 2, 3, 4, 5)
                        .buffer(3, 2)
                        .subscribe(new Consumer<List<Integer>>() {
                            @Override
                            public void accept(@NonNull List<Integer> integers) throws Exception {
                                mRxOperatorsText.append("buffer size : " + integers.size() + "\n");
                                Log.e(TAG, "buffer size : " + integers.size() + "\n");
                                mRxOperatorsText.append("buffer value : ");
                                Log.e(TAG, "buffer value : " );
                                for (Integer i : integers) {
                                    mRxOperatorsText.append(i + "");
                                    Log.e(TAG, i + "");
                                }
                                mRxOperatorsText.append("\n");
                                Log.e(TAG, "\n");
                            }
                        });
    timer : 在RxJava 2.x 中此功能交给了interval
            需要在onDestroy（）中注销 使用 disposable 去注销
        
    doOnNext（非操作符）
        作用：让订阅者在接受数据之前做些有意思的事情
        eg:
        Observable.just(1, 2, 3, 4)
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                mRxOperatorsText.append("doOnNext 保存 " + integer + "成功" + "\n");
                                Log.e(TAG, "doOnNext 保存 " + integer + "成功" + "\n");
                            }
                        }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mRxOperatorsText.append("doOnNext :" + integer + "\n");
                        Log.e(TAG, "doOnNext :" + integer + "\n");
                    }
                });
        输出：
            02-08 22:03:25.073 2626-2626/? E/HomeActivity: doOnNext 保存 1成功
            02-08 22:03:25.073 2626-2626/? E/HomeActivity: doOnNext :1
            02-08 22:03:25.073 2626-2626/? E/HomeActivity: doOnNext 保存 2成功
            02-08 22:03:25.073 2626-2626/? E/HomeActivity: doOnNext :2
            02-08 22:03:25.073 2626-2626/? E/HomeActivity: doOnNext 保存 3成功
            02-08 22:03:25.073 2626-2626/? E/HomeActivity: doOnNext :3
            02-08 22:03:25.073 2626-2626/? E/HomeActivity: doOnNext 保存 4成功
            02-08 22:03:25.073 2626-2626/? E/HomeActivity: doOnNext :4
    skip :
        接受一个龙行参数count，代表跳过count个数目开始接收
    take ： 
        接受一个long型参数count，代表至多接收count个数据
    just ：
        就是一个简单大二发射器依次调用onNext（）方法
    Single ： 只接收一个参数
    
    debounce ：去除发送频率过快的项
    
    defer：
        每次订阅都会创建一个新的Observable，并且如果没有被订阅，就不会产生新的Observable。
        
    last ：
        last操作符仅取出可观察到最后一个值，或者是满足某些条件的最后一项
    merge ：
        作用是把多个Observable结合起来，接收可变参数，也支持迭代器集合。
        它和concat的区别在于，不用等到发射器A发送完所有的时间贼进行发射器B的发送
    reduce ：
        reduce操作符每次用一个方法处理一个值，可以有一个seed作为初始值
    scan :
        scan 操作符作用合上面的reduce一致，唯一区别是reduce是个追求结果，二scan始终如一地吧每个步骤都输出
        
**********************************************Rxjava**********************************************
参考链接：http://gank.io/post/560e15be2dca930e00da1083#toc_1
### RxJava 的观察者模式   
    RxJava有四个基本概念：
        Observable（被观察者）
        observer（观察者）
        subscriber（订阅）
        event（事件）
    四者之间的关系：Observable和Observer通过subscribe（） 方法实现订阅，
                  从而Observable可以在需要的时候发出事件通知observer
                  
    RxJava的事件回调方法：
        onCompleted()
        onNext()
        onError()
        
### RxJava提供了快捷创建事件队列
    just（T...）:将参数依次发送出来
    eg：
        Observable observable = Observable.just("Hello", "Hi", "Aloha");
        // 将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();
    
    from(T[])/from(Iterable<? extends T>) :将传入的数组或Iterable 拆分成具体对象后，依次发送出来
    eg：
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable = Observable.from(words);
        与just类似
    
### Subscribe（订阅） 
    支持不完整定义回调
    RxJava提供多个ActionX形式的接口（eg：Action2，Action3）
        ActionX 包装的是没有返回值的方法
        FuncX 包装的是有返回值的方法
### 线程控制 Scheduler（一）
    在不指定线程的情况下，RxJava遵循的是线程不变的原则，
    即：在那个线程调用subscribe() ，就在哪个线程生产事件；
    在哪个线程生产事件就在哪个线程消费事件。如果需要切换线程就需要用到Scheduler（调度器）
    内置Scheduler：
    Schedulers.immediate()
        直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
    Schedulers.newThread(): 
        总是启用新线程，并在新线程执行操作。   
    Schedulers.io(): 
        I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
        行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，
        可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。
        不要把计算工作放在 io() 中，可以避免创建不必要的线程
    Schedulers.computation(): 
        计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，
        例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。
        不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU
    Android 还有一个专用的 AndroidSchedulers.mainThread()，
            它指定的操作将在 Android 主线程运行。
    
    subscribeOn()：
        指定subscribe() 所发生的线程即Observable。OnSubscribe被激活是所处的线程
        或者叫做事件产生的线程
    observeOn(): 
        指定Subscriber所运行在的线程。或者叫做事件消费的线程
    
### map（）操作符
    map(): 事件对象的直接变换
    eg：
       Observable.just("images/logo.png") // 输入类型 String
           .map(new Func1<String, Bitmap>() {
               @Override
               public Bitmap call(String filePath) { // 参数类型 String
                   return getBitmapFromPath(filePath); // 返回类型 Bitmap
               }
           })
           .subscribe(new Action1<Bitmap>() {
               @Override
               public void call(Bitmap bitmap) { // 参数类型 Bitmap
                   showBitmap(bitmap);
               }
           });
       map() 方法将参数中的 String 对象转换成一个 Bitmap 对象后返回，而在经过 map() 方法后，
       事件的参数类型也由 String 转为了 Bitmap。这种直接变换对象并返回的，是最常见的也最容易理解的变换。
       不过 RxJava 的变换远不止这样，它不仅可以针对事件对象，还可以针对整个事件队列，这使得 RxJava 变得非常灵活。
### flatMap（）操作符    
    flatMap（）和map()相同点：把传入的参数转化之后返回另一个对象
    flatMap（）中返回的是个Observable对象，并且这个Observable对象并不是
               被直接发送到Subscriber的回调方法。
    flatMap（）原理：
        1、使用传入的事件对象创建一个Observable对象
        2、并不发送这个Observable，而是将它激活，于是它开始发送事件
        3、每一个创建出来的Observable发送的事件都被汇入同一个Observable，而这个Observable负责将这些事件
           统一交给Subscriber的回调方法
           
### 变换的原理：lift（）
### compose: 对 Observable 整体的变换
### 线程控制：Scheduler
           
    
    
        
    
    

        
    