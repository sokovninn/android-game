package cz.fel.cvut.pjv.holycrab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private CharacterSprite characterSprite;
    private EnemySprite enemySprite;
    private MapSprite mapSprite;
    static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Point point = new Point();
    private boolean playerIsMoving = false;
    Context context;

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
                playerIsMoving = true;
                point.set((int)event.getX(),(int)event.getY());
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
        //Create map
        MapSprite.initializeTileSprites(BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet), 32, 84, 1);
        int[][] mapArray = new int[][]{
                {83, 83, 5, 6, 7, 83, 83},
                {83, 83, 19, 2, 21, 83, 83},
                {83, 83, 33, 36, 35, 83, 83},
                {83, 83, 83, 18, 83, 83, 83},
                {5, 6, 6, 38, 6, 6, 7},
                {19, 20, 20, 62, 20, 20, 21},
                {33, 34, 34, 34, 34, 34, 35}};
        mapSprite = new MapSprite(mapArray, mapArray.length, mapArray[0].length);
        //Create character
        Bitmap characterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dwarf);
        Bitmap characterHitPointBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart_48x48);
        characterSprite = new CharacterSprite(characterBitmap, mapSprite, characterHitPointBitmap);
        //Create enemy
        ArrayList<Point> enemyBehavior = new ArrayList<>();
        enemyBehavior.add(new Point(1, 0));
        enemyBehavior.add(new Point(0, 1));
        enemyBehavior.add(new Point(-1, 0));
        enemyBehavior.add(new Point(0, -1));
        Bitmap enemyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tiny_skelly);
        Bitmap enemyHitPointBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart_16x16);
        enemySprite = new EnemySprite(enemyBitmap, mapSprite, enemyHitPointBitmap, enemyBehavior);
        enemySprite.setMapCoordinates(2, 1);


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
        mapSprite.draw(canvas);
        characterSprite.draw(canvas);
        enemySprite.draw(canvas);
    }

    public void update() {
        // TODO Create fight handler
        if (playerIsMoving) {
            Point characterMove = characterSprite.getCoordinatesAfterUpdate(point);
            Point enemyMove = enemySprite.getCoordinatesAfterUpdate();
            if (characterMove.x == enemyMove.x && characterMove.y == enemyMove.y) {
                if (enemySprite.checkAttackable()) {
                    enemySprite.updateHitPoints(-1);
                } else {
                    characterSprite.updateHitPoints(-1);
                    if (characterSprite.getHitPoints() < 1) {
                        //MainActivity.mainActivity.finish();
                        Intent intent = new Intent(context, GameOverActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    enemySprite.update();
                }
            } else {
                characterSprite.update(point);
                enemySprite.update();
            }
            playerIsMoving = false;
        }
    }
}
