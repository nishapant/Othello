/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello.pkgfinal;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

/**
 *
 * @author pant9060
 */
public class FXMLDocumentController implements Initializable {
    ImageView[][] btn = new ImageView[6][6];
    //Create a 2 dimensional data array.  It will contain 0,1,2 for black, blue, or empty
    //8 by 8 array to represent spots
    int[][] spaces = new int[6][6];
    //Create a single dimension arrray or arraylist of Strings to store the path
    //names of the images.  Put the images into the resource folder.
    ArrayList<String> pictures = new ArrayList<>();
    private int pTurn;
    private int p1Points=2;
    private int p2Points=2;
    private String Player1="player1";
    private String Player2="player2";
    
    @FXML
    private AnchorPane aPane;
    
    @FXML
    private GridPane gPane;
    
    @FXML 
    private MenuBar menu;
    
    //you will eventually need more xml to make things like a menu to start,
    //reset button, place for names, who wins, etc...
    @FXML
    private Label player1, player2, p1points, p2points, messages;
    
    @FXML
    private void handleStart(ActionEvent event) {
        //Put the path name strings into your String array.
        pictures.add("resources/blue.png");
        pictures.add("resources/black.png");
        pictures.add("resources/white.png");
       
               
        
        
        //put the 0,1,2 into your data array
            //Use a double loop to put in the 2's.  Then add 4 lines of code to
            //ovewrite the 2's with 1's and 0's to make the innitial board.
        for(int i=0; i<spaces.length; i++){
            for(int k=0; k<spaces.length;k++){
                spaces[i][k] = 2;
            }
        }
        printArray();
        spaces[2][2]= 1;
        spaces[3][2]=0;
        spaces[2][3]=0;
        spaces[3][3]=1;
        printArray();
        
        for(int i=0; i<btn.length; i++){
                for(int j=0; j<btn.length;j++){
                        btn[i][j] = new ImageView();
                        //The line below will add the 2 (blank) image to the imagviews.
                        //you can add if thens and additional setImage lines so 
                        //that the image will be correct based on the data array.
                        
                        if(spaces[i][j]== 0){
                            btn[i][j].setImage(new Image(pictures.get(0)));
                        } else if(spaces[i][j]== 1){
                            btn[i][j].setImage(new Image(pictures.get(1)));
                        } else{
                            btn[i][j].setImage(new Image(pictures.get(2)));
                        }
                        
                        btn[i][j].setFitHeight(67);
                        btn[i][j].setFitWidth(67);
                        //Paramters:  object, columns, rows
                        gPane.add(btn[i][j], j, i);
                        
                        }
        }
        gPane.setGridLinesVisible(true);
        gPane.setVisible(true);
        
        //call set random player method
        setRandomPlayer();
        if(pTurn == 0){
            messages.setText("player1 goes first. player 1 is blue");
        }else{
            messages.setText("player 2 goes first. player 2 is black.");
        }
        
        EventHandler z = new EventHandler<MouseEvent>() 
        {
            
            @Override
            public void handle(MouseEvent t) 
            {
                if(!(spaces[GridPane.getRowIndex(((ImageView) t.getSource()))]
                            [GridPane.getColumnIndex(((ImageView) t.getSource()))] == 2)){
                    return;
                }
                if(pTurn == 0){
                    ((ImageView) t.getSource()).setImage(new Image(pictures.get(0)));
                    spaces[GridPane.getRowIndex(((ImageView) t.getSource()))]
                            [GridPane.getColumnIndex(((ImageView) t.getSource()))] = 0;
                }else{
                    ((ImageView) t.getSource()).setImage(new Image(pictures.get(1)));
                    spaces[GridPane.getRowIndex(((ImageView) t.getSource()))]
                            [GridPane.getColumnIndex(((ImageView) t.getSource()))] = 1;
                }
                //All method calls dealing with buttons being clicked must be here!
                
                //call method to decide if the user clicked on a valid space.
                //This method should be done last
                
                //change the string in the line below to put the correct image
                //on the imageview based on the pturn variable.  pturn will represent
                //the index of the string array that contains the image path names.
                
                //Replace the sout's with a row and column variable that will store
                //the rowindex and columnindex.  I would still system out for 
                //test purposes.
                System.out.println("Row:    " + GridPane.getRowIndex(((ImageView) t.getSource())));
                System.out.println("Column: " + GridPane.getColumnIndex(((ImageView) t.getSource())));
                //change the data array using row,column to match the player turn
                
                //Print the data array so you can compare the before and after
                
                //call the horizontal check method
                checkHorizontal(GridPane.getRowIndex(((ImageView) t.getSource()))
                        ,GridPane.getColumnIndex(((ImageView) t.getSource())));
               
                //call the vertical check method
                 checkVertical(GridPane.getRowIndex(((ImageView) t.getSource()))
                        ,GridPane.getColumnIndex(((ImageView) t.getSource())));
                //call the diagonal check method here
                 checkDiagonals(GridPane.getRowIndex(((ImageView) t.getSource()))
                        ,GridPane.getColumnIndex(((ImageView) t.getSource())));
                //call the check winner method
                //call the change player method
                
                //Print the data array so you can compare the before and after
                printArray();
                changePlayer();
                if(pTurn == 0){
                    messages.setText("player 1's turn. player 1 is blue");
                }else{
                    messages.setText("player 2's turn. player 2 is black");
                }
                calculatePoints();
                p1points.setText(""+p1Points);
                p2points.setText(""+p2Points);
                checkWinner();
            }
            
        };
        for(int i=0; i<btn.length; i++){
                for(int j=0; j<btn.length;j++){
                    btn[i][j].setOnMouseClicked(z);
    
                        }
        }
        
         
    }
    

