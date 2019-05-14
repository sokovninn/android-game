package cz.fel.cvut.pjv.holycrab.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import cz.fel.cvut.pjv.holycrab.LevelLoader;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class StartActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_start);

        Button startButton = findViewById(R.id.button1);
        startButton.setOnClickListener(this);

        Button exitButton = findViewById(R.id.button2);
        exitButton.setOnClickListener(this);

        Button loadButton = findViewById(R.id.button9);
        loadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1: {
                Log.i("Information", "Starting new game");
                GameView.setLoadFromSaved(false);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } break;

            case R.id.button2: {
                Log.i("Information", "Exiting");
                finish();
            } break;

            case R.id.button9: {
                Log.i("Information", "Loading level");
                GameView.setLoadFromSaved(true);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } break;

            default:
                break;

        }
    }
}
