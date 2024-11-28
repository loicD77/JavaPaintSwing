package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeManager {
    private List<Shape> shapes = new ArrayList<>();
    private Shape selectedShape = null;

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public void drawAllShapes(Graphics g) {
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }

    public void selectShape(Point point) {
        selectedShape = null;
        for (Shape shape : shapes) {
            if (shape.contains(point)) {
                selectedShape = shape;
                break;
            }
        }
    }

    public void deleteSelectedShape() {
        if (selectedShape != null) {
            shapes.remove(selectedShape);
            selectedShape = null;
        }
    }

    public void moveSelectedShape(int dx, int dy) {
        if (selectedShape != null) {
            selectedShape.move(dx, dy);
        }
    }

    public void resizeSelectedShape(Point point) {
        if (selectedShape != null) {
            selectedShape.resize(point);
        }
    }
}
