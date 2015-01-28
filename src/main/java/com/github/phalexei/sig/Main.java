package com.github.phalexei.sig;

import com.github.phalexei.sig.database.Utils;
import com.github.phalexei.sig.gui.*;
import com.github.phalexei.sig.gui.Point;
import com.github.phalexei.sig.gui.Polygon;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public Main(String arg) {
        //Question 9 : OK using arg = "Dom__ne _niversit" (2 words)
        if (!arg.isEmpty()) {
            question9(arg);
        }

        // TODO : other questions using UI
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
    private void question9(String arg) {
        // Get DB connection
        Connection connection = Utils.getConnection();

        try {
            //prepare statement
            PreparedStatement statement = connection.prepareStatement(
                    "select tags->'name' as nom, ST_X(geom) as longitude," +
                            " ST_Y(geom) as latitude from nodes where tags->'name' like ? || '%'");
            //add string
            statement.setString(1, arg);
            //display request
            System.out.println(statement.toString());
            //execute request
            ResultSet resultSet = statement.executeQuery();
            //display result
            while (resultSet.next()) {
                System.out.println("nom = " + resultSet.getString(1) + ", longitude = " + resultSet.getDouble(2) + ", latitude = " + resultSet.getDouble(3));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException se) {
            System.err
                    .println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }
    }

    private void question10() {
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

        new GeoMainFrame("frame", map);

        // MAIS C'EST DE LA MERDE CE TP ...

    }
}
