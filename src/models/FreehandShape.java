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
        if (points.isEmpty()) {
            return;
        }

        // Calculer la boîte englobante de la forme
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

        for (Point p : points) {
            minX = Math.min(minX, p.x);
            maxX = Math.max(maxX, p.x);
            minY = Math.min(minY, p.y);
            maxY = Math.max(maxY, p.y);
        }

        // Point central de la forme
        Point center = new Point((minX + maxX) / 2, (minY + maxY) / 2);

        // Calculer le facteur d'échelle basé sur la distance depuis le centre
        double distanceToCurrent = center.distance(currentPoint);
        double distanceToMax = center.distance(new Point(maxX, maxY));
        double scaleFactor = distanceToCurrent / distanceToMax;

        // Appliquer le redimensionnement aux points
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            int newX = (int) (center.x + (p.x - center.x) * scaleFactor);
            int newY = (int) (center.y + (p.y - center.y) * scaleFactor);
            points.set(i, new Point(newX, newY));
        }
    }
}
