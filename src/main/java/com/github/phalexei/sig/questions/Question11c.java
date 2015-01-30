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
            
            //System.out.println(pointMin.getX());
            //System.out.println(pointMin.getY());
            //System.out.println(pointMax.getX());
            //System.out.println(pointMax.getY());
            
            com.github.phalexei.sig.gui.Point tmp = new com.github.phalexei.sig.gui.Point(xMin, yMax);
            
            //calculate distance & pas (meter)
            String request = "SELECT ST_Distance("
            		+"ST_Transform(ST_GeomFromText('POINT("+pointMin.getX()+" "+pointMin.getY()+")',4326),26986),"
            		+"ST_Transform(ST_GeomFromText('POINT("+tmp.getX()+" "+tmp.getY()+")',4326),26986)"
            		+")";
            
            
            resultSet = statement.executeQuery(request);
            
            //System.out.println(statement.toString());
            
            double distance = 0;
            while (resultSet.next()) {
            	 distance = resultSet.getDouble(1);
            }
            
            //System.out.println("distance (m) : "+distance);
            
            int pas = 10000;
            int nbcase = (int) (distance / pas);
            System.out.println("matrice de :"+nbcase+"x"+nbcase);
            int [][] matrix = new int[nbcase][nbcase];
          
            
            // get all buildings and fill matrix
            double pasX = (xMax-xMin) / nbcase;
            double pasY = (yMax-yMin) / nbcase;
            
            System.out.println("pas x : "+pasX);
            System.out.println("pas y : "+pasY);
            
            String requestBuilding = "select ST_X(ST_Centroid(bbox)), ST_Y(ST_Centroid(bbox)) from ways where tags ? 'building'";
            resultSet = statement.executeQuery(requestBuilding);
   		 	while (resultSet.next()) {
   		 		
   		 		double x = resultSet.getDouble(1);
   		 		double y = resultSet.getDouble(2);
   		 		
   		 		matrix[(int) ((x -xMin) / pasX)][(int) ((y - yMin) / pasY)] += 1;
   		
   		 	}
            
            /*for (int i = 0; i < nbcase; i++){
            	for (int j = 0; j < nbcase; j++){
                	System.out.print(matrix[i][j]+" ");
                }
            	System.out.println();
            }*/
            
            //display result
             
            
            resultSet.close();
            statement.close();
            

        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }

    }
}
