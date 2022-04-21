package com.dungeon;

import com.dungeon.v2.ArrowsV2;
import com.dungeon.v2.DungeonV2;
import com.dungeon.v2.OtyughsV2;
import com.dungeon.v2.TreasureV2;

import java.io.IOException;
import java.util.Scanner;

/**
 * Represents a Controller for Dungeon: handle the functions by executing them using the model.
 * acts as an intermediate between user and model.
 */

public class GameController implements ControllerInterface {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Contructor of the game controller class.
   *
   * @param in  input
   * @param out output
   */

  public GameController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);

  }

  /**
   * Execute a single game of dungeon given a game Model. When the game is over,
   * the playGame method ends.
   * All the required methods are called.
   *
   * @param rows                 number of rows passed in command line
   * @param cols                 number of columns passed in command line
   * @param interConnectivity    interconnectivity passed in command line
   * @param percentageOfTreasure percentage of treasure passed in command line
   * @param wrapping             wrapping boolean passed in command line
   * @param noofOtyughs          number of otyughs passed in command line
   * @param needStaticGame       true while running JUnit Test cases for static game
   */

  @Override
  public void playGame(int rows, int cols, int interConnectivity,
                       int percentageOfTreasure, boolean wrapping,
                       int noofOtyughs, boolean needStaticGame) {

    GameModel gameModel = new GameModel();

    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);

    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols,
            wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);

    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);

    ArrowsV2 arrowsV2 = gameModel.allocateArrows(dungeon.getNumberOfCavesTreasureAllocated(),
            otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    gameModel.playGame(rows, cols, wrapping, arrowsV2.getProject2(),
            arrowsV2.getOtyughLocations(),
            arrowsV2.getPlayerPossibleLocations(), arrowsV2.getPlayer(),
            arrowsV2.getFinalDungeon(),
            arrowsV2.getTreasureCaves(), arrowsV2.getPlayerCurrentlyAt(),
            arrowsV2.getPlayerEndAt(),
            arrowsV2.getCaves(), arrowsV2.getArrowLocations(),
            arrowsV2.getInputScanner(), needStaticGame);

    try {
      out.append("Game Over\n");
    } catch (IOException ioe) {
      scan.next();
      throw new IllegalStateException("Append failed", ioe);
    }
  }

}
