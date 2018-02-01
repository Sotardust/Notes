### insert 语句
    MySQL INSERT语句允许您将一行或多行插入到表中
    语法：
        INSERT INTO table(column1,column2...)
        VALUES (value1,value2,...);
    具有SELECT子句的MySQL INSERT
        在MySQL中，可以使用SELECT语句返回的列和值来填充INSERT语句的值。 此功能非常方便，因为您可以使用INSERT和SELECT子句完全或部分复制表
        语法：
            INSERT INTO table_1
            SELECT c1, c2, FROM table_2;    
            
        MySQL INSERT与ON DUPLICATE KEY UPDATE
### update 语句
    UPDATE语句来更新表中的现有数据。也可以使用UPDATE语句来更改表中单个行，一组行或所有行的列值。
    语法：
      UPDATE [LOW_PRIORITY] [IGNORE] table_name 
      SET 
          column_name1 = expr1,
          column_name2 = expr2,
          ...
      WHERE
          condition;
### delete 语句
    从表中删除数据 语法：
      DELETE FROM table_name WHERE condition;  （如果省略where语句  则delete将删除表中的所有行）
    DELETE JOIN语句来从多个表中删除数据 
       语句：
            DELETE T1, T2
            FROM T1
            INNER JOIN T2 ON T1.key = T2.key
            WHERE condition
    MySQL DELETE与LEFT JOIN子句     
       语法：  
           DELETE T1 
           FROM T1
                   LEFT JOIN
               T2 ON T1.key = T2.key 
           WHERE
               T2.key IS NULL;
    TRUNCATE TABLE 语句删除表中的所有数据。
        语法：TRUNCATE TABLE 表名== truncate table 表名
    删除表：drop table 表名
### 创建，删除数据库
    CREATE DATABASE [IF NOT EXISTS] database_name;
    显示数据库：show databases;
    使用数据库：use 数据库名;
    删除数据库：DROP DATABASE [IF EXISTS] database_name;
    
    