package com.github.phalexei.sig.questions;

import com.github.phalexei.sig.gui.GeoMainFrame;
import com.github.phalexei.sig.gui.MapPanel;
import com.github.phalexei.sig.gui.Polygon;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.postgis.Point;

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
           
            com.github.phalexei.sig.gui.Point pointMin = new com.github.phalexei.sig.gui.Point(xMin, yMin);
            com.github.phalexei.sig.gui.Point pointMax = new com.github.phalexei.sig.gui.Point(xMax, yMax);
            
            com.github.phalexei.sig.gui.Point tmp = new com.github.phalexei.sig.gui.Point(xMin, yMax);
            
            //calculate distance & pas (meter)
             
            String request = "SELECT ST_Distance("
            		+"ST_Transform(ST_GeomFromText('POINT("+pointMin.getX()+" "+pointMin.getY()+")',4326),26986),"
            		+"ST_Transform(ST_GeomFromText('POINT("+tmp.getX()+" "+tmp.getY()+")',4326),26986)"
            		+")";
            
            
            resultSet = statement.executeQuery(request);
            
            System.out.println(statement.toString());
            
            double distance = 0;
            while (resultSet.next()) {
            	 distance = resultSet.getDouble(1);
            }
           
            System.out.println(distance);
           
            int pas = 10000;
            int nbcase = (int) (distance / pas);
            System.out.println(nbcase);
            int[] matrix = new int[nbcase];
            
            Polygon p = new Polygon(Color.RED, Color.BLACK);
            p.addPoint(pointMin);
            p.addPoint(tmp);
            p.addPoint(new com.github.phalexei.sig.gui.Point(xMax, yMin));
            p.addPoint(pointMax);
           
            
            
            MapPanel panel = new MapPanel(2, 46, 200);
            panel.addPrimitive(p);
           
          
           
            new GeoMainFrame("frame", panel);
            
            //pour chaque case, requete intersect
            	//count++ de la case
            
            //display result
             
            
            resultSet.close();
            statement.close();
            

        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }

    }
}
