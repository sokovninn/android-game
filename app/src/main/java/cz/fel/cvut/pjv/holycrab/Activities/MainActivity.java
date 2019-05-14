package cz.fel.cvut.pjv.holycrab.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.OnSwipeListener;
import cz.fel.cvut.pjv.holycrab.R;

public class MainActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private static final Logger LOGGER = Logger.getLogger(Activity.class.getName());

    private GameView gameView;
    private GestureDetector gestureDetector;
    private MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameView = new GameView(this);
        FrameLayout game = new FrameLayout(this);
        LinearLayout gameWidgets = new LinearLayout(this);

        Button pauseButton = new Button(this);
        pauseButton.setWidth(300);
        pauseButton.setText("Menu");
        pauseButton.setX(750);
        pauseButton.setY(2000);
        gameWidgets.addView(pauseButton);

        game.addView(gameView);
        game.addView(gameWidgets);

        gameView.setOnTouchListener(this);
        pauseButton.setOnClickListener(this);
        OnSwipeListener onSwipeListener = setOnSwipeListener();
        gestureDetector = new GestureDetector(this, onSwipeListener);
        setContentView(game);
        backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.rebels_be);
        backgroundMusic.setLooping(true);
        backgroundMusic.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.i("Information", "Game paused");
        Intent intent = new Intent(this, PauseActivity.class);
        startActivity(intent);
        // re-starts this activity from game-view. add this.finish(); to remove from stack
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        gameView = new GameView(this);
        gameView.setOnTouchListener(this);
        setContentView(gameView);
        backgroundMusic.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        backgroundMusic.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundMusic.stop();

    }

    protected OnSwipeListener setOnSwipeListener() {
        OnSwipeListener onSwipeListener = new OnSwipeListener() {
            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.left) {
                    gameView.setMoveDirection(-1, 0);
                    LOGGER.log(Level.INFO, "Move left");
                    //Log.i("Movement", "Move left");
                    return true;
                } else if (direction == Direction.right) {
                    gameView.setMoveDirection(1, 0);
                    LOGGER.log(Level.INFO, "Move right");
                    return true;
                } else if (direction == Direction.up) {
                    gameView.setMoveDirection(0, -1);
                    LOGGER.log(Level.INFO, "Move up");
                    return true;
                } else if (direction == Direction.down) {
                    gameView.setMoveDirection(0, 1);
                    LOGGER.log(Level.INFO, "Move down");
                    return true;
                }
                return super.onSwipe(direction);
            }
        };
        return onSwipeListener;
    }
}
