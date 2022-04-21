package com.dungeon.v3.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.dungeon.data.GameDataV3;
import com.dungeon.data.Node;
import com.dungeon.data.PlayerLocations;
import com.dungeon.v3.view.DungeonViewV3;

/**
 * The Dungeon Model class generates the model and performs the moves of player.
 * It implements the Model interface of MVC.
 * The warning for pit and win message is also dispalyed here.
 */

public class DungeonModelV3 extends JPanel implements ModelInterfaceMVC {

  private int rowX = 0;
  private int colY = 0;

  private final DungeonViewV3 dungeonViewV3;

  /**
   * Constructor of the dungeon model class.
   * This is an empty constructor for instantiating the model.
   */

  public DungeonModelV3() {
    this.dungeonViewV3 = new DungeonViewV3();
  }

  /**
   * Constructor of the dungeon model class.
   *
   * @param dungeonViewV3        the view information is passed here
   * @param playerStartPositionX the start position of the player
   * @param playerEndPositionY   tye end position of the player
   * @param gameDataV3           game data components
   */

  public DungeonModelV3(DungeonViewV3 dungeonViewV3, int playerStartPositionX,
                        int playerEndPositionY, GameDataV3 gameDataV3) {
    this.dungeonViewV3 = dungeonViewV3;
    this.rowX = playerStartPositionX;
    this.colY = playerEndPositionY;
  }

  /*
  Referred Code from :
  https://github.com/STLkru/Maze-Game/blob/master/Board.java
   */

  /**
   * Method to move player to the west.
   *
   * @param dungeonViewV3 instance of te view.
   * @param gameDataV3    game data.
   */

  @Override
  public void moveWest(DungeonViewV3 dungeonViewV3, GameDataV3 gameDataV3) {
    dungeonViewV3.set(rowX, colY, 'O', gameDataV3);
    if (dungeonViewV3.get(rowX -= 1, colY) == '0') {
      win();
    } else {
      dungeonViewV3.set(rowX, colY, 'X', gameDataV3);
    }
  }

  /**
   * Method to move player to the east.
   *
   * @param dungeonViewV3 instance of te view.
   * @param gameDataV3    game data.
   */

  @Override
  public void moveEast(DungeonViewV3 dungeonViewV3, GameDataV3 gameDataV3) {
    dungeonViewV3.set(rowX, colY, 'O', gameDataV3);
    if (dungeonViewV3.get(rowX += 1, colY) == '0') {
      win();

    } else {
      dungeonViewV3.set(rowX, colY, 'X', gameDataV3);
    }
  }

  /**
   * Method to move player to the north.
   *
   * @param dungeonViewV3 instance of te view.
   * @param gameDataV3    game data.
   */

  @Override
  public void moveNorth(DungeonViewV3 dungeonViewV3, GameDataV3 gameDataV3) {
    dungeonViewV3.set(rowX, colY, 'O', gameDataV3);
    if (dungeonViewV3.get(rowX, colY -= 1) == '0') {
      win();
    } else {
      dungeonViewV3.set(rowX, colY, 'X', gameDataV3);
    }
  }

  /**
   * Method to move player to the south.
   *
   * @param dungeonViewV3 instance of te view.
   * @param gameDataV3    game data.
   */

  @Override
  public void moveSouth(DungeonViewV3 dungeonViewV3, GameDataV3 gameDataV3) {
    dungeonViewV3.set(rowX, colY, 'O', gameDataV3);
    if (dungeonViewV3.get(rowX, colY += 1) == '0') {
      win();
    } else {
      dungeonViewV3.set(rowX, colY, 'X', gameDataV3);
    }
  }

  /**
   * Method to display the win message when player sucessfully reaches destination.
   */

  @Override
  public void win() {
    JFrame frame2 = new JFrame("Dungeon - Game Completed.");

    JLabel textLabel = new JLabel("<html>Congratulations!<br>"
            + "You have successfully reached the destination.</html>", JLabel.CENTER);
    textLabel.setFont(new Font("Verdana", Font.BOLD, 32));

    frame2.add(textLabel, BorderLayout.CENTER);
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame2.setSize(500, 500);
    frame2.setLocationRelativeTo(null);
    frame2.setVisible(true);
  }

  /**
   * Method to check if there is a pit nearby.
   *
   * @param pitLocation             location of pit
   * @param playerPossibleLocations location possible by player
   * @return boolean value
   */

  @Override
  public boolean isPitNearBy(Node pitLocation, PlayerLocations playerPossibleLocations) {
    boolean canShowPitWarning = false;
    Node playerNorthMove = playerPossibleLocations.getNorth();
    if (playerPossibleLocations.hasNorth() && playerNorthMove.getX() == pitLocation.getX()
            && playerNorthMove.getY() == pitLocation.getY()) {
      canShowPitWarning = true;
    }

    Node playerEastMove = playerPossibleLocations.getEast();
    if (playerPossibleLocations.hasEast() && playerEastMove.getX() == pitLocation.getX()
            && playerEastMove.getY() == pitLocation.getY()) {
      canShowPitWarning = true;
    }

    Node playerSouthMove = playerPossibleLocations.getSouth();
    if (playerPossibleLocations.hasSouth() && playerSouthMove.getX() == pitLocation.getX()
            && playerSouthMove.getY() == pitLocation.getY()) {
      canShowPitWarning = true;
    }

    Node playerWestMove = playerPossibleLocations.getWest();
    if (playerPossibleLocations.hasWest() && playerWestMove.getX() == pitLocation.getX()
            && playerWestMove.getY() == pitLocation.getY()) {
      canShowPitWarning = true;
    }
    return canShowPitWarning;
  }

  /**
   * Method to display message when the game ends.
   *
   * @param msg message to be dipalyed
   */

  public static void gameOver(String msg) {
    JFrame frame2 = new JFrame("Dungeon - Game Completed.");

    JLabel textLabel = new JLabel(msg, JLabel.CENTER);
    textLabel.setFont(new Font("Verdana", Font.BOLD, 32));

    frame2.setBackground(Color.green);
    frame2.add(textLabel, BorderLayout.CENTER);
    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame2.setSize(1200, 500);
    frame2.setLocationRelativeTo(null);
    frame2.setVisible(true);
  }
}