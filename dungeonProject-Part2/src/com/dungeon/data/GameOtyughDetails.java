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

  /**
   * number of otyugh that one path away from player.
   *
   * @return number of otyughs
   */
  public int getNoOfOtyughsInOnePath() {
    return noOfOtyughsInOnePath;
  }


  /**
   * number of otyugh thats two paths away from player.
   *
   * @return number of otyughs
   */

  public int getNoOfOtyughsInTwoPath() {
    return noOfOtyughsInTwoPath;
  }

  /**
   * get the otyughs that are near player.
   *
   * @return list of otyughs
   */

  public ArrayList<NearbyOtyughs> getNearByOtyughs() {
    return nearByOtyughs;
  }


}
