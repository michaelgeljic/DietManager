package edu.rit.croatia.swen383.g3;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import edu.rit.croatia.swen383.g3.controller.Controller;
import edu.rit.croatia.swen383.g3.model.*;
import edu.rit.croatia.swen383.g3.util.FileHandler;
import edu.rit.croatia.swen383.g3.view.View;
import com.formdev.flatlaf.FlatLightLaf;  // Import FlatLaf

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
           try {
                FlatIntelliJLaf.setup();  // Use FlatDarkLaf.setup() for dark mode
            } catch (Exception e) {
                System.out.println("Could not set FlatLaf look and feel: " + e.getMessage());
            }

            // Your usual setup
            FileHandler handler = new FileHandler();
            Foods foods = new Foods(handler);
            Logs logs = new Logs(handler);
            Exercises exercises = new Exercises();
            View view = new View();
            new Controller(foods, logs, exercises, view);
            view.setVisible(true);
        });
    }
}
