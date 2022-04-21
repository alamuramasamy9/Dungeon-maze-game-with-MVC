package com.dungeon.data;

/**
 * The class to find the otyughs located nearby.
 * The path to the otyugh is calculated as 1 and 2 paths are required.
 * getters and setts for the location and direction are set.
 *
 */

public class NearbyOtyughs {

  private int numberPathToOtyugh;
  private Node otyughLocation;
  private Directions directionTowardsOtyugh;
  private int otyughLife;

  /**
   * Constructor of the nearby otyugh class.
   *
   * @param inumberPathToOtyugh     path to the otyugh
   * @param iotyughLocation         location Node of otyugh
   * @param idirectionTowardsOtyugh Direction of otyugh with respect to location
   * @param iotyughLife             Life of otyugh (100 / 50)
   */

  public NearbyOtyughs(int inumberPathToOtyugh, Node iotyughLocation,
                       Directions idirectionTowardsOtyugh, int iotyughLife) {
    this.numberPathToOtyugh = inumberPathToOtyugh;
    this.otyughLocation = iotyughLocation;
    this.directionTowardsOtyugh = idirectionTowardsOtyugh;
    this.otyughLife = iotyughLife;
  }

  /**
   * get number of positions to otyugh.
   *
   * @return the number
   */

  public int getNumberPathToOtyugh() {
    return numberPathToOtyugh;
  }

  /**
   * set number of positions to otyugh.
   *
   * @param numberPathToOtyugh number of positions
   */
  public void setNumberPathToOtyugh(int numberPathToOtyugh) {
    this.numberPathToOtyugh = numberPathToOtyugh;
  }

  /**
   * get location of the otyugh.
   *
   * @return location Node
   */

  public Node getOtyughLocation() {
    return otyughLocation;
  }

  /**
   * set location of otyugh.
   *
   * @param otyughLocation location node
   */
  public void setOtyughLocation(Node otyughLocation) {
    this.otyughLocation = otyughLocation;
  }

  /**
   * get direction at which otyugh lies in.
   *
   * @return Direction
   */
  public Directions getDirectionTowardsOtyugh() {
    return directionTowardsOtyugh;
  }

  /**
   * set direction at which otyugh lies in.
   *
   * @param directionTowardsOtyugh Direction
   */
  public void setDirectionTowardsOtyugh(Directions directionTowardsOtyugh) {
    this.directionTowardsOtyugh = directionTowardsOtyugh;
  }

  /**
   * get the life of the otyugh left.
   *
   * @return life
   */
  public int getOtyughLife() {
    return otyughLife;
  }

  /**
   * set the life ofthe otyugh.
   *
   * @param otyughLife life
   */
  public void setOtyughLife(int otyughLife) {
    this.otyughLife = otyughLife;
  }

}
