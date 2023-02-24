package com.group8project.characters;

import com.group8project.common.Direction;
import com.group8project.common.Position;
import com.group8project.grid.Tile;

import java.util.Optional;

/**
 * Represents a character in the game's map
 */
public abstract class Character {

    /**
     * The moving speed of the character
     */
    private int speed;

    /**
     * The tile the character is in
     */
    private Tile tile;

    /**
     * The tick on which the charatcer's last move
     */
    private long lastMovedTick;

    /**
     * The last position of the character
     */
    private Position lastPosition;

    /**
     * Initialize a character with some properties
     *
     * @param tile the tile the character is currently in
     */
    public Character(Tile tile) {
        this.tile = tile;
        speed = 1;
        lastMovedTick = 0;
        lastPosition = tile.getPosition();
    }

    /**
     * Removes the character from current tile and
     * places the character in next tile by the direction given
     * if the target tile isn't enterable, it will do nothing
     *
     * @param direction the direction of the tile which character is moving to
     */
    public boolean move(Direction direction) {
        Tile nextTile = tile.neighbor(direction);

        if (nextTile == null) {
            return false;
        }

        Optional<Character> maybeCharacter = nextTile.getCharacter();
        if (maybeCharacter.isPresent()) {
            Character collisionCharacter = maybeCharacter.get();
            collisionCharacter.collide(this);
            this.collide(collisionCharacter);
        }

        if (nextTile.isEnterable()) {
            lastPosition = new Position(getPosition().getX(), getPosition().getY());
            tile.leave();
            nextTile.enter(this);
            tile = nextTile;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the position of the tile
     */
    public Position getPosition() {
        return this.tile.getPosition();
    }

    /**
     * @param target the target which collides with the character
     */
    public void collide(Character target) {
    }

    /**
     * @return the tile
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * A setter method for the tile
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * @return the tick on which the character's last move
     */
    public long getLastMovedTick() {
        return lastMovedTick;
    }

    /**
     * A setter method for the tick on which the character's last move
     */
    public void setLastMovedTick(long lastMovedTick) {
        this.lastMovedTick = lastMovedTick;
    }

    /**
     * @return the last position of the character
     */
    public Position getLastPosition() {
        return lastPosition;
    }
}
