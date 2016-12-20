package person.daizhongde.virtue.util.jdbc;

/* 导入必要的包 */
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.io.*;
import java.util.*;

public class WeblogicJNDIConnection
{
   public static void main(String args[])
   {
      String tablename="t_online_trans";             //数据库中表名
      String sqlstr;                          //sql语句
      Connection con=null;                    //连接对象
      Statement stmt=null;                    //语句对象
      ResultSet rs=null;                      //结果集对象
      Context ctx=null;
      Hashtable ht=new Hashtable();
      try
      {
         /*1、建立数据库连接 */
         ht.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
	     ht.put(Context.PROVIDER_URL,"t3://10.244.6.60:7001"); 
	     // 创建一个初始上下文环境
	     ctx=new InitialContext(ht);                     
	     //查询weblogic server的JNDI名字服务，JNDI 会指明访问的数据源所在位置。DataSource 数据源与配置的名字是一样
	     DataSource ds=(DataSource)ctx.lookup("AppDataSource");
         //利用DataSource调用getConnection()方法，获取数据库的配置信息。
	     con=ds.getConnection(); 

	     /*2、向数据库提交查询请求 */
         stmt=con.createStatement();                // 创建statement对象
         sqlstr="select * from "+tablename;          // 书写SQL语句
		 rs=stmt.executeQuery(sqlstr);              // 执行SQL语句，返回查询结果
         
	     /*3、读取查询结果        */
         while(rs.next())
         {
               System.out.print(rs.getInt("N_ID"));
               System.out.print("\t");
               System.out.print(rs.getString("C_GETTIME"));
               System.out.print("\t");
               System.out.print(rs.getString("C_JYFQF"));
               System.out.print("\t");
               System.out.print(rs.getString("C_XM"));
               System.out.print("\t");
               System.out.print("\n");
         }
       }
        /*4、异常处理        */
      catch(NamingException e1)
      {
         System.out.println(e1.toString());
		  System.out.println("驱动程序没有找到！");
      }
      catch(SQLException e2)
      {
         System.out.println(e2.toString());
		 System.out.println("数据库异常！");
      }
	 /*5、关闭数据库       */
      finally
      {
         try
         {
            if(rs!=null) rs.close();
            if(stmt!=null) stmt.close();
            if(con!=null) con.close();
         }
         catch(Exception e)
         {
		    System.out.println(e.toString());
		 }
      }
   }
}

