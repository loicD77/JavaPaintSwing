package utils;

import javax.swing.*;
import java.awt.*;

public class ColorPicker {
    /**
     * Affiche une boîte de dialogue de sélection de couleur.
     *
     * @param parentComponent La fenêtre parente de la boîte de dialogue.
     * @param currentColor La couleur actuellement sélectionnée.
     * @return La couleur choisie par l'utilisateur ou `null` s'il annule.
     */
    public static Color pickColor(Component parentComponent, Color currentColor) {
        return JColorChooser.showDialog(parentComponent, "Select a Color", currentColor);
    }
}
