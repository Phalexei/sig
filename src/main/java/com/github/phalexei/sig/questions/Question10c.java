package com.github.phalexei.sig.questions;

import com.github.phalexei.sig.gui.GeoMainFrame;
import com.github.phalexei.sig.gui.LineString;
import com.github.phalexei.sig.gui.MapPanel;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Question10c extends Question {
    @Override
    public void answerInternal() {
        try {
            //prepare statement
            Statement statement = this.getConnection().createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("SELECT ST_Transform(linestring, 2154) from ways where tags->'boundary' = 'administrative' AND tags->'admin_level' < '7'");

            MapPanel panel = new MapPanel(698750, 6620131, 300000);
            Random random = new Random();
            //get result
            while (resultSet.next()) {
                org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(1));

                LineString guiLineString = new LineString(new Color(0, 0, random.nextInt(255)));
                for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i++) {
                    org.postgis.Point point = lineString.getGeometry().getPoint(i);
                    guiLineString.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                }
                panel.addPrimitive(guiLineString);
            }
            resultSet.close();
            statement.close();

            //display result
            new GeoMainFrame("frame", panel);

        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

    }
}
