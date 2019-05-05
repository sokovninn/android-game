package cz.fel.cvut.pjv.holycrab;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private CharacterSprite characterSprite;
    static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Point moveDirection = new Point();
    private Context context;
    private Dungeon dungeon;
    private boolean playerIsActive = false;

    private static Resources resources;

    public GameView(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //playerIsActive = true;
                //point.set((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        resources = getResources();
        dungeon = new Dungeon(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        dungeon.draw(canvas);
        characterSprite.draw(canvas);

    }

    public void update() {
        if (playerIsActive) {
            dungeon.update();
            playerIsActive = false;
        }
    }

    public void endGame() {
        Intent intent = new Intent(context, GameOverActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void setMoveDirection(int x, int y) {
        playerIsActive = true;
        moveDirection.x = x;
        moveDirection.y = y;
    }

    public static Resources getGameResources() {
        return resources;
    }

    public CharacterSprite getCharacter() {
        return characterSprite;
    }

    public void setCharacterSprite(CharacterSprite characterSprite) {
        this.characterSprite = characterSprite;
    }

    public Point getMoveDirection() {
        return moveDirection;
    }

}
