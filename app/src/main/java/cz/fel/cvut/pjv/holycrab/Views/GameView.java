package cz.fel.cvut.pjv.holycrab.Views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cz.fel.cvut.pjv.holycrab.Activities.GameOverActivity;
import cz.fel.cvut.pjv.holycrab.Activities.WinActivity;
import cz.fel.cvut.pjv.holycrab.Environment.Room;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Dungeon;
import cz.fel.cvut.pjv.holycrab.Utils.LevelLoader;
import cz.fel.cvut.pjv.holycrab.UserInterface;
import cz.fel.cvut.pjv.holycrab.Threads.MainThread;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Player player;
    public static int screenWidth;
    public static int screenHeight;
    private Point moveDirection = new Point();
    private Context context;
    private static Dungeon dungeon;
    private static boolean loadFromSaved = false;
    private UserInterface userInterface;
    private boolean playerIsActive = false;

    private static Resources resources;

    /**
     * @param context Context with useful data
     */
    public GameView(Context context) {
        super(context);
        this.context = context;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
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
        if (!thread.isAlive()) {
            thread.start();
            thread.setRunning(true);
        }
        userInterface = new UserInterface();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Room.setRoomsAmount(0); //TODO: correct this
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
        player.draw(canvas);
        userInterface.draw(canvas);
    }
    //TODO correct this part and part in Room

    /**
     * Update state of the game
     */
    public void update() {
        userInterface.update(player);
        if (playerIsActive) {
            dungeon.update();
            playerIsActive = false;
        }
    }

    /**
     * Finish the game and call GameOverActivity
     */
    public void endGame() {
        Intent intent = new Intent(context, GameOverActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Finish the game and call WinActivity
     */
    public void winGame() {
        Intent intent = new Intent(context, WinActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * @param x X direction of move
     * @param y Y direction of move
     */
    public void setMoveDirection(int x, int y) {
        playerIsActive = true;
        moveDirection.x = x;
        moveDirection.y = y;
    }

    /**
     * @return Game files
     */
    public static Resources getGameResources() {
        return resources;
    }

    /**
     * @return Players character
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player Players charaxter
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return Direction of move
     */
    public Point getMoveDirection() {
        return moveDirection;
    }

    /**
     * @return Full dungeon
     */
    public static Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * @param loadFromSaved True if loading game from saved file
     */
    public static void setLoadFromSaved(boolean loadFromSaved) {
        GameView.loadFromSaved = loadFromSaved;
    }
}
