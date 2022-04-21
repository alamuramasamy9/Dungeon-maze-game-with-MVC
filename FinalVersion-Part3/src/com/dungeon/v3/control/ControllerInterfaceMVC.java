package com.dungeon.v3.control;

import com.dungeon.data.Node;
import com.dungeon.v2.ArrowsV2;
import com.dungeon.v2.DungeonV2;

/**
 * This interface is implemented by the controller class.
 * This is the controller class of the MVC model.
 * It carries out all fot the required actions and acts as a mediator between the view and model.
 */

public interface ControllerInterfaceMVC {

  /**
   * The play game method through which the program functions.
   * It is the central controller of the program through which execution of the model begins.
   */

  void playGame();

  /**
   * Node at which thief is randomly allocated.
   *
   * @param dungeon the dungeon with caves and tunnels
   * @return location of thief
   */

  Node generateThiefLocation(DungeonV2 dungeon);

  /**
   * Node at which pit is located.
   *
   * @param dungeon  the dungeon with caves and tunnels
   * @param arrowsV2 location of the dungeon details
   * @return pit location
   */

  Node generatePitLocation(DungeonV2 dungeon, ArrowsV2 arrowsV2);

  /**
   * method to initialize and carry out all actions.
   *
   * @param rows                 rows
   * @param cols                 columns
   * @param interConnectivity    interconnectivity
   * @param percentageOfTreasure percentage of treasure
   * @param wrapping             wrapping boolean
   * @param noofOtyughs          num of otyughs
   * @param needStaticGame       need static game
   */

  void init(int rows, int cols, int interConnectivity,
            int percentageOfTreasure, boolean wrapping,
            int noofOtyughs, boolean needStaticGame);
}
