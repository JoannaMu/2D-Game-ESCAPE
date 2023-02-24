package com.group8project.grid;

import com.group8project.characters.Character;
import com.group8project.characters.Player;
import com.group8project.common.Position;

/**
 * A Door that is locked, but can be unlocked if the player has a key
 */
public class LockableDoor extends Door {
    private int lockID;

    /**
     * Constructor that creates a LockableDoor extended from Door class and sets the initial state to be locked
     *
     * @param position the x, y position where the LockableDoor resides relative to the MapGrid
     * @param parentGrid the parent MapGrid in which the LockableDoor resides
     * @param lockID a specific lockID unique to that LockableDoor
     */
    public LockableDoor(Position position, MapGrid parentGrid, int lockID) {
        super(position, parentGrid);
        this.lockID = lockID;
        super.setOpen(false);
    }

    /**
     * Allows the player to call the use override method
     *
     * @param character The character using the door
     */
    @Override
    public void use(Character character) {
        if (character instanceof Player) {
            use((Player) character);
        }
    }

    /**
     * Opens the door if the Player has the required key
     *
     * @param player The player attempting to open the door
     */
    public void use(Player player) {
        if (player.canUnlock(lockID)) {
            super.setOpen(true);
        }
    }

    /**
     * @return the LockID to compare with the keyID that the Player holds
     */
    public int getLockID() {
        return lockID;
    }
}