    //create all of your methods here

    private void printArray(){
        System.out.println("");
        for(int i = 0; i<spaces.length; i++){
            for(int k = 0; k<spaces.length; k++){
                System.out.print(spaces[i][k]+ "|");
            }
            System.out.println("");
        }
         
    }
    
    private void setRandomPlayer(){
        int randomNumber = (int)(Math.random()*2);
        if(randomNumber == 0){
            pTurn = 0;
        }else{
            pTurn = 1;
        }
    }
    
    private void changePlayer(){
        pTurn++;
        if(pTurn == 2){
            pTurn = 0;
        }
    }
    
    private void checkHorizontal(int rowPressed, int columnPressed){
        outerLoop:
        for(int i=0; i<columnPressed; i++){
            if(spaces[rowPressed][i]==pTurn){
                for(int k=i+1; k<columnPressed; k++){
                    
                    spaces[rowPressed][k] = pTurn;
                    btn[rowPressed][k].setImage(new Image(pictures.get(pTurn)));  
                }
                
                break;
            }
        }
        
        outerLoop2: 
        for(int i=spaces.length-1; i>columnPressed; i--){
            if(spaces[rowPressed][i]==pTurn){
                for(int k=i-1; k>columnPressed; k--){
                    spaces[rowPressed][k] = pTurn;
                    btn[rowPressed][k].setImage(new Image(pictures.get(pTurn))); 
                }
                break;
            }
        }
        
    }
    
        private void checkVertical(int rowPressed, int columnPressed){
        outerLoop:
        for(int i=0; i<rowPressed; i++){
            if(spaces[i][columnPressed]==pTurn){
                for(int k=i+1; k<rowPressed; k++){
                spaces[k][columnPressed] = pTurn;
                btn[k][columnPressed].setImage(new Image(pictures.get(pTurn)));  
                }

                break;
            }
        }
        
        outerLoop2: 
        for(int i=spaces.length-1; i>rowPressed; i--){
            if(spaces[i][columnPressed]==pTurn){
                for(int k=i-1; k>rowPressed; k--){
                spaces[k] [columnPressed]= pTurn;
                btn[k][columnPressed].setImage(new Image(pictures.get(pTurn)));   
               }

              break;
            }

        }
        
    }
     
    private void checkDiagonals(int rowPressed, int columnPressed){
        int rowCounter = rowPressed-1;
        outerLoop:
        for(int i = columnPressed+1; i<spaces.length && rowCounter >= 0; i++){
            if(spaces[rowCounter][i]==pTurn){
              for(int k=columnPressed+1;k<i;k++){
                    rowCounter++;
                    spaces[rowCounter][k]= pTurn;
                    btn[rowCounter][k].setImage(new Image(pictures.get(pTurn))); 
              }
              break;
            }
          rowCounter--;
        }
        
        
        rowCounter = rowPressed +1;
        outerLoop2:
        for(int i = columnPressed-1; i>0 && rowCounter < spaces.length; i--){
            if(spaces[rowCounter][i]==pTurn){
              for(int k=columnPressed-1;k>i;k--){
                    rowCounter--;
                    spaces[rowCounter][k]= pTurn;
                    btn[rowCounter][k].setImage(new Image(pictures.get(pTurn))); 
              }
              break;
            }
          rowCounter++;
        }
        
        
        
        rowCounter = rowPressed-1;
        outerLoop3:
        for(int i = columnPressed-1; i>0 && rowCounter > 0; i--){
            if(spaces[rowCounter][i]==pTurn){
                for(int k=columnPressed-1;k>i;k--){
                    rowCounter++;
                    spaces[rowCounter][k]= pTurn;
                    btn[rowCounter][k].setImage(new Image(pictures.get(pTurn))); 
              }
              break;
            }
          rowCounter--;
        }
        
        
        
        rowCounter = rowPressed+1;
        outerLoop4:
        for(int i = columnPressed-1; i>0 && rowCounter < spaces.length; i--){
            if(spaces[rowCounter][i]==pTurn){  
            for(int k=columnPressed-1;k>i;k--){
                    rowCounter--;
                    spaces[rowCounter][k]= pTurn;
                    btn[rowCounter][k].setImage(new Image(pictures.get(pTurn))); 
            }
              break;
            }
          rowCounter++;
        }
       
    }
    
    public void checkWinner(){
        int counter=0; 
        for(int i=0; i<spaces.length; i++){
            for(int k=0; k<spaces.length;k++){
                if(spaces[i][k] == 2){
                    counter++;
                }
                
            }
        }
        if(counter==0){
            if(p1Points>p2Points){
                messages.setText(player1+ " won");
            }
            else{
                messages.setText(player2+ " won");
            }
        }
    }
    
    private void calculatePoints(){
        p1Points = 0;
        p2Points = 0;
        for(int i=0; i<spaces.length; i++){
            for(int k=0; k<spaces.length;k++){
                if(spaces[i][k] == 0){
                    p1Points++;
                }
                if(spaces[i][k]==1){
                    p2Points++;
                }
                
            }
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
