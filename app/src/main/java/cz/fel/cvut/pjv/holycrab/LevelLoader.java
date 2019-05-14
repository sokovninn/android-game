package cz.fel.cvut.pjv.holycrab;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.fel.cvut.pjv.holycrab.Environment.BossRoom;
import cz.fel.cvut.pjv.holycrab.Environment.Dungeon;
import cz.fel.cvut.pjv.holycrab.Environment.Room;
import cz.fel.cvut.pjv.holycrab.Environment.ShopRoom;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Arachne;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Enemy;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Mage;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Enemies.Skeleton;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC.Djinn;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC.NPC;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC.Penguin;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC.Pug;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.NPC.Vendor;
import cz.fel.cvut.pjv.holycrab.GameObjects.Creatures.Player;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Axe;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Chest;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.HealthPotion;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Item;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Key;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Ring;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Sword;
import cz.fel.cvut.pjv.holycrab.GameObjects.Items.Treasure;
import cz.fel.cvut.pjv.holycrab.Views.GameView;

public class LevelLoader {
    private BufferedReader reader;
    private GameView gameView;
    private Location[][] roomConnections;
    private int roomCounter = 0;

    public LevelLoader(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * @param flag True if loading level from saved file
     * @param context Context with information
     * @return Dungeon which represents loaded game
     */
    public Dungeon loadLevelFromFile(boolean flag, Context context) {
        roomCounter = 0;
        Dungeon dungeon = new Dungeon(gameView);
        try {
            if (flag) {
                File file = new File(context.getFilesDir(), "saved_level.txt");
                reader = new BufferedReader(new FileReader(file));
                Log.i("Load", file.getPath());
            } else {
                InputStream is = gameView.getResources().openRawResource(R.raw.level);
                // FileReader fileReader = new FileReader(fileName);
                reader = new BufferedReader(new InputStreamReader(is));
            }
            int roomsAmount = Integer.parseInt(reader.readLine());
            ArrayList<Room> rooms = new ArrayList<>();
            roomConnections = new Location[roomsAmount][roomsAmount];
            String line;
            for (int i = 0; i < roomsAmount; i++) {
                line = reader.readLine();
                String roomType = line.split(" ")[0];
                rooms.add(readRoom(roomType));
                roomCounter++;
            }
            int currentRoom = readPLayer();
            for (int i = 0; i < roomsAmount; i++) {
                for (int j = 0; j < roomsAmount; j++) {
                    if (roomConnections[i][j] != null) {
                        rooms.get(i).setNextRoom(rooms.get(j), roomConnections[i][j]);
                    }
                }
            }
            dungeon.setRooms(rooms);
            dungeon.setCurrentRoom(rooms.get(currentRoom - 1));
            dungeon.setPlayer(gameView.getPlayer());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dungeon;
    }

    private int readPLayer() throws IOException {
        Player player = gameView.getPlayer();
        String[] lineData;
        int currentRoom;
        reader.readLine();
        lineData = reader.readLine().split(" ");
        player.setMapCoordinates(Integer.parseInt(lineData[3]), Integer.parseInt(lineData[4]));
        currentRoom = Integer.parseInt(lineData[2]);
        lineData = reader.readLine().split(" ");
        player.setHitPoints(Integer.parseInt(lineData[1]), Integer.parseInt(lineData[2]));
        player.setStrength(Integer.parseInt(lineData[3]));
        reader.readLine();
        lineData = reader.readLine().split(" ");
        player.setGoldAmount(Integer.parseInt(lineData[1]));
        lineData = reader.readLine().split(" ");
        player.setKeysAmount(Integer.parseInt(lineData[1]));
        lineData = reader.readLine().split(" ");
        for (int i = 0; i < lineData.length; i++) {
            player.takeItem(lineData[i]);
        }
        return currentRoom;
    }

    private Room readRoom(String roomType) throws IOException {
        String line;
        int[][] mapArray = readMapArray();
        Room room = null;
        switch (roomType) {
            case "Room":
                room = new Room(gameView, mapArray);
                break;
            case "ShopRoom":
                room = new ShopRoom(gameView, mapArray);
                break;
            case "BossRoom":
                room = new BossRoom(gameView, mapArray);
                break;
        }
        while (!(line = reader.readLine()).isEmpty()) {
            switch (line) {
                case "Doors:":
                    readDoors();
                    break;
                case "Enemies:":
                    readEnemies(room);
                    break;
                case "Items:":
                    if (room instanceof ShopRoom) {
                        readShopItems((ShopRoom)room);
                    } else {
                        readItems(room);
                    }
                    break;
                case "NPCs:":
                    readNPCs(room);
                    break;
            }
        }
        return room;
    }

    private int[][] readMapArray() throws IOException {
        String line;
        int[][] mapArray = new int[10][10];
        for (int row = 0; row < 10; row++) {
            line = reader.readLine();
            String[] rowArray = line.split(" ");
            for (int col = 0; col < 10; col++) {
                mapArray[row][col] = Integer.parseInt(rowArray[col]);
            }
        }
        return mapArray;
    }

    private void readDoors() throws IOException {
        int doorsAmount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < doorsAmount; i++) {
            String[] doorData = reader.readLine().split(" ");
            int nextRoom = Integer.parseInt(doorData[1]);
            Location location = null;
            switch (doorData[0]) {
                case "top":
                    location = Location.top;
                    break;
                case "right":
                    location = Location.right;
                    break;
                case "left":
                    location = Location.left;
                    break;
                case "bottom":
                    location = Location.bottom;
                    break;
            }
            roomConnections[roomCounter][nextRoom - 1] = location;
        }
    }

    private void readEnemies(Room room) throws IOException {
        int enemiesAmount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < enemiesAmount; i++) {
            String[] line = reader.readLine().split(" ");
            int[] enemyData = new int[4];
            Enemy enemy = null;
            for (int j = 1; j < 5; j++) {
                enemyData[j - 1] = Integer.parseInt(line[j]);
            }
            switch (line[0]) {
                case "Skeleton":
                    enemy = new Skeleton(room, enemyData[0], enemyData[1]);
                    break;
                case "Mage":
                    enemy = new Mage(room, enemyData[0], enemyData[1]);
                    break;
                case "Arachne":
                    enemy = new Arachne(room, enemyData[0], enemyData[1]);
                    break;
            }
            enemy.setHitPoints(enemyData[2]);
            enemy.setStrength(enemyData[3]);
            room.addGameObject(enemy, false);
        }
    }

