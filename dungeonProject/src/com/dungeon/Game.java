package com.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import com.dungeon.data.DungeonAndLeftOverPath;
import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.data.TreasureCollected;

/**
 * The Driver class Game in which the program executes. The maze generation is
 * done by following the steps of the kruskal's algorithm. The interconnectivity
 * is applied later to have multiple paths. They are classified into caves and
 * tunnels based on paths. Treasure is allocated to the given percentage of
 * caves. The random start and end are generated and the player execution
 * begins.
 */

public class Game {

  /**
   * The main method of the Game Class. The inputs required are obtained in this
   * class. The outputs are displayed with the help od print statements.
   *
   * @param args args
   */

  public static void main(String[] args) {

    int rows = Integer.parseInt(args[0]);
    int cols = Integer.parseInt(args[1]);
    int interConnectivity = Integer.parseInt(args[2]);
    int percentageOfTreasure = Integer.parseInt(args[3]);
    int wrap = Integer.parseInt(args[4]);
    boolean wrapping = false;

    // Taking inputs
    Scanner inputScanner = new Scanner(System.in);
    System.out.println("Number of rows: "+rows);
    System.out.println("Number of cols: "+cols);
    System.out.println("Number of interconnectivity: "+interConnectivity);
    System.out.println("Percentage of treasure: "+percentageOfTreasure);
    System.out.println("Wrapping / Non-Wrapping : "+wrap);

    /*
    // row
    do {
      System.out.print("Enter number of rows (between 3 to 10) :");
      rows = inputScanner.nextInt();

      if (rows < 3 || rows > 10) {
        System.out.println("Only between 3 and 10.");
      }
    }
    while (rows < 3 || rows > 10);

    // column
    do {
      System.out.print("Enter number of columns (between 3 to 10) :");
      cols = inputScanner.nextInt();

      if (cols < 3 || cols > 10) {
        System.out.println("Only between 3 and 10.");
      }
    }
    while (cols < 3 || cols > 10);

    // interconnectivity
    do {
      System.out.print("Enter interconnectivity:");
      interConnectivity = inputScanner.nextInt();

      if (interConnectivity < 0) {
        System.out.println("Enter positive value");
      }
    }
    while (interConnectivity < 0);

    // percentage of treasure
    do {
      System.out.print("Enter percentage of treasure (between 0 to 100):");
      percentageOfTreasure = inputScanner.nextInt();

      if (percentageOfTreasure < 0 || percentageOfTreasure > 100) {
        System.out.println("Only between 0 and 100.");
      }
    }
    while (percentageOfTreasure < 0 || percentageOfTreasure > 100);

    int wrap = 0;
    // wrapping or not wrapping
    do {
      System.out.print("Enter 0 for non-wrapping, 1 for wrapping :");
      wrap = inputScanner.nextInt();
      inputScanner.nextLine();
      if (wrap != 0 && wrap != 1) {
        System.out.println("Only 0 or 1 must be the input.");
      }
    }
     */

    while (wrap != 0 && wrap != 1);
    if (wrap == 0) {
      wrapping = false;
    } else if (wrap == 1) {
      wrapping = true;
    }
    line();

    DungeonLevel dungeonLevel = new DungeonLevel();
    ArrayList<Node> nodes = dungeonLevel.generateNodes(rows, cols);

    // Iterate edges
    System.out.println("Iterating through the Nodes : ");
    for (int i = 0; i < nodes.size(); i++) {
      Node eachNode = nodes.get(i);
      System.out.println("X: " + eachNode.getX() + " Y: " + eachNode.getY());
    }
    line();

    // Identify Potential Paths
    ArrayList<Path> potentialPaths = dungeonLevel.identifyPaths(rows, cols, nodes);

    //Shuffle potential paths - So random order will be generated
    dungeonLevel.shufflePaths(potentialPaths);
    //potentialPaths=shufflePathsStatic(potentialPaths);

    // Iterate Potential Paths
    System.out.println("No. of potential paths :" + potentialPaths.size());// Should be - 38
    for (int i = 0; i < potentialPaths.size(); i++) {
      Path eachPath = potentialPaths.get(i);
      Node startNode = eachPath.getStart();
      Node endNode = eachPath.getEnd();
      System.out.println("Path start: " + startNode.getX() + ", " + startNode.getY()
              + " >> Path end: " + endNode.getX() + ", " + endNode.getY());
    }
    line();

    // Generate Dungeon
    TraverseNode traverseNode = new TraverseNode();
    DungeonAndLeftOverPath dungeonAndLeftOverPath
            = traverseNode.generateDungeonAndLeftOverPath(potentialPaths);

    ArrayList<ArrayList<Path>> maze = dungeonAndLeftOverPath.getDungeon();
    ArrayList<Path> leftOverPaths = dungeonAndLeftOverPath.getLeftOverPaths();

    System.out.println("The following paths are connected: ");
    for (int i = 0; i < maze.size(); i++) {
      ArrayList<Path> each = maze.get(i);
      for (int j = 0; j < each.size(); j++) {
        Path eachPath = each.get(j);
        Node startNode = eachPath.getStart();
        Node endNode = eachPath.getEnd();
        System.out.println("Connected Paths >> start: " + startNode.getX() + ", " + startNode.getY()
                + " >> end: " + endNode.getX() + ", " + endNode.getY());
      }
    }
    line();

    // Iterate leftover paths
    System.out.println("No. of Set in Maze : " + maze.size());
    System.out.println("No. of leftover paths : " + leftOverPaths.size());
    for (int i = 0; i < leftOverPaths.size(); i++) {
      Path eachPath = leftOverPaths.get(i);
      Node startNode = eachPath.getStart();
      Node endNode = eachPath.getEnd();
      System.out.println("Left over Paths >> start: " + startNode.getX() + ", " + startNode.getY()
              + " >> end: " + endNode.getX() + ", " + endNode.getY());
    }
    line();

    //Shuffle leftover paths - So random order will be generated to pick and start
    //InterConnection
    traverseNode.shuffleLeftOverPaths(leftOverPaths);
    //leftOverPaths=shuffleLeftOverStatic(leftOverPaths);

    int interCount = traverseNode.validateInterconnectivityPathWithLeftOverPath(
            leftOverPaths, interConnectivity);
    if (maze.get(0) == null) {
      System.err.println("Dungeon error, dungeon grid is empty");
      return;
    }

    // Applying interconnection
    ArrayList<Path> finalDungeon = maze.get(0);
    ArrayList<Path> interConnectedPaths = traverseNode.applyInterconnectivity(
            interCount, leftOverPaths, finalDungeon);
    System.out.println("Inter connectivity applied to Dungeon:");
    for (int i = 0; i < interConnectivity; i++) {
      Path eachPath = interConnectedPaths.get(i);
      Node startNode = eachPath.getStart();
      Node endNode = eachPath.getEnd();
      System.out.println("Paths Connected >> start: " + startNode.getX() + ", " + startNode.getY()
              + " >> end: " + endNode.getX() + ", " + endNode.getY());
    }
    line();

    GenerateDungeon generateDungeon = new GenerateDungeon();
    // Identify Nodes which has only 2 paths & make them tunnel
    ArrayList<Node> tunnels = generateDungeon.identifyTunnel(finalDungeon, rows, cols);

    System.out.println("No. of nodes which are tunnels: " + tunnels.size());
    // Tunnel identified in Dungeon, they are below
    for (int i = 0; i < tunnels.size(); i++) {
      Node eachTunnel = tunnels.get(i);
      System.out.println("Dungeon tunnel Edges are : " + eachTunnel.getX() + ", "
              + eachTunnel.getY());
    }
    line();

    // Identify Caves for treasure allocation
    ArrayList<Node> caves = generateDungeon.identifyCaves(finalDungeon, tunnels);
    System.out.println("No. of Caves in Dungeon is : " + caves.size());

    float percent = (float) percentageOfTreasure;
    int numberOfCavesTreasureAllocated = (int) (percent / 100 * caves.size());
    System.out
            .println(percentageOfTreasure + "% of " + caves.size() + " Caves is : "
                    + numberOfCavesTreasureAllocated + ", so treasure will be allocated for "
                    + numberOfCavesTreasureAllocated + " Caves");

    //Shuffle caves, so random caves will be selected for allocating treasure
    Collections.shuffle(caves);
    //caves=shuffleCavesStatic(caves);

    // Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(false);

    // Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves = mazeObj.allocateTreasure(
            numberOfCavesTreasureAllocated,
            caves, treasureMaster, false);

