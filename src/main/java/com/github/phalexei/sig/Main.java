package com.github.phalexei.sig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sig.Database;

public class Main {
	
	public Main(){
		callSQLQuery();
	}
	
	
    public static void main(String[] args) {
    	new Main();
    }
    
    /**
     * Simple query
     */
    private void callSQLQuery(){
    	
    	Connection db = Database.getConnection();
    	
    	try 
        {
          Statement st = db.createStatement();
          ResultSet rs = st.executeQuery("SELECT * FROM users");
          while ( rs.next() )
          {
            System.out.println(rs.toString());
          }
          rs.close();
          st.close();
        }
        catch (SQLException se) {
          System.err.println("Threw a SQLException creating the list of blogs.");
          System.err.println(se.getMessage());
        }
    }
}
