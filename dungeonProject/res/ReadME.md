##README FOR DUNGEON PROJECT

###EXPLANATION:
This program is for creating a maze using kruskals algorithm.\
The number of rows, columns, interconnectivity, 
wrapping or not wrapping and percentage of the 
treasure is given as input by the user.\
Using this the maze is generated.\
Start and end are randomly generated and the 
user has to give inputs to traverse through the 
maze from start to the end.

###INPUTS REQUIRED

+ The number of rows is passed as an integer
+ The number of columns is passed as an integer
+ The interconnectivity is passed as an integer
+ The percentage of treasure is passed as an integer from 0-100.
+ The wrapping variable is passed as 0 or 1.
+ N,E,W,S to move depending on the player's choice. 

###INSTRUCTIONS TO RUN THE PROGRAM:

The program has multiple interfaces and classes.
The functionality of everything can be tested using the driver (Game) class. \
In order to run the Game class, right click it and click on Run. The jar
file of the driver class has also been added to this folder.


###EXAMPLE RUN EXPLANATION:

The example run contains the output of the program.
+ At first all the inputs mentioned above are collected.
+ Maze nodes which are displayed 
+ The various potential paths are displayed
+ The required paths are chosen using kruskal's algorithm and connected 
+ The remaining paths are labelled leftover paths
+ Few paths (number of interconnections) are selected from the path list and connected
+ Path with 2 entrances are made into tunnel and rest are caves
+ A few caves (% of treasure) are allocated with random treasure
+ The random start and end is generated and the player is placed at start.
+ The game now starts and the user is asked to enter N/E/W/S to move towards the north
east, west or south depending on the availability of move.
+ When the user reaches the end node, the game terminates.

***RUN1***
Run 1 is a non wrapping dungeon. \
The player moves from the start to the end. \
The details of the player location and description are shown at every step.

***RUN2***
Run 2 is a wrapping dungeon. \
The player traverses through all the paths in the maze. \
The player moves from the start to the end. \
The details of the player location and description are shown at every step.

###CHANGES IN THE UML DESIGN:

+ The basic structure of the UML has a few changes.
+ Directions and Treasures have been made into Classes instead of having Enums.
+ Instead of using random utility by a random generator class, collection shuffle is used.
+ The DungeonAndLeftOver class is added to the implementation.

###ASSUMPTIONS MADE DURING IMPLEMENTATION:

+ It has been assumed that the player can move 
only in the given 4 directions and not diagonally.
+ It has also been assumed that the user understands 
coordinates that are provided without visiblity of the maze.


###LIMITATIONS OF THE PROGRAM

+ The visibility of the maze is not available
so, it can be a little challenging to visualize the game.

###CITATIONS:

Some websites have been referred for performing certain simple functionalities.
+ For shuffling to produce random edges from the list of paths this has been referred:
  https://www.javatpoint.com/java-collections-shuffle-method
+ For creating sets to create a maze using kruskals algorithm this has been referred:
  https://www.geeksforgeeks.org/set-in-java/
+ To allocate treasure within the cave hashmap has been referred from here:
  https://www.w3schools.com/java/java_hashmap.asp

###CONTENTS OF FOLDER:

***src package*** - Has all classes and interfaces.
+ ***com.dungeon package*** 
  + ***com.dungeon.data package*** - Contains all classes with data required
    + Node - The class for allocating node values to each node of dungeon
    + Path - The class for allocating start and end node values to each path of dungeon
    + PlayerLocation - The class for allocating node of all 4 directions of each node of dungeon
    + Treasure - Contains all type of Treasure
    + TreasureCollected - The class for allocating the treasure collected by the player
    + DungeonAndLeftOverPath - Contains the paths that are selected and the ones which are leftover
  + Dungeon - Interface for generating dungeon
  + PlayerInterface - Interface for player related methods
  + Maze - The class for getting start and end location and allocating treasure
  + GenerateDungeon - The class for generating the maze with caves and tunnels
  + Player - The class for player location and other methods
  + TraverseNode - The class that implements the kruskal's algorithm
  + Game - the Driver class named Game using which the program is executed

***test package***
+ ***gametest***
  + TestGame - For testing all the functions of the program

***res***
+ UML_initial - Initial Design
+ UML_revised - Final Design
+ run1.txt - Output of the driver class
+ run2.txt - Output of the driver class
+ readME.md - Program Description
+ jar file - To run the program
