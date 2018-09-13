
package minesweeper;
/*
    MINESWEEPER GAME
    Version: 1.0
    Author: Carolina Dorado
    Date: 11/09/2018
 
 */
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
    
        int height =0;
        int width = 0;
        int mines = 0;
        boolean valid = false;
        
        Scanner inputScanner = new Scanner (System.in);
        
        System.out.println ("Welcome to MineSweeper Game :)");
        System.out.println ("To start the game you should write the height, width and number of mines of the game board."
                            + " (eg. 8 15 10).Then press enter");
        
        while(valid==false){            
                       
            height = inputScanner.nextInt();
            width = inputScanner.nextInt();  
            mines = inputScanner.nextInt(); 
                      
            if(height<=80 && width <=80 && mines < height*width){
                valid = true;
            }else{
                System.out.println("Invalid data. The height and width must be less than 80."
                                    + " The mines must be less than height*width. Try again.");
            }  
        }
        MineSweeper minesweeper = new MineSweeper(height,width,mines);
        minesweeper.manageGame();
    }
}
