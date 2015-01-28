package com.github.phalexei.sig;

import com.github.phalexei.sig.database.Utils;

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
    private void question9(String name) {
        // Get DB connection
        Connection connection = Utils.getConnection();

        try {
            //prepare statement
            PreparedStatement statement = connection.prepareStatement(
                    "select tags->'name' as nom, ST_X(geom) as longitude," +
                            " ST_Y(geom) as latitude from nodes where tags->'name' like ? || '%'");
            //add string
            statement.setString(1, name);
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

    }
}
