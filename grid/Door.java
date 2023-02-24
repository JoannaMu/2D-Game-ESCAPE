package com.group8project.grid;

import com.group8project.characters.Character;
import com.group8project.characters.Player;
import com.group8project.common.Position;

/**
 * A door class that can be opened and closed
 */
public class Door extends Tile {
    private boolean isOpen;

    /**
     * Constructor that sets the current open/closed boolean to false
     *
     * @param position the x, y position where the door resides relative to the MapGrid
     * @param parentGrid the parent MapGrid in which the door resides
     */
    public Door(Position position, MapGrid parentGrid) {
        super(position, parentGrid);
        this.isOpen = false;
    }

    /**
     * @return the value of Open/Closed boolean
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Set the boolean value of open/close explicitly
     *
     * @param value True or False, whether the door is opened or closed
     */
    public void setOpen(boolean value) {
        this.isOpen = value;
    }

    /**
     * Checks if the door is enterable
     *
     * @return true when the door is open, false when closed
     */
    @Override
    public boolean isEnterable() {
        return isOpen && super.isEnterable();
    }

    /**
     * Toggles the open status of the door
     */
    public void toggleOpen() {
        isOpen = !isOpen;
    }

    /**
     * Opens the door
     *
     * @param character The character using the door
     */
    @Override
    public void use(Character character) {
        if (character instanceof Player) {
            setOpen(true);
        }
    }
}
