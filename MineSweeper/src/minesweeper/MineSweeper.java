
package minesweeper;

import java.util.Scanner;

public class MineSweeper 
{
    private int height;
    private int width;
    private int mines;
    private int rightFlags;
    private int totalFlags;
    private boolean endGame;
    private Square gameBoard[][];
  
    //Description: this class is responsible for controlling the logic of the game.
    
    MineSweeper(int heightChosen, int widthChosen , int minesChosen){    
        
        height = heightChosen;
        width = widthChosen;
        mines = minesChosen;
        endGame = false;        
           
        gameBoard = new Square[height][width];
	for (int i=0; i<height; i++){            
            for(int j=0; j<width; j++){
		gameBoard[i][j] = new Square();				
            }	
	}        
        putMines();   
    }
    
    //Place all the mines(number of mines chosen by the player) on the board randomly.
    public void putMines(){    
        
        int randomRow = 0; 
        int randomColumn = 0; 
        int activeMines = 0;
	
	for(int i=0; activeMines < mines; i++){            
            randomRow =(int) (Math.random()*height);
            randomColumn = (int) (Math.random()*width);
            if(	gameBoard[randomRow][randomColumn].getContent() != 9){
		gameBoard[randomRow][randomColumn].setContent(9);
		activeMines++;
                registerMines(randomRow,randomColumn);
            }
	}
    }
    
    //Given the position of a mine, add 1 to all the squares around the mine (8 posible squares around), 
    //which represents the number of adjacent mines.
    //Input: row and column where the mine is located.
    public void registerMines(int row, int column){
             
        if( (row-1 >= 0) && (column-1 >= 0) && (gameBoard[row-1][column-1].getContent() != 9)){
            gameBoard[row-1][column-1].sumMine();
        }
        if( (row-1 >= 0) && (gameBoard[row-1][column].getContent() != 9)){
            gameBoard[row-1][column].sumMine();
        }
        if( (row-1 >= 0) && (column+1 < width) && (gameBoard[row-1][column+1].getContent() != 9)){
            gameBoard[row-1][column+1].sumMine();
        }
        if((column-1 >= 0) && (gameBoard[row][column-1].getContent() != 9)){
            gameBoard[row][column-1].sumMine();
        }
        if((column+1 <width) && (gameBoard[row][column+1].getContent() != 9)){
            gameBoard[row][column+1].sumMine();
        }
        if( (row+1 < height) && (column-1 >= 0) && (gameBoard[row+1][column-1].getContent() != 9)){
            gameBoard[row+1][column-1].sumMine();
        }
        if( (row+1 < height) && (gameBoard[row+1][column].getContent() != 9)){
            gameBoard[row+1][column].sumMine();
        }
        if( (row+1 < height) && (column+1 < width) && (gameBoard[row+1][column+1].getContent() != 9)){
            gameBoard[row+1][column+1].sumMine();
        }    
    }

    //This method is responsible for uncovering all the adjacent squares in case the player selects 
    //a cell that does not contain a mine.
    //Input: row and column of the selected cell.
    public void uncoverCells(int row, int column){  
        
        if(gameBoard[row][column].getContent() == 0){ 
            gameBoard[row][column].setState(1);
            if( (row-1 >= 0) && (column-1 >= 0) && validCell(row-1,column-1)){
                uncoverCells(row-1,column-1);              
            }      
            if( (row-1 >= 0) && validCell(row-1,column)){
                uncoverCells(row-1,column);              
            } 
            if( (row-1 >= 0) && (column+1 < width) &&  validCell(row-1,column+1)){
                uncoverCells(row-1,column+1);              
            }
            if( (column-1 >= 0) &&  validCell(row,column-1)){
                uncoverCells(row,column-1);              
            }
            if( (column+1 < width) &&  validCell(row,column+1)){
                uncoverCells(row,column+1);              
            }
            if( (row+1 < height) && (column-1 >= 0) &&  validCell(row+1,column-1)){
                uncoverCells(row+1,column-1);              
            }
            if( (row+1 < height) &&  validCell(row+1,column)){
                uncoverCells(row+1,column);              
            }
            if( (row+1 < height) && (column+1 < width) &&  validCell(row+1,column+1)){
                uncoverCells(row+1,column+1);              
            }            
        }else if(gameBoard[row][column].getContent() == 9){
            endGame(1);
        }else{
            if((gameBoard[row][column].getState() == 2)){
                totalFlags--;
            }
            gameBoard[row][column].setState(1);             
        }      
    }    
    
