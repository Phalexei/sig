package com.github.phalexei.sig.questions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Question9 extends Question {

    private final String name;

    public Question9(String arg) {
        this.name = arg;
    }

    @Override
    public void answerInternal() {

        try {
            //prepare statement
            PreparedStatement statement = this.getConnection().prepareStatement(
                    "select tags->'name' as nom, ST_X(geom) as longitude," +
                            " ST_Y(geom) as latitude from nodes where tags->'name' like ? || '%'");
            //add string
            statement.setString(1, name);
            //execute request
            ResultSet resultSet = statement.executeQuery();
            //display result
            System.out.println("Résultats question 9 :");
            while (resultSet.next()) {
                System.out.println("nom = " + resultSet.getString(1) + ", longitude = " + resultSet.getDouble(2) + ", latitude = " + resultSet.getDouble(3));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException se) {
            System.err.println("Threw a SQLException creating the list of blogs.");
            System.err.println(se.getMessage());
        }
    }
}
