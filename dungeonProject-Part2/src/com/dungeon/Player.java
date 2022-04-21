package com.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.data.TreasureCollected;

/**
 * This is a java class for representing the Player in the game.
 * The player treasure collection is done in this class.
 * The moves of the player is also updated in this class.
 * It is checked if the end has been reached usign these methods.
 * The PlayerInterface is implemented.
 */

public class Player implements PlayerInterface {

  /**
   * checking if the node the player is at has treasure.
   *
   * @param treasureCaves caves that have treasure
   * @param playerStartAt the location node of the player
   * @return the treasure that is collected
   */


  @Override
  public TreasureCollected checkPlayerCaveHasTreasure(ArrayList<HashMap<Node,
          Treasure>> treasureCaves, Node playerStartAt) {
    TreasureCollected treasureCollected = new TreasureCollected(0, 0, 0);
    Treasure treasureDetailsOfPlayerCurrentCave
            = getTreasureDetailsOfCave(treasureCaves, playerStartAt);
    if (treasureDetailsOfPlayerCurrentCave.hasDiamond()) {
      treasureCollected.addDiamond(1);
    }
    if (treasureDetailsOfPlayerCurrentCave.hasRuby()) {
      treasureCollected.addRuby(1);
    }
    if (treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
      treasureCollected.addSapphire(1);
    }
    return treasureCollected;
  }

  /**
   * checking the treasure of the cave.
   *
   * @param treasureCaves list of caves that have treasure.
   * @param cave          the current cave node.
   * @return the treasure if present else assign all to false
   */

  @Override
  public Treasure getTreasureDetailsOfCave(ArrayList<HashMap<Node, Treasure>> treasureCaves,
                                           Node cave) {
    for (int i = 0; i < treasureCaves.size(); i++) {
      HashMap<Node, Treasure> eachTreasureCave = treasureCaves.get(i);
      Set<Node> eachCave = eachTreasureCave.keySet();
      for (Node node : eachCave) {
        if (node.getX() == cave.getX() && node.getY() == cave.getY()) {
          return eachTreasureCave.get(node);
        }
      }
    }
    return new Treasure(false, false, false);
  }

  /**
   * getting the node to move given the input.
   *
   * @param playerPossibleLocations possible directions for the player to move in.
   * @param nextMove                string passed to move the player
   * @return node to be moved to
   */

  @Override
  public Node getNextMoveCave(PlayerLocations playerPossibleLocations, String nextMove) {
    if (nextMove.equals("N")) {
      return playerPossibleLocations.getNorth();
    } else if (nextMove.equals("E")) {
      return playerPossibleLocations.getEast();
    } else if (nextMove.equals("S")) {
      return playerPossibleLocations.getSouth();
    } else if (nextMove.equals("W")) {
      return playerPossibleLocations.getWest();
    }
    return null;
  }

  /**
   * method to get the possible moves of the player at a location.
   *
   * @param rows         number of rows
   * @param cols         number of columns
   * @param finalDungeon the dungeon with all caves and tunnels
   * @param playerAt     current location of player
   * @param wrapping     wrapping detail of player
   * @return return possible loations
   */