    // Iterate treasure caves
    for (int i = 0; i < treasureCaves.size(); i++) {
      HashMap<Node, Treasure> eachTreasureCave = treasureCaves.get(i);
      Set<Node> listTreasureCaves = eachTreasureCave.keySet();
      for (Node node : listTreasureCaves) {
        Treasure allocatedTreasure = eachTreasureCave.get(node);
        System.out.println("Treasure cave node " + node.getX() + ", " + node.getY()
                + " and the treasure allocated is ( Diamond : " + allocatedTreasure.hasDiamond()
                + ", Ruby : " + allocatedTreasure.hasRuby() + ", Sapphire : "
                + allocatedTreasure.hasSapphire() + " )");
      }
    }
    line();

    // Dungeon successfully generated
    System.out.println("Dungeon is successfully generated, setting player start and end position");

    // Shuffle the final dungeon, so random start point will be picked as first
    // index of the array
    mazeObj.generateRandomStart(finalDungeon);

    // Pick first path start node as player start location
    Node playerStartAt = finalDungeon.get(0).getStart();
    Node playerCurrentlyAt = finalDungeon.get(0).getStart();
    Node playerEndAt = mazeObj.generateRandomEnd(finalDungeon);
    int getPlayerEndLocationIndex = mazeObj.getplayerEndLocationIndex();

