 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



/** grid class: makes a new  9x9 Grid of Cells with 9 mines randomly placed
 * 
 */

public class GameBoard {
    
    private Cell[][] cells;
    private boolean isWin;
    private boolean isLose;
    private int clickCount;
    
    // constructor that generates a grid with mines placed randomly
    public GameBoard(){
        cells = new Cell[9][9];
        newGrid();
        isWin = false;
        isLose = false;
    }
    
    //constructor that places mines at given coordinates. Used for testing
    public GameBoard(int[] coords){
        cells = new Cell[9][9];
        newGrid(coords);
        isWin = false;
        isLose = false;
    }
    
    public Cell[][] getCells(){
        Cell[][] copyCells= new Cell[9][9];
        for(int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                copyCells[i][j] = cells[i][j];
            }
        }
        return copyCells;
    }
    
    public boolean isWin(){
        return isWin;
    }
    
    public boolean isLose(){
        return isLose;
    }
    
    private void newGrid() {
         for(int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell(false, 0);    
            }
        }
        int counter = 0;
        int  randomCol = 0;
        int randomRow = 0;
        while(counter < 9){
            randomCol = (int) (Math.random() * 9);
            randomRow = (int) (Math.random() * 9);
            Cell tgtCell = cells[randomCol][randomRow];
            if(!tgtCell.isMine()){
                tgtCell.setMine(true);
                counter++;
            }
        }
        
          for(int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                if(!cells[i][j].isMine()){
                    int numMines = checkMines(i, j);
                    Cell tgtCell = cells[i][j];
                    tgtCell.setNumMines(numMines);
                }
            }
          }
    }
    
    //creates a new grid with mines in given coordinates.  First int is the x coord of the first
    //mine, followed by the y coordinate of the first mine, then the same for the other 8 mines
    private void newGrid(int[] coords) {
         for(int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell(false, 0);    
            }
        }
        
        for (int i = 0; i < 16; i += 2){
            int xcoord = coords[i];
            int ycoord = coords[i + 1];
            cells[xcoord][ycoord].setMine(true);
        }
        
          for(int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                if(!cells[i][j].isMine()){
                    int numMines = checkMines(i, j);
                    Cell tgtCell = cells[i][j];
                    tgtCell.setNumMines(numMines);
                }
            }
          }
    }
    
    //helper method to check the number of mines around a Cell
    public int checkMines(int col, int row){
        if(col < 0 || col > 8 || row < 0 || row > 8){
            throw new IllegalArgumentException("invalid cell index");
        }
        if(cells[col][row].isMine()){
            throw new IllegalArgumentException("can't check mines around a mine");
        }
        int leftBound = Math.max(col - 1, 0);
        int rightBound = Math.min(col + 1, 8);
        int upperBound = Math.max(row - 1, 0);
        int lowerBound = Math.min(row + 1, 8);
        int numMines = 0;
        for(int i = leftBound; i <= rightBound; i++){
            for(int j = upperBound; j <= lowerBound; j++){
                if(cells[i][j].isMine()){
                    numMines++;
                }
            }
        }
        return numMines;
    }

    //handles clicks on a cell at a given index
    public void clickCell(int col, int row){
        if(col < 0 || col > 8 || row < 0 || row > 8){
            throw new IllegalArgumentException("invalid cell index");
        }
        Cell cell= cells[col][row];
        if (cell.isMine()){
            cell.click();
            isLose = true;
        }else if(cell.getNumMines() == 0){
            recClick(col, row);
        } else{
            if(!cell.isClicked()){
              clickCount++;
            }
            cell.click();
        }
        //check win
        if (clickCount == 72){
            isWin = true;
        }
    }

    //function that recursively clicks the cells around a cell with no mines around it
    private void recClick(int col, int row){
        if(col < 0 || col > 8 || row < 0 || row > 8){
            throw new IllegalArgumentException("invalid cell index");
        }
        if(!cells[col][row].isClicked()){
            clickCount++;
        }
        cells[col][row].click();
        int leftBound = Math.max(col - 1, 0);
        int rightBound = Math.min(col + 1, 8);
        int upperBound = Math.max(row - 1, 0);
        int lowerBound = Math.min(row + 1, 8);
        for(int i = leftBound; i <= rightBound; i++){
            for(int j = upperBound; j <= lowerBound; j++){
                if (i != col || j != row){
                    Cell cell = cells[i][j];
                    if (cell.isMine() || cell.isClicked()){
                        //do nothing to it 
                    } else if (cell.getNumMines() == 0){
                        recClick(i, j);
                    } else {
                        if(!cell.isClicked()){
                          clickCount++;
                        }
                        cell.click();
                    }
                }  
            }
        }
        
    }
}