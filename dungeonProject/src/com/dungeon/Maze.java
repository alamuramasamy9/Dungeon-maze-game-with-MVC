package com.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.dungeon.data.Node;
import com.dungeon.data.Path;
import com.dungeon.data.Treasure;

/**
 * The Maze class is created.
 * It is used to allocate treasure inside the caves.
 * It is also used for generating the start node and the end node randomly.
 */

public class Maze {

  private int playerEndLocationIndex;

  /**
   * The treasure master method is used to decide on one or more treasures to be assigned.
   * It is randomly generated after shuffling.
   *
   * @param needStaticAllocation static allocation for testing
   * @return it returns the value randomly for the treasure to be allotted in the caves.
   */

  public ArrayList<Treasure> treasureMaster(boolean needStaticAllocation) {
    /*
     * 0 0 0 - Not applied, as minimum one treasure is mandatory
     * 0 0 1
     * 0 1 0
     * 0 1 1
     * 1 0 0
     * 1 0 1
     * 1 1 0
     * 1 1 1
     * */
    ArrayList<Treasure> treasureMaster = new ArrayList<Treasure>();
    treasureMaster.add(new Treasure(false, false, true));//0 0 1
    treasureMaster.add(new Treasure(false, true, false));//0 1 0
    treasureMaster.add(new Treasure(false, true, true));//0 1 1
    treasureMaster.add(new Treasure(true, false, false));//1 0 0
    treasureMaster.add(new Treasure(true, false, true));//1 0 1
    treasureMaster.add(new Treasure(true, true, false));//1 1 0
    treasureMaster.add(new Treasure(true, true, true));//1 1 1
    if (!needStaticAllocation) {
      //Shuffle Treasure Master so Random Treasure combination will be allocated.
      Collections.shuffle(treasureMaster);
    }
    return treasureMaster;
  }

  /**
   * This method is used to map the type of treasure to each of the nodes.
   *
   * @param numberOfCavesTreasureAllocated number of caves that need to have treasure.
   * @param caves                          list of nodes that are caves
   * @param treasureMaster                 the list of the allocations of treasure that is given
   * @param needStaticAllocation           static allocation for testing
   * @return treasure allocated in the caves
   */

  public ArrayList<HashMap<Node, Treasure>> allocateTreasure(
          int numberOfCavesTreasureAllocated, ArrayList<Node> caves,
          ArrayList<Treasure> treasureMaster, boolean needStaticAllocation) {
    //Allocate treasure to caves
    ArrayList<HashMap<Node, Treasure>> treasureCaves = new ArrayList<HashMap<Node, Treasure>>();
    for (int i = 0; i < numberOfCavesTreasureAllocated; i++) {
      Node eachCave = caves.get(i);
      HashMap<Node, Treasure> eachTreasureCave = new HashMap<Node, Treasure>();

      int treasureIndex = i % 7;//7 is used because no. of treasure master combination maximum is 7
      Treasure randomTreasure = treasureMaster.get(treasureIndex);

      eachTreasureCave.put(eachCave, randomTreasure);
      treasureCaves.add(eachTreasureCave);
    }
    if (!needStaticAllocation) {
      //Shuffle treasure allocated caves for random locations
      Collections.shuffle(treasureCaves);
    }
    return treasureCaves;
  }

  /**
   * method to get the start of the player randomly.
   *
   * @param finalDungeon list of nodes
   */

  public void generateRandomStart(ArrayList<Path> finalDungeon) {
    //Shuffle the final dungeon, so random start point will be picked as first index of the array
    Collections.shuffle(finalDungeon);
  }

  /**
   * method to get the end of the player randomly.
   *
   * @param finalDungeon list of nodes
   * @return end node
   */

  public Node generateRandomEnd(ArrayList<Path> finalDungeon) {
    ArrayList<Integer> endLoc = new ArrayList<Integer>();
    //starting with 5+3 paths, as minimum 5 caves should be used between start and end point of game.
    endLoc.add(new Integer(8));
    endLoc.add(new Integer(9));
    endLoc.add(new Integer(10));
    endLoc.add(new Integer(11));
    endLoc.add(new Integer(12));
    //Shuffle player end location master, so random minimum travel path will be selected.
    Collections.shuffle(endLoc);

    int playerEndLocationIndex = endLoc.get(0).intValue();
    if (finalDungeon.size() < playerEndLocationIndex) {
      System.err.print("Dungeon does not have even 5 path locations, "
              + "so auto selecting maximum value possible");
      playerEndLocationIndex = finalDungeon.size() - 1;
    }
    this.playerEndLocationIndex = playerEndLocationIndex;
    return finalDungeon.get(playerEndLocationIndex).getEnd();
  }

  /**
   * method to get the player end location.
   *
   * @return the player end location
   */

  public int getplayerEndLocationIndex() {
    return this.playerEndLocationIndex;
  }

}
