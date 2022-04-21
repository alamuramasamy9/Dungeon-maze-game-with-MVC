package com.dungeon.v3.control;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.awt.BorderLayout;
import java.awt.MenuItem;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import javax.swing.InputMap;
import javax.swing.KeyStroke;


import com.dungeon.GameModel;
import com.dungeon.Player;
import com.dungeon.UpdateGame;
import com.dungeon.data.ArrowLocations;
import com.dungeon.data.GameDataV3;
import com.dungeon.data.GameOtyughDetails;
import com.dungeon.data.NearbyOtyughs;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.data.TreasureCollected;
import com.dungeon.v2.ArrowsV2;
import com.dungeon.v2.DungeonV2;
import com.dungeon.v2.OtyughsV2;
import com.dungeon.v2.TreasureV2;
import com.dungeon.v3.model.DungeonModelV3;
import com.dungeon.v3.view.DungeonInputsV3;
import com.dungeon.v3.view.DungeonViewV3;

/**
 * This is the controller class of the MVC model.
 * It implements method of the controller interface.
 * It carries out all fot the required actions and acts as a mediator between the view and model.
 */

public class DungeonControllerV3 extends JPanel implements ControllerInterfaceMVC {

  /**
   * An empty constructor.
   */

  public DungeonControllerV3() {
    //This is utilized for testing.
  }

  /**
   * Constructor of the dungeon controller class.
   *
   * @param dungeonModelV3 instance of the model
   * @param dungeonViewV3  instance of the view
   */
  public DungeonControllerV3(DungeonModelV3 dungeonModelV3, DungeonViewV3 dungeonViewV3) {

    //passing in the view and model.
  }

  /**
   * The play game method through which the program functions.
   * It is the central controller of the program through which execution of the model begins.
   */

  @Override
  public void playGame() {
    new DungeonInputsV3();
  }

  private JFrame frame = new JFrame("Dungeon");
  private JPanel p = new JPanel(new BorderLayout());
  private JPanel p2 = new JPanel(new BorderLayout());

  private TreasureCollected treasureCollected = new TreasureCollected(0, 0, 0);
  private GameOtyughDetails gameOtyughDetails = null;
  public int noOfArrowsCollected = 3;
  private int loopNumber = 1;
  private UpdateGame updateGame = null;
  private ArrayList<Otyugh> otyughLocations = null;
  private PlayerLocations playerPossibleLocations = null;
  private Player player = null;
  private ArrayList<Path> finalDungeon = null;
  private ArrayList<HashMap<Node, Treasure>> treasureCaves = null;
  private Node playerCurrentlyAt = null;
  private Node playerEndAt = null;
  private ArrayList<Node> caves = null;
  private ArrayList<ArrowLocations> arrowLocations = null;
  private GameDataV3 gameDataV3 = null;
  private int numberOfArrowsHere = 0;
  private boolean isShootButtonPressed = false;
  private Node thiefLocation = null;
  private Node pitLocation = null;

  /**
   * The main method that s used for testing purposes of the controller.
   *
   * @param args args
   */
  public static void main(String[] args) {
    args = new String[]{"4", "6", "8", "20", "false", "4"};
    int rows = Integer.parseInt(args[0]);
    int cols = Integer.parseInt(args[1]);
    int interConnectivity = Integer.parseInt(args[2]);
    int percentageOfTreasure = Integer.parseInt(args[3]);
    boolean wrapping = Boolean.parseBoolean(args[4]);
    int noofOtyughs = Integer.parseInt(args[5]);
    boolean needStaticGame = false;
    new DungeonControllerV3().init(rows, cols, interConnectivity, percentageOfTreasure,
            wrapping, noofOtyughs, needStaticGame);
  }

  /**
   * Node at which thief is randomly allocated.
   *
   * @param dungeon the dungeon with caves and tunnels
   * @return location of thief
   */

  @Override
  public Node generateThiefLocation(DungeonV2 dungeon) {
    ArrayList<Node> cavesListTemp = new ArrayList<Node>();
    cavesListTemp.addAll(dungeon.getCaves());
    Collections.shuffle(cavesListTemp);
    Node thiefLocation = cavesListTemp.get(0);
    cavesListTemp = null;
    return thiefLocation;
  }

  /**
   * Node at which pit is located.
   *
   * @param dungeon  the dungeon with caves and tunnels
   * @param arrowsV2 location of the dungeon details
   * @return pit location
   */

