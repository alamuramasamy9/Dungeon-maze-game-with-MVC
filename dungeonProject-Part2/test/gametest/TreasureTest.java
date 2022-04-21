package gametest;

import com.dungeon.DungeonLevel;
import com.dungeon.GenerateDungeon;
import com.dungeon.Maze;
import com.dungeon.Player;
import com.dungeon.TraverseNode;
import com.dungeon.data.DungeonAndLeftOverPath;
import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.Treasure;
import com.dungeon.data.TreasureCollected;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A JUnit4 test class for testing all method related to Treasure.
 */

public class TreasureTest {
  @Test
  public void testPercentageTreasure() {
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
    assertEquals(numberOfCavesTreasureAllocated, treasureCaves.size());
  }

  @Test
  public void testTreasureOfPlayer() {
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

    //Shuffle the final dungeon, so random start point will be picked as first index of the array
    mazeObj.generateRandomStart(finalDungeon);
    //Pick first path start edge as player start location
    Node playerStartAt = finalDungeon.get(0).getStart();
    Player player = new Player();
    TreasureCollected treasureCollected = player.checkPlayerCaveHasTreasure(
            treasureCaves, playerStartAt);
    assertNotNull(treasureCollected.getDiamondCount());
    assertNotNull(treasureCollected.getRubyCount());
    assertNotNull(treasureCollected.getSapphireCount());
  }

  @Test
  public void testTreasureInCave() {
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
    assertNotEquals(0,treasureCaves.size());
  }

  @Test
  public void testNullTreasure() {
    int rows = 9;
    int cols = 10;
    int interConnectivity = 6;
    int percentageOfTreasure = 0;

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
    assertEquals(0, treasureCaves.size());
  }

}
