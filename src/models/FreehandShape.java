package models;

import java.awt.*;
import java.util.ArrayList;

public class FreehandShape extends Shape {
    private ArrayList<Point> points = new ArrayList<>();

    public FreehandShape(Color color, Point startPoint) {
        super(color);
        points.add(startPoint);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public void update(Point currentPoint) {
        points.add(currentPoint);
    }

    @Override
    public boolean contains(Point point) {
        // Vérifie si un point est proche d’un des points de la forme
        for (Point p : points) {
            if (p.distance(point) < 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(int dx, int dy) {
        for (Point p : points) {
            p.translate(dx, dy);
        }
    }

    @Override
    public void resize(Point currentPoint) {
        // Non applicable pour FreehandShape
    }
}
