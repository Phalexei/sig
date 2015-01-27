package com.github.phalexei.sig;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.github.phalexei.sig.database.Utils;
import com.github.phalexei.sig.gui.GeoMainFrame;
import com.github.phalexei.sig.gui.LineString;
import com.github.phalexei.sig.gui.MapPanel;
import com.github.phalexei.sig.gui.Point;
import com.github.phalexei.sig.gui.Polygon;

public class Main {

	public Main(String arg0) {
		
		//Question 9 : OK using arg = "Dom__ne _niversit" (2 words)
		if (!arg0.isEmpty()){
			Question9(arg0);
		}
		
		// TODO : other questions using UI
		Question10();
	}

	public static void main(String[] args) {
		String s = parseArguments(args);
		new Main(s);
	}

	/**
	 * Question 9
	 */
	private void Question9(String arg0) {
		
		// Get DB connection
		Connection conn = Utils.getConnection();
		
		//make SQL request
		String request = "select tags->'name' as nom, ST_X(geom) as longitude, ST_Y(geom) as latitude from nodes where tags->'name' like ? || '%'";
		
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
				System.out.println("nom = " + rs.getString(1)+", longitude = "+rs.getDouble(2)+", latitude = "+rs.getDouble(3));
			}
			rs.close();
			stmt.close();
		} catch (SQLException se) {
			System.err
					.println("Threw a SQLException creating the list of blogs.");
			System.err.println(se.getMessage());
		}
	}
	
	private void Question10(){
		
		Point p1 = new Point(15.758102, 45.187486500000006, Color.BLUE);
		Point p2 = new Point(14.7680106, 145.192893000000005, Color.RED);
		
		Point p3 = new Point(50.758102, 50.187486500000006, Color.BLUE);
		Point p4 = new Point(48.7680106, 160.192893000000005, Color.RED);
		
		Point p5 = new Point(3.758102, 20.187486500000006, Color.BLUE);
		Point p6 = new Point(2.7680106, 120.192893000000005, Color.RED);
		
		LineString linestring = new LineString(Color.YELLOW);
		linestring.addPoint(p5);
		linestring.addPoint(p6);
		
		Polygon p = new Polygon(Color.BLACK, Color.CYAN);
		p.addPoint(p1);
		p.addPoint(p3);
		p.addPoint(p4);
		p.addPoint(p2);
		// attention à l'ordre pour dessiner la figure
		
		MapPanel map = new MapPanel(100, 100, 500);
		map.addPrimitive(linestring);
		map.addPrimitive(p5);
		map.addPrimitive(p6);
		map.addPrimitive(p);
		
		new GeoMainFrame("frame",map);
		
		// MAIS C'EST DE LA MERDE CE TP ...
		
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
