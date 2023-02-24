package com.group8project.characters;

/**
 * A controller for a Character
 */
abstract public class CharacterController {
    private Character character;

    /**
     * @param character the character being controlled
     */
    public CharacterController(Character character) {
        this.character = character;
    }

    /**
     * Performs an action on the Character controlled by this CharacterController
     *
     * @param tick the tick on which the action is performed
     */
    abstract public void performAction(long tick);

    /**
     * @return the character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * A setter method for the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }
}
