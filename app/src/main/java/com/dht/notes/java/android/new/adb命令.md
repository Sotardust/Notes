### 参考链接：https://www.jianshu.com/p/5b21377cf69b
### 查看应用列表
    adb shell pm list package
    eg：
        E:\WorkFile\GitHub\RxJava2Examples>adb shell pm list package
        WARNING: linker: /system/lib/libhoudini.so has text relocations. This is wasting memory and prevents security hardening. Please fix.
        package:com.example.android.livecubes
        package:com.android.providers.telephony
        package:com.android.providers.calendar

    adb shell ls /data/data/
    eg:
        E:\WorkFile\GitHub\RxJava2Examples>adb shell ls /data/data/
        cn.wongxming.contact
        com.amaze.filemanager
        com.android.backupconfirm
        com.android.bluetooth
### adb 启动/停止 应用程序
    adb shell am start -n [packageName/StartActivity]

