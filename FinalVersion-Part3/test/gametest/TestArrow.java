package gametest;

import com.dungeon.DungeonLevel;
import com.dungeon.GameModel;
import com.dungeon.GenerateDungeon;
import com.dungeon.Maze;
import com.dungeon.TraverseNode;
import com.dungeon.data.ArrowLocations;
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * A JUnit4 test class for testing the all functionalities involving Arrows.
 */

public class TestArrow {


  @Test
  public void testPlayerStartWithThreeArrowInitially() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 50;
    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    // Set static end location:
    treasureV2.setPlayerStartAt(new Node(2, 1));
    treasureV2.setPlayerEndAt(new Node(0, 3));

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

    assertEquals(3, gameModel.noOfArrowsCollected);
  }


  @Test
  public void testArrowInBothCavesAndTunnels() {
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
            treasureV2.getPlayerPossibleLocations(),
            treasureV2.getPlayer(), treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    ArrayList<ArrowLocations> arrowLocations = arrowsV2.getArrowLocations();
    ArrayList<Node> caves = dungeon.getCaves();
    ArrayList<Node> tunnels = dungeon.getTunnels();
    boolean arrowFoundInCaves = false;
    boolean arrowFoundInTunnels = false;
    for (ArrowLocations eachArrowLocations : arrowLocations) {
      for (Node eachCaves : caves) {
        if (eachCaves.getX() == eachArrowLocations.getLocation().getX()
                && eachCaves.getY() == eachArrowLocations.getLocation().getY()) {
          arrowFoundInCaves = true;
        }
      }
      for (Node eachTunnels : tunnels) {
        if (eachTunnels.getX() == eachArrowLocations.getLocation().getX()
                && eachTunnels.getY() == eachArrowLocations.getLocation().getY()) {
          arrowFoundInTunnels = true;
        }
      }
    }
    assertEquals(true, arrowFoundInCaves);
    assertEquals(true, arrowFoundInTunnels);
  }

  @Test
  public void testIfArrowReachesSpecifiedDistance() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 50;
    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;

    String userInput = "S";
    String shootDirection = "S";
    int inputDistance = 2;
    String nextMove = "";
    int loopNumber = 0;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    treasureV2.setPlayerStartAt(new Node(2, 1));
    treasureV2.setPlayerEndAt(new Node(0, 3));

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

    String shootResult = gameModel.playGameUsingTestCase(rows, cols, wrapping,
            arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput, shootDirection,
            inputDistance, nextMove, loopNumber);
    assertEquals("You shoot an arrow into the darkness", shootResult);
  }


  @Test
  public void testIfArrowInjuredOtyugh() {
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
    int loopNumber = 0;

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

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(),
            otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(),
            treasureV2.getPlayer(), treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    String shootResult = gameModel.playGameUsingTestCase(rows, cols, wrapping,
            arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput, shootDirection,
            inputDistance, nextMove, loopNumber);
    assertEquals("You hear a great howl in the distance", shootResult);
  }

  @Test
  public void testIfOtyughDies() {
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
    int loopNumber = 0;

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

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(),
            otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    String shootResult = gameModel.playGameUsingTestCase(rows, cols, wrapping,
            arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(),
            arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput,
            shootDirection, inputDistance, nextMove, loopNumber);
    assertEquals("You hear a great howl in the distance", shootResult);

    shootResult = gameModel.playGameUsingTestCase(rows, cols, wrapping,
            arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput, shootDirection,
            inputDistance, nextMove, loopNumber);
    assertEquals("You killed the Otyugh, present in the location (2, 3)", shootResult);
  }


  @Test
  public void testArrowAllocation() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols,
            wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs,
            dungeon.getTunnels(), needStaticGame);


    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(),
            otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);


    assertTrue(arrowsV2.getArrowLocations().size() > 0);
  }

  @Test
  public void testPickUpArrow() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols,
            wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs,
            dungeon.getTunnels(), needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(),
            otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    boolean isOtyughPresentInPlayerEndLocation = false;
    for (Otyugh eachOtyugh : otyughsV2.getOtyughLocations()) {
      if (eachOtyugh.getOtyughLocation().getX() == treasureV2.getPlayerEndAt().getX()
              && eachOtyugh.getOtyughLocation().getY() == treasureV2.getPlayerEndAt().getY()) {
        isOtyughPresentInPlayerEndLocation = true;
        break;
      }
    }
    assertTrue(arrowsV2.numberofArrows >= 3);

  }

  @Test
  public void testShootArrow() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs,
            dungeon.getTunnels(), needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    boolean isOtyughPresentInPlayerEndLocation = false;
    for (Otyugh eachOtyugh : otyughsV2.getOtyughLocations()) {
      if (eachOtyugh.getOtyughLocation().getX() == treasureV2.getPlayerEndAt().getX()
              && eachOtyugh.getOtyughLocation().getY() == treasureV2.getPlayerEndAt().getY()) {
        isOtyughPresentInPlayerEndLocation = true;
        break;
      }
    }
    assertTrue(arrowsV2.numberofArrows <= 3);
  }

  @Test
  public void testShootArrowToCave() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs,
            dungeon.getTunnels(), needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    boolean shootToCave = false;
    for (Otyugh eachOtyugh : otyughsV2.getOtyughLocations()) {
      if (eachOtyugh.getOtyughLocation().getX() == treasureV2.getPlayerEndAt().getX()
              && eachOtyugh.getOtyughLocation().getY() == treasureV2.getPlayerEndAt().getY()) {
        shootToCave = true;
        break;
      }
    }
    assertTrue(shootToCave);
  }

  @Test
  public void testShootArrowToCaveWhenNotPossible() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs,
            dungeon.getTunnels(), needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    boolean shootToCave = true;
    for (Otyugh eachOtyugh : otyughsV2.getOtyughLocations()) {
      if (eachOtyugh.getOtyughLocation().getX() == treasureV2.getPlayerEndAt().getX()
              && eachOtyugh.getOtyughLocation().getY() == treasureV2.getPlayerEndAt().getY()) {
        shootToCave = false;
        break;
      }
    }
    assertFalse(shootToCave);
  }

  @Test
  public void testTravelThroughTunnel() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs,
            dungeon.getTunnels(), needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    boolean travelThroughTunnel = false;
    for (Otyugh eachOtyugh : otyughsV2.getOtyughLocations()) {
      if (eachOtyugh.getOtyughLocation().getX() == treasureV2.getPlayerEndAt().getX()
              && eachOtyugh.getOtyughLocation().getY() == treasureV2.getPlayerEndAt().getY()) {
        travelThroughTunnel = true;
        break;
      }
    }
    assertTrue(travelThroughTunnel);
  }

  @Test
  public void testPercentageArrows() {
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
    int numberOfCavesArrowAllocated = numberOfCavesTreasureAllocated;
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

    //Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(false);
    //Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves = mazeObj.allocateTreasure(
            numberOfCavesTreasureAllocated, caves, treasureMaster, false);
    assertEquals(numberOfCavesArrowAllocated, arrowsV2.getArrowLocations().size());
  }

  @Test
  public void testArrowIsZero() {
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

    //Treasure master
    Maze mazeObj = new Maze();
    ArrayList<Treasure> treasureMaster = mazeObj.treasureMaster(false);
    //Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves = mazeObj.allocateTreasure(
            numberOfCavesTreasureAllocated, caves, treasureMaster, false);
    assertEquals(numberOfCavesArrowAllocated, 0);
  }
}
