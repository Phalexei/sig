package com.github.phalexei.sig.questions;

import com.github.phalexei.sig.gui.MapPanel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Question11c extends Question {
    @Override
    public void answerInternal() {
        try {
            //prepare statement
            Statement statement = this.getConnection().createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("SELECT ST_Transform(linestring, 27563) from ways " +
                    "where tags ? 'building' order by ST_YMin(linestring), St_XMin(linestring) limit 10;");

            MapPanel panel = new MapPanel(919000, 6450000, 1000);
            Random random = new Random();
            //get result
            while (resultSet.next()) {
                // org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(1));
                //
                // LineString guiLineString = new LineString(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                // for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i) {
                // Point point = lineString.getGeometry().getPoint(i);
                // guiLineString.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                // }
                // com.github.phalexei.sig.gui.Point first = new com.github.phalexei.sig.gui.Point(lineString.getGeometry().getPoint(0).getX(), lineString.getGeometry().getPoint(0).getY());
                // guiLineString.addPoint(first);
                // panel.addPrimitive(guiLineString);
                System.out.println(resultSet.getObject(1).toString());
            }
            resultSet.close();
            statement.close();

            //display result
            // new GeoMainFrame("frame", panel);

        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }

    }
}
