 ```SELECT lastName FROM employees ORDER BY lastName
 
 SELECT DISTINCT lastName FROM employees ORDER BY  lastName
 
 SELECT DISTINCT  state, city FROM  customers WHERE  state IS NOT NULL ORDER BY state , city
 
 SELECT COUNT(DISTINCT state) FROM customers WHERE country ="USA"
 
 SELECT DISTINCT state FROM customers WHERE state IS NOT NULL LIMIT 3;
 
 SELECT lastname, firstname, jobtitle FROM employees WHERE jobtitle = 'Sales Rep' AND officeCode = 1
 
 SELECT lastname, firstname, jobtitle FROM employees WHERE jobtitle <>
 'Sales Rep';
 
 SELECT lastname, firstname, officeCode FROM employees WHERE officecode > 5;
 
 SELECT productCode, productName, buyPrice FROM products WHERE buyPrice
 NOT BETWEEN 20 AND 100;
```

当使用BETWEEN运算符与日期类型值时，要获得最佳结果，应该使用类型转换将列或表达式的类型显式转换为DATE类型。
``` 
 SELECT orderNumber,requiredDate,status FROM orders WHERE
 requireddate BETWEEN CAST('2013-01-01' AS DATE)AND CAST('2013-01-31' AS
 DATE);
   
 SELECT employeeNumber, lastName, firstName FROM employees WHERE firstName LIKE 'a%'; 以a开头
 
 SELECT employeeNumber, lastName, firstName FROM employees WHERE firstName LIKE '%a'; 以a结尾
 
 SELECT employeeNumber, lastName, firstName FROM employees WHERE firstName LIKE '%a%'; 只要包含a 就会被列出
 ```
 查找名字以T开头的员工，以m结尾，并且包含例如Tom，Tim之间的任何单个字符 
 
 `SELECT employeeNumber, lastName, firstName FROM employees WHERE
 firstname LIKE 'T_m';`


MySQL允许将NOT运算符与LIKE运算符组合，以找到不匹配特定模式的字符串。查找不以B开头的字符串
```
 SELECT employeeNumber, lastName, firstName FROM employees WHERE
lastName NOT LIKE 'B%';
```

则反斜杠字符\是默认转义字符。如下语句，将查询productCode字段中包含_20字符串的值
```
 SELECT productCode, productName FROM products WHERE productCode LIKE
'%\_20%';
```

也可以使用ESCAPE子句指定一个不同的转义字符，例如$： 
``` 
SELECT productCode,productName FROM products WHERE productCode LIKE '%$_20%' ESCAPE '$';
```

查找位于美国和法国的办事处，可以使用IN运算符作为以下查询： 
``` 
SELECT officeCode, city, phone, country FROM offices WHERE country IN ('USA' ,'France'); 
SELECT officeCode, city, phone FROM offices WHERE country ='USA' OR country = 'France';
执行结果相同
```
获得不在美国和法国的办事处，在WHERE子句中使用NOT IN如下： 
```
SELECT officeCode, city, phone FROM offices WHERE country NOT IN( 'USA',
'France');
```

IN运算符通常用于子查询。子查询不提供常量值列表，而是提供值列表。查找总金额大于60000的订单，
则使用IN运算符查询如下 
``` 
SELECT orderNumber, customerNumber, status,
shippedDate FROM orders WHERE orderNumber IN ( 
SELECT orderNumber FROM
orderDetails GROUP BY orderNumber HAVING SUM(quantityOrdered *
priceEach) > 60000
);
```

使用LIMIT子句来选择表中的前N行记录。查询employees表中前5个客户 
```
SELECT customernumber, customername, creditlimit FROM customers LIMIT 5;

SELECT customernumber, customername, creditlimit FROM customers LIMIT 3,10;
```

要查询信用额度最高的前五名客户 
``` 
SELECT customernumber, customername,
creditlimit FROM customers ORDER BY creditlimit DESC LIMIT 5;
```


