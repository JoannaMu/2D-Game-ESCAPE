package com.group8project.objects;

import com.group8project.characters.Player;
import com.group8project.common.Position;
import com.group8project.grid.Tile;

/**
 * Represents a static object in the game's map
 */
public abstract class StaticObject {
    /**
     * The tile the StaticObject is in
     */
    private Tile parentTile;

    StaticObject(Tile parentTile) {
        this.parentTile = parentTile;
    }

    /**
     * Handles the behaviour of the game when a player has entered a tile occupied by a staticObject
     * i.e., Adding the object to the inventory, punishing the player etc.
     *
     * @param player the player that has entered the tile
     */
    public abstract void enter(Player player);

    /**
     * Removes a staticObject from the tile it occupies (removes the object from the map)
     * Precondition: A player has entered the tile that is occupied by a staticObject
     */
    public void remove() {
        parentTile.setObject(null);
        parentTile = null;
    }

    /**
     * @return the position of the StaticObject
     */
    public Position getPosition() {
        return parentTile.getPosition();
    }

    /**
     * @return the tile the StaticObject is in
     */
    public Tile getParentTile() {
        return this.parentTile;
    }

    /**
     * Sets the parent tile of the static object to be parentTile
     * @param parentTile the new tile for the static object to be in
     */
    public void setParentTile(Tile parentTile) {
        this.parentTile = parentTile;
    }
}
