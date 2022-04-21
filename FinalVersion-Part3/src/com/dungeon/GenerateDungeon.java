package com.dungeon;

import java.util.ArrayList;
import java.util.Collections;

import com.dungeon.data.Node;
import com.dungeon.data.Path;

/**
 * The GenerateDungeon class is created.
 * It is used to classify the nodes depending on the number of paths it is connected to.
 * If it is connected to 2 paths it is classified as a tunnel.
 * All other nodes are classified as caves.
 * the shuffle caves method is used to shuffle caves to randomly allocate treasure.
 */

public class GenerateDungeon implements Dungeon {

  /**
   * This method is used to identify the tunnels.
   *
   * @param finalDungeon is the list of the paths in dungeon.
   * @param rows         is the number of rows
   * @param cols         is the number of columns
   * @return list of nodes which are tunnels
   */

  @Override
  public ArrayList<Node> identifyTunnel(ArrayList<Path> finalDungeon, int rows, int cols) {
    ArrayList<Node> tunnels = new ArrayList<Node>();
    if (finalDungeon.size() != 0) {
      for (int i = 0; i < finalDungeon.size(); i++) {
        Path eachPath = finalDungeon.get(i);
        Node startNode = eachPath.getStart();
        Node endNode = eachPath.getEnd();


        int sX = startNode.getX();
        int sY = startNode.getY();
        int noOfPaths = 0;
        //TOP (NORTH) - Is existing
        if (sY - 1 != -1) {
          //Path pathTop=new Path(new Edge(sX,sY-1), new Edge(sX,sY));
          Node pathTopStart = new Node(sX, sY - 1);
          Node pathTopEnd = new Node(sX, sY);

          for (int j = 0; j < finalDungeon.size(); j++) {
            Path eachDungeonPath = finalDungeon.get(j);
            Node eachDungeonPathStartNode = eachDungeonPath.getStart();
            Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
            //Checking the identified pathTop is present in Dungeon
            if (eachDungeonPathStartNode.getX() == pathTopStart.getX()
                    && eachDungeonPathStartNode.getY() == pathTopStart.getY()
                    && eachDungeonPathEndNode.getX() == pathTopEnd.getX()
                    && eachDungeonPathEndNode.getY() == pathTopEnd.getY()) {
              noOfPaths++;
              break;
            }
          }
        }
        //RIGHT (EAST) - Is existing
        if (sX + 1 < cols) {
          //Path pathRight=new Path(new Edge(sX,sY), new Edge(sX+1,sY));
          Node pathRightStart = new Node(sX, sY);
          Node pathRightEnd = new Node(sX + 1, sY);

          for (int j = 0; j < finalDungeon.size(); j++) {
            Path eachDungeonPath = finalDungeon.get(j);
            Node eachDungeonPathStartNode = eachDungeonPath.getStart();
            Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
            //Checking the identified pathTop is present in Dungeon
            if (eachDungeonPathStartNode.getX() == pathRightStart.getX()
                    && eachDungeonPathStartNode.getY() == pathRightStart.getY()
                    && eachDungeonPathEndNode.getX() == pathRightEnd.getX()
                    && eachDungeonPathEndNode.getY() == pathRightEnd.getY()) {
              noOfPaths++;
              break;
            }
          }
        }
        //DOWN (SOUTH) - Is existing
        if (sY + 1 < rows) {
          //Path pathDown=new Path(new Edge(sX,sY), new Edge(sX,sY+1));
          Node pathDownStart = new Node(sX, sY);
          Node pathDownEnd = new Node(sX, sY + 1);

          for (int j = 0; j < finalDungeon.size(); j++) {
            Path eachDungeonPath = finalDungeon.get(j);
            Node eachDungeonPathStartNode = eachDungeonPath.getStart();
            Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
            //Checking the identified pathTop is present in Dungeon
            if (eachDungeonPathStartNode.getX() == pathDownStart.getX()
                    && eachDungeonPathStartNode.getY() == pathDownStart.getY()
                    && eachDungeonPathEndNode.getX() == pathDownEnd.getX()
                    && eachDungeonPathEndNode.getY() == pathDownEnd.getY()) {
              noOfPaths++;
              break;
            }
          }
        }
        //LEFT (WEST) - Is existing
        if (sX - 1 != -1) {
          //Path pathLeft=new Path(new Edge(sX-1,sY), new Edge(sX,sY));
          Node pathLeftStart = new Node(sX - 1, sY);
          Node pathLeftEnd = new Node(sX, sY);

          for (int j = 0; j < finalDungeon.size(); j++) {
            Path eachDungeonPath = finalDungeon.get(j);
            Node eachDungeonPathStartNode = eachDungeonPath.getStart();
            Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
            //Checking the identified pathTop is present in Dungeon
            if (eachDungeonPathStartNode.getX() == pathLeftStart.getX()
                    && eachDungeonPathStartNode.getY() == pathLeftStart.getY()
                    && eachDungeonPathEndNode.getX() == pathLeftEnd.getX()
                    && eachDungeonPathEndNode.getY() == pathLeftEnd.getY()) {
              noOfPaths++;
              break;
            }
          }
        }

        //Start Edge - as Path has 2 edges
        if (noOfPaths == 2) {
          boolean isEdgeAddedAlready = false;
          for (int j = 0; j < tunnels.size(); j++) {
            Node eachTunnelNode = tunnels.get(j);
            //Since we are using Path(StartEdge, EndEdge)
            // it is possible for duplicate edges so we are checking
            if (eachTunnelNode.getX() == sX && eachTunnelNode.getY() == sY) {
              isEdgeAddedAlready = true;
              break;
            }
          }
          if (!isEdgeAddedAlready) {
            tunnels.add(startNode);
          }
        }

        int eX = endNode.getX();
        int eY = endNode.getY();
        noOfPaths = 0;
        //TOP (NORTH) - Is existing
        if (eY - 1 != -1) {
          //Path pathTop=new Path(new Edge(sX,sY-1), new Edge(sX,sY));
          Node pathTopStart = new Node(eX, eY - 1);
          Node pathTopEnd = new Node(eX, eY);

          for (int j = 0; j < finalDungeon.size(); j++) {
            Path eachDungeonPath = finalDungeon.get(j);
            Node eachDungeonPathStartNode = eachDungeonPath.getStart();
            Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
            //Checking the identified pathTop is present in Dungeon
            if (eachDungeonPathStartNode.getX() == pathTopStart.getX()
                    && eachDungeonPathStartNode.getY() == pathTopStart.getY()
                    && eachDungeonPathEndNode.getX() == pathTopEnd.getX()
                    && eachDungeonPathEndNode.getY() == pathTopEnd.getY()) {
              noOfPaths++;
              break;
            }
          }
        }
        //RIGHT (EAST) - Is existing
        if (eX + 1 < cols) {
          //Path pathRight=new Path(new Edge(sX,sY), new Edge(sX+1,sY));
          Node pathRightStart = new Node(eX, eY);
          Node pathRightEnd = new Node(eX + 1, eY);

          for (int j = 0; j < finalDungeon.size(); j++) {
            Path eachDungeonPath = finalDungeon.get(j);
            Node eachDungeonPathStartNode = eachDungeonPath.getStart();
            Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
            //Checking the identified pathTop is present in Dungeon
            if (eachDungeonPathStartNode.getX() == pathRightStart.getX()
                    && eachDungeonPathStartNode.getY() == pathRightStart.getY()
                    && eachDungeonPathEndNode.getX() == pathRightEnd.getX()
                    && eachDungeonPathEndNode.getY() == pathRightEnd.getY()) {
              noOfPaths++;
              break;
            }
          }
        }
        //DOWN (SOUTH) - Is existing
        if (eY + 1 < rows) {
          //Path pathDown=new Path(new Edge(sX,sY), new Edge(sX,sY+1));
          Node pathDownStart = new Node(eX, eY);
          Node pathDownEnd = new Node(eX, eY + 1);

          for (int j = 0; j < finalDungeon.size(); j++) {
            Path eachDungeonPath = finalDungeon.get(j);
            Node eachDungeonPathStartNode = eachDungeonPath.getStart();
            Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
            //Checking the identified pathTop is present in Dungeon
            if (eachDungeonPathStartNode.getX() == pathDownStart.getX()
                    && eachDungeonPathStartNode.getY() == pathDownStart.getY()
                    && eachDungeonPathEndNode.getX() == pathDownEnd.getX()
                    && eachDungeonPathEndNode.getY() == pathDownEnd.getY()) {
              noOfPaths++;
              break;
            }
          }
        }
        //LEFT (WEST) - Is existing
        if (eX - 1 != -1) {
          //Path pathLeft=new Path(new Edge(sX-1,sY), new Edge(sX,sY));
          Node pathLeftStart = new Node(eX - 1, eY);
          Node pathLeftEnd = new Node(eX, eY);

          for (int j = 0; j < finalDungeon.size(); j++) {
            Path eachDungeonPath = finalDungeon.get(j);
            Node eachDungeonPathStartNode = eachDungeonPath.getStart();
            Node eachDungeonPathEndNode = eachDungeonPath.getEnd();
            //Checking the identified pathTop is present in Dungeon
            if (eachDungeonPathStartNode.getX() == pathLeftStart.getX()
                    && eachDungeonPathStartNode.getY() == pathLeftStart.getY()
                    && eachDungeonPathEndNode.getX() == pathLeftEnd.getX()
                    && eachDungeonPathEndNode.getY() == pathLeftEnd.getY()) {
              noOfPaths++;
              break;
            }
          }
        }

        //End Edge - as Path has 2 edges
        if (noOfPaths == 2) {
          boolean isEdgeAddedAlready = false;
          for (int j = 0; j < tunnels.size(); j++) {
            Node eachTunnelNode = tunnels.get(j);
            //Since we are using Path(StartEdge, EndEdge) -
            // it is possible for duplicate edges so we are checking
            if (eachTunnelNode.getX() == sX && eachTunnelNode.getY() == sY) {
              isEdgeAddedAlready = true;
              break;
            }
          }
          if (!isEdgeAddedAlready) {
            tunnels.add(startNode);
          }
        }
      }
    }
    return tunnels;
  }

