package tictactoe.com.tictacoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        final Intent gameIntent = new Intent(this, GameActivity.class);

        Button humanButton  = (Button)findViewById(R.id.human_button);
        Button aiLevelOneButton  = (Button)findViewById(R.id.ai_level1_button);

        humanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO set params as human
                gameIntent.putExtra("player","human");
                startActivity(gameIntent);

            }
        });

        aiLevelOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////TODO set params for level1
            }
        });
    }
}
