/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;
import java.io.*;
/**
 *
 * @author inhatc
 */
public class userDB {
    

   String strDriver = "oracle.jdbc.driver.OracleDriver";
//   String strURL="jdbc:sqlserver://;serverName=localhost;databaseName=Automobile";
     String strURL="jdbc:oracle:thin:@218.51.98.234:1521";

   String strUser = "INHATC";
   String strPWD="inha1958";

           
  Connection DB_con;
  Statement DB_stmt; //정적 
  PreparedStatement DB_pstmt; //동적 
  ResultSet DB_rs;
  
  public void dbOpen() throws IOException{
      try{
        Class.forName(strDriver);
        
        DB_con=DriverManager.getConnection(strURL,strUser,strPWD);
        DB_stmt=DB_con.createStatement();
      }
      catch(Exception e){
          System.out.println("SQLException:"+e.getMessage());
      }
  }
  
  
  public void dbClose()throws IOException{
      try{
          DB_stmt.close();
          DB_con.close();
      }catch(Exception e){
          System.out.println("SQLException:"+e.getMessage());
      }
  }
}
