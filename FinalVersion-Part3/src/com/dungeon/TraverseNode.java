package com.dungeon;

import java.util.ArrayList;
import java.util.Collections;

import com.dungeon.data.DungeonAndLeftOverPath;
import com.dungeon.data.Node;
import com.dungeon.data.Path;

/**
 * The TraverseNode Class is created.
 * It is used to perform the Kruskal's Algorithm.
 * The paths are selected and leftover paths are classified.
 * Interconnectivity is applied to the leftover paths.
 */

public class TraverseNode {

  /**
   * this method is used to generate the dungeon with the paths that are connected.
   *
   * @param potentialPaths all the potential paths are passed as input
   * @return the dungeon(selected path) and leftover paths are classified.
   */

  public DungeonAndLeftOverPath generateDungeonAndLeftOverPath(ArrayList<Path> potentialPaths) {
    ArrayList<ArrayList<Path>> maze = new ArrayList<ArrayList<Path>>();
    ArrayList<Path> leftOverPaths = new ArrayList<Path>();
    for (int i = 0; i < potentialPaths.size(); i++) {
      Path eachPath = potentialPaths.get(i);
      Node startNode = eachPath.getStart();
      Node endNode = eachPath.getEnd();

      //Check if same set - To identify leftover
      boolean isLeftOverPath = false;
      for (int j = 0; j < maze.size(); j++) {
        ArrayList<Path> eachSet = maze.get(j);
        boolean isSameSetStart = false;
        boolean isSameSetEnd = false;
        for (int k = 0; k < eachSet.size(); k++) {
          Path eachPathInSet = eachSet.get(k);
          Node setStartNode = eachPathInSet.getStart();
          Node setEndNode = eachPathInSet.getEnd();
          if (startNode.getX() == setStartNode.getX() && startNode.getY() == setStartNode.getY()) {
            isSameSetStart = true;
          }
          if (endNode.getX() == setStartNode.getX() && endNode.getY() == setStartNode.getY()) {
            isSameSetEnd = true;
          }
          if (startNode.getX() == setEndNode.getX() && startNode.getY() == setEndNode.getY()) {
            isSameSetStart = true;
          }
          if (endNode.getX() == setEndNode.getX() && endNode.getY() == setEndNode.getY()) {
            isSameSetEnd = true;
          }
        }
        if (isSameSetStart && isSameSetEnd) {
          isLeftOverPath = true;
          break;
        }
      }
      //System.out.println("Each potential path >> Start: "+startEdge.getX()+",
      // "+startEdge.getY()+" >> end: "+endEdge.getX()+", "+endEdge.getY()+" >>
      // is leftover: "+isLeftOverPath);

      //Add to maze only if the path is not in same set else add it to leftover paths
      if (isLeftOverPath) {
        leftOverPaths.add(eachPath);
      } else {
        //Check this path is joint of any existing two sets by
        // checking Start Edge in one set & End node in another set
        //If so then mege those 2 set as one
        int firstSetIndex = -1;
        int secondSetIndex = -1;
        for (int j = 0; j < maze.size(); j++) {
          ArrayList<Path> eachSet = maze.get(j);
          for (int k = 0; k < eachSet.size(); k++) {
            Path eachPathinSet = eachSet.get(k);
            Node eachPathStartEdgeinSet = eachPathinSet.getStart();
            Node eachPathEndEdgeinSet = eachPathinSet.getEnd();
            if ((eachPathStartEdgeinSet.getX() == startNode.getX()
                    && eachPathStartEdgeinSet.getY() == startNode.getY())
                    || (eachPathEndEdgeinSet.getX() == startNode.getX()
                    && eachPathEndEdgeinSet.getY() == startNode.getY())) {
              firstSetIndex = j;
            }
            if ((eachPathStartEdgeinSet.getX() == endNode.getX()
                    && eachPathStartEdgeinSet.getY() == endNode.getY())
                    || (eachPathEndEdgeinSet.getX() == endNode.getX()
                    && eachPathEndEdgeinSet.getY() == endNode.getY())) {
              secondSetIndex = j;
            }
          }
          if (firstSetIndex != -1 && secondSetIndex != -1) {
            break;
          }
        }

        //Yes - Path eachPath=potentialPaths.get(i); is connecting two different sets, so merge them
        ArrayList<ArrayList<Path>> tmpNewMaze = new ArrayList<ArrayList<Path>>();
        if (firstSetIndex != -1 && secondSetIndex != -1 && firstSetIndex != secondSetIndex) {
          ArrayList<Path> firstSet = maze.get(firstSetIndex);
          ArrayList<Path> secondSet = maze.get(secondSetIndex);
          ArrayList<Path> mergedSet = new ArrayList<Path>();
          mergedSet.addAll(firstSet);
          mergedSet.addAll(secondSet);//Both sets are merged
          mergedSet.add(eachPath);//Adding current path as well
          tmpNewMaze.add(mergedSet);//Merged set is added new temp. maze

          for (int j = 0; j < maze.size(); j++) {
            if (j != firstSetIndex && j != secondSetIndex) {
              tmpNewMaze.add(maze.get(j));
            }
          }
          //replace the new temp. maze into actual maze,
          // as merged set should be primary for further operations
          maze = tmpNewMaze;
        }

        boolean isAddedExistingSet = false;
        for (int j = 0; j < maze.size(); j++) {
          ArrayList<Path> eachSet = maze.get(j);
          for (int k = 0; k < eachSet.size(); k++) {
            Path eachPathinSet = eachSet.get(k);
            Node eachPathStartEdgeinSet = eachPathinSet.getStart();
            Node eachPathEndEdgeinSet = eachPathinSet.getEnd();
            if (eachPathStartEdgeinSet.getX() == startNode.getX()
                    && eachPathStartEdgeinSet.getY() == startNode.getY()) {
              isAddedExistingSet = true;
              break;
            } else if (eachPathStartEdgeinSet.getX() == endNode.getX()
                    && eachPathStartEdgeinSet.getY() == endNode.getY()) {
              isAddedExistingSet = true;
              break;
            } else if (eachPathEndEdgeinSet.getX() == startNode.getX()
                    && eachPathEndEdgeinSet.getY() == startNode.getY()) {
              isAddedExistingSet = true;
              break;
            } else if (eachPathEndEdgeinSet.getX() == endNode.getX()
                    && eachPathEndEdgeinSet.getY() == endNode.getY()) {
              isAddedExistingSet = true;
              break;
            }
          }
          if (isAddedExistingSet) {
            eachSet.add(eachPath);
            break;
          }
        }
        if (!isAddedExistingSet) {
          ArrayList<Path> eachSet = new ArrayList<Path>();
          eachSet.add(eachPath);
          maze.add(eachSet);
        }
      }
    }
    return new DungeonAndLeftOverPath(maze, leftOverPaths);
  }

