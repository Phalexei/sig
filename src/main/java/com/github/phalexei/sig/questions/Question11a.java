package com.github.phalexei.sig.questions;

import com.github.phalexei.sig.gui.GeoMainFrame;
import com.github.phalexei.sig.gui.MapPanel;
import org.postgis.PGgeometry;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Question11a extends Question {
    @Override
    public void answerInternal() {
        try {
            //prepare statement
            Statement statement = this.getConnection().createStatement();

            //execute request
            ResultSet resultSet = statement.executeQuery("select count(n.id), q.the_geom FROM nodes n, quartier q " +
                    "WHERE n.tags->'shop' = 'bakery' and ST_Intersects(ST_Transform(q.the_geom,4326), n.geom) " +
                    "GROUP BY q.the_geom ORDER BY count desc;");

            MapPanel panel = new MapPanel(919000, 6450000, 1000);

            Color[] colors = {Color.WHITE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.BLUE, Color.RED, Color.DARK_GRAY, Color.BLACK};
            //get result
            while (resultSet.next()) {
                PGgeometry g = (PGgeometry) resultSet.getObject(2);
                int count = resultSet.getInt(1);
                Color theColor = colors[Math.min(count, colors.length - 1)];
                com.github.phalexei.sig.gui.Polygon poly = new com.github.phalexei.sig.gui.Polygon(theColor, Color.WHITE);

                for (int i = 0; i < g.getGeometry().numPoints(); i++) {
                    poly.addPoint(new com.github.phalexei.sig.gui.Point(g.getGeometry().getPoint(i).getX(), g.getGeometry().getPoint(i).getY()));
                }
                panel.addPrimitive(poly);
            }
            resultSet.close();
            statement.close();

            //display result
            new GeoMainFrame("Question 11a", panel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
