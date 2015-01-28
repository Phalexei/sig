package com.github.phalexei.sig;

import com.github.phalexei.sig.database.Utils;
import com.github.phalexei.sig.gui.GeoMainFrame;
import com.github.phalexei.sig.gui.LineString;
import com.github.phalexei.sig.gui.MapPanel;
import com.github.phalexei.sig.gui.Polygon;
import org.postgis.PGgeometry;
import org.postgis.Point;

import java.awt.*;
import java.sql.*;
import java.util.Random;

public class Main {

    public Main(String arg) {
        if (!arg.isEmpty()) {
            question9(arg);
        }

        question10_A();

        question10_B();
    	question10_C();
    	question11a();
		question11b();
    }

    public static void main(String[] args) {
        new Main(parseArguments(args));
    }

    public static String parseArguments(String[] args) {
        StringBuilder s = new StringBuilder();

        for (String arg : args) {
            System.out.println(arg);
            s.append(arg).append(" ");
        }
        if (s.length() != 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    /**
     * Question 9
     */
    private void question9(String name) {
        // Get DB connection
        Connection connection = Utils.getConnection();

        try {
            //prepare statement
            PreparedStatement statement = connection.prepareStatement(
                    "select tags->'name' as nom, ST_X(geom) as longitude," +
                            " ST_Y(geom) as latitude from nodes where tags->'name' like ? || '%'");
            //add string
            statement.setString(1, name);
            //execute request
            ResultSet resultSet = statement.executeQuery();
            //display result
            System.out.println("Résultats question 9 :");
            while (resultSet.next()) {
                System.out.println("nom = " + resultSet.getString(1) + ", longitude = " + resultSet.getDouble(2) + ", latitude = " + resultSet.getDouble(3));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }
    }

    /**
     * Question 10 A
     */
    private void question10_A() {
        // Get DB connection
        Connection connection = Utils.getConnection();
        try {
            //prepare statement
            Statement statement = connection.createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("SELECT ST_Transform(linestring, 2154) from ways where ST_Intersects(ST_SetSRID" +
                    "(ST_MakeBox2D(ST_Point(5.7, 45.1), ST_Point(5.8, 45.2)), 4326), linestring)" +
                    "AND tags ? 'highway'");

            MapPanel panel = new MapPanel(919000, 6450000, 1000);
            Random random = new Random();
            //get result
            while (resultSet.next()) {
                org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(1));

                LineString guiLineString = new LineString(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i++) {
                    Point point = lineString.getGeometry().getPoint(i);
                    guiLineString.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                }

                panel.addPrimitive(guiLineString);
            }
            resultSet.close();
            statement.close();
            
            //display result
            new GeoMainFrame("Question 10", panel);
            
        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }
    }

    /**
     * Question 10 B
     */
    private void question10_B() {

        // Get DB connection
        Connection connection = Utils.getConnection();
        try {
            //prepare statement
            Statement statement = connection.createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("SELECT ST_Transform(linestring, 2154) from ways where ST_Intersects(ST_SetSRID" +
                    "(ST_MakeBox2D(ST_Point(5.7, 45.1), ST_Point(5.8, 45.2)), 4326), linestring)" +
                    "AND tags ? 'building'");

            MapPanel panel = new MapPanel(919000, 6450000, 1000);
            Random random = new Random();
            //get result
            while (resultSet.next()) {
                org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(1));

                LineString guiLineString = new LineString(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i++) {
                    Point point = lineString.getGeometry().getPoint(i);
                    guiLineString.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                }
                com.github.phalexei.sig.gui.Point first = new com.github.phalexei.sig.gui.Point(lineString.getGeometry().getPoint(0).getX(), lineString.getGeometry().getPoint(0).getY());
                guiLineString.addPoint(first);
                panel.addPrimitive(guiLineString);
            }
            resultSet.close();
            statement.close();
            
            //display result
            new GeoMainFrame("frame", panel);
        
        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }

    }

    /**
     * Question 10 C
     */
    private void question10_C() {

    	// Get DB connection
        Connection connection = Utils.getConnection();
        try {
            //prepare statement
            Statement statement = connection.createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("SELECT ST_Transform(linestring, 2154) from ways where tags->'boundary' = 'administrative' AND tags->'admin_level' < '7'");

            MapPanel panel = new MapPanel(698750, 6620131, 300000);
            Random random = new Random();
            //get result
            while (resultSet.next()) {
                org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(1));

                LineString guiLineString = new LineString(new Color(0, 0, random.nextInt(255)));
                for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i++) {
                    Point point = lineString.getGeometry().getPoint(i);
                    guiLineString.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                }
                panel.addPrimitive(guiLineString);
            }
            resultSet.close();
            statement.close();
            
            //display result
            new GeoMainFrame("frame", panel);
            
        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }

    }

    /**
     * Question 11 A
     */
    private void question11a() {
        // Get DB connection
        Connection connection = Utils.getConnection();
        try {
            //prepare statement
            Statement statement = connection.createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("select count(n.id), q.the_geom FROM nodes n, quartier q " +
                    "WHERE n.tags->'shop' = 'bakery' and ST_Intersects(ST_Transform(q.the_geom,4326), n.geom) " +
                    "GROUP BY q.the_geom ORDER BY count desc;");

            MapPanel panel = new MapPanel(919000, 6450000, 1000);

            Color[] colors = {Color.WHITE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.BLUE, Color.RED, Color.DARK_GRAY, Color.BLACK};
            //get result
            while (resultSet.next()) {
                PGgeometry g = (PGgeometry) resultSet.getObject(2);
                int count = resultSet.getInt(1);
                Color theColor = colors[Math.min(count, colors.length-1)];
                com.github.phalexei.sig.gui.Polygon poly = new Polygon(theColor, theColor);

                for (int i = 0; i < g.getGeometry().numPoints(); i++) {
                    poly.addPoint(new com.github.phalexei.sig.gui.Point(g.getGeometry().getPoint(i).getX(), g.getGeometry().getPoint(i).getY()));
                }
                panel.addPrimitive(poly);
            }
            resultSet.close();
            statement.close();
            
            //display result
            new GeoMainFrame("Question 11a", panel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
     * Question 11 B
     */
    private void question11b() {
        MapPanel panel = new MapPanel(844767, 6523077, 200000);
        //get result for railway
		drawNoisyArea("railway", "rail", Color.RED, Color.GREEN, panel);
		 //get result for highway
		drawNoisyArea("highway", "motorway", Color.BLUE, Color.YELLOW, panel);
		 //get result for aeroway
		drawNoisyArea("aeroway", "aerodrome", Color.WHITE, Color.BLACK, panel);
		//display result
		new GeoMainFrame("Question 11b", panel);

    }

    /**
     * Allow to fill the UI panel
     * @param key : tags->'key'
     * @param value tags->'key' = 'value'
     * @param colorLine road's color
     * @param colorNoise noise's color
     * @param panel map's panel
     */
	private void drawNoisyArea(String key, String value, Color colorLine, Color colorNoise, MapPanel panel) {
		 // Get DB connection
		Connection connection = Utils.getConnection();
        
		try {
            //prepare statement
            Statement statement = connection.createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("select ST_Buffer(ST_Transform(linestring, 2154), 300), " +
				"ST_Transform(linestring, 2154) from ways " +
				"WHERE tags->'" + key + "' = '" + value + "' ");
            //get result
            while (resultSet.next()) {
                org.postgis.PGgeometry lineStringBruit = ((org.postgis.PGgeometry) resultSet.getObject(1));
				org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(2));

				com.github.phalexei.sig.gui.Polygon poly = new Polygon(colorNoise, colorNoise);
				LineString guiLineString = new LineString(colorLine);

                for (int i = 0; i < lineStringBruit.getGeometry().numPoints() - 1; i++) {
                    Point point = lineStringBruit.getGeometry().getPoint(i);
                    poly.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                }
				for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i++) {
                    Point point = lineString.getGeometry().getPoint(i);
                    guiLineString.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                }
                panel.addPrimitive(poly);
				panel.addPrimitive(guiLineString);
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
