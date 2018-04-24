package com.savingtime.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
* Classe respons�vel pela conex�o com banco de dados MySQL
*/
public class AcessoBD
{
// -----------------------------------------------------------
// Carrega driver JDBC

	String FORNAME_URL = "com.mysql.jdbc.Driver";
/*
   String USERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME").trim();
   String PASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD").trim();
   String DB_NAME = System.getenv("OPENSHIFT_APP_NAME").trim();
   String HOST = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
   String PORT = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
   String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
	*/
  
	
//
   static
   {
      try
      {
         Class.forName("com.mysql.jdbc.Driver");
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
   }

// -----------------------------------------------------------
// Obt�m conex�o com o banco de dados
   public Connection obtemConexao() throws SQLException
   {
	
	   /*Connection m_connection = DriverManager.getConnection(URL , USERNAME , PASSWORD);  
      return m_connection;*/
	  
	return DriverManager.getConnection
		         (
		         "jdbc:mysql://localhost/USJT?user=root&password=root"
		         );
   }
}