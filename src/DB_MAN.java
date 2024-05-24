/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;
import java.io.*;
/**
 *
 * @author ShinHyeonSeo
 */
public class DB_MAN {
    //String strDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String strDriver = "oracle.jdbc.driver.OracleDriver";
    //String strUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=Automobile;";
    //String strUrl = "jdbc:sqlserver://10.70.41.81:1433;DatabaseName=Automobile;";
    String strUrl = "jdbc:oracle:thin:@218.51.98.234:1521";
    String strUser = "INHATC";
    String strPWD = "inha1958";
    
    Connection DB_con;
    Statement DB_stmt;
    ResultSet DB_rs;
    
    public void dbOpen() throws IOException {
        try{
            Class.forName(strDriver);
            DB_con = DriverManager.getConnection(strUrl,strUser,strPWD);
            DB_stmt = DB_con.createStatement();
        }catch(Exception e){
            System.out.println("SQLExcetption : "+e.getMessage());
        }
    }
    public void dbClose() throws IOException {
        try{
            DB_stmt.close();
            DB_con.close();
        }catch(SQLException e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
}
