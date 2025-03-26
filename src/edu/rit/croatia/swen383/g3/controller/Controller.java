package edu.rit.croatia.swen383.g3.controller;

import edu.rit.croatia.swen383.g3.model.Foods;
import edu.rit.croatia.swen383.g3.model.Logs;
import edu.rit.croatia.swen383.g3.util.FileHandler;
import edu.rit.croatia.swen383.g3.view.View;

/*The Controller class coordinates interaction between the View and the Model
in the Diet Manager application. It connects button actions to the appropriate
logic using external ActionListeners.*/
public class Controller {
    private final Foods foods;
    private final Logs logs;
    private final FileHandler fileHandler;
    private final View view;

    /*Constructs the Controller and wires up the action listeners
    for all buttons in the View.*
    @param foods        the Foods model (data collection)
    @param logs         the Logs model (user food log)
    @param fileHandler  the FileHandler for reading/writing CSV files
    @param view         the View (GUI) of the application*/
    public Controller(Foods foods, Logs logs, FileHandler fileHandler, View view) {
        this.foods = foods;
        this.logs = logs;
        this.fileHandler = fileHandler;
        this.view = view;

        // Connect button events to their listeners
        view.addLoadListener(new LoadButtonListener(this));
        view.addLogListener(new AddLogButtonListener(this));
        view.addFoodListener(new AddFoodButtonListener(this));
    }

    /*

    Returns the Foods model instance.*
            @return the Foods object*/
    public Foods getFoods() {
        return foods;}

    /*

    Returns the Logs model instance.*
            @return the Logs object*/
    public Logs getLogs() {
        return logs;}

    /*

    Returns the FileHandler used for file operations.*
            @return the FileHandler object*/
    public FileHandler getFileHandler() {
        return fileHandler;}

    /*

    Returns the View (GUI) instance.*
            @return the View object*/
    public View getView() {
        return view;}
}