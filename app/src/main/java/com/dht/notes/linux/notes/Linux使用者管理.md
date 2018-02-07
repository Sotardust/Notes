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
    finger ：查询用户相关信息
        语法：finger [-sm] username
        -s: 仅列出用户的账号、全名、终端机代号与登录时间
        -m： 列出与后面接的账号相同者，而不是利用部分对比（包括全名部分）
    chfn ：change finger 
        语法：chfn [-foph] 账号名
    chsh ： change shell 简写
        语法： chsh [-ls]
        -l:列出目前系统上面可用的shell 其实就是/etc/shell的内容
    id ：查询某人或者自己相关的UID/GID等相关信息
        也可用与查看系统上是否有dht这个账号
### 新增与删除用户组
    用户组的内容与 ：/etc/group,/etc/gshadow有关
    groupadd,groupmod,groupdel 与useradd,usermod,userdel 类似
    gpasswd：用户组管理员功能
        语法：
            gpasswd groupname
            gpasswd [-A user1,...] [-M user3,...] groupname
            gpasswd [-rR] groupname
            
                若没有任何参数时，表示给予groupname一个密码（/etc/gshadow）
            -A ：将groupname的主控权交由后面的用户管理（该用户组的管理员）
            -M : 将某些账号加入这个用户组中
            -r ：将groupname的密码删除
            -R ：将groupname的密码栏失效
            
            gpasswd [-ad] user groupname
            
            -a ：将某位用户加入到groupname这个用户组中
            -d ：将某位用户删除出groupname这个用户组中
### 主机的具体权限规划：ACL的使用
    ACL是Access Control List的缩写，主要目的是提供传统的owner、group、others
    和read、write、execute权限之外的具体权限设置。
    ACL可以针对单一用户、文件或目录来进行r、w、x的权限设置
    
    ACL：针对用户（user）、用户组（group）设置权限，
        默认属性（mask）：还可以在该目录下在新建新文件/目录时设置新数据的默认权限
        
    ACl的设置机巧 ：getfacl ，setfacl
    
    
### 用户身份切换
    su [-lm] [-c命令] [username]
    -,-l :代表使用login-shell的变量文件读取方式来登录系统
    
    sudo ：仅有/etc/sudoers内的用户才能够执行sudo这个命令
    
    sudo 默认仅有root能使用
    visudo ：只是利用vi 将/etc/sudoers文件调出来修改而已
### PAM 模块简介
    PAM 可以说是一套应用程序编程接口（Application Programming Interface API）
        它提供一连串的验证机制，只要用户将验证阶段的需求告知PAM后，PAM就能够回报用户验证的结果
        （成功或失败）
        
### Linux主机上的用户信息传递
    查看用户：w、who、last、lastlog
            也可以使用id、finger
    
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
    
        
            
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    
                        
                  