package person.daizhongde.virtue.util.jdbc;

import java.sql.*;
public class SQLServerConnect
{

    public static void main(String args[])
    {
         Connection connection;
         Statement statement;
         ResultSet resultSet;
         //通过Microsoft的JDBC驱动连接 (SelectMethod＝cursor);
         String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
         String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=ypzlk";//资源地址,协议字段
         //通过JTDS JDBC Driver连接SQL Server数据库
//         String driver ="net.sourceforge.jtds.jdbc.Driver";
//         String url ="jdbc:jtds:sqlserver://localhost:1433/mydb";
         String username = "ypxx";
         String password = "ypxx";
         try
         {
              Class.forName(driver);
              connection=DriverManager.getConnection(url,username,password);
              if(connection!=null)
                  System.out.println("success");
              statement=connection.createStatement();
              resultSet=statement.executeQuery("select * from test");
//              resultSet=statement.executeQuery("select stu_number from user1;");
              while(resultSet.next())
              {
              	  System.out.println(resultSet.getString(1)+"  "+resultSet.getString(2));
//              	  if(resultSet.isFirst())
//              		  System.out.println("检索指针是位于此 ResultSet 对象的第一行。");
//              	  if(resultSet.isLast())
//            		  System.out.println("检索指针是位于此 ResultSet 对象的最后一行。");
              }
              statement.close();
              connection.close();
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
    }
}