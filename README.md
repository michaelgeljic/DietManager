The Diet Manager V2.0 is a Java Swing desktop application built with a clean Model-View-Controller (MVC) architecture and the Composite design pattern to give users a unified, extensible interface for tracking both diet and exercise. On launch, the app simultaneously loads foods (basic items and multi‐ingredient recipes) from foods.csv and exercises (name + calories burned per kg per hour) from exercise.csv into in-memory models.
Users can
•	Add or edit basic foods, recipes, and exercises—each persisted immediately back to their respective CSV.
•	Select any date (default = today) via “Change Date” and log either foods (with servings) or exercises (with minutes).
•	Record weight and set a daily calorie goal; both values are saved to log.csv and, if absent on a given day, automatically fall back to the most recent entry (or sensible defaults).
Every time a log is added, edited, or deleted, the application
1.	Recomputes total calories, fat, carbs, and protein consumed,
2.	Calculates calories expended in exercise (using the user’s weight),
3.	Derives net calories and goal difference,
4.	Renders an updated pie-chart of macronutrient breakdown,
5.	Updates all textual stats (including weight and remaining calories),
6.	And immediately persists the change to log.csv.
By decoupling data (Model) from UI (View) through the Controller and using Composite for uniform treatment of BasicFood and Recipe, Diet Manager V2.0 delivers a scalable, maintainable foundation for comprehensive diet & exercise tracking—and lays the groundwork for future enhancements such as user accounts, richer analytics, or mobile synchronization.
Design Overview
Diet Manager V2.0 retains a strict Model–View–Controller separation, with each component shouldering a focused responsibility and communicating only through well‐defined interfaces.
•	Model
o	Food hierarchy: an abstract Food interface is the root of a Composite pattern. BasicFood represents atomic items; Recipe composes any combination of Food instances (including other recipes) and computes all nutrient totals recursively.
o	Exercises: a new Exercise class encapsulates name and calories-burned-per-kg-per-hour, managed by an Exercises collection that handles deduplication, two-way CSV persistence, and lookup.
o	Logs: the Logs class now tracks four parallel data streams—food entries (Log), exercise entries (ExerciseEntry), daily weight, and daily calorie goals—providing both exact and “fall‐back” queries (most recent earlier date or sensible defaults). It computes total calories, nutrients, and net calories on demand.
o	FileHandler: orchestrates all CSV I/O—foods (two-pass parse for basic + recipes), exercises, and logs (food, exercise, weight, goals)—ensuring date formatting, zero-padding, and adherence to “no forward references” in recipes.
•	View
o	A single Swing‐based View class lays out two side-by-side panels:
	Left: selectable lists of foods and exercises, plus buttons for “Log Food/Exercise,” “Add Food/Recipe,” “Add Exercise,” “Edit Exercise,” and “Delete Log Entry.”
	Right: a pie-chart of macronutrient breakdown, textual daily stats (calories, fat, carbs, protein, weight, remaining calories), date controls, and split lists for food and exercise logs.
o	All user prompts (adding/editing foods, recipes, exercises, servings/minutes, weight/goals) are rendered in single‐dialog forms for streamlined data entry.
•	Controller & Listeners
o	The central Controller initializes models and view, loads all CSV data, and wires up six dedicated listeners:
1.	AddFoodButtonListener
2.	AddExerciseButtonListener
3.	AddLogButtonListener
4.	ChangeDateButtonListener
5.	DeleteLogButtonListener
6.	EditExerciseButtonListener
o	Each listener handles its own user‐interaction flow, updates the appropriate model(s), refreshes the view, and immediately persists changes—keeping UI logic out of models and persistence out of views.
This design preserves low coupling and high cohesion: models know nothing of Swing, views know nothing of file I/O, and controllers coordinate everything through clear listener interfaces. Composite ensures that BasicFood and Recipe are interchangeable, while the enhanced Logs and Exercises abstractions support the new exercise‐tracking, weight/fallback, and edit/delete features with minimal impact on existing code.


