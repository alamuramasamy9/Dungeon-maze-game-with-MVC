package com.dungeon;

import com.dungeon.data.ArrowLocations;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.v2.ArrowsV2;
import com.dungeon.v2.DungeonV2;
import com.dungeon.v2.OtyughsV2;
import com.dungeon.v2.TreasureV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This is the Model interface that contains all the functionaliies of the model.
 * This includes generation of dungeon maze with caves and tunnels.
 * It also includes allocation of treasure, otyughs and arrows.
 * Finally the play game method is used to execute the entire game.
 * This is implemented in the game model class.
 */


public interface ModelInterface {

  /**
   * The method to generate the dungeon.
   *
   * @param rows                 no of rows
   * @param cols                 no of cols
   * @param interConnectivity    interconnections
   * @param percentageOfTreasure percentage of treasure to be allocated
   * @param wrapping             wrapping boolean
   * @param noofOtyughs          no of otyughs
   * @param needStaticGame       static or random
   * @return the final dungeon object
   */

  DungeonV2 generateDungeon(int rows, int cols, int interConnectivity,
                            int percentageOfTreasure, boolean wrapping,
                            int noofOtyughs, boolean needStaticGame);

  /**
   * method to allocate the treasure.
   *
   * @param rows                           no of rows
   * @param cols                           no of cols
   * @param wrapping                       wrapping boolean
   * @param mazeObj                        maze object
   * @param numberOfCavesTreasureAllocated caves with treasure in it
   * @param caves                          list of caves
   * @param treasureMaster                 treasure master for determining treasure
   * @param finalDungeon                   final maze with caves and tunnels
   * @param needStaticGame                 static or random
   * @return treasure object
   */

  TreasureV2 allocateTreasure(int rows, int cols, boolean wrapping, Maze mazeObj,
                              int numberOfCavesTreasureAllocated,
                              ArrayList<Node> caves, ArrayList<Treasure> treasureMaster,
                              ArrayList<Path> finalDungeon, boolean needStaticGame);

  /**
   * method to allocate the otyughs in caves.
   *
   * @param caves          caves list
   * @param playerStartAt  player start location
   * @param playerEndAt    player end location
   * @param noofOtyughs    no of otyughs
   * @param needStaticGame static or random
   * @return otyugh details
   */

  OtyughsV2 allocateOtyughs(ArrayList<Node> caves, Node playerStartAt, Node playerEndAt,
                            int noofOtyughs,ArrayList<Node> tunnels, boolean needStaticGame);

  /**
   * method to allocate the arrows.
   *
   * @param noofOtyughs             no of otyughs
   * @param caves                   list of caves
   * @param updateGame              update game details
   * @param tunnels                 tunnel list
   * @param otyughLocations         location of otyughs
   * @param rows                    no of rows
   * @param cols                    no of cols
   * @param wrapping                wrapping boolean
   * @param playerPossibleLocations player location possibility
   * @param player                  player
   * @param finalDungeon            dungeon with caves and tunnels
   * @param treasureCaves           cavs with treasure
   * @param playerCurrentlyAt       player current location
   * @param playerEndAt             player start location
   * @param needStaticGame          static or random
   * @return arrow allocation details
   */

  ArrowsV2 allocateArrows(int noofOtyughs, ArrayList<Node> caves, UpdateGame updateGame,
                          ArrayList<Node> tunnels,
                          ArrayList<Otyugh> otyughLocations, int rows,
                          int cols, boolean wrapping,
                          PlayerLocations playerPossibleLocations, Player player,
                          ArrayList<Path> finalDungeon,
                          ArrayList<HashMap<Node, Treasure>> treasureCaves,
                          Node playerCurrentlyAt, Node playerEndAt, boolean needStaticGame);

  /**
   * method to carry of the game.
   *
   * @param rows                    no of rows
   * @param cols                    no of cols
   * @param wrapping                wrapping boolean
   * @param updateGame              update game details
   * @param otyughLocations         location of otyughs
   * @param playerPossibleLocations player location possibility
   * @param player                  player
   * @param finalDungeon            dungeon with caves and tunnels
   * @param treasureCaves           cavs with treasure
   * @param playerCurrentlyAt       player current location
   * @param playerEndAt             player start location
   * @param caves                   list of caves
   * @param arrowLocations          arrow location nodes
   * @param inputScanner            scanner
   * @param needStaticGame          random or static
   */

  void playGame(int rows, int cols, boolean wrapping, UpdateGame updateGame,
                ArrayList<Otyugh> otyughLocations, PlayerLocations playerPossibleLocations,
                Player player, ArrayList<Path> finalDungeon,
                ArrayList<HashMap<Node, Treasure>> treasureCaves,
                Node playerCurrentlyAt, Node playerEndAt, ArrayList<Node> caves,
                ArrayList<ArrowLocations> arrowLocations,
                Scanner inputScanner, boolean needStaticGame);
}