    //Evaluate if a cell is suitable to be uncovered automatically (when the player selects a square that is not mine). 
    //A cell is suitable to be uncovered if it is not a mine, and it is covered.
    //Input: row and column of the cell that is going to be evaluated.
    public boolean validCell(int row, int column){
        if((gameBoard[row][column].getContent() != 9)&& (gameBoard[row][column].getState() == 0)){
            return true;
        }
        else{ 
            return false;
        }
    }  

    //Update in each move(when the player use the M of mark) the number of total flags and correct flags on the board.
    //When all (and only) the squares containing mines are marked, the game ends.
    //Input: row and column of the actual move.
    public void updateFlags(int row, int column){

        if(gameBoard[row][column].getState()== 0){
            gameBoard[row][column].setState(2);
            if(gameBoard[row][column].getContent() == 9){
                rightFlags++;
                totalFlags++;
            }else{
                totalFlags++;
            }
        }else if(gameBoard[row][column].getState()== 2){
            gameBoard[row][column].setState(0);
            if(gameBoard[row][column].getContent() == 9){
                rightFlags--;
                totalFlags--;
            }else{
                totalFlags--;
            }        
        }
        if((rightFlags == mines) && (totalFlags == rightFlags)){
            endGame(2);
        }       
    }
    
    //Is responsible for finishing the game in case of losing or winning.
    //Input:number that indicates if the player lost(1) or won(2)
    public void endGame(int result){
        if(result == 1){
            showMines();
            printBoard();
            System.out.println("Game Over. You lost :(");
        } else{
            printBoard();
            System.out.println("Congratulations. You won :)");
        } 
        endGame = true;
    }
    
    //Uncover the cells with mines
    public void showMines(){
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                if(gameBoard[i][j].getContent()==9){
                    gameBoard[i][j].setState(1);
                }
            }
        }        
    }
  
    //Print the board
    public void printBoard(){
        System.out.println();
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                if(gameBoard[i][j].getState() == 1){
                    
                    switch(gameBoard[i][j].getContent()){
                        case 0:
                            System.out.print("- ");
                            break;
                        case 9:
                            System.out.print("* ");
                            break;
                        default:
                            System.out.print(gameBoard[i][j].getContent() + " ");
                            break; 
                    }
                } else if(gameBoard[i][j].getState() == 2){
                    System.out.print("P ");
                } else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }
    
    
    //Validates if the entered data is correct.
    public boolean validations(int row, int column, String action){
        
        if((row >= height) || (column >= width)){
            System.out.println("The height and width must be less than " + height + " and "+ width + " respectively");
            return false;
        }
        if(!((action.equals("M")) || (action.equals("m")) || (action.equals("U")) || (action.equals("u")))){
            System.out.println("Invalid action. Please try again."); 
            return false;
        }        
        return true;  
    }
    
    //Method that manages the moves in the game.
    public void manageGame(){
        
        int row = 0;
        int column = 0; 
        String action = " ";
        Scanner inputScanner = new Scanner (System.in);   
        
        System.out.println("To make a move please enter the row, column and action. Use M to mark and U to uncover. (eg. 3 6 U)");
        
        while(endGame == false){ 
            printBoard();            
            System.out.println("Enter your next move...");
            
            row = inputScanner.nextInt();
            column = inputScanner.nextInt();
            action = inputScanner.next();
            
            if(validations(row, column, action)){
                if((action.equals("M")) || (action.equals("m"))){
                    updateFlags(row,column);
                }else{
                    uncoverCells(row,column); 
                }                
            }
        }
    }
}
