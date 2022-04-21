package com.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import com.dungeon.data.ArrowLocations;
import com.dungeon.data.DungeonAndLeftOverPath;
import com.dungeon.data.GameOtyughDetails;
import com.dungeon.data.NearbyOtyughs;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.data.TreasureCollected;
import com.dungeon.v2.ArrowsV2;
import com.dungeon.v2.DungeonV2;
import com.dungeon.v2.OtyughsV2;
import com.dungeon.v2.TreasureV2;

/**
 * This is the Game Model that contains all the functionaliies of the model.
 * This includes generation of dungeon maze with caves and tunnels.
 * It also includes allocation of treasure, otyughs and arrows.
 * Finally the play game method is used to execute the entire game.
 */

public class GameModel implements ModelInterface {

  public int noOfArrowsCollected = 3;

  /**
   * The method to generate the dungeon.
   *
   * @param rows                 no of rows
   * @param cols                 no of cols
   * @param interConnectivity    interconnections
   * @param percentageOfTreasure percentage of treasure to be allocated
   * @param wrapping             wrapping boolean
   * @param noofOtyughs          no of otyughs
   * @param needStaticGame       static or random
   * @return the final dungeon object
   */

  @Override
  public DungeonV2 generateDungeon(int rows, int cols, int interConnectivity,
                                   int percentageOfTreasure, boolean wrapping,
                                   int noofOtyughs, boolean needStaticGame) {
    //Validated inputs
    System.out.println("Number of Rows : " + rows);
    System.out.println("Number of Columns : " + cols);
    System.out.println("Interconnectivity : " + interConnectivity);
    System.out.println("Percentage of Treasure : " + percentageOfTreasure);
    System.out.println("Wrapping : " + wrapping);
    System.out.println("Number of Extra Otyughs : " + noofOtyughs);

    line();
    Scanner inputScanner = new Scanner(System.in);
    DungeonLevel dungeonLevel = new DungeonLevel();
    ArrayList<Node> nodes = dungeonLevel.generateNodes(rows, cols);


    // Identify Potential Paths
    ArrayList<Path> potentialPaths = dungeonLevel.identifyPaths(rows, cols, nodes);

    // Shuffle potential paths - So random order will be generated
    if (needStaticGame) {
      potentialPaths = shufflePathsStatic(potentialPaths);
    } else {
      dungeonLevel.shufflePaths(potentialPaths);
    }


    // Generate Dungeon
    TraverseNode traverseNode = new TraverseNode();
    DungeonAndLeftOverPath dungeonAndLeftOverPath
            = traverseNode.generateDungeonAndLeftOverPath(potentialPaths);

    ArrayList<ArrayList<Path>> maze = dungeonAndLeftOverPath.getDungeon();
    ArrayList<Path> leftOverPaths = dungeonAndLeftOverPath.getLeftOverPaths();


    // Shuffle leftover paths - So random order will be generated to pick and start
    // InterConnection
    if (needStaticGame) {
      leftOverPaths = shuffleLeftOverStatic(leftOverPaths);
    } else {
      traverseNode.shuffleLeftOverPaths(leftOverPaths);
    }

    int interCount = traverseNode.validateInterconnectivityPathWithLeftOverPath(leftOverPaths,
            interConnectivity);
    if (maze.get(0) == null) {
      System.err.println("Dungeon error, dungeon grid is empty");
      return null;
    }

    // Applying interconnection
    ArrayList<Path> finalDungeon = maze.get(0);
    ArrayList<Path> interConnectedPaths = traverseNode.applyInterconnectivity(interCount,
            leftOverPaths, finalDungeon);

    GenerateDungeon generateDungeon = new GenerateDungeon();
    // Identify Edges which has only 2 paths & make them tunnel
    ArrayList<Node> tunnels = generateDungeon.identifyTunnel(finalDungeon, rows, cols);


    // Identify Caves for treasure allocation
    ArrayList<Node> caves = generateDungeon.identifyCaves(finalDungeon, tunnels);
    //System.out.println("No. of Caves in Dungeon is : " + caves.size());

    float percent = (float) percentageOfTreasure;
    int numberOfCavesTreasureAllocated = (int) (percent / 100 * caves.size());
    System.out
            .println(percentageOfTreasure + "% of " + caves.size() + " Caves is : "
                    + numberOfCavesTreasureAllocated
                    + ", so treasure will be allocated for "
                    + numberOfCavesTreasureAllocated + " Caves");

    // Shuffle caves, so random caves will be selected for allocating treasure
    if (needStaticGame) {
      caves = shuffleCavesStatic(caves);
    } else {
      Collections.shuffle(caves);
    }

    // Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(false);

    return new DungeonV2(mazeObj, numberOfCavesTreasureAllocated, caves,
            treasureMaster, finalDungeon, tunnels);
  }

  /**
   * method to allocate the treasure.
   *
   * @param rows                           no of rows
   * @param cols                           no of cols
   * @param wrapping                       wrapping boolean
   * @param mazeObj                        maze object
   * @param numberOfCavesTreasureAllocated caves with treasure in it
   * @param caves                          list of caves
   * @param treasureMaster                 treasure master for determining treasure
   * @param finalDungeon                   final maze with caves and tunnels
   * @param needStaticGame                 static or random
   * @return treasure object
   */

