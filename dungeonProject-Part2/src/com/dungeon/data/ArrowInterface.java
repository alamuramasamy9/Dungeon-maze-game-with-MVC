package com.dungeon.data;

/**
 * Interface for allocating the arrows and storing the number of arrows in each location.
 * It has the getters and setters for the arrows in the caves.
 * It is implemented by the Arrow locations class.
 */

public interface ArrowInterface {

  /**
   * Get location of arrow.
   *
   * @return location of arrow
   */

  Node getLocation();

  /**
   * set the location of arrow.
   *
   * @param location location of arrow
   */

  void setLocation(Node location);

  /**
   * get number of arrows in location.
   *
   * @return number of arrows
   */


  int getNoOfArrows();

  /**
   * set number of arrows in location.
   *
   * @param noOfArrows number of arrow
   */

  void setNoOfArrows(int noOfArrows);
}
