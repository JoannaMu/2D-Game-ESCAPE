package com.group8project.grid;

import com.group8project.characters.Character;
import com.group8project.characters.Player;
import com.group8project.common.Direction;
import com.group8project.common.Position;
import com.group8project.objects.StaticObject;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * An abstract class that represents a single tile in the game's map
 */
public abstract class Tile {
    private MapGrid parentGrid;
    private Position position;

    private Character character;

    private StaticObject object;

    public Tile(Position position, MapGrid parentGrid) {
        this.parentGrid = parentGrid;
        this.position = position;
    }

    /**
     * Gets an Optional containing the character in the tile.
     * If there is no character then the Optional will be empty
     *
     * @return An Optional containing the character in the tile
     */
    public Optional<Character> getCharacter() {
        if (character == null) {
            return Optional.empty();
        } else {
            return Optional.of(character);
        }
    }

    /**
     * Gets an Optional containing the object in the tile.
     * If there is no object then the Optional will be empty
     *
     * @return An Optional containing the object in the tile
     */
    public Optional<StaticObject> getObject() {
        if (object == null) {
            return Optional.empty();
        } else {
            return Optional.of(object);
        }
    }

    public void setObject(StaticObject object) {
        this.object = object;
    }

    /**
     * Checks whether a tile is enterable by Characters
     *
     * @return whether the tile is enterable
     */
    public boolean isEnterable() {
        return getCharacter().isEmpty();
    }

    /**
     * Returns the tile's neighbor
     *
     * @param direction the direction of the returned tile
     * @return the neighboring tile in the given direction
     */
    @Nullable
    public Tile neighbor(Direction direction) {
        int x = this.position.getX();
        int y = this.position.getY();

        int xmax = parentGrid.getWidth() - 1;
        int ymax = parentGrid.getHeight() - 1;

        // Checks if the tile trying to be accessed is within the bounds of the MapGrid, return null if not
        if (direction == Direction.UP && !(y == 0)) {
            return parentGrid.getTile(x, y - 1);
        } else if (direction == Direction.DOWN && !(y == ymax)) {
            return parentGrid.getTile(x, y + 1);
        } else if (direction == Direction.RIGHT && !(x == xmax)) {
            return parentGrid.getTile(x + 1, y);
        } else if (direction == Direction.LEFT && !(x == 0)) {
            return parentGrid.getTile(x - 1, y);
        } else {
            return null;
        }
    }

    /**
     * @return the x, y position of the Tile in the MapGrid it belongs to
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Performs a "use" action on the tile. Default is to do nothing
     *
     * @param character the character using the Tile
     */
    public void use(Character character) {

    }

    /**
     * Moves the character into this tile. Assumes that the tile
     * is enterable and not blocked by another Character
     *
     * @param character the character to move to the tile
     */
    public void enter(Character character) {
        this.character = character;
        if (character instanceof Player) {
            this.getObject().ifPresent(x -> x.enter((Player) character));
        }
    }

    /**
     * Removes the character from this tile
     */
    public void leave() {
        character = null;
    }
}
