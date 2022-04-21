package gametest.v3;

import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.dungeon.GameModel;
import com.dungeon.Player;
import com.dungeon.data.Node;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.v2.ArrowsV2;
import com.dungeon.v2.DungeonV2;
import com.dungeon.v2.OtyughsV2;
import com.dungeon.v2.TreasureV2;
import com.dungeon.v3.control.DungeonControllerV3;

/**
 * A JUnit4 test class for testing all method related to the chnages in the model.
 */

public class DungeonModelV3Test {


  @Test
  public void testIfPitIsNotInStartLocation() {
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
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(
            otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    for (HashMap<Node, Treasure> eachTreasureCaves : treasureV2.getTreasureCaves()) {
      for (Node eachTreasureNode : eachTreasureCaves.keySet()) {
        Treasure treasureDetails = eachTreasureCaves.get(eachTreasureNode);
        System.out.println("The allocated treasure caves are : (" +
                eachTreasureNode.getX() + "," + eachTreasureNode.getY()
                + "), Treasure details : Diamond - " + treasureDetails.hasDiamond()
                + ", Ruby - " + treasureDetails.hasRuby() + ", Sapphire - "
                + treasureDetails.hasSapphire());
      }
    }

    DungeonControllerV3 dungeonControllerV3 = new DungeonControllerV3();
    Node pitLocation = dungeonControllerV3.generatePitLocation(dungeon, arrowsV2);

    boolean isPitFoundInStartLocation = false;
    if (pitLocation.getX() == treasureV2.getPlayerStartAt().getX() && pitLocation.getY()
            == treasureV2.getPlayerStartAt().getY()) {
      isPitFoundInStartLocation = true;
    }
    assertEquals(isPitFoundInStartLocation, false);
  }

  @Test
  public void testIfPitIsNotInEndLocation() {
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
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows,
            cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    for (HashMap<Node, Treasure> eachTreasureCaves : treasureV2.getTreasureCaves()) {
      for (Node eachTreasureNode : eachTreasureCaves.keySet()) {
        Treasure treasureDetails = eachTreasureCaves.get(eachTreasureNode);
        System.out.println("The allocated treasure caves are : (" + eachTreasureNode.getX()
                + "," + eachTreasureNode.getY() + "), Treasure details : Diamond - "
                + treasureDetails.hasDiamond() + ", Ruby - " + treasureDetails.hasRuby()
                + ", Sapphire - " + treasureDetails.hasSapphire());
      }
    }

    DungeonControllerV3 dungeonControllerV3 = new DungeonControllerV3();
    Node pitLocation = dungeonControllerV3.generatePitLocation(dungeon, arrowsV2);

    boolean isPitFoundInEndLocation = false;
    if (pitLocation.getX() == treasureV2.getPlayerEndAt().getX() && pitLocation.getY()
            == treasureV2.getPlayerEndAt().getY()) {
      isPitFoundInEndLocation = true;
    }
    assertEquals(isPitFoundInEndLocation, false);
  }

  @Test
  public void testIfPlayerFallsIntoPitAndDies() {
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
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(),
            treasureV2.getPlayer(), treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    Node pitLocation = new Node(4, 2);
    Player player = new Player();
    PlayerLocations playerPossibleLocations = arrowsV2.getPlayerPossibleLocations();
    String nextMove = "E";
    Node playerCurrentlyAt = player.getNextMoveCave(playerPossibleLocations, nextMove);
    boolean isPlayerFallInPit = false;
    if (playerCurrentlyAt.getX() == pitLocation.getX()
            && playerCurrentlyAt.getY() == pitLocation.getY()) {
      isPlayerFallInPit = true;
    }
    assertEquals(isPlayerFallInPit, true);
  }

  @Test
  public void testIfThiefStealsTreasure() {
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
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    Node thiefLocation = new Node(4, 2);
    Player player = new Player();
    PlayerLocations playerPossibleLocations = arrowsV2.getPlayerPossibleLocations();
    String nextMove = "E";
    Node playerCurrentlyAt = player.getNextMoveCave(playerPossibleLocations, nextMove);
    boolean isTreasureStolenByThief = false;
    if (playerCurrentlyAt.getX() == thiefLocation.getX()
            && playerCurrentlyAt.getY() == thiefLocation.getY()) {
      isTreasureStolenByThief = true;
    }
    assertEquals(isTreasureStolenByThief, true);
  }
}
