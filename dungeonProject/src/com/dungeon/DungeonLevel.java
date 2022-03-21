package com.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import com.dungeon.data.Node;
import com.dungeon.data.Path;

/**
 * This is a java class Dungeon Level where the rows and columns are passed.
 * This class is mainly used for creating the Nodes.
 * After that the potential paths are identified.
 * The generated paths are then shuffled.
 */

public class DungeonLevel {

  /**
   * The method to generate all the nodes of the class.
   *
   * @param rows value of rows from user
   * @param cols value of cols from user
   * @return the array list of nodes that are generated
   */

  public ArrayList<Node> generateNodes(int rows, int cols) {
    ArrayList<Node> nodes = new ArrayList<Node>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Node e = new Node(j, i);
        nodes.add(e);
      }
    }
    return nodes;
  }

  /**
   * the method to identify all the potential paths.
   *
   * @param rows  value of rows from user
   * @param cols  value of cols from user
   * @param nodes the array list of nodes that are generated
   * @return the array list of potential paths
   */

  public ArrayList<Path> identifyPaths(int rows, int cols, ArrayList<Node> nodes) {
    ArrayList<Path> potentialPaths = new ArrayList<Path>();
    for (int i = 0; i < nodes.size(); i++) {
      Node eachNode = nodes.get(i);
      int edgeX = eachNode.getX();
      int edgeY = eachNode.getY();

      /*
       * int rows=4; (Y)
       * int cols=6; (X)
       * */
      //6-1!=5
      //forward
      if (cols - 1 != edgeX) {
        Node pathStart1 = new Node(edgeX, edgeY);
        Node pathEnd1 = new Node(edgeX + 1, edgeY);
        Path path1 = new Path(pathStart1, pathEnd1);
        potentialPaths.add(path1);
      }

      //4-1!=3
      //downward
      if (rows - 1 != edgeY) {
        Node pathStart2 = new Node(edgeX, edgeY);
        Node pathEnd2 = new Node(edgeX, edgeY + 1);
        Path path2 = new Path(pathStart2, pathEnd2);
        potentialPaths.add(path2);
      }
    }
    return potentialPaths;
  }

  /**
   * the method to shuffle the identified paths.
   *
   * @param potentialPaths list of potential paths that is passed.
   */

  public void shufflePaths(ArrayList<Path> potentialPaths) {
    Collections.shuffle(potentialPaths);
  }
}