  @Override
  public Node generatePitLocation(DungeonV2 dungeon, ArrowsV2 arrowsV2) {
    Node pitLocation = null;
    ArrayList<Node> cavesListTemp = new ArrayList<Node>();
    cavesListTemp.addAll(dungeon.getCaves());
    Collections.shuffle(cavesListTemp);
    if (arrowsV2.getPlayerCurrentlyAt().getX() == cavesListTemp.get(1).getX()
            && arrowsV2.getPlayerCurrentlyAt().getY() == cavesListTemp.get(1).getY()) {
      pitLocation = cavesListTemp.get(2);
      if (arrowsV2.getPlayerEndAt().getX() == cavesListTemp.get(2).getX()
              && arrowsV2.getPlayerEndAt().getY() == cavesListTemp.get(2).getY()) {
        pitLocation = cavesListTemp.get(3);
      }
    } else {
      pitLocation = cavesListTemp.get(1);
      if (arrowsV2.getPlayerEndAt().getX() == cavesListTemp.get(1).getX()
              && arrowsV2.getPlayerEndAt().getY() == cavesListTemp.get(1).getY()) {
        pitLocation = cavesListTemp.get(2);
      }
    }
    cavesListTemp = null;
    return pitLocation;
  }

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

  @Override
  public void init(int rows, int cols, int interConnectivity,
                   int percentageOfTreasure, boolean wrapping,
                   int noofOtyughs, boolean needStaticGame) {
    frame = new JFrame("Dungeon");
    GameModel gameModel = new GameModel();
    DungeonV2 dungeon = gameModel.generateDungeon(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame);
    TreasureV2 treasureV2 = gameModel.allocateTreasure(rows, cols, wrapping, dungeon.getMazeObj(),
            dungeon.getNumberOfCavesTreasureAllocated(),
            dungeon.getCaves(), dungeon.getTreasureMaster(),
            dungeon.getFinalDungeon(), needStaticGame);
    OtyughsV2 otyughsV2 = gameModel.allocateOtyughs(treasureV2.getCaves(),
            treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), noofOtyughs, dungeon.getTunnels(), needStaticGame);
    ArrowsV2 arrowsV2 = gameModel.allocateArrows(
            dungeon.getNumberOfCavesTreasureAllocated(), otyughsV2.getCaves(),
            otyughsV2.getProject2(), otyughsV2.getTunnels(),
            otyughsV2.getOtyughLocations(), rows, cols, wrapping,
            treasureV2.getPlayerPossibleLocations(), treasureV2.getPlayer(),
            treasureV2.getFinalDungeon(),
            treasureV2.getTreasureCaves(), treasureV2.getPlayerStartAt(),
            treasureV2.getPlayerEndAt(), needStaticGame);

    for (HashMap<Node, Treasure> eachTreasureCaves : treasureV2.getTreasureCaves()) {
      for (Node eachTreasureNode : eachTreasureCaves.keySet()) {
        Treasure treasureDetails = eachTreasureCaves.get(eachTreasureNode);
        System.out.println("The allocated treasure caves are : (" + eachTreasureNode.getX()
                + "," + eachTreasureNode.getY() + "), Treasure details : Diamond - "
                + treasureDetails.hasDiamond() + ", Ruby - "
                + treasureDetails.hasRuby() + ", Sapphire - " + treasureDetails.hasSapphire());
      }

    }

    this.thiefLocation = this.generateThiefLocation(dungeon);
    System.out.println("The thief location is : (" + this.thiefLocation.getX()
            + "," + this.thiefLocation.getY() + ")");
    this.pitLocation = this.generatePitLocation(dungeon, arrowsV2);
    System.out.println("The Pit location is : (" + this.pitLocation.getX()
            + "," + this.pitLocation.getY() + ")");

    updateGame = arrowsV2.getProject2();
    otyughLocations = arrowsV2.getOtyughLocations();
    playerPossibleLocations = arrowsV2.getPlayerPossibleLocations();
    player = arrowsV2.getPlayer();
    finalDungeon = arrowsV2.getFinalDungeon();
    treasureCaves = arrowsV2.getTreasureCaves();
    playerCurrentlyAt = arrowsV2.getPlayerCurrentlyAt();
    playerEndAt = arrowsV2.getPlayerEndAt();
    caves = arrowsV2.getCaves();
    arrowLocations = arrowsV2.getArrowLocations();
    gameDataV3 = new GameDataV3(playerCurrentlyAt, treasureCollected,
            numberOfArrowsHere, playerCurrentlyAt, playerEndAt,
            noOfArrowsCollected, new Treasure(false, false, false));

