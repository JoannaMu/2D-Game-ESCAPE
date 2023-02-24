package com.group8project.grid;

import com.group8project.common.Position;

/**
 * A factory that creates tiles and related objects
 */
public class TileFactory {
    private MapGrid parentGrid;

    public TileFactory(MapGrid parentGrid) {
        this.parentGrid = parentGrid;
    }

    /**
     * Door factory that returns a door object
     *
     * @param position the x, y, coordinate where the door resides relative to the MapGrid
     * @return a new door object
     */
    public Door makeDoorAt(Position position) {
        return new Door(position, this.parentGrid);
    }

    /**
     * Lockable door factory that returns a lockable door object
     *
     * @param position the x, y, coordinate where the lockable door resides relative to the MapGrid
     * @param lockID the lockID unique to this lockable door
     * @return a new lockable door object
     */
    public LockableDoor makeLockableDoorAt(Position position, int lockID) {
        return new LockableDoor(position, this.parentGrid, lockID);
    }

    /**
     * Floor factory that returns a floor object
     *
     * @param position the x, y, coordinate where the floor resides relative to the MapGrid
     * @return a new floor object
     */
    public Floor makeFloorAt(Position position) {
        return new Floor(position, this.parentGrid);
    }

    /**
     * Wall factory that returns a wall object
     *
     * @param position the x, y, coordinate where the wall resides relative to the MapGrid
     * @return a new wall object
     */
    public Wall makeWallAt(Position position) {
        return new Wall(position, this.parentGrid);
    }

}
