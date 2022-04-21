package com.dungeon;

import java.io.InputStreamReader;

/**
 * The Driver class Game in which the program executes. The maze generation is
 * done by following the steps of the kruskal's algorithm. The interconnectivity
 * is applied later to have multiple paths. They are classified into caves and
 * tunnels based on paths. Treasure is allocated to the given percentage of
 * caves. The random start and end are generated and the player execution
 * begins.
 */

public class Game {

  /**
   * The main method of the Game Class. The inputs required are obtained in this
   * class. The outputs are displayed with the help of print statements.
   *
   * @param args args
   */

  public static void main(String[] args) {

    //can be given in command line
    //has been assigned here to make jar file run
    args = new String[]{"4", "6", "8", "20", "true", "2"};

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    int rows;
    int cols;
    int interConnectivity;
    int percentageOfTreasure;
    boolean wrapping;
    int noofOtyughs;
    boolean needStaticGame = false;

    try {
      rows = Integer.parseInt(args[0]);
    } catch (final NumberFormatException e) {
      System.err.println("Rows must be a positive integer. Setting it to default value 4.");
      rows = 4;
    }

    try {
      cols = Integer.parseInt(args[1]);
    } catch (final NumberFormatException e) {
      System.err.println("Column must be a positive integer. Setting it to default value 6.");
      cols = 6;
    }

    try {
      interConnectivity = Integer.parseInt(args[2]);
    } catch (final NumberFormatException e) {
      System.err.println("Interconnectivity must be a positive integer. "
              + "Setting it to default value 8.");
      interConnectivity = 8;
    }

    try {
      percentageOfTreasure = Integer.parseInt(args[3]);
    } catch (final NumberFormatException e) {
      System.err.println("Percentage of treasure must be a positive integer between 0 to 100. "
              + "Setting it to default value 20.");
      percentageOfTreasure = 20;
    }

    try {
      wrapping = Boolean.parseBoolean(args[4]);
    } catch (final NumberFormatException e) {
      System.err.println("Wrapping must be a positive integer. Setting it to default value false.");
      wrapping = false;
    }

    try {
      noofOtyughs = Integer.parseInt(args[5]);
    } catch (final NumberFormatException e) {
      System.err.println("No Of Otyughs must be a positive integer. Setting it to default val 2.");
      noofOtyughs = 2;
    }

    if (rows < 0) {
      System.err.println("Rows cannot be negative. Setting it to default value 4.");
      rows = 4;
    }

    if (cols < 0) {
      System.err.println("Rows cannot be negative.  Setting it to default value 6.");
      cols = 6;
    }

    if (interConnectivity < 0) {
      System.err.println("Interconnectivity cannot be negative.  Setting it to default value 8.");
      interConnectivity = 8;
    }

    if (percentageOfTreasure < 0 || percentageOfTreasure > 100) {
      System.err.println("Percentage of treasure cannot be less that 0 or greater than 100. "
              + " Setting it to default value 20.");
      percentageOfTreasure = 20;
    }

    if (noofOtyughs < 1) {
      System.err.println("No of otyughs cannot be less than 1.  Setting it to default value 1.");
      noofOtyughs = 1;
    }

    //calling Game controller
    new GameController(input, output).playGame(rows, cols, interConnectivity,
            percentageOfTreasure, wrapping, noofOtyughs, needStaticGame);
  }


}
