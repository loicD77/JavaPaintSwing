package gui;

import utils.ColorPicker;

import javax.swing.*;
import java.awt.*;

public class ToolbarPanel extends JToolBar {
    private DrawingPanel drawingPanel;

    public ToolbarPanel() {
        JButton rectangleButton = new JButton("Rectangle");
        JButton circleButton = new JButton("Circle");
        JButton freehandButton = new JButton("Freehand");
        JButton colorPickerButton = new JButton("Color");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        add(rectangleButton);
        add(circleButton);
        add(freehandButton);
        add(colorPickerButton);
        add(saveButton);
        add(loadButton);

        // Actions des boutons
        rectangleButton.addActionListener(e -> drawingPanel.setDrawMode("Rectangle"));
        circleButton.addActionListener(e -> drawingPanel.setDrawMode("Circle"));
        freehandButton.addActionListener(e -> drawingPanel.setDrawMode("Freehand"));
        colorPickerButton.addActionListener(e -> {
            Color selectedColor = ColorPicker.pickColor(null, Color.BLACK);
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
