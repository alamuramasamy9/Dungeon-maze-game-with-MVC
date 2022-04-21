##README FOR DUNGEON PROJECT - Version 2

###EXPLANATION:
This program is for creating a maze using kruskals algorithm.\
The number of rows, columns, interconnectivity,
wrapping or not wrapping and percentage of the
treasure is given as input by the user.\
Using this the maze is generated.\
Start and end are randomly generated.
Arrows and Otyughs are allocated in random positions in the cave. \
The user has to give inputs to traverse through the
maze from start to the end without being killed by the Otyugh.

###INPUTS REQUIRED

+ The number of rows is passed as an integer
+ The number of columns is passed as an integer
+ The interconnectivity is passed as an integer
+ The percentage of treasure is passed as an integer from 0-100.
+ The wrapping variable is passed as 0 or 1.
+ Number of Otyughs, minimum 1.
+ M, P, S for Move, Pick-up or Shoot.
+ For M -> N,E,W,S to move depending on the player's choice.
+ For P -> Treasure, Arrow depending on the player's choice.
+ For S-> N,E,W,S to shoot depending on the player's choice. \
Caves from 1-5 depending on the player's choice. 

###INSTRUCTIONS TO RUN THE PROGRAM:

The program has multiple interfaces and classes.
The functionality of everything can be tested using the driver (Game) class. \
In order to run the Game class, right click it and click on Run. The jar
file of the driver class has also been added to this folder.


###EXAMPLE RUN EXPLANATION:

The example run contains the output of the program.
+ The command line arguments are printed.
+ The dungeon is created and treasure is allocated.
+ The Start and End node are randomly generated.
+ The Otyughs and Arrows are randomly allocated.
+ The game now starts and the user is asked to enter M/P/S for Move, PickUp or Shoot.
+ if M is selected, N/E/W/S to move towards the north
  east, west or south depending on the availability of move.
+ if P is selected, Treasure or Arrow and it is picked up.
+ is S is selected, N/E/W/S to move towards the north
  east, west or south depending on the availability of path.
The cave number from 1-5 is asked and that cave is shot.
+ When the user reaches the end node without being killed, the game terminates.
+ If the player is killed by Otyugh game terminates.

***RUN1***\
Treasure is picked up
Arrow is picked Up
Player kills Otyugh
Player reaches the end 
Player wins the game

***RUN2***\
Player is killed by the Otyugh

***RUN3***\
Player is killed by Injured Otyugh

***RUN4***\
Player escapes from an injured Otyugh

###CHANGES IN THE UML DESIGN:

+ The basic structure of the UML has a few changes.
+ Directions is made into an Enum for ease of usage.
+ Instead of using random utility by a random generator class, collection shuffle is used.

###ASSUMPTIONS MADE DURING IMPLEMENTATION:

+ It has been assumed that the player can move
  only in the given 4 directions and not diagonally.
+ It has also been assumed that the user understands
  coordinates that are provided without visiblity of the maze.
+ The player enters cave from 1-5. If the cave location is 
not possible, we say it is shot into darkness.


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

***test package***
+ ***gametest***
    For testing all the functions of the program

***res***
+ UML_initial - Initial Design
+ UML_revised - Final Design
+ run1.txt - Output of the driver class
+ run2.txt - Output of the driver class
+ readME.md - Program Description
+ jar file - To run the program