  @Override
  public TreasureV2 allocateTreasure(int rows, int cols, boolean wrapping,
                                     Maze mazeObj, int numberOfCavesTreasureAllocated,
                                     ArrayList<Node> caves, ArrayList<Treasure> treasureMaster,
                                     ArrayList<Path> finalDungeon, boolean needStaticGame) {

    // Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves
            = mazeObj.allocateTreasure(numberOfCavesTreasureAllocated,
            caves, treasureMaster, false);


    // Dungeon successfully generated
    System.out.println("Dungeon is successfully generated, setting player start and end position");

    // Shuffle the final dungeon, so random start point will be picked as first
    // index of the array
    if (needStaticGame) {
      finalDungeon = generateRandomStartStatic(finalDungeon);
    } else {
      mazeObj.generateRandomStart(finalDungeon);
    }

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
    PlayerLocations playerPossibleLocations
            = player.getPossibleMoves(rows, cols, finalDungeon, playerCurrentlyAt,
            wrapping);
    //System.out.println("Player start and end position has been set.");

    return new TreasureV2(caves, playerStartAt, playerEndAt, getPlayerEndLocationIndex,
            playerPossibleLocations, player, finalDungeon, treasureCaves);
  }

  /**
   * method to allocate the otyughs in caves.
   *
   * @param caves          caves list
   * @param playerStartAt  player start location
   * @param playerEndAt    player end location
   * @param noofOtyughs    no of otyughs
   * @param needStaticGame static or random
   * @return otyugh details
   */

  @Override
  public OtyughsV2 allocateOtyughs(ArrayList<Node> caves, Node playerStartAt,
                                   Node playerEndAt, int noofOtyughs,
                                   ArrayList<Node> tunnels, boolean needStaticGame) {

    UpdateGame updateGame = new UpdateGame();

    ArrayList<Otyugh> otyughLocations = new ArrayList<Otyugh>();

    // Shuffle caves, to allocate Otyugh in random locations
    if (needStaticGame) {
      caves = shuffleCavesStatic(caves);
    } else {
      Collections.shuffle(caves);
    }

    int noofOtyughsAllocated = 0;


    otyughLocations.add(new Otyugh(playerEndAt, 100));
    noofOtyughsAllocated = 1;

    for (int i = 0; i < caves.size(); i++) {
      Node eachCave = caves.get(i);

      // Checking to ensure at player start location has no Otyugh
      if (playerStartAt.getX() != eachCave.getX() && playerStartAt.getY() != eachCave.getY()) {
        if (playerEndAt.getX() != eachCave.getX() && playerEndAt.getY() != eachCave.getY()) {
          otyughLocations.add(new Otyugh(eachCave, 100));
          noofOtyughsAllocated = noofOtyughsAllocated + 1;
        }
      }

      if (noofOtyughsAllocated == noofOtyughs) {
        break;
      }
    }

    System.out.println("\n" + otyughLocations.size() + " Otyughs has been allocated in the Game");


    return new OtyughsV2(noofOtyughsAllocated, caves, updateGame, tunnels, otyughLocations);
  }

  /**
   * method to allocate the arrows.
   *
   * @param numberOfCavesTreasureAllocated no of otyughs
   * @param caves                          list of caves
   * @param updateGame                     update game details
   * @param tunnels                        tunnel list
   * @param otyughLocations                location of otyughs
   * @param rows                           no of rows
   * @param cols                           no of cols
   * @param wrapping                       wrapping boolean
   * @param playerPossibleLocations        player location possibility
   * @param player                         player
   * @param finalDungeon                   dungeon with caves and tunnels
   * @param treasureCaves                  cavs with treasure
   * @param playerCurrentlyAt              player current location
   * @param playerEndAt                    player start location
   * @param needStaticGame                 static or random
   * @return arrow allocation details
   */

  @Override
  public ArrowsV2 allocateArrows(int numberOfCavesTreasureAllocated, ArrayList<Node> caves,
                                 UpdateGame updateGame, ArrayList<Node> tunnels,
                                 ArrayList<Otyugh> otyughLocations,
                                 int rows, int cols, boolean wrapping,
                                 PlayerLocations playerPossibleLocations,
                                 Player player, ArrayList<Path> finalDungeon,
                                 ArrayList<HashMap<Node, Treasure>> treasureCaves,
                                 Node playerCurrentlyAt, Node playerEndAt,
                                 boolean needStaticGame) {

    Scanner inputScanner = new Scanner(System.in);

    ArrayList<ArrowLocations> arrowLocations = new ArrayList<ArrowLocations>();

    int noOfArrows = numberOfCavesTreasureAllocated;
    System.out.println(numberOfCavesTreasureAllocated);
    // Allocate 50% of arrows into Caves
    int percentageOfArrowInTunnel = noOfArrows / 2;
    // Allocate 50% of arrows into Tunnels
    int percentageOfArrowsInCave = noOfArrows - percentageOfArrowInTunnel;
    // Shuffle caves, to allocate Arrows in random locations
    if (needStaticGame) {
      caves = shuffleCavesStatic(caves);
    } else {
      Collections.shuffle(caves);
    }
    ArrayList<Integer> arrowMaster = updateGame.playerArrowMaster(false);
    // Shuffle tunnels, to allocate Arrows in random locations
    if (needStaticGame) {
      tunnels = shuffleTunnelsStatic(tunnels);
    } else {
      Collections.shuffle(tunnels);
    }
    int numberOfArrowsAllocatedInCaves = 0;
    int numberOfCaves = 0;
    for (int i = 0; i < percentageOfArrowsInCave; i++) {
      Node eachCave = caves.get(i);
      arrowLocations.add(new ArrowLocations(eachCave, arrowMaster.get(i % 3).intValue()));
      numberOfArrowsAllocatedInCaves += arrowMaster.get(i % 3).intValue();
      numberOfCaves++;
    }

    arrowMaster = updateGame.playerArrowMaster(false);
    // Shuffle tunnels, to allocate Arrows in random locations
    if (needStaticGame) {
      tunnels = shuffleTunnelsStatic(tunnels);
    } else {
      Collections.shuffle(tunnels);
    }

    int numberOfArrowsAllocatedInTunnel = 0;
    int numberOfTunnels = 0;
    for (int i = 0; i < percentageOfArrowInTunnel; i++) {
      Node eachTunnel = tunnels.get(i);
      arrowLocations.add(new ArrowLocations(eachTunnel, arrowMaster.get(i % 3).intValue()));
      numberOfArrowsAllocatedInTunnel += arrowMaster.get(i % 3).intValue();
      numberOfTunnels++;
    }

    int totalArrowsAllocated = 3;

    // Populate Arrows & it's locations
    for (int i = 0; i < arrowLocations.size(); i++) {
      ArrowLocations eachArrow = arrowLocations.get(i);
      totalArrowsAllocated += eachArrow.getNoOfArrows();
    }

    System.out.println("Apart from 3 default Arrow to start the game, "
            + (totalArrowsAllocated - 3)
            + " more Arrows has been allocated to kill "
            + otyughLocations.size() + " Otyughs, within "
            + numberOfCaves + " Cave(s) and "
            + numberOfTunnels
            + " Tunnel(s).\n");

    // Populate Arrows & it's locations
    for (int i = 0; i < arrowLocations.size(); i++) {
      ArrowLocations eachArrow = arrowLocations.get(i);
      totalArrowsAllocated += eachArrow.getNoOfArrows();
      System.out.println("Location is (" + eachArrow.getLocation().getX() + ","
              + eachArrow.getLocation().getY()
              + "), Number of Arrows on this location : " + eachArrow.getNoOfArrows());
    }

    return new ArrowsV2(rows, cols, wrapping, updateGame, otyughLocations,
            playerPossibleLocations, player, finalDungeon, treasureCaves,
            playerCurrentlyAt, playerEndAt, caves, arrowLocations, inputScanner);
  }

