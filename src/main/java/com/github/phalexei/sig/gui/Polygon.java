/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.phalexei.sig.gui;

import java.awt.*;
import java.util.Iterator;

/**
 * A polygon (roughly a sequence of points, interpreted as a closed shape).
 *
 * @author Sylvain B.
 * @version 1.0
 */
public class Polygon implements GraphicalPrimitive {
    /**
     * The fill color of the shape.
     */
    public Color fillColor;
    /**
     * The drawing color of the shape
     */
    public Color drawColor;
    private LineString ls;

    /**
     * Initializes an empty polygon (with no point at the beginning).
     *
     * @param drawColor the drawing color.
     * @param fillColor the fill color.
     */
    public Polygon(Color drawColor, Color fillColor) {
        this.drawColor = drawColor;
        this.fillColor = fillColor;
        this.ls = new LineString();
    }

    /**
     * Initializes an empty polygon (with no point at the beginning) with default drawing color (strong gray, half opacity)
     * and default fill color (gray, half opacity).
     */
    public Polygon() {
        this(new Color(200, 200, 200, 220), new Color(100, 100, 100, 120));
    }

    @Override
    public void draw(Graphics2D g2d, CoordinateConverter converter) {
        Color oldDC = g2d.getColor();
        Iterator<Point> iter = ls.iterator();
        java.awt.Polygon poly = new java.awt.Polygon();
        while (iter.hasNext()) {
            Point p1 = iter.next();
            poly.addPoint(converter.xMapToScreen(p1.getX()), converter.yMapToScreen(p1.getY()));
        }
        g2d.setColor(this.fillColor);
        g2d.fillPolygon(poly);
        g2d.setColor(this.drawColor);
        g2d.drawPolygon(poly);
        g2d.setColor(oldDC);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return ls.getBoundingBox();
    }

    /**
     * Adds a point to the polygon (at the end of the current sequence).
     *
     * @param point the point to be added.
     */
    public void addPoint(Point point) {
        this.ls.addPoint(point);
    }
}
