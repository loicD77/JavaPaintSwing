package gui;

import models.Shape;
import models.ShapeManager;
import models.RectangleShape;
import models.CircleShape;
import models.TriangleShape;
import models.FreehandShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import utils.FileManager;

public class DrawingPanel extends JPanel {
    private Color currentColor = Color.BLACK; // Couleur actuelle
    private String drawMode = "Rectangle"; // Mode de dessin actuel
    private ShapeManager shapeManager = new ShapeManager();
    private Shape currentShape = null; // Forme en cours de dessin

    public DrawingPanel() {
        setBackground(Color.WHITE);

        // Gérer les événements de la souris
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Démarrer une nouvelle forme selon le mode actif
                switch (drawMode) {
                    case "Rectangle":
                        currentShape = new RectangleShape(currentColor, e.getPoint());
                        break;
                    case "Circle":
                        currentShape = new CircleShape(currentColor, e.getPoint());
                        break;
                    case "Triangle":
                        currentShape = new TriangleShape(currentColor, e.getPoint());
                        break;
                    case "Freehand":
                        currentShape = new FreehandShape(currentColor, e.getPoint());
                        break;
                }
                if (currentShape != null) {
                    shapeManager.addShape(currentShape);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Terminer la forme courante
                if (currentShape != null) {
                    currentShape.update(e.getPoint());
                    currentShape = null;
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Mettre à jour la forme pendant que la souris est déplacée
                if (currentShape != null) {
                    currentShape.update(e.getPoint());
                    repaint();
                }
            }
        });
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color; // Met à jour la couleur
    }

    public void setDrawMode(String mode) {
        this.drawMode = mode; // Change le mode de dessin
    }

    public void saveShapesToFile(String filePath) {
        try {
            FileManager.saveShapes(shapeManager.getShapes(), filePath);
            JOptionPane.showMessageDialog(this, "Shapes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save shapes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadShapesFromFile(String filePath) {
        try {
            shapeManager.setShapes(FileManager.loadShapes(filePath));
            repaint();
            JOptionPane.showMessageDialog(this, "Shapes loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load shapes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        shapeManager.drawAllShapes(g); // Dessine toutes les formes
    }
}
