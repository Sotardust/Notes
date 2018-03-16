面试总结：  
1、形参，实参  
2、String a = new String("1"+"2"); 共几个对象  
3. final，finalize ，finizedly（try{
    return 1}catch（）{
    return ）finishlsy{
    return3}
4、syn代码块，syn object  voliate
5、gc

### java中的形参和实参的区别以及传值调用和传值引用调用
#### 基础数据类型（传值调用）
       int a = 2;
       int b = 3;
       change(a, b);  //传值调用，值改变的是形参，不会作用到实参 ，a，b值不变
       System.out.println("a = " + a);
       System.out.println("b = " + b);
    
        private void change(int m, int n) {
            int temp = m;
            m = n;
            n = temp;
        }
        
        输出结果：
        
        a = 2
        b = 3
        
#### 引用数据类型（引用调用）
传引用，方法体内改变形参引用，不会改变实参引用，但有可能改变实参对象的属性值
###### 方法体内改变形参引用，但不会改变实参引用 ，实参值不变。
    public class TestFun2 {  
    public static void testStr(String str){  
    str="hello";//型参指向字符串 “hello”  
    }  
    public static void main(String[] args) {  
    String s="1" ;  
    TestFun2.testStr(s);  
    System.out.println("s="+s); //实参s引用没变，值也不变  
    }  
    }
    
    输出结果：
        s=1
###### 方法体内，通过引用改变了实际参数对象的内容，注意是“内容”，引用还是不变的。
    public class TestFun4 {  
    public static void testStringBuffer(StringBuffer sb){  
    sb.append("java");//改变了实参的内容  
    }  
    public static void main(String[] args) {  
    StringBuffer sb= new StringBuffer("my ");  
    new TestFun4().testStringBuffer(sb);  
    System.out.println("sb="+sb.toString());//内容变化了  
    }  
    }
    
    输出：sb=my java 