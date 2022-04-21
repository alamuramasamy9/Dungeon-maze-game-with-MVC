package com.dungeon;

/**
 * Represents a Controller for Dungeon: handle the functions by executing them using the model.
 * acts as an intermediate between user and model.
 */

public interface ControllerInterface {

  /**
   * Execute a single game of dungeon given a game Model. When the game is over,
   * the playGame method ends.
   *
   * @param rows number of rows passed in command line
   * @param cols number of columns passed in command line
   * @param interConnectivity interconnectivity passed in command line
   * @param percentageOfTreasure percentage of treasure passed in command line
   * @param wrapping wrapping boolean passed in command line
   * @param noofOtyughs number of otyughs passed in command line
   * @param needStaticGame true while running JUnit Test cases for static game
   */

  void playGame(int rows, int cols, int interConnectivity, int percentageOfTreasure,
                boolean wrapping, int noofOtyughs, boolean needStaticGame);


}