    private void readItems(Room room) throws IOException {
        int itemsAmount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < itemsAmount; i++) {
            String[] line = reader.readLine().split(" ");
            int[] itemData = new int[2];
            Item item = null;
            for (int j = 1; j < 3; j++) {
                itemData[j - 1] = Integer.parseInt(line[j]);
            }
            switch (line[0]) {
                case "Axe":
                    item = new Axe(itemData[0], itemData[1], room.getMap());
                    break;
                case "Chest":
                    item = new Chest(itemData[0], itemData[1], room.getMap());
                    ((Chest) item).addLoot(line[3]);
                    break;
                case "HealthPotion":
                    item = new HealthPotion(itemData[0], itemData[1], room.getMap());
                    break;
                case "Key":
                    item = new Key(itemData[0], itemData[1], room.getMap());
                    break;
                case "Ring":
                    item = new Ring(itemData[0], itemData[1], room.getMap());
                    break;
                case "Sword":
                    item = new Sword(itemData[0], itemData[1], room.getMap());
                    break;
                case "Treasure":
                    item = new Treasure(itemData[0], itemData[1], Integer.parseInt(line[3]), room.getMap());
                    break;
            }
            room.addGameObject(item, true);
        }
    }

    private void readShopItems(ShopRoom shopRoom) throws IOException {
        int shopItemsAmount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < shopItemsAmount; i++) {
            String[] line = reader.readLine().split(" ");
            int cost = Integer.parseInt(line[1]);
            shopRoom.addItemForSale(line[0], cost);
        }

    }

    public void readNPCs(Room room) throws IOException {
        int npcAmount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < npcAmount; i++) {
            String[] line = reader.readLine().split(" ");
            int[] npcData = new int[2];
            NPC npc = null;
            for (int j = 1; j < 3; j++) {
                npcData[j - 1] = Integer.parseInt(line[j]);
            }
            switch (line[0]) {
                case "Djinn":
                    npc = new Djinn(npcData[0], npcData[1], room.getMap());
                    break;
                case "Penguin":
                    npc = new Penguin(npcData[0], npcData[1], room.getMap());
                    break;
                case "Pug":
                    npc = new Pug(npcData[0], npcData[1], room.getMap());
                    break;
                case "Vendor":
                    npc = new Vendor(npcData[0], npcData[1], room.getMap());
                    break;
            }
            room.addGameObject(npc, false);
        }

    }
}
