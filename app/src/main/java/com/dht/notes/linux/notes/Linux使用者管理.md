### linux的账号与用户组
    用户标识符：UID 与 GID
    每个登录的用户至少都会获取两个ID  存储在/etc/passwd中
        UID：用户ID（UserID 简称UID）
        GID：用户组ID（GroupID 简称GID）
    /etc/passwd:管理用户UID/GID重要参数
    /etc/shadow：专门管理密码相关数据
    
    /etc/passwd文件结构：
        每一行都代表一个账号，例如：bin，daemon，adm，nobody 为系统账号
        
        root : x : 0 : 0 : root : /root : /bin/bash  使用":" 分开共7个字段
         [1]  [2] [3] [4]   [5]     [6]     [7]
          
        [1]:账号名称
        [2]:密码 使用"x"替代 并把密码保存到/etc/shadow
        [3]:UID   linux对于UID限制 
                  0(系统管理员) ：当UID为0时，代表这个账号为系统系统管理员root，若把其他账号的UID改为0
                  则该账号也为系统管理员，一个系统上面的管理员不见得只有一个root,不建议有多个系统管理员
                  1~499（系统账号）：保留给系统使用的ID 系统账号有分两种
                        1~99 ：有distributions自行创建的系统账号
                        100~499：若用户有系统账号需求时，可以使用的账号UID
                  500~2^32-1(可登录账号):给一般用户使用
        [4]：GID 其与/etc/group有关，与/etc/passwd差不多
        [5]:用户信息说明列
        [6]:主文件夹
        [7]:Shell
        
    /etc/shadow文件结构
        root:$6$AjM1YfU/$xX6BJOOkqNSP1PTXKdeufHufn85Vo/7h9XOeAlMjRVKxA9nMBW.
        kGgC5eConv58O8JX/W.H2WQEnRGtHoJNKn/:17548:0:99999:7:::
        
    有效与初始用户组：groups、newgrp
        groups :有效与支持用户组的查看
        newgrp：有效用户组的切换
           eg：newgrp 用户组
### 账号管理
    新增与删除用户：useradd
        新建一个用户：dht
        eg：useradd  dht
    修改密码：passwd
        在root 系统管理员下 可以使用：passwd 用户名  
        修改或显示更详细的密码参数 使用：chage [-ldEImMW] 账号名
        
    usermod ：对账号信息数据进行微调
        语法：usermod [-cdegGlsuLU] username
        
    userdel： 删除用户的相关数据
        语法：userdel [-r] username
        -r: 连同用户的主文件夹也一起删除
### 用户功能
    
                        
                  