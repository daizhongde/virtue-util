package person.daizhongde.virtue.util.jdbc;

import java.sql.*;
public class MySQLConnect
{

    public static void main(String args[])
    {
         Connection connection;
         Statement statement;
         ResultSet resultSet;
         String driver="com.mysql.jdbc.Driver";
         String url="jdbc:mysql://localhost/tool?charset=utf-8";
         String username = "root";
         String password = "123";
         try
         {
              Class.forName(driver);
              connection=DriverManager.getConnection(url,username,password);
              if(connection!=null)
                  System.out.println("success");
              statement=connection.createStatement();
              resultSet=statement.executeQuery("select * from t_authority_employee;");
              while(resultSet.next())
              {
              	  System.out.println(resultSet.getString(1)+"  "+resultSet.getString(2)+"  "+resultSet.getString(3)+"  "+resultSet.getString(4)+" "+resultSet.getString(5)+" "+resultSet.getString(6));
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