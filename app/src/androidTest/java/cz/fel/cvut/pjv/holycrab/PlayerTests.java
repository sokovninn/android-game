package cz.fel.cvut.pjv.holycrab;

import android.graphics.Point;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import cz.fel.cvut.pjv.holycrab.Environment.Doors.WoodenDoor;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Skeleton;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Axe;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Item;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PlayerTests {
    @Test
    public void updateShouldChangeCoordinates() {
        Point playerInitialCoordinates = new Point(4, 4);
        Player player = new Player(playerInitialCoordinates.x, playerInitialCoordinates.y);
        Point moveDirection = new Point(1, 1);
        player.update(moveDirection);
        Point playersCoordinates = player.getMapCoordinates();

        assertNotEquals(playersCoordinates.x, playerInitialCoordinates.y);
        assertNotEquals(playersCoordinates.y, playerInitialCoordinates.y);
    }

    @Test
    public void getCoordinatesAfterUpdate_ShouldReturn_CorrectCoordinates() {
        Point playerInitialCoordinates = new Point(2, 2);
        Player player = new Player(playerInitialCoordinates.x, playerInitialCoordinates.y);
        Point moveDirection = new Point(1, 0);
        Point updatedCoordinates = player.getCoordinatesAfterUpdate(moveDirection);

        assertEquals(updatedCoordinates.x, playerInitialCoordinates.x + 1);
        assertEquals(updatedCoordinates.y, playerInitialCoordinates.y);
    }

    @Test
    public void playerShould_Not_GoThrough_TheWalls() {
        Point playerInitialCoordinates = new Point(1, 1);
        Player player = new Player(playerInitialCoordinates.x, playerInitialCoordinates.y);
        // Tile on (0, 1) is wall
        Point moveDirection = new Point(-1, 0);
        Point updatedCoordinates = player.getCoordinatesAfterUpdate(moveDirection);

        assertEquals(updatedCoordinates.x, playerInitialCoordinates.x);
        assertEquals(updatedCoordinates.y, playerInitialCoordinates.y);
    }

    @Test
    public void amountOfKeys_ShouldIncrease_AfterTakingKey() {
        Player player = new Player(0,0);
        player.takeKey();

        assertEquals(player.getKeysAmount(), 1);
        assertTrue(player.hasKey());
    }

    @Test
    public void playerShould_NotOverheal() {
        Player player = new Player(0,0);
        player.setHitPoints(2,3);
        player.heal(2);

        assertEquals(player.getHitPoints(), 3);
    }

    @Test
    public void ringShould_Increase_MaxHitpoins() {
        Player player = new Player(0, 0);
        int maxHitPoints = player.getMaxHitPoints();
        player.takeItem("Ring");

        assertEquals(player.getMaxHitPoints(), maxHitPoints + 1);
    }

    @Test
    public void weaponShould_IncreaseStrength() {
        Player player = new Player(0, 0);
        player.takeItem("Axe");

        assertEquals(player.getStrength(), 3);
    }

    @Test
    public void item_ShouldBe_InInventory_AfterTaking() {
        Player player = new Player(0, 0);
        Axe axe = new Axe(0, 0, null);
        player.takeWeapon(axe, Axe.getDamage());
        Item equipment[] = player.getInventory().getEquipment();

        // Weapon is on the 4th position in equipment
        assertEquals(axe, equipment[4]);
    }

    @Test
    public void playerShould_DecreaseEnemy_HealthAfter_Attack() {
        Player player = new Player(0, 0);
        Skeleton skeleton = new Skeleton(0, 1);
        int skeletonHealth = skeleton.getHitPoints();
        player.attack(skeleton);

        assertEquals(skeleton.getHitPoints(), skeletonHealth - 1);
    }

    @Test
    public void player_ShouldOpen_TheDoor_ByKey() {
        Player player = new Player(5, 1);
        player.takeKey();
        WoodenDoor woodenDoor = new WoodenDoor(Location.top, null, null);
        woodenDoor.setAvailable(true);
        woodenDoor.interact(player);

        assertTrue(woodenDoor.checkOpened());

    }
}
