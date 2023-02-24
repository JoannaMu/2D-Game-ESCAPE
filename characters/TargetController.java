package com.group8project.characters;

import com.group8project.common.Direction;
import com.group8project.common.Position;
import com.group8project.grid.Tile;

import java.util.Optional;

/**
 * A controller for the target enemy
 */
public class TargetController extends RandomController {
    /**
     * The default probability of the target moves randomly
     */
    public static final float DEFAULT_PROBABILITY = 0.5f;

    /**
     * The probability of the target moves randomly
     */
    float randomProbability;
    private Character target;

    /**
     * @param character the player
     * @param target the target being controlled
     * @param randomProbability the probability of the target moves randommly
     */
    public TargetController(Character character, Character target, float randomProbability) {
        super(character);
        this.target = target;
        this.randomProbability = randomProbability;
    }

    /**
     * Moves the target randomly or towards player according to its probability
     *
     * @param tick the tick on which the target moves
     */
    @Override
    public void performAction(long tick) {
        if (random.nextFloat() < randomProbability) {
            // move randomly with randomProbability% chance
            super.performAction(tick);
        } else {
            // move towards player with (1 - randomProbability)%  chance
            int minDistance = Integer.MAX_VALUE;
            Direction bestDirection = null;
            for (Direction direction : Direction.values()) {
                Tile potentialMove = getCharacter().getTile().neighbor(direction);
                if (potentialMove == null) {
                    continue;
                }

                Optional<Character> optionalCharacter = potentialMove.getCharacter();
                if (optionalCharacter.isPresent()) {
                    Character character = optionalCharacter.get();
                    // short circuit if player is reachable
                    if (character instanceof Player) {
                        bestDirection = direction;
                        break;
                    }
                }

                if (potentialMove.isEnterable()) {
                    // get manhattan distance to player
                    Position vecToTarget = potentialMove.getPosition().minus(target.getPosition());
                    int distance = Math.abs(vecToTarget.getX()) + Math.abs(vecToTarget.getX());
                    if (distance < minDistance) {
                        minDistance = distance;
                        bestDirection = direction;
                    }
                }
            }

            if (bestDirection != null) {
                if (getCharacter().move(bestDirection)) {
                    getCharacter().setLastMovedTick(tick);
                }
            } else {
                super.performAction(tick);
            }
        }
    }
}
