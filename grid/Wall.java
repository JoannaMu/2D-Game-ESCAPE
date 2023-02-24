package com.group8project.grid;

import com.group8project.common.Position;

/**
 * A wall tile that characters cannot pass through
 */
public class Wall extends Tile {
    /**
     * Set the x, y position in the MapGrid and takes in reference to the parent grid that it is in
     *
     * @param position the x, y position of the tile relative to the parent grid
     * @param parentGrid the parent grid in which the Wall resides
     */
    Wall(Position position, MapGrid parentGrid) {
        super(position, parentGrid);
    }

    /**
     * Returns whether the wall is enterable (it is never enterable)
     *
     * @return Always returns false
     */
    @Override
    public boolean isEnterable() {
        return false;
    }
}
