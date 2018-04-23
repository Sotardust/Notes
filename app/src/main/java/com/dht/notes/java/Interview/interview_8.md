### Activity 不同模式 创建Activity加入的是哪个栈
stardard ,singleTop ,singleTask,在同一个栈中，singleInstance在独自的栈中
若让以上三种模式位于不同的任务栈中需要使用taskAffinity：给Activity单独指定任务栈名

### RecycleView 与listView的不同之处，以及如何更新一个item
1. RecycleView设置布局管理器LayoutManager 
   1. LinearLayoutManager 垂直布局
   2. GridLayoutManager 网格布局管理器
   3. StaggeredGridLayoutManager 瀑布流式布局管理器
2. RecycleView设置ItemDecoration 控制Item间的间隔，可对其进行绘制
3. 设置ItemAnimator 控制Item增删的动画
4. 不过控制点击事件以及长按事件需要自己写

listView如何更新一条item ，通过getChildAt（int position） 获取要更新的view，
然后获取View视图中要更新的控件再进行设置数据

    

### MVP的使用
google官方给出的MVP结构，contract（契约类）+ fragment（View）+ presenter + module

### 表单模块的module介绍