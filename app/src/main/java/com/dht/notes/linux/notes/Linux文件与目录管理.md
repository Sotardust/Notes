### 
    mkdir 创建目录   
    mkdir -p directory ：它的作用就是递归创建目录，即使上级目录不存在。
                         还有一种情况就是如果你想要创建的目录存在的话，会提示报错，然后你加上-p参数
    rmdir 删除目录
    rm 删除目录或者文件 rmdir 只能删除目录但不能删除文件，要想删除一个文件，则要用rm命令了
### 查看文件内容
    cat 比较常用的一个命令，即查看一个文件的内容并显示在屏幕上。
        -n 查看文件时，把行号也显示到屏幕上。
        -A 显示所有东西出来，包括特殊字符
    echo "456123" >> test.txt
        上例中出现了一个”>>”，这个符号跟前面介绍的”>”的作用都是重定向，即把前面输出的东西输入到后边的文件中，
        只是”>>”是追加的意思，而用”>”，如果文件中有内容则会删除文件中内容，而”>>”则不会。
    tac 其实是cat的反写，同样的功能也是反向打印文件的内容到屏幕上。    
    more 也是用来查看一个文件的内容。当文件内容太多，一屏幕不能占下，而你用cat肯定是看不前面的内容的，
         那么使用more就可以解决这个问题了。
         当看完一屏后按空格键继续看下一屏。但看完所有内容后就会退出。如果你想提前退出，只需按q键即可。
    less 作用跟more一样，但比more好在可以上翻，下翻。空格键同样可以翻页，而按”j”键可以向下移动（按一下就向下移动一行），
         按”k”键向上移动。在使用more和less查看某个文件时，你可以按一下”/” 键，然后输入一个word回车，
         这样就可以查找这个word了。如果是多个该word可以按”n”键显示下一个。
         另外你也可以不按”/”而是按”?”后边同样跟word来搜索这个word，唯一不同的是，”/”是在当前行向下搜索，
         而”?”是在当前行向上搜索
    head head后直接跟文件名，则显示文件的前十行。如果加 –n 选项则显示文件前n行。
    tail 和head一样，后面直接跟文件名，则显示文件最后十行。如果加-n 选项则显示文件最后n行。
    
    文件内容查阅：
        cat ：右第一行开始显示文件内容
        tac：最后一行开始显示文件内容
        nl ：显示时顺便输入行号
        more：一页一页地显示文件内容
        less ：与more类似，但是比more可往前翻页
        head ：只看头几行
        tail ：只看尾几行
        od：以二级制的方式读取内容
        
        cat ：concatenaate （连续）简写
        cat -n ：也可显示行号 与-b有区别空白行也显示行号
        more  一页一页翻看
        space 向下翻页
        enter 向下滑动一行
        /字符串 向下查询"字符串"关键字 、
        f  ：显示出文件名以及 显示的行数
        q ：离开
        b或者ctrl-b 往回翻页
        
        head  [-n number] 文件默认显示10行
        head -n 20 显示前20行
        tail -n 10 取出后10行
             -f 表示持续检测后面所接的文件名
             按 ctrl -c 才会结束 tail的检测
             
             只显示文本10到20行
             od [-t TYPE] 文件
             od -t a 默认 ，c使用ascii 字符输出
                   d 十进制
                   f 浮点数
                   o 八进制
                   x 十六进制
             eg： od -t occ txt
        touch [-acdmt] 文件
            -a ：修改访问时间
            -c ：仅修改文件的的时间若不存在则不创建新文件
            -d ：后面可接与修改的日期，也可使用 --date = “日期或时间”
            ";": 代表连续命令的别名
            -m ：修改mtime
            -t ： 后可接欲修改的时间 ，格式[YYMMDDhhmm]
            
### Linux 文件权限

    -rw-r--r--    1     root   root   42304     sep 4 18:36   install.log
    [    1   ]   [2]     [3]    [4]    [5]      [    6    ]    [   7    ]
       权限      连接    所有者  用户组  文件容量    修改日期        文件名
    
    [1]：代表这个文件的类型与权限
    [2]: 代表有多少我呢间连接到此节点上（i-node）
    [3]: 该文件或目录的所有账号
    [4]: 表示这个文件的所属用户组
    [5]: 文件容量大小，默认单位为B
    [6]: 为文件的创建文件日期或者是最近的修改日期
    [7]: 为该文件 "."则代表为隐藏文件
    
        -      r  w  x    r  w  x   ---
       [1]     [  2  ]    [  3  ]   [4]
    r：可读 w：可写 x：可执行
    [1]:文件类型
    [2]:文件所有者的权限
    [3]:文件所属用户组的权限
    [4]:其他人对此文件的权限
    
    第一个字符代表这个文件的是目录，文件，或者是连接文件等 
    [d]: 目录 ，directory
    [-]: 文件
    [l]: 连接文件（linkfile）
    [b]: 设备文件里的可供存储的接口设备 block 块设备文件
    [c]: 设备文件里的穿行接口设备例如键盘，鼠标 character 设备文件
    
### 改变文件属性与权限
    chgrp ：改变文件所属用户组
    chown ：改变文件所有者
    chmod ：改变文件的权限
    
    例： chgrp [-R] dirname/filename 
        -R:进行递归更改即联通子目录下的所有文件，目录
    
        chown [-R] 账号名称 文件或目录
        chown [-R] 账号名称：组名 文件或者目录
        
        cp   test      test1
           [源文件]   [目标文件]
        
    cp ：会复制执行者的属性与权限
    
    数字类型改变文件权限
        r：4
        w：2
        x：1
        
        owner = rwx = 4+2+1 =7
        group = rwx = 4+2+1 =7 
        others= --- = 0+0+0 =0
        chmod 777.bashrc
        
        chmod [-R] xyz 文件名或目录
        
        符号类型改变文件权限
        9个权限 分别为：user ，group，other 3种身份权限。a代表：all
        
        chmod u =rwx，go =rx .bashrc
        chmod a+w .bashrc  改变权限 ==> -rwxrwxrwx 
        
