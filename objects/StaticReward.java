package com.group8project.objects;

import com.group8project.characters.Player;
import com.group8project.grid.Tile;

/**
 * A StaticObject that gives the player score when activated
 */
public class StaticReward extends StaticObject {
    /**
     * The score to give the player
     */
    private int score;

    /**
     * Constructor: Initializes the score variable
     */
    public StaticReward(Tile parentTile) {
        this(parentTile, 0);
    }

    /**
     * Initializes the StaticObject with a set score
     * @param score the score to give the player
     */
    public StaticReward(Tile parentTile, int score) {
        super(parentTile);
        this.score = score;
    }

    /**
     * @return the score the static reward gives
     */
    public int getScore() {
        return this.score;
    }

    /**
     * A setter method for the score variable
     * Assigns a staticReward object with a respective score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Handles the behaviour of the game when a player has entered a tile occupied by a staticReward
     * i.e., Increasing the score (Positive Reward), Decreasing the score (Punishment) etc.
     * Intended: enter() should take in a Player object as an attribute i.e., enter(Player)
     */
    @Override
    public void enter(Player player) {
        player.addScore(score);
    }
}
