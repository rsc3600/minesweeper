/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    private GameBoard gameBoard;
    private JButton[] buttons;
    private JLabel status; // Current status text, i.e. "Running..."
    private int time;
    private Timer timer;
    private ArrayList<HighScore> scores = new ArrayList<>();
    private String name;
    private Writer out;
    private BufferedReader in;
    private int counter;
    
     // Game constants
    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 300;
    private final int INTERVAL = 1000;

    public GameCourt(JLabel status) {
            
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.status = status;
    }
    
    
    private JButton makeButton(int i,int j){
        JButton button = new JButton();
        button.addActionListener(new ActionListener() { 
          public void actionPerformed(ActionEvent e) {
             gameBoard.clickCell(i, j);
             repaint();              
             if (gameBoard.isWin()){
                updateScores();
                JOptionPane.showMessageDialog(null, "You are User" + counter, "CONGRATS!",
                                      JOptionPane.PLAIN_MESSAGE);
             }
          }
        });
        return button;
    }
    
    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        removeAll();
        gameBoard = new GameBoard();
        buttons = new JButton[81];
        setLayout(new GridLayout(9, 9, 0, 0));
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        int arrayCount = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                JButton button = makeButton(i, j);
                add(button);
                buttons[arrayCount] = button;
                arrayCount++;
            }
        }
        
        timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });

        time = 0;
        status.setText("Time: " + Integer.toString(time));
        
        String instructions = "WELCOME TO MINESWEEPER\n\n To play, click on a cell to reveal " +
                          "what's underneath it. Each cell\n either contains a mine, or a " +
                          "number representing the number\n of mines in the 8 adjacent cells." +
                          "Cells with no number or mine have\n no mines surrounding them.  The " +
                          "object of the game is to reveal  \nall of the cells that aren't mines" +
                          "in the shortest amount of time possible\nGOOD LUCK ;)";
        JOptionPane.showMessageDialog(null, instructions, "Instructions",
                                      JOptionPane.PLAIN_MESSAGE);
        timer.start();
     
    }
    
    public void updateScores(){
        
        scores = new ArrayList<>();
         try {
                  in = new BufferedReader(new FileReader("files/HighScores.txt"));
                  String firstLine = in.readLine();
                  counter = 0;
                  counter += Integer.parseInt(firstLine.substring(12, firstLine.length())) + 1;
                  name = "user" + Integer.toString(counter);
                  HighScore hs = new HighScore(name, time);
                  scores.add(hs);
                  in.readLine();
                  for(String line = in.readLine(); line != null; line = in.readLine()){
                      if (line.charAt(5) == ' '){
                          String oldName = line.substring(0, 5);
                          int score = Integer.parseInt(line.substring(6, line.length()));
                          HighScore hisc = new HighScore(oldName, score);
                          scores.add(hisc);
                      } else{
                          String oldName = line.substring(0,6);
                          int score = Integer.parseInt(line.substring(7, line.length()));
                          HighScore hisc = new HighScore(oldName, score);
                          scores.add(hisc);
                      }
                  }
                  Collections.sort(scores);
                  out = new BufferedWriter(new FileWriter("files/HighScores.txt", false));
                  out.write("HIGH SCORES " + counter + "\n\n");
                  for(HighScore his : scores){
                      out.write(his.getName() + " " + his.getScore() + "\n"); 
                    }
                  out.close();
                  in.close();
                } catch (IOException f){
                    throw new IllegalArgumentException("blahhhh");
                }
                
    }
    
    public void tick(){
        time++;
        status.setText("Time: " + Integer.toString(time));
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //losing case
        if(gameBoard.isLose()){
            timer.stop();
            status.setText("YOU LOSE :()");
            for (int i = 0; i < 81; i++){
              JButton button = new JButton();
              button = buttons[i];
              int col = (int) (i / 9);
              int row = i % 9;
              Cell cell = gameBoard.getCells()[col][row];
              if (cell.isMine()){
                  button.setText("X");
              }else if (cell.getNumMines() != 0){
                  button.setText(Integer.toString(cell.getNumMines()));
              }else {
                  button.setText("");
              }
              button.setForeground(Color.WHITE);
              button.setBackground(Color.RED);
              button.repaint();
            }
       }            
      
        //winning case
        if (gameBoard.isWin()){
            timer.stop();
            status.setText("YOU WIN :) Time : " + Integer.toString(time));
            for (int i = 0; i < 81; i++){
              JButton button = new JButton();
              button = buttons[i];
              int col = (int) (i / 9);
              int row = i % 9;
              Cell cell = gameBoard.getCells()[col][row];
              if (cell.isMine()){
                  button.setText("X");
              }else if (cell.getNumMines() != 0){
                  button.setText(Integer.toString(cell.getNumMines()));
              }else {
                  button.setText("");
              }
              button.setBackground(Color.GREEN);
              button.setForeground(Color.WHITE);
              button.repaint();
            }
            
        }
            
               
                
        
        
        
        
        for (int i = 0; i < 81; i++){
            JButton button = new JButton();
            button = buttons[i];
            int col = (int) (i / 9);
            int row = i % 9;
            Cell cell = gameBoard.getCells()[col][row];
            if (cell.isClicked()){
              if (cell.isMine()){
                  button.setText("X");
              }else if (cell.getNumMines() != 0){
                  button.setText(Integer.toString(cell.getNumMines()));
              }else {
                  button.setText("");
              }
              button.setForeground(Color.WHITE);
              button.setBackground(Color.BLUE);
            }
            button.repaint();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}