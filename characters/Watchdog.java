package com.group8project.characters;

import com.group8project.grid.Tile;

/**
 *  A watchdog (moving enemy) which will end the game if the player collides with it,
 *  resulting in a loss for the player
 */
public class Watchdog extends Enemy {
    /**
     * Initialize a watchdog with the tile it is in
     *
     * @param tile the tile the watchdog is currently in
     */
    public Watchdog(Tile tile) {
        super(tile);
    }
}
