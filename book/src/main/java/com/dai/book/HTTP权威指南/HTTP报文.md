###### 报文类型
请求报文`request message` ：

    起始行： GET /text/hi.txt HTTP/1.1

    首部：Accept: text/*
         Host:www.baidu.com

响应报文`response message`:

    起始行：HTTP/1.0 200 OK

    首部：Content-type：text/plan
         Content-length:19

    主体：Hi！ I'm a message！


##### HTTP常用方法
|方法|描述|是否包含主体|
|-|-|-|
|GET|从服务器获取一份文档|否|
|HEAD|只从服务器获取文档的首部|否|
|POST|向服务器发送需要处理的数据|是|
|PUT|将请求的主体部分存在服务器上|是|
|TRACE|对可能经过代理服务器传送到服务器上的报文进行追踪|否|
|OPTIONS|决定可以在服务器上执行那些方法|否|
|DELETE|从服务器上删除一份文档|否|



##### HTTP 状态码

- 100~199 信息性状态码   
- 200~299 成功状态码 200   
- 300~399 重定向状态码   
- 301 在请求的URL已被移除时使用。 响应的Location首部中应该包含资源现在所处的URL   
- 302 重定向（客户端应该使用Location首部给出的URL来临时定位资源）   
- 400~499 客户单错误状态码   
- 403 Forbidden 说明请求呗服务器拒绝了   
- 404 not found  
- 500~599 服务器错误状态码





