package com.dungeon.v3.view;

import com.dungeon.data.GameDataV3;

import java.awt.Graphics;

/**
 * The Dungeon View class implements View Interface.
 * This class is the main view class that is passed on to the controller.
 * This uses Java Swing to draw the results of the
 * game and dynamically change the positions of the player.
 */

public interface ViewInterfaceMVC {

  /**
   * The paint method where the all the display functions are carried out.
   * It includes descriptions of the player, dungeon as well as the maze and maze details.
   *
   * @param g graphics
   */

  void paint(Graphics g);

  /**
   * Method to be display when player dies after getting eaten by Otyugh.
   *
   * @param errorMsg error message
   */

  void showUserError(String errorMsg);

  /**
   * Method to be display when player escapes from injured Otyugh.
   *
   * @param specialInfo error message
   */

  void showSpecialInfo(String specialInfo);

  /**
   * Method to display warning when there is a strong smell of otyugh.
   *
   * @param otuyghWarningSmellHigh     message to be displayed
   * @param showOtuyghWarningSmellHigh boolean to see if it needs to be dispalyed
   */

  void showOtuyghWarningSmellHigh(String otuyghWarningSmellHigh,
                                  boolean showOtuyghWarningSmellHigh);

  /**
   * Method to display warning when there is a slight smell of otyugh.
   *
   * @param otuyghWarningSmellLess     message to be displayed
   * @param showOtuyghWarningSmellLess boolean to see if it needs to be dispalyed
   */

  void showOtuyghWarningSmellLess(
          String otuyghWarningSmellLess, boolean showOtuyghWarningSmellLess);

  /**
   * Method to show player description.
   *
   * @param showPlayerDesc player descripton true or false
   */
  void showPlayerDesc(boolean showPlayerDesc);

  /**
   * Method to show dungeon description.
   *
   * @param dungeonDescription     dungeon description.
   * @param showDungeonDescription boolean value.
   */
  void showDungeonDescription(String dungeonDescription, boolean showDungeonDescription);

  /**
   * Method to show the treasure information of the current node.
   *
   * @param treasureInformation     treasure details of node
   * @param showTreasureInformation boolean value
   */
  void showTreasureInformaion(String treasureInformation, boolean showTreasureInformation);

  /**
   * Method to show the arrow information of the current node.
   *
   * @param arrowsInformation     arrow details of node
   * @param showArrowsInformation boolean value
   */
  void showArrowsInformation(String arrowsInformation, boolean showArrowsInformation);

  /**
   * Method to show details of treasure picked up by user.
   *
   * @param treasurePickUpInformation     msg for treasure that has been picked up.
   * @param showTreasurePickUpInformation boolean value
   */

  void showTreasurePickUpInformation(String treasurePickUpInformation,
                                     boolean showTreasurePickUpInformation);

  /**
   * Method to show details of arrow picked up by user.
   *
   * @param arrowPickUpInformation     msg for arrows that have been picked up.
   * @param showArrowPickUpInformation boolean value
   */

  void showArrowPickUpInformation(String arrowPickUpInformation,
                                  boolean showArrowPickUpInformation);


  /**
   * method to show number of arrows player has.
   *
   * @param playerArrowInformation number of arrows
   */
  void showPlayerArrowInformation(int playerArrowInformation);

  /**
   * method to show the instruction for shoot.
   *
   * @param showShootInstructions boolean
   */

  void showShootInstructions(boolean showShootInstructions);

  /**
   * method to show message after shoot( hit or miss).
   *
   * @param shootStatusInfo     message to be dispalyed
   * @param showShootStatusInfo boolean value
   */

  void showShootStatusInfo(String shootStatusInfo, boolean showShootStatusInfo);

  /**
   * method to be displayed if thief steals treasure.
   *
   * @param thiefActionInformation     message to be displayed
   * @param showThiefActionInformation boolean value
   */

  void showThiefActionInformation(String thiefActionInformation,
                                  boolean showThiefActionInformation);

  /**
   * Method to show warning when there is a pit close by.
   *
   * @param pitWarningInformation     message to be displayed
   * @param showPitWarningInformation boolean value
   */
  void showPitWarningInformation(String pitWarningInformation,
                                 boolean showPitWarningInformation);

  /**
   * method to get current coordinates of player.
   *
   * @param x x coordinate
   * @param y y coordiante
   * @return result
   */

  char get(int x, int y);

  /**
   * mthod to set value.
   *
   * @param x          x coordinate
   * @param y          y coordiante
   * @param value      value to set
   * @param gameDataV3 game data
   */

  void set(int x, int y, char value, GameDataV3 gameDataV3);

  /**
   * Method to print the final maze.
   */
  void printmaze();


}