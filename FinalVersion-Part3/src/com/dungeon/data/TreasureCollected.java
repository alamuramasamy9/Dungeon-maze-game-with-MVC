package com.dungeon.data;

/**
 * This is a java class for representing the Treasure Collected by the Player.
 * The add treasure methods for all three treasures to increment count.
 * The getters and setters for the three treasures to see if it is collected.
 * The constructor of the class is also defined.
 */

public class TreasureCollected {

  int diamondCount;
  int rubyCount;
  int sapphireCount;

  /**
   * The constructor of the Treasure Collected class.
   *
   * @param idiamondCount  the number of diamonds collected
   * @param irubyCount     the number of rubies collected
   * @param isapphireCount the number of sapphires collected
   */

  public TreasureCollected(int idiamondCount, int irubyCount, int isapphireCount) {
    this.diamondCount = idiamondCount;
    this.rubyCount = irubyCount;
    this.sapphireCount = isapphireCount;
  }

  /**
   * The add diamond method to increment the number of diamonds when it is collected by the player.
   *
   * @param diamondCountToAdd value of diamonds collected
   */

  public void addDiamond(int diamondCountToAdd) {
    this.diamondCount += diamondCountToAdd;
  }

  /**
   * The add ruby method to increment the number of rubies when it is collected by the player.
   *
   * @param rubyCountToAdd value of rubies collected
   */

  public void addRuby(int rubyCountToAdd) {
    this.rubyCount += rubyCountToAdd;
  }

  /**
   * The add sapphire method to increment the number of sapphire when it is collected by the player.
   *
   * @param sapphireCountToAdd value of sapphire collected
   */

  public void addSapphire(int sapphireCountToAdd) {
    this.sapphireCount += sapphireCountToAdd;
  }

  /**
   * The get diamond count method to get the number of diamonds collected by player.
   *
   * @return value of diamonds collected
   */

  public int getDiamondCount() {
    return diamondCount;
  }

  /**
   * The set diamond count method to set the number of diamonds collected by player.
   *
   * @param diamondCount is the value of number of diamonds that is passed
   */

  public void setDiamondCount(int diamondCount) {
    this.diamondCount = diamondCount;
  }

  /**
   * The get ruby count method to get the number of ruby collected by player.
   *
   * @return value of ruby collected
   */

  public int getRubyCount() {
    return rubyCount;
  }

  /**
   * The set ruby count method to set the number of ruby collected by player.
   *
   * @param rubyCount is the value of number of ruby that is passed
   */


  public void setRubyCount(int rubyCount) {
    this.rubyCount = rubyCount;
  }

  /**
   * The get sapphire count method to get the number of sapphire collected by player.
   *
   * @return value of sapphire collected
   */

  public int getSapphireCount() {
    return sapphireCount;
  }

  /**
   * The set sapphire count method to set the number of sapphire collected by player.
   *
   * @param sapphireCount is the value of number of sapphire that is passed
   */


  public void setSapphireCount(int sapphireCount) {
    this.sapphireCount = sapphireCount;
  }

}
