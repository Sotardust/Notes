##### 创建表
```
 CREATE TABLE IF NOT EXISTS tasks ( 
     task_id INT(11) AUTO_INCREMENT,
     subject VARCHAR(45) DEFAULT NULL, 
     start_date DATE DEFAULT NULL,
     end_date DATE DEFAULT NULL, 
     description VARCHAR(200) DEFAULT NULL,
     PRIMARY KEY (task_id) 
 ) 
 ENGINE=InnoDB DEFAULT CHARSET=utf8;

```
##### INSERT语句
插入单行基本数据 
```
INSERT INTO tasks (subject, start_date, end_date, description)
VALUES ('Learn MySQL INSERT', '2017-07-21', '2017-07-22', 'Start learning..');
```
插入多行数据 
```
INSERT INTO tasks(subject,start_date,end_date,description) VALUES
('任务-1','2017-01-01','2017-01-02','Description 1'),
('任务-2','2017-01-01','2017-01-02','Description 2'),
('任务-3','2017-01-01','2017-01-02','Description 3'); 
```
通过复制tasks表结构，创建名为tasks_bak新表 
```
 CREATE TABLE tasks_bak LIKE tasks;
 INSERT INTO tasks_bak SELECT * FROM tasks;
```
##### update 语句
 使用UPDATE语句将人员code为
 1056的电子邮件更新为新的电子邮件：email@test.com.com 
``` 
 UPDATE employees SET email = 'email@test.com.com' WHERE employeeNumber = 1056; MySQL
```
UPDATE多列 
``` 
 UPDATE employees SET lastname = 'Hill', email = 'email@test.com.com' WHERE employeeNumber = 1056;
```
使用SELECT语句的MySQL
UPDATE示例，使用SELECT语句查询来自其他表的数据来提供给SET子句的值。 
```
UPDATE customers SET salesRepEmployeeNumber = ( 
SELECT employeeNumber FROM employees WHERE jobtitle = 'Sales Rep'LIMIT 1
) 
WHERE salesRepEmployeeNumber IS NULL;
```
可以使用DELETE语句与LEFT JOIN子句来清理客户数据。 以下声明删除未下订单的客户：

``` 
DELETE customers FROM customers LEFT JOIN orders ON
customers.customerNumber = orders.customerNumber WHERE orderNumber IS NULL;
```
以下DELETE语句选择法国(France)的客户，按升序按信用额度(creditLimit)进行排序，并删除前5个客户：
```
DELETE FROM customers WHERE country = 'France' ORDER BY creditLimit LIMIT 5;
```

##### Mysql事务

```
-- start a new transaction
start transaction;

-- get latest order number
select @orderNumber := max(orderNUmber)
from orders;
-- set new order number
set @orderNumber = @orderNumber  + 1;

-- insert a new order for customer 145
insert into orders(orderNumber,
                   orderDate,
                   requiredDate,
                   shippedDate,
                   status,
                   customerNumber)
values(@orderNumber,
       now(),
       date_add(now(), INTERVAL 5 DAY),
       date_add(now(), INTERVAL 2 DAY),
       'In Process',
       145);
-- insert 2 order line items
insert into orderdetails(orderNumber,
                         productCode,
                         quantityOrdered,
                         priceEach,
                         orderLineNumber)
values(@orderNumber,'S18_1749', 30, '136', 1),
  (@orderNumber,'S18_2248', 50, '55.09', 2);
-- commit changes
commit;

-- get the new inserted order
select * from orders a
  inner join orderdetails b on a.ordernumber = b.ordernumber
where a.ordernumber = @ordernumber;
```

show PROCESSLIST ;
SELECT CONNECTION_ID();
SELECT  HOUR(now()),MINUTE(now()),second(now()) ;

##### 创建Mysql 临时表
```
CREATE TEMPORARY TABLE top10customers
  SELECT p.customerNumber,
    c.customerName,
    FORMAT(SUM(p.amount),2) total
  FROM payments p
    INNER JOIN customers c ON c.customerNumber = p.customerNumber
  GROUP BY p.customerNumber
  ORDER BY total DESC
  LIMIT 10;
  
```
查询临时表数据 `SELECT * FROM top10customers;`

删除临时表 `DROP TEMPORARY TABLE top10customers;`










