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
}
