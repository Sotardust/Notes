面试总结：
1、targetVieSon 与Android 6.0 权限相关  
### targetSdkVersion
1.targetSdkVersion:targetSdk主要是提供向前兼容的作用，什么意思呢？
手机系统4.0的，或者 5.0或者更高，但是编译包的时候用的compileSdk是6.0，但是表现形式得按照targetSdk。
>  1.提供向下兼容。
>  2.确定app的表现行为。
>  3.这允许你在适应新的行为变化之前就可以使用新的API（因为你已经更新了compileSdkVersion打出来的包就是在compileSdkVersion上打出来的）。

2、listView getView中图片加载错乱 原因  
3、下载图片  图片明如何命名使用时间戳，UID等不唯一  
### OkHttp3 下载网络图片
     @Override
          public void onResponse(Call call, Response response) throws IOException {
             //将响应数据转化为输入流数据
              InputStream inputStream=response.body().byteStream();
              //将输入流数据转化为Bitmap位图数据
              Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
              File file=new File("/mnt/sdcard/picture.jpg");
              file.createNewFile();
              //创建文件输出流对象用来向文件中写入数据
              FileOutputStream out=new FileOutputStream(file);
              //将bitmap存储为jpg格式的图片
              bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
              //刷新文件流
              out.flush();
              out.close();
              Message msg=Message.obtain();
              msg.obj=bitmap;
              handler.sendMessage(msg);
          }
    
    
4、事件点击事件  
5、sqlit 线程中同事执行insert语句阻塞，在进程中或进程的线程中使用则崩溃  
6、process可以使用在activity，以及service中  
### android:process=”:XXX”与android:process=”XXX”区别
android:process=":xxx"与android:process="XXX"不仅仅是用来定义当前进程的名字，一般情况各组件的进程名均为当前应用的包名。那有":"开头意味着：
  - 1、进程名字是在当前进程名（即包名）下追加命名，如当前包名为com.terminal.a，那么第一种情况下的进程名就是com.terminal.a:xxx而第二种就直接以"xxx"来命名；
  - 2、":"表示当前新进程为主进程（进程名为包名）的私有子进程，其他应用的组件不可以和它跑在同一个进程中。而后者则是全局的进程，其他应用通过设置相同的ShareUID可以和它跑在同一个进程。