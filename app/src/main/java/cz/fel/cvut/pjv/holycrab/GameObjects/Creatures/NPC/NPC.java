package cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC;

import android.graphics.Bitmap;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Creature;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.Environment.Map;

public abstract class NPC extends Creature {
    NPC(Bitmap spriteSheet, Map map) {
        super(spriteSheet, map);
    }

    /**
     * @param player Player to interact with
     */
    @Override
    public void interact(Player player) {
        player.setMoveOver(true);
    }
}
