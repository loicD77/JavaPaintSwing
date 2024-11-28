package utils;

import models.Shape;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class FileManager {
    public static void saveShapes(ArrayList<Shape> shapes, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(shapes);
        }
    }

    public static ArrayList<Shape> loadShapes(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ArrayList<Shape>) ois.readObject();
        }
    }

    // Sauvegarde les points de gomme circulaire
    public static void saveEraserPoints(ArrayList<Point> eraserPoints, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(eraserPoints);
        }
    }

    // Charge les points de gomme circulaire
    public static ArrayList<Point> loadEraserPoints(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ArrayList<Point>) ois.readObject();
        }
    }
}
