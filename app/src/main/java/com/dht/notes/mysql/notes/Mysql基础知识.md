 ### 查询数据
     select 语句
     eg： select * from  表名
     select distinct 语句 ：
        查询的数据中可能会有重复行，该语句是为了去除这些重复行并显示
     eg：select distinct 列名 from 表名 order by 列名
### 过滤数据

    where
   
    eg：SELECT 
        lastname, firstname, jobtitle
    FROM
        employees
    WHERE
        jobtitle = 'Sales Rep' AND officeCode = 1;
    操作符：
    = ： 等于，几乎任何数据类型都可以使用它
    <> 或 != ：不等于
    <  : 小于通常使用数字和日期、时间数据类型
    >  : 大于
    <=  :小于或等于
    >=  :大于或等于
    
    AND 和 
    OR  或
    运算符优先级：
        当您在语句中使用多个逻辑运算符时，MySQL会在AND运算符之后再对OR运算符进行求值。
        这称为运算符优先级。优先级：（括号）> AND > OR
    BETWEEN：选择在给定范围值内的值
    LIKE：匹配基于模式匹配的值
        MySQL提供两个通配符，用于与LIKE运算符一起使用，它们分别是：百分比符号 - %和下划线 - _。
        百分比(%)：通配符允许匹配任何字符串的零个或多个字符。
        下划线(_)：通配符允许匹配任何单个字符。
    IN：指定值是否匹配列表中的任何值。
    LIMIT子句 :来限制SELECT语句返回记录的行数
    IS NULL ：检查该值是否为NULL。
    
    