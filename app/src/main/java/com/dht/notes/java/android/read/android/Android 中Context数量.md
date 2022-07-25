### Android中Context数量个数
Context一共有Application、Activity和Service三种类型，
因此一个应用程序中Context数量的计算公式就可以这样写：
    Context数量 = Activity数量 + Service数量 + ApplicationContext 
### ApplicationContext，getApplication(),getApplicationContext()区别？
    Context理解（没答完整）（ getApplication()是用来获取Application实例的，但是该方法只在Activity和Service中才能调用；
    在一些其他的地方，比如说当我们在BroadcastReceiver中也想获取Application实例，这时就需要用getApplicationContext()方法）
    
[参考文章：Android中Context数量个数](http://blog.csdn.net/guolin_blog/article/details/47028975)