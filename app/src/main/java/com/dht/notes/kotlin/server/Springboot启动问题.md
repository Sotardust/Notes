### spring boot could not resolve placeholder in string value 问题解决方法
    在DataSourceConfig 中配置如下代码
    @EnableTransactionManagement
    // 自己添加的，指定配置文件
    @PropertySource(value = "classpath:application-dev.properties", ignoreResourceNotFound = true)