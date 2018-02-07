### MD5（hash加密）
     MD5以512位分组来处理输入的信息，且每一分组又被划分为16个32位子分组，经过了一系列的处理后，
     算法的输出由四个32位分组组成，将这四个32位分组级联后将生成一个128位散列值。
     md5算法主要运用在数字签名、文件完整性验证以及口令加密等方面。
     
### RSA 
    能用于数据加密也能用于数字签名的算法
    RSA的安全性依赖大数分解，由于进行的收拾大数计算，速度较慢
     
### DES
    非机密数据的正式数据加密标准
    （Data Encryption Standard） 多用于pos ATM 磁卡及智能IC卡上
### 对称加密和分组加密中的四种模式
    对称加密：文件加密和解密使用相同的密钥
    非对称加密：需要两个密钥：公开密钥（publickey）和私有密钥
    Hash算法：Hash算法常用在不可还原的密码存储、信息完整性校验等。
    ECB ,CBC CFB OFB
    ECB CBC 为链加密模式
    AES为分组加密 也成块加密
    MD5 SHA1 Base64 RSA AES DES IDEA
    
    对称加密    ：DES、3DES、Blowfish、IDEA、RC4、RC5、RC6和AES
    Hash加密算法：MD2、MD4、MD5、HAVAL、SHA
    非对称加密  : RSA、ECC（移动设备用）、Diffie-Hellman、El Gamal、DSA（数字签名用）
     