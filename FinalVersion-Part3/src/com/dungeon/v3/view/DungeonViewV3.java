package com.dungeon.v3.view;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.dungeon.Player;
import com.dungeon.data.GameDataV3;
import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.v2.OtyughsV2;
import com.dungeon.v2.TreasureV2;

import java.util.ArrayList;

/**
 * The Dungeon View class which implements View Interface.
 * This class is the main view class that is passed on to the controller.
 * This uses Java Swing to draw the results of the
 * game and dynamically change the positions of the player.
 */

public class DungeonViewV3 extends JPanel implements ViewInterfaceMVC {

  private String[][] playerFlgMapping;
  private String[][] dungeonFlgMapping;
  private char[][] maze;
  private int rows;
  private int cols;
  private int scale = 1;

  private GameDataV3 gameDataV3;
  private String userErrorMessage = null;
  private String specialInfo = null;
  private String otuyghWarningSmellLess = null;
  private String otuyghWarningSmellHigh = null;
  private boolean showPlayerDesc = false;
  private String dungeonDescription = null;
  private String arrowsInformation = null;
  private String treasureInformation = null;
  private String arrowPickUpInformation = null;
  private String treasurePickUpInformation = null;
  private int playerArrowInformation = 0;
  private String shootInstructions = null;
  private String shootStatusInfo = null;
  private String thiefActionInformation = null;
  private String pitWarningInformation = null;

  /**
   * An empty constructor of the dungeon view class.
   */
  public DungeonViewV3() {
    //Empty constructor used for testing.
  }

  /**
   * Constructor of the dungeon view class.
   *
   * @param rows                 rows
   * @param cols                 columns
   * @param interConnectivity    interconnectivity
   * @param percentageOfTreasure percentage of treasure
   * @param wrapping             wrapping boolean
   * @param noofOtyughs          number of otyughs
   * @param needStaticGame       static to test
   * @param treasureV2           treasure details
   * @param otyughsV2            otyugh details
   * @param gameDataV3           game data details
   * @param finalDungeon         final dungeon wih caves and tunnels
   */

