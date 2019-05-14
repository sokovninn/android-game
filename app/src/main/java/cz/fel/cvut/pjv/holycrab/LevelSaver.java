package cz.fel.cvut.pjv.holycrab;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.fel.cvut.pjv.holycrab.Environment.Doors.Door;
import cz.fel.cvut.pjv.holycrab.Environment.Dungeon;
import cz.fel.cvut.pjv.holycrab.Environment.Room;
import cz.fel.cvut.pjv.holycrab.Environment.ShopRoom;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Enemy;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC.NPC;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.GameObjects.GameObject;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Chest;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Item;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Ring;

public class LevelSaver {
    private BufferedWriter writer;
    private Dungeon dungeon;

    // TODO: handle exception
    public void saveLevel(Dungeon dungeon) {
        this.dungeon = dungeon;
        try {
            File file = new File(dungeon.getContext().getFilesDir(), "saved_level.txt");
            Log.i("Save", file.getPath());
            writer = new BufferedWriter(new FileWriter(file));
            Log.i("Save", file.getPath());
            ArrayList<Room> rooms = dungeon.getRooms();
            writer.write(String.valueOf(rooms.size()));
            writer.newLine();
            for (int i = 0; i < rooms.size(); i++) {
                writer.write(rooms.get(i).getClass().getSimpleName() + " " + (i + 1));
                writer.newLine();
                saveRoom(rooms.get(i));
                writer.newLine();
            }
            savePlayer();
            writer.close();
        } catch (IOException e) {

        }
    }

    private void savePlayer() throws IOException {
        Player player = dungeon.getPlayer();
        writer.write("Player:");
        writer.newLine();
        writer.write("Current room: ");
        writer.write(dungeon.getCurrentRoom().getRoomNumber() + " ");
        writer.write(player.getMapCoordinates().x + " " + player.getMapCoordinates().y);
        writer.newLine();
        writer.write("Characteristics: ");
        if (player.getInventory().getEquipment()[7] instanceof Ring) {
            writer.write((player.getHitPoints() - 1) + " " + (player.getMaxHitPoints() - 1));
        } else {
            writer.write(player.getHitPoints() + " " + player.getMaxHitPoints());
        }
        writer.write(" " + player.getStrength());
        writer.newLine();
        writer.write("Items:");
        writer.newLine();
        writer.write("Gold: " + player.getGoldAmount());
        writer.newLine();
        writer.write("Keys: " + player.getKeysAmount());
        writer.newLine();
        Item[] equipemnt = player.getInventory().getEquipment();
        for (int i = 0; i < equipemnt.length; i++) {
            if (equipemnt[i] != null) {
                writer.write(equipemnt[i].getClass().getSimpleName() + " ");
            }
        }

    }

    private void saveRoom(Room room) throws IOException {
        int[][] mapArray =  room.getMap().getMapArray();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                writer.write(mapArray[i][j] + " ");
            }
            writer.write(String.valueOf(mapArray[i][9]));
            writer.newLine();
        }
        saveDoors(room);
        saveEnemies(room);
        if (room instanceof ShopRoom) {
            saveShopItems((ShopRoom)room);
        } else {
            saveItems(room);
        }
        saveNPCs(room);
    }

    private void saveDoors(Room room) throws IOException {
        writer.write("Doors:");
        writer.newLine();
        HashMap<Location, Door> doors = room.getDoors();
        writer.write(String.valueOf(doors.size()));
        writer.newLine();
        for (Map.Entry<Location, Door> entry: doors.entrySet()) {
            String location = entry.getKey().toString();
            int nextRoomNumber = entry.getValue().getNextRoom().getRoomNumber();
            writer.write(location + " " + nextRoomNumber);
            writer.newLine();
        }
    }

    private void saveEnemies(Room room) throws IOException {
        writer.write("Enemies:");
        writer.newLine();
        writer.write(String.valueOf(room.getTotalEnemies()));
        writer.newLine();
        for (GameObject object: room.getGameObjects()) {
            if (object instanceof Enemy) {
                Enemy enemy = (Enemy)object;
                writer.write(object.getClass().getSimpleName() + " ");
                writer.write(enemy.getMapCoordinates().x + " " + enemy.getMapCoordinates().y + " ");
                writer.write(enemy.getHitPoints() + " " + enemy.getStrength());
                writer.newLine();
            }
        }
    }

    private void saveItems(Room room) throws IOException {
        writer.write("Items:");
        writer.newLine();
        writer.write(String.valueOf(room.getTotalItems()));
        writer.newLine();
        for (GameObject object: room.getGameObjects()) {
            if (object instanceof Item) {
                Item item = (Item)object;
                writer.write(item.getClass().getSimpleName() + " ");
                writer.write(item.getMapCoordinates().x + " " + item.getMapCoordinates().y + " ");
                if (item instanceof Chest) {
                    Chest chest = (Chest)item;
                    writer.write(chest.getLoot().getClass().getSimpleName());
                }
                writer.newLine();
            }
        }
    }

    private void saveShopItems(ShopRoom shopRoom) throws IOException {
        writer.write("Items:");
        writer.newLine();
        writer.write(String.valueOf(shopRoom.getItemsForSaleAmount()));
        writer.newLine();
        for (GameObject object: shopRoom.getGameObjects()) {
            if (object instanceof Item) {
                Item item = (Item)object;
                if (item.checkIsForSale()) {
                    writer.write(item.getClass().getSimpleName() + " ");
                    writer.write(String.valueOf(item.getCost()));
                    writer.newLine();
                }
            }
        }
    }

    private void saveNPCs(Room room) throws IOException {
        writer.write("NPCs:");
        writer.newLine();
        writer.write(String.valueOf(room.getTotalNPCs()));
        writer.newLine();
        for (GameObject object: room.getGameObjects()) {
            if (object instanceof NPC) {
                NPC npc = (NPC) object;
                writer.write(npc.getClass().getSimpleName() + " ");
                writer.write(npc.getMapCoordinates().x + " " + npc.getMapCoordinates().y + " ");
                writer.newLine();
            }
        }
    }
}
