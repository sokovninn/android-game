package cz.fel.cvut.pjv.holycrab;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements View.OnTouchListener {

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
        gameView.setOnTouchListener(this);
        OnSwipeListener onSwipeListener = new OnSwipeListener() {
            @Override
            public boolean onSwipe(Direction direction) {
                if (direction == Direction.left) {
                    gameView.setMoveDirection(-1, 0);
                    return true;
                } else if (direction == Direction.right) {
                    gameView.setMoveDirection(1, 0);
                    return true;
                } else if (direction == Direction.up) {
                    gameView.setMoveDirection(0, -1);
                    return true;
                } else if (direction == Direction.down) {
                    gameView.setMoveDirection(0, 1);
                    return true;
                }
                return super.onSwipe(direction);
            }
        };
        gestureDetector = new GestureDetector(this, onSwipeListener);
        setContentView(gameView);
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
}
