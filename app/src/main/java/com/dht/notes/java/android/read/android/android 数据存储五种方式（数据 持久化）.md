### 一、使用SHaredPreferences存储数据

    明显SharedPreferences数据总是存储在/data/data/<package name>/shared_prefs目录下。 
    存储在.xml 文件中
    
### 二、使用文件存储数据

    核心原理: Context提供了两个方法来打开数据文件里的文件IO流 
    FileInputStream openFileInput(String name); 
    FileOutputStream(String name , int mode),这两个方法第一个参数 用于指定文件名，
    第二个参数指定打开文件的模式。
    
### 三、使用SQLite存储数据

### 四、使用ContentProvider存储数据

### 五、使用网络存储数据