package com.group8project.grid;

import com.group8project.common.Position;

/**
 * An empty tile on which a Character can stand
 */
public class Floor extends Tile {
    /**
     * Constructor that sets position and parent MapGrid
     *
     * @param position the x, y position where the floor resides relative to the MapGrid
     * @param parentGrid the parent MapGrid in which the floor resides
     */
    public Floor(Position position, MapGrid parentGrid) {
        super(position, parentGrid);
    }

    /**
     * @return Always returns true
     */
    @Override
    public boolean isEnterable() {
        return super.isEnterable();
    }
}
