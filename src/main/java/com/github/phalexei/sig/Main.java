package com.github.phalexei.sig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.github.phalexei.sig.database.Utils;

public class Main {

	public Main(String arg0) {
		
		//3 rows expected. 3 row found. It misses Latitude and Longitude
		if (!arg0.isEmpty()){
			callSQLQueryQ9(arg0);
		}
		
		// TODO : after question 9.
		//GeoMainFrame frame = new GeoMainFrame("frame",new MapPanel(100, 100, 100));
	}

	public static void main(String[] args) {
		String s = parseArguments(args);
		new Main(s);
	}

	/**
	 * Question 9
	 */
	private void callSQLQueryQ9(String arg0) {
		
		// Get DB connection
		Connection conn = Utils.getConnection();
		
		//make SQL request
		//TODO : Add Latitude and Longitude in the SQL request
		String request = "select tags->'name' as nom from nodes where tags->'name' like ? || '%'";
		
		try {
			//prepare statement
			PreparedStatement stmt = conn.prepareStatement(request);
			//add string
			stmt.setString(1, arg0);
			//display request
			System.out.println(stmt.toString());
			//execute request
			ResultSet rs = stmt.executeQuery();
			//display result
			while (rs.next()) { 
				System.out.println("nom = " + rs.getString(1));
			}
			rs.close();
			stmt.close();
		} catch (SQLException se) {
			System.err
					.println("Threw a SQLException creating the list of blogs.");
			System.err.println(se.getMessage());
		}
	}
	
	public static String parseArguments(String[] args){
		String st = "";
		for(int i = 0; i<args.length; i++){
			System.out.println(args[i]);
			st = st.concat(args[i]);
			st = st.concat(" ");
		}
		st = st.substring(0, st.length() -1);
		return st;
	}
}