    final DungeonViewV3 dungeonViewV3 = new DungeonViewV3(rows, cols,
            interConnectivity, percentageOfTreasure, wrapping,
            noofOtyughs, needStaticGame, treasureV2, otyughsV2, gameDataV3, finalDungeon);
    final DungeonModelV3 dungeonModelV3 = new DungeonModelV3(dungeonViewV3,
            treasureV2.getPlayerStartAt().getX(), treasureV2.getPlayerStartAt().getY(), gameDataV3);

    p.add(dungeonViewV3, BorderLayout.CENTER);
    p.setFocusable(true);
    frame.add(p, BorderLayout.CENTER);
    frame.add(p2, BorderLayout.SOUTH);

    final MenuBar menuBar = new MenuBar();

    //https://www.javatpoint.com/java-jmenuitem-and-jmenu
    //create menus
    Menu optionsMenu = new Menu("Options");
    Menu gameMenu = new Menu("Game");
    Menu aboutMenu = new Menu("Help");

    //create menu items
    MenuItem optionsMenuItem1 = new MenuItem("Show Player Description");
    optionsMenuItem1.setActionCommand("Show Player Description");

    MenuItem optionsMenuItem2 = new MenuItem("Hide Player Description");
    optionsMenuItem2.setActionCommand("Hide Player Description");

    MenuItem optionsMenuItem3 = new MenuItem("Show Dungeon Description");
    optionsMenuItem3.setActionCommand("Show Dungeon Description");

    MenuItem optionsMenuItem4 = new MenuItem("Hide Dungeon Description");
    optionsMenuItem4.setActionCommand("Hide Dungeon Description");

    MenuItem gameItem1 = new MenuItem("New Game");
    gameItem1.setActionCommand("New Game");

    MenuItem gameItem2 = new MenuItem("Reset Game");
    gameItem2.setActionCommand("Reset Game");

    MenuItem gameItem3 = new MenuItem("Quit");
    gameItem3.setActionCommand("Quit");

    MenuItem aboutItem1 = new MenuItem("Instructions");
    aboutItem1.setActionCommand("Instructions");

