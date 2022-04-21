##README FOR DUNGEON PROJECT - Version 3

###EXPLANATION:
This program is for creating a maze using kruskals algorithm.\
The number of rows, columns, interconnectivity,
wrapping or not wrapping and percentage of the
treasure is given as input by the user.\
Using this the maze is generated.\
Start and end are randomly generated.
Arrows and Otyughs are allocated in random positions in the cave. \
The user has to give inputs to traverse through the
maze from start to the end without being killed by the Otyugh. \
Additionally a pit has been added at a random cave. \
A warning is given 1 space away from the pit. \
If player fallss into pit, the game ends. \
A thief has been added who can steal the treasure from player. \
Menu options such as displayer player description, dungeon description are given. \
Menu to reset gave and new game are given.



###INPUTS REQUIRED

+ The number of rows is passed as an integer
+ The number of columns is passed as an integer
+ The interconnectivity is passed as an integer
+ The percentage of treasure is passed as an integer from 0-100.
+ The wrapping variable is passed as true or false.
+ Number of Otyughs, minimum 1.
+ Arrow keys are pressed to move
+ s is pressed for shoot.
+ p is pressed for shoot and arrow to specify direction to shoot.

###INSTRUCTIONS TO RUN THE PROGRAM:

The program has multiple interfaces and classes.
The functionality of everything can be tested using the driver (DungeonRunner) class. \
In order to run the Game class, right click it and click on Run. The jar
file of the driver class has also been added to this folder. \
Once you run a screen prompt appear to enter inputs and start game must be clicked. \
Once it is clicked, the maze appears with all locations hidden except the traversed locations. \
the dungeon is then traversed until the end is reached or until player dies. \
Menu options such as display player description, dungeon description are given. \
Menu to reset gave and new game are given.


###EXAMPLE RUN (SKETCH & SCREENSHOT) EXPLANATION:
+ Sketch Frame 1 - Initial Screen
+ Sketch Frame 2 - Game Screen 
+ All nodes hidden – Revealed as we traverse
+ Dead by Pit
+ Escape from injured otyugh
+ Finding arrow and treasure
+ Full maze displayed
+ Initial frame to get inputs
+ Injured otyugh
+ Instructions
+ Invalid move
+ Kill the otyugh
+ Killed by injured otyugh
+ Killed by otyugh
+ Picking up arrow and treasure
+ Pit warning
+ Shoot and Miss
+ Show Dungeon Description
+ Invalid move
+ Kill the otyugh
+ Killed by injured otyugh
+ Killed by otyugh
+ Picking up arrow and treasure
+ Pit warning
+ Shoot and miss
+ Show dungeon description
+ Show player description – Jmenu
+ Slight smell (2 positions away)
+ Starting start of game
 Terrible smell
+ Thief message 
+ Win message


###CHANGES IN THE UML DESIGN:


+ The basic structure of the UML has a few changes.
+ DungeonInputsV3 class is added to acquire inputs from the user.
+ Directions is made into an Enum for ease of usage.
+ Instead of using random utility by a random generator class, collection shuffle is used.

###ASSUMPTIONS MADE DURING IMPLEMENTATION:

+ It has been assumed that the player can move
  only in the given 4 directions and not diagonally.
+ It has also been assumed that the user understands
  coordinates that are provided without visiblity of the maze.
+ It has been assumed that the player knows how to play the game.


###LIMITATIONS OF THE PROGRAM

+ The caves are too small and would be crowded to put the items inside it.

###CITATIONS:

Some websites have been referred for performing certain simple functionalities.
+ For shuffling to produce random edges from the list of paths this has been referred:
  https://www.javatpoint.com/java-collections-shuffle-method
+ For creating sets to create a maze using kruskals algorithm this has been referred:
  https://www.geeksforgeeks.org/set-in-java/
+ To allocate treasure within the cave hashmap has been referred from here:
  https://www.w3schools.com/java/java_hashmap.asp
+ To add scroll bar :
https://stackoverflow.com/questions/11919941/add-scrollpane-to-jpanel-when-the-panel-is-full-java
+ To make scroll bar not move with arrow keys :
https://coderanch.com/t/342364/java/JScrollPane-JTextArea-Disabling-Keyboard-Events
+ To add items to Jmenu:
https://www.javatpoint.com/java-jmenuitem-and-jmenu
+ To configure moves and generate maze char array:
https://github.com/STLkru/Maze-Game

###CONTENTS OF FOLDER:

***src package*** - Has all classes and interfaces.
+ ***com.dungeon package***
    + ***com.dungeon.data package*** - Contains all classes with data required
        + Node - The class for allocating node values to each node of dungeon
        + Path - The class for allocating start and end node values to each path of dungeon
        + PlayerLocation - The class for allocating node of all 4 directions of each node of dungeon
        + Treasure - Contains all type of Treasure
        + ArrowLocations - Class for setting arrows into locations
        + Directions - An enum for directions
        + Otyugh - Placing otyugh and setting health of otyugh
        + GameOtyughDetails - Locations of the Otyughs
        + NearbyOtyughs - To keep track of the close by Otyughs to produce smell
        + TreasureCollected - The class for allocating the treasure collected by the player
        + DungeonAndLeftOverPath - Contains the paths that are selected and the ones which are leftover
    + Dungeon - Interface for generating dungeon
    + PlayerInterface - Interface for player related methods
    + Controller Interface - Interface for the controller
    + GameModel - Where all the model functionalitites take place
    + GameController - When the controller acts as a medium between Driver and Model.
    + Maze - The class for getting start and end location and allocating treasure
    + GenerateDungeon - The class for generating the maze with caves and tunnels
    + Player - The class for player location and other methods
    + UpdateGame - Includes the updations required in the game.
    + TraverseNode - The class that implements the kruskal's algorithm
    + Game - the Driver class named Game using which the program is executed
    + ***model***
      + ModelInterface - Interface for Model
      + DungeonModel - Model of MVC
    + ***view***
      + ViewInterface - Interface for view
      + DungeonView - view of MVC
    + ***model***
      + ControllerInterface - Interface for controller
      + DungeonControl - controller of MVC

***test package***
+ ***gametest***
  For testing all the functions of the program

***res***
+ UML_initial - Initial Design
+ UML_revised - Final Design
+ images for the view
+ screenshots of the program
+ readME.md - Program Description
+ jar file - To run the program