    System.out.println("The player START position is (" + playerStartAt.getX()
            + ", " + playerStartAt.getY() + ").");
    System.out.println("The player END position is (" + playerEndAt.getX() + ", "
            + playerEndAt.getY() + ") which has " + (getPlayerEndLocationIndex + 1)
            + " possible paths from start position of the player.");

    Player player = new Player();
    PlayerLocations playerPossibleLocations = player.getPossibleMoves(
            rows, cols, finalDungeon, playerCurrentlyAt, wrapping);
    System.out.println("Player start and end position has been set.");
    line();
    TreasureCollected treasureCollected
            = player.checkPlayerCaveHasTreasure(treasureCaves, playerStartAt);
    Treasure treasureDetailsOfPlayerCurrentCave
            = player.getTreasureDetailsOfCave(treasureCaves, playerStartAt);

    System.out.println("\nTHE GAME HAS BEGUN\n");
    if (wrapping) {
      System.out.println("This is a Wrapping Dungeon");
    }
    else {
      System.out.println("This is a Non-Wrapping Dungeon");
    }
    System.out.println("The player START position is (" + playerStartAt.getX()
            + ", " + playerStartAt.getY() + ").");
    System.out.println("The player END position is (" + playerEndAt.getX() + ", "
            + playerEndAt.getY() + ").");
    System.out.println(
            "You can move the player now by typing N - for North, E - for East,"
                    + " S - for South, W - for West.  \n");


    validateAndUpdateGame(treasureCollected, playerStartAt, treasureDetailsOfPlayerCurrentCave,
            playerPossibleLocations);

    System.out.println("\nPlease input your move [case-sensitive] (N/E/S/W):");

    // Validate user input & update game
    String nextMove = getPlayerInput(inputScanner, playerPossibleLocations, playerCurrentlyAt);

