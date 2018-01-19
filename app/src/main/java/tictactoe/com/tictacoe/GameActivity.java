package tictactoe.com.tictacoe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by aawesh on 1/19/18.
 */

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    int[][] boardStatus;
    boolean PLAYER_X = true;

    Button[][] button;

    Button menu_button;
    Button rematch_button;

    TextView tvInfo;

    int TURN_COUNT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

         button = new Button[3][3];

        button[0][0] = (Button) findViewById(R.id.b00);
        button[0][1] = (Button) findViewById(R.id.b01);
        button[0][2] = (Button) findViewById(R.id.b02);

        button[1][0] = (Button) findViewById(R.id.b10);
        button[1][1] = (Button) findViewById(R.id.b11);
        button[1][2] = (Button) findViewById(R.id.b12);

        button[2][0] = (Button) findViewById(R.id.b20);
        button[2][1] = (Button) findViewById(R.id.b21);
        button[2][2] = (Button) findViewById(R.id.b22);

        menu_button = (Button) findViewById(R.id.menu_button);
        rematch_button = (Button) findViewById(R.id.rematch_button);

        tvInfo = (TextView) findViewById(R.id.tvInfo);

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

        String opponent = getIntent().getStringExtra("player");
        if(opponent.equalsIgnoreCase("human")){
            startGameWithHuman();
        }
        //TODO else if play with ai level

    }

    private void initializeBoardStatus(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boardStatus[i][j] = -1;
            }
        }
    }

    private void startGameWithHuman(){
        //TODO set game parameters
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Inside onClick");

        switch (view.getId()){
            case R.id.b00:
                if(PLAYER_X){
                    button[0][0].setText("X");
                    boardStatus[0][0] = 1;
                }
                else{
                    button[0][0].setText("0");
                    boardStatus[0][0] = 0;
                }
                button[0][0].setEnabled(false);
                break;

            case R.id.b01:
                if(PLAYER_X){
                    button[0][1].setText("X");
                    boardStatus[0][1] = 1;
                }
                else{
                    button[0][1].setText("0");
                    boardStatus[0][1] = 0;
                }
                button[0][1].setEnabled(false);
                break;

            case R.id.b02:
                if(PLAYER_X){
                    button[0][2].setText("X");
                    boardStatus[0][2] = 1;
                }
                else{
                    button[0][2].setText("0");
                    boardStatus[0][2] = 0;
                }
                button[0][2].setEnabled(false);
                break;

            case R.id.b10:
                if(PLAYER_X){
                    button[1][0].setText("X");
                    boardStatus[1][0] = 1;
                }
                else{
                    button[1][0].setText("0");
                    boardStatus[1][0] = 0;
                }
                button[1][0].setEnabled(false);
                break;

            case R.id.b11:
                if(PLAYER_X){
                    button[1][1].setText("X");
                    boardStatus[1][1] = 1;
                }
                else{
                    button[1][1].setText("0");
                    boardStatus[1][1] = 0;
                }
                button[1][1].setEnabled(false);
                break;

            case R.id.b12:
                if(PLAYER_X){
                    button[1][2].setText("X");
                    boardStatus[1][2] = 1;
                }
                else{
                    button[1][2].setText("0");
                    boardStatus[1][2] = 0;
                }
                button[1][2].setEnabled(false);
                break;

            case R.id.b20:
                if(PLAYER_X){
                    button[2][0].setText("X");
                    boardStatus[2][0] = 1;
                }
                else{
                    button[2][0].setText("0");
                    boardStatus[2][0] = 0;
                }
                button[2][0].setEnabled(false);
                break;

            case R.id.b21:
                if(PLAYER_X){
                    button[2][1].setText("X");
                    boardStatus[2][1] = 1;
                }
                else{
                    button[2][1].setText("0");
                    boardStatus[2][1] = 0;
                }
                button[2][1].setEnabled(false);
                break;

            case R.id.b22:
                if(PLAYER_X){
                    button[2][2].setText("X");
                    boardStatus[2][2] = 1;
                }
                else{
                    button[2][2].setText("0");
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


        Log.v(TAG,TURN_COUNT+"");
        PLAYER_X = !PLAYER_X;

        if(PLAYER_X){
            setInfo("Player X's turn");
        }
        else {
            setInfo("Player 0's turn");
        }

        if(TURN_COUNT==9){
            setInfo("Game Draw");
            gamestatus("END");
        }

        TURN_COUNT ++;
        checkWinner();
    }

    private void checkWinner(){

        Log.d(TAG, "Inside checkWinner");

        //Horizontal --- rows
        for(int i=0; i<3; i++){
            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]){
                if (boardStatus[i][0]==1){
                    result("Player X wins");
                    showWinPattern(button[i][0],button[i][1],button[i][2]);
                    break;
                }
                else if (boardStatus[i][0]==0) {
                    result("Player 0 wins");
                    showWinPattern(button[i][0],button[i][1],button[i][2]);
                    break;
                }



            }
        }

        //Vertical --- columns
        for(int i=0; i<3; i++){
            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]){
                if (boardStatus[0][i]==1){
                    result("Player X wins");
                    showWinPattern(button[0][i],button[1][i],button[2][i]);
                    break;
                }
                else if (boardStatus[0][i]==0) {
                    result("Player 0 wins");
                    showWinPattern(button[0][i],button[1][i],button[2][i]);
                    break;
                }


            }
        }

        //First diagonal
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if (boardStatus[0][0]==1){
                result("Player X wins");
                showWinPattern(button[0][0],button[1][1],button[2][2]);
            }
            else if (boardStatus[0][0]==0) {
                result("Player 0 wins");
                showWinPattern(button[0][0],button[1][1],button[2][2]);
            }


        }

        //Second diagonal
        if(boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]){
            if (boardStatus[0][2]==1){
                result("Player X wins");
                showWinPattern(button[0][2],button[1][1],button[2][0]);
            }
            else if (boardStatus[0][2]==0) {
                result("Player 0 wins");
                showWinPattern(button[0][2],button[1][1],button[2][0]);
            }


        }
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
        Log.d(TAG, "Inside result");

        setInfo(winner);

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
            tvInfo.setText("Start. X's turn");
        }

    }

    private void enableAllBoxes(boolean value){
        Log.d(TAG, "Inside enableAllBoxes");

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
               button[i][j].setEnabled(value);
            }
        }
    }

    private void resetBoard(){
        Log.d(TAG, "Inside resetBoard");

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

        setInfo("Start !!! Player X's turn");

        Toast.makeText(this,"Board Reset",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        //toDo nothing add a dialog box to ask confirmation
    }

}
