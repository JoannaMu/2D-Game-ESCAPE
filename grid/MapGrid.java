package com.group8project.grid;

import com.group8project.common.KeyIDConverter;
import com.group8project.common.Position;
import com.group8project.items.Document;
import com.group8project.items.DocumentFactory;
import com.group8project.items.Key;
import com.group8project.items.KeyFactory;
import com.group8project.objects.Camera;
import com.group8project.objects.StaticItemPickup;
import com.group8project.objects.StaticObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A map grid representation that holds a List of List of tile objects
 */

public class MapGrid {
    TileFactory tileFactory;
    private ArrayList<String> keys = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
    private ArrayList<String> locked_doors = new ArrayList<>(Arrays.asList(")", "!", "@", "#", "$", "%", "^", "&", "*", "("));
    private List<List<Tile>> tiles;
    private List<List<String>> tilemap;
    private Tile startCell;
    private Tile endCell;
    private List<Tile> guardSpawns;
    private int width;
    private int height;
    private int documentCount;
    private int keyCount;

    @SuppressWarnings("Convert2Diamond")
    public MapGrid(int width, int height) {
        tileFactory = new TileFactory(this);
        tiles = new ArrayList<>(width);
        for (int x = 0; x < width; x++) {
            List<Tile> newColumn = new ArrayList<>(height);
            for (int y = 0; y < height; y++) {
                Tile newTile;
                Position newPosition = new Position(x, y);
                if ((x == 0 || x == width - 1) || (y == 0 || y == height - 1)) {
                    newTile = tileFactory.makeWallAt(newPosition);
                } else {
                    newTile = tileFactory.makeFloorAt(newPosition);
                }
                newColumn.add(newTile);
            }
            tiles.add(newColumn);
        }
        this.height = height;
        this.width = width;
    }

    /**
     * A constructor that reads a file from PATH input and stores a 2D ArrayList version of the text map
     * Once the text map is translated into a 2D array, the constructor creates a 2D ArrayList of tiles corresponding to the map
     */
    public MapGrid(String map) {
        documentCount = 0;
        keyCount = 0;
        this.tileFactory = new TileFactory(this);
        // Read the text file and parse each line and each character to store into a 2D list.
        try {
            // Load the "map" txt file into a buffer and read character by character to store into 2D array called tilemap
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(map), StandardCharsets.UTF_8));
            this.tilemap = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                List<String> newCol = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    newCol.add(String.valueOf(line.charAt(i)));
                }
                tilemap.add(newCol);
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Could not load map file.");
        }

        // Transpose the 2D list to match so that first layer of 2D list represents x and second layer represents y.
        // (y, x) -> (x, y)
        List<List<String>> newtilemap = new ArrayList<>();
        int N = tilemap.get(0).size();

        for (int i = 0; i < N; i++) {
            List<String> col = new ArrayList<>();
            for (List<String> row : tilemap) {
                col.add(row.get(i));
            }
            newtilemap.add(col);
        }

        this.tiles = new ArrayList<>(newtilemap.size());
        List<String> sample = newtilemap.get(0);
        this.width = newtilemap.size();
        this.height = sample.size();
        this.guardSpawns = new ArrayList<>();

        // Loop through the tilemap and create a new tile for each character in the 2D array
        for (int x = 0; x < this.width; x++) {
            sample = newtilemap.get(x);
            List<Tile> newRow = new ArrayList<>(sample.size());
            for (int y = 0; y < this.height; y++) {
                Tile newTile;
                Position newPosition = new Position(x, y);
                String tileRead = newtilemap.get(x).get(y);
                // Make a wall object on "w"
                if (tileRead.equals("w")) {
                    newTile = tileFactory.makeWallAt(newPosition);
                    // Make a StaticItemPickup object with KeyFactory as parameter to store in the floor tile
                } else if (keys.contains(tileRead)) {
                    keyCount++;
                    newTile = tileFactory.makeFloorAt(newPosition);
                    StaticObject keyCard = new StaticItemPickup(Key.DEFAULT_SCORE, new KeyFactory(Integer.parseInt(tileRead)), newTile);
                    newTile.setObject(keyCard);
                    // Make a StaticItemPickup object with DocumentFactory as parameter to store in the floor tile
                } else if (tileRead.equals("r")) {
                    documentCount++;
                    newTile = tileFactory.makeFloorAt(newPosition);
                    StaticObject document = new StaticItemPickup(Document.DEFAULT_SCORE, new DocumentFactory(), newTile);
                    newTile.setObject(document);
                } else if (tileRead.equals("m")) {
                    newTile = tileFactory.makeFloorAt(newPosition);
                } else if (tileRead.equals("c")) {
                    // Make a Camera object to store in StaticObject variable of the floor tile
                    newTile = tileFactory.makeFloorAt(newPosition);
                    StaticObject camera = new Camera(newTile, Camera.DEFAULT_SCORE);
                    newTile.setObject(camera);
                    // Add the newly created tile to the guard spawn List for spawning
                } else if (tileRead.equals("g")) {
                    newTile = tileFactory.makeFloorAt(newPosition);
                    guardSpawns.add(newTile);
                    // Make a lockable door
                } else if (locked_doors.contains(tileRead)) {
                    newTile = tileFactory.makeLockableDoorAt(newPosition, KeyIDConverter.doorStringToKeyID(tileRead));
                } else if (tileRead.equals("d")) {
                    newTile = tileFactory.makeDoorAt(newPosition);
                } else {
                    newTile = tileFactory.makeFloorAt(newPosition);
                }
                newRow.add(newTile);
            }
            this.tiles.add(newRow);
        }

        // Sets the start position and end position of the game
        this.startCell = getTile(0, 14);
        this.endCell = getTile(36, 0);
    }

    public void tick() {
    }

    /**
     * @param position the x, y position of the tile
     * @return reference to the tile object
     */
    public Tile getTileAt(Position position) {
        return tiles.get(position.getX()).get(position.getY());
    }

    /**
     * @param x the x position of the tile
     * @param y the y position of the tile
     * @return reference to the tile object
     */
    public Tile getTile(int x, int y) {
        return this.tiles.get(x).get(y);
    }

    /**
     * @return reference to the tile in which the main Player starts the game
     */
    public Tile getStartCell() {
        return startCell;
    }

    /**
     * @return reference to the tile in which the game state ends if required items are all present the Player's inventory
     */
    public Tile getEndCell() {
        return endCell;
    }

    /**
     * @return the width of the MapGrid
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height of the MapGrid
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the reference to the List of tiles that has the guards spawning at the start of the game
     */
    public List<Tile> getGuardSpawns() {
        return guardSpawns;
    }

    /**
     * @return the current number of documents in the Player's inventory
     */
    public int getDocumentCount() {
        return documentCount;
    }

    /**
     * @return the current number of keys in the Player's inventory
     */
    public int getKeyCount() {
        return keyCount;
    }
}
