## ContentProvider
### 参考连接：https://juejin.im/entry/597811f6f265da6c2e0fc6fd
### 定义
即内提供者四大组件之一
### 作用
进程间进行 数据交互与共享 即跨进程通信
### 原理
ContentProvider 的底层是采用Android中的Binder机制
### 具体使用方法
#### 统一资源标识符（URI）
定义：Uniform Resource Identifier 即统一资源标识符
作用：唯一标识
     外界通信通过URI找到对应的ContentProvider 与其中的数据，在进行数据操作
具体使用
     URI分为系统预置与自定义 分别对应系统内置的数据如通讯录，日程表等等
     自定义数据库
设置URI
     Uri uri = Uri.parse("content://com.carson.provider/User/1") 
     // 上述URI指向的资源是：名为 `com.carson.provider`的`ContentProvider` 中表名 为`User` 中的 `id`为1的数据
### 组织数据方法
ContentProvider主要以表格的形式组织数据（同时也支持文件数据，只是表格形式用的比较多）
每个个表格中包含多张表，每张表包含行和列 分别对应记录与字段（同数据库）
### 主要方法
进程间共享数据的本质：添加、删除、获取以及修改（更新）数据
public Uri insert（Uri uri，ContentValues values）
public int delete(Uri uri, String selection, String[] selectionArgs)
public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,  String sortOrder)
ContentProvider类并不会直接与外部进程交互。而是通过ContentResolver类　 

### ContentProvider、ContentResolver、ContentObserver 之间的关系。

ContentProvider：

四大组件的内容提供者，主要用于对外提供数据

实现各个应用程序之间的（跨应用）数据共享，比如联系人应用中就使用了ContentProvider，你在自己的应用中可以读取和修改联系人的数据，不过需要获得相应的权限。其实它也只是一个中间人，真正的数据源是文件或者SQLite等

一个应用实现ContentProvider来提供内容给别的应用来操作，通过ContentResolver来操作别的应用数据，当然在自己的应用中也可以。

ContentResolver：

内容解析者，用于获取内容提供者提供的数据

ContentResolver.notifyChange（uri）发出消息

ContentObserver：

内容监听器，可以监听数据的改变状态

目的是观察（捕捉）特定Uri引起的数据库的变化，继而做一些相应的处理，它类似于数据库技术中的触发器（Trigger），当ContentObserver所观察的Uri发生变化时，便会触发它。触发器分为表触发器、行触发器，相应地ContentObsever也分为表ContentObserver、行ContentObserver，当然这是与它所监听的Uri MIME Type有关的

ContentResolver.registerContentObserver()监听消息

联系：

简单一句话来描述就是：使用ContentResolver来获取ContentProvider提供的数据，同时注册ContentObserver监听Uri数据的变化

    
    
    
    