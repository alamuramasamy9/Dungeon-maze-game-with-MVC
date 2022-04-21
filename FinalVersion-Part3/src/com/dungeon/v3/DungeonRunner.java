package com.dungeon.v3;

import com.dungeon.v3.control.DungeonControllerV3;
import com.dungeon.v3.model.DungeonModelV3;
import com.dungeon.v3.view.DungeonViewV3;

/**
 * The DungeonRunner class is the main driver class that is executed in this game program.
 * It creates instances of the model and view and passes it to the controller.
 * The playGame method of the controller is called and the program executes accordingly.
 */

public class DungeonRunner {

  /**
   * The main method of the Dungeon Runner class.
   *
   * @param args the arguments that are being passed.
   */

  public static void main(String[] args) {
    // New : Graphical User Interface:
    // 1. Create an instance of the model.
    // 2. Create an instance of the view.
    // 3. Create an instance of the controller.
    // 4. Call playGame() on the controller.

    DungeonModelV3 dungeonModelV3 = new DungeonModelV3();
    DungeonViewV3 dungeonViewV3 = new DungeonViewV3();
    DungeonControllerV3 dungeonController = new DungeonControllerV3(dungeonModelV3, dungeonViewV3);
    dungeonController.playGame();
  }
}
