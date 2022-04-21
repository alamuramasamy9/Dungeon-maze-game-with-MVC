package com.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.dungeon.data.ArrowLocations;
import com.dungeon.data.Directions;
import com.dungeon.data.GameOtyughDetails;
import com.dungeon.data.NearbyOtyughs;
import com.dungeon.data.Node;
import com.dungeon.data.Otyugh;
import com.dungeon.data.Path;
import com.dungeon.data.PlayerLocations;
import com.dungeon.data.Treasure;
import com.dungeon.data.TreasureCollected;

/**
 * The Update Game class is used to update game.
 * The various validation functions are also included in it.
 * The methods of details to be displayed are also included.
 */

public class UpdateGame implements GameInterface {

  /**
   * player arrow master method is used to determine arrows into node.
   *
   * @param isStaticMaster static parameter for test cases
   * @return no of arrow
   */

  @Override
  public ArrayList<Integer> playerArrowMaster(boolean isStaticMaster) {
    ArrayList<Integer> arrows = new ArrayList<Integer>();
    arrows.add(new Integer(1));
    arrows.add(new Integer(2));
    arrows.add(new Integer(3));
    arrows.add(new Integer(4));
    if (!isStaticMaster) {
      Collections.shuffle(arrows);
    }
    return arrows;
  }

  /**
   * method to get number of arrows at the given location.
   *
   * @param arrowLocations    arrow location
   * @param playerCurrentlyAt player current location
   * @return number of arrows
   */

  @Override
  public int getNumberOfArrowsInCurrentLocation(ArrayList<ArrowLocations> arrowLocations,
                                                Node playerCurrentlyAt) {
    int noOfArrows = 0;
    for (int i = 0; i < arrowLocations.size(); i++) {
      ArrowLocations eachArrowLocation = arrowLocations.get(i);
      if (eachArrowLocation.getLocation().getX() == playerCurrentlyAt.getX()
              && eachArrowLocation.getLocation().getY() == playerCurrentlyAt.getY()) {
        noOfArrows = eachArrowLocation.getNoOfArrows();
      }
    }
    return noOfArrows;
  }

  /**
   * method to update the game according to the moves performed.
   *
   * @param numberOfArrowsHere                 number of arrows in location
   * @param noOfArrowsCollected                number of arrows player colected
   * @param treasureCollected                  treasure collected by player
   * @param playerCurrentlyAt                  player current location
   * @param treasureDetailsOfPlayerCurrentCave treasure details in current cave
   * @param playerPossibleLocations            possible moves
   * @param isCave                             is it a cave
   */

