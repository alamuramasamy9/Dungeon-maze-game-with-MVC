package gametest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.dungeon.GameModel;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;
import com.dungeon.v2.ArrowsV2;
import com.dungeon.v2.DungeonV2;
import com.dungeon.v2.OtyughsV2;
import com.dungeon.v2.TreasureV2;

import java.util.ArrayList;

/**
 * A JUnit4 test class for testing the all functionalities involving Otyughs.
 */

public class TestOtyugh {


  @Test
  public void testOtyughAllocation() {
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
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);

    assertEquals(otyughsV2.getOtyughLocations().size(), noofOtyughs);
  }

  @Test
  public void testNoOtyughInStartLocation() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 50;
    int noofOtyughs = 4;
    boolean wrapping = false;
    boolean needStaticGame = true;
    boolean val = true;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);

    assertNotEquals(otyughsV2.getOtyughLocations().size(), 0);
    for (Otyugh eachOtyugh : otyughsV2.getOtyughLocations()) {
      if (eachOtyugh.getOtyughLocation().getX() == treasureV2.getPlayerStartAt().getX()
              && eachOtyugh.getOtyughLocation().getY() == treasureV2.getPlayerStartAt().getY()) {
        val = true;
      }
    }
    assertTrue(val);
  }

  @Test
  public void testOtyughPresentInEndLocation() {
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
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);

    boolean isOtyughPresentInPlayerEndLocation = false;
    for (Otyugh eachOtyugh : otyughsV2.getOtyughLocations()) {
      if (eachOtyugh.getOtyughLocation().getX() == treasureV2.getPlayerEndAt().getX()
              && eachOtyugh.getOtyughLocation().getY() == treasureV2.getPlayerEndAt().getY()) {
        isOtyughPresentInPlayerEndLocation = true;
        break;
      }
    }
    assertTrue(isOtyughPresentInPlayerEndLocation);
  }

  @Test
  public void testNoOtyughInTunnel() {
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
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
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

    //System.out.println(dungeon.getCaves());
    int numberOfOtyughsInTunnels = 0;
    for (Otyugh eachOtyugh : otyughsV2.getOtyughLocations()) {
      for (Node eachTunnel : dungeon.getTunnels()) {
        if (eachOtyugh.getOtyughLocation().getX() == eachTunnel.getX()
                && eachOtyugh.getOtyughLocation().getY() == eachTunnel.getY()) {
          break;
        }
      }
    }
    assertEquals(0, numberOfOtyughsInTunnels);
  }

  @Test
  public void testTotalNoOfOtyughByCommandline() {
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
            noofOtyughs, true);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols,
            wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);

    assertEquals(noofOtyughs, otyughsV2.getOtyughLocations().size());
  }

  @Test
  public void testSlightSmellWithOneOtyughTwoPositionsAway() {
    int rows = 4;
    int cols = 6;
    int interConnectivity = 8;
    int percentageOfTreasure = 50;
    int noofOtyughs = 1;
    boolean wrapping = false;
    boolean needStaticGame = true;

    String userInput = "";
    String shootDirection = "";
    int inputDistance = 2;
    String nextMove = "";
    int loopNumber = 1;

    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping,
            dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    treasureV2.setPlayerStartAt(new Node(3, 2));
    treasureV2.setPlayerEndAt(new Node(0, 2));

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

    //Override with fixed otyugh locations
    ArrayList<Otyugh> staticOtyughs = new ArrayList<Otyugh>();
    staticOtyughs.add(new Otyugh(new Node(4, 3), 100));
    //staticOtyughs.add(new Otyugh(new Node(5,2), 100));
    arrowsV2.setOtyughLocations(staticOtyughs);

    String shootResult = gameModel.playGameUsingTestCase(rows, cols,
            wrapping, arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput,
            shootDirection, inputDistance, nextMove, loopNumber);
    assertEquals("You smell something little nearby", shootResult);
  }


  @Test
  public void testTerribleSmellWithOtyughOnePositionAway() {
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
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping,
            dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    // Set static end location:
    treasureV2.setPlayerStartAt(new Node(2, 1));
    treasureV2.setPlayerEndAt(new Node(1, 0));

    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(),
            needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(otyughsV2.getNoofOtyughs(),
            otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(),
            needStaticGame);

    String smellStatus = gameModel.playGameAndGetSmellStatus(rows, cols,
            wrapping, arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame);
    assertEquals("You smell something terrible nearby", smellStatus);
  }

  @Test
  public void testTerribleSmellWithMultipleOtyughTwoPositionAway() {
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
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping,
            dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(), dungeon.getCaves(),
            dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    treasureV2.setPlayerStartAt(new Node(3, 2));
    treasureV2.setPlayerEndAt(new Node(0, 2));

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

    //Override with fixed otyugh locations
    ArrayList<Otyugh> staticOtyughs = new ArrayList<Otyugh>();
    staticOtyughs.add(new Otyugh(new Node(2, 3), 100));
    staticOtyughs.add(new Otyugh(new Node(4, 3), 100));
    arrowsV2.setOtyughLocations(staticOtyughs);

    String shootResult = gameModel.playGameUsingTestCase(rows, cols, wrapping,
            arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(), arrowsV2.getPlayerPossibleLocations(),
            arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(), arrowsV2.getTreasureCaves(),
            arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(), arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame, userInput, shootDirection,
            inputDistance, nextMove, loopNumber);
    assertEquals("You smell something terrible nearby", shootResult);
  }

}

