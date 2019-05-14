package cz.fel.cvut.pjv.holycrab.Views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cz.fel.cvut.pjv.holycrab.Activities.GameOverActivity;
import cz.fel.cvut.pjv.holycrab.Activities.WinActivity;
import cz.fel.cvut.pjv.holycrab.Environment.Room;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Dungeon;
import cz.fel.cvut.pjv.holycrab.LevelLoader;
import cz.fel.cvut.pjv.holycrab.LevelSaver;
import cz.fel.cvut.pjv.holycrab.R;
import cz.fel.cvut.pjv.holycrab.UserInterface;
import cz.fel.cvut.pjv.holycrab.Threads.MainThread;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Player player;
    public static int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Point moveDirection = new Point();
    private Context context;
    private static Dungeon dungeon;
    private static boolean loadFromSaved = false;
    private UserInterface userInterface;
    private boolean playerIsActive = false;

    private static Resources resources;

    public GameView(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        resources = getResources();
        LevelLoader levelLoader = new LevelLoader(this);
        dungeon = levelLoader.loadLevelFromFile(loadFromSaved, context);
        //thread.start();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
        thread.setRunning(true);
        userInterface = new UserInterface();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Room.setRoomsAmount(0); //TODO: correct this
        boolean retry = true;
        thread.setRunning(false);
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
        player.draw(canvas);
        userInterface.draw(canvas);
    }
    //TODO correct this part and part in Room

    public void update() {
        userInterface.update(player);
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

    public void winGame() {
        Intent intent = new Intent(context, WinActivity.class);
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Point getMoveDirection() {
        return moveDirection;
    }

    public static Dungeon getDungeon() {
        return dungeon;
    }

    public static void setLoadFromSaved(boolean loadFromSaved) {
        GameView.loadFromSaved = loadFromSaved;
    }
}
