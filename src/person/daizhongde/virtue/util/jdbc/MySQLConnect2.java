package person.daizhongde.virtue.util.jdbc;

import java.sql.*;
public class MySQLConnect2
{

    public static void main(String args[])
    {
         Connection connection;
         Statement statement;
         ResultSet resultSet;
         String driver="com.mysql.jdbc.Driver";
//         String url="jdbc:mysql://localhost/tool?charset=utf-8";
         String url="jdbc:mysql://localhost/daizd?user=root&password=123&charset=utf-8";
//         String username = "root";
//         String password = "123";
         try
         {
              Class.forName(driver);
              connection=DriverManager.getConnection(url);
              if(connection!=null)
                  System.out.println("success");
              statement=connection.createStatement();
              resultSet=statement.executeQuery("select * from t_mysiainfo_employee;");
              int i = 0;
              while(resultSet.next())
              {
            	  i++;
              	  System.out.println(resultSet.getString(1)+"  "+resultSet.getString(2)+"  "+resultSet.getString(3)+"  "+resultSet.getString(4)+" "+resultSet.getString(5)+" "+resultSet.getString(6));
            	  if(i==100)break;
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