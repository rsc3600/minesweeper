// JUnit tests for the different aspects of the cell and GameState classes
import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;


public class GameTest{
//TEST CELL CLASS
    @Test(timeout = 500)
    public void testCellConstr(){
        Cell cell = new Cell(true, 6);
        assertTrue("new mine is a mine", cell.isMine());
        assertEquals("constructor works numMines",cell.getNumMines(), 6);    
    }

    @Test(timeout = 500)
    public void testCellConstrInvalid(){
        try{
            Cell cell = new Cell(false, 11);
            fail("expected IllegalArgumentException: illegal numMines");
        } catch (IllegalArgumentException e){
            //do nothing
        }
    }
    
    //TEST GAMEBOARD
    //array used to place mines all in the left row
    int[] coords = {0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8};
    @Test(timeout = 500)
    public void testGameBoardConstr(){
        GameBoard gameBoard = new GameBoard();
        Cell[][] cells = gameBoard.getCells();
        assertEquals("creates 9 x 9 grid", cells.length, 9);
        assertEquals("creates 9 x 9 grid", cells[0].length, 9);
        assertFalse("isWin starts false", gameBoard.isWin());
        assertFalse("isLose starts false", gameBoard.isLose());
    }
    
    
    @Test(timeout = 500)
    public void testGameBoardNumMines(){
        GameBoard gameBoard = new GameBoard();
        Cell[][] cells = gameBoard.getCells();
        int numMines = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if(cells[i][j].isMine()){
                    numMines++;
                }
            }
        }
        assertEquals("9 mines placed", numMines, 9);
    }
     
    @Test(timeout = 500)
    public void testGameBoardCheckMines(){
        GameBoard gameBoard = new GameBoard(coords);
        Cell[][] cells = gameBoard.getCells();
        
        //check different cells
        assertEquals("check mines proper", gameBoard.checkMines(1, 4), 3);
        assertEquals("check mines no mines", gameBoard.checkMines(3, 4), 0);
        try{
             gameBoard.checkMines(0, 1);
            fail("expected IllegalArgumentException: checking mines arfound a mine");
        } catch (IllegalArgumentException e){
            //do nothing
        }
    }
    
    @Test(timeout = 500)
    public void testGameBoardIllegalInputs(){
        GameBoard gameBoard = new GameBoard();
        Cell[][] cells = gameBoard.getCells();
        
        try{
            gameBoard.checkMines(-1, 3);
            fail ("expected IllegalArgumentException: invalid input");
        } catch (IllegalArgumentException e){
            //do nothing
        }
        try{
            gameBoard.clickCell(1, 9);
            fail ("expected IllegalArgumentException: invalid input");
        } catch (IllegalArgumentException e){
            //do nothing
        }
    }
    
    //test clickcell mine
    @Test(timeout = 500)
    public void testClickCellClickMine(){
        GameBoard gameBoard = new GameBoard(coords);
        Cell[][] cells = gameBoard.getCells();

        gameBoard.clickCell(0,1);
        assertTrue("game should be over", gameBoard.isLose());
    }
    
    //test clickcell recClick
    @Test(timeout = 500)
    public void testClickCellNonMine(){
       GameBoard gameBoard = new GameBoard(coords);
        Cell[][] cells = gameBoard.getCells();
        
        gameBoard.clickCell(1,3);
        assertTrue("cell is clicked", cells[1][3].isClicked());
        gameBoard.clickCell(5,4);
        assertTrue("center cell is clicked", cells[5][4].isClicked());
        assertTrue("adjacent cell is clicked", cells[4][4].isClicked());
        assertTrue("far cells are clicked", cells[8][8].isClicked());
        assertTrue("edge cells are clicked", cells[1][6].isClicked());
        assertFalse("user did not lose", gameBoard.isLose());
    }

    @Test(timeout = 500)
      public void testClickCellWin(){
          GameBoard gameBoard = new GameBoard();
          Cell[][] cells = gameBoard.getCells();
        
          for(int i = 0; i < 9; i++){
              for (int j = 0; j < 9; j++){
                  if(!cells[i][j].isMine()){
                      gameBoard.clickCell(i, j);
                  }
              }
          }
          assertTrue("should win", gameBoard.isWin());
        }
        
}