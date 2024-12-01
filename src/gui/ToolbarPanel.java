package gui;

import javax.swing.*;
import java.awt.*;

public class ToolbarPanel extends JToolBar {
    private DrawingPanel drawingPanel;

    public ToolbarPanel() {
        JButton rectangleButton = new JButton("Rectangle");
        JButton circleButton = new JButton("Circle");
        JButton triangleButton = new JButton("Triangle");
        JButton freehandButton = new JButton("Freehand");
        JButton eraserButton = new JButton("Eraser");
        JButton circleEraserButton = new JButton("Circle Eraser"); // Ajout de la gomme circulaire
        JButton colorPickerButton = new JButton("Color");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        add(rectangleButton);
        add(circleButton);
        add(triangleButton);
        add(freehandButton);
        add(eraserButton);
        add(circleEraserButton); // Ajoute le bouton pour gomme circulaire
        add(colorPickerButton);
        add(saveButton);
        add(loadButton);

        // Actions des boutons
        rectangleButton.addActionListener(e -> drawingPanel.setDrawMode("Rectangle"));
        circleButton.addActionListener(e -> drawingPanel.setDrawMode("Circle"));
        triangleButton.addActionListener(e -> drawingPanel.setDrawMode("Triangle"));
        freehandButton.addActionListener(e -> drawingPanel.setDrawMode("Freehand"));
        eraserButton.addActionListener(e -> drawingPanel.setDrawMode("Eraser"));
        circleEraserButton.addActionListener(e -> drawingPanel.setDrawMode("CircleEraser")); // Action pour gomme circulaire
        colorPickerButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(null, "Select a Color", Color.BLACK);
            if (selectedColor != null) {
                drawingPanel.setCurrentColor(selectedColor);
            }
        });

        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                drawingPanel.saveShapesToFile(fileChooser.getSelectedFile().getPath());
            }
        });

        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                drawingPanel.loadShapesFromFile(fileChooser.getSelectedFile().getPath());
            }
        });





    }

    public void setDrawingPanel(DrawingPanel panel) {
        this.drawingPanel = panel;
    }
}
