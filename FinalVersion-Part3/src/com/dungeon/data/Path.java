package com.dungeon.data;

/**
 * This is a java class for representing the Paths created between the nodes of the maze.
 * The getters and setters for the start node and end node are defined.
 * The constructor of the class is also defined.
 */

public class Path {
  private Node start = null;
  private Node end = null;

  /**
   * Constructor of the Path class.
   *
   * @param istart the start node object
   * @param iend   the end node object
   */

  public Path(Node istart, Node iend) {
    this.start = istart;
    this.end = iend;
  }

  /**
   * The get start method to get the start node of the path.
   *
   * @return value of start node
   */

  public Node getStart() {
    return start;
  }

  /**
   * The set start method to set the start node of the path.
   *
   * @param start is the value of start that is passed
   */

  public void setStart(Node start) {
    this.start = start;
  }

  /**
   * The get end method to get the end node of the path.
   *
   * @return value of end node
   */

  public Node getEnd() {
    return end;
  }

  /**
   * The set end method to set the end node of the path.
   *
   * @param end is the value of end that is passed
   */

  public void setEnd(Node end) {
    this.end = end;
  }

}
