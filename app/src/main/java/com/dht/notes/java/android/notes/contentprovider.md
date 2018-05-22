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
    
    
    
    