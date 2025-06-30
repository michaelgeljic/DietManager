# Diet Manager

**Diet Manager** is a Java Swing desktop application designed for comprehensive diet and exercise tracking. Built with a clean Model-View-Controller (MVC) architecture and the Composite design pattern, it offers a unified, extensible interface that helps users monitor foods, recipes, exercises, weight, and daily calorie goals.

---

## Features

- **Unified Food and Exercise Tracking**  
  Loads foods (basic items and multi-ingredient recipes) and exercises (calories burned per kg per hour) from CSV files on startup.

- **Flexible Logging by Date**  
  Select any date (default is today) to log foods (with servings) and exercises (with duration in minutes).

- **Add, Edit & Delete Entries**  
  Manage foods, recipes, and exercises with immediate persistence to CSV files.

- **Weight and Calorie Goals**  
  Record daily weight and calorie goals; missing values automatically fallback to the most recent entry or sensible defaults.

- **Automatic Calculations and Visuals**  
  Every change triggers recalculation of consumed calories and nutrients, calories burned via exercise (adjusted for user weight), net calories, and goal differences — all reflected in an updated macronutrient pie chart and daily stats display.

---

## Design Overview

### Architecture

The app follows the **Model-View-Controller (MVC)** design pattern to separate concerns:

- **Model:**  
  Handles all data and business logic, including food hierarchy, exercises, and logs.

- **View:**  
  Java Swing GUI displaying foods, exercises, logs, charts, and user input dialogs.

- **Controller:**  
  Coordinates between Model and View, manages event listeners, and handles persistence.

### Key Design Patterns

- **Composite Pattern:**  
  Enables `Recipe` objects to contain any combination of `BasicFood` and other `Recipe` instances, allowing recursive nutritional calculations and uniform treatment of simple and complex foods.

- **Dedicated Listener Classes:**  
  Modularize user interaction logic for adding/editing foods, exercises, logs, and date changes.

---

## System Components

### Model

- **Food Interface:**  
  Abstract root of the food hierarchy.

- **BasicFood:**  
  Atomic food items with nutrition data.

- **Recipe:**  
  Composite food composed of multiple foods/recipes, supporting nested ingredients.

- **Exercise:**  
  Represents exercises with calories burned per kg per hour.

- **Foods:**  
  Collection managing all food and exercise instances, handling CSV persistence.

- **Log & Logs:**  
  Manage food and exercise log entries by date, track weight and calorie goals, and compute nutritional summaries.

- **ExerciseEntry:**  
  Represents individual exercise log entries with duration and calorie calculations.

- **FileHandler:**  
  Manages all CSV reading/writing for foods, exercises, and logs.

### View

- Java Swing GUI with:  
  - Selectable food and exercise lists  
  - Buttons for logging, adding, editing, and deleting entries  
  - Nutrient pie chart and textual daily stats  
  - Date selector and split logs for food and exercise  
  - Streamlined dialogs for all user input

### Controller

- Initializes models and views.

- Loads CSV data.

- Connects UI components to their dedicated listeners:  
  1. AddFoodButtonListener  
  2. AddExerciseButtonListener  
  3. AddLogButtonListener  
  4. ChangeDateButtonListener  
  5. DeleteLogButtonListener  
  6. EditExerciseButtonListener

- Ensures all model updates trigger view refreshes and immediate CSV persistence.

---

## How It Works

1. On launch, foods and exercises load from `foods.csv` and `exercise.csv` into memory.

2. Users select a date and log foods or exercises.

3. Weight and calorie goals can be recorded and defaulted if missing.

4. Logging updates recalculate totals, calories burned, net calories, and refresh the pie chart and stats.

5. All changes are instantly saved to CSV files to ensure persistence.

---

## Extensibility & Future Work

The clean separation of concerns and use of Composite pattern make the app highly maintainable and extensible. Future enhancements might include:

- User accounts and multi-user support.

- Richer analytics and reporting.

- Mobile app synchronization.

---

## UML Overview

The application is structured around the MVC pattern and Composite design pattern, ensuring:

- Clear separation between UI, data, and control logic.

- Maintainable, testable, and scalable code.

- Uniform handling of simple and complex food items.

- Flexible logging and editing of both diet and exercise.

- ![image](https://github.com/user-attachments/assets/62118af5-2648-4983-a644-aa097bba16ee)


---

## Getting Started

1. Clone the repository.

2. Ensure you have Java 8+ and Swing support installed.

3. Place your `foods.csv`, `exercise.csv`, and `log.csv` files in the project directory.

4. Run the main application class to launch the GUI.

---



