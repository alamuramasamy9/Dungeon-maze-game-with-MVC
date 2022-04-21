package com.dungeon.data;

/**
 * This is a java class for representing the Nodes of the maze.
 * The getters and setters for the X coordinate and Y coordinate are defined.
 * The constructor of the class is also defined.
 */

public class Node {
  private int x;
  private int y;

  /**
   * Constructor of the Node class.
   *
   * @param ix the input of x coordinate.
   * @param iy the input of y coordinate.
   */

  public Node(int ix, int iy) {
    this.x = ix;
    this.y = iy;
  }

  /**
   * The get x method to get the x coordinate of the maze.
   *
   * @return value of x
   */

  public int getX() {
    return x;
  }

  /**
   * The set x method to set the x coordinate of the maze.
   *
   * @param x is the value of x that is passed
   */

  public void setX(int x) {
    this.x = x;
  }

  /**
   * The get y method to get the y coordinate of the maze.
   *
   * @return value of y
   */

  public int getY() {
    return y;
  }

  /**
   * The set y method to set the y coordinate of the maze.
   *
   * @param y is the value of y that is passed
   */

  public void setY(int y) {
    this.y = y;
  }

}
