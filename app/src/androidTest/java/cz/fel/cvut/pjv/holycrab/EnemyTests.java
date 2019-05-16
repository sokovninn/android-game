package cz.fel.cvut.pjv.holycrab;

import android.content.Context;
import android.graphics.Point;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Arachne;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Skeleton;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Treasure;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EnemyTests {
    @Test
    public void enemyShould_AttackPlayer_OnTheWay() {
        Player player = new Player(3, 1);
        int playerHealth = player.getHitPoints();
        Point playerMove = new Point(2,1);
        Skeleton skeleton = new Skeleton(1, 1);
        skeleton.setTargetPlayer(player);
        skeleton.update(playerMove);

        assertEquals(player.getHitPoints(), playerHealth - 1);
    }

    @Test
    public void enemy_ShouldMove_AccordingToBehavior() {
        Player player = new Player(1, 1);
        Point playerMove = new Point(2,1);
        Skeleton skeleton = new Skeleton(5, 5);
        skeleton.setTargetPlayer(player);

        ArrayList<Point> behavior = new ArrayList<>();
        behavior.add(new Point(-2, -3));
        skeleton.setBehavior(behavior);
        skeleton.update(playerMove);

        assertEquals(skeleton.getMapCoordinates().x, 3);
        assertEquals(skeleton.getMapCoordinates().y, 2);
    }

    @Test
    public void enemy_ShouldGive_Reward() {
        Skeleton skeleton = new Skeleton(1, 1);
        Treasure treasure = skeleton.getReward();
        boolean treasureIsEmpty = treasure == null;

        assertFalse(treasureIsEmpty);
    }

    @Test
    public void arachne_ShouldFollow_Player() {
        Player player = new Player(5, 8);
        Point playerMove = new Point(5,7);
        Arachne arachne = new Arachne(5, 1);
        arachne.setTargetPlayer(player);
        arachne.update(playerMove);

        // Arachne moves 2 tiles in the direction of the player
        assertEquals(arachne.getMapCoordinates().x, 5);
        assertEquals(arachne.getMapCoordinates().y, 3);
    }

    @Test
    public void arachne_ShouldHeal_AfterAttack() {
        Player player = new Player(5, 8);
        Point playerMove = new Point(5,7);
        Arachne arachne = new Arachne(5, 5);
        arachne.setHitPoints(4, 5);
        arachne.setTargetPlayer(player);
        arachne.update(playerMove);

        // Arachne moves 2 tiles in the direction of the player
        assertEquals(arachne.getHitPoints(), 5);
    }

}