    optionsMenuItem1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dungeonViewV3.showPlayerArrowInformation(noOfArrowsCollected);
        dungeonViewV3.showPlayerDesc(true);
      }
    });
    optionsMenuItem2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dungeonViewV3.showPlayerDesc(false);
      }
    });
    optionsMenuItem3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dungeonViewV3.showDungeonDescription("Rows : "
                + rows + ", Cols : " + cols + ", InterConnection : " + interConnectivity
                + ", % of Treasure : " + percentageOfTreasure + ", Wrapping : "
                + wrapping + ", No. of Otyughs : " + noofOtyughs, true);
      }
    });
    optionsMenuItem4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dungeonViewV3.showDungeonDescription(null, false);
      }
    });


    gameItem1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        frame.dispose();
        new DungeonInputsV3();
      }
    });
    gameItem2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        frame.dispose();
        new DungeonControllerV3().init(rows, cols, interConnectivity,
                percentageOfTreasure, wrapping, noofOtyughs, needStaticGame);
      }
    });
    gameItem3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        System.exit(0);
      }
    });

    aboutItem1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        JFrame frame = new JFrame("Instructions");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JLabel textLabel = new JLabel("" + "<html>" +
                "1. Player starts at the position that appears.<br/>"
                + "The red robot icon is used to indicate the player.<br/>"
                + "2. The maze will appear as the caves and tunnels are visited<br/>"
                + "3. Keyboard Arrow keys can be used to move the player<br/>"
                + "4. Top Arrow -> North, Right Arrow -> East, Bottom Arrow "
                + "-> South, Left Arrow -> West<br/>"
                + "5. Press p to pick up treasures and arrows <br/>"
                + "6. Press s to shoot followed by the arrow key for direction to shoot. <br/>"
                + "7. The Options menu has the option to show and hide player"
                + " and dungeon description.<br/>"
                + "8. The Game menu has the options for new game, reset and quit<br/>."
                + "9. Help has instructions that pop up."
                + "10. The game ends when Player wins or dies.<br/>"
                + "</html>");
        frame.getContentPane().add(textLabel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
      }
    });


    //add menu items to menus
    optionsMenu.add(optionsMenuItem1);
    optionsMenu.add(optionsMenuItem2);
    optionsMenu.add(optionsMenuItem3);
    optionsMenu.add(optionsMenuItem4);

    gameMenu.add(gameItem1);
    gameMenu.add(gameItem2);
    gameMenu.add(gameItem3);

    aboutMenu.add(aboutItem1);

    //add menu to menubar
    menuBar.add(optionsMenu);
    menuBar.add(gameMenu);
    menuBar.add(aboutMenu);
    frame.setMenuBar(menuBar);
    frame.setVisible(true);


    /*Game control buttons - start*/
    JButton south = new JButton("South");
    south.setFocusable(false);
    p2.add(south, BorderLayout.SOUTH);
    south.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dungeonModelV3.moveSouth(dungeonViewV3, gameDataV3);
      }
    });

    JButton north = new JButton("North");
    north.setFocusable(false);
    p2.add(north, BorderLayout.NORTH);
    north.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dungeonModelV3.moveNorth(dungeonViewV3, gameDataV3);
      }
    });

    JButton east = new JButton("East");
    east.setFocusable(false);
    p2.add(east, BorderLayout.EAST);
    east.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dungeonModelV3.moveEast(dungeonViewV3, gameDataV3);
      }
    });

    JButton menu = new JButton("New Game");
    menu.setFocusable(false);
    p2.add(menu, BorderLayout.CENTER);
    menu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        frame.dispose();
        new DungeonInputsV3();
      }
    });

    JButton west = new JButton("West");
    west.setFocusable(false);
    p2.add(west, BorderLayout.WEST);
    west.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dungeonModelV3.moveWest(dungeonViewV3, gameDataV3);
      }
    });


    //https://stackoverflow.com/questions/11919941/add-scrollpane-to-jpanel-when-the-panel-is-full-java

    JScrollPane scrollPanel = new JScrollPane(p);
    disableArrowKeys(scrollPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));
    scrollPanel.setBounds(0, 0, 800, 900);
    p.setBounds(0, 0, 1920, 1080);
    p.setPreferredSize(new Dimension(1920, 1080));
    frame.getContentPane().add(scrollPanel);

    /*Game control buttons - end*/


    /*Show if any arrows found while starting the game - Starts*/
    gameOtyughDetails = updateGame.getGameOtyughDetails(otyughLocations,
            playerPossibleLocations, player, finalDungeon, rows, cols, wrapping);
    int noOfOtyughsInOnePath = gameOtyughDetails.getNoOfOtyughsInOnePath();
    int noOfOtyughsInTwoPath = gameOtyughDetails.getNoOfOtyughsInTwoPath();
    ArrayList<NearbyOtyughs> nearByOtyughs = gameOtyughDetails.getNearByOtyughs();

    Treasure treasureDetailsOfPlayerCurrentCave = player.getTreasureDetailsOfCave(treasureCaves,
            playerCurrentlyAt);

    boolean isCave = updateGame.isCave(playerEndAt, caves);

    numberOfArrowsHere = updateGame.getNumberOfArrowsInCurrentLocation(
            arrowLocations, playerCurrentlyAt);

    updateGame.validateAndUpdateGame(numberOfArrowsHere, noOfArrowsCollected, treasureCollected,
            playerCurrentlyAt, treasureDetailsOfPlayerCurrentCave, playerPossibleLocations, isCave);

    if (numberOfArrowsHere > 0) {
      System.out.println("You find " + numberOfArrowsHere + " arrows here. ");
      dungeonViewV3.showArrowsInformation("You find " + numberOfArrowsHere
              + " arrows here. ", true);
    } else {
      dungeonViewV3.showArrowsInformation(null, false);
    }
    if (treasureDetailsOfPlayerCurrentCave.hasDiamond()
            || treasureDetailsOfPlayerCurrentCave.hasRuby()
            || treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
      String treasureInfo = "You find treasure here. ";
      System.out.println("You find treasure here. ");
      if (treasureDetailsOfPlayerCurrentCave.hasDiamond()) {
        System.out.println("Diamond : 1");
        treasureInfo += " Diamond : 1 ";
      }
      if (treasureDetailsOfPlayerCurrentCave.hasRuby()) {
        System.out.println("Ruby : 1");
        treasureInfo += " Ruby : 1 ";
      }
      if (treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
        System.out.println("Sapphire : 1");
        treasureInfo += " Sapphire : 1";
      }
      dungeonViewV3.showTreasureInformaion(treasureInfo, true);
    } else {
      dungeonViewV3.showTreasureInformaion(null, false);
    }

    if (noOfOtyughsInOnePath > 0 || noOfOtyughsInTwoPath > 1) {
      System.out.println("You smell something terrible nearby");
      dungeonViewV3.showOtuyghWarningSmellHigh(
              "You smell something terrible nearby", true);
    } else if (noOfOtyughsInTwoPath == 1) {
      System.out.println("You smell something little nearby");
      dungeonViewV3.showOtuyghWarningSmellLess(
              "You smell something little nearby", true);
    } else {
      dungeonViewV3.showOtuyghWarningSmellLess(null, false);
      dungeonViewV3.showOtuyghWarningSmellHigh(null, false);
    }
    System.out.println("Move, Pickup, or Shoot (M-P-S)?");

    dungeonViewV3.showArrowPickUpInformation(null, false);
    dungeonViewV3.showTreasurePickUpInformation(null, false);
    dungeonViewV3.hideSpecialInfo();
    /*Show if any arrows found while starting the game - Ends*/

    p.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        String userInput = "";//M, P, S
        String nextMove = "";//N, E, S, W
        String treasureToPick = "";//Treasure, Arrow
        String shootDirection = "";//N, E, S, W
        int inputDistance = 1;//1 - 5


        dungeonViewV3.showShootInstructions(false);
        dungeonViewV3.showShootStatusInfo(null, false);
        if (keyCode == KeyEvent.VK_LEFT) {
          userInput = "M";
          nextMove = "W";
          shootDirection = "W";
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
          userInput = "M";
          nextMove = "E";
          shootDirection = "E";
        }
        if (keyCode == KeyEvent.VK_UP) {
          userInput = "M";
          nextMove = "N";
          shootDirection = "N";
        }
        if (keyCode == KeyEvent.VK_DOWN) {
          userInput = "M";
          nextMove = "S";
          shootDirection = "S";
        }
        if (e.getKeyChar() == 's') {
          System.out.println("you shoot");

          dungeonViewV3.showShootInstructions(true);
          isShootButtonPressed = true;
          return;
        }
        if (e.getKeyChar() == 'p') {
          userInput = "P";
        }
        if (userInput.equals("")) {
          return;
        }
        if (isShootButtonPressed) {
          userInput = "S";
          isShootButtonPressed = false;
        }


        dungeonViewV3.showPlayerArrowInformation(noOfArrowsCollected);
        ArrayList<NearbyOtyughs> nearByOtyughs = null;
        dungeonViewV3.showArrowPickUpInformation(null, false);
        dungeonViewV3.showTreasurePickUpInformation(null, false);
        dungeonViewV3.hideSpecialInfo();
        dungeonViewV3.showThiefActionInformation(null, false);
        Treasure treasureDetailsOfPlayerCurrentCave = null;
        if (userInput.equals("M")) {

          //Validate if the move is valid, if not ignore the move
          if (!updateGame.validatePlayerMoveInput(playerPossibleLocations, nextMove,
                  false, playerCurrentlyAt)) {
            String nextMoveDesc = "";
            if (nextMove.equals("N")) {
              nextMoveDesc = "North";
            } else if (nextMove.equals("E")) {
              nextMoveDesc = "East";
            } else if (nextMove.equals("S")) {
              nextMoveDesc = "South";
            } else if (nextMove.equals("W")) {
              nextMoveDesc = "West";
            }
            dungeonViewV3.showUserError("Invalid Move : The move of "
                    + nextMoveDesc + " direction from (" + playerCurrentlyAt.getX() + ", "
                    + playerCurrentlyAt.getY() + ") is not valid");
            return;
          } else {
            dungeonViewV3.hideUserError();
          }

          if (isCave) {
            System.out.println("Where to?, you are in a cave at ("
                    + playerCurrentlyAt.getX() + ", "
                    + playerCurrentlyAt.getY() + ") and possible moves are (North["
                    + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasWest()
                    + ")");
          } else {
            System.out.println("Where to?, you are in a tunnel at ("
                    + playerCurrentlyAt.getX() + ", "
                    + playerCurrentlyAt.getY() + ") and possible moves are (North["
                    + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasWest()
                    + ")");
          }

          System.out.println("\nPlease input your move [case-sensitive] (N/E/S/W):");
          // Validate user input & update game


          System.out.println("\n\n\nMove accepted & player " +
                  "successfully moved to the next location");
          playerCurrentlyAt = player.getNextMoveCave(playerPossibleLocations, nextMove);
          playerPossibleLocations = player.getPossibleMoves(
                  rows, cols, finalDungeon, playerCurrentlyAt,
                  wrapping);
          gameDataV3.setPlayerCurrentAt(playerCurrentlyAt);

          treasureDetailsOfPlayerCurrentCave = player.getTreasureDetailsOfCave(treasureCaves,
                  playerCurrentlyAt);


          if (pitLocation.getX() == playerCurrentlyAt.getX() && pitLocation.getY() ==
                  playerCurrentlyAt.getY()) {
            frame.dispose();
            DungeonModelV3.gameOver(
                    "<html>You have fallen into the Pit<br/>Game Over.</html>");
            return;
          }

          if (thiefLocation.getX() == playerCurrentlyAt.getX()
                  && thiefLocation.getY() == playerCurrentlyAt.getY()) {
            treasureCollected.setDiamondCount(0);
            treasureCollected.setRubyCount(0);
            treasureCollected.setSapphireCount(0);
            dungeonViewV3.showThiefActionInformation(
                    "Oh No! Your treasure has been stolen by the Thief", true);
          }

          if (dungeonModelV3.isPitNearBy(pitLocation, playerPossibleLocations)) {
            dungeonViewV3.showPitWarningInformation(
                    "You are near a Pit!", true);
          } else {
            dungeonViewV3.showPitWarningInformation(null, false);
          }

          gameOtyughDetails = updateGame.getGameOtyughDetails(otyughLocations,
                  playerPossibleLocations, player, finalDungeon, rows, cols, wrapping);
          int noOfOtyughsInOnePath = gameOtyughDetails.getNoOfOtyughsInOnePath();
          int noOfOtyughsInTwoPath = gameOtyughDetails.getNoOfOtyughsInTwoPath();
          nearByOtyughs = gameOtyughDetails.getNearByOtyughs();


          boolean isCave = updateGame.isCave(playerEndAt, caves);

          numberOfArrowsHere = updateGame.getNumberOfArrowsInCurrentLocation(
                  arrowLocations, playerCurrentlyAt);

          // validateAndUpdateGame(treasureCollected, playerStartAt,
          // treasureDetailsOfPlayerCurrentCave, playerPossibleLocations);
          updateGame.validateAndUpdateGame(numberOfArrowsHere,
                  noOfArrowsCollected, treasureCollected,
                  playerCurrentlyAt, treasureDetailsOfPlayerCurrentCave,
                  playerPossibleLocations, isCave);

          if (numberOfArrowsHere > 0) {
            System.out.println("You find " + numberOfArrowsHere + " arrows here. ");
            dungeonViewV3.showArrowsInformation("You find " +
                    numberOfArrowsHere + " arrows here. ", true);
          } else {
            dungeonViewV3.showArrowsInformation(null, false);
          }
          if (treasureDetailsOfPlayerCurrentCave.hasDiamond()
                  || treasureDetailsOfPlayerCurrentCave.hasRuby()
                  || treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
            String treasureInfo = "You find treasure here. ";
            System.out.println("You find treasure here. ");
            if (treasureDetailsOfPlayerCurrentCave.hasDiamond()) {
              System.out.println("Diamond : 1");
              treasureInfo += " Diamond : 1 ";
            }
            if (treasureDetailsOfPlayerCurrentCave.hasRuby()) {
              System.out.println("Ruby : 1");
              treasureInfo += " Ruby : 1 ";
            }
            if (treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
              System.out.println("Sapphire : 1");
              treasureInfo += " Sapphire : 1";
            }
            dungeonViewV3.showTreasureInformaion(treasureInfo, true);
          } else {
            dungeonViewV3.showTreasureInformaion(null, false);
          }


          if (noOfOtyughsInOnePath > 0 || noOfOtyughsInTwoPath > 1) {
            System.out.println("You smell something terrible nearby");
            dungeonViewV3.showOtuyghWarningSmellLess(null, false);
            dungeonViewV3.showOtuyghWarningSmellHigh(""
                   + "You smell something terrible nearby", true);
          } else if (noOfOtyughsInTwoPath == 1) {
            System.out.println("You smell something little nearby");
            dungeonViewV3.showOtuyghWarningSmellLess(""
                   + "You smell something little nearby", true);
            dungeonViewV3.showOtuyghWarningSmellHigh(null, false);
          } else {
            dungeonViewV3.showOtuyghWarningSmellLess(null, false);
            dungeonViewV3.showOtuyghWarningSmellHigh(null, false);
          }

          System.out.println("Move, Pickup, or Shoot (M-P-S)?");


          int otyughDecisionInPlayerMovedLocation =
                  updateGame.isPlayerEatenByOtyugh(otyughLocations,
                          playerCurrentlyAt, loopNumber);
          if (otyughDecisionInPlayerMovedLocation == 1) {
            System.out.println("Chomp, chomp, chomp, you are eaten by an Otyugh!");
            System.out.println("Better luck next time");
            System.out.println("#................Game over................#");
            dungeonViewV3.showUserError("Chomp, chomp, chomp,"
                    + " you are eaten by an Otyugh!, better luck next time, game over.");
            dungeonModelV3.gameOver("<html>Chomp, chomp, chomp, "
                    + "you are eaten by an Otyugh!<br/>Better luck next time<br/>"
                    + "Game over.</html>");
            return;
          } else if (otyughDecisionInPlayerMovedLocation == 2) {
            System.out.println("Chomp, chomp, chomp, "
                    + "you are eaten by an Otyugh which has 50% health");
            System.out.println("Better luck next time");
            System.out.println("#................Game over................#");
            dungeonViewV3.showUserError("Chomp, chomp, chomp, "
                    + "you are eaten by an Otyugh which has 50% health,"
                    + " better luck next time, Game over.");
            dungeonModelV3.gameOver("<html>Chomp, chomp, chomp, "
                    + "you are eaten by an Otyugh which has 50% health<br/>"
                    + "Better luck next time<br/>Game over.</html>");
            return;
          } else if (otyughDecisionInPlayerMovedLocation == 3) {
            System.out.println("You are lucky");
            System.out.println("You escaped from Otyugh which has "
                    + "50% health at your current location ("
                    + playerCurrentlyAt.getX() + ", " + playerCurrentlyAt.getY() + ")");
            dungeonViewV3.showSpecialInfo("You are lucky, you escaped "
                    + "from Otyugh which has 50% health at your current location ("
                    + playerCurrentlyAt.getX() + ", " + playerCurrentlyAt.getY() + ")");
          }

          if (nextMove.equals("N")) {
            dungeonModelV3.moveNorth(dungeonViewV3, gameDataV3);
          } else if (nextMove.equals("E")) {
            dungeonModelV3.moveEast(dungeonViewV3, gameDataV3);
          } else if (nextMove.equals("S")) {
            dungeonModelV3.moveSouth(dungeonViewV3, gameDataV3);
          } else if (nextMove.equals("W")) {
            dungeonModelV3.moveWest(dungeonViewV3, gameDataV3);
          }

        } else if (userInput.equals("P")) {
          treasureDetailsOfPlayerCurrentCave = player.getTreasureDetailsOfCave(treasureCaves,
                  playerCurrentlyAt);

          //Code to Pick up Arrows
          int noOfArrowsInCurrentLocation = 0;
          // ArrayList<ArrowLocations> arrowLocations
          for (int i = 0; i < arrowLocations.size(); i++) {
            ArrowLocations eachArrowLocation = arrowLocations.get(i);
            if (eachArrowLocation.getLocation().getX() == playerCurrentlyAt.getX()
                    && eachArrowLocation.getLocation().getY() == playerCurrentlyAt.getY()) {
              noOfArrowsInCurrentLocation = eachArrowLocation.getNoOfArrows();
              noOfArrowsCollected += eachArrowLocation.getNoOfArrows();
              eachArrowLocation.setNoOfArrows(0);
              break;
            }
          }
          System.out.println("You pick up " + noOfArrowsInCurrentLocation + " Arrow(s)");
          dungeonViewV3.showArrowPickUpInformation("You pick up "
                  + noOfArrowsInCurrentLocation + " Arrow(s)", true);
          dungeonViewV3.showPlayerArrowInformation(noOfArrowsCollected);

          //Code to Pick up Treasure
          int dimondPicked = 0;
          int rubyPicked = 0;
          int sapphiePicked = 0;
          // treasureDetailsOfPlayerCurrentCave =
          // player.getTreasureDetailsOfCave(treasureCaves, playerCurrentlyAt);
          if (treasureDetailsOfPlayerCurrentCave.hasDiamond()) {
            treasureCollected.addDiamond(1);
            dimondPicked = 1;
            treasureDetailsOfPlayerCurrentCave.setHasDiamond(false);
          }
          if (treasureDetailsOfPlayerCurrentCave.hasRuby()) {
            treasureCollected.addRuby(1);
            rubyPicked = 1;
            treasureDetailsOfPlayerCurrentCave.setHasRuby(false);
          }
          if (treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
            treasureCollected.addSapphire(1);
            sapphiePicked = 1;
            treasureDetailsOfPlayerCurrentCave.setHasSapphire(false);
          }
          System.out.println("You pick up " + dimondPicked + " Diamonds, "
                  + rubyPicked + " Rubies, "
                  + sapphiePicked + " Sapphires");
          dungeonViewV3.showTreasurePickUpInformation("You pick up "
                  + dimondPicked + " Diamonds, " + rubyPicked + " Rubies, "
                  + sapphiePicked + " Sapphires", true);
        } else if (userInput.equals("S")) {
          if (noOfArrowsCollected <= 0) {
            System.out.println("You are out of arrows, explore to find more");
            dungeonViewV3.showShootStatusInfo(
                    "You are out of arrows, explore to find more", true);
            return;
          }

          if (isCave) {
            System.out.println("Where to?, you are in a cave at (" + playerCurrentlyAt.getX()
                    + ", "
                    + playerCurrentlyAt.getY() + ") and possible directions are (North["
                    + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasWest()
                    + ")");
          } else {
            System.out.println("Where to?, you are in a tunnel at ("
                    + playerCurrentlyAt.getX() + ", "
                    + playerCurrentlyAt.getY() + ") and possible directions are (North["
                    + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + " || West[" + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasWest()
                    + ")");
          }

          System.out.println("\nPlease input the direction to shoot [case-sensitive] (N/E/S/W):");

          System.out.println("No. of caves (1-5)?");

          // Reducing one arrow from stock as used by player
          noOfArrowsCollected--;
          dungeonViewV3.showPlayerArrowInformation(noOfArrowsCollected);

          gameOtyughDetails = updateGame.getGameOtyughDetails(
                  otyughLocations, playerPossibleLocations, player,
                  finalDungeon, rows, cols, wrapping);

          nearByOtyughs = gameOtyughDetails.getNearByOtyughs();
          boolean isOtyughAttached = false;
          for (int i = 0; i < nearByOtyughs.size(); i++) {
            NearbyOtyughs eachNearByOtyughs = nearByOtyughs.get(i);
            if (inputDistance == eachNearByOtyughs.getNumberPathToOtyugh()
                    && eachNearByOtyughs.getDirectionTowardsOtyugh().toString().equals(
                    shootDirection)) {
              //Reduce Otyugh health by 50% if not attached,
              // if already attacked then kill it by making life 0%
              for (int j = 0; j < otyughLocations.size(); j++) {
                Otyugh eachOtyughLocation = otyughLocations.get(j);
                if (eachNearByOtyughs.getOtyughLocation().getX()
                        == eachOtyughLocation.getOtyughLocation().getX()
                        && eachNearByOtyughs.getOtyughLocation().getY()
                        == eachOtyughLocation.getOtyughLocation().getY()
                        && eachOtyughLocation.getOtyughHealth() > 0) {
                  if (eachOtyughLocation.getOtyughHealth() == 100) {
                    eachOtyughLocation.setOtyughHealth(50);
                    System.out.println("You hear a great howl in the distance");
                    dungeonViewV3.showShootStatusInfo(
                            "You hear a great howl in the distance", true);
                  } else {
                    eachOtyughLocation.setOtyughHealth(0);
                    System.out.println("You killed the Otyugh, present in the location ("
                            + eachOtyughLocation.getOtyughLocation().getX() + ", "
                            + eachOtyughLocation.getOtyughLocation().getY() + ")");
                    dungeonViewV3.showShootStatusInfo(
                            "You killed the Otyugh, present in the location ("
                                    + eachOtyughLocation.getOtyughLocation().getX() + ", "
                                    + eachOtyughLocation.getOtyughLocation().getY() + ")", true);
                  }
                  isOtyughAttached = true;
                  break;
                }
              }
            }
          }
          if (!isOtyughAttached) {
            System.out.println("You shoot an arrow into the darkness");
            dungeonViewV3.showShootStatusInfo(
                    "You shoot an arrow into the darkness", true);
          }

          if (noOfArrowsCollected == 0) {
            System.out.println("You are out of arrows, explore to find more");
            dungeonViewV3.showShootStatusInfo(
                    "You are out of arrows, explore to find more", true);
          }
        }

        if (player.isDestinationReached(playerCurrentlyAt, playerEndAt)) {
          System.out.println("Congratulations.....!!!");
          System.out.println("You have successfully reached the destination.");

          System.out.println("#................Game completed................#");
          //break;
        }

        loopNumber++;

      }
    });

    frame.setSize(1200, 600);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(true);
    frame.setBackground(Color.green);
    frame.setVisible(true);
  }

  //https://coderanch.com/t/342364/java/JScrollPane-JTextArea-Disabling-Keyboard-Events

  /**
   * Method to disable key movements for scrollbar.
   *
   * @param im input map
   */
  static void disableArrowKeys(InputMap im) {
    String[] keystrokeNames = {"UP", "DOWN", "LEFT", "RIGHT"};
    for (int i = 0; i < keystrokeNames.length; ++i) {
      im.put(KeyStroke.getKeyStroke(keystrokeNames[i]), "none");
    }
  }
}
