package com.github.phalexei.sig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.phalexei.sig.database.Utils;

public class Main {

	public Main() {
		//TODO
		callSQLQueryQ9();
		
		//GeoMainFrame frame = new GeoMainFrame("frame",new MapPanel(100, 100, 100));
	}

	public static void main(String[] args) {
		new Main();
	}

	/**
	 * Simple query
	 */
	private void callSQLQueryQ9() {

		Connection conn = Utils.getConnection();

		try {

			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.toString()); 
				//System.out.println("colonne 1 = " + rs.getInt(1) + "; colonne 2 = " + ((PGgeometry) rs.getObject(2)).getGeometry());
			}
			rs.close();
			stmt.close();
		} catch (SQLException se) {
			System.err
					.println("Threw a SQLException creating the list of blogs.");
			System.err.println(se.getMessage());
		}
	}
}
