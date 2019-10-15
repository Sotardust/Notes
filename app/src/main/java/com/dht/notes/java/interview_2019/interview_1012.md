### HashMap
### aidl 标志位
### view setContentView Activity window view 联系
### merge 使用
### handle 
### 内存泄漏

### 设计模式 代理模式


### recycleview listview 

### ArrayList sublist 添加
父list 不能进行删除，添加，子list可以进行删除添加， 
fail-fast 机制是java集合(Collection)中的一种错误机制
。当多个线程对同一个集合的内容进行操作时，就可能会产生fail-fast事件。
例如：当某一个线程A通过iterator去遍历某集合的过程中，
若该集合的内容被其他线程所改变了；那么线程A访问集合时，
就会抛出ConcurrentModificationException异常，产生fail-fast事件。
要了解fail-fast机制，我们首先要对ConcurrentModificationException 异常有所了解。
当方法检测到对象的并发修改，但不允许这种修改时就抛出该异常。
同时需要注意的是，该异常不会始终指出对象已经由不同线程并发修改，
如果单线程违反了规则，同样也有可能会抛出改异常。
诚然，迭代器的快速失败行为无法得到保证，它不能保证一定会出现该错误，
但是快速失败操作会尽最大努力抛出ConcurrentModificationException异常，
所以因此，为提高此类操作的正确性而编写一个依赖于此异常的程序是错误的做法，
正确做法是：ConcurrentModificationException 应该仅用于检测 bug。

### message obtain 

### viewStub 使用过后取消 使用

### 自定义view 为什么要写全名称





