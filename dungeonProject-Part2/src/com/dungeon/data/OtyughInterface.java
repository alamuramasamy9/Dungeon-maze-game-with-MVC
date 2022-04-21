package com.dungeon.data;


/**
 * Otyugh interface is used to allocate the otyugh to the right location and health of otyugh.
 * The getters and setters are defined in this class.
 * This is implemented by the otyugh class.
 */


public interface OtyughInterface {

  /**
   * get location Node of otyugh.
   *
   * @return location Node
   */
  Node getOtyughLocation();

  /**
   * set location Node of otyugh.
   *
   * @param otyughLocation location Node
   */
  void setOtyughLocation(Node otyughLocation);

  /**
   * get health of otyugh.
   *
   * @return health
   */
  int getOtyughHealth();

  /**
   * set location Node of otyugh.
   *
   * @param otyughHealth health
   */
  void setOtyughHealth(int otyughHealth);

}

