/**
 * Created by aawesh on 3/6/18.
 */

package tictactoe.com.tictacoe;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import java.util.HashMap;

/**
 * Created by aawesh on 1/19/18.
 */

public class AIActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = MainActivity.class.getSimpleName();
    int[][] boardStatus;
    boolean PLAYER_X = true;

    Button[][] button;

    Button menu_button;
    Button rematch_button;

    TextView tvInfo;
    TextView header;
    String headerString;

    int TURN_COUNT = 0;


    private static HumanPlayer humanPlayer;
    private static AIPlayer aiPlayer;
    public static QTable qTable;
    private static Game game;
    private boolean turn;
    private boolean swapTurn;

    private HashMap<Integer,Button> buttonHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button = new Button[3][3];

        button[0][0] = findViewById(R.id.b00);
        button[0][1] = findViewById(R.id.b01);
        button[0][2] = findViewById(R.id.b02);

        button[1][0] = findViewById(R.id.b10);
        button[1][1] = findViewById(R.id.b11);
        button[1][2] = findViewById(R.id.b12);

        button[2][0] = findViewById(R.id.b20);
        button[2][1] = findViewById(R.id.b21);
        button[2][2] = findViewById(R.id.b22);

        menu_button = findViewById(R.id.menu_button);
        rematch_button = findViewById(R.id.rematch_button);

        tvInfo = findViewById(R.id.tvInfo);
        header = findViewById(R.id.header);

        gamestatus("START");

        for (int i =0;i<3;i++){
            for (int j=0;j<3;j++){
                button[i][j].setOnClickListener(this);
            }
        }

        menu_button.setOnClickListener(this);
        rematch_button.setOnClickListener(this);

        boardStatus = new int[3][3];

        initializeBoardStatus();

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b.getString("player").equalsIgnoreCase("ai1")){
            game = new Game(0.5);
            headerString = "LEVEL - EASY";
        }else{
            game = new Game(0.005);
            headerString = "LEVEL - HARD";
        }

        header.setText(headerString);

        buttonHashMap = new HashMap<>();
        buttonHashMap.put(0,button[0][0]);
        buttonHashMap.put(1,button[0][1]);
        buttonHashMap.put(2,button[0][2]);
        buttonHashMap.put(3,button[1][0]);
        buttonHashMap.put(4,button[1][1]);
        buttonHashMap.put(5,button[1][2]);
        buttonHashMap.put(6,button[2][0]);
        buttonHashMap.put(7,button[2][1]);
        buttonHashMap.put(8,button[2][2]);

        play();

    }

    private void play() {
        game.resetBoard();

        aiPlayer = new AIPlayer();
        humanPlayer = new HumanPlayer();

        resetBoard(); //reset display


    }

    void askFirstPlayer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to go first?");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        swapTurn = false;
                        header.setText(headerString+"  1P HUMAN(X)");

                        qTable = new QTable("qtable_ai_second.txt");

                        if(swapTurn){   // if true then AI first
                            turn = false; // AI always play 0
                            int index = aiPlayer.makeLearnedMove(game,turn);
                            updateBoardStatus(index); //perform AI move in board
                        }
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        swapTurn = true;
                        header.setText(headerString + "  1P AI(O)");

                        qTable = new QTable("qtable_ai_first.txt");


                        if(swapTurn){   // if true then AI first
                            turn = false; // AI always play 0
                            System.out.println("AI turn = " + turn);
                            int index = aiPlayer.makeLearnedMove(game,turn);
                            updateBoardStatus(index); //perform AI move in board
                        }
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    private void initializeBoardStatus(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boardStatus[i][j] = -1;
                button[i][j].setTextSize(40);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Inside onClick");

        turn = !turn;

        switch (view.getId()){
            case R.id.b00:
                if(turn){
                    button[0][0].setText("X");
                    boardStatus[0][0] = 1;
                }
                else{
                    button[0][0].setText("O");
                    boardStatus[0][0] = 0;
                }
                humanPlayer.makeMove(game,0,turn);
                button[0][0].setEnabled(false);
                break;

            case R.id.b01:
                if(turn){
                    button[0][1].setText("X");
                    boardStatus[0][1] = 1;
                }
                else{
                    button[0][1].setText("O");
                    boardStatus[0][1] = 0;
                }
                humanPlayer.makeMove(game,1,turn);

                button[0][1].setEnabled(false);
                break;

            case R.id.b02:
                if(turn){
                    button[0][2].setText("X");
                    boardStatus[0][2] = 1;
                }
                else{
                    button[0][2].setText("O");
                    boardStatus[0][2] = 0;
                }
                humanPlayer.makeMove(game,2,turn);

                button[0][2].setEnabled(false);
                break;

            case R.id.b10:
                if(turn){
                    button[1][0].setText("X");
                    boardStatus[1][0] = 1;
                }
                else{
                    button[1][0].setText("O");
                    boardStatus[1][0] = 0;
                }
                humanPlayer.makeMove(game,3,turn);

                button[1][0].setEnabled(false);
                break;

            case R.id.b11:
                if(turn){
                    button[1][1].setText("X");
                    boardStatus[1][1] = 1;
                }
                else{
                    button[1][1].setText("O");
                    boardStatus[1][1] = 0;
                }
                humanPlayer.makeMove(game,4,turn);

                button[1][1].setEnabled(false);
                break;

            case R.id.b12:
                if(turn){
                    button[1][2].setText("X");
                    boardStatus[1][2] = 1;
                }
                else{
                    button[1][2].setText("O");
                    boardStatus[1][2] = 0;
                }
                humanPlayer.makeMove(game,5,turn);

                button[1][2].setEnabled(false);
                break;

            case R.id.b20:
                if(turn){
                    button[2][0].setText("X");
                    boardStatus[2][0] = 1;
                }
                else{
                    button[2][0].setText("O");
                    boardStatus[2][0] = 0;
                }
                humanPlayer.makeMove(game,6,turn);

                button[2][0].setEnabled(false);
                break;

            case R.id.b21:
                if(turn){
                    button[2][1].setText("X");
                    boardStatus[2][1] = 1;
                }
                else{
                    button[2][1].setText("O");
                    boardStatus[2][1] = 0;
                }
                humanPlayer.makeMove(game,7,turn);

                button[2][1].setEnabled(false);
                break;

            case R.id.b22:
                if(turn){
                    button[2][2].setText("X");
                    boardStatus[2][2] = 1;
                }
                else{
                    button[2][2].setText("O");
                    boardStatus[2][2] = 0;
                }
                humanPlayer.makeMove(game,8,turn);

                button[2][2].setEnabled(false);
                break;

            default:
                break;

        }

        TURN_COUNT ++;
        Log.v(TAG,TURN_COUNT+"");


        if(checkWinner() == false){
            if(TURN_COUNT != 9 && TURN_COUNT <10){
                turn = !turn;
                int index = aiPlayer.makeLearnedMove(game,turn);
                updateBoardStatus(index);

                Log.v(TAG,TURN_COUNT+"");

                if(checkWinner() == false){
                    if(TURN_COUNT == 9){
                        setInfo("Game Draw");
                        gamestatus("END");
                    }
                }
            }else{
                setInfo("Game Draw");
                gamestatus("END");
            }

        }

        switch (view.getId()){
            case R.id.menu_button:
                super.onBackPressed();
                break;

            case R.id.rematch_button:
                resetBoard();
                break;
            default:
                break;
        }
    }

    private void updateBoardStatus(int index) {
        switch (index){
            case 0:
                if(turn){
                    button[0][0].setText("X");
                    boardStatus[0][0] = 1;
                }
                else{
                    button[0][0].setText("O");
                    boardStatus[0][0] = 0;
                }
                button[0][0].setEnabled(false);
                break;

            case 1:
                if(turn){
                    button[0][1].setText("X");
                    boardStatus[0][1] = 1;
                }
                else{
                    button[0][1].setText("O");
                    boardStatus[0][1] = 0;
                }

                button[0][1].setEnabled(false);
                break;

            case 2:
                if(turn){
                    button[0][2].setText("X");
                    boardStatus[0][2] = 1;
                }
                else{
                    button[0][2].setText("O");
                    boardStatus[0][2] = 0;
                }

                button[0][2].setEnabled(false);
                break;

            case 3:
                if(turn){
                    button[1][0].setText("X");
                    boardStatus[1][0] = 1;
                }
                else{
                    button[1][0].setText("O");
                    boardStatus[1][0] = 0;
                }

                button[1][0].setEnabled(false);
                break;

            case 4:
                if(turn){
                    button[1][1].setText("X");
                    boardStatus[1][1] = 1;
                }
                else{
                    button[1][1].setText("O");
                    boardStatus[1][1] = 0;
                }

                button[1][1].setEnabled(false);
                break;

            case 5:
                if(turn){
                    button[1][2].setText("X");
                    boardStatus[1][2] = 1;
                }
                else{
                    button[1][2].setText("O");
                    boardStatus[1][2] = 0;
                }

                button[1][2].setEnabled(false);
                break;

            case 6:
                if(turn){
                    button[2][0].setText("X");
                    boardStatus[2][0] = 1;
                }
                else{
                    button[2][0].setText("O");
                    boardStatus[2][0] = 0;
                }

                button[2][0].setEnabled(false);
                break;

            case 7:
                if(turn){
                    button[2][1].setText("X");
                    boardStatus[2][1] = 1;
                }
                else{
                    button[2][1].setText("O");
                    boardStatus[2][1] = 0;
                }

                button[2][1].setEnabled(false);
                break;

            case 8:
                if(turn){
                    button[2][2].setText("X");
                    boardStatus[2][2] = 1;
                }
                else{
                    button[2][2].setText("O");
                    boardStatus[2][2] = 0;
                }

                button[2][2].setEnabled(false);
                break;

            case R.id.menu_button:
                super.onBackPressed();
                break;

            case R.id.rematch_button:
                resetBoard();
                break;

            default:
                break;

        }
        TURN_COUNT++;
    }

    private boolean checkWinner(){

        Log.d(TAG, "Inside checkWinner");

        //Horizontal --- rows
        for(int i=0; i<3; i++){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]){
                if (boardStatus[i][0]==1){
                    result("Player X wins");
                    showWinPattern(button[i][0],button[i][1],button[i][2]);
                    return true;
                }
                else if (boardStatus[i][0]==0) {
                    result("Player 0 wins");
                    showWinPattern(button[i][0],button[i][1],button[i][2]);
                    return true;
                }



            }
        }

        //Vertical --- columns
        for(int i=0; i<3; i++){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]){
                if (boardStatus[0][i]==1){
                    result("Player X wins");
                    showWinPattern(button[0][i],button[1][i],button[2][i]);
                    return true;
                }
                else if (boardStatus[0][i]==0) {
                    result("Player 0 wins");
                    showWinPattern(button[0][i],button[1][i],button[2][i]);
                    return true;
                }


            }
        }

        //First diagonal
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if (boardStatus[0][0]==1){
                result("Player X wins");
                showWinPattern(button[0][0],button[1][1],button[2][2]);
                return true;
            }
            else if (boardStatus[0][0]==0) {
                result("Player 0 wins");
                showWinPattern(button[0][0],button[1][1],button[2][2]);
                return true;
            }


        }

        //Second diagonal
        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]){
            if (boardStatus[0][2]==1){
                result("Player X wins");
                showWinPattern(button[0][2],button[1][1],button[2][0]);
                return true;
            }
            else if (boardStatus[0][2]==0) {
                result("Player 0 wins");
                showWinPattern(button[0][2],button[1][1],button[2][0]);
                return true;
            }
        }

        return false;
    }

    private void showWinPattern(Button button, Button button1, Button button2) {

        button.setTextColor(Color.BLACK);
        button1.setTextColor(Color.BLACK);
        button2.setTextColor(Color.BLACK);
    }

    private void setInfo(String text){
        tvInfo.setText(text);
    }

    private void result(String winner){
        String player = turn?"HUMAN":"AI";
        setInfo(player +" "+winner);

        gamestatus("END");

        enableAllBoxes(false);
    }

    private void gamestatus(String status) {
        if(status.equalsIgnoreCase("END")){
            rematch_button.setVisibility(View.VISIBLE);
            menu_button.setVisibility(View.VISIBLE);

        }else{
            rematch_button.setVisibility(View.GONE);
            menu_button.setVisibility(View.GONE);
        }

    }

    private void enableAllBoxes(boolean value){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                button[i][j].setEnabled(value);
            }
        }
    }

    private void resetBoard(){
        game.resetBoard();

        turn = false; // !turn is done before humman make move so Human plays X

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                button[i][j].setText("");
                button[i][j].setTextColor(Color.WHITE);
            }
        }

        gamestatus("START");

        enableAllBoxes(true);

        PLAYER_X = true;
        TURN_COUNT = 0;

        initializeBoardStatus();

        setInfo("");

        askFirstPlayer();
    }

    @Override
    public void onBackPressed() {
        //toDo nothing add a dialog box to ask confirmation
    }

}