### 文件种类与扩展名
    文件种类
    管道 FIFO ，pipe  FIFO 是 first-in-first-out 缩写
    
    Linux 文件长度限制
        单一文件或目录的最大容许文件名为255个字符
        包含完整路径名称及目录的完整文件名为4096个字符
    Linux 目录配置标准FHS 
        FHS：Filesystem Hierarchy standard
        
        根目录（/） 所在分区应该 越小越好
        
        /usr :Unix Software Resource
              Unix 操作系统软件资源
### Linux 文件，目录管理
    目录与路径：（path）
    绝对路径： /
    相对路径：cd../
    
    目录相关操作：
        cd ~ 回到自己的主文件夹
        cd - 回到刚才的那个目录
        cd： change directory 缩写
        
        pwd ：显示当前的工作目录
        pwd -p ：显示当前工作路径
        pwd ：print working directory
        
        mkdir ： 新建新目录
        mkdir -p ：创建多级新目录
        rmdir -p ：删除"空"的目录
        
        echo ：显示打印  eg： echo $PATH   打印路径
        
    查看文件与目录 ：ls ，ll
        ls -a ：全部文件
        ls -d ：仅列出目录本身
        ls -l ：列出 长数据串
        
        呈现文件完整时间
            ：ls -al --full-time ~
            
    复制，删除与移动：
        cp , rm , mv
        cp [-adfi|prsu] 源文件 目标文件
        cp -a 连同权限一起复制
        cp -i 目标文件已存在时，覆盖会先询问操作
        cp -r 递归持续复制，用于目录的复制行为（常用）
        
        rm [-fir] 文件目录
            -f ：强制删除
            -i ：删除是给出提示
            -r ：递归删除
        删除带有 -开头的文件
        touch ./-aaa- 偷吃可以创建空文件
        rm      -aaa- "-" 为参数导致系统误判
        正确： ./-aaa- 
        

        mv [-fiu] 文件或目录 目录
        
        获取文件的名与目录名称
            basename 源文件（路径） 取得最后文件名
            dirname  获取目录名
### 文件默认权限
    文件默认权限 ：umask 指该默认值需要减掉的的值
    文件隐藏属性 chattr lsattr（查看隐藏属性）
    chattr [+-=] [asacdistu] 文件或目录名称
    chattr +a 只能增加数据
           +i 它可以让一个文件 不能被删除改名设置
           -i 取消属性
           参考链接：http://www.ha97.com/5172.html
    文件特殊权限
        4 SUID setUID
        2 SGID
        1 SBIT
        
        查看文件类型： file
                file 文件
        命令与文件的查询
        脚本文件名的查询
            which （寻找"执行文件"）
            which [-a] command
            -a 将所有由 PATH 目录中可以执行的文件文件找到找到并列出
        find 命令 （参考链接：https://www.cnblogs.com/piwefei/p/5434037.html）
            find [目录] -name filename 查找名为filename的文件
            语法：
                -name   filename             #查找名为filename的文件
                -perm                        #按执行权限来查找
                -user    username             #按文件属主来查找
                -group groupname            #按组来查找
                -mtime   -n +n                #按文件更改时间来查找文件，-n指n天以内，+n指n天以前
                -atime    -n +n               #按文件访问时间来查GIN: 0px">
                -ctime    -n +n              #按文件创建时间来查找文件，-n指n天以内，+n指n天以前
                -type TYPE （b：设备文件，c   d：目录， l ：连接文件，f：正规文件，s: socket及p：FIFO属性） 
                eg：find -type d 按目录查找
                
            eg：
                find -name april* -print   在当前目录下查找以april开始的文件 并打印出来
                find -name april* fprint file  在当前目录下查找以april开始的文件，并把结果输出到file中
                find -name ap* -o -name may*   查找以ap或may开头的文件
                find /mnt -name tom.txt -ftype vfat   在/mnt下查找名称为tom.txt且文件系统类型为vfat的文件
                find . -size +1000000c  -print        #查长度大于1Mb的文件
                
                
        文件名的查找 whereis 寻找特定文件
            whereis [-bmsu]文件或者目录名
                -b ：只查找二进制格式文件，可执行文件
                -m ：只找在说明文件manual路径下的文件
                -s ：只找源文件
                -u ：查找不在上述中的其他特殊文件
                
            locate，whereis 均是从数据库中查找
            locate 应 updatedb
### Linux磁盘与文件管理系统
    硬盘醉成与分区
        扇区 Sector 最小的物理存储单位 每个扇区为512 bytes
        柱面 Cylinder 是分区partition的最小单位
        第一个扇区最重要包含硬盘主引导记录 Masterbootrecod
        MBR             分区表 partition table
        446bytes        64byte
        
        /dev/sb[a-p][1-15] 为SCSI，SATA，USB，Flash 磁盘
        /dev/hd[a-d][1-63] 为IDE接D的磁盘文件名
        
    文件系统的简单操作
        df [-ahikHTm] [目录或文件名] 列出文件系统的整体磁盘使用量
        
        -h ：以GB，MB，KB 等格式自行显示
        -i ： 不用硬盘容量而以inode的数量显示
        
        du ：评估文件系统的磁盘使用量  （常用评估目录所占容量）
      
        du [-ahskm] 文件或目录名称
            -s ：列出总量而不列出每个割裂的目录占用容量
            
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        