  @Override
  public void validateAndUpdateGame(int numberOfArrowsHere, int noOfArrowsCollected,
                                    TreasureCollected treasureCollected, Node playerCurrentlyAt,
                                    Treasure treasureDetailsOfPlayerCurrentCave,
                                    PlayerLocations playerPossibleLocations, boolean isCave) {
    System.out.println("Number of Arrows the Player has :" + noOfArrowsCollected);
    System.out.println("Treasures collected by the player so far : (Diamond - "
            + treasureCollected.getDiamondCount()
            + ", Ruby - " + treasureCollected.getRubyCount() + ", Sapphire - "
            + treasureCollected.getSapphireCount() + ")");
    if (isCave) {
      System.out.println("You are in a cave at (" + playerCurrentlyAt.getX()
              + ", " + playerCurrentlyAt.getY()
              + ")\n\nPossible moves are (North[" + playerPossibleLocations.getNorth().getX() + ", "
              + playerPossibleLocations.getNorth().getY() + "]:"
              + playerPossibleLocations.hasNorth()
              + " || East[" + playerPossibleLocations.getEast().getX() + ", "
              + playerPossibleLocations.getEast().getY() + "]:" + playerPossibleLocations.hasEast()
              + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
              + playerPossibleLocations.getSouth().getY()
              + "]:" + playerPossibleLocations.hasSouth() + " || West["
              + playerPossibleLocations.getWest().getX()
              + ", " + playerPossibleLocations.getWest().getY() + "]:"
              + playerPossibleLocations.hasWest() + ")");
    } else {
      System.out.println("You are in a tunnel at (" + playerCurrentlyAt.getX()
              + ", " + playerCurrentlyAt.getY()
              + ")\n\nPossible moves are (North[" + playerPossibleLocations.getNorth().getX()
              + ", " + playerPossibleLocations.getNorth().getY() + "]:"
              + playerPossibleLocations.hasNorth()
              + " || East[" + playerPossibleLocations.getEast().getX() + ", "
              + playerPossibleLocations.getEast().getY() + "]:"
              + playerPossibleLocations.hasEast() + " || South["
              + playerPossibleLocations.getSouth().getX() + ", "
              + playerPossibleLocations.getSouth().getY()
              + "]:" + playerPossibleLocations.hasSouth() + " || West["
              + playerPossibleLocations.getWest().getX()
              + ", " + playerPossibleLocations.getWest().getY() + "]:"
              + playerPossibleLocations.hasWest() + ")");
    }
    if (numberOfArrowsHere > 0) {
      System.out.println("You find " + numberOfArrowsHere + " arrows here. ");
    }
    if (treasureDetailsOfPlayerCurrentCave.hasDiamond()
            || treasureDetailsOfPlayerCurrentCave.hasRuby()
            || treasureDetailsOfPlayerCurrentCave.hasRuby()) {

      System.out.println("You find treasure here. ");
      if (treasureDetailsOfPlayerCurrentCave.hasDiamond()) {
        System.out.println("Diamond : 1");
      }
      if (treasureDetailsOfPlayerCurrentCave.hasRuby()) {
        System.out.println("Ruby : 1");
      }
      if (treasureDetailsOfPlayerCurrentCave.hasSapphire()) {
        System.out.println("Sapphire : 1");
      }

    }
  }

  /**
   * method for getting otyugh details.
   *
   * @param otyughLocations         location with otyughs in it
   * @param playerPossibleLocations possible locations
   * @param player                  player
   * @param finalDungeon            final dungeon with caves and tunnels
   * @param rows                    no of rows
   * @param cols                    no of cols
   * @param wrapping                wrapping
   * @return number of otyughs nearby
   */

