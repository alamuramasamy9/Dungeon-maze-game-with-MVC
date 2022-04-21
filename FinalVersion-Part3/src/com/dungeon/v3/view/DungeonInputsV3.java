package com.dungeon.v3.view;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;


import com.dungeon.v3.control.DungeonControllerV3;

/**
 * The Dungeon inputs class is used to get the inputs from the user using a jFrame.
 * The inputs are validated here and passed as parameters for the dungeon.
 */
public class DungeonInputsV3 {

  private JFrame frame;
  private JFrame error = new JFrame("Invalid Input");

  /**
   * The constructor of the dungeon inputs class.
   * The various labels and test fields are created to get the required numerical values.
   * A drop down combo box is used to get true or false for wrapping.
   */

  public DungeonInputsV3() {
    frame = new JFrame("Dungeon - Get user inputs");
    JLabel labelRows = new JLabel("Rows");
    labelRows.setBounds(65, 68, 46, 14);
    frame.getContentPane().add(labelRows);

    JTextField textRows = new JTextField();
    textRows.setBounds(128, 65, 86, 20);
    textRows.setText("8");
    frame.getContentPane().add(textRows);
    textRows.setColumns(10);

    JLabel labelColumns = new JLabel("Columns");
    labelColumns.setBounds(65, 115, 46, 14);
    frame.getContentPane().add(labelColumns);

    JTextField textColumns = new JTextField();
    textColumns.setBounds(128, 112, 247, 17);
    textColumns.setText("10");
    frame.getContentPane().add(textColumns);
    textColumns.setColumns(10);

    JLabel labelInterconnection = new JLabel("Interconnection");
    labelInterconnection.setBounds(65, 115, 46, 14);
    frame.getContentPane().add(labelInterconnection);

    JTextField textInterconnection = new JTextField();
    textInterconnection.setBounds(128, 112, 247, 17);
    textInterconnection.setText("12");
    frame.getContentPane().add(textInterconnection);
    textInterconnection.setColumns(10);

    JLabel labelPercentageOfTreasure = new JLabel("Percentage Of Treasure (%)");
    labelPercentageOfTreasure.setBounds(65, 115, 46, 14);
    frame.getContentPane().add(labelPercentageOfTreasure);

    JTextField textPercentageOfTreasure = new JTextField();
    textPercentageOfTreasure.setBounds(128, 112, 247, 17);
    textPercentageOfTreasure.setText("50");
    frame.getContentPane().add(textPercentageOfTreasure);
    textPercentageOfTreasure.setColumns(10);

    JLabel lblOccupation = new JLabel("Wrapping");
    lblOccupation.setBounds(65, 288, 67, 14);
    frame.getContentPane().add(lblOccupation);

    JComboBox comboBox = new JComboBox();
    comboBox.addItem("False");
    comboBox.addItem("True");
    comboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        //to get wrapping value
      }
    });
    comboBox.setBounds(180, 285, 91, 20);
    frame.getContentPane().add(comboBox);

    JLabel labelNumberOfOtyughs = new JLabel("Number of Otyughs");
    labelNumberOfOtyughs.setBounds(65, 115, 46, 14);
    frame.getContentPane().add(labelNumberOfOtyughs);

    JTextField textNumberOfOtyughs = new JTextField();
    textNumberOfOtyughs.setBounds(128, 112, 247, 17);
    textNumberOfOtyughs.setText("4");
    frame.getContentPane().add(textNumberOfOtyughs);
    textNumberOfOtyughs.setColumns(10);


    BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
    Button play = new Button("Start Game");
    play.setFont(new Font("Verdana", Font.BOLD, 32));

    Button instruction = new Button("Instructions");
    instruction.setFont(new Font("Verdana", Font.BOLD, 32));

    frame.add(play);
    frame.add(instruction);

    frame.setLayout(boxLayout);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);


    play.addActionListener(new ActionListener() {

      /**
       * Function to get inputs from user and start game.
       * @param ae action
       */

      public void actionPerformed(ActionEvent ae) throws IllegalArgumentException {
        try {
          int rows = Integer.parseInt(textRows.getText());
          int cols = Integer.parseInt(textColumns.getText());
          int interConnectivity = Integer.parseInt(textInterconnection.getText());
          int percentageOfTreasure = Integer.parseInt(textPercentageOfTreasure.getText());
          boolean wrapping = Boolean.parseBoolean(comboBox.getSelectedItem().toString());
          int noofOtyughs = Integer.parseInt(textNumberOfOtyughs.getText());
          boolean needStaticGame = false;
          frame.dispose();
          new DungeonControllerV3().init(rows, cols, interConnectivity,
                  percentageOfTreasure, wrapping, noofOtyughs, needStaticGame);
        } catch (Exception e) {
          JLabel textLabel = new JLabel("You have entered an invalid input. Please try again");
          error.getContentPane().add(textLabel, BorderLayout.CENTER);

          error.setLocationRelativeTo(null);
          error.pack();
          error.setVisible(true);
          throw new IllegalArgumentException("Invalid Values");
        }
      }
    });

    instruction.addActionListener(new ActionListener() {

      /**
       * Function to display instructions on button click.
       * @param ae action
       */

      public void actionPerformed(ActionEvent ae) {
        JFrame frame = new JFrame("Instructions");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JLabel textLabel = new JLabel("" + "<html>" +
                "1. Player starts at the position that appears.<br/>"
                + "The red robot icon is used to indicate the player.<br/>"
                + "2. The maze will appear as the caves and tunnels are visited<br/>"
                + "3. Keyboard Arrow keys can be used to move the player<br/>"
                + "4. Top Arrow -> North, Right Arrow -> East, Bottom Arrow "
                + "-> South, Left Arrow -> West<br/>"
                + "5. Press p to pick up treasures and arrows <br/>"
                + "6. Press s to shoot followed by the arrow key for direction to shoot. <br/>"
                + "7. The Options menu has the option to show and hide player"
                + " and dungeon description.<br/>"
                + "8. The Game menu has the options for new game, reset and quit<br/>."
                + "9. Help has instructions that pop up."
                + "10. The game ends when Player wins or dies.<br/>"
                + "</html>");
        frame.getContentPane().add(textLabel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
      }
    });
  }
}
