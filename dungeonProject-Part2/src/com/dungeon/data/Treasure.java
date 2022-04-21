package com.dungeon.data;

/**
 * This is a java class for representing the Treasure of the caves of the maze.
 * The getters and setters for the three treasures to see if it is present.
 * The constructor of the class is also defined.
 */

public class Treasure implements TreasureInterface {

  boolean hasDiamond;
  boolean hasRuby;
  boolean hasSapphire;

  /**
   * Constructor of the Treasure class.
   *
   * @param ihasDiamond  boolean input to see if diamond is present
   * @param ihasRuby     boolean input to see if ruby is present
   * @param ihasSapphire boolean input to see if sapphire is present
   */

  //i - denotes input
  public Treasure(boolean ihasDiamond, boolean ihasRuby, boolean ihasSapphire) {
    this.hasDiamond = ihasDiamond;
    this.hasRuby = ihasRuby;
    this.hasSapphire = ihasSapphire;
  }

  /**
   * The has diamond method to see if cave has diamond.
   *
   * @return boolean value
   */

  @Override
  public boolean hasDiamond() {
    return hasDiamond;
  }

  /**
   * The set has diamond method to set boolean value.
   *
   * @param hasDiamond boolean that is passed
   */
  @Override
  public void setHasDiamond(boolean hasDiamond) {
    this.hasDiamond = hasDiamond;
  }

  /**
   * The has ruby method to see if cave has ruby.
   *
   * @return boolean value
   */

  @Override
  public boolean hasRuby() {
    return hasRuby;
  }

  /**
   * The set has ruby method to set boolean value.
   *
   * @param hasRuby boolean that is passed
   */

  @Override
  public void setHasRuby(boolean hasRuby) {
    this.hasRuby = hasRuby;
  }

  /**
   * The has sapphire method to see if cave has sapphire.
   *
   * @return boolean value
   */

  @Override
  public boolean hasSapphire() {
    return hasSapphire;
  }

  /**
   * The set has sapphire method to set boolean value.
   *
   * @param hasSapphire boolean that is passed
   */

  @Override
  public void setHasSapphire(boolean hasSapphire) {
    this.hasSapphire = hasSapphire;
  }

}
