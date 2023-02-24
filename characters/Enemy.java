package com.group8project.characters;

import com.group8project.grid.Tile;

/**
 * Represents an enemy in the game's map
 */
public abstract class Enemy extends Character {
    /**
     * Initialize an enemy with the tile it is in
     *
     * @param tile the tile the enemy is currently in
     */
    public Enemy(Tile tile) {
        super(tile);
    }

    /**
     * @param player the player which collides with the enemy
     */
    public void collide(Player player) {

    }
}