Overall System Structure


The UML class diagram illustrates the final version of the Diet Manager application. It follows the Model-View-Controller (MVC) architectural pattern and implements the Composite design pattern to manage both simple and complex food structures. Each class’s responsibilities are clearly defined to ensure maintainability, separation of concerns, and extendibility.
Model Subsystem:
•	Food (abstract): Defines a unified interface for nutritional data access. It is the root of the Composite hierarchy, allowing both BasicFood and Recipe to be treated interchangeably.
•	BasicFood: Stores information for individual food items including calories, fat, carbs, and protein. Implements Food methods by returning simple values.
•	Recipe: A composite food that aggregates other Food objects (basic or recipe), each with a serving size. It recursively computes nutrient totals, supporting nested recipes.
•	Exercise: Represents an exercise with calories burned per kilogram per hour. This supports calculating calories burned during exercise logging.
•	Foods: Maintains a list of all Food and Exercise instances, providing operations to add, search, and persist them. It handles saving through internal calls to FileHandler.
•	Log: Represents one consumption or exercise entry, storing a Food or Exercise, servings or minutes, and the associated date.
•	Logs: Holds all log entries, provides filtering by date, and calculates total nutrient values (calories, fat, carbs, protein) and calories burned for any day.
•	ExerciseEntry: Represents a specific instance of an exercise log, storing the exercise, minutes performed, weight at the time, and calories burned.
•	FileHandler: Handles reading and writing of both foods and logs to CSV files. It manages foods.csv, log.csv, and exercise.csv, ensuring correct reconstruction of basic and composite foods, as well as exercise logs.
View Subsystem:
•	The View class creates and manages the Java Swing interface. It displays the food list, exercise list, current logs, nutrient totals, calories burned, and a pie chart. It provides prompts for user input, including recipe creation, exercise logging, calorie goals, and food servings. It supports deletion and editing of exercise logs, reflecting the extended logging functionality. The View remains responsible for showing and updating GUI elements without containing any business logic.
Controller Subsystem:
•	The Controller class orchestrates the application by connecting the View and Model. It initializes all components, sets up event listeners, manages the current date for logging, and handles coordination between data updates and the GUI. The Controller provides centralized access to Foods, Logs, and the View, but delegates user interaction handling to dedicated listener classes.
Listener Classes:
•	AddFoodButtonListener: Prompts the user to create either a basic food or recipe and adds it to the model, persisting the data to foods.csv.
•	AddLogButtonListener: Allows the user to log one or more food or exercise entries for the current date. It updates logs, exercise logs, and stats in the view.
•	AddExerciseButtonListener: Enables users to add a new exercise (name and calories burned per kg per hour) to the system, saved in exercise.csv.
•	ChangeDateButtonListener: Allows navigation between different dates. When the date changes, logs and stats refresh automatically for the selected day.
•	DeleteLogButtonListener: Supports deletion of food or exercise logs from the selected date, ensuring the logs and stats are updated accordingly.
•	EditExerciseButtonListener: Allows users to edit existing exercise definitions, such as adjusting calories burned per kg per hour.
________________________________________
The system is organized around the MVC architecture to cleanly separate user interface, data management, and control logic. This structure ensures that each component focuses on its own responsibility. The Model encapsulates all data handling and business logic, the View focuses solely on GUI presentation and input prompts, and the Controller coordinates communication between the two. This separation makes the system easier to debug, test, and extend.
The Composite design pattern allows recipes to contain any number of ingredients, including other recipes, while being treated as a single Food object. This simplifies nutritional calculations and supports nested recipes.
The system has been extended to incorporate exercise tracking. Exercises can be logged alongside food consumption, and their calorie burn contributions are reflected in the daily stats. The introduction of ExerciseEntry and dedicated exercise logging ensures the same flexibility and uniform handling for physical activities.
The use of dedicated listener classes isolates user interaction logic, making the system modular and adaptable. 
