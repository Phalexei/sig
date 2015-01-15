package com.github.phalexei.sig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgis.PGgeometry;

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
		//TODO : 3 rows expected. 1 row found !
		Connection conn = Utils.getConnection();

		try {
			PreparedStatement stmt = conn.prepareStatement("select tags->'name' as nom, ST_X(ST_Centroid(bbox)) as longitude, ST_Y(ST_Centroid(bbox)) as relations from ways where tags->'name' like '%Domaine Unive%'");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) { 
				System.out.println("nom = " + rs.getString(1) + "; longitude = " + rs.getDouble(2) +" ;  latitude ="+ rs.getDouble(3));
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
