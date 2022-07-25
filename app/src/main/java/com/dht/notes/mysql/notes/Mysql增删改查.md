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
    ON DELETE CASCADE 引用操作来执行外键从多个相关表中删除数据。（仅支持使用存储引擎支持外键(如InnoDB)的表上工作）
    删除表：drop table 表名
### 创建，删除数据库
    CREATE DATABASE [IF NOT EXISTS] database_name;
    显示数据库：show databases;
    使用数据库：use 数据库名;
    删除数据库：DROP DATABASE [IF EXISTS] database_name;
### 修改表结构
    使用 alter table 语句修改现有表的结构
    alter table 语句可用来 添加列，删除列，更改列的数据类型，添加主键，重命名表等
    语法：ALTER TABLE table_name action1[,action2,…]
    添加主键语法：ALTER TABLE table_name ADD PRIMARY KEY(primary_key_column);
    添加列语法：（MySQL允许通过指定FIRST关键字将新列添加到表的第一列。 
                它还允许您使用AFTER existing_column子句在现有列之后添加新列。
                如果没有明确指定新列的位置，MySQL会将其添加为最后一列。）
        ALTER TABLE table
        ADD [COLUMN] column_name_1 column_1_definition [FIRST|AFTER existing_column],
        ADD [COLUMN] column_name_2 column_2_definition [FIRST|AFTER existing_column],
        ...;
        eg：ALTER TABLE vendors ADD COLUMN vendor_group INT NOT NULL;
    删除列语法：ALTER TABLE table
               DROP COLUMN column;       
               DROP COLUMN column_2,
               …;
    重命名表：可使用rename table语句，alter table 
        （不能使用RENAME TABLE语句来重命名临时表，但可以使用ALTER TABLE语句重命名临时表。）
        rename table语法：RENAME TABLE old_table_name TO new_table_name;
        alter  table语法：ALTER TABLE old_table_name RENAME TO new_table_name;
    使用MySQL ALTER TABLE语句来设置列的自动递增属性
        ALTER TABLE tasks
        CHANGE COLUMN task_id task_id INT(11) NOT NULL AUTO_INCREMENT;
