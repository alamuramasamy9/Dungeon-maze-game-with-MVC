package com.dungeon.v2;

import java.util.ArrayList;

import com.dungeon.Maze;
import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.Treasure;

/**
 * The Dungeons class that contains the details of the maze.
 * It also contains treasure details and generates the final dungeon.
 * This class is used in game model.
 */

public class DungeonV2 {

  Maze mazeObj;
  int numberOfCavesTreasureAllocated;
  ArrayList<Node> caves;
  ArrayList<Treasure> treasureMaster;
  ArrayList<Path> finalDungeon;
  ArrayList<Node> tunnels;

  /**
   * Constuctor of the dungeons class.
   *
   * @param mazeObj                        Maze
   * @param numberOfCavesTreasureAllocated number of caves with treasure
   * @param caves                          list of caves
   * @param treasureMaster                 posssible allocation of treasure
   * @param finalDungeon                   final dungeon
   */

  public DungeonV2(Maze mazeObj, int numberOfCavesTreasureAllocated, ArrayList<Node> caves,
                   ArrayList<Treasure> treasureMaster,
                   ArrayList<Path> finalDungeon, ArrayList<Node> tunnels) {
    super();
    this.mazeObj = mazeObj;
    this.numberOfCavesTreasureAllocated = numberOfCavesTreasureAllocated;
    this.caves = caves;
    this.treasureMaster = treasureMaster;
    this.finalDungeon = finalDungeon;
    this.tunnels = tunnels;
  }

  /**
   * get the Maze and parse it.
   *
   * @return the Maze
   */

  public Maze getMazeObj() {
    return mazeObj;
  }

  /**
   * get the number of caves with treasure in it.
   *
   * @return number of caves
   */

  public int getNumberOfCavesTreasureAllocated() {
    return numberOfCavesTreasureAllocated;
  }

  /**
   * get the caves in the dungeon.
   *
   * @return list of cave Nodes
   */

  public ArrayList<Node> getCaves() {
    return caves;
  }

  /**
   * get the treasure master to allocate treasure.
   *
   * @return treasure list
   */

  public ArrayList<Treasure> getTreasureMaster() {
    return treasureMaster;
  }

  /**
   * get the final dungeon with caves and tunnels.
   *
   * @return final dungeon.
   */

  public ArrayList<Path> getFinalDungeon() {
    return finalDungeon;
  }

  /**
   * get the list of tunnels.
   *
   * @return list of tunnels
   */

  public ArrayList<Node> getTunnels() {
    return tunnels;
  }


}
