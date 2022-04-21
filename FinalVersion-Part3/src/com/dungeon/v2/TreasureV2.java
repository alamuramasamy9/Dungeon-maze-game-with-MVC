package com.dungeon.v2;

import java.util.ArrayList;
import java.util.HashMap;

import com.dungeon.Player;
import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;

/**
 * The Jewels class contains getters for the dungeons, start and end location.
 * It also has methods for the treasure details.
 * This class is used in game model.
 */

public class TreasureV2 {

  ArrayList<Node> caves;
  Node playerStartAt;
  Node playerEndAt;
  int noofOtyughs;
  PlayerLocations playerPossibleLocations;
  Player player;
  ArrayList<Path> finalDungeon;
  ArrayList<HashMap<Node, Treasure>> treasureCaves;

  /**
   * The constructor for the jewels cclass.
   *
   * @param caves                   list of caves
   * @param playerStartAt           player start location
   * @param playerEndAt             player end location
   * @param noofOtyughs             number of otyughs allocated
   * @param playerPossibleLocations player possible locations
   * @param player                  player
   * @param finalDungeon            final dungeon
   * @param treasureCaves           caves with treasure
   */
  public TreasureV2(ArrayList<Node> caves, Node playerStartAt,
                    Node playerEndAt, int noofOtyughs,
                    PlayerLocations playerPossibleLocations,
                    Player player, ArrayList<Path> finalDungeon,
                    ArrayList<HashMap<Node, Treasure>> treasureCaves) {
    super();
    this.caves = caves;
    this.playerStartAt = playerStartAt;
    this.playerEndAt = playerEndAt;
    this.noofOtyughs = noofOtyughs;
    this.playerPossibleLocations = playerPossibleLocations;
    this.player = player;
    this.finalDungeon = finalDungeon;
    this.treasureCaves = treasureCaves;
  }

  /**
   * get the caves in the dungeon.
   *
   * @return list of cave Nodes
   */

  public ArrayList<Node> getCaves() {
    return caves;
  }

  /**
   * start location of the player.
   *
   * @return the Node at which player is starting.
   */

  public Node getPlayerStartAt() {
    return playerStartAt;
  }

  /**
   * set start location of player.
   *
   * @param playerStartAt location node
   */

  public void setPlayerStartAt(Node playerStartAt) {

    this.playerStartAt = playerStartAt;
  }

  /**
   * set end location of the player.
   *
   * @param playerEndAt Node which player needs to reach.
   */

  public void setPlayerEndAt(Node playerEndAt) {

    this.playerEndAt = playerEndAt;
  }

  public Node getPlayerEndAt() {
    return playerEndAt;
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


}
