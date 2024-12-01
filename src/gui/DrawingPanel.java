package gui;

import models.FreehandShape;
import models.RectangleShape;
import models.CircleShape;
import models.TriangleShape;
import models.ShapeManager;
import models.Shape;
import utils.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {
    private Color currentColor = Color.BLACK;
    private String drawMode = "Rectangle";
    private ShapeManager shapeManager = new ShapeManager();
    private Shape currentShape = null;
    private Point lastDragPoint = null;

    // Liste des points de gomme circulaire
    private ArrayList<Point> eraserPoints = new ArrayList<>();
    private Point eraserCursor = null; // Point pour visualiser la position de la gomme circulaire

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
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if ("CircleEraser".equals(drawMode)) {
                    eraserCursor = e.getPoint();
                    repaint();
                }
            }
        });
    }

    private void handleMousePressed(MouseEvent e) {
        switch (drawMode) {
            case "Rectangle":
            case "Circle":
            case "Triangle":
                currentShape = createShape(drawMode, currentColor, e.getPoint());
                if (currentShape != null) {
                    shapeManager.addShape(currentShape);
                }
                break;

            case "Freehand":
                currentShape = new FreehandShape(currentColor, e.getPoint());
                shapeManager.addShape(currentShape);
                break;

            case "Move":
                shapeManager.selectShape(e.getPoint());
                lastDragPoint = e.getPoint();
                break;

            case "Resize":
                shapeManager.selectShape(e.getPoint());
                break;

            case "Delete":
                shapeManager.selectShape(e.getPoint());
                shapeManager.deleteSelectedShape();
                break;

            case "CircleEraser":
                eraserPoints.add(e.getPoint());
                repaint();
                break;

            case "Eraser":
                shapeManager.selectShape(e.getPoint());
                shapeManager.deleteSelectedShape();
                break;

            default:
                break;
        }
        repaint();
    }

    private void handleMouseReleased(MouseEvent e) {
        currentShape = null;
        lastDragPoint = null;
        eraserCursor = null;
    }

    private void handleMouseDragged(MouseEvent e) {
        if (currentShape != null) {
            currentShape.update(e.getPoint());
        } else if ("Move".equals(drawMode) && lastDragPoint != null) {
            int dx = e.getX() - lastDragPoint.x;
            int dy = e.getY() - lastDragPoint.y;
            shapeManager.moveSelectedShape(dx, dy);
            lastDragPoint = e.getPoint();
        } else if ("Resize".equals(drawMode)) {
            shapeManager.resizeSelectedShape(e.getPoint());
        } else if ("CircleEraser".equals(drawMode)) {
            eraserPoints.add(e.getPoint());
        }
        repaint();
    }

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

        if ("CircleEraser".equals(mode)) {
            setCursor(createCircleEraserCursor());
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private Cursor createCircleEraserCursor() {
        int cursorSize = 20;
        BufferedImage cursorImage = new BufferedImage(cursorSize, cursorSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = cursorImage.createGraphics();

        // Dessiner un cercle transparent avec une bordure noire
        g2d.setColor(new Color(0, 0, 0, 128)); // Bordure noire semi-transparente
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(0, 0, cursorSize - 1, cursorSize - 1);
        g2d.dispose();

        // Créer le curseur personnalisé
        return Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(cursorSize / 2, cursorSize / 2), "Circle Eraser");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        shapeManager.drawAllShapes(g);

        // Dessiner les points de gomme circulaire
        g.setColor(getBackground()); // Utilise la couleur de fond pour effacer
        for (Point p : eraserPoints) {
            g.fillOval(p.x - 10, p.y - 10, 20, 20);
        }
    }

    // Méthode pour sauvegarder les formes et les points de gomme dans un fichier
    public void saveShapesToFile(String filePath) {
        try {
            FileManager.saveShapes(new ArrayList<>(shapeManager.getShapes()), filePath);
            FileManager.saveEraserPoints(eraserPoints, filePath + ".eraser");
            JOptionPane.showMessageDialog(this, "Les formes et les points de gomme ont été sauvegardés avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Échec de la sauvegarde des formes : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour charger les formes et les points de gomme depuis un fichier
    public void loadShapesFromFile(String filePath) {
        try {
            shapeManager.setShapes(FileManager.loadShapes(filePath));
            eraserPoints = FileManager.loadEraserPoints(filePath + ".eraser");
            repaint();
            JOptionPane.showMessageDialog(this, "Les formes et les points de gomme ont été chargés avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Échec du chargement des formes : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
