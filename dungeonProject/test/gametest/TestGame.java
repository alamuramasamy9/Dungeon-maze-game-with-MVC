package gametest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.dungeon.DungeonLevel;
import com.dungeon.GenerateDungeon;
import com.dungeon.Maze;
import com.dungeon.TraverseNode;
import com.dungeon.data.DungeonAndLeftOverPath;
import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.Treasure;

/**
 * A JUnit4 test class for testing the all functionalities involving
 * inputs, nodes, paths and interconnectivity and caves.
 */

public class TestGame {


  @Test
  public void testInputs() {
    int rows = 6;
    int cols = 6;
    DungeonLevel dungeonLevel = new DungeonLevel();
    ArrayList<Node> nodes = dungeonLevel.generateNodes(rows, cols);
    assertEquals(36, nodes.size());
  }

  @Test
  public void testNodes() {
    int rows = 4;
    int cols = 6;
    DungeonLevel dungeonLevel = new DungeonLevel();
    ArrayList<Node> nodes = dungeonLevel.generateNodes(rows, cols);
    assertEquals(1, nodes.get(13).getX());
    assertEquals(2, nodes.get(13).getY());
  }

  @Test
  public void testPotentialPaths() {
    int rows = 8;
    int cols = 10;
    DungeonLevel dungeonLevel = new DungeonLevel();
    ArrayList<Node> nodes = dungeonLevel.generateNodes(rows, cols);
    ArrayList<Path> potentialPaths = dungeonLevel.identifyPaths(rows, cols, nodes);
    assertEquals(142, potentialPaths.size());
  }

  @Test
  public void testPathsPicked() {
    int rows = 4;
    int cols = 6;
    DungeonLevel dungeonLevel = new DungeonLevel();
    ArrayList<Node> nodes = dungeonLevel.generateNodes(rows, cols);
    ArrayList<Path> potentialPaths = dungeonLevel.identifyPaths(rows, cols, nodes);
    Path thirdPath = potentialPaths.get(2);//B-C
    Node startNode = thirdPath.getStart();
    Node endNode = thirdPath.getEnd();

    assertEquals(1, startNode.getX());
    assertEquals(0, startNode.getY());
    assertEquals(2, endNode.getX());
    assertEquals(0, endNode.getY());
  }

  @Test
  public void testLeftOverPath() {
    int rows = 7;
    int cols = 9;
    DungeonLevel dungeonLevel = new DungeonLevel();
    ArrayList<Node> nodes = dungeonLevel.generateNodes(rows, cols);
    ArrayList<Path> potentialPaths = dungeonLevel.identifyPaths(rows, cols, nodes);
    TraverseNode traverseNode = new TraverseNode();
    DungeonAndLeftOverPath dungeonAndLeftOverPath
            = traverseNode.generateDungeonAndLeftOverPath(potentialPaths);
    ArrayList<Path> leftOverEdges = dungeonAndLeftOverPath.getLeftOverPaths();
    assertNotEquals(0, leftOverEdges.size());
  }

  @Test
  public void testTunnels() {
    int rows = 9;
    int cols = 10;
    int interConnectivity = 6;
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
    assertNotEquals(0, tunnels.size());
  }

  @Test
  public void testCaves() {
    int rows = 9;
    int cols = 10;
    int interConnectivity = 6;
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
    assertNotEquals(0, caves.size());
  }


  @Test
  public void testRandomPath() {
    int rows = 4;
    int cols = 6;

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
    Path randomPath = randomPotentialPath.get(36);
    assertEquals(0, randomPath.getStart().getX());
    assertEquals(0, randomPath.getStart().getY());
    assertEquals(0, randomPath.getEnd().getX());
    assertEquals(1, randomPath.getEnd().getY());
  }

  @Test
  public void testMinimumDistance() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;

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

    // Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(false);
    // Shuffle the final dungeon, so random start point will be picked as first
    // index of the array
    mazeObj.generateRandomStart(finalDungeon);

    // Pick first path start node as player start location
    Node playerStartAt = finalDungeon.get(0).getStart();
    Node playerCurrentlyAt = finalDungeon.get(0).getStart();
    Node playerEndAt = mazeObj.generateRandomEnd(finalDungeon);
    int getPlayerEndLocationIndex = mazeObj.getplayerEndLocationIndex();
    boolean result = false;
    if ((getPlayerEndLocationIndex + 1) >= 5) {
      result = true;
    }
    assertTrue(result);
  }

  @Test
  public void testEveryCaveReachable() {

    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;

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
    ArrayList<Path> interConnectedPaths = traverseNode.applyInterconnectivity(interCount,
            leftOverPaths, finalDungeon);

    GenerateDungeon generateDungeon = new GenerateDungeon();
    // Identify Edges which has only 2 paths & make them tunnel
    ArrayList<Node> tunnels = generateDungeon.identifyTunnel(finalDungeon, rows, cols);

    // Identify Caves for treasure allocation
    ArrayList<Node> caves = generateDungeon.identifyCaves(finalDungeon, tunnels);
    assertEquals(17, caves.size());
  }
}

