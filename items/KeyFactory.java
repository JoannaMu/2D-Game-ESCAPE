package com.group8project.items;

/**
 * Creates a key card
 */
public class KeyFactory extends ItemFactory {
    private int lockID;

    /**
     * Initialize the Key item with a valid lockID
     *
     * @param lockID the lockID of key card used to pair with one locked door
     */
    public KeyFactory(int lockID) {
        this.lockID = lockID;
    }

    /**
     * @return the created key with a valid lockID
     */
    public Item createItem() {
        return new Key(this.lockID);
    }
}
