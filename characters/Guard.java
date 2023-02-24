package com.group8project.characters;

import com.group8project.grid.Tile;

/**
 * A guard (moving enemy) which will end the game if the player collides with it,
 * resulting in a loss for the player
 */
public class Guard extends Enemy {
    /**
     * Initialize a guard with the tile it is in
     *
     * @param tile the tile the guard is currently in
     */
    public Guard(Tile tile) {
        super(tile);
    }
}
