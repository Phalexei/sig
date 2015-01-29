package com.github.phalexei.sig.questions;

import com.github.phalexei.sig.gui.GeoMainFrame;
import com.github.phalexei.sig.gui.LineString;
import com.github.phalexei.sig.gui.MapPanel;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Question11b extends Question {
    @Override
    public void answerInternal() {
        MapPanel panel = new MapPanel(844767, 6523077, 200000);
        //get result for highway
        drawNoisyArea("highway", "motorway", Color.BLUE, Color.YELLOW, panel, 300);
        //get result for railway
        drawNoisyArea("railway", "rail", Color.RED, Color.GREEN, panel, 500);
        //get result for aeroway
        drawNoisyArea("aeroway", "aerodrome", Color.WHITE, Color.BLACK, panel, 1000);
        //display result
        new GeoMainFrame("Question 11b", panel);
    }

    /**
     * Allow to fill the UI panel
     *
     * @param key        : tags->'key'
     * @param value      tags->'key' = 'value'
     * @param colorLine  road's color
     * @param colorNoise noise's color
     * @param panel      map's panel
     */
    private void drawNoisyArea(String key, String value, Color colorLine, Color colorNoise, MapPanel panel, int noiseFactor) {
        try {
            //prepare statement
            Statement statement = this.getConnection().createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("select ST_Buffer(ST_Transform(linestring, 2154), " + noiseFactor + "), " +
                    "ST_Transform(linestring, 2154) from ways " +
                    "WHERE tags->'" + key + "' = '" + value + "' ");
            //get result
            while (resultSet.next()) {
                org.postgis.PGgeometry lineStringBruit = ((org.postgis.PGgeometry) resultSet.getObject(1));
                org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(2));

                com.github.phalexei.sig.gui.Polygon poly = new com.github.phalexei.sig.gui.Polygon(colorNoise, colorNoise);
                LineString guiLineString = new LineString(colorLine);

                for (int i = 0; i < lineStringBruit.getGeometry().numPoints() - 1; i++) {
                    org.postgis.Point point = lineStringBruit.getGeometry().getPoint(i);
                    poly.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                }
                for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i++) {
                    org.postgis.Point point = lineString.getGeometry().getPoint(i);
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
