package com.group8project.characters;

import com.group8project.common.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A controller for a character using the keyboard
 */
public class UserInputController extends CharacterController implements KeyListener {
    /**
     * The current direction of the player
     */
    private Direction currentDirection;

    /**
     * @param character the character being controlled
     */
    public UserInputController(Character character) {
        super(character);
    }

    /**
     * Moves the character by the given direction
     *
     * @param tick the tick on which the character moves
     */
    @Override
    public void performAction(long tick) {
        if (currentDirection != null) {
            if (getCharacter().move(currentDirection)) {
                getCharacter().setLastMovedTick(tick);
            }
            currentDirection = null;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    /**
     * Set the direction of the next move based on the user input
     *
     * @param keyEvent key event with user input
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            currentDirection = Direction.UP;
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            currentDirection = Direction.LEFT;
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            currentDirection = Direction.RIGHT;
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            currentDirection = Direction.DOWN;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }
}
