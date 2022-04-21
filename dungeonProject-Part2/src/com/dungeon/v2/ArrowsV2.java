package com.dungeon.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.dungeon.Player;
import com.dungeon.UpdateGame;
import com.dungeon.data.ArrowLocations;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;

/**
 * The arrows class contains the getters and setters for player location, otyughs and arrows.
 * The player locations are also specified.
 * This class is used in game model.
 */

public class ArrowsV2 {

  int rows;
  int cols;
  boolean wrapping;
  UpdateGame updateGame;
  ArrayList<Otyugh> otyughLocations;
  PlayerLocations playerPossibleLocations;
  Player player;
  ArrayList<Path> finalDungeon;
  ArrayList<HashMap<Node, Treasure>> treasureCaves;
  Node playerCurrentlyAt;
  Node playerEndAt;
  ArrayList<Node> caves;
  ArrayList<ArrowLocations> arrowLocations;
  Scanner inputScanner;
  public int numberofArrows = 3;

  /**
   * Constructor of the Arrows class.
   *
   * @param rows                    number of rows
   * @param cols                    number cols
   * @param wrapping                number wrapping
   * @param updateGame              update agme
   * @param otyughLocations         list of otyugh locations
   * @param playerPossibleLocations player locations
   * @param player                  player
   * @param finalDungeon            final dungeon with caves and tunnels
   * @param treasureCaves           caves of treasure
   * @param playerCurrentlyAt       player current location
   * @param playerEndAt             end location player much reach
   * @param caves                   caves in dungeon
   * @param arrowLocations          locations with arrows
   * @param inputScanner            scanner
   */

  public ArrowsV2(int rows, int cols, boolean wrapping, UpdateGame updateGame,
                  ArrayList<Otyugh> otyughLocations,
                  PlayerLocations playerPossibleLocations, Player player,
                  ArrayList<Path> finalDungeon,
                  ArrayList<HashMap<Node, Treasure>> treasureCaves,
                  Node playerCurrentlyAt, Node playerEndAt,
                  ArrayList<Node> caves, ArrayList<ArrowLocations> arrowLocations,
                  Scanner inputScanner) {
    super();
    this.rows = rows;
    this.cols = cols;
    this.wrapping = wrapping;
    this.updateGame = updateGame;
    this.otyughLocations = otyughLocations;
    this.playerPossibleLocations = playerPossibleLocations;
    this.player = player;
    this.finalDungeon = finalDungeon;
    this.treasureCaves = treasureCaves;
    this.playerCurrentlyAt = playerCurrentlyAt;
    this.playerEndAt = playerEndAt;
    this.caves = caves;
    this.arrowLocations = arrowLocations;
    this.inputScanner = inputScanner;
  }


  /**
   * details of game are updated with otyughs.
   *
   * @return update game
   */

  public UpdateGame getProject2() {
    return updateGame;
  }

  /**
   * get the location of otyughs.
   *
   * @return list of nodes
   */

  public ArrayList<Otyugh> getOtyughLocations() {
    return otyughLocations;
  }

  /**
   * get possible locations of player.
   *
   * @return player location
   */

  public PlayerLocations getPlayerPossibleLocations() {
    return playerPossibleLocations;
  }

  /**
   * get Player method.
   *
   * @return player
   */

  public Player getPlayer() {
    return player;
  }

  /**
   * get the final dungeon with caves and tunnels.
   *
   * @return final dungeon.
   */


  public ArrayList<Path> getFinalDungeon() {
    return finalDungeon;
  }

  /**
   * get list of caves with treasure in it.
   *
   * @return list of nodes
   */

  public ArrayList<HashMap<Node, Treasure>> getTreasureCaves() {
    return treasureCaves;
  }

  /**
   * current location of the player.
   *
   * @return the Node at which player is located.
   */

  public Node getPlayerCurrentlyAt() {
    return playerCurrentlyAt;
  }

  /**
   * end location of the player.
   *
   * @return the Node which player needs to reach.
   */

  public Node getPlayerEndAt() {
    return playerEndAt;
  }

  /**
   * get the list of caves in dungeon.
   *
   * @return list of caves
   */

  public ArrayList<Node> getCaves() {
    return caves;
  }

  /**
   * get list of locations in which arrow is present.
   *
   * @return arrow locations
   */

  public ArrayList<ArrowLocations> getArrowLocations() {
    return arrowLocations;
  }

  /**
   * Scanner to get player input for moves.
   *
   * @return scanner
   */

  public Scanner getInputScanner() {
    return inputScanner;
  }

  /**
   * set otyugh location list.
   *
   * @param otyughLocations list of location nodes.
   */

  public void setOtyughLocations(ArrayList<Otyugh> otyughLocations) {
    this.otyughLocations = otyughLocations;
  }

  /**
   * set player current location.
   *
   * @param playerCurrentlyAt location node of player
   */

  public void setPlayerCurrentlyAt(Node playerCurrentlyAt) {
    this.playerCurrentlyAt = playerCurrentlyAt;
  }

}