  /**
   * method to carry of the game.
   *
   * @param rows                    no of rows
   * @param cols                    no of cols
   * @param wrapping                wrapping boolean
   * @param updateGame              update game details
   * @param otyughLocations         location of otyughs
   * @param playerPossibleLocations player location possibility
   * @param player                  player
   * @param finalDungeon            dungeon with caves and tunnels
   * @param treasureCaves           cavs with treasure
   * @param playerCurrentlyAt       player current location
   * @param playerEndAt             player start location
   * @param caves                   list of caves
   * @param arrowLocations          arrow location nodes
   * @param inputScanner            scanner
   * @param needStaticGame          random or static
   */

  @Override
  public void playGame(int rows, int cols, boolean wrapping, UpdateGame updateGame,
                       ArrayList<Otyugh> otyughLocations, PlayerLocations playerPossibleLocations,
                       Player player, ArrayList<Path> finalDungeon,
                       ArrayList<HashMap<Node, Treasure>> treasureCaves,
                       Node playerCurrentlyAt, Node playerEndAt,
                       ArrayList<Node> caves, ArrayList<ArrowLocations> arrowLocations,
                       Scanner inputScanner, boolean needStaticGame) {

    // No. of arrows collected
    int noOfArrowsCollected = 3;
    line();
    System.out.println("\nTHE GAME HAS BEGUN\n");
    if (wrapping) {
      System.out.println("This is a Wrapping Dungeon");
    } else {
      System.out.println("This is a Non-Wrapping Dungeon");
    }
    System.out.println("The player START position is (" + playerCurrentlyAt.getX()
            + ", " + playerCurrentlyAt.getY() + ").");
    System.out.println("The player END position is (" + playerEndAt.getX() + ", "
            + playerEndAt.getY() + ").");
    System.out.println(
            "You can move the player now by typing N - for North, E - for East,"
                    + " S - for South, W - for West.  \n");
    line();

    // Set collected treasure details as 0,0,0 for Diamond, Ruby, Sapphire
    // respectively
    TreasureCollected treasureCollected = new TreasureCollected(0, 0, 0);
    GameOtyughDetails gameOtyughDetails = null;
    int loopNumber = 1;
    for (; ; ) {

      gameOtyughDetails = updateGame.getGameOtyughDetails(otyughLocations,
              playerPossibleLocations, player, finalDungeon, rows, cols, wrapping);
      int noOfOtyughsInOnePath = gameOtyughDetails.getNoOfOtyughsInOnePath();
      int noOfOtyughsInTwoPath = gameOtyughDetails.getNoOfOtyughsInTwoPath();
      ArrayList<NearbyOtyughs> nearByOtyughs = gameOtyughDetails.getNearByOtyughs();

      //System.out.println("Number of Otyughs in 1 location : " + noOfOtyughsInOnePath
      //+ " & Nuber of Otyughs in 2 locations : " + noOfOtyughsInTwoPath);
      //System.out.println();
      // TreasureCollected treasureCollected =
      // player.checkPlayerStartingCaveHasTreasure(treasureCaves, playerCurrentlyAt);
      Treasure treasureDetailsOfPlayerCurrentCave = player.getTreasureDetailsOfCave(treasureCaves,
              playerCurrentlyAt);

      boolean isCave = updateGame.isCave(playerEndAt, caves);

      int numberOfArrowsHere = updateGame.getNumberOfArrowsInCurrentLocation(arrowLocations,
              playerCurrentlyAt);

      // validateAndUpdateGame(treasureCollected, playerStartAt,
      // treasureDetailsOfPlayerCurrentCave, playerPossibleLocations);
      updateGame.validateAndUpdateGame(numberOfArrowsHere, noOfArrowsCollected, treasureCollected,
              playerCurrentlyAt, treasureDetailsOfPlayerCurrentCave,
              playerPossibleLocations, isCave);

      if (noOfOtyughsInOnePath > 0 || noOfOtyughsInTwoPath > 1) {
        System.out.println("\nYou smell something terrible nearby");
      } else if (noOfOtyughsInTwoPath == 1) {
        System.out.println("\nYou smell something little nearby");
      }

      System.out.println("Move, Pickup, or Shoot (M-P-S)?");
      String userInput = updateGame.getPlayerInput(inputScanner);

      String nextMove = "";
      if (userInput.equals("M")) {

        if (isCave) {
          System.out.println("Where to?, you are in a cave at (" + playerCurrentlyAt.getX() + ", "
                  + playerCurrentlyAt.getY() + ") and possible moves are (North["
                  + playerPossibleLocations.getNorth().getX() + ", "
                  + playerPossibleLocations.getNorth().getY() + "]:"
                  + playerPossibleLocations.hasNorth()
                  + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                  + playerPossibleLocations.getEast().getY() + "]:"
                  + playerPossibleLocations.hasEast()
                  + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                  + playerPossibleLocations.getSouth().getY() + "]:"
                  + playerPossibleLocations.hasSouth()
                  + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                  + playerPossibleLocations.getWest().getY() + "]:"
                  + playerPossibleLocations.hasWest()
                  + ")");
        } else {
          System.out.println("Where to?, you are in a tunnel at (" + playerCurrentlyAt.getX() + ", "
                  + playerCurrentlyAt.getY() + ") and possible moves are (North["
                  + playerPossibleLocations.getNorth().getX() + ", "
                  + playerPossibleLocations.getNorth().getY() + "]:"
                  + playerPossibleLocations.hasNorth()
                  + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                  + playerPossibleLocations.getEast().getY() + "]:"
                  + playerPossibleLocations.hasEast()
                  + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                  + playerPossibleLocations.getSouth().getY() + "]:"
                  + playerPossibleLocations.hasSouth()
                  + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                  + playerPossibleLocations.getWest().getY() + "]:"
                  + playerPossibleLocations.hasWest()
                  + ")");
        }

        System.out.println("\nPlease input your move [case-sensitive] (N/E/S/W):");
        // Validate user input & update game
        nextMove = updateGame.getPlayerMoveInput(inputScanner,
                playerPossibleLocations, playerCurrentlyAt);

        System.out.println("\nMove accepted & player successfully moved to the next location");
        playerCurrentlyAt = player.getNextMoveCave(playerPossibleLocations, nextMove);
        playerPossibleLocations = player.getPossibleMoves(rows, cols,
                finalDungeon, playerCurrentlyAt,
                wrapping);

        int otyughDecisionInPlayerMovedLocation = updateGame.isPlayerEatenByOtyugh(otyughLocations,
                playerCurrentlyAt, loopNumber);
        if (otyughDecisionInPlayerMovedLocation == 1) {
          System.out.println("Chomp, chomp, chomp, you are eaten by an Otyugh!");
          System.out.println("Better luck next time");
          System.out.println("#................Game over................#");
          break;
        } else if (otyughDecisionInPlayerMovedLocation == 2) {
          System.out.println("Chomp, chomp, chomp, you are eaten by an "
                  + "Otyugh which has only 50% health");
          System.out.println("Better luck next time");
          System.out.println("#................Game over................#");
          break;
        } else if (otyughDecisionInPlayerMovedLocation == 3) {
          System.out.println("You are lucky");
          System.out.println("You escaped from Otyugh at your current location ("
                  + playerCurrentlyAt.getX() + ", " + playerCurrentlyAt.getY()
                  + ") which has 50% health");
        }
      } else if (userInput.equals("P")) {
        System.out.println("What (Treasure, Arrow)?");
        String treasureToPick = updateGame.getPlayerPickInput(inputScanner);
        if (treasureToPick.equals("Arrow")) {
          int noOfArrowsInCurrentLocation = 0;
          // ArrayList<ArrowLocations> arrowLocations
          for (int i = 0; i < arrowLocations.size(); i++) {
            ArrowLocations eachArrowLocation = arrowLocations.get(i);
            if (eachArrowLocation.getLocation().getX() == playerCurrentlyAt.getX()
                    && eachArrowLocation.getLocation().getY() == playerCurrentlyAt.getY()) {
              noOfArrowsInCurrentLocation = eachArrowLocation.getNoOfArrows();
              noOfArrowsCollected += eachArrowLocation.getNoOfArrows();
              eachArrowLocation.setNoOfArrows(0);
              break;
            }
          }
          System.out.println("You pick up " + noOfArrowsInCurrentLocation + " Arrow(s)");
        } else {
          int dimondPicked = 0;
          int rubyPicked = 0;
          int sapphiePicked = 0;
          // treasureDetailsOfPlayerCurrentCave =
          // player.getTreasureDetailsOfCave(treasureCaves, playerCurrentlyAt);
          if (treasureDetailsOfPlayerCurrentCave.hasDiamond()) {
            treasureCollected.addDiamond(1);
            dimondPicked = 1;
            treasureDetailsOfPlayerCurrentCave.setHasDiamond(false);
          }
          if (treasureDetailsOfPlayerCurrentCave.hasRuby()) {
            treasureCollected.addRuby(1);
            rubyPicked = 1;
            treasureDetailsOfPlayerCurrentCave.setHasRuby(false);
          }
          if (treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
            treasureCollected.addSapphire(1);
            sapphiePicked = 1;
            treasureDetailsOfPlayerCurrentCave.setHasSapphire(false);
          }
          System.out.println("You pick up " + dimondPicked + " Diamonds, "
                  + rubyPicked + " Rubies, "
                  + sapphiePicked + " Sapphires");
        }
      } else if (userInput.equals("S")) {
        if (noOfArrowsCollected <= 0) {
          System.out.println("You are out of arrows, explore to find more");
          continue;
        }

        if (isCave) {
          System.out.println("Where to?, you are in a cave at (" + playerCurrentlyAt.getX() + ", "
                  + playerCurrentlyAt.getY() + ") and possible directions are (North["
                  + playerPossibleLocations.getNorth().getX() + ", "
                  + playerPossibleLocations.getNorth().getY() + "]:"
                  + playerPossibleLocations.hasNorth()
                  + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                  + playerPossibleLocations.getEast().getY() + "]:"
                  + playerPossibleLocations.hasEast()
                  + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                  + playerPossibleLocations.getSouth().getY() + "]:"
                  + playerPossibleLocations.hasSouth()
                  + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                  + playerPossibleLocations.getWest().getY() + "]:"
                  + playerPossibleLocations.hasWest()
                  + ")");
        } else {
          System.out.println("Where to?, you are in a tunnel at (" + playerCurrentlyAt.getX() + ", "
                  + playerCurrentlyAt.getY() + ") and possible directions are (North["
                  + playerPossibleLocations.getNorth().getX() + ", "
                  + playerPossibleLocations.getNorth().getY() + "]:"
                  + playerPossibleLocations.hasNorth()
                  + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                  + playerPossibleLocations.getEast().getY() + "]:"
                  + playerPossibleLocations.hasEast()
                  + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                  + playerPossibleLocations.getSouth().getY() + "]:"
                  + playerPossibleLocations.hasSouth()
                  + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                  + playerPossibleLocations.getWest().getY() + "]:"
                  + playerPossibleLocations.hasWest()
                  + ")");
        }

        System.out.println("\nPlease input the direction to shoot [case-sensitive] (N/E/S/W):");
        // Validate user input & get shoot direction
        String shootDirection = updateGame.getPlayerMoveInput(inputScanner, playerPossibleLocations,
                playerCurrentlyAt);
        System.out.println("No. of caves (1-5)?");

        int inputDistance = Integer.parseInt(updateGame.getPlayerShootDistanceInput(inputScanner));
        // Reducing one arrow from stock as used by player
        noOfArrowsCollected--;

        gameOtyughDetails = updateGame.getGameOtyughDetails(otyughLocations,
                playerPossibleLocations, player,
                finalDungeon, rows, cols, wrapping);
        // noOfOtyughsInOnePath=gameOtyughDetails.getNoOfOtyughsInOnePath();
        // noOfOtyughsInTwoPath=gameOtyughDetails.getNoOfOtyughsInTwoPath();
        nearByOtyughs = gameOtyughDetails.getNearByOtyughs();
        boolean isOtyughAttached = false;
        for (int i = 0; i < nearByOtyughs.size(); i++) {
          NearbyOtyughs eachNearByOtyughs = nearByOtyughs.get(i);
          if (inputDistance == eachNearByOtyughs.getNumberPathToOtyugh()
                  && eachNearByOtyughs.getDirectionTowardsOtyugh()
                  .toString().equals(shootDirection)) {
            //Reduce Otyugh health by 50% if not attacked,
            // if already attacked then kill it by making life 0%
            for (int j = 0; j < otyughLocations.size(); j++) {
              Otyugh eachOtyughLocation = otyughLocations.get(j);
              if (eachNearByOtyughs.getOtyughLocation().getX()
                      == eachOtyughLocation.getOtyughLocation().getX()
                      && eachNearByOtyughs.getOtyughLocation().getY()
                      == eachOtyughLocation.getOtyughLocation().getY()
                      && eachOtyughLocation.getOtyughHealth() > 0) {
                if (eachOtyughLocation.getOtyughHealth() == 100) {
                  eachOtyughLocation.setOtyughHealth(50);
                  System.out.println("\nYou hear a great howl in the distance");
                } else {
                  eachOtyughLocation.setOtyughHealth(0);
                  System.out.println("\nYou killed the Otyugh, present in the location ("
                          + eachOtyughLocation.getOtyughLocation().getX() + ", "
                          + eachOtyughLocation.getOtyughLocation().getY() + ")");
                }
                isOtyughAttached = true;
                break;
              }
            }
          }
        }
        if (!isOtyughAttached) {
          System.out.println("\nYou shoot an arrow into the darkness");
        }

        if (noOfArrowsCollected == 0) {
          System.out.println("\nYou are out of arrows, explore to find more");
        }
      }


      if (player.isDestinationReached(playerCurrentlyAt, playerEndAt)) {
        System.out.println("Congratulations.....!!!");
        System.out.println("You have successfully reached the destination.");

        System.out.println("#................Game completed................#");
        break;
      }

      loopNumber++;
    }

    inputScanner.close();
  }