  /**
   * method to shuffle the leftover paths to get random paths.
   *
   * @param leftOverPaths list of leftover paths
   */
  public void shuffleLeftOverPaths(ArrayList<Path> leftOverPaths) {
    Collections.shuffle(leftOverPaths);
  }

  /**
   * method to validate the interconnectivity i.e. less than or equal to leftover path.
   *
   * @param leftOverPaths     list of leftover paths
   * @param interConnectivity value of interconnectivity given by user.
   * @return value of interconnectivity returned.
   */
  public int validateInterconnectivityPathWithLeftOverPath(ArrayList<Path> leftOverPaths,
                                                           int interConnectivity) {
    int interCount = 0;
    if (leftOverPaths.size() < interConnectivity) {
      interCount = leftOverPaths.size();
    } else {
      interCount = interConnectivity;
    }
    return interCount;
  }

  /**
   * method to apply interconnectivity and get the final maze.
   *
   * @param interCount    value of interconnectivity
   * @param leftOverPaths list of leftover paths
   * @param finalDungeon  list of all the paths added
   * @return list of all the paths after applying interconnectivity
   */

  public ArrayList<Path> applyInterconnectivity(int interCount, ArrayList<Path> leftOverPaths,
                                                ArrayList<Path> finalDungeon) {
    for (int i = 0; i < interCount; i++) {
      Path eachPath = leftOverPaths.get(i);
      finalDungeon.add(eachPath);
    }
    return finalDungeon;
  }

}