    for (; ; ) {
      System.out.println("\nMove accepted & player successfully moved to the next location");
      playerCurrentlyAt = player.getNextMoveCave(playerPossibleLocations, nextMove);
      playerPossibleLocations = player.getPossibleMoves(
              rows, cols, finalDungeon, playerCurrentlyAt, wrapping);

      treasureDetailsOfPlayerCurrentCave = player.getTreasureDetailsOfCave(
              treasureCaves, playerCurrentlyAt);
      if (treasureDetailsOfPlayerCurrentCave.hasDiamond()) {
        treasureCollected.addDiamond(1);
      }
      if (treasureDetailsOfPlayerCurrentCave.hasRuby()) {
        treasureCollected.addRuby(1);
      }
      if (treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
        treasureCollected.addSapphire(1);
      }

      validateAndUpdateGame(treasureCollected, playerCurrentlyAt,
              treasureDetailsOfPlayerCurrentCave, playerPossibleLocations);

      if (player.isDestinationReached(playerCurrentlyAt, playerEndAt)) {
        System.out.println("Congratulations!!!");
        System.out.println("You have successfully reached the destination.");

        System.out.println("#................Game completed................#");
        break;
      } else {
        System.out.println("\nPlease input your move [case-sensitive] (N/E/S/W):");
        nextMove = getPlayerInput(inputScanner, playerPossibleLocations, playerCurrentlyAt);
      }
    }

