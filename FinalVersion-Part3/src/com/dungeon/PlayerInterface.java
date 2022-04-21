package com.dungeon;

import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.data.TreasureCollected;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Player Interface defines all the methods that is required in the player class.
 * The player treasure collection is done in this class.
 * The moves of the player is also updated in this class.
 * It is checked if the end has been reached usign these methods.
 * It is implemented by the Player class.
 */

public interface PlayerInterface {

  /**
   * checking if the node the player is at has treasure.
   *
   * @param treasureCaves caves that have treasure
   * @param playerStartAt the location node of the player
   * @return the treasure that is collected
   */

  TreasureCollected checkPlayerCaveHasTreasure(
          ArrayList<HashMap<Node, Treasure>> treasureCaves, Node playerStartAt);

  /**
   * cheking the treasure of the cave.
   *
   * @param treasureCaves list of caves that have treasure.
   * @param cave          the current cave node.
   * @return the treasure if present else assign all to false
   */

  Treasure getTreasureDetailsOfCave(ArrayList<HashMap<Node, Treasure>> treasureCaves,
                                    Node cave);


  /**
   * getting the node to move given the input.
   *
   * @param playerPossibleLocations possible directions for the player to move in.
   * @param nextMove                string passed to move the player
   * @return node to be moved to
   */

  Node getNextMoveCave(PlayerLocations playerPossibleLocations, String nextMove);


  /**
   * method to get the possible moves of the player at a location.
   *
   * @param rows         number of rows
   * @param cols         number of columns
   * @param finalDungeon the dungeon with all caves and tunnels
   * @param playerAt     current location of player
   * @return return possible loations
   */

  PlayerLocations getPossibleMoves(int rows, int cols, ArrayList<Path> finalDungeon,
                                   Node playerAt, boolean wrapping);

  /**
   * method to check if destination is reached.
   *
   * @param currentCave current location of player
   * @param playerEndAt end generated randomly
   * @return true if it is end else false
   */

  boolean isDestinationReached(Node currentCave, Node playerEndAt);
}
