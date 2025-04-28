package edu.rit.croatia.swen383.g3;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import edu.rit.croatia.swen383.g3.controller.Controller;
import edu.rit.croatia.swen383.g3.model.*;
import edu.rit.croatia.swen383.g3.util.FileHandler;
import edu.rit.croatia.swen383.g3.view.View;
import com.formdev.flatlaf.FlatLightLaf;  // Import FlatLaf

/**
 * The entry point for the Diet Manager application.
 * This class sets up the Look and Feel for the GUI, initializes the model, view, and controller components,
 * and launches the application on the Event Dispatch Thread.
 */
public class Main {

    /**
     * The main method launches the Diet Manager application.
     * It sets the FlatLaf Look and Feel, initializes the necessary models (Foods, Logs, Exercises),
     * the view, and connects them with the controller.
     *
     * @param args the command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                // Set up the FlatLaf IntelliJ theme
                FlatIntelliJLaf.setup();
            } catch (Exception e) {
                System.out.println("Could not set FlatLaf look and feel: " + e.getMessage());
            }

            // Initialize core components: models, view, and controller
            FileHandler handler = new FileHandler();
            Foods foods = new Foods(handler);
            Logs logs = new Logs(handler);
            Exercises exercises = new Exercises();
            View view = new View();
            new Controller(foods, logs, exercises, view);
            view.setVisible(true); // Display the GUI
        });
    }
}