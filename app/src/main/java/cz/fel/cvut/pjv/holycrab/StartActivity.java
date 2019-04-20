package cz.fel.cvut.pjv.holycrab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } break;

            case R.id.button2: {
                finish();
            } break;

            default:
                break;

        }
    }
}