  @Override
  public PlayerLocations getPossibleMoves(int rows, int cols, ArrayList<Path> finalDungeon,
                                          Node playerAt, boolean wrapping) {

    int sX = playerAt.getX();
    int sY = playerAt.getY();
    boolean canMoveWest = false;
    boolean canMoveNorth = false;
    boolean canMoveEast = false;
    boolean canMoveSouth = false;

    Node north = null;
    Node east = null;
    Node south = null;
    Node west = null;

    // TOP (NORTH) - Is existing
    north = new Node(sX, sY - 1);
    if (sY - 1 != -1) {
      // Path pathTop=new Path(new Edge(sX,sY-1), new Edge(sX,sY));
      Node pathTopStart = new Node(sX, sY - 1);
      Node pathTopEnd = new Node(sX, sY);

      for (int j = 0; j < finalDungeon.size(); j++) {
        Path eachDungeonPath = finalDungeon.get(j);
        Node eachDungeonPathStartNode = eachDungeonPath.getStart();
        Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
        // Checking the identified pathTop is present in Dungeon
        if (eachDungeonPathStartNode.getX() == pathTopStart.getX()
                && eachDungeonPathStartNode.getY() == pathTopStart.getY()
                && eachDungeonPathEndNode.getX() == pathTopEnd.getX()
                && eachDungeonPathEndNode.getY() == pathTopEnd.getY()) {
          canMoveNorth = true;
          break;
        }
      }
    } else if (wrapping) {
      north = new Node(sX, rows - 1);
      canMoveNorth = true;
    }

    // RIGHT (EAST) - Is existing
    east = new Node(sX + 1, sY);
    if (sX + 1 < cols) {
      // Path pathRight=new Path(new Edge(sX,sY), new Edge(sX+1,sY));
      Node pathRightStart = new Node(sX, sY);
      Node pathRightEnd = new Node(sX + 1, sY);

      for (int j = 0; j < finalDungeon.size(); j++) {
        Path eachDungeonPath = finalDungeon.get(j);
        Node eachDungeonPathStartNode = eachDungeonPath.getStart();
        Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
        // Checking the identified pathTop is present in Dungeon
        if (eachDungeonPathStartNode.getX() == pathRightStart.getX()
                && eachDungeonPathStartNode.getY() == pathRightStart.getY()
                && eachDungeonPathEndNode.getX() == pathRightEnd.getX()
                && eachDungeonPathEndNode.getY() == pathRightEnd.getY()) {
          canMoveEast = true;
          break;
        }
      }
    } else if (wrapping) {
      east = new Node(0, sY);
      canMoveEast = true;
    }

    // DOWN (SOUTH) - Is existing
    south = new Node(sX, sY + 1);
    if (sY + 1 < rows) {
      // Path pathDown=new Path(new Edge(sX,sY), new Edge(sX,sY+1));
      Node pathDownStart = new Node(sX, sY);
      Node pathDownEnd = new Node(sX, sY + 1);

      for (int j = 0; j < finalDungeon.size(); j++) {
        Path eachDungeonPath = finalDungeon.get(j);
        Node eachDungeonPathStartNode = eachDungeonPath.getStart();
        Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
        // Checking the identified pathTop is present in Dungeon
        if (eachDungeonPathStartNode.getX() == pathDownStart.getX()
                && eachDungeonPathStartNode.getY() == pathDownStart.getY()
                && eachDungeonPathEndNode.getX() == pathDownEnd.getX()
                && eachDungeonPathEndNode.getY() == pathDownEnd.getY()) {
          canMoveSouth = true;
          break;
        }
      }
    } else if (wrapping) {
      south = new Node(sX, 0);
      canMoveSouth = true;
    }

    // LEFT (WEST) - Is existing
    west = new Node(sX - 1, sY);
    if (sX - 1 != -1) {
      // Path pathLeft=new Path(new Edge(sX-1,sY), new Edge(sX,sY));
      Node pathLeftStart = new Node(sX - 1, sY);
      Node pathLeftEnd = new Node(sX, sY);

      for (int j = 0; j < finalDungeon.size(); j++) {
        Path eachDungeonPath = finalDungeon.get(j);
        Node eachDungeonPathStartNode = eachDungeonPath.getStart();
        Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
        // Checking the identified pathTop is present in Dungeon
        if (eachDungeonPathStartNode.getX() == pathLeftStart.getX()
                && eachDungeonPathStartNode.getY() == pathLeftStart.getY()
                && eachDungeonPathEndNode.getX() == pathLeftEnd.getX()
                && eachDungeonPathEndNode.getY() == pathLeftEnd.getY()) {
          canMoveWest = true;
          break;
        }
      }
    } else if (wrapping) {
      west = new Node(cols - 1, sY);
      canMoveWest = true;
    }
    return new PlayerLocations(canMoveWest, canMoveNorth, canMoveEast, canMoveSouth,
            west, north, east, south);
  }

  /**
   * method to check if destination is reached.
   *
   * @param currentCave current location of player
   * @param playerEndAt end generated randomly
   * @return true if it is end else false
   */

  @Override
  public boolean isDestinationReached(Node currentCave, Node playerEndAt) {
    return currentCave.getX() == playerEndAt.getX() && currentCave.getY() == playerEndAt.getY();
  }

  /**
   * player treasure description that is printed.
   *
   * @param treasureCollected treasure of player
   */

  public String getPlayerTreasureDetails(TreasureCollected treasureCollected) {
    return "Treasure collected so far : (Diamond - " + treasureCollected.getDiamondCount()
            + ", Ruby - " + treasureCollected.getRubyCount() + ", Sapphire - "
            + treasureCollected.getSapphireCount() + ")";
  }

  /**
   * player location description that is printed.
   *
   * @param playerCurrentlyAt       current location of player
   * @param playerPossibleLocations possible moves of player
   */

  public String getPlayerLocationDetails(
          Node playerCurrentlyAt, PlayerLocations playerPossibleLocations) {
    return "The player is currently at (" + playerCurrentlyAt.getX()
            + ", " + playerCurrentlyAt.getY()
            + ") and possible moves are (North["
            + playerPossibleLocations.getNorth().getX() + ", "
            + playerPossibleLocations.getNorth().getY() + "]:"
            + playerPossibleLocations.hasNorth() + " || East["
            + playerPossibleLocations.getEast().getX() + ", "
            + playerPossibleLocations.getEast().getY() + "]:"
            + playerPossibleLocations.hasEast() + " || South["
            + playerPossibleLocations.getSouth().getX() + ", "
            + playerPossibleLocations.getSouth().getY() + "]:"
            + playerPossibleLocations.hasSouth() + " || West["
            + playerPossibleLocations.getWest().getX() + ", "
            + playerPossibleLocations.getWest().getY() + "]:"
            + playerPossibleLocations.hasWest() + ")";
  }

}
