package gui;

import models.Shape;
import models.ShapeManager;
import models.RectangleShape;
import models.CircleShape;
import models.TriangleShape;
import models.FreehandShape;
import utils.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


public class DrawingPanel extends JPanel {
    private Color currentColor = Color.BLACK; // Couleur actuelle
    private String drawMode = "Rectangle"; // Mode de dessin actuel
    private ShapeManager shapeManager = new ShapeManager();
    private Shape currentShape = null; // Forme en cours de création
    private Point lastDragPoint = null; // Point pour le déplacement des formes
    private ArrayList<Point> eraserPoints = new ArrayList<>(); // Liste des points effacés
    private static final int ERASER_RADIUS = 20; // Rayon de la gomme circulaire

    public DrawingPanel() {
        setBackground(Color.WHITE);

        // Gestion des clics de la souris
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseReleased(e);
            }
        });

        // Gestion du déplacement de la souris (dessin, déplacement ou redimensionnement)
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }
        });
    }

    private void handleMousePressed(MouseEvent e) {
        switch (drawMode) {
            case "Rectangle":
            case "Circle":
            case "Triangle":
                // Créer une nouvelle forme
                currentShape = createShape(drawMode, currentColor, e.getPoint());
                if (currentShape != null) {
                    shapeManager.addShape(currentShape);
                }
                break;
            case "Freehand":
                // Initialiser une forme à main levée
                currentShape = new FreehandShape(currentColor, e.getPoint());
                shapeManager.addShape(currentShape);
                break;
            case "Eraser":
                // Supprimer la forme sous le point cliqué
                shapeManager.selectShape(e.getPoint());
                shapeManager.deleteSelectedShape();
                break;
            case "CircleEraser":
                // Ajouter la position de la gomme pour effacer partiellement
                eraserPoints.add(e.getPoint());
                eraseInCircle(e.getPoint());
                break;
            case "Move":
                // Sélectionner une forme pour la déplacer
                shapeManager.selectShape(e.getPoint());
                lastDragPoint = e.getPoint(); // Enregistrer le point initial pour le déplacement
                break;
            case "Resize":
                // Sélectionner une forme pour la redimensionner
                shapeManager.selectShape(e.getPoint());
                break;
        }
        repaint();
    }

    private void handleMouseReleased(MouseEvent e) {
        currentShape = null; // Réinitialiser la forme en cours
        lastDragPoint = null; // Réinitialiser le point de glissement
    }

    private void handleMouseDragged(MouseEvent e) {
        if (currentShape != null) {
            // Mettre à jour la forme en cours de dessin
            currentShape.update(e.getPoint());
        } else if ("Move".equals(drawMode) && lastDragPoint != null) {
            // Déplacer la forme sélectionnée
            int dx = e.getX() - lastDragPoint.x;
            int dy = e.getY() - lastDragPoint.y;
            shapeManager.moveSelectedShape(dx, dy);
            lastDragPoint = e.getPoint(); // Mettre à jour le dernier point glissé
        } else if ("Resize".equals(drawMode)) {
            // Redimensionner la forme sélectionnée
            shapeManager.resizeSelectedShape(e.getPoint());
        } else if ("CircleEraser".equals(drawMode)) {
            // Ajouter la position de la gomme pour effacer partiellement
            eraserPoints.add(e.getPoint());
            eraseInCircle(e.getPoint());
        }
        repaint();
    }

    // Crée une nouvelle forme en fonction du mode actuel
    private Shape createShape(String mode, Color color, Point startPoint) {
        switch (mode) {
            case "Rectangle":
                return new RectangleShape(color, startPoint);
            case "Circle":
                return new CircleShape(color, startPoint);
            case "Triangle":
                return new TriangleShape(color, startPoint);
            case "Freehand":
                return new FreehandShape(color, startPoint);
            default:
                return null;
        }
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setDrawMode(String mode) {
        this.drawMode = mode;

        // Modifier le curseur de la souris en fonction du mode
        if ("CircleEraser".equals(mode)) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            BufferedImage cursorImg = new BufferedImage(ERASER_RADIUS * 2, ERASER_RADIUS * 2, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = cursorImg.createGraphics();
            g2d.setColor(Color.BLACK);
            g2d.drawOval(0, 0, ERASER_RADIUS * 2 - 1, ERASER_RADIUS * 2 - 1);
            g2d.dispose();
            Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(ERASER_RADIUS, ERASER_RADIUS), "Gomme Circulaire");
            setCursor(customCursor);
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    public void saveShapesToFile(String filePath) {
        try {
            FileManager.saveShapes(new ArrayList<>(shapeManager.getShapes()), filePath);
            FileManager.saveEraserPoints(eraserPoints, filePath + ".eraser");
            JOptionPane.showMessageDialog(this, "Shapes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save shapes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadShapesFromFile(String filePath) {
        try {
            resetDrawingPanel(); // Réinitialiser le panneau avant de charger un nouveau fichier
            shapeManager.setShapes(FileManager.loadShapes(filePath));
            eraserPoints = FileManager.loadEraserPoints(filePath + ".eraser");
            repaint();
            JOptionPane.showMessageDialog(this, "Shapes loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Failed to load shapes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        shapeManager.drawAllShapes(g); // Dessiner toutes les formes

        // Redessiner les zones effacées par la gomme circulaire
        g.setColor(getBackground());
        for (Point point : eraserPoints) {
            g.fillOval(point.x - ERASER_RADIUS, point.y - ERASER_RADIUS, ERASER_RADIUS * 2, ERASER_RADIUS * 2);
        }
    }

    // Méthode pour la gomme circulaire partielle
    private void eraseInCircle(Point center) {
        eraserPoints.add(center);
    }

    // Réinitialise le panneau de dessin
    public void resetDrawingPanel() {
        shapeManager = new ShapeManager(); // Nouvelle instance de ShapeManager
        eraserPoints.clear(); // Vider les points de gomme
        repaint(); // Repeindre pour supprimer toutes les anciennes formes
    }
}
