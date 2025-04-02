package edu.rit.croatia.swen383.g3;

import edu.rit.croatia.swen383.g3.view.View;
import edu.rit.croatia.swen383.g3.model.*;
import edu.rit.croatia.swen383.g3.util.*;
import edu.rit.croatia.swen383.g3.controller.*;

/**
 * The entry point of the Diet Manager application.
 * Initializes the model, view, controller, and launches the GUI.
 */

public class Main {

    /**
     * Launches the application on the Swing event dispatch thread.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Initialize model, view
            Foods foods = new Foods();
            Logs logs = new Logs();
            View view = new View();

            // Set up controller and link everything
            new Controller(foods, logs, view);
            // Display the GUI
            view.setVisible(true);
        });
    }
}