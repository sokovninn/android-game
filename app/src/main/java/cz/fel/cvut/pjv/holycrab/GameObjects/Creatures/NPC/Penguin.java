package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;

import java.io.InputStream;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.R;

public class Penguin extends NPC {
    private static Bitmap penguinSpriteSheet;
    static {
        Resources resources = GameView.getGameResources();
        if (resources != null) {
            penguinSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.penguin);
        }
    }
    /**
     * @param initialX X coordinate in tiles
     * @param initialY Y coordinate in tiles
     * @param map Map of room
     */
    public Penguin(int initialX, int initialY, Map map) {
        super(penguinSpriteSheet, map);
        setMapCoordinates(initialX, initialY);
        inputStream = GameView.getGameResources().openRawResource(R.raw.speech3);
        movie = Movie.decodeStream(inputStream);
    }
    private InputStream inputStream;
    private Movie movie;
    private long movieStart;
    private boolean isSpeaking;

    /**
     * @param canvas Canvas to draw on
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isSpeaking) {
            drawSpeech(canvas);
        }
    }

    private void drawSpeech(Canvas canvas) {
        long now=android.os.SystemClock.uptimeMillis();
        if (movieStart == 0)
            movieStart = now;
        int relTime = (int)((now - movieStart));
        if (relTime > movie.duration()) {
            movieStart = 0;
            movie.setTime(0);
            isSpeaking = false;
        }
        movie.setTime(relTime);
        movie.draw(canvas,screenCoordinates.x - 220,screenCoordinates.y - 220);
    }

    /**
     * @param player Player to interact with
     */
    @Override
    public void interact(Player player) {
        player.setMoveOver(true);
        isSpeaking = true;

    }
}
