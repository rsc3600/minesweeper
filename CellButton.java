import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CellButton extends JButton{
    
    private int x;
    private int y;
    
    CellButton(){
        super();
    }
    
    public void setX(int xcord){
        if (xcord > 8 || xcord < 0){
            throw new IllegalArgumentException("please enter valid coord");
        }
        x = xcord;
    }
    
    public void setY(int ycord){
        if (ycord > 8 || ycord < 0){
            throw new IllegalArgumentException("please enter valid coord");
        }
        y = ycord;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    
}