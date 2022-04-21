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

import static org.junit.Assert.assertNotNull;


/**
 * A JUnit4 test class for testing wrapping and not wrapping.
 */

public class WrappingTest {

  @Test
  public void testPlayerDirectionWithWrapping() {
    int rows = 5;
    int cols = 8;
    int interConnectivity = 5;
    int percentageOfTreasure = 85;
    boolean wrapping = true;

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

    //Shuffle the final dungeon, so random start point will be picked as first index of the array
    mazeObj.generateRandomStart(finalDungeon);
    //Pick first path start edge as player start location
    Node playerStartAt = finalDungeon.get(0).getStart();
    Player player = new Player();
    TreasureCollected treasureCollected
            = player.checkPlayerCaveHasTreasure(treasureCaves, playerStartAt);
    PlayerLocations playerPossibleLocations = player.getPossibleMoves(
            rows, cols, finalDungeon, playerStartAt, wrapping);

    assertNotNull(playerPossibleLocations.getEast());
    assertNotNull(playerPossibleLocations.getNorth());
    assertNotNull(playerPossibleLocations.getSouth());
    assertNotNull(playerPossibleLocations.getWest());
  }

  @Test
  public void testPlayerDirectionWithoutWrapping() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 85;
    boolean wrapping = false;

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

    //Shuffle the final dungeon, so random start point will be picked as first index of the array
    mazeObj.generateRandomStart(finalDungeon);
    //Pick first path start edge as player start location
    Node playerStartAt = finalDungeon.get(0).getStart();
    Player player = new Player();
    TreasureCollected treasureCollected = player.checkPlayerCaveHasTreasure(
            treasureCaves, playerStartAt);
    PlayerLocations playerPossibleLocations = player.getPossibleMoves(
            rows, cols, finalDungeon, playerStartAt, wrapping);

    assertNotNull(playerPossibleLocations.getEast());
    assertNotNull(playerPossibleLocations.getNorth());
    assertNotNull(playerPossibleLocations.getSouth());
    assertNotNull(playerPossibleLocations.getWest());
  }
}
