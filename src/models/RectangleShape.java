package models;

import java.awt.*;

public class RectangleShape extends Shape {
    private Point startPoint;
    private Point endPoint;

    public RectangleShape(Color color, Point startPoint) {
        super(color);
        this.startPoint = startPoint;
        this.endPoint = startPoint;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void update(Point currentPoint) {
        this.endPoint = currentPoint;
    }
}
