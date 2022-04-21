package com.dungeon.data;

/**
 * Otyugh class is used to allocate the otyugh to the right location and health of otyugh.
 * The getters and setters are defined in this class.
 */

public class Otyugh implements OtyughInterface {

  private Node otyughLocation;
  private int otyughHealth;

  /**
   * The constucot of the otyugh class is defined.
   *
   * @param iotyughLocation location of otyugh
   * @param iotyughHealth   health of otyugh
   */
  public Otyugh(Node iotyughLocation, int iotyughHealth) {
    this.otyughLocation = iotyughLocation;
    this.otyughHealth = iotyughHealth;
  }

  /**
   * get location Node of otyugh.
   *
   * @return location Node
   */
  @Override
  public Node getOtyughLocation() {
    return otyughLocation;
  }

  /**
   * set location Node of otyugh.
   *
   * @param otyughLocation location Node
   */

  @Override
  public void setOtyughLocation(Node otyughLocation) {
    this.otyughLocation = otyughLocation;
  }

  /**
   * get health of otyugh.
   *
   * @return health
   */

  @Override
  public int getOtyughHealth() {
    return otyughHealth;
  }

  /**
   * set location Node of otyugh.
   *
   * @param otyughHealth health
   */

  @Override
  public void setOtyughHealth(int otyughHealth) {
    this.otyughHealth = otyughHealth;
  }


}
