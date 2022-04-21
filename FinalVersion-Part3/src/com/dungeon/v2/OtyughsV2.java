package com.dungeon.v2;

import java.util.ArrayList;

import com.dungeon.UpdateGame;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;

/**
 * The Otyughs class has getters and setters for placing otyughs into the caves and tunnels.
 * All of these methods are called to allocate the otyughs.
 * This class is used in game model.
 */

public class OtyughsV2 {

  int noofOtyughs;
  ArrayList<Node> caves;
  UpdateGame updateGame;
  ArrayList<Node> tunnels;
  ArrayList<Otyugh> otyughLocations;

  /**
   * Constructor of the Otyughs class.
   *
   * @param noofOtyughs     number of otyughs
   * @param caves           caves to place otyughs
   * @param updateGame      update game details
   * @param tunnels         tunels to place otyughs
   * @param otyughLocations update otyugh locations
   */

  public OtyughsV2(int noofOtyughs, ArrayList<Node> caves, UpdateGame updateGame,
                   ArrayList<Node> tunnels,
                   ArrayList<Otyugh> otyughLocations) {
    super();
    this.noofOtyughs = noofOtyughs;
    this.caves = caves;
    this.updateGame = updateGame;
    this.tunnels = tunnels;
    this.otyughLocations = otyughLocations;
  }

  /**
   * get the number of otyughs.
   *
   * @return number of otyughs
   */
  public int getNoofOtyughs() {
    return noofOtyughs;
  }

  /**
   * get the caves to place otyughs.
   *
   * @return list of cave Nodes
   */
  public ArrayList<Node> getCaves() {
    return caves;
  }

  /**
   * details of game are updated with otyughs.
   *
   * @return update game
   */

  public UpdateGame getProject2() {
    return updateGame;
  }

  /**
   * get the tunnels to place otyughs.
   *
   * @return list of tunnel Nodes
   */

  public ArrayList<Node> getTunnels() {
    return tunnels;
  }

  /**
   * get the location of otyughs.
   *
   * @return list of nodes
   */

  public ArrayList<Otyugh> getOtyughLocations() {
    return otyughLocations;
  }

  public void setOtyughLocations(ArrayList<Otyugh> otyughLocations) {
    this.otyughLocations = otyughLocations;
  }

}
