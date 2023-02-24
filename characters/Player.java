package com.group8project.characters;

import com.group8project.common.Direction;
import com.group8project.grid.Tile;
import com.group8project.items.Document;
import com.group8project.items.Item;
import com.group8project.items.Key;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player (main character) of the game
 */
public class Player extends Character {
    /**
     * The penalty score the player receives if it collides with the enemy
     */
    private static final int ENEMY_SCORE_PENALTY = -500;

    /**
     * The items the player has picked up
     */
    public List<Item> items;

    /**
     * The score of the player
     */
    private int score;

    /**
     * True if the player is dead, false otherwise
     */
    private boolean isDead;

    /**
     * Initialize a player with some properties
     * @param tile the tile the player is currently in
     */
    public Player(Tile tile) {
        super(tile);
        score = 0;
        isDead = false;
        items = new ArrayList<>();
    }

    /**
     * @return the score of the player
     */
    public int getScore() {
        return this.score;
    }

    /**
     * A setter method of the player's score
     * @param newscore the updated score of the player
     */
    public void setScore(int newscore) {
        this.score = newscore;
    }

    /**
     * @param character the character collides with the player
     */
    public void collide(Character character) {
        if (character instanceof Enemy) {
            collide((Enemy) character);
        }
    }

    /**
     * End the game if the player collides with an enemy
     * @param target the enemy which collides with the player
     */
    private void collide(Enemy target) {
        // game should end
        addScore(ENEMY_SCORE_PENALTY);
        getTile().leave();
        isDead = true;
    }

    @Override
    // Player class move method
    public boolean move(Direction direction) {
        // Calls Character class move method
        if (super.move(direction)) {
            // If successful(true), Player class move method calls use method on each adjacent tiles
            for (Direction neighborDirection : Direction.values()) {
                Tile neighbor = getTile().neighbor(neighborDirection);
                if (neighbor != null) {
                    neighbor.use(this);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the key card can unlock a door
     *
     * @param lockID the lockID of a key card
     * @return true if the lockID of the key is the same as the lockID of the door, false otherwise
     */
    public boolean canUnlock(int lockID) {
        for (Item item : items) {
            if (item instanceof Key) {
                int keyID = ((Key) item).getLockID();
                if (keyID == lockID) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Adds the received score to the total score of the player
     *
     * @param score the score the player received
     */
    public void addScore(int score) {
        this.score = this.score + score;
    }

    public boolean isDead() {
        return isDead;
    }

    /**
     * @return the items the player has
     */
    public List<Item> getItems() {
        return this.items;
    }

    /**
     * Adds the new picked up item into the item list of the player
     *
     * @param itemCreated the item the player just picked up
     */
    public void addItem(Item itemCreated) {
        this.items.add(itemCreated);
    }

    /**
     * @return the number of documents the player has
     */
    public int getDocumentCount() {
        return (int) items.stream().filter(item -> item instanceof Document).count();
    }

    /**
     * @return the number of key cards the player has
     */
    public int getKeyCount() {
        return (int) items.stream().filter(item -> item instanceof Key).count();
    }
}

