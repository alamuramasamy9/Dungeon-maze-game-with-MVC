package com.dungeon.data;

/**
 * This is a java class for representing the Player location of the nodes of the maze.
 * The getters and setters for the four directions are defined.
 * The getters and setters for the four directions to see if it is present.
 * The constructor of the class is also defined.
 */

public class PlayerLocations {

  private boolean hasWest = false;
  private boolean hasNorth = false;
  private boolean hasEast = false;
  private boolean hasSouth = false;

  private Node west;
  private Node north;
  private Node east;
  private Node south;

  /**
   * Constructor of the Player Locations class.
   *
   * @param ihasWest  boolean input to see if move to west is possible
   * @param ihasNorth boolean input to see if move to north is possible
   * @param ihasEast  boolean input to see if move to east is possible
   * @param ihasSouth boolean input to see if move to south is possible
   * @param iwest     Node object in west
   * @param inorth    Node object in north
   * @param ieast     Node object in east
   * @param isouth    Node object in south
   */

  public PlayerLocations(boolean ihasWest, boolean ihasNorth, boolean ihasEast,
                         boolean ihasSouth, Node iwest, Node inorth, Node ieast, Node isouth) {
    this.hasWest = ihasWest;
    this.hasNorth = ihasNorth;
    this.hasEast = ihasEast;
    this.hasSouth = ihasSouth;
    this.west = iwest;
    this.north = inorth;
    this.east = ieast;
    this.south = isouth;
  }

  /**
   * The get west method to get the node in the west.
   *
   * @return value of node in west if it exists
   */


  public Node getWest() {
    return west;
  }

  /**
   * The set west method to set the node in the west.
   *
   * @param west node object passed
   */

  public void setWest(Node west) {
    this.west = west;
  }

  /**
   * The get west method to get the node in the north.
   *
   * @return value of node in north if it exists
   */

  public Node getNorth() {
    return north;
  }

  /**
   * The set north method to set the node in the north.
   *
   * @param north node object passed
   */


  public void setNorth(Node north) {
    this.north = north;
  }

  /**
   * The get west method to get the node in the east.
   *
   * @return value of node in east if it exists
   */

  public Node getEast() {
    return east;
  }

  /**
   * The set east method to set the node in the east.
   *
   * @param east node object passed
   */


  public void setEast(Node east) {
    this.east = east;
  }

  /**
   * The get west method to get the node in the south.
   *
   * @return value of node in south if it exists
   */

  public Node getSouth() {
    return south;
  }

  /**
   * The set south method to set the node in the south.
   *
   * @param south node object passed
   */

  public void setSouth(Node south) {
    this.south = south;
  }

  /**
   * The has west method to see if it has a node in the west.
   *
   * @return boolean value
   */

  public boolean hasWest() {
    return hasWest;
  }

  /**
   * The set has west method to set boolean value.
   *
   * @param hasWest boolean that is passed
   */

  public void setHasWest(boolean hasWest) {
    this.hasWest = hasWest;
  }

  /**
   * The has north method to see if it has a node in the north.
   *
   * @return boolean value
   */

  public boolean hasNorth() {
    return hasNorth;
  }

  /**
   * The set has north method to set boolean value.
   *
   * @param hasNorth boolean that is passed
   */

  public void setHasNorth(boolean hasNorth) {
    this.hasNorth = hasNorth;
  }

  /**
   * The has east method to see if it has a node in the east.
   *
   * @return boolean value
   */

  public boolean hasEast() {
    return hasEast;
  }

  /**
   * The set has east method to set boolean value.
   *
   * @param hasEast boolean that is passed
   */

  public void setHasEast(boolean hasEast) {
    this.hasEast = hasEast;
  }

  /**
   * The has south method to see if it has a node in the south.
   *
   * @return boolean value
   */

  public boolean hasSouth() {
    return hasSouth;
  }

  /**
   * The set has south method to set boolean value.
   *
   * @param hasSouth boolean that is passed
   */

  public void setHasSouth(boolean hasSouth) {
    this.hasSouth = hasSouth;
  }
}
