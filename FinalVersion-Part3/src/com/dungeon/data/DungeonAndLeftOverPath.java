package com.dungeon.data;

import java.util.ArrayList;

/**
 * This is a java class for representing dungeons and the leftover paths.
 * The getters and setters for dungeon and leftover paths are defined.
 * The constructor of the class is also defined.
 */

public class DungeonAndLeftOverPath {

  ArrayList<ArrayList<Path>> dungeon = null;
  ArrayList<Path> leftOverPaths = null;

  /**
   * Constructor of the DungeonandLeftOverPath class.
   *
   * @param idungeon       is the nested array list of dungeon representing all elements of the maze
   * @param ileftOverPaths is the list of left over paths
   */

  public DungeonAndLeftOverPath(ArrayList<ArrayList<Path>> idungeon,
                                ArrayList<Path> ileftOverPaths) {
    this.dungeon = idungeon;
    this.leftOverPaths = ileftOverPaths;
  }

  /**
   * The get dungeon method to get elements of maze.
   *
   * @return dungeon nested array list.
   */

  public ArrayList<ArrayList<Path>> getDungeon() {
    return dungeon;
  }

  /**
   * The set dungeon method to set elements of maze.
   *
   * @param dungeon is a nested array list that is passed into the method
   */

  public void setDungeon(ArrayList<ArrayList<Path>> dungeon) {
    this.dungeon = dungeon;
  }

  /**
   * The get leftover paths method to get list of all leftover paths.
   *
   * @return leftover list.
   */

  public ArrayList<Path> getLeftOverPaths() {
    return leftOverPaths;
  }

  /**
   * The set leftover paths method to get list of all leftover paths.
   *
   * @param leftOverPaths is the leftover list that is passed into the method
   */

  public void setLeftOverPaths(ArrayList<Path> leftOverPaths) {
    this.leftOverPaths = leftOverPaths;
  }

}
