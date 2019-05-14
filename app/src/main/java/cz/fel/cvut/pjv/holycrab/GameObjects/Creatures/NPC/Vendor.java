package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;

import java.io.InputStream;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Views.GameView;
import cz.fel.cvut.pjv.holycrab.Environment.Map;
import cz.fel.cvut.pjv.holycrab.R;

public class Vendor extends NPC {
    private static Bitmap vendorSpriteSheet;
    static {
        vendorSpriteSheet = BitmapFactory.decodeResource(GameView.getGameResources(), R.drawable.vendor);
    }
    public Vendor(int initialX, int initialY, Map map) {
        super(vendorSpriteSheet, map);
        setMapCoordinates(initialX, initialY);
        inputStream = GameView.getGameResources().openRawResource(R.raw.vendor_speech);
        movie = Movie.decodeStream(inputStream);
    }
    private InputStream inputStream;
    private Movie movie;
    private long movieStart;
    private boolean isSpeaking;
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isSpeaking) {
            drawSpeech(canvas);
        }
    }

    public void drawSpeech(Canvas canvas) {
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
        movie.draw(canvas,screenCoordinates.x - 40,screenCoordinates.y - 180);
    }

    @Override
    public void interact(Player player) {
        player.setMoveOver(true);
        isSpeaking = true;

    }
}
