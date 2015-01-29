package com.github.phalexei.sig;

import com.github.phalexei.sig.questions.Question;
import com.sun.javaws.exceptions.InvalidArgumentException;

public class Main {

    /**
     * @param args Arguments for
     *             <li> Question9 : 9 dom__ne uni_er_it
     *             <li> Question10_A : 10 A
     *             <li> Question10_B : 10 B
     *             <li> Question10_C : 10 C
     *             <li> Question11_A : 11 A
     *             <li> Question11_B : 11 B
     */
    public static void main(String[] args) {
<<<<<<< HEAD
        
        if(args[0].equals("9")){
        	new Main().question9(parseArguments(args));
        }else if (args[0].equals("10") && args[1].equals("A")){
        	new Main().question10_A();
        }else if (args[0].equals("10") && args[1].equals("B")){
        	new Main().question10_B();
        }else if (args[0].equals("10") && args[1].equals("C")){
        	new Main().question10_C();
        }else if (args[0].equals("11") && args[1].equals("A")){
        	new Main().question11_A();
        }else if (args[0].equals("11") && args[1].equals("B")){
        	new Main().question11_B();
        }else if (args[0].equals("11") && args[1].equals("C")){
        	new Main().question11_C();
        }
        else{
        	System.out.println("Mauvais arguments");
        	return;
=======
        try {
            parseArguments(args).answer();
        } catch (InvalidArgumentException e) {
            System.out.println(e.getRealMessage());
>>>>>>> 4711f3c1ed4888f0ec7cdfd767e016e57113ecbb
        }
    }

    /**
     * Returns the question corresponding to arguments
     *
     * @param args : array args (from main)
     * @return
     */
    public static Question parseArguments(String[] args) throws InvalidArgumentException {

        int questionNumber = Integer.parseInt(args[0]);
        Character questionLetter = null;
        StringBuilder s = new StringBuilder();

        if (questionNumber == 10 | questionNumber == 11 && args[1].length() == 1) {
            questionLetter = args[1].charAt(0);
        } else {
            for (int i = 1; i < args.length; i++) {
                System.out.println(args[i]);
                s.append(args[i]).append(" ");
            }

            if (s.length() != 0) {
                s.deleteCharAt(s.length() - 1);
            }
        }

        return Question.newQuestion(questionNumber, questionLetter, s.toString());
    }
<<<<<<< HEAD
    
    /**
     * Question 11 C
     */
    private void question11_C() {

        // Get DB connection
        Connection connection = Utils.getConnection();
        try {
            //prepare statement
            Statement statement = connection.createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("SELECT ST_Transform(linestring, 27563) from ways " +
                    "where tags ? 'building' order by ST_YMin(linestring), St_XMin(linestring) limit 10;");

            MapPanel panel = new MapPanel(919000, 6450000, 1000);
            Random random = new Random();
            //get result
            while (resultSet.next()) {
//                org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(1));
//
//                LineString guiLineString = new LineString(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
//                for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i++) {
//                    Point point = lineString.getGeometry().getPoint(i);
//                    guiLineString.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
//                }
//                com.github.phalexei.sig.gui.Point first = new com.github.phalexei.sig.gui.Point(lineString.getGeometry().getPoint(0).getX(), lineString.getGeometry().getPoint(0).getY());
//                guiLineString.addPoint(first);
//                panel.addPrimitive(guiLineString);
                System.out.println(resultSet.getObject(1).toString());
            }
            resultSet.close();
            statement.close();
            
            //display result
//            new GeoMainFrame("frame", panel);
        
        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }

    }

    /**
     * Allow to fill the UI panel
     * @param key : tags->'key'
     * @param value tags->'key' = 'value'
     * @param colorLine road's color
     * @param colorNoise noise's color
     * @param panel map's panel
     */
	private void drawNoisyArea(String key, String value, Color colorLine, Color colorNoise, MapPanel panel) {
		 // Get DB connection
		Connection connection = Utils.getConnection();
        
		try {
            //prepare statement
            Statement statement = connection.createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("select ST_Buffer(ST_Transform(linestring, 2154), 300), " +
				"ST_Transform(linestring, 2154) from ways " +
				"WHERE tags->'" + key + "' = '" + value + "' ");
            //get result
            while (resultSet.next()) {
                org.postgis.PGgeometry lineStringBruit = ((org.postgis.PGgeometry) resultSet.getObject(1));
				org.postgis.PGgeometry lineString = ((org.postgis.PGgeometry) resultSet.getObject(2));

				com.github.phalexei.sig.gui.Polygon poly = new Polygon(colorNoise, colorNoise);
				LineString guiLineString = new LineString(colorLine);

                for (int i = 0; i < lineStringBruit.getGeometry().numPoints() - 1; i++) {
                    Point point = lineStringBruit.getGeometry().getPoint(i);
                    poly.addPoint(new com.github.phalexei.sig.gui.Point(point.getX(), point.getY()));
                }
				for (int i = 0; i < lineString.getGeometry().numPoints() - 1; i++) {
                    Point point = lineString.getGeometry().getPoint(i);
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
=======
>>>>>>> 4711f3c1ed4888f0ec7cdfd767e016e57113ecbb
}
