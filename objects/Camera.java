package com.group8project.objects;

import com.group8project.characters.Player;
import com.group8project.grid.Tile;

/**
 * A camera that decreases the score of the player when they walk into a tile containing it.
 * It is a static punishment
 */
public class Camera extends StaticReward {
    /**
     * The default score the player receives when activating the camera
     */
    public static final int DEFAULT_SCORE = -250;
    /**
     * True if the camera has been activated by the player, false otherwise
     */
    private boolean activated;

    /**
     * Initializes a camera object
     * @param score the score to initialize the camera with
     */
    public Camera(Tile parentTile, int score) {
        super(parentTile, score);
        activated = false;
    }

    /**
     * @return whether the camera is activated
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Handles the behaviour of the game when a player has entered a tile occupied by a Camera
     * Decreases the score of the player
     */
    @Override
    public void enter(Player player) {
        if (!activated) {
            super.enter(player);
            activated = true;
        }
    }
}