    inputScanner.close();
  }

  /**
   * method to get the player input for moves.
   *
   * @param inputScanner            to scan inout
   * @param playerPossibleLocations possible moves of player
   * @param playerCurrentlyAt       current node
   * @return the player moving to next location.
   */

  public static String getPlayerInput(Scanner inputScanner, PlayerLocations playerPossibleLocations,
                                      Node playerCurrentlyAt) {
    String nextMove = "";
    do {
      nextMove = inputScanner.nextLine();
      if (!validatePlayerInput(playerPossibleLocations, nextMove, true,
              playerCurrentlyAt)) {
        System.out.println("\nInput your move again [case-sensitive] (N/E/S/W):");
      }
    }
    while (!validatePlayerInput(playerPossibleLocations, nextMove, false,
            playerCurrentlyAt));
    return nextMove;
  }

  /**
   * validating the input of the player.
   *
   * @param playerPossibleLocations possible moves of the player
   * @param nextMove                move to the next node
   * @param showErrorMessage        if any other input is given
   * @param playerCurrentlyAt       current location of player
   * @return true or false according to validity of input
   */

  public static boolean validatePlayerInput(
          PlayerLocations playerPossibleLocations, String nextMove,
          boolean showErrorMessage, Node playerCurrentlyAt) {
    if (nextMove.equals("N") || nextMove.equals("E")
            || nextMove.equals("S") || nextMove.equals("W")) {
      if (nextMove.equals("N")) {
        if (playerPossibleLocations.hasNorth()) {
          return true;
        } else {
          if (showErrorMessage) {
            System.out.println("The input move North is not valid for this Cave ("
                    + playerCurrentlyAt.getX() + ", " + playerCurrentlyAt.getY()
                    + "), the possible moves are (West[" + playerPossibleLocations.getWest().getX()
                    + ", " + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasEast() + " || North["
                    + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth() + ")");
          }
          return false;
        }
      } else if (nextMove.equals("E")) {
        if (playerPossibleLocations.hasEast()) {
          return true;
        } else {
          if (showErrorMessage) {
            System.out.println("The input move East is not valid for this Cave ("
                    + playerCurrentlyAt.getX()
                    + ", " + playerCurrentlyAt.getY() + "), the possible moves are (West["
                    + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || North[" + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + ")");
          }
          return false;
        }
      } else if (nextMove.equals("S")) {
        if (playerPossibleLocations.hasSouth()) {
          return true;
        } else {
          if (showErrorMessage) {
            System.out.println("The input move South is not valid for this Cave ("
                    + playerCurrentlyAt.getX() + ", " + playerCurrentlyAt.getY()
                    + "), the possible moves are (West["
                    + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || North[" + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth() + ")");
          }
          return false;
        }
      } else if (nextMove.equals("W")) {
        if (playerPossibleLocations.hasWest()) {
          return true;
        } else {
          if (showErrorMessage) {
            System.out.println("The input move West is not valid for this Cave ("
                    + playerCurrentlyAt.getX()
                    + ", " + playerCurrentlyAt.getY() + "), the possible moves are (West["
                    + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || North[" + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + ")");
          }
          return false;
        }
      }
    } else if (showErrorMessage) {
      System.out.println("Invalid input...!");
    }
    return false;
  }

  /**
   * player description that is printed.
   *
   * @param treasureCollected                  treasure so far
   * @param playerCurrentlyAt                  current location of player
   * @param treasureDetailsOfPlayerCurrentCave treasure at current cave
   * @param playerPossibleLocations            possible moves of player
   */

  public static void validateAndUpdateGame(TreasureCollected treasureCollected,
                                           Node playerCurrentlyAt,
                                           Treasure treasureDetailsOfPlayerCurrentCave,
                                           PlayerLocations playerPossibleLocations) {
    System.out.println("Treasure collected so far : (Diamond - "
            + treasureCollected.getDiamondCount()
            + ", Ruby - " + treasureCollected.getRubyCount() + ", Sapphire - "
            + treasureCollected.getSapphireCount() + ")");
    System.out.println("The player is currently at (" + playerCurrentlyAt.getX() + ", "
            + playerCurrentlyAt.getY()
            + ") \nPossible moves are (North[" + playerPossibleLocations.getNorth().getX() + ", "
            + playerPossibleLocations.getNorth().getY() + "]:"
            + playerPossibleLocations.hasNorth() + " || East["
            + playerPossibleLocations.getEast().getX() + ", "
            + playerPossibleLocations.getEast().getY() + "]:"
            + playerPossibleLocations.hasEast() + " || South["
            + playerPossibleLocations.getSouth().getX() + ", "
            + playerPossibleLocations.getSouth().getY() + "]:"
            + playerPossibleLocations.hasSouth() + " || West["
            + playerPossibleLocations.getWest().getX() + ", "
            + playerPossibleLocations.getWest().getY() + "]:"
            + playerPossibleLocations.hasWest() + ")");
    System.out.println("Treasure details of the cave where player currently at - Diamond : "
            + treasureDetailsOfPlayerCurrentCave.hasDiamond() + ", Ruby : "
            + treasureDetailsOfPlayerCurrentCave.hasRuby() + ", Sapphire : "
            + treasureDetailsOfPlayerCurrentCave.hasSapphire());
  }

  /**
   * a separator.
   */

  public static void line() {
    System.out.println("_______________________________________________________________________\n");
  }

  	/*
  	public static ArrayList<Path> shufflePathsStatic(ArrayList<Path> input) {
		int[] tmp = new int[] { 12, 14, 21, 25, 30, 35, 37, 10, 11, 23, 13, 28, 31, 32, 36, 18, 17, 9, 15, 16, 19, 20,
				6, 8, 34, 24, 29, 5, 4, 22, 26, 27, 33, 2, 3, 7, 1, 0 };
		ArrayList<Path> result = new ArrayList<Path>();
		for (int i = 0; i < tmp.length; i++) {
			result.add(input.get(tmp[i]));
		}
		return result;
	}

	public static ArrayList<Path> shuffleLeftOverStatic(ArrayList<Path> input) {
		int[] tmp = new int[] { 13, 14, 8, 6, 10, 12, 11, 4, 7, 2, 1, 3, 9, 5, 0 };
		ArrayList<Path> result = new ArrayList<Path>();
		for (int i = 0; i < tmp.length; i++) {
			result.add(input.get(tmp[i]));
		}
		return result;
	}

	public static ArrayList<Node> shuffleCavesStatic(ArrayList<Node> input) {
		int[] tmp = new int[] { 7, 13, 15, 12, 8, 4, 10, 14, 11, 3, 5, 2, 9, 16, 6, 0, 1 };
		ArrayList<Node> result = new ArrayList<Node>();
		for (int i = 0; i < tmp.length; i++) {
			result.add(input.get(tmp[i]));
		}
		return result;
	}*/

}
