package tictactoe.com.tictacoe;

public class HumanPlayer {

    public void makeMove(Game g,int moveIndex,boolean turn){
        g.updateBoard(moveIndex,turn);
    }
}
