package tictactoe.com.tictacoe;

public class Game {
    private String boardStatus;
    private double temperature;

    public Game(double tempaerature){
        boardStatus = "         ";
        this.temperature = tempaerature;
    }

    public void updateBoard(int actionIndex,boolean turn){
        char[] state = boardStatus.toCharArray();
        state[actionIndex] = turn?'1':'0';
        boardStatus = new String(state);
    }

    public String getBoard(){
        return this.boardStatus;
    }

    public boolean isValidMove(int moveIndex){
        if(moveIndex <0 || moveIndex > 8){
            return false;
        }else{
            char[] state = boardStatus.toCharArray();
            if(state[moveIndex] == ' '){
                return true;
            }else{
                return false;
            }
        }
    }

    public void resetBoard(){
        boardStatus = "         ";
    }

    public double getTemperature(){
        return this.temperature;
    }
}
