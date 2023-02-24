package com.group8project.objects;

import com.group8project.characters.Player;
import com.group8project.grid.Tile;
import com.group8project.items.Item;
import com.group8project.items.ItemFactory;

/**
 * An object that gives the player an item when activated.
 * Used to give the player keys and documents (required rewards).
 */
public class StaticItemPickup extends SingleUseStaticReward {
    /**
     * Items are created from here when the player enters the tile containing this object
     */
    private ItemFactory factory;

    /**
     * Initializes the StaticItemPickup
     */
    public StaticItemPickup(int score, ItemFactory factory, Tile parentTile) {
        super(parentTile, score);
        this.factory = factory;
    }

    /**
     * Handles the behaviour of the game when a player has entered a tile occupied by item that can be picked up
     * i.e. add Keys, documents to the player's inventory and increase the player's score
     */
    @Override
    public void enter(Player player) {
        super.enter(player);
        player.addItem(createItem());
    }

    /**
     * @return an item created from the factory
     */
    public Item createItem() {
        return factory.createItem();
    }
}
