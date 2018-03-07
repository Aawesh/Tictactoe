package tictactoe.com.tictacoe;

import android.widget.Button;

import java.util.*;

public class AIPlayer {
    private boolean turn;
    private String currentState;
    private int moveIndex;
    private static boolean terminalState;
    private HashMap<Integer,Button> buttonHashMap;

    public AIPlayer(){
        this.currentState = "";
        this.moveIndex = 0;
        terminalState = false;
    }

    public int makeMove(Game g,boolean turn){
        double [] actionValues;
        do{
            //0. If the current board status is not in the Q table, add an entry
            this.currentState = g.getBoard();
            AIActivity.qTable.saveState(this.currentState);

            //1. Select random AIActivity from the q table move if the reward is same for all or some move
            actionValues = AIActivity.qTable.getActionValueArray(this.currentState);

            this.moveIndex = getAIMove(actionValues,this.currentState);


        }while(!g.isValidMove(this.moveIndex));

        //3. update the board with AI move
        g.updateBoard(this.moveIndex,turn);

        //4. Update Q table for the move performed
        double reward = getReward(g.getBoard(),turn,g);

        AIActivity.qTable.updateQtable(this.currentState,this.moveIndex,reward,"ai");
        return moveIndex;
    }


    public static double getReward(String board,boolean turn,Game g){
        double reward;
        //0. Winner
        //1. Loser
        //2. Draw
        //3. Game continues

        double [] actionValues;

        int winner = checkWinner(board,g);
        switch (winner){
            case 0:
            case 1:
            case 2:
                terminalState = true;
                actionValues = AIActivity.qTable.getActionValueArray(board);
                if(actionValues != null){
                    reward = actionValues[0]; // if the state is in qtable get the value from 0th index
                }else{
                    AIActivity.qTable.saveState(board); //initializes all the action value pairs with 0
                    actionValues = AIActivity.qTable.getActionValueArray(board);
                    if((winner == 1 && turn == true) || (winner == 0 && turn == false)){
                        reward = 1;
                    }else if((winner == 1 && turn == false) || (winner == 0 && turn == true)){
                        reward = -1;
                    }else{ // draw = 2
                        reward = 0;
                    }
                    actionValues[0] = reward;//put the reward from the game
                }
                break;
            case 3:
            default:
                terminalState = false;
                actionValues = AIActivity.qTable.getActionValueArray(board);
                if(actionValues != null){
                    reward = getMaxValue(actionValues);
                }else{
                    AIActivity.qTable.saveState(board); //initializes all the action value pairs with 0
                    reward = 0;
                }
                break;
        }

        return reward;
    }

    public static int checkWinner(String board,Game g){
        char[] state = board.toCharArray();
        int[][] boardStatus = new int[3][3];
        int k = 0;
        for(int i = 0;i<3;i++){
            for (int j = 0;j<3;j++){
                boardStatus[i][j] = state[k] - '0';
                k++;
            }
        }

        //Horizontal --- rows
        for(int i=0; i<3; i++){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]){
                if (boardStatus[i][0]==1){
                    result("Player X wins");
                    return 1;
                }
                else if (boardStatus[i][0]==0) {
                    result("Player 0 wins");
                    return 0;
                }
            }
        }

        //Vertical --- columns
        for(int i=0; i<3; i++){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]){
                if (boardStatus[0][i]==1){
                    result("Player X wins");
                    return 1;
                }
                else if (boardStatus[0][i]==0) {
                    result("Player 0 wins");
                    return 0;
                }
            }
        }

        //First diagonal
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if (boardStatus[0][0]==1){
                result("Player X wins");
                return 1;
            }
            else if (boardStatus[0][0]==0) {
                result("Player 0 wins");
                return 0;
            }
        }

        //Second diagonal
        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]){
            if (boardStatus[0][2]==1){
                result("Player X wins");
                return 1;
            }
            else if (boardStatus[0][2]==0) {
                result("Player 0 wins");
                return 0;
            }
        }

        //2 means no moves and draw
        if(g.availableMoves() == 0){
            result("AI does Game Draw");
            return 2;
        }

        //3 means no winner, game continues
        return 3;
    }


    /**
     *
     * @return index of movement
     */
    public static int getAIMove(double [] actionValues,String state){
        int maxIndex = getIndexOfLargest(state,actionValues);
        System.out.println("maxIndex = " + maxIndex);
        System.out.println("state===" + state+"======");
        if(maxIndex != -1){
            List indexList = new ArrayList();
            char[] st = state.toCharArray();
            for ( int i = 0; i < actionValues.length; i++ ){
                if(st[i] == ' '){ //Exclude filled position for selection of next move
                    if ( actionValues[i] == actionValues[maxIndex] ){ //TODO must be equal to because we select random from same values
                        indexList.add(i);
                    }
                }
            }

            if(indexList.size() == 1){
                return (int) indexList.get(0);
            }else{
                Random randomizer = new Random();
                System.out.println("indexList.toString() = " + indexList.toString());
                System.out.println("randomizer.nextInt(intexList.size()) = " + randomizer.nextInt(indexList.size()));
                int random = (int) indexList.get(randomizer.nextInt(indexList.size()));
                return random;
            }
        }else{
            return -1;
        }
    }

    public static int getIndexOfLargest( String state, double[] array ){

        char[] st = state.toCharArray();

        if ( array == null || array.length == 0 ){
            return -1; // null or empty
        }

        int largest = 0; //Cannot say 0th index is the largest value, might be an invalid move
        //initializing largest to the first index that has empty space in the state
        for(int i = 0;i<array.length;i++){
            if(st[i] == ' '){
                largest = i;
            }
        }

        for ( int i = 1; i < array.length; i++ )
        {
            if(st[i] == ' '){ //Exclude filled position for selection of next move
                if ( array[i] > array[largest] ){
                    largest = i;
                }
            }
        }
        return largest; // position of the first largest found
    }

    public static double getMaxValue( double[] array ){
        if ( array == null || array.length == 0 ) return -1.0;

        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) largest = i;
        }
        return array[largest]; // position of the first largest found
    }

    public static void result(String s){
        System.out.println(s);
    }

    public String getCurrentState() {
        return this.currentState;
    }

    public int getMoveIndex() {
        return this.moveIndex;
    }

    public static void setTerminalState(boolean state){
        terminalState = state;
    }

    public static boolean isTerminalState(){
        return terminalState;
    }
}