使用MySQL LIMIT 获得第n个最高值
解决步骤：首先，按照降序对结果集进行排序。第二步，使用LIMIT子句获得第n贵的产品。
(注意：偏移量从0开始，所以要指定从1开始，然后取一行记录） 
```
 SELECT productCode,productName, buyprice FROM products ORDER BY buyprice DESC
LIMIT 1, 1;
```


查询有销售代表的客户，请使用IS NOT NULL运算符 
```
SELECT customerName,country,salesrepemployeenumber FROM customers
WHERE salesrepemployeenumber IS NOT NULL ORDER BY customerName;
```


要按姓氏按降序和名字按升序排序联系人，请在相应列中分别指定DESC和ASC 
```
SELECT contactLastname, contactFirstname FROM customers ORDER BY
contactLastname DESC,contactFirstname ASC LIMIT 10;
```


ORDER BY子句允许使用FIELD()函数为列中的值定义自己的自定义排序顺序。 
``` 
SELECT orderNumber, status FROM orders ORDER BY FIELD(status,'In Process','On
Hold','Cancelled','Resolved','Disputed','Shipped');
```


为输出的标题分配一个有意义的列别名 
``` 
SELECT CONCAT_WS(', ', lastName,firstname) AS `Full name`FROM employees;
```


使用ORDER BY，GROUP BY和HAVING子句中的列别名来引用该列。 
``` 
SELECT orderNumber `Order no.`, SUM(priceEach * quantityOrdered) total FROM
orderdetails GROUP BY `Order no.`HAVING total > 60000;

SELECT customerName,  COUNT(o.orderNumber) total FROM customers c  
INNER JOIN orders o ON c.customerNumber = o.customerNumber
GROUP BY customerName HAVING total >=5 ORDER BY  total DESC;
 ```
 

通过使用INNER JOIN子句根据productline列匹配行来从两个表中查询选择数据 
```
SELECT productCode, productName, textDescription FROM products t1
INNER JOIN productlines t2 ON t1.productline = t2.productline;
```


如果两个表的连接列是使用相同一个列：productline 返回结果同上 
```
SELECT productCode, productName, textDescription FROM products t1
INNER JOIN productlines USING (productLine);
```


使用具有GROUP BY子句的INNER
JOIN子句从orders和orderdetails表中获取订单号，订单状态和总销售额 
```
SELECT T1.orderNumber, status,SUM(quantityOrdered * priceEach) total
FROM orders AS T1 INNER JOIN orderdetails AS T2 ON T1.orderNumber =
T2.orderNumber GROUP BY orderNumber;
```


inner join 使用等于以外的运算符 
```
SELECT orderNumber,productName,msrp,priceEach FROM products p INNER
JOIN orderdetails o ON p.productcode = o.productcode AND p.msrp >
o.priceEach WHERE p.productcode = 'S10_1678' LIMIT 0, 15;
```

要查询每个客户的所有订单 （ON c.customerNumber = o.customerNumber
==USING(customerNumber)） 

```
SELECT c.customerNumber, c.customerName, orderNumber, o.status FROM
customers c LEFT JOIN orders o ON c.customerNumber = o.customerNumber;
```

使用MySQL LEFT JOIN子句来查找不匹配的行 

```
SELECT c.customerNumber, c.customerName, orderNumber, o.status FROM
customers c LEFT JOIN orders o ON c.customerNumber = o.customerNumber
WHERE orderNumber IS NULL;
```


使用employeeNumber和reportsTo列将employees表连接自身。employees表有两个角色：一个是经理，另一个是直接报告者(即，下属员工)。
```
SELECT CONCAT(m.lastname, ', ', m.firstname) AS 'Manager',
CONCAT(e.lastname, ', ', e.firstname) AS 'Direct report' FROM employees
e INNER JOIN employees m ON m.employeeNumber = e.reportsto ORDER BY
Manager;

 SELECT
   c1.city, c1.customerName, c2.customerName
 FROM
   customers c1
   INNER JOIN
   customers c2 ON c1.city = c2.city
                   AND c1.customername > c2.customerName
 ORDER BY c1.city;
```


使用GROUP BY子句并指定按status列来执行分组 
```
SELECT status FROM orders GROUPBY status;
SELECT DISTINCT status FROM orders; 结果同上
```

GROUP BY子句通常与聚合函数一起使用以执行计算每个分组并返回单个值。
可以使用COUNT函数与GROUP BY子句查询每个状态中的订单数 
``` 
SELECT status,COUNT(*) AS total_number FROM orders GROUP BY status;
```

按状态获取所有订单的总金额，可以使用orderdetails表连接orders表，并使用SUM函数计算总金额。

```
SELECT status, SUM(quantityOrdered * priceEach) AS amount FROM
orders INNER JOIN orderdetails USING (orderNumber) GROUP BY status;
```

 可以按表达式对行进行分组。以下查询获取每年的总销售额。 
 ```
 
 SELECT
   YEAR(orderDate) AS year,
   SUM(quantityOrdered * priceEach) AS total
 FROM
   orders
   INNER JOIN
   orderdetails USING (orderNumber)
 WHERE
   status = 'Shipped'
 GROUP BY YEAR(orderDate);
 ```
 
 
 可使用HAVING子句过滤GROUP
 BY子句返回的分组。以下查询使用HAVING子句来选择2013年以后的年销售总额。 
 ```
 
 SELECT
   YEAR(orderDate) AS year,
   SUM(quantityOrdered * priceEach) AS total
 FROM
   orders
   INNER JOIN
   orderdetails USING (orderNumber)
 WHERE
   status = 'Shipped'
 GROUP BY year
 HAVING year <= 2014;

```



查询返回在位于美国(USA)的办公室工作的员工 
```
 
 SELECT CONCAT_WS(",", lastName, firstName) `full name`
 FROM
   employees
 WHERE
   officeCode IN (SELECT officeCode
                  FROM
                    offices
                  WHERE
                    country = 'USA');
```


查询返回最大付款额的客户。 
 ```
 
 SELECT
   customerNumber, checkNumber, amount
 FROM
   payments
 WHERE
   amount = (SELECT
               MAX(amount)
             FROM
               payments);
```


使用带有NOT IN运算符的子查询来查找没有下过任何订单的客户 
 ```
 
 SELECT
   customerName,count(*) total
 FROM
   customers
 WHERE
   customerNumber NOT IN (SELECT DISTINCT
                            customerNumber
                          FROM
                            orders) GROUP BY  customerName;
```


以下子查询将查找订单表中的最大，最小和平均数： 
```
 
 SELECT
   MAX(items),
   MIN(items),
   FLOOR(AVG(items))
 FROM
   (SELECT
      orderNumber,
      COUNT(orderNumber) AS items
    FROM
      orderdetails
    GROUP BY orderNumber) AS tempTable;
 ```
 
 
子查询 
 ```
 
 SELECT
   orderNumber, COUNT(orderNumber) AS items
 FROM
   orderdetails
 GROUP BY orderNumber
```


查询选择总额大于60000的销售订单。 
 ```
 
 SELECT
   orderNumber,
   SUM(priceEach * quantityOrdered) total
 FROM
   orderdetails
   INNER JOIN
   orders USING (orderNumber)
 GROUP BY orderNumber
 HAVING SUM(priceEach * quantityOrdered) > 60000;
 ```
 
 
 
 使用上面的查询作为相关子查询，通过使用EXISTS运算符来查找至少有一个总额大于60000的销售订单的客户信息：
 ```
 
 SELECT
   customerNumber,
   customerName
 FROM
   customers
 WHERE
   EXISTS( SELECT
             orderNumber, SUM(priceEach * quantityOrdered)
           FROM
             orderdetails
             INNER JOIN
             orders USING (orderNumber)
           WHERE
             customerNumber = customers.customerNumber
           GROUP BY orderNumber
           HAVING SUM(priceEach * quantityOrdered) > 60000);
```



首先，执行子查询来创建一个结果集或派生表。
然后，在productCode列上使用products表连接top5product2013派生表的外部查询。
```
 
 SELECT
   productName,
   sales
 FROM
   (SELECT
      productCode,
      ROUND(SUM(quantityOrdered * priceEach)) sales
    FROM
      orderdetails
      INNER JOIN orders USING (orderNumber)
    WHERE
      YEAR(shippedDate) = 2013
    GROUP BY productCode
    ORDER BY sales DESC
    LIMIT 5) top5products2013
   INNER JOIN
   products USING (productCode);

```
 更复杂的MySQL派生表示例
 假设必须将2013年的客户分为3组：铂金，白金和白银。 此外，需要了解每个组中的客户数量，具体情况如下：

 订单总额大于100000的为铂金客户；
 订单总额为10000至100000的为黄金客户
 订单总额为小于10000的为银牌客户
 要构建此查询，首先，您需要使用CASE表达式和GROUP BY子句将每个客户放入相应的分组中，如下所示：
 
 ```
 
 SELECT
   customerNumber,
   ROUND(SUM(quantityOrdered * priceEach)) sales,
   (CASE
    WHEN SUM(quantityOrdered * priceEach) < 10000
      THEN 'Silver'
    WHEN SUM(quantityOrdered * priceEach) BETWEEN 10000 AND 100000
      THEN 'Gold'
    WHEN SUM(quantityOrdered * priceEach) > 100000
      THEN 'Platinum'
    END)                                   customerGroup
 FROM
   orderdetails
   INNER JOIN
   orders USING (orderNumber)
 WHERE
   YEAR(shippedDate) = 2013
 GROUP BY customerNumber
 ORDER BY sales DESC;
```
 做为派生表去查询
 
 ```
 
 SELECT
   customerGroup,
   COUNT(cg.customerGroup) AS groupCount
 FROM
   (SELECT
      customerNumber,
      ROUND(SUM(quantityOrdered * priceEach)) sales,
      (CASE
       WHEN SUM(quantityOrdered * priceEach) < 10000
         THEN 'Silver'
       WHEN SUM(quantityOrdered * priceEach) BETWEEN 10000 AND 100000
         THEN 'Gold'
       WHEN SUM(quantityOrdered * priceEach) > 100000
         THEN 'Platinum'
       END)                                   customerGroup
    FROM
      orderdetails
      INNER JOIN orders USING (orderNumber)
    WHERE
      YEAR(shippedDate) = 2013
    GROUP BY customerNumber) cg
 GROUP BY cg.customerGroup ;

```