  /**
   * a separator.
   */

  public static void line() {
    System.out.println("_______________________________________________________________________\n");
  }

  /**
   * method to pass static shuffled inputs for test cases.
   *
   * @param input path list
   * @return static shuffled path list
   */
  public static ArrayList<Path> shufflePathsStatic(ArrayList<Path> input) {
    int[] tmp = new int[]{12, 14, 21, 25, 30, 35, 37, 10, 11, 23, 13, 28, 31,
      32, 36, 18, 17, 9, 15, 16, 19, 20,
      6, 8, 34, 24, 29, 5, 4, 22, 26, 27, 33, 2, 3, 7, 1, 0};
    ArrayList<Path> result = new ArrayList<Path>();
    for (int i = 0; i < tmp.length; i++) {
      result.add(input.get(tmp[i]));
    }
    return result;
  }

  /**
   * method to pass static shuffled inputs for test cases.
   *
   * @param input path list
   * @return static shuffled leftover list
   */
  public static ArrayList<Path> shuffleLeftOverStatic(ArrayList<Path> input) {
    int[] tmp = new int[]{13, 14, 8, 6, 10, 12, 11, 4, 7, 2, 1, 3, 9, 5, 0};
    ArrayList<Path> result = new ArrayList<Path>();
    for (int i = 0; i < tmp.length; i++) {
      result.add(input.get(tmp[i]));
    }
    return result;
  }