  public DungeonViewV3(int rows, int cols, int interConnectivity,
                       int percentageOfTreasure, boolean wrapping,
                       int noofOtyughs, boolean needStaticGame, TreasureV2 treasureV2,
                       OtyughsV2 otyughsV2, GameDataV3 gameDataV3, ArrayList<Path> finalDungeon) {
    this.gameDataV3 = gameDataV3;
    this.scale = rows;
    this.rows = rows;
    this.cols = cols;

    //Generate Dungeon
    maze = new char[cols][rows];
    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < cols; k++) {
        maze[k][i] = '=';
      }
    }
    playerFlgMapping = new String[cols][rows];
    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < cols; k++) {
        playerFlgMapping[k][i] = "player-N";
      }
    }
    dungeonFlgMapping = new String[cols][rows];
    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < cols; k++) {
        dungeonFlgMapping[k][i] = "dungeon-N";
      }
    }

    Player player = new Player();
    for (Path eachDungeonNode : finalDungeon) {

      Node startNode = eachDungeonNode.getStart();
      Node endNode = eachDungeonNode.getEnd();

      PlayerLocations playerPossibleLocations = player.getPossibleMoves(
              rows, cols, finalDungeon, startNode, wrapping);

      if (!playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = 'g';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-ES";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-ES";
      }
      if (!playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = 'h';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-EW";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-EW";
      }
      if (playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = 'i';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-NE";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-NE";
      }
      if (playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = 'j';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-NS";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-NS";
      }
      if (!playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = 'k';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-SW";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-SW";
      }
      if (playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = 'l';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-WN";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-WN";
      }

      if (playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = '1';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-N";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-N";
      }
      if (!playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = '2';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-E";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-E";
      }
      if (!playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = '3';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-S";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-S";
      }
      if (!playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = '4';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-W";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-W";
      }
      if (!playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = '5';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-ESW";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-ESW";
      }
      if (playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = '6';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-NES";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-NES";
      }
      if (playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = '7';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-NESW";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-NESW";
      }
      if (playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = '8';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-NEW";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-NEW";
      }
      if (playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[startNode.getX()][startNode.getY()] = '9';
        playerFlgMapping[startNode.getX()][startNode.getY()] = "player-SWN";
        dungeonFlgMapping[startNode.getX()][startNode.getY()] = "dungeon-SWN";
      }


      playerPossibleLocations = player.getPossibleMoves(rows, cols, finalDungeon, endNode,
              wrapping);

      if (!playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = 'g';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-ES";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-ES";
      }
      if (!playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = 'h';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-EW";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-EW";
      }
      if (playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = 'i';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-NE";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-NE";
      }
      if (playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = 'j';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-NS";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-NS";
      }
      if (!playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = 'k';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-SW";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-SW";
      }
      if (playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = 'l';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-WN";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-WN";
      }

      if (playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = '1';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-N";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-N";
      }
      if (!playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = '2';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-E";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-E";
      }
      if (!playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = '3';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-S";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-S";
      }
      if (!playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = '4';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-W";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-W";
      }
      if (!playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = '5';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-ESW";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-ESW";
      }
      if (playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && !playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = '6';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-NES";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-NES";
      }
      if (playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = '7';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-NESW";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-NESW";
      }
      if (playerPossibleLocations.hasNorth() && playerPossibleLocations.hasEast()
              && !playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = '8';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-NEW";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-NEW";
      }
      if (playerPossibleLocations.hasNorth() && !playerPossibleLocations.hasEast()
              && playerPossibleLocations.hasSouth() && playerPossibleLocations.hasWest()) {
        maze[endNode.getX()][endNode.getY()] = '9';
        playerFlgMapping[endNode.getX()][endNode.getY()] = "player-SWN";
        dungeonFlgMapping[endNode.getX()][endNode.getY()] = "dungeon-SWN";
      }
    }

    //Set start
    maze[treasureV2.getPlayerStartAt().getX()][treasureV2.getPlayerStartAt().getY()] = 'X';

    //Set end
    maze[treasureV2.getPlayerEndAt().getX()][treasureV2.getPlayerEndAt().getY()] = '0';

    printmaze();
  }

  /**
   * The paint method where the all the display functions are carried out.
   * It includes descriptions of the player, dungeon as well as the maze and maze details.
   *
   * @param g graphics
   */

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    int n = 500 / (scale + 10);

    if (showPlayerDesc) {
      g.drawString("Player current at (" + gameDataV3.getPlayerCurrentAt().getX() +
              "," + gameDataV3.getPlayerCurrentAt().getY() + ")", 500, 20);
      g.drawString("The player START position is (" + gameDataV3.getPlayerStartingLoc().getX()
              + ", " + gameDataV3.getPlayerStartingLoc().getY() + ").", 500, 40);
      g.drawString("The player END position is ("
              + gameDataV3.getPlayerEndAt().getX() + ", "
              + gameDataV3.getPlayerEndAt().getY() + ").", 500, 60);
      g.drawString("Number of Arrows the Player has : "
              + playerArrowInformation, 500, 80);
      g.drawString("Treasures collected by the player so far : (Diamond - "
              + gameDataV3.getTreasureCollected().getDiamondCount() + ", Ruby - "
              + gameDataV3.getTreasureCollected().getRubyCount() + ", Sapphire - "
              + gameDataV3.getTreasureCollected().getSapphireCount() + ")", 500, 100);
    } else {
      g.drawString("", 500, 20);
      g.drawString("", 500, 40);
      g.drawString("", 500, 60);
      g.drawString("", 500, 80);
      g.drawString("", 500, 100);
    }

    if (this.userErrorMessage != null) {
      g.setColor(Color.red);
      g.drawString(this.userErrorMessage, 500, 120);
    } else {
      g.setColor(Color.red);
      g.drawString("", 500, 120);
    }

    if (this.specialInfo != null) {
      g.setColor(Color.green);
      g.drawString(this.specialInfo, 500, 140);
    } else {
      g.setColor(Color.green);
      g.drawString("", 500, 140);
    }

    if (this.otuyghWarningSmellHigh != null) {
      g.setColor(Color.red);
      g.drawString("", 500, 160);
      g.setColor(Color.red);
      g.drawString(this.otuyghWarningSmellHigh, 500, 160);
      try {
        BufferedImage image = ImageIO.read(
                DungeonViewV3.class.getResourceAsStream("/images/smell-high.png"));
        g.drawImage(image, 470, 145, 20, 20, null);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (this.otuyghWarningSmellLess != null) {
      g.setColor(Color.red);
      g.drawString("", 500, 160);
      g.setColor(Color.red);
      g.drawString(this.otuyghWarningSmellLess, 500, 160);
      try {
        BufferedImage image = ImageIO.read(
                DungeonViewV3.class.getResourceAsStream("/images/smell-less.png"));
        g.drawImage(image, 470, 145, 20, 20, null);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      g.setColor(Color.red);
      g.drawString("", 500, 160);
    }

    if (this.dungeonDescription != null) {
      g.setColor(Color.black);
      g.drawString(this.dungeonDescription, 500, 180);
    } else {
      g.setColor(Color.black);
      g.drawString("", 500, 180);
    }

    if (this.arrowsInformation != null) {
      g.setColor(Color.black);
      g.drawString(this.arrowsInformation, 500, 200);
      try {
        BufferedImage image = ImageIO.read(
                DungeonViewV3.class.getResourceAsStream("/images/arrow-black.png"));
        g.drawImage(image, 450, 192, null);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      g.setColor(Color.black);
      g.drawString("", 500, 200);
    }

    if (this.treasureInformation != null) {
      g.setColor(Color.black);
      g.drawString(this.treasureInformation, 500, 240);
      try {
        BufferedImage image = ImageIO.read(
                DungeonViewV3.class.getResourceAsStream("/images/diamond.png"));
        g.drawImage(image, 470, 220, null);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      g.setColor(Color.black);
      g.drawString("", 500, 240);
    }

    if (this.arrowPickUpInformation != null) {
      g.setColor(Color.black);
      g.drawString(this.arrowPickUpInformation, 500, 260);
    } else {
      g.setColor(Color.black);
      g.drawString("", 500, 260);
    }

    if (this.treasurePickUpInformation != null) {
      g.setColor(Color.black);
      g.drawString(this.treasurePickUpInformation, 500, 280);
    } else {
      g.setColor(Color.black);
      g.drawString("", 500, 280);
    }

    if (this.shootInstructions != null) {
      g.setColor(Color.black);
      g.drawString(this.shootInstructions, 500, 300);
    } else {
      g.setColor(Color.black);
      g.drawString("", 500, 300);
    }

    if (this.shootStatusInfo != null) {
      g.setColor(Color.black);
      g.drawString(this.shootStatusInfo, 500, 320);
    } else {
      g.setColor(Color.black);
      g.drawString("", 500, 320);
    }

    if (this.thiefActionInformation != null) {
      g.setColor(Color.red);
      g.drawString(this.thiefActionInformation, 500, 340);
    } else {
      g.setColor(Color.black);
      g.drawString("", 500, 340);
    }

    if (this.pitWarningInformation != null) {
      g.setColor(Color.red);
      g.drawString(this.pitWarningInformation, 500, 360);
    } else {
      g.setColor(Color.black);
      g.drawString("", 500, 360);
    }

    // Referred code from following link:https://github.com/STLkru/Maze-Game/blob/master/Board.java

    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < cols; k++) {
        if ((maze[k][i] == 'O')) {
          try {
            BufferedImage image = ImageIO.read(
                    DungeonViewV3.class.getResourceAsStream(
                            "/images/" + dungeonFlgMapping[k][i] + ".png"));
            g.drawImage(image, k * n, i * n, n, n, null);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        if (maze[k][i] == 'X') {
          try {
            BufferedImage image = ImageIO.read(
                    DungeonViewV3.class.getResourceAsStream(
                            "/images/" + playerFlgMapping[k][i] + ".png"));
            g.drawImage(image, k * n, i * n, n, n, null);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      }
    }
  }

  /**
   * Method to be display when player dies after getting eaten by Otyugh.
   *
   * @param errorMsg error message
   */

  @Override
  public void showUserError(String errorMsg) {
    this.userErrorMessage = errorMsg;
    repaint();
  }

  /**
   * Method to hide the message when it is not required.
   */

  public void hideUserError() {
    this.userErrorMessage = null;
    repaint();
  }


  /**
   * Method to be display when player escapes from injured Otyugh.
   *
   * @param specialInfo error message
   */

  @Override
  public void showSpecialInfo(String specialInfo) {
    this.specialInfo = specialInfo;
    repaint();
  }

  /**
   * Method to hide the message when it is not required.
   */


  public void hideSpecialInfo() {
    this.specialInfo = null;
    repaint();
  }

  /**
   * Method to display warning when there is a slight smell of otyugh.
   *
   * @param otuyghWarningSmellLess     message to be displayed
   * @param showOtuyghWarningSmellLess boolean to see if it needs to be dispalyed
   */

  @Override
  public void showOtuyghWarningSmellLess(String otuyghWarningSmellLess,
                                         boolean showOtuyghWarningSmellLess) {
    if (showOtuyghWarningSmellLess) {
      this.otuyghWarningSmellLess = otuyghWarningSmellLess;
    } else {
      this.otuyghWarningSmellLess = null;
    }
    repaint();
  }

  /**
   * Method to display warning when there is a strong smell of otyugh.
   *
   * @param otuyghWarningSmellHigh     message to be displayed
   * @param showOtuyghWarningSmellHigh boolean to see if it needs to be dispalyed
   */

  @Override
  public void showOtuyghWarningSmellHigh(String otuyghWarningSmellHigh,
                                         boolean showOtuyghWarningSmellHigh) {
    if (showOtuyghWarningSmellHigh) {
      this.otuyghWarningSmellHigh = otuyghWarningSmellHigh;
    } else {
      this.otuyghWarningSmellHigh = null;
    }
    repaint();
  }

  /**
   * Method to show player description.
   *
   * @param showPlayerDesc player descripton true or false
   */

  @Override
  public void showPlayerDesc(boolean showPlayerDesc) {
    this.showPlayerDesc = showPlayerDesc;
    repaint();
  }

  /**
   * Method to show dungeon description.
   *
   * @param dungeonDescription     dungeon description.
   * @param showDungeonDescription boolean value.
   */

  @Override
  public void showDungeonDescription(String dungeonDescription, boolean showDungeonDescription) {
    if (showDungeonDescription) {
      this.dungeonDescription = dungeonDescription;
    } else {
      this.dungeonDescription = null;
    }
    repaint();
  }

  /**
   * Method to show the arrow information of the current node.
   *
   * @param arrowsInformation     arrow details of node
   * @param showArrowsInformation boolean value
   */

  @Override
  public void showArrowsInformation(String arrowsInformation, boolean showArrowsInformation) {
    if (showArrowsInformation) {
      this.arrowsInformation = arrowsInformation;
    } else {
      this.arrowsInformation = null;
    }
    repaint();
  }

  /**
   * Method to show the treasure information of the current node.
   *
   * @param treasureInformation     treasure details of node
   * @param showTreasureInformation boolean value
   */

  @Override
  public void showTreasureInformaion(String treasureInformation, boolean showTreasureInformation) {
    if (showTreasureInformation) {
      this.treasureInformation = treasureInformation;
    } else {
      this.treasureInformation = null;
    }
    repaint();
  }

  /**
   * Method to show details of arrow picked up by user.
   *
   * @param arrowPickUpInformation     msg for arrows that have been picked up.
   * @param showArrowPickUpInformation boolean value
   */

  @Override
  public void showArrowPickUpInformation(String arrowPickUpInformation,
                                         boolean showArrowPickUpInformation) {
    if (showArrowPickUpInformation) {
      this.arrowPickUpInformation = arrowPickUpInformation;
    } else {
      this.arrowPickUpInformation = null;
    }
    repaint();
  }

  /**
   * Method to show details of treasure picked up by user.
   *
   * @param treasurePickUpInformation     msg for treasure that has been picked up.
   * @param showTreasurePickUpInformation boolean value
   */

  @Override
  public void showTreasurePickUpInformation(String treasurePickUpInformation,
                                            boolean showTreasurePickUpInformation) {
    if (showTreasurePickUpInformation) {
      this.treasurePickUpInformation = treasurePickUpInformation;
    } else {
      this.treasurePickUpInformation = null;
    }
    repaint();
  }

  /**
   * method to show number of arrows player has.
   *
   * @param playerArrowInformation number of arrows
   */

  @Override
  public void showPlayerArrowInformation(int playerArrowInformation) {
    this.playerArrowInformation = playerArrowInformation;
    repaint();
  }

  /**
   * method to show the instruction for shoot.
   *
   * @param showShootInstructions boolean
   */

  @Override
  public void showShootInstructions(boolean showShootInstructions) {
    if (showShootInstructions) {
      this.shootInstructions = "Please choose the direction to shoot"
              + " (Use arrow keys on your keyboard)";
    } else {
      this.shootInstructions = null;
    }
    repaint();
  }

  /**
   * method to show message after shoot( hit or miss).
   *
   * @param shootStatusInfo     message to be dispalyed
   * @param showShootStatusInfo boolean value
   */
  @Override
  public void showShootStatusInfo(String shootStatusInfo, boolean showShootStatusInfo) {
    if (showShootStatusInfo) {
      this.shootInstructions = shootStatusInfo;
    } else {
      this.shootInstructions = null;
    }
    repaint();
  }

  /**
   * method to be displayed if thief steals treasure.
   *
   * @param thiefActionInformation     message to be displayed
   * @param showThiefActionInformation boolean value
   */

  @Override
  public void showThiefActionInformation(String thiefActionInformation,
                                         boolean showThiefActionInformation) {
    if (showThiefActionInformation) {
      this.thiefActionInformation = thiefActionInformation;
    } else {
      this.thiefActionInformation = null;
    }
    repaint();
  }

  /**
   * Method to show warning when there is a pit close by.
   *
   * @param pitWarningInformation     message to be displayed
   * @param showPitWarningInformation boolean value
   */
  @Override
  public void showPitWarningInformation(String pitWarningInformation,
                                        boolean showPitWarningInformation) {
    if (showPitWarningInformation) {
      this.pitWarningInformation = pitWarningInformation;
    } else {
      this.pitWarningInformation = null;
    }
    repaint();
  }

  /**
   * method to get current coordinates of player.
   *
   * @param x x coordinate
   * @param y y coordiante
   * @return result
   */

  @Override
  public char get(int x, int y) {
    System.out.println("The get X & Y Coordinates are : " + x + ", " + y);
    char result = '#';
    try {
      result = maze[x][y];
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * mthod to set value.
   *
   * @param x          x coordinate
   * @param y          y coordiante
   * @param value      value to set
   * @param gameDataV3 game data
   */

  @Override
  public void set(int x, int y, char value, GameDataV3 gameDataV3) {
    this.gameDataV3 = gameDataV3;
    maze[x][y] = value;
    System.out.println("the moving coordinates (" + x + "," + y + ") & value : " + value);
    repaint();
  }

  /**
   * Method to print the final maze.
   */
  @Override
  public void printmaze() {
    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < cols; k++) {
        System.out.print(maze[k][i]);
        System.out.print(" ");
      }
      System.out.println("");
    }
  }

}
