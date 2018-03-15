SQLite 线程中同时执行insert语句阻塞，在进程中或进程的线程中使用则崩溃
### 事务处理
事务是一个最小的工作单元，不论成功与否都作为一个整体进行工作。  
不会有部分完成的事务。由于事务是由几个任务组成的，因此如果一个事务作为一个整体是成功的，则事务中的每个任务都必须成功。  
如果事务中有一部分失败，则整个事务失败。当事务失败时，系统返回到事务开始前的状态。这个取消所有变化的过程称为“回滚”( rollback )。  
例如，如果一个事务成功更新了两个表，在更新第三个表时失败，则系统将两次更新恢复原状，并返回到原始的状态。
保持应用程序的完整性

情况多线程 执行多个语句：  
如何保证正常执行：  
Semaphore ： 协调多线程下的控制同步的问题。  

    private java.util.concurrent.Semaphore semaphoreTransaction = new java.util.concurrent.Semaphore(1);
    public boolean execSQLList(List<String> sqlList) {  
            boolean result = true;  
            db = getSQLiteDataBase();  
            String currentSqlString = "";  
            try {  
                semaphoreTransaction.acquire();  //多线程下的调用许可数为1
                db.beginTransaction();   //事务开始
                for (String sql : sqlList) {  
                    currentSqlString = sql;  
                    db.execSQL(sql);  
                }  
                db.setTransactionSuccessful();  
                result = true;  
            } catch (Exception e) {  
                result = false;  
                Log.e("SQLERROR", "IN SQLDA: " + e.getMessage() + currentSqlString);  
            } finally {  
                db.endTransaction();  
                 //semaphore会检测是否有其他信号量已经执行，如果有，改线程就会停止，
                 //直到另一个semaphore释放资源之后，才会继续执行下去，
                semaphoreTransaction.release(); 
                closeSQLiteDatabase();  
            }  
            return result;  
        }  
      