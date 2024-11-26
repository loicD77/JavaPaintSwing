package models;

import java.awt.*;

public class CircleShape extends Shape {
    private Point center;
    private int radius;

    public CircleShape(Color color, Point center) {
        super(color);
        this.center = center;
        this.radius = 0; // Initialisation avec un rayon nul
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void update(Point currentPoint) {
        // Calcule le rayon comme la distance entre le centre et le point actuel
        this.radius = (int) center.distance(currentPoint);
    }

    @Override
    public boolean contains(Point point) {
        // Vérifie si le point est dans le cercle
        return center.distance(point) <= radius;
    }

    @Override
    public void move(int dx, int dy) {
        center.translate(dx, dy);
    }

    @Override
    public void resize(Point currentPoint) {
        // Redimensionne en recalculant le rayon
        this.radius = (int) center.distance(currentPoint);
    }
}