  /**
   * used to identify the caves which are the nodes which are not tunnels.
   *
   * @param finalDungeon is the list of the paths in dungeon.
   * @param tunnels      list of the nodes that are tunnels
   * @return list of nodes that are caves
   */

  @Override
  public ArrayList<Node> identifyCaves(ArrayList<Path> finalDungeon, ArrayList<Node> tunnels) {
    ArrayList<Node> caves = new ArrayList<Node>();
    if (finalDungeon.size() != 0) {
      for (int i = 0; i < finalDungeon.size(); i++) {
        Path eachDungeonPath = finalDungeon.get(i);
        Node eachPathStartNode = eachDungeonPath.getStart();
        Node eachPathEndNode = eachDungeonPath.getEnd();
        boolean isStartEdgeAddedAsCave = false;
        boolean isEndEdgeAddedAsCave = false;
        for (int j = 0; j < caves.size(); j++) {
          Node eachCave = caves.get(j);
          //This is to avoid duplicate cave added
          if (eachCave.getX() == eachPathStartNode.getX() && eachCave.getY()
                  == eachPathStartNode.getY()) {
            isStartEdgeAddedAsCave = true;
          }
          //This is to avoid duplicate cave added
          if (eachCave.getX() == eachPathEndNode.getX() && eachCave.getY()
                  == eachPathEndNode.getY()) {
            isEndEdgeAddedAsCave = true;
          }
        }

        //Check if the Dungeon Edge exist in Tunnel, if so then the edge should not be added as CAVE
        boolean isStartEdgePresentInTunnel = false;
        boolean isEndEdgePresentInTunnel = false;
        for (int j = 0; j < tunnels.size(); j++) {
          Node eachTunnen = tunnels.get(j);
          if (eachTunnen.getX() == eachPathStartNode.getX() && eachTunnen.getY()
                  == eachPathStartNode.getY()) {
            isStartEdgePresentInTunnel = true;
          }
          if (eachTunnen.getX() == eachPathEndNode.getX() && eachTunnen.getY()
                  == eachPathEndNode.getY()) {
            isEndEdgePresentInTunnel = true;
          }
        }

        //Start edge is not found in tunnels & also not exists in existing Cave list.
        if (!isStartEdgePresentInTunnel && !isStartEdgeAddedAsCave) {
          caves.add(eachPathStartNode);
        }
        //End edge is not found in tunnels & also not exists in existing Cave list, so can be added
        if (!isEndEdgePresentInTunnel && !isEndEdgeAddedAsCave) {
          caves.add(eachPathStartNode);
        }
      }
    }
    return caves;
  }

  /**
   * shuffle caves method used to shuffle the caves in the list.
   *
   * @param caves return shuffled caves.
   */

  @Override
  public void shuffleCaves(ArrayList<Node> caves) {
    Collections.shuffle(caves);
  }

}
