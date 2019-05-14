package cz.fel.cvut.pjv.holycrab.Environment;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Arachne;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class BossRoom extends Room {

    /**
     * @param gameView Game view
     * @param mapArray Array with tile numbers
     */
    public BossRoom(GameView gameView, int[][] mapArray) {
        super(gameView, mapArray);
    }
}
