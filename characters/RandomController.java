package com.group8project.characters;

import com.group8project.common.Direction;

import java.util.Random;

/**
 * A random controller for a character
 */
public class RandomController extends CharacterController {
    protected static Random random = new Random();

    /**
     * @param character the character being controlled
     */
    public RandomController(Character character) {
        super(character);
    }

    /**
     * Moves the character in a random direction
     *
     * @param tick the tick on which the character moves
     */
    @Override
    public void performAction(long tick) {
        if (getCharacter().move(getRandomDirection())) {
            getCharacter().setLastMovedTick(tick);
        }
    }

    /**
     * @return the random direction
     */
    private Direction getRandomDirection() {
        int randDirNum = random.nextInt(Direction.class.getEnumConstants().length);
        return Direction.class.getEnumConstants()[randDirNum];
    }
}
