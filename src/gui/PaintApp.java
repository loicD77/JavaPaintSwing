package gui;

import javax.swing.*;
import java.awt.*;

public class PaintApp extends JFrame {
    private JTabbedPane tabbedPane;

    public PaintApp() {
        setTitle("Java Paint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Barre de menus
        setJMenuBar(createMenuBar());

        // Système d'onglets
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        // Ajouter un premier onglet par défaut
        addNewTab("Nouveau fichier");

        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Fichier
        JMenu fileMenu = new JMenu("Fichier");
        JMenuItem newItem = new JMenuItem("Nouveau");
        JMenuItem saveItem = new JMenuItem("Sauvegarder");
        JMenuItem loadItem = new JMenuItem("Charger");
        JMenuItem closeTabItem = new JMenuItem("Fermer l'onglet");
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(closeTabItem);

        // Actions Fichier
        newItem.addActionListener(e -> addNewTab("Nouveau fichier"));
        saveItem.addActionListener(e -> {
            DrawingPanel currentPanel = getCurrentDrawingPanel();
            if (currentPanel != null) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    currentPanel.saveShapesToFile(fileChooser.getSelectedFile().getPath());
                    tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), fileChooser.getSelectedFile().getName());
                }
            }
        });
        loadItem.addActionListener(e -> {
            DrawingPanel currentPanel = getCurrentDrawingPanel();
            if (currentPanel == null) {
                // Créer un nouvel onglet avec un panneau de dessin s'il n'y en a pas
                addNewTab("Nouveau fichier");
                currentPanel = getCurrentDrawingPanel(); // Récupérer le panneau créé
            }
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                currentPanel.loadShapesFromFile(fileChooser.getSelectedFile().getPath());
                tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), fileChooser.getSelectedFile().getName());
            }
        });


        closeTabItem.addActionListener(e -> closeCurrentTab());

        // Menu Outils
        JMenu toolsMenu = new JMenu("Outils");
        JMenuItem rectangleTool = new JMenuItem("Rectangle");
        JMenuItem circleTool = new JMenuItem("Cercle");
        JMenuItem triangleTool = new JMenuItem("Triangle");
        JMenuItem freehandTool = new JMenuItem("Main levée");
        JMenuItem eraserTool = new JMenuItem("Gomme");
        JMenuItem circleEraserTool = new JMenuItem("Gomme Circulaire");

        toolsMenu.add(rectangleTool);
        toolsMenu.add(circleTool);
        toolsMenu.add(triangleTool);
        toolsMenu.add(freehandTool);
        toolsMenu.add(eraserTool);
        toolsMenu.add(circleEraserTool);

        // Actions Outils
        rectangleTool.addActionListener(e -> setCurrentDrawMode("Rectangle"));
        circleTool.addActionListener(e -> setCurrentDrawMode("Circle"));
        triangleTool.addActionListener(e -> setCurrentDrawMode("Triangle"));
        freehandTool.addActionListener(e -> setCurrentDrawMode("Freehand"));
        eraserTool.addActionListener(e -> setCurrentDrawMode("Eraser"));
        circleEraserTool.addActionListener(e -> setCurrentDrawMode("CircleEraser"));

        // Menu Couleur
        JMenu colorMenu = new JMenu("Couleur");
        JMenuItem chooseColor = new JMenuItem("Choisir une couleur");
        colorMenu.add(chooseColor);

        chooseColor.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(this, "Choisir une couleur", Color.BLACK);
            if (selectedColor != null) {
                setCurrentColor(selectedColor);
            }
        });

        // Menu Édition
        JMenu editMenu = new JMenu("Édition");
        JMenuItem deleteItem = new JMenuItem("Supprimer");
        JMenuItem moveItem = new JMenuItem("Déplacer");
        JMenuItem resizeItem = new JMenuItem("Redimensionner");

        deleteItem.addActionListener(e -> setCurrentDrawMode("Delete"));
        moveItem.addActionListener(e -> setCurrentDrawMode("Move"));
        resizeItem.addActionListener(e -> setCurrentDrawMode("Resize"));

        editMenu.add(deleteItem);
        editMenu.add(moveItem);
        editMenu.add(resizeItem);

        // Ajouter les menus à la barre de menus
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(colorMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(editMenu);

        return menuBar;
    }

    private void addNewTab(String title) {
        DrawingPanel newPanel = new DrawingPanel();
        tabbedPane.addTab(title, newPanel);
        tabbedPane.setSelectedComponent(newPanel);
    }

    private void closeCurrentTab() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex >= 0) {
            tabbedPane.removeTabAt(selectedIndex);
        }
    }

    private DrawingPanel getCurrentDrawingPanel() {
        Component selectedComponent = tabbedPane.getSelectedComponent();
        if (selectedComponent instanceof DrawingPanel) {
            return (DrawingPanel) selectedComponent;
        }
        return null;
    }

    private void setCurrentDrawMode(String mode) {
        DrawingPanel currentPanel = getCurrentDrawingPanel();
        if (currentPanel != null) {
            currentPanel.setDrawMode(mode);
        }
    }

    private void setCurrentColor(Color color) {
        DrawingPanel currentPanel = getCurrentDrawingPanel();
        if (currentPanel != null) {
            currentPanel.setCurrentColor(color);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaintApp::new);
    }
}
