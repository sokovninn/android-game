package cz.fel.cvut.pjv.holycrab;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;

public interface Interactable {
    /**
     * @param player Player to interact with
     */
    void interact(Player player);
}
