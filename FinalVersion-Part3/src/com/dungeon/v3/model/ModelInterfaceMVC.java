package com.dungeon.v3.model;

import com.dungeon.data.GameDataV3;
import com.dungeon.data.Node;
import com.dungeon.data.PlayerLocations;
import com.dungeon.v3.view.DungeonViewV3;

/**
 * This interface is implemented by the dungeon model class.
 * The Dungeon Model class generates the model and performs the moves of player.
 * The warning for pit and win message is also dispalyed here.
 */

public interface ModelInterfaceMVC {


  void moveWest(DungeonViewV3 dungeonViewV3, GameDataV3 gameDataV3);

  /**
   * Method to move player to the east.
   *
   * @param dungeonViewV3 instance of te view.
   * @param gameDataV3    game data.
   */

  void moveEast(DungeonViewV3 dungeonViewV3, GameDataV3 gameDataV3);

  /**
   * Method to move player to the north.
   *
   * @param dungeonViewV3 instance of te view.
   * @param gameDataV3    game data.
   */

  void moveNorth(DungeonViewV3 dungeonViewV3, GameDataV3 gameDataV3);

  /**
   * Method to move player to the south.
   *
   * @param dungeonViewV3 instance of te view.
   * @param gameDataV3    game data.
   */

  void moveSouth(DungeonViewV3 dungeonViewV3, GameDataV3 gameDataV3);

  /**
   * Method to display the win message when player sucessfully reaches destination.
   */

  void win();

  /**
   * Method to check if there is a pit nearby.
   *
   * @param pitLocation             location of pit
   * @param playerPossibleLocations location possible by player
   * @return boolean value
   */

  boolean isPitNearBy(Node pitLocation, PlayerLocations playerPossibleLocations);


}
