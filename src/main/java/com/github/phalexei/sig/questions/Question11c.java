package com.github.phalexei.sig.questions;

import com.github.phalexei.sig.gui.MapPanel;

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
           
            Point pointMin = new Point(xMin, yMin);
            Point pointMax = new Point(xMax, yMax);
            
            Point tmp = new Point(xMin, yMax);
            
            //calculate distance & pas
            
            //sqlrequest st_distance(pointmax,new point(xmin,ymax))
            
            String request = "SELECT ST_Distance("
            		+"ST_GeomFromText('POINT("+pointMin.getX()+" "+pointMin.getY()+")',4326),"
            		+"ST_GeomFromText('POINT("+tmp.getX()+" "+tmp.getY()+")',4326)"
            		+")";
            
            resultSet = statement.executeQuery(request);
            
            double distance = 0;
            while (resultSet.next()) {
            	 distance = resultSet.getDouble(1);
            }
            
            //km
            System.out.println(distance);
            //km
            int pas = 100;
            int nbcase = (int) (distance / pas);
            int[] matrix = new int[nbcase];
            
            
            //pour chaque case, requete intersect
            	//count++ de la case
            
            //display result
            // new GeoMainFrame("frame", panel);
            
            resultSet.close();
            statement.close();
            

        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }

    }
}
