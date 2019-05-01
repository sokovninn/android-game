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
    static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Point moveDirection = new Point();
    private boolean playerIsActive = false;
    private boolean enemyIsActive = true;
    private Context context;
    private ArrayList<Room> rooms = new ArrayList<>();
    private Room currentRoom;

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
        //Initialize map tiles
        MapSprite.initializeTileSprites(BitmapFactory.decodeResource(getResources(), R.drawable.dungeon), 32, 247, 1);
        int[][] mapArrayFirstRoom = new int[][]{
                {3, 57, 57, 57, 155, 156, 57, 57, 57, 4},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {181, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {200, 6, 6, 6, 6, 167, 6, 6, 6, 77},
                {76, 73, 74, 6, 6, 6, 6, 6, 6, 77},
                {76, 92, 93, 6, 6, 6, 6, 6, 6, 77},
                {76, 111, 112, 6, 6, 6, 6, 6, 6, 77},
                {22, 78, 78, 78, 215, 216, 78, 78, 78, 23}};

        int[][] mapArraySecondRoom = new int[][]{
                {3, 57, 57, 57, 155, 156, 57, 57, 57, 4},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 77},
                {76, 6, 6, 6, 6, 6, 6, 6, 6, 171},
                {76, 6, 6, 6, 6, 167, 6, 6, 6, 190},
                {76, 73, 74, 6, 6, 6, 6, 6, 6, 77},
                {76, 92, 93, 6, 6, 6, 6, 6, 6, 77},
                {76, 111, 112, 6, 6, 6, 6, 6, 6, 77},
                {22, 78, 78, 78, 215, 216, 78, 78, 78, 23}};
        //Create new room
        Room newRoom = new Room(this, mapArrayFirstRoom);
        rooms.add(newRoom);
        currentRoom = newRoom;
        //One more room
        newRoom = new Room(this, mapArraySecondRoom);
        rooms.add(newRoom);

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
        currentRoom.draw(canvas);
        characterSprite.draw(canvas);

    }

    public void update() {
        ArrayList<GameObject> gameObjects = currentRoom.getGameObjects();
        GameObject[][] objectsOnMap = currentRoom.getObjectsOnMap();
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
            if (currentRoom == rooms.get(0) && characterSprite.getMapCoordinates().x == 0) {
                currentRoom = rooms.get(1);
                characterSprite.setMapCoordinates(8, 4);
            }
            if (currentRoom == rooms.get(1) && characterSprite.getMapCoordinates().x == 9) {
                currentRoom = rooms.get(0);
                characterSprite.setMapCoordinates(1, 4);
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

    public CharacterSprite getCharacter() {
        return characterSprite;
    }

    public void setCharacterSprite(CharacterSprite characterSprite) {
        this.characterSprite = characterSprite;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
