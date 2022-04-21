package com.dungeon.data;

import java.util.ArrayList;

/**
 * Details of the Otyughs are set using this class.
 * The Otyughs within 1 position and 2 positions are determined and listed.
 * The getters for it are defined here.
 */

public class GameOtyughDetails {

  private int noOfOtyughsInOnePath;
  private int noOfOtyughsInTwoPath;
  private ArrayList<NearbyOtyughs> nearByOtyughs;

  /**
   * constructor of the otyugh locations class.
   *
   * @param inoOfOtyughsInOnePath number of otyughs in one position
   * @param inoOfOtyughsInTwoPath number of otyughs in two positions
   * @param inearByOtyughs        list of nearby otyughs
   */

  public GameOtyughDetails(int inoOfOtyughsInOnePath, int inoOfOtyughsInTwoPath,
                           ArrayList<NearbyOtyughs> inearByOtyughs) {
    this.noOfOtyughsInOnePath = inoOfOtyughsInOnePath;
    this.noOfOtyughsInTwoPath = inoOfOtyughsInTwoPath;
    this.nearByOtyughs = inearByOtyughs;
  }

  public int getNoOfOtyughsInOnePath() {
    return noOfOtyughsInOnePath;
  }

  public void setNoOfOtyughsInOnePath(int noOfOtyughsInOnePath) {
    this.noOfOtyughsInOnePath = noOfOtyughsInOnePath;
  }

  public int getNoOfOtyughsInTwoPath() {
    return noOfOtyughsInTwoPath;
  }

  public void setNoOfOtyughsInTwoPath(int noOfOtyughsInTwoPath) {
    this.noOfOtyughsInTwoPath = noOfOtyughsInTwoPath;
  }

  public ArrayList<NearbyOtyughs> getNearByOtyughs() {
    return nearByOtyughs;
  }

  public void setNearByOtyughs(ArrayList<NearbyOtyughs> nearByOtyughs) {
    this.nearByOtyughs = nearByOtyughs;
  }


}
