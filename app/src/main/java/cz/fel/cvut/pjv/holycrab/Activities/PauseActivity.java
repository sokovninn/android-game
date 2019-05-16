package cz.fel.cvut.pjv.holycrab.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import cz.fel.cvut.pjv.holycrab.Utils.LevelSaver;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class PauseActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_pause);

        Button continueButton = findViewById(R.id.button4);
        continueButton.setOnClickListener(this);

        Button saveButton = findViewById(R.id.button5);
        saveButton.setOnClickListener(this);

        Button exitToMenuButton = findViewById(R.id.button6);
        exitToMenuButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button4: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } break;

            case R.id.button5: {
                LevelSaver levelSaver = new LevelSaver();
                levelSaver.saveLevel(GameView.getDungeon());
            } break;

            case R.id.button6: {
                Intent intent = new Intent(this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } break;

            default:
                break;

        }
    }
}
