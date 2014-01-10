<%@ page contentType="text/html;charset=euc-kr" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*"%>
<html>
<head>
<title>MySQL select 예제</title> 
</head>
<body>
<%
 //String DB_URL = "jdbc:mysql://localhost:3306/egovam?useUnicode=true&characterEncoding=utf-8";
String DB_URL = "jdbc:mysql://localhost:3306/egovam";
 String DB_USER = "egovam";
 String DB_PASSWORD= "egovam01";
 Connection conn;
 Statement stmt;
 ResultSet rs = null;
 
 System.out.println("file.encoding ===========> " + System.getProperty("file.encoding"));
 
 String query = "select * from egovam_systems";
  try {
  Class.forName("com.mysql.jdbc.Driver");
  conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
  stmt = conn.createStatement();
  rs = stmt.executeQuery(query);
  out.print("result: </br></br>");
  String s;
  
  while (rs.next()) 
  {
   out.println(rs.getString("system_id"));
   out.println(" : ");
   out.println(rs.getString("system_name"));
   
   System.out.println("system name ===========> " + rs.getString("system_name"));
   System.out.println("system name * ===========> " + new String(rs.getString("system_name").getBytes("utf-8"), System.getProperty("file.encoding")));
   
   out.println("<br>");
  }
  rs.close();
  stmt.close();
  conn.close();
 } 
 catch(Exception e){
  out.print("Exception Error...");
  out.print(e.toString());
 }
 finally {
 }
%>
</body>
</html>