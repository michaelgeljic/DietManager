package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.model.Food;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

/*
Handles the action of adding a new food item when the
"Add New Food" button is clicked in the View.
Prompts the user for food and nutrient input,
creates a new Food object, stores it, and updates the view and CSV file.*/
public class AddFoodButtonListener implements ActionListener {
    private final Controller controller;

    /*
    Constructs the listener and stores a reference to the main controller.*
    @param controller the Controller instance to access model and view*/
    public AddFoodButtonListener(Controller controller) {
        this.controller = controller;}

    /**
     Called when the "Add New Food" button is clicked.
     It shows a popup for food input, creates and saves a new food object,
     updates the CSV file, and refreshes the food list in the view.*
     @param e the event triggered by the button click*/
    @Override
    public void actionPerformed(ActionEvent e) {
        Food newFood = controller.getView().promptForFoodWithNutrition();
        if (newFood == null) return;

        controller.getFoods().addFood(newFood);
        controller.getFileHandler().writeFoods(controller.getFoods().getAllFoods(), "./src/data/foods.csv");

        List<String> foodNames = controller.getFoods().getAllFoods().stream()
                .map(Food::getName)
                .collect(Collectors.toList());

        controller.getView().updateFoodList(foodNames);
        controller.getView().showMessage("New food added: " + newFood.getName());
    }
}