  /**
   * method to pass static shuffled inputs for test cases.
   *
   * @param input node list
   * @return static shuffled caves list
   */
  public static ArrayList<Node> shuffleCavesStatic(ArrayList<Node> input) {
    int[] tmp = new int[]{7, 13, 15, 12, 8, 4, 10, 14, 11, 3, 5, 2, 9, 16, 6, 0, 1};
    ArrayList<Node> result = new ArrayList<Node>();
    for (int i = 0; i < tmp.length; i++) {
      result.add(input.get(tmp[i]));
    }
    return result;
  }

  /**
   * method to pass static shuffled inputs for test cases.
   *
   * @param input node list
   * @return static shuffled tunnel list
   */

  public static ArrayList<Node> shuffleTunnelsStatic(ArrayList<Node> input) {
    int[] tmp = new int[]{9, 7, 3, 2, 5, 1, 11, 8, 10, 6, 4, 0, 12};
    ArrayList<Node> result = new ArrayList<Node>();
    for (int i = 0; i < tmp.length; i++) {
      result.add(input.get(tmp[i]));
    }
    return result;
  }

  /**
   * method to pass static shuffled inputs for test cases.
   *
   * @param input path list
   * @return static shuffled for getting start
   */
  public static ArrayList<Path> generateRandomStartStatic(ArrayList<Path> input) {
    int[] tmp = new int[]{30, 14, 21, 25, 12, 10, 11, 23, 13, 28, 31, 32, 18, 17,
      9, 15, 16, 19, 20, 6, 8, 34, 24,
      29, 5, 4, 22, 26, 27, 33, 2, 3, 7, 1, 0};
    ArrayList<Path> result = new ArrayList<Path>();
    for (int i = 0; i < tmp.length; i++) {
      result.add(input.get(tmp[i]));
    }
    return result;
  }

