package cz.fel.cvut.pjv.holycrab.Environment;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Arachne;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class BossRoom extends Room {

    public BossRoom(GameView gameView, int[][] mapArray) {
        super(gameView, mapArray);
        //addGameObject(new Arachne(this, 1, 1), false);
    }
}
