package com.dungeon.data;

/**
 * Class for allocating the arrows and storing the number of arrows in each location.
 * It has the getters and setters for the arrows in the caves.
 */

public class ArrowLocations implements ArrowInterface {

  private Node location;
  private int noOfArrows;

  public ArrowLocations(Node ilocation, int inoOfArrows) {
    this.location = ilocation;
    this.noOfArrows = inoOfArrows;
  }

  /**
   * Get location of arrow.
   *
   * @return location of arrow
   */

  @Override
  public Node getLocation() {
    return location;
  }

  /**
   * set the location of arrow.
   *
   * @param location location of arrow
   */
  @Override
  public void setLocation(Node location) {
    this.location = location;
  }

  /**
   * get number of arrows in location.
   *
   * @return number of arrows
   */

  @Override
  public int getNoOfArrows() {
    return noOfArrows;
  }

  /**
   * set number of arrows in location.
   *
   * @param noOfArrows number of arrow
   */

  @Override
  public void setNoOfArrows(int noOfArrows) {
    this.noOfArrows = noOfArrows;
  }

}
