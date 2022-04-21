package gametest;

import com.dungeon.DungeonLevel;
import com.dungeon.GameModel;
import com.dungeon.GenerateDungeon;
import com.dungeon.Maze;
import com.dungeon.TraverseNode;
import com.dungeon.UpdateGame;
import com.dungeon.data.DungeonAndLeftOverPath;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;
import com.dungeon.data.Path;
import com.dungeon.data.Treasure;
import com.dungeon.v2.ArrowsV2;
import com.dungeon.v2.DungeonV2;
import com.dungeon.v2.OtyughsV2;
import com.dungeon.v2.TreasureV2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit4 test class for testing the all functionalities involving the Player.
 */

public class PlayerGameTest {

  @Test
  public void testIfPlayerDies() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 50;
    int noofOtyughs = 2;
    boolean wrapping = false;
    boolean needStaticGame = true;

    String userInput = "M";
    String shootDirection = "";
    int inputDistance = 0;
    String nextMove = "W";
    int loopNumber = 0;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    //Override fixed start & end location of the player
    treasureV2.setPlayerStartAt(new Node(4, 1));
    treasureV2.setPlayerEndAt(new Node(0, 2));

    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);
    //Override with fixed otyugh locations
    ArrayList<Otyugh> staticOtyughs = new ArrayList<Otyugh>();
    staticOtyughs.add(new Otyugh(new Node(3, 1), 100));
    staticOtyughs.add(new Otyugh(new Node(0, 2), 100));
    otyughsV2.setOtyughLocations(staticOtyughs);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    arrowsV2.setPlayerCurrentlyAt(new Node(3, 1));
    String shootResult = gameModel.playGameUsingTestCase(rows, cols,
            wrapping, arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(),
            arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput,
            shootDirection, inputDistance, nextMove, loopNumber);
    assertEquals("Chomp, chomp, chomp, you are eaten by an Otyugh!", shootResult);
  }

  @Test
  public void testIfPlayerDiesWhenEnterCaveWithInjuredOtyugh() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 50;
    int noofOtyughs = 5;
    boolean wrapping = false;
    boolean needStaticGame = true;

    String userInput = "S";
    String shootDirection = "S";
    int inputDistance = 2;
    String nextMove = "";
    int loopNumber = 2;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    treasureV2.setPlayerStartAt(new Node(3, 2));
    treasureV2.setPlayerEndAt(new Node(0, 1));

    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    String shootResult = gameModel.playGameUsingTestCase(rows, cols,
            wrapping, arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput, shootDirection,
            inputDistance, nextMove, loopNumber);
    assertEquals("You hear a great howl in the distance", shootResult);

    userInput = "M";
    shootDirection = "";
    inputDistance = 0;
    nextMove = "E";
    arrowsV2.setPlayerCurrentlyAt(new Node(4, 3));
    shootResult = gameModel.playGameUsingTestCase(rows, cols, wrapping, arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(),
            arrowsV2.getPlayerPossibleLocations(), arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput,
            shootDirection, inputDistance, nextMove, loopNumber);
    assertEquals("Chomp, chomp, chomp, you are eaten by an Otyugh!", shootResult);
  }

  @Test
  public void testIfPlayerSurvivesWhenEnterCaveWithInjuredOtyugh() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 50;
    int noofOtyughs = 5;
    boolean wrapping = false;
    boolean needStaticGame = true;

    String userInput = "S";
    String shootDirection = "S";
    int inputDistance = 2;
    String nextMove = "";
    int loopNumber = 1;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    treasureV2.setPlayerStartAt(new Node(3, 2));
    treasureV2.setPlayerEndAt(new Node(0, 1));

    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    String shootResult = gameModel.playGameUsingTestCase(rows, cols,
            wrapping, arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(),
            arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput, shootDirection,
            inputDistance, nextMove, loopNumber);
    assertEquals("You hear a great howl in the distance", shootResult);

    userInput = "M";
    shootDirection = "";
    inputDistance = 0;
    nextMove = "E";
    arrowsV2.setPlayerCurrentlyAt(new Node(4, 3));

    //Override with fixed otyugh locations
    ArrayList<Otyugh> staticOtyughs = new ArrayList<Otyugh>();
    staticOtyughs.add(new Otyugh(new Node(0, 1), 100));
    staticOtyughs.add(new Otyugh(new Node(4, 2), 50));
    arrowsV2.setOtyughLocations(staticOtyughs);

    shootResult = gameModel.playGameUsingTestCase(rows, cols, wrapping, arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(),
            arrowsV2.getPlayerPossibleLocations(), arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput,
            shootDirection, inputDistance, nextMove, loopNumber);
    assertEquals("You smell something terrible nearby", shootResult);
  }


  @Test
  public void testIfPlayerWins() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 50;
    int noofOtyughs = 5;
    boolean wrapping = false;
    boolean needStaticGame = true;

    String userInput = "";
    String shootDirection = "";
    int inputDistance = 2;
    String nextMove = "";
    int loopNumber = 1;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols,
            wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    treasureV2.setPlayerStartAt(new Node(3, 2));
    treasureV2.setPlayerEndAt(new Node(3, 2));

    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(),
            otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    arrowsV2.setPlayerCurrentlyAt(new Node(3, 2));
    String shootResult = gameModel.playGameUsingTestCase(rows,
            cols, wrapping, arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(),
            arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput,
            shootDirection, inputDistance, nextMove, loopNumber);
    assertEquals("You have successfully reached the destination.", shootResult);
  }


  @Test
  public void testPlayerAliveWhenNoOtyugh() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;

    DungeonLevel dungeonLevel = new DungeonLevel();
    ArrayList<Node> nodes = dungeonLevel.generateNodes(rows, cols);
    ArrayList<Path> potentialPaths = dungeonLevel.identifyPaths(rows, cols, nodes);
    TraverseNode traverseNode = new TraverseNode();
    DungeonAndLeftOverPath dungeonAndLeftOverPath
            = traverseNode.generateDungeonAndLeftOverPath(potentialPaths);
    ArrayList<Path> leftOverPaths = dungeonAndLeftOverPath.getLeftOverPaths();
    ArrayList<ArrayList<Path>> maze = dungeonAndLeftOverPath.getDungeon();
    traverseNode.shuffleLeftOverPaths(leftOverPaths);
    int interCount = traverseNode.validateInterconnectivityPathWithLeftOverPath(
            leftOverPaths, interConnectivity);
    //Applying interconnection
    ArrayList<Path> finalDungeon = maze.get(0);
    traverseNode.applyInterconnectivity(interCount, leftOverPaths, finalDungeon);
    GenerateDungeon generateDungeon = new GenerateDungeon();
    //Identify Edges which has only 2 paths & make them tunnel
    ArrayList<Node> tunnels = generateDungeon.identifyTunnel(finalDungeon, rows, cols);
    //Identify Caves for treasure allocation
    ArrayList<Node> caves = generateDungeon.identifyCaves(finalDungeon, tunnels);

    float percent = (float) percentageOfTreasure;
    int numberOfCavesTreasureAllocated = (int) (percent / 100 * caves.size());

    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;

    UpdateGame updateGame = new UpdateGame();
    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping,
            dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs,
            dungeon.getTunnels(), needStaticGame);
    int numberOfCavesArrowAllocated = 0;
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(numberOfCavesArrowAllocated,
            otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    //Shuffle caves, so random caves will be selected for allocating treasure
    Collections.shuffle(caves);

    treasureV2.setPlayerEndAt(new Node(1, 1));
    treasureV2.setPlayerStartAt(new Node(1, 1));

    //Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(false);
    //Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves = mazeObj.allocateTreasure(
            numberOfCavesTreasureAllocated, caves, treasureMaster, false);
    assertEquals(updateGame.isPlayerEatenByOtyugh(otyughsV2.getOtyughLocations(),
            arrowsV2.getPlayerCurrentlyAt(), 2), 4);
  }
}
