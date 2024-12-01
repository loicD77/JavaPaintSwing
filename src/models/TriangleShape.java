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
        // Met à jour les sommets pour définir un triangle
        vertices[1] = new Point(currentPoint.x, vertices[0].y); // sommet du côté horizontal
        vertices[2] = currentPoint; // point final
    }

    @Override
    public boolean contains(Point point) {
        // Utilise Polygon pour détecter si le point est dans le triangle
        Polygon polygon = new Polygon(
                new int[]{vertices[0].x, vertices[1].x, vertices[2].x},
                new int[]{vertices[0].y, vertices[1].y, vertices[2].y},
                3
        );
        return polygon.contains(point);
    }

    @Override
    public void move(int dx, int dy) {
        for (Point vertex : vertices) {
            vertex.translate(dx, dy);
        }
    }

    @Override
    public void resize(Point currentPoint) {
        // Redimensionne en ajustant le point final (sommet 2)
        vertices[2] = currentPoint;
    }
}
