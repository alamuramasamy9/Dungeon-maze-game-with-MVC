package gametest;

import com.dungeon.DungeonLevel;
import com.dungeon.GenerateDungeon;
import com.dungeon.Maze;
import com.dungeon.Player;
import com.dungeon.TraverseNode;
import com.dungeon.data.DungeonAndLeftOverPath;
import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.data.TreasureCollected;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * A JUnit4 test class for testing all method related to Player.
 */

public class PlayerTest {

  @Test
  public void testPlayerPosition() {
    int rows = 9;
    int cols = 10;
    int interConnectivity = 6;
    int percentageOfTreasure = 50;

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
    //Shuffle caves, so random caves will be selected for allocating treasure
    Collections.shuffle(caves);

    //Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(false);
    //Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves = mazeObj.allocateTreasure(
            numberOfCavesTreasureAllocated, caves, treasureMaster, false);

    mazeObj.generateRandomStart(finalDungeon);
    //Pick first path start edge as player start location
    Node playerStartAt = finalDungeon.get(0).getStart();
    assertNotNull(playerStartAt.getX());
    assertNotNull(playerStartAt.getY());
  }

  @Test
  public void testPlayerDescriptionAndLocation() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    boolean wrapping = false;

    DungeonLevel dungeonLevel = new DungeonLevel();
    ArrayList<Node> nodes = dungeonLevel.generateNodes(rows, cols);

    // Identify Potential Paths
    ArrayList<Path> potentialPaths = dungeonLevel.identifyPaths(rows, cols, nodes);

    //Shuffle potential paths - So random order will be generated
    int[] tmp = new int[]{12, 14, 21, 25, 30, 35, 37, 10, 11, 23, 13, 28, 31, 32, 36, 18, 17, 9,
        15, 16, 19, 20, 6, 8, 34, 24, 29, 5, 4, 22, 26, 27, 33, 2, 3, 7, 1, 0};
    ArrayList<Path> randomPotentialPath = new ArrayList<Path>();
    for (int i = 0; i < tmp.length; i++) {
      randomPotentialPath.add(potentialPaths.get(tmp[i]));
    }
    //Now actual potential path replaced with static shuffled potential path
    potentialPaths = randomPotentialPath;

    // Generate Dungeon
    TraverseNode traverseNode = new TraverseNode();
    DungeonAndLeftOverPath dungeonAndLeftOverPath
            = traverseNode.generateDungeonAndLeftOverPath(potentialPaths);

    ArrayList<ArrayList<Path>> maze = dungeonAndLeftOverPath.getDungeon();
    ArrayList<Path> leftOverPaths = dungeonAndLeftOverPath.getLeftOverPaths();

    //Shuffle leftover paths - So random order will be generated to pick and start
    //InterConnection
    tmp = new int[]{13, 14, 8, 6, 10, 12, 11, 4, 7, 2, 1, 3, 9, 5, 0};
    ArrayList<Path> staticLeftOverPath = new ArrayList<Path>();
    for (int i = 0; i < tmp.length; i++) {
      staticLeftOverPath.add(leftOverPaths.get(tmp[i]));
    }
    //Replace actual left over path with static shuffled left over path
    leftOverPaths = staticLeftOverPath;

    int interCount = traverseNode.validateInterconnectivityPathWithLeftOverPath(
            leftOverPaths, interConnectivity);

    // Applying interconnection
    ArrayList<Path> finalDungeon = maze.get(0);
    ArrayList<Path> interConnectedPaths = traverseNode.applyInterconnectivity(
            interCount, leftOverPaths, finalDungeon);

    GenerateDungeon generateDungeon = new GenerateDungeon();
    // Identify Edges which has only 2 paths & make them tunnel
    ArrayList<Node> tunnels = generateDungeon.identifyTunnel(finalDungeon, rows, cols);

    // Identify Caves for treasure allocation
    ArrayList<Node> caves = generateDungeon.identifyCaves(finalDungeon, tunnels);

    float percent = (float) percentageOfTreasure;
    int numberOfCavesTreasureAllocated = (int) (percent / 100 * caves.size());
    //Shuffle caves, so random caves will be selected for allocating treasure
    tmp = new int[]{14, 13, 8, 6, 10, 12, 11, 4, 7, 2, 1, 3, 9, 5, 0};
    ArrayList<Node> staticCaves = new ArrayList<Node>();
    for (int i = 0; i < tmp.length; i++) {
      staticCaves.add(caves.get(tmp[i]));
    }
    //Replace actual caves with static shuffled caves for fixed result
    caves = staticCaves;

    // Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(true);

    // Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves = mazeObj.allocateTreasure(
            numberOfCavesTreasureAllocated, caves, treasureMaster, true);

    tmp = new int[]{13, 14, 21, 25, 30, 12, 11, 23, 10, 28, 31, 32, 18, 17, 9, 15, 16, 19, 20,
        6, 8, 34, 24, 29, 5, 4, 22, 26, 27, 33, 2, 3, 7, 1, 0};
    ArrayList<Path> staticShuffledDungeon = new ArrayList<Path>();
    for (int i = 0; i < tmp.length; i++) {
      staticShuffledDungeon.add(finalDungeon.get(tmp[i]));
    }
    finalDungeon = staticShuffledDungeon;

    // Pick first path start node as player start location
    Node playerStartAt = finalDungeon.get(0).getStart();
    Node playerCurrentlyAt = finalDungeon.get(0).getStart();
    Node playerEndAt = mazeObj.generateRandomEnd(finalDungeon);
    int getPlayerEndLocationIndex = mazeObj.getplayerEndLocationIndex();

    Player player = new Player();
    PlayerLocations playerPossibleLocations = player.getPossibleMoves(
            rows, cols, finalDungeon, playerCurrentlyAt, wrapping);

    TreasureCollected treasureCollected = player.checkPlayerCaveHasTreasure(
            treasureCaves, playerStartAt);
    Treasure treasureDetailsOfPlayerCurrentCave = player.getTreasureDetailsOfCave(
            treasureCaves, playerStartAt);

    String playerTreasureDetails = player.getPlayerTreasureDetails(treasureCollected);
    String playerLocationDetails
            = player.getPlayerLocationDetails(playerCurrentlyAt, playerPossibleLocations);

    assertEquals("Treasure collected so far : (Diamond - 0, Ruby - 1, Sapphire - 1)",
            playerTreasureDetails);
    assertEquals("The player is currently at (4, 2) and possible moves are (North[4, 1]:false"
                    + " || East[5, 2]:true || South[4, 3]:true || West[3, 2]:true)",
            playerLocationDetails);
  }

  @Test
  public void testPlayerStart() {
    int rows = 9;
    int cols = 10;
    int interConnectivity = 6;
    int percentageOfTreasure = 50;

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
    //Shuffle caves, so random caves will be selected for allocating treasure
    Collections.shuffle(caves);

    //Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(false);
    //Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves = mazeObj.allocateTreasure(
            numberOfCavesTreasureAllocated, caves, treasureMaster, false);

    mazeObj.generateRandomStart(finalDungeon);
    //Pick first path start edge as player start location
    Node playerStartAt = finalDungeon.get(0).getStart();
    assertNotNull(playerStartAt.getX());
    assertNotNull(playerStartAt.getY());
  }

  @Test
  public void testPlayerEnd() {
    int rows = 9;
    int cols = 10;
    int interConnectivity = 6;
    int percentageOfTreasure = 50;

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
    //Shuffle caves, so random caves will be selected for allocating treasure
    Collections.shuffle(caves);

    //Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(false);
    //Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves = mazeObj.allocateTreasure(
            numberOfCavesTreasureAllocated, caves, treasureMaster, false);

    mazeObj.generateRandomStart(finalDungeon);
    //Pick first path start edge as player start location
    Node playerStartAt = finalDungeon.get(0).getStart();
    Node playerEndAt = mazeObj.generateRandomEnd(finalDungeon);

    assertNotNull(playerEndAt.getX());
    assertNotNull(playerEndAt.getY());
  }


}
