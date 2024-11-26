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
        g.drawRect(
                Math.min(startPoint.x, endPoint.x),
                Math.min(startPoint.y, endPoint.y),
                Math.abs(endPoint.x - startPoint.x),
                Math.abs(endPoint.y - startPoint.y)
        );
    }

    @Override
    public void update(Point currentPoint) {
        this.endPoint = currentPoint;
    }

    @Override
    public void move(int dx, int dy) {
        startPoint.translate(dx, dy);
        endPoint.translate(dx, dy);
    }

    @Override
    public void resize(Point point) {
        this.endPoint = point;
    }

    @Override
    public boolean contains(Point point) {
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(endPoint.x - startPoint.x);
        int height = Math.abs(endPoint.y - startPoint.y);

        return (point.x >= x && point.x <= x + width && point.y >= y && point.y <= y + height);
    }
}
