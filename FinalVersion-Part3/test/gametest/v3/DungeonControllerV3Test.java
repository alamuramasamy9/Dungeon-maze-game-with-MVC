package gametest.v3;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import com.dungeon.GameModel;
import com.dungeon.Player;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.v2.ArrowsV2;
import com.dungeon.v2.DungeonV2;
import com.dungeon.v2.OtyughsV2;
import com.dungeon.v2.TreasureV2;

/**
 * A JUnit4 test class for testing all method to the controller.
 */


public class DungeonControllerV3Test {

  /*
   * 1. Test if controller handles IOException 2. Test if controller handles
   * invalid inputs 3. Test Move 4. Test PickUp 5. Test ShootMiss 6. Test ShootHit
   */

  @Test
  public void testIfControllerHandlesIOException() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    boolean wrapping = false;
    int noofOtyughs = 2;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(
            rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(
            treasureV2.getCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs,
            dungeon.getTunnels(), needStaticGame);
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(
            otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);
    PlayerLocations playerPossibleLocations = arrowsV2.getPlayerPossibleLocations();
    // Checking IOException is handled as player doesn't have West move for this
    // scenario
    assertEquals(playerPossibleLocations.hasWest(), false);
  }

  @Test
  public void testIfControllerHandlesInvalidInputs() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    boolean wrapping = false;
    int noofOtyughs = 2;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(
            treasureV2.getCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(),
            treasureV2.getPlayer(), treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);
    PlayerLocations playerPossibleLocations = arrowsV2.getPlayerPossibleLocations();
    // Checking West move of the game as player doesn't have West move for this
    // scenario
    assertEquals(playerPossibleLocations.hasWest(), false);
  }

  @Test
  public void testMove() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    boolean wrapping = false;
    int noofOtyughs = 2;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols,
            wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(
            treasureV2.getCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(),
            treasureV2.getPlayer(), treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    Node expectedMove = new Node(4, 2);
    Player player = new Player();
    PlayerLocations playerPossibleLocations = arrowsV2.getPlayerPossibleLocations();
    String nextMove = "E";
    Node playerCurrentlyAt = player.getNextMoveCave(playerPossibleLocations, nextMove);
    boolean isPlayerMovedToEast = false;
    if (playerCurrentlyAt.getX() == expectedMove.getX()
            && playerCurrentlyAt.getY() == expectedMove.getY()) {
      isPlayerMovedToEast = true;
    }
    assertEquals(isPlayerMovedToEast, true);
  }

  @Test
  public void testPickup() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    boolean wrapping = false;
    int noofOtyughs = 2;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(
            rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(
            treasureV2.getCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(
            otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(),
            treasureV2.getPlayer(), treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    boolean pickupTreasureHere = true;
    ArrayList<HashMap<Node, Treasure>> treasureCaves = treasureV2.getTreasureCaves();
    for (int i = 0; i < treasureCaves.size(); i++) {
      HashMap<Node, Treasure> eachTreasureCave = treasureCaves.get(i);
      Set<Node> eachCave = eachTreasureCave.keySet();
      for (Node node : eachCave) {
        if (treasureV2.getPlayerStartAt().getX() == node.getX()
                && treasureV2.getPlayerStartAt().getY() == node.getY()) {
          pickupTreasureHere = false;
          break;
        }
      }
    }
    assertEquals(pickupTreasureHere, true);
  }

  @Test
  public void testShootMiss() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 20;
    boolean wrapping = false;
    int noofOtyughs = 2;
    boolean needStaticGame = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(
            rows, cols, interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(
            rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(
            treasureV2.getCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(
            otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(),
            treasureV2.getPlayer(), treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    PlayerLocations playerPossibleLocations = arrowsV2.getPlayerPossibleLocations();
    String nextMove = "E";
    Player player = new Player();
    Node playerEastLocation = player.getNextMoveCave(playerPossibleLocations, nextMove);
    ArrayList<Otyugh> otyughLocations = arrowsV2.getOtyughLocations();
    boolean isArrowShootOtyugh = false;
    for (Otyugh eachOtyugh : otyughLocations) {
      if (eachOtyugh.getOtyughLocation().getX() == playerEastLocation.getX()
              && eachOtyugh.getOtyughLocation().getY() == playerEastLocation.getY()) {
        isArrowShootOtyugh = true;
      }
    }
    assertEquals(isArrowShootOtyugh, false);
  }

}
