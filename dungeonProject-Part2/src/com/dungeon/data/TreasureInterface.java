package com.dungeon.data;


/**
 * This is a java interface for representing the Treasure of the caves of the maze.
 * The getters and setters for the three treasures to see if it is present.
 * The methods of this interface are implemented by the treasure class.
 */

public interface TreasureInterface {

  /**
   * The has diamond method to see if cave has diamond.
   *
   * @return boolean value
   */

  boolean hasDiamond();

  /**
   * The set has diamond method to set boolean value.
   *
   * @param hasDiamond boolean that is passed
   */

  void setHasDiamond(boolean hasDiamond);

  /**
   * The has ruby method to see if cave has ruby.
   *
   * @return boolean value
   */

  boolean hasRuby();

  /**
   * The set has ruby method to set boolean value.
   *
   * @param hasRuby boolean that is passed
   */

  void setHasRuby(boolean hasRuby);

  /**
   * The has sapphire method to see if cave has sapphire.
   *
   * @return boolean value
   */

  boolean hasSapphire();

  /**
   * The set has sapphire method to set boolean value.
   *
   * @param hasSapphire boolean that is passed
   */

  void setHasSapphire(boolean hasSapphire);

}

