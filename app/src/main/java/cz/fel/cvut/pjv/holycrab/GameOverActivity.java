package cz.fel.cvut.pjv.holycrab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GameOverActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_game_over);

        Button returnToMenuButton = findViewById(R.id.button);
        returnToMenuButton.setOnClickListener(this);

        Button tryAginButton = findViewById(R.id.button3);
        tryAginButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button: {
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
            } break;

            case R.id.button3: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } break;

            default:
                break;

        }
    }
}
