/* High score class to link a user's score to his name*/

public class HighScore implements Comparable<HighScore>{
    
    private int score;
    private String name;
    
    public HighScore(String name, int score){
        if (score < 0){
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.name = name;
        this.score = score;
    }
    
    public int getScore(){
        return score;
    }
    
    public String getName(){
        return name;
    }
    
    public int compareTo(HighScore otherHs) {
      if (this.getScore() > otherHs.getScore()){
          return 1;
      }
      else if (this.getScore() < otherHs.getScore()){
          return -1;
      }
      else {
          return 0;
      }
    }
}