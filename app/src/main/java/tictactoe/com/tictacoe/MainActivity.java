package tictactoe.com.tictacoe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        final Intent humanVsHuman = new Intent(this, HumanActivity.class);
        final Intent humanVsAI = new Intent(this, AIActivity.class);

        Button humanButton  = findViewById(R.id.human_button);
        Button aiLevelOneButton  = findViewById(R.id.ai_level1_button);
        Button aiLevelTwoButton  = findViewById(R.id.ai_level2_button);
        Button aiLevelThreeButton  = findViewById(R.id.ai_level3_button);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12);

                return;
            }
        }

        humanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO set params as human
                humanVsHuman.putExtra("player","human");
                startActivity(humanVsHuman);

            }
        });

        aiLevelOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanVsAI.putExtra("player","ai1");
                startActivity(humanVsAI);
            }
        });

        aiLevelTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanVsAI.putExtra("player","ai2");
                startActivity(humanVsAI);
            }
        });


        aiLevelThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanVsAI.putExtra("player","ai3");
                startActivity(humanVsAI);
            }
        });
    }
}
