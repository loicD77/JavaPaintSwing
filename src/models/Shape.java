package models;

import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable {
    protected Color color;

    public Shape(Color color) {
        this.color = color;
    }

    public abstract void draw(Graphics g);

    public abstract void update(Point currentPoint);

    // Détecte si un point est à l'intérieur de la forme
    public abstract boolean contains(Point point);

    // Déplace la forme en mettant à jour sa position
    public abstract void move(int dx, int dy);

    // Redimensionne la forme
    public abstract void resize(Point currentPoint);
}
