package com.group8project.objects;

import com.group8project.characters.Player;
import com.group8project.grid.Tile;

/**
 * A StaticReward that removes itself from the map when activated
 */
public class SingleUseStaticReward extends StaticReward {
    SingleUseStaticReward(Tile parentTile) {
        this(parentTile, 0);
    }

    SingleUseStaticReward(Tile parentTile, int score) {
        super(parentTile, score);
    }

    /**
     * Handles a player entering the StaticReward by removing itself
     * @param player the player entering the StaticReward
     */
    @Override
    public void enter(Player player) {
        super.enter(player);
        this.remove();
    }
}
