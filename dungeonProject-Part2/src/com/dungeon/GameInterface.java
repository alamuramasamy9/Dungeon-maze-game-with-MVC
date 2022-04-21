package com.dungeon;

import com.dungeon.data.ArrowLocations;
import com.dungeon.data.GameOtyughDetails;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.data.TreasureCollected;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The game interface is used to update game.
 * The various validation functions are also included in it.
 * The methods of details to be displayed are also included.
 * This is implemented by updateGame.
 */

public interface GameInterface {

  /**
   * player arrow master method is used to determine arrows into node.
   *
   * @param isStaticMaster static parameter for test cases
   * @return no of arrow
   */

  ArrayList<Integer> playerArrowMaster(boolean isStaticMaster);

  /**
   * method to get number of arrows at the given location.
   *
   * @param arrowLocations    arrow location
   * @param playerCurrentlyAt player current location
   * @return number of arrows
   */

  int getNumberOfArrowsInCurrentLocation(ArrayList<ArrowLocations> arrowLocations,
                                         Node playerCurrentlyAt);

  /**
   * method to update the game according to the moves performed.
   *
   * @param numberOfArrowsHere                 number of arrows in location
   * @param noOfArrowsCollected                number of arrows player colected
   * @param treasureCollected                  treasure collected by player
   * @param playerCurrentlyAt                  player current location
   * @param treasureDetailsOfPlayerCurrentCave treasure details in current cave
   * @param playerPossibleLocations            possible moves
   * @param isCave                             is it a cave
   */

  void validateAndUpdateGame(int numberOfArrowsHere, int noOfArrowsCollected,
                             TreasureCollected treasureCollected, Node playerCurrentlyAt,
                             Treasure treasureDetailsOfPlayerCurrentCave,
                             PlayerLocations playerPossibleLocations, boolean isCave);

  /**
   * method for getting otyugh details.
   *
   * @param otyughLocations         location with otyughs in it
   * @param playerPossibleLocations possible locations
   * @param player                  player
   * @param finalDungeon            final dungeon with caves and tunnels
   * @param rows                    no of rows
   * @param cols                    no of cols
   * @param wrapping                wrapping
   * @return number of otyughs nearby
   */

  GameOtyughDetails getGameOtyughDetails(ArrayList<Otyugh> otyughLocations,
                                         PlayerLocations playerPossibleLocations,
                                         Player player, ArrayList<Path> finalDungeon,
                                         int rows, int cols, boolean wrapping);

  /**
   * method to determine if node is a cave.
   *
   * @param playerLocation location of player
   * @param caves          list of caves
   * @return boolean
   */

  boolean isCave(Node playerLocation, ArrayList<Node> caves);

  /**
   * method to get player input.
   *
   * @param inputScanner scaner
   * @return validated inout
   */

  String getPlayerInput(Scanner inputScanner);

  /**
   * Method to get the player input for moves.
   *
   * @param inputScanner            to scan inout
   * @param playerPossibleLocations possible moves of player
   * @param playerCurrentlyAt       current node
   * @return the player moving to next location.
   */
  String getPlayerMoveInput(Scanner inputScanner, PlayerLocations playerPossibleLocations,
                            Node playerCurrentlyAt);

  /**
   * method to get the distance to shoot to.
   *
   * @param inputScanner scanner
   * @return validated input
   */

  String getPlayerShootDistanceInput(Scanner inputScanner);

  /**
   * method to get user input for pick.
   *
   * @param inputScanner scanner
   * @return validated input
   */

  String getPlayerPickInput(Scanner inputScanner);

  /**
   * method to find if player is killed.
   *
   * @param otyughLocations   otyugh location
   * @param playerCurrentlyAt player location
   * @param loopNumber        number
   * @return value to determine if player dead
   */

  int isPlayerEatenByOtyugh(ArrayList<Otyugh> otyughLocations,
                                Node playerCurrentlyAt, int loopNumber);
}