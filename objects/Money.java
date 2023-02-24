package com.group8project.objects;

import com.group8project.grid.Tile;

/**
 * Money that increments the player's score when picked up.
 * This is a bonus reward
 */
public class Money extends SingleUseStaticReward {
    /**
     * The default score the player receives from the money
     */
    public static final int DEFAULT_SCORE = 500;

    public static final int DEFAULT_TICKS = 7;

    /**
     * Number of ticks until the money despawns
     */
    private long ticksRemaining;

    /**
     * Initializes a Money object
     * @param score the score the player receives form the money
     * @param ticksRemaining the number of ticks the money should last for
     */
    public Money(Tile parentTile, int score, int ticksRemaining) {
        super(parentTile, score);
        this.ticksRemaining = ticksRemaining;
    }

    /**
     * Reduces the ticks remaining by one
     */
    public void tick() {
        if (ticksRemaining == 1) {
            this.remove();
        } else {
            ticksRemaining--;
        }
    }
}
