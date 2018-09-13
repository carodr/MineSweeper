
package minesweeper;

//Description: this class represents each of the cells in the board.
public class Square {
    
    private int content; 
    private int state;    

    Square(){
        content = 0;
        state = 0;
    }
	
    public int getContent() {
            return content;
    }
    public void setContent(int content) {
            this.content = content;
    }
    public int getState() {
            return state;
    }
    public void setState(int state) {
            this.state = state;
    }
    public void sumMine(){
        content++;
    }
}