  /**
   * method to carry of the game.
   *
   * @param rows                    no of rows
   * @param cols                    no of cols
   * @param wrapping                wrapping boolean
   * @param updateGame              update game details
   * @param otyughLocations         location of otyughs
   * @param playerPossibleLocations player location possibility
   * @param player                  player
   * @param finalDungeon            dungeon with caves and tunnels
   * @param treasureCaves           cavs with treasure
   * @param playerCurrentlyAt       player current location
   * @param playerEndAt             player start location
   * @param caves                   list of caves
   * @param arrowLocations          arrow location nodes
   * @param inputScanner            scanner
   * @param needStaticGame          random or static
   */

  public String playGameAndGetSmellStatus(int rows, int cols, boolean wrapping,
                                          UpdateGame updateGame, ArrayList<Otyugh> otyughLocations,
                                          PlayerLocations playerPossibleLocations,
                                          Player player, ArrayList<Path> finalDungeon,
                                          ArrayList<HashMap<Node, Treasure>> treasureCaves,
                                          Node playerCurrentlyAt, Node playerEndAt,
                                          ArrayList<Node> caves,
                                          ArrayList<ArrowLocations> arrowLocations,
                                          Scanner inputScanner,
                                          boolean needStaticGame) {

    // No. of arrows collected
    int noOfArrowsCollected = 3;

    TreasureCollected treasureCollected = new TreasureCollected(0, 0, 0);
    GameOtyughDetails gameOtyughDetails = null;
    int loopNumber = 1;

    gameOtyughDetails = updateGame.getGameOtyughDetails(otyughLocations,
            playerPossibleLocations, player,
            finalDungeon, rows, cols, wrapping);
    int noOfOtyughsInOnePath = gameOtyughDetails.getNoOfOtyughsInOnePath();
    int noOfOtyughsInTwoPath = gameOtyughDetails.getNoOfOtyughsInTwoPath();
    ArrayList<NearbyOtyughs> nearByOtyughs = gameOtyughDetails.getNearByOtyughs();

    Treasure treasureDetailsOfPlayerCurrentCave = player.getTreasureDetailsOfCave(treasureCaves,
            playerCurrentlyAt);

    boolean isCave = updateGame.isCave(playerEndAt, caves);

    int numberOfArrowsHere = updateGame.getNumberOfArrowsInCurrentLocation(arrowLocations,
            playerCurrentlyAt);

    updateGame.validateAndUpdateGame(numberOfArrowsHere, noOfArrowsCollected, treasureCollected,
            playerCurrentlyAt, treasureDetailsOfPlayerCurrentCave, playerPossibleLocations, isCave);

    if (noOfOtyughsInOnePath > 0 || noOfOtyughsInTwoPath > 1) {
      return "You smell something terrible nearby";
    } else if (noOfOtyughsInTwoPath == 1) {
      return "You smell something little nearby";
    }
    return "You don't smell anything nearby";
  }

