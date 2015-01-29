package com.github.phalexei.sig.questions;

import com.github.phalexei.sig.gui.GeoMainFrame;
import com.github.phalexei.sig.gui.LineString;
import com.github.phalexei.sig.gui.MapPanel;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Question10a extends Question {
    @Override
    public void answerInternal() {
        try {
            //prepare statement
            Statement statement = this.getConnection().createStatement();

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
                    org.postgis.Point point = lineString.getGeometry().getPoint(i);
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
}
