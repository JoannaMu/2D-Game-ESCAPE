package com.group8project.grid;

/**
 * A factory that creates map grids and related objects
 */
public class MapGridFactory {
    /**
     * Loads a map by taking an input of PATH to the map text file as the parameter
     *
     * @return a new MapGrid object parsed from that specific text file
     */
    public MapGrid makeMapGrid() {
        return new MapGrid("src/main/resources/maps/finalmapalpha.txt");
    }
}