  /**
   * method to carry of the game.
   *
   * @param rows                    no of rows
   * @param cols                    no of cols
   * @param wrapping                wrapping boolean
   * @param updateGame              update game details
   * @param otyughLocations         location of otyughs
   * @param playerPossibleLocations player location possibility
   * @param player                  player
   * @param finalDungeon            dungeon with caves and tunnels
   * @param treasureCaves           cavs with treasure
   * @param playerCurrentlyAt       player current location
   * @param playerEndAt             player start location
   * @param caves                   list of caves
   * @param arrowLocations          arrow location nodes
   * @param inputScanner            scanner
   * @param needStaticGame          random or static
   * @param nextMove                move to make
   * @param loopNumber              loop number
   */

  public String playGameUsingTestCase(int rows, int cols, boolean wrapping,
                                      UpdateGame updateGame, ArrayList<Otyugh> otyughLocations,
                                      PlayerLocations playerPossibleLocations,
                                      Player player, ArrayList<Path> finalDungeon,
                                      ArrayList<HashMap<Node, Treasure>> treasureCaves,
                                      Node playerCurrentlyAt, Node playerEndAt,
                                      ArrayList<Node> caves,
                                      ArrayList<ArrowLocations> arrowLocations,
                                      Scanner inputScanner,
                                      boolean needStaticGame, String userInput,
                                      String shootDirection, int inputDistance,
                                      String nextMove, int loopNumber) {

    String shootResult = "";

    TreasureCollected treasureCollected = new TreasureCollected(0, 0, 0);
    GameOtyughDetails gameOtyughDetails = null;

    gameOtyughDetails = updateGame.getGameOtyughDetails(otyughLocations,
            playerPossibleLocations, player,
            finalDungeon, rows, cols, wrapping);
    int noOfOtyughsInOnePath = gameOtyughDetails.getNoOfOtyughsInOnePath();
    int noOfOtyughsInTwoPath = gameOtyughDetails.getNoOfOtyughsInTwoPath();
    ArrayList<NearbyOtyughs> nearByOtyughs = gameOtyughDetails.getNearByOtyughs();

    Treasure treasureDetailsOfPlayerCurrentCave = player.getTreasureDetailsOfCave(treasureCaves,
            playerCurrentlyAt);

    boolean isCave = updateGame.isCave(playerEndAt, caves);

    int numberOfArrowsHere = updateGame.getNumberOfArrowsInCurrentLocation(arrowLocations,
            playerCurrentlyAt);

    updateGame.validateAndUpdateGame(numberOfArrowsHere, noOfArrowsCollected, treasureCollected,
            playerCurrentlyAt, treasureDetailsOfPlayerCurrentCave, playerPossibleLocations, isCave);

    if (noOfOtyughsInOnePath > 0 || noOfOtyughsInTwoPath > 1) {
      //System.out.println("You smell something terrible nearby");
      shootResult = "You smell something terrible nearby";
    } else if (noOfOtyughsInTwoPath == 1) {
      //System.out.println("You smell something little nearby");
      shootResult = "You smell something little nearby";
    }

    //System.out.println("Move, Pickup, or Shoot (M-P-S)?");

    if (userInput.equals("M")) {

      if (isCave) {
        System.out.println("Where to?, you are in a cave at (" + playerCurrentlyAt.getX() + ", "
                + playerCurrentlyAt.getY() + ") and possible moves are (North["
                + playerPossibleLocations.getNorth().getX() + ", "
                + playerPossibleLocations.getNorth().getY() + "]:"
                + playerPossibleLocations.hasNorth()
                + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                + playerPossibleLocations.getEast().getY() + "]:"
                + playerPossibleLocations.hasEast()
                + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                + playerPossibleLocations.getSouth().getY() + "]:"
                + playerPossibleLocations.hasSouth()
                + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                + playerPossibleLocations.getWest().getY() + "]:"
                + playerPossibleLocations.hasWest()
                + ")");
      } else {
        System.out.println("Where to?, you are in a tunnel at (" + playerCurrentlyAt.getX() + ", "
                + playerCurrentlyAt.getY() + ") and possible moves are (North["
                + playerPossibleLocations.getNorth().getX() + ", "
                + playerPossibleLocations.getNorth().getY() + "]:"
                + playerPossibleLocations.hasNorth()
                + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                + playerPossibleLocations.getEast().getY() + "]:"
                + playerPossibleLocations.hasEast()
                + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                + playerPossibleLocations.getSouth().getY() + "]:"
                + playerPossibleLocations.hasSouth()
                + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                + playerPossibleLocations.getWest().getY() + "]:"
                + playerPossibleLocations.hasWest()
                + ")");
      }


      playerPossibleLocations = player.getPossibleMoves(rows, cols, finalDungeon,
              playerCurrentlyAt, wrapping);

      int otyughDecisionInPlayerMovedLocation = updateGame.isPlayerEatenByOtyugh(otyughLocations,
              playerCurrentlyAt, loopNumber);
      System.out.println(" the resonse i s >> " + otyughDecisionInPlayerMovedLocation);
      if (otyughDecisionInPlayerMovedLocation == 1) {
        System.out.println("Chomp, chomp, chomp, you are eaten by an Otyugh!");
        shootResult = "Chomp, chomp, chomp, you are eaten by an Otyugh!";
        System.out.println("Better luck next time");
        System.out.println("#................Game over................#");
      } else if (otyughDecisionInPlayerMovedLocation == 2) {
        System.out.println(
                "Chomp, chomp, chomp, you are eaten by an Otyugh though which has only 50% health");
        System.out.println("Better luck next time");
        System.out.println("#................Game over................#");
      } else if (otyughDecisionInPlayerMovedLocation == 3) {
        System.out.println("You are lucky");
        System.out.println(
                "You got a great escape from Otyugh which has 50% health on your current location ("
                        + playerCurrentlyAt.getX() + ", " + playerCurrentlyAt.getY() + ")");
      }
    } else if (userInput.equals("P")) {
      System.out.println("What (Treasure, Arrow)?");
      String treasureToPick = updateGame.getPlayerPickInput(inputScanner);
      if (treasureToPick.equals("Arrow")) {
        int noOfArrowsInCurrentLocation = 0;

        for (int i = 0; i < arrowLocations.size(); i++) {
          ArrowLocations eachArrowLocation = arrowLocations.get(i);
          if (eachArrowLocation.getLocation().getX() == playerCurrentlyAt.getX()
                  && eachArrowLocation.getLocation().getY() == playerCurrentlyAt.getY()) {
            noOfArrowsInCurrentLocation = eachArrowLocation.getNoOfArrows();
            noOfArrowsCollected += eachArrowLocation.getNoOfArrows();
            eachArrowLocation.setNoOfArrows(0);
            break;
          }
        }
        System.out.println("You pick up " + noOfArrowsInCurrentLocation + " Arrow(s)");
      } else {
        int dimondPicked = 0;
        int rubyPicked = 0;
        int sapphiePicked = 0;

        if (treasureDetailsOfPlayerCurrentCave.hasDiamond()) {
          treasureCollected.addDiamond(1);
          dimondPicked = 1;
          treasureDetailsOfPlayerCurrentCave.setHasDiamond(false);
        }
        if (treasureDetailsOfPlayerCurrentCave.hasRuby()) {
          treasureCollected.addRuby(1);
          rubyPicked = 1;
          treasureDetailsOfPlayerCurrentCave.setHasRuby(false);
        }
        if (treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
          treasureCollected.addSapphire(1);
          sapphiePicked = 1;
          treasureDetailsOfPlayerCurrentCave.setHasSapphire(false);
        }
        System.out.println("You pick up " + dimondPicked + " Diamonds, " + rubyPicked + " Rubies, "
                + sapphiePicked + " Sapphires");
      }
    } else if (userInput.equals("S")) {

      if (isCave) {
        System.out.println("Where to?, you are in a cave at (" + playerCurrentlyAt.getX() + ", "
                + playerCurrentlyAt.getY() + ") and possible directions are (North["
                + playerPossibleLocations.getNorth().getX() + ", "
                + playerPossibleLocations.getNorth().getY() + "]:"
                + playerPossibleLocations.hasNorth()
                + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                + playerPossibleLocations.getEast().getY() + "]:"
                + playerPossibleLocations.hasEast()
                + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                + playerPossibleLocations.getSouth().getY() + "]:"
                + playerPossibleLocations.hasSouth()
                + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                + playerPossibleLocations.getWest().getY() + "]:"
                + playerPossibleLocations.hasWest()
                + ")");
      } else {
        System.out.println("Where to?, you are in a tunnel at (" + playerCurrentlyAt.getX() + ", "
                + playerCurrentlyAt.getY() + ") and possible directions are (North["
                + playerPossibleLocations.getNorth().getX() + ", "
                + playerPossibleLocations.getNorth().getY() + "]:"
                + playerPossibleLocations.hasNorth()
                + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                + playerPossibleLocations.getEast().getY() + "]:"
                + playerPossibleLocations.hasEast()
                + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                + playerPossibleLocations.getSouth().getY() + "]:"
                + playerPossibleLocations.hasSouth()
                + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                + playerPossibleLocations.getWest().getY() + "]:"
                + playerPossibleLocations.hasWest()
                + ")");
      }

      noOfArrowsCollected--;

      gameOtyughDetails = updateGame.getGameOtyughDetails(otyughLocations,
              playerPossibleLocations, player,
              finalDungeon, rows, cols, wrapping);

      nearByOtyughs = gameOtyughDetails.getNearByOtyughs();
      boolean isOtyughAttached = false;
      for (int i = 0; i < nearByOtyughs.size(); i++) {
        NearbyOtyughs eachNearByOtyughs = nearByOtyughs.get(i);
        if (inputDistance == eachNearByOtyughs.getNumberPathToOtyugh()
                && eachNearByOtyughs.getDirectionTowardsOtyugh()
                .toString().equals(shootDirection)) {

          for (int j = 0; j < otyughLocations.size(); j++) {
            Otyugh eachOtyughLocation = otyughLocations.get(j);
            if (eachNearByOtyughs.getOtyughLocation().getX()
                    == eachOtyughLocation.getOtyughLocation()
                    .getX()
                    && eachNearByOtyughs.getOtyughLocation().getY() == eachOtyughLocation
                    .getOtyughLocation().getY()
                    && eachOtyughLocation.getOtyughHealth() > 0) {
              if (eachOtyughLocation.getOtyughHealth() == 100) {
                eachOtyughLocation.setOtyughHealth(50);

                shootResult = "You hear a great howl in the distance";
              } else {
                eachOtyughLocation.setOtyughHealth(0);

                shootResult = "You killed the Otyugh, present in the location ("
                        + eachOtyughLocation.getOtyughLocation().getX() + ", "
                        + eachOtyughLocation.getOtyughLocation().getY() + ")";
              }
              isOtyughAttached = true;
              break;
            }
          }
        }
      }
      if (!isOtyughAttached) {

        shootResult = "You shoot an arrow into the darkness";
      }

    }

    if (player.isDestinationReached(playerCurrentlyAt, playerEndAt)) {
      shootResult = "You have successfully reached the destination.";
    }
    /*
     * else { System.out.
     * println("Please input your move & this is case-sensitive (W/N/E/S):");
     * nextMove = getPlayerInput(inputScanner, playerPossibleLocations,
     * playerCurrentlyAt); }
     */
    loopNumber++;
    return shootResult;
  }
}
