### SystemServer
SystemServer中的服务可以分为7大类

##### 第一类服务
第一类为Android 的和核心类 `ActivityManagerService`,`PowerManagerService`,
`PackageManagerService`,`WindowManagerService`

##### 第二类服务
第二类服务是和通信相关的服务 如wifi的相关服务，Telephone的相关服务，以及以下服务
NetworkManagementService ，NetworkTimeUpdateService ，NetworkPolicyManagerService,
WifiService,ConnectivityService,TelephonyRegistry

##### 第三类服务
第三大类的服务适合系统功能相关的服务，如AudioService ，MountService，UsbService 以及以下服务
ContentService ，AccountManagerService  MountService ， LocationManagerService ，DevicePolicyManagerService

##### 第四类服务
第四类服务是BatteryService VibratorService，AlarmManagerService等服务

##### 第五类服务
第五类服务是 EntropyService ,DiskStatsService,DeviceStorageMonitorService，Watchdog等相对独立的服务

##### 第六类服务
第六类服务是蓝牙服务 ：BluetoothA2dpService ，BluetoothService

##### 第七类服务
第七类服务是UI 方面的服务，如状态栏，通知管理服务等，如以下服务
AppWidgetService InputMethodManagerService ，UiModeManagerService
NotificationManagerService ，WallpaperManagerService


以上服务为Android Framework Java 层的核心