  @Override
  public GameOtyughDetails getGameOtyughDetails(ArrayList<Otyugh> otyughLocations,
                                                PlayerLocations playerPossibleLocations,
                                                Player player, ArrayList<Path> finalDungeon,
                                                int rows, int cols, boolean wrapping) {
    int noOfOtyughsInOnePath = 0;
    int noOfOtyughsInTwoPath = 0;
    ArrayList<NearbyOtyughs> nearByOtyughs = new ArrayList<NearbyOtyughs>();

    // Get no. of Otyughs in 1 path from player's current location
    for (int i = 0; i < otyughLocations.size(); i++) {
      Otyugh eachOtyugh = otyughLocations.get(i);
      if (eachOtyugh.getOtyughHealth() == 0) {
        continue;
      }
      Node playerNorthMove = playerPossibleLocations.getNorth();
      if (playerPossibleLocations.hasNorth() && playerNorthMove.getX()
              == eachOtyugh.getOtyughLocation().getX()
              && playerNorthMove.getY() == eachOtyugh.getOtyughLocation().getY()) {
        noOfOtyughsInOnePath++;
        nearByOtyughs.add(new NearbyOtyughs(1,
                eachOtyugh.getOtyughLocation(), Directions.N, 100));
      }

      Node playerEastMove = playerPossibleLocations.getEast();
      if (playerPossibleLocations.hasEast() && playerEastMove.getX()
              == eachOtyugh.getOtyughLocation().getX()
              && playerEastMove.getY() == eachOtyugh.getOtyughLocation().getY()) {
        noOfOtyughsInOnePath++;
        nearByOtyughs.add(new NearbyOtyughs(1,
                eachOtyugh.getOtyughLocation(), Directions.E, 100));
      }

      Node playerSouthMove = playerPossibleLocations.getSouth();
      if (playerPossibleLocations.hasSouth() && playerSouthMove.getX()
              == eachOtyugh.getOtyughLocation().getX()
              && playerSouthMove.getY() == eachOtyugh.getOtyughLocation().getY()) {
        noOfOtyughsInOnePath++;
        nearByOtyughs.add(new NearbyOtyughs(1,
                eachOtyugh.getOtyughLocation(), Directions.S, 100));
      }

      Node playerWestMove = playerPossibleLocations.getWest();
      if (playerPossibleLocations.hasWest() && playerWestMove.getX()
              == eachOtyugh.getOtyughLocation().getX()
              && playerWestMove.getY() == eachOtyugh.getOtyughLocation().getY()) {
        noOfOtyughsInOnePath++;
        nearByOtyughs.add(new NearbyOtyughs(1,
                eachOtyugh.getOtyughLocation(), Directions.W, 100));
      }
    }


    // Get no. of Otyughs in 2 path from player's current location
    for (int i = 0; i < otyughLocations.size(); i++) {
      Otyugh eachOtyugh = otyughLocations.get(i);

      // Check duplicate locations, If Otyugh location already found no need to add
      // again
      boolean isNearByOtyughLocationAlreadyAdded = false;
      for (NearbyOtyughs eachNearBy : nearByOtyughs) {
        if (eachNearBy.getOtyughLocation().getX() == eachOtyugh.getOtyughLocation().getX()
                && eachNearBy.getOtyughLocation().getY() == eachOtyugh.getOtyughLocation().getY()) {
          isNearByOtyughLocationAlreadyAdded = true;
          break;
        }
      }
      if (isNearByOtyughLocationAlreadyAdded) {
        // Otuugh location already added no need any logic
        continue;
      }

      Node playerNorthMove = playerPossibleLocations.getNorth();
      if (playerPossibleLocations.hasNorth()) {
        PlayerLocations secondPossibleMoveLocationsOfNorth
                = player.getPossibleMoves(rows, cols, finalDungeon,
                playerNorthMove, wrapping);
        if (secondPossibleMoveLocationsOfNorth.hasNorth()
                && secondPossibleMoveLocationsOfNorth.getNorth().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfNorth.getNorth().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.N, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfNorth.hasEast()
                && secondPossibleMoveLocationsOfNorth.getEast().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfNorth.getEast().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.N, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfNorth.hasSouth()
                && secondPossibleMoveLocationsOfNorth.getSouth().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfNorth.getSouth().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.N, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfNorth.hasWest()
                && secondPossibleMoveLocationsOfNorth.getWest().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfNorth.getWest().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.N, 100));
          noOfOtyughsInTwoPath++;
        }
      }
      // Check duplicate locations, If Otyugh location already found no need to add
      // again
      isNearByOtyughLocationAlreadyAdded = false;
      for (NearbyOtyughs eachNearBy : nearByOtyughs) {
        if (eachNearBy.getOtyughLocation().getX() == eachOtyugh.getOtyughLocation().getX()
                && eachNearBy.getOtyughLocation().getY() == eachOtyugh.getOtyughLocation().getY()) {
          isNearByOtyughLocationAlreadyAdded = true;
          break;
        }
      }
      if (isNearByOtyughLocationAlreadyAdded) {
        // Otuugh location already added no need any logic
        continue;
      }

