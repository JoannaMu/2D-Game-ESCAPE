package com.group8project.items;

/**
 * A Key card (regular reward) that can be picked up by the player
 * Increases the score of the player
 */
public class Key extends Item {
    /**
     * The default score the player receives from the key card
     */
    public static final int DEFAULT_SCORE = 250;

    /**
     * Set a lockID, used to pair with one locked door
     */
    private int keyID;

    /**
     * Initialize a Key item
     *
     * @param keyID the lockID of key card used to pair with one locked door
     */
    public Key(int keyID) {
        this.keyID = keyID;
    }

    /**
     * @return the lockID of the current key
     */
    public int getLockID() {
        return keyID;
    }
}
