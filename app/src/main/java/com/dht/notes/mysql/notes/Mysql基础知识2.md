### 使用set操作符
    union 和union all 使用union 和 union all 操作符将两个或多个select 语句的结果集合合并到一个结果集中
    INTERSECT运算符是一个集合运算符，它只返回两个查询或更多查询的交集。（Mysql 不支持 intersect 操作符）
        （列的顺序和数量必须相同。相应列的数据类型必须兼容或可转换。）
        语法：(SELECT column_list FROM table_1) INTERSECT (SELECT column_list FROM table_2);
### replace语句
    replace语句是标准SQL的MySQL扩展，工作原理如下：
        如果给定行数据不存在，那么MySQL REPLACE语句会插入一个新行。
        如果给定行数据存在，则REPLACE语句首先删除旧行，然后插入一个新行。 在某些情况下，REPLACE语句仅更新现有行。
    MySQL使用PRIMARY KEY或UNIQUE KEY索引来要确定表中是否存在新行。如果表没有这些索引，则REPLACE语句等同于INSERT语句。
### Mysql事务
    Mysql事务 使用COMMIT和ROLLBACK来管理MySQL中的事务
    Mysql表锁定 使用Mysql锁来协调会话之间的表访问
        获取表的锁的简单形式如下：
            LOCK TABLES table_name [READ | WRITE]
        释放锁：
            unlock tables
### Mysql 索引
    MySQL自动将声明为PRIMARY KEY，KEY，UNIQUE或INDEX的任何列添加到索引。 另外，您可以向已经有数据的表添加索引。
    CREATE INDEX语句的语法：
        CREATE [UNIQUE|FULLTEXT|SPATIAL] INDEX index_name
        USING [BTREE | HASH | RTREE] 
        ON table_name (column_name [(length)] [ASC | DESC],...)
        以下是具有相应允许的索引类型的表的存储引擎：
        存储引擎	            允许的索引类型
        MyISAM	            BTREE, RTREE
        InnoDB	            BTREE
        MEMORY/HEAP	        HASH,BTREE
        NDB             	HASH
        eg：CREATE INDEX index_officeCode ON employees(officeCode)
    删除索引的语法：
        DROP INDEX index_name ON table_name
        eg：DROP INDEX index_officeCode ON employees