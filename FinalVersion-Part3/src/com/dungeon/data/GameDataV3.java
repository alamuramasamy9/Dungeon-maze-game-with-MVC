package com.dungeon.data;

/**
 * This class contains the details of the game for implementing the view.
 * It includes player location, treasure details, arrow details, start and end location,
 * number of arrows collected and the current cave at which player is present.
 */

public class GameDataV3 {
  Node playerCurrentAt;
  TreasureCollected treasureCollected;
  int numberOfArrowsHere;
  Node playerStartingLoc;
  Node playerEndAt;
  int noOfArrowsCollected;
  Treasure treasureDetailsOfPlayerCurrentCave;

  /**
   * Constructor for the Game Data V3 class.
   *
   * @param playerCurrentAt                    player current location
   * @param treasureCollected                  treasure collected by the player
   * @param numberOfArrowsHere                 number of arrows in the current node
   * @param playerStartingLoc                  random starting location
   * @param playerEndAt                        random ending location
   * @param noOfArrowsCollected                number of arrows collected by player
   * @param treasureDetailsOfPlayerCurrentCave treasure in the current cave
   */
  public GameDataV3(Node playerCurrentAt, TreasureCollected treasureCollected,
                    int numberOfArrowsHere, Node playerStartingLoc, Node playerEndAt,
                    int noOfArrowsCollected, Treasure treasureDetailsOfPlayerCurrentCave) {
    super();
    this.playerCurrentAt = playerCurrentAt;
    this.treasureCollected = treasureCollected;
    this.numberOfArrowsHere = numberOfArrowsHere;
    this.playerStartingLoc = playerStartingLoc;
    this.playerEndAt = playerEndAt;
    this.noOfArrowsCollected = noOfArrowsCollected;
    this.treasureDetailsOfPlayerCurrentCave = treasureDetailsOfPlayerCurrentCave;
  }

  /**
   * get player's current location.
   *
   * @return player current location
   */
  public Node getPlayerCurrentAt() {
    return playerCurrentAt;
  }

  /**
   * set player's current location.
   *
   * @param playerCurrentAt player location to set
   */
  public void setPlayerCurrentAt(Node playerCurrentAt) {
    this.playerCurrentAt = playerCurrentAt;
  }

  /**
   * get treasure collected by the player.
   *
   * @return treasure collected by player
   */
  public TreasureCollected getTreasureCollected() {
    return treasureCollected;
  }

  /**
   * set treasure collected by player.
   *
   * @param treasureCollected treasure collected by player
   */
  public void setTreasureCollected(TreasureCollected treasureCollected) {
    this.treasureCollected = treasureCollected;
  }

  /**
   * get number of arrows in the current node.
   *
   * @return number of arrows in the node
   */
  public int getNumberOfArrowsHere() {
    return numberOfArrowsHere;
  }

  /**
   * set number of arrows in the current node.
   *
   * @param numberOfArrowsHere number of arrows in the node
   */
  public void setNumberOfArrowsHere(int numberOfArrowsHere) {
    this.numberOfArrowsHere = numberOfArrowsHere;
  }

  /**
   * get starting location of the player.
   *
   * @return start location
   */
  public Node getPlayerStartingLoc() {
    return playerStartingLoc;
  }

  /**
   * set starting location of the player.
   *
   * @param playerStartingLoc start location
   */
  public void setPlayerStartingLoc(Node playerStartingLoc) {
    this.playerStartingLoc = playerStartingLoc;
  }

  /**
   * get starting ending of the player.
   *
   * @return end location
   */

  public Node getPlayerEndAt() {
    return playerEndAt;
  }

  /**
   * set end location of the player.
   *
   * @param playerEndAt end location
   */

  public void setPlayerEndAt(Node playerEndAt) {
    this.playerEndAt = playerEndAt;
  }

  /**
   * get number of arrows cllected by the player.
   *
   * @return number of arrows
   */

  public int getNoOfArrowsCollected() {
    return noOfArrowsCollected;
  }

  /**
   * set number of arrows collected by the player.
   *
   * @param noOfArrowsCollected number of arrows
   */

  public void setNoOfArrowsCollected(int noOfArrowsCollected) {
    this.noOfArrowsCollected = noOfArrowsCollected;
  }

  /**
   * get treasure details of current cave.
   *
   * @return treasure in cave
   */
  public Treasure getTreasureDetailsOfPlayerCurrentCave() {
    return treasureDetailsOfPlayerCurrentCave;
  }

  /**
   * set treasure details of current cave.
   *
   * @param treasureDetailsOfPlayerCurrentCave treasure in cave
   */
  public void setTreasureDetailsOfPlayerCurrentCave(Treasure treasureDetailsOfPlayerCurrentCave) {
    this.treasureDetailsOfPlayerCurrentCave = treasureDetailsOfPlayerCurrentCave;
  }

}
