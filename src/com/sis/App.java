package com.sis;

import com.sis.ui.MainFrame;
import javax.swing.SwingUtilities;

/**
 * App is the main entry point for the Student Information System.
 * It initializes and displays the main application window.
 */
public class App {
    public static void main(String[] args) {
        // The invokeLater method ensures that all UI updates are made on the
        // Event Dispatch Thread, which is the proper way to handle Swing components.
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