      Node playerEastMove = playerPossibleLocations.getEast();
      if (playerPossibleLocations.hasEast()) {
        PlayerLocations secondPossibleMoveLocationsOfEast
                = player.getPossibleMoves(rows, cols, finalDungeon,
                playerEastMove, wrapping);
        if (secondPossibleMoveLocationsOfEast.hasNorth()
                && secondPossibleMoveLocationsOfEast.getNorth().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfEast.getNorth().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2, eachOtyugh.getOtyughLocation(),
                  Directions.E, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfEast.hasEast()
                && secondPossibleMoveLocationsOfEast.getEast().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfEast.getEast().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.E, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfEast.hasSouth()
                && secondPossibleMoveLocationsOfEast.getSouth().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfEast.getSouth().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.E, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfEast.hasWest()
                && secondPossibleMoveLocationsOfEast.getWest().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfEast.getWest().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.E, 100));
          noOfOtyughsInTwoPath++;
        }
      }
      // Check duplicate locations, If Otyugh location already found no need to add
      // again
      isNearByOtyughLocationAlreadyAdded = false;
      for (NearbyOtyughs eachNearBy : nearByOtyughs) {
        if (eachNearBy.getOtyughLocation().getX() == eachOtyugh.getOtyughLocation().getX()
                && eachNearBy.getOtyughLocation().getY() == eachOtyugh.getOtyughLocation().getY()) {
          isNearByOtyughLocationAlreadyAdded = true;
          break;
        }
      }
      if (isNearByOtyughLocationAlreadyAdded) {
        // Otuugh location already added no need any logic
        continue;
      }

      Node playerSouthMove = playerPossibleLocations.getSouth();
      if (playerPossibleLocations.hasSouth()) {
        PlayerLocations secondPossibleMoveLocationsOfSouth
                = player.getPossibleMoves(rows, cols, finalDungeon,
                playerSouthMove, wrapping);
        if (secondPossibleMoveLocationsOfSouth.hasNorth()
                && secondPossibleMoveLocationsOfSouth.getNorth().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfSouth.getNorth().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.S, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfSouth.hasEast()
                && secondPossibleMoveLocationsOfSouth.getEast().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfSouth.getEast().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.S, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfSouth.hasSouth()
                && secondPossibleMoveLocationsOfSouth.getSouth().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfSouth.getSouth().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.S, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfSouth.hasWest()
                && secondPossibleMoveLocationsOfSouth.getWest().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfSouth.getWest().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.S, 100));
          noOfOtyughsInTwoPath++;
        }
      }
      // Check duplicate locations, If Otyugh location already found no need to add
      // again
      isNearByOtyughLocationAlreadyAdded = false;
      for (NearbyOtyughs eachNearBy : nearByOtyughs) {
        if (eachNearBy.getOtyughLocation().getX() == eachOtyugh.getOtyughLocation().getX()
                && eachNearBy.getOtyughLocation().getY() == eachOtyugh.getOtyughLocation().getY()) {
          isNearByOtyughLocationAlreadyAdded = true;
          break;
        }
      }
      if (isNearByOtyughLocationAlreadyAdded) {
        // Otuugh location already added no need any logic
        continue;
      }

      Node playerWestMove = playerPossibleLocations.getWest();
      if (playerPossibleLocations.hasWest()) {
        PlayerLocations secondPossibleMoveLocationsOfWest
                = player.getPossibleMoves(rows, cols, finalDungeon,
                playerWestMove, wrapping);
        if (secondPossibleMoveLocationsOfWest.hasNorth()
                && secondPossibleMoveLocationsOfWest.getNorth().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfWest.getNorth().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.W, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfWest.hasEast()
                && secondPossibleMoveLocationsOfWest.getEast().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfWest.getEast().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.W, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfWest.hasSouth()
                && secondPossibleMoveLocationsOfWest.getSouth().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfWest.getSouth().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.W, 100));
          noOfOtyughsInTwoPath++;
        } else if (secondPossibleMoveLocationsOfWest.hasWest()
                && secondPossibleMoveLocationsOfWest.getWest().getX()
                == eachOtyugh.getOtyughLocation().getX()
                && secondPossibleMoveLocationsOfWest.getWest().getY()
                == eachOtyugh.getOtyughLocation().getY()) {
          nearByOtyughs.add(new NearbyOtyughs(2,
                  eachOtyugh.getOtyughLocation(), Directions.W, 100));
          noOfOtyughsInTwoPath++;
        }
      }
    }
    return new GameOtyughDetails(noOfOtyughsInOnePath, noOfOtyughsInTwoPath, nearByOtyughs);
  }

  /**
   * method to determine if node is a cave.
   *
   * @param playerLocation location of player
   * @param caves          list of caves
   * @return boolean
   */

  @Override
  public boolean isCave(Node playerLocation, ArrayList<Node> caves) {
    for (Node eachCave : caves) {
      if (eachCave.getX() == playerLocation.getX() && eachCave.getY() == playerLocation.getY()) {
        return true;
      }
    }
    return false;
  }

  /**
   * method to get player input.
   *
   * @param inputScanner scaner
   * @return validated inout
   */

  @Override
  public String getPlayerInput(Scanner inputScanner) {
    String userInput = "";
    do {
      userInput = inputScanner.nextLine();
      if (!validateUserInput(userInput)) {
        System.out.println("Invalid input, try again.");
        System.out.println("Pickup, or Shoot (M-P-S)?");
      }
    }
    while (!validateUserInput(userInput));
    return userInput;
  }

  /**
   * Method to get the player input for moves.
   *
   * @param inputScanner            to scan inout
   * @param playerPossibleLocations possible moves of player
   * @param playerCurrentlyAt       current node
   * @return the player moving to next location.
   */

  @Override
  public String getPlayerMoveInput(Scanner inputScanner, PlayerLocations playerPossibleLocations,
                                   Node playerCurrentlyAt) {
    String nextMove = "";
    do {
      nextMove = inputScanner.nextLine();
      if (!validatePlayerMoveInput(playerPossibleLocations, nextMove,
              true, playerCurrentlyAt)) {
        System.out.println("Invalid input, try again.");
        System.out.println("Possible moves & this is casesensitive (W/N/E/S):");
      }
    }
    while (!validatePlayerMoveInput(playerPossibleLocations, nextMove,
            false, playerCurrentlyAt));
    return nextMove;
  }

  /**
   * method to get the distance to shoot to.
   *
   * @param inputScanner scanner
   * @return validated input
   */

  @Override
  public String getPlayerShootDistanceInput(Scanner inputScanner) {
    String shootDistance = "";
    do {
      shootDistance = inputScanner.nextLine();
      if (!validatePlayerShootDistanceInput(shootDistance)) {
        System.out.println("Invalid input, try again.");
        System.out.println("No. of caves (1-5)?");
      }
    }
    while (!validatePlayerShootDistanceInput(shootDistance));
    return shootDistance;
  }

  /**
   * validate shoot distance.
   *
   * @param input input of user
   * @return boolean
   */

  private boolean validatePlayerShootDistanceInput(String input) {
    return input.equals("1") || input.equals("2") || input.equals("3")
            || input.equals("4") || input.equals("5");
  }

  /**
   * method to get user input for pick.
   *
   * @param inputScanner scanner
   * @return validated input
   */

  @Override
  public String getPlayerPickInput(Scanner inputScanner) {
    String userInput = "";
    do {
      userInput = inputScanner.nextLine();
      if (!validatePlayerPickInput(userInput)) {
        System.out.println("Invalid input, try again.");
        System.out.println("Treasure or Arrow?");
      }
    }
    while (!validatePlayerPickInput(userInput));
    return userInput;
  }

  /**
   * validating the input of the player.
   *
   * @param input possible actions of the player
   * @return true or false according to validity of input
   */
  private boolean validateUserInput(String input) {
    return input.equals("M") || input.equals("P") || input.equals("S");
  }

  /**
   * validate player pick input.
   *
   * @param input inout of player
   * @return boolean
   */

  private boolean validatePlayerPickInput(String input) {
    return input.equals("Treasure") || input.equals("Arrow");
  }

  /**
   * validating the input moves of the player.
   *
   * @param playerPossibleLocations possible moves of the player
   * @param nextMove                move to the next node
   * @param showErrorMessage        if any other input is given
   * @param playerCurrentlyAt       current location of player
   * @return true or false according to validity of input
   */
  private boolean validatePlayerMoveInput(PlayerLocations playerPossibleLocations, String nextMove,
                                          boolean showErrorMessage, Node playerCurrentlyAt) {
    if (nextMove.equals("N") || nextMove.equals("E")
            || nextMove.equals("S") || nextMove.equals("W")) {
      if (nextMove.equals("N")) {
        if (playerPossibleLocations.hasNorth()) {
          return true;
        } else {
          if (showErrorMessage) {
            System.out.println("The input move North is not valid for this Cave ("
                    + playerCurrentlyAt.getX() + ", " + playerCurrentlyAt.getY()
                    + "), the possible moves are (West["
                    + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || North[" + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + ")");
          }
          return false;
        }
      } else if (nextMove.equals("E")) {
        if (playerPossibleLocations.hasEast()) {
          return true;
        } else {
          if (showErrorMessage) {
            System.out.println("The input move East is not valid for this Cave ("
                    + playerCurrentlyAt.getX()
                    + ", " + playerCurrentlyAt.getY() + "), the possible moves are (West["
                    + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || North[" + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + ")");
          }
          return false;
        }
      } else if (nextMove.equals("S")) {
        if (playerPossibleLocations.hasSouth()) {
          return true;
        } else {
          if (showErrorMessage) {
            System.out.println("The input move South is not valid for this Cave ("
                    + playerCurrentlyAt.getX() + ", " + playerCurrentlyAt.getY()
                    + "), the possible moves are (West["
                    + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || North[" + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + ")");
          }
          return false;
        }
      } else if (nextMove.equals("W")) {
        if (playerPossibleLocations.hasWest()) {
          return true;
        } else {
          if (showErrorMessage) {
            System.out.println("The input move West is not valid for this Cave ("
                    + playerCurrentlyAt.getX()
                    + ", " + playerCurrentlyAt.getY() + "), the possible moves are (West["
                    + playerPossibleLocations.getWest().getX() + ", "
                    + playerPossibleLocations.getWest().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || North[" + playerPossibleLocations.getNorth().getX() + ", "
                    + playerPossibleLocations.getNorth().getY() + "]:"
                    + playerPossibleLocations.hasNorth()
                    + " || East[" + playerPossibleLocations.getEast().getX() + ", "
                    + playerPossibleLocations.getEast().getY() + "]:"
                    + playerPossibleLocations.hasEast()
                    + " || South[" + playerPossibleLocations.getSouth().getX() + ", "
                    + playerPossibleLocations.getSouth().getY() + "]:"
                    + playerPossibleLocations.hasSouth()
                    + ")");
          }
          return false;
        }
      }
    } else if (showErrorMessage) {
      System.out.println("Invalid input...!");
    }
    return false;
  }


  /**
   * method to find if player is killed.
   *
   * @param otyughLocations   otyugh location
   * @param playerCurrentlyAt player location
   * @param loopNumber        number
   * @return value to determine if player dead
   */

  @Override
  public int isPlayerEatenByOtyugh(ArrayList<Otyugh> otyughLocations,
                                   Node playerCurrentlyAt, int loopNumber) {
    for (Otyugh eachOtyugh : otyughLocations) {
      if (eachOtyugh.getOtyughLocation().getX() == playerCurrentlyAt.getX()
              && eachOtyugh.getOtyughLocation().getY() == playerCurrentlyAt.getY()) {
        if (eachOtyugh.getOtyughHealth() == 100) {
          return 1;
        } else if (eachOtyugh.getOtyughHealth() == 50 && loopNumber % 2 == 0) {
          return 2;
        } else if (eachOtyugh.getOtyughHealth() == 50 && loopNumber % 2 != 0) {
          return 3;
        }
      }
    }
    return 4;
  }
}
