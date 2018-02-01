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
### 排序数据
    order by 显示如何使用order by 自语句排序结果集
        对单个列或多个列排序结果集。
        按升序或降序对不同列的结果集进行排序。
        允许使用FIELD（）自定义排序
        ASC:升序 DESC：降序
        默认情况下，如果不明确指定ASC或DESC，ORDER BY子句会按照升序对结果集进行排序。
    语法： select 列名1，列名2 from 表名 order by 列名1 [ASC|DESC],列名2 [ASC|DESC]
### 连接表
    Mysql别名：
        Mysql列名的别名
            有时，列的名称是一些表达式，使查询的输出很难理解。要给列一个描述性名称，可以使用列别名。
            语句：SELECT 
                 [column_1 | expression] AS descriptive_name
                 FROM table_name;
                添加列别名 使用：AS 如果别名包含空格需要使用：`descriptive name`
                CONCAT_WS函数用于连接名字和姓氏。
                eg:SELECT CONCAT_WS(', ', lastName, firstname) AS `Full name`FROM employees;
        Mysql表的别名
            使用AS关键字在表名称分配别名
            语句：表名 AS 别名  == table_name AS table_alias
    inner join 子句将一个表中的行与其他表中的行进行匹配，并允许从两个表中查询包含列的行记录 出现在from之后
    inner join 语法：
        SELECT 列名
        FROM 表名1
        INNER JOIN 表名2 ON join_condition1
        INNER JOIN 表名3 ON join_condition2
        ...
        WHERE where_conditions;
    left join  ：生成包含来自连接左侧表中的行的结果集，并使用NULL值来补充不匹配行。
    cross join ：使用来自多个表的行的笛卡尔乘积。
    自连接 ：使用表别名将表连接到自身，并使用其他类型的连接(如INNER JOIN或LEFT JOIN)连接同一表中的行记录。
### 分组数据
    group by 子句 ：如何根据列或表达式将行记录分组到子组。
    group by子句通过列或表达式的值将一组行分组为一个小分组的汇总行记录。 GROUP BY子句为每个分组返回一行。换句话说，它减少了结果集中的行数。
    经常使用group by子句与聚合函数一起使用，如SUM，AVG，MAX，MIN和COUNT。SELECT子句中使用聚合函数来计算有关每个分组的信息。
    group by子句必须出现在FROM和WHERE子句之后
    group by 语句 ：
        SELECT 
            c1, c2,..., cn, aggregate_function(ci)
        FROM
            table
        WHERE
            where_conditions
        GROUP BY c1 , c2,...,cn;
    having 子句来指定一组行或聚合的过滤条件
    HAVING子句通常与GROUP BY子句一起使用，以根据指定的条件过滤分组。如果省略GROUP BY子句，则HAVING子句的行为与WHERE子句类似。
### MySQL子查询，派生表和通用表达式
    MySQL子查询 - 学习如何在另一个查询(外部查询)中嵌套另一个查询语句(内部查询)，并使用内部查询的结果值作为外部查询条件。
        WHERE子句中的MySQL子查询
            MySQL子查询称为内部查询，而包含子查询的查询称为外部查询。 子查询可以在使用表达式的任何地方使用，并且必须在括号中关闭。
            
        如果子查询返回多个值，则可以在WHERE子句中使用IN或NOT IN运算符等其他运算符
        
        FROM子句中的MySQL子查询
            在FROM子句中使用子查询时，从子查询返回的结果集将用作临时表。 该表称为派生表或物化子查询。
        
        MySQL子查询与EXISTS和NOT EXISTS
            当子查询与EXISTS或NOT EXISTS运算符一起使用时，子查询返回一个布尔值为TRUE或FALSE的值。
            
    MySQL派生表 - 介绍派生表概念，并演示如何使用它来简化复杂查询。
        与子查询不同，派生表必须具有别名
        语句：
            SELECT 
                column_list
            FROM
                (SELECT 
                    column_list
                FROM
                    table_1) derived_table_name;
            WHERE derived_table_name.c1 > 0;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    