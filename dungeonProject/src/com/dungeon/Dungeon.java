package com.dungeon;

import com.dungeon.data.Node;
import com.dungeon.data.Path;

import java.util.ArrayList;

/**
 * Dungeon Interface defines all the methods that is required to generate dungeon.
 * It is used to classify the nodes depending on the number of paths it is connected to.
 * If it is connected to 2 paths it is classified as a tunnel.
 * All other nodes are classified as caves.
 * the shuffle caves method is used to shuffle caves to randomly allocate treasure.
 * It is implemented in the GenerateDungeon Class.
 */

public interface Dungeon {

  /**
   * This method is used to identify the tunnels.
   *
   * @param finalDungeon is the list of the paths in dungeon.
   * @param rows         is the number of rows
   * @param cols         is the number of columns
   * @return list of nodes which are tunnels
   */

  public ArrayList<Node> identifyTunnel(ArrayList<Path> finalDungeon, int rows, int cols);

  /**
   * used to identify the caves which are the nodes which are not tunnels.
   *
   * @param finalDungeon is the list of the paths in dungeon.
   * @param tunnels      list of the nodes that are tunnels
   * @return list of nodes that are caves
   */

  public ArrayList<Node> identifyCaves(ArrayList<Path> finalDungeon, ArrayList<Node> tunnels);

  /**
   * shuffle caves method used to shuffle the caves in the list.
   *
   * @param caves return shuffled caves.
   */

  public void shuffleCaves(ArrayList<Node> caves);
}
