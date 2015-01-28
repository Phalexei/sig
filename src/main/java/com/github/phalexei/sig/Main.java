package com.github.phalexei.sig;

import com.github.phalexei.sig.database.Utils;
import com.github.phalexei.sig.gui.GeoMainFrame;
import com.github.phalexei.sig.gui.LineString;
import com.github.phalexei.sig.gui.MapPanel;
import org.postgis.Point;

import java.awt.*;
import java.sql.*;
import java.util.Random;

public class Main {

    public Main(String arg) {
        if (!arg.isEmpty()) {
            question9(arg);
        }

        question10();
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
     * Question 10
     */
    private void question10() {
        // Get DB connection
        Connection connection = Utils.getConnection();
        try {
            //prepare statement
            Statement statement = connection.createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("SELECT ST_Transform(linestring, 2154) from ways where ST_Intersects(ST_SetSRID" +
                    "(ST_MakeBox2D(ST_Point(5.7, 45.1), ST_Point(5.8, 45.2)), 4326), linestring)" +
                    "AND tags ? 'highway'");

            //MapPanel panel = new MapPanel(5.75, 45.15, 0.25);
            MapPanel panel = new MapPanel(919000, 6450000, 1000);
            Random random = new Random();
            //display result
            while (resultSet.next()) {
                org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(1));

                LineString guiLineString = new LineString(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i++) {
                    Point point = lineString.getGeometry().getPoint(i);
                    guiLineString.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                    System.out.print("point.getX() = " + point.getX());
                    System.out.println(":point.getY() = " + point.getY());
                }
                panel.addPrimitive(guiLineString);
            }
            resultSet.close();
            statement.close();
            new GeoMainFrame("frame", panel);
        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }
    }

    /**
     * Question 11
     */
    private void question11() {

    }
}
