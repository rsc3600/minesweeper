import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* Cell class for the Minesweeper game
 * A Cell is a box on the game grid.  The user will be able to click on the cell 
 * if s/he thinks there is no mine there.  If there is
 * no mine in the box and the user clicks it, it will display the number of 
 * mines around it.  If it is a mine, the game is over */

public class Cell{
     
    //store some information about the Cell
    private boolean isMine;
    private int numMines;
    private boolean isClicked;
    
    public Cell(boolean mine, int num){
        if (num > 8 || num < 0){
            throw new IllegalArgumentException("number of surrounding mines should be 0-8");
        }
        isMine = mine;
        numMines = num;
        isClicked = false;
    }
    
    public boolean isMine(){
        return isMine;
    }
    public int getNumMines(){
        return numMines;
    }
    public void setMine(boolean bool){
        isMine = bool;
    }
    public void click(){
        isClicked = true;
    }
    public void setNumMines(int num){
        if (num < 0 || num > 8){
            throw new IllegalArgumentException("invalid numMines");
        }else{
            numMines = num;
        }      
    }
    public boolean isClicked(){
        return isClicked;
    }
}