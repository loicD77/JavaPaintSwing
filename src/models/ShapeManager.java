package models;

import java.awt.*;
import java.util.ArrayList;

public class ShapeManager {
    private ArrayList<Shape> shapes = new ArrayList<>();

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public void drawAllShapes(Graphics g) {
        for (Shape shape : shapes) {
            shape.draw(g); // Dessine chaque forme
        }
    }
}
