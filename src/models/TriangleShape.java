package models;

import java.awt.*;

public class TriangleShape extends Shape {
    private Point[] vertices = new Point[3];

    public TriangleShape(Color color, Point startPoint) {
        super(color);
        vertices[0] = startPoint;
        vertices[1] = startPoint;
        vertices[2] = startPoint;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int[] xPoints = {vertices[0].x, vertices[1].x, vertices[2].x};
        int[] yPoints = {vertices[0].y, vertices[1].y, vertices[2].y};
        g.drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void update(Point currentPoint) {
        vertices[1] = new Point(currentPoint.x, vertices[0].y);
        vertices[2] = currentPoint;
    }
}
