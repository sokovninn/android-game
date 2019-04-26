package cz.fel.cvut.pjv.holycrab;

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
    private MapSprite mapSprite;
    static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Point moveDirection = new Point();
    private boolean playerIsActive = false;
    private boolean enemyIsActive = true;
    private Context context;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private GameObject[][] objectsOnMap;

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
        //Create map
        MapSprite.initializeTileSprites(BitmapFactory.decodeResource(getResources(), R.drawable.spritesheet), 32, 84, 1);
        int[][] mapArray = new int[][]{
                {5, 6, 6, 6, 6, 6, 6, 6, 7},
                {19, 20, 20, 20, 20, 20, 20, 20, 21},
                {19, 20, 20, 20, 20, 20, 20, 20, 21},
                {19, 20, 20, 20, 20, 20, 20, 20, 21},
                {19, 20, 20, 20, 20, 20, 20, 20, 21},
                {19, 20, 20, 20, 20, 20, 20, 20, 21},
                {19, 20, 20, 20, 20, 20, 20, 20, 21},
                {19, 20, 20, 20, 20, 20, 20, 20, 21},
                {33, 34, 34, 34, 34, 34, 34, 34, 35}};
        objectsOnMap = new GameObject[mapArray.length][mapArray[0].length];
        mapSprite = new MapSprite(mapArray, mapArray.length, mapArray[0].length);
        //Create character
        characterSprite = new CharacterSprite(this, 4, 4);
        SkeletonSprite skeletonSprite = new SkeletonSprite(this, 2, 1);
        gameObjects.add(skeletonSprite);
        objectsOnMap[2][1] = skeletonSprite;
        //Create another enemy
        MageSprite mageSprite = new MageSprite(this, 5, 4);
        gameObjects.add(mageSprite);
        objectsOnMap[5][4] = mageSprite;



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
        for (GameObject gameObject: gameObjects) {
            gameObject.draw(canvas);
        }
    }

    public void update() {
        // TODO Create fight handler
        if (playerIsActive) {
            playerIsActive = false;
            Point characterMove = characterSprite.getCoordinatesAfterUpdate(moveDirection);
            if (enemyIsActive) {
                enemyIsActive = false;
                for (GameObject gameObject: gameObjects) {
                    if (gameObject instanceof EnemySprite) {
                        Point position;
                        EnemySprite enemySprite = (EnemySprite)gameObject;
                        position = enemySprite.getMapCoordinates();
                        objectsOnMap[position.x][position.y] = null;
                        enemySprite.update(characterMove);
                        position = enemySprite.getMapCoordinates();
                        objectsOnMap[position.x][position.y] = enemySprite;
                    }
                }
                if (characterSprite.isDead) {
                    endGame();
                }
                if (!characterSprite.isAttacked) {
                    characterSprite.update(moveDirection);
                } else {
                    characterSprite.setAttacked(false);
                }
            } else {
                enemyIsActive = true;
                if (objectsOnMap[characterMove.x][characterMove.y] != null) {
                    EnemySprite enemySprite = (EnemySprite)objectsOnMap[characterMove.x][characterMove.y];
                    characterSprite.attack(enemySprite);
                    if (enemySprite.isDead) {
                        objectsOnMap[characterMove.x][characterMove.y] = null;
                        gameObjects.remove(enemySprite);
                    }
                    enemySprite.setAttacked(false);
                } else {
                    characterSprite.update(moveDirection);
                }
            }
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

    public MapSprite getMap() {
        return mapSprite;
    }

    public CharacterSprite getCharacter() {
        return characterSprite;
    }
}
