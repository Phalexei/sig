package com.github.phalexei.sig.questions;

import com.github.phalexei.sig.gui.GeoMainFrame;
import com.github.phalexei.sig.gui.MapPanel;
import com.github.phalexei.sig.gui.Polygon;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Question11c extends Question {
    @Override
    public void answerInternal() {
        try {
            //prepare statement
            Statement statement = this.getConnection().createStatement();


            //get minimal and maximal point
            ResultSet resultSet = statement.executeQuery("SELECT "
                    + "min(ST_XMin(linestring)), min(ST_YMin(linestring)), max(ST_XMax(linestring)),max(ST_YMax(linestring)) "
                    + " from ways where tags ? 'building'");

            double xMin = 0, xMax = 0, yMin = 0, yMax = 0;

            while (resultSet.next()) {
                xMin = resultSet.getDouble(1);
                yMin = resultSet.getDouble(2);
                xMax = resultSet.getDouble(3);
                yMax = resultSet.getDouble(4);
            }

            // corners of the whole matric
            com.github.phalexei.sig.gui.Point pointMin = new com.github.phalexei.sig.gui.Point(xMin, yMin);
            com.github.phalexei.sig.gui.Point pointMax = new com.github.phalexei.sig.gui.Point(xMax, yMax);
            com.github.phalexei.sig.gui.Point tmp = new com.github.phalexei.sig.gui.Point(xMin, yMax);
            com.github.phalexei.sig.gui.Point tmp2 = new com.github.phalexei.sig.gui.Point(xMax, yMin);

            //calculate distance & pas (meter)
            String request = "SELECT ST_Distance("
                    + "ST_Transform(ST_GeomFromText('POINT(" + pointMin.getX() + " " + pointMin.getY() + ")',4326),26986),"
                    + "ST_Transform(ST_GeomFromText('POINT(" + tmp.getX() + " " + tmp.getY() + ")',4326),26986)"
                    + "),"
                    + "ST_Distance("
                    + "ST_Transform(ST_GeomFromText('POINT(" + pointMin.getX() + " " + pointMin.getY() + ")',4326),26986),"
                    + "ST_Transform(ST_GeomFromText('POINT(" + tmp2.getX() + " " + tmp2.getY() + ")',4326),26986)"
                    + ")";


            resultSet = statement.executeQuery(request);

            double distanceY = 0, distanceX = 0;
            while (resultSet.next()) {
                distanceY = resultSet.getDouble(1);
                distanceX = resultSet.getDouble(2);
            }

            int pas = 10000;
            int nbcaseX = (int) (distanceX / pas);
            int nbcaseY = (int) (distanceY / pas);
            int[][] matrix = new int[nbcaseX][nbcaseY];


            // get all buildings and fill matrix
            double pasX = (xMax - xMin) / nbcaseX;
            double pasY = (yMax - yMin) / nbcaseY;

            String requestBuilding = "select ST_X(ST_Centroid(bbox)), ST_Y(ST_Centroid(bbox)) from ways where tags ? 'building'";
            resultSet = statement.executeQuery(requestBuilding);

            while (resultSet.next()) {
                double x = resultSet.getDouble(1);
                double y = resultSet.getDouble(2);

                matrix[(int) ((x - xMin) / pasX)][(int) ((y - yMin) / pasY)] += 1;
            }

            MapPanel panel = new MapPanel(5, 45, 10);


            for (int i = 0; i < nbcaseX; i++) {
                for (int j = 0; j < nbcaseY; j++) {
                    Polygon p = new Polygon(Color.BLACK, getColor(matrix[i][j]));

                    p.addPoint(new com.github.phalexei.sig.gui.Point(xMin + i * pasX, yMin + j * pasY));
                    p.addPoint(new com.github.phalexei.sig.gui.Point(xMin + i * pasX, yMin + (j + 1) * pasY));
                    p.addPoint(new com.github.phalexei.sig.gui.Point(xMin + (i + 1) * pasX, yMin + (j + 1) * pasY));
                    p.addPoint(new com.github.phalexei.sig.gui.Point(xMin + (i + 1) * pasX, yMin + j * pasY));

                    panel.addPrimitive(p);
                }
            }

            new GeoMainFrame("Question 11 c", panel);
            resultSet.close();
            statement.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }

    }

    private Color getColor(int density) {
        // 100,000 is the max density we observed on this data set
        float color = 1 - (density / 100_000f);

        return new Color(color, color, color);
    }
}
