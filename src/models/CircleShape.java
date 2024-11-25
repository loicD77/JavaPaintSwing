package models;

import java.awt.*;

public class CircleShape extends Shape {
    private Point center;
    private int radius;

    public CircleShape(Color color, Point center) {
        super(color);
        this.center = center;
        this.radius = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void update(Point currentPoint) {
        this.radius = (int) center.distance(currentPoint);
    }
}
