package edu.rit.croatia.swen383.g3.view;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.rit.croatia.swen383.g3.model.*;

/**
 * The View class defines the GUI for the Diet Manager application.
 * It handles layout and user input, and provides methods for the controller to
 * update and interact with the GUI
 */
public class View extends JFrame {

    private JList<String> foodList;
    private DefaultListModel<String> foodListModel;
    private JTextArea logDisplayArea;
    private JButton loadButton;
    private JButton addLogButton;
    private JButton addFoodButton;
    private JLabel calorieLabel;
    private JLabel weightLabel;

    /**
     * Constructs the GUI layout including panels for food list, logs, and control
     * buttons
     */
    public View() {
        setTitle("Diet Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        foodListModel = new DefaultListModel<>();
        foodList = new JList<>(foodListModel);
        JScrollPane foodScroll = new JScrollPane(foodList);
        JPanel foodPanel = new JPanel(new BorderLayout());
        foodPanel.add(new JLabel("Available Foods (Click to Select)"), BorderLayout.NORTH);
        foodPanel.add(foodScroll, BorderLayout.CENTER);

        logDisplayArea = new JTextArea(10, 30);
        logDisplayArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(logDisplayArea);
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.add(new JLabel("Today's Log"), BorderLayout.NORTH);
        logPanel.add(logScroll, BorderLayout.CENTER);

        loadButton = new JButton("Load Data");
        addLogButton = new JButton("Add Log Entry");
        addFoodButton = new JButton("Add New Food");
        calorieLabel = new JLabel("Total Calories: 0");
        weightLabel = new JLabel("Weight: 0.0 kg");

        JPanel controPanel = new JPanel();
        controPanel.add(loadButton);
        controPanel.add(addLogButton);
        controPanel.add(addFoodButton);
        controPanel.add(calorieLabel);
        controPanel.add(weightLabel);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(foodPanel);
        centerPanel.add(logPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(controPanel, BorderLayout.SOUTH);

    }

    /**
     * Updates the food list display with the given list of food names.
     *
     * @param foodNames the list of food names to display
     */
    public void updateFoodList(List<String> foodNames) {
        foodListModel.clear();
        for (String name : foodNames) {
            foodListModel.addElement(name);
        }
    }

     /**
     * Returns the food name selected by the user in the list.
     *
     * @return the selected food name, or null if nothing is selected
     */
    public String getSelectedFoodName() {
        return foodList.getSelectedValue();
    }


    /**
     * Updates the log display area with the given text.
     *
     * @param content the log content to display
     */
    public void updateLogList(String content) {
        logDisplayArea.setText(content);

    }


    /**
     * Updates the calorie label with the given value.
     *
     * @param calories the total calories to display
     */
    public void updateCalories(double calories) {
        calorieLabel.setText("Total Calories: " + calories);

    }


    /**
     * Updates the weight label with the given value.
     *
     * @param weight the weight to display in kilograms
     */
    public void updateWeight(double weight) {
        weightLabel.setText("Weight " + weight + " kg");

    }


    /**
     * Adds an ActionListener to the Load Data button.
     *
     * @param listener the listener to add
     */
    public void addLoadListener(ActionListener listener) {
        loadButton.addActionListener(listener);

    }


    /**
     * Adds an ActionListener to the Add Log Entry button.
     *
     * @param listener the listener to add
     */
    public void addLogListener(ActionListener listener) {
        addLogButton.addActionListener(listener);

    }


    /**
     * Adds an ActionListener to the Add New Food button.
     *
     * @param listener the listener to add
     */
    public void addFoodListener(ActionListener listener) {
        addFoodButton.addActionListener(listener);

    }


    /**
     * Displays a message dialog with the given text.
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    /**
     * Prompts the user to input the number of servings for a food.
     *
     * @return the user input as a string, or null if cancelled
     */
    public String promptForServings() {
        return JOptionPane.showInputDialog(this, "Enter a number of servings: ");

    }


    /**
     * Displays a popup form where the user can enter the food name and
     * all nutrition values (calories, fat, carbs, protein) in one dialog.
     *
     * @return a new Food object with the provided values, or null if cancelled or invalid
     */
    public Food promptForFoodWithNutrition() {
        JTextField nameField = new JTextField();
        String[] nutrients = { "Calories", "Fat", "Carbs", "Protein" };
        Map<String, JTextField> fieldMap = new HashMap<>();

        for (String nutrient : nutrients) {
            fieldMap.put(nutrient, new JTextField());

        }

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Food Name:"));
        panel.add(nameField);

        for (String nutrient : nutrients) {
            panel.add(new JLabel(nutrient + ":"));
            panel.add(fieldMap.get(nutrient));

        }

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Food", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty())
                    return null;
                Map<String, Double> values = new HashMap<>();
                for (String nutrient : nutrients) {
                    String input = fieldMap.get(nutrient).getText().trim();
                    values.put(nutrient.toLowerCase(), Double.parseDouble(input));
                }
                return new Food(
                        name,
                        values.get("calories"),
                        values.get("fat"),
                        values.get("carbs"),
                        values.get("protein"))

                ;

            } catch (NumberFormatException e) {
                showMessage("Invalid input! Please enter valid numbers.");
            }

        }
        return null;
    }

}
