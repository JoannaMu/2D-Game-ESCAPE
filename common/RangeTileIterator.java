package com.group8project.common;

import com.group8project.grid.MapGrid;
import com.group8project.grid.Tile;

import java.util.Iterator;

/**
 * Iterator class for iterating over the tiles
 */
public class RangeTileIterator implements Iterator<Tile> {
    MapGrid grid;
    private Position minPosition;
    private Position currPosition;
    private Position maxPosition;

    /**
     * Constructor that sets the minimum position of x and y as the current position
     * @param grid the parent grid to iterate
     * @param minPosition the minimum position
     * @param maxPosition the maximum position
     */
    public RangeTileIterator(MapGrid grid, Position minPosition, Position maxPosition) {
        currPosition = new Position(minPosition.getX(), minPosition.getY());
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.grid = grid;
    }

    /**
     * Override method of Iterator class hasNext
     * Checks if the List of tiles has a next tile
     *
     * @return boolean of True if has next, False if no next
     */
    @Override
    public boolean hasNext() {
        return (minPosition.getX() <= currPosition.getX() && currPosition.getX() <= maxPosition.getX()
                && minPosition.getY() <= currPosition.getY() && currPosition.getY() <= maxPosition.getY());
    }

    /**
     * Override method of Iterator class next
     * Returns the next tile in the List of tiles
     *
     * @return returns the next tile in the List
     */
    @Override
    public Tile next() {
        Tile retTile = grid.getTileAt(currPosition);
        if (currPosition.getX() == maxPosition.getX()) {
            currPosition.setY(currPosition.getY() + 1);
            currPosition.setX(minPosition.getX());
        } else {
            currPosition.setX(currPosition.getX() + 1);
        }
        return retTile;
    }
}
