package com.group8project.common;

/**
 * Holds the 2D position ((x, y) coordinates) of an object that is part of a map grid
 */
public class Position {
    private int x;
    private int y;

    /**
     * Initialize the current position to x = 0, y = 0
     */
    public Position() {
        setX(0);
        setY(0);
    }

    /**
     * sets the x, y coordinates
     * @param x takes in x coordinate of the object
     * @param y takes in y coordinate of the object
     */
    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * @return the value of x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @param x set the x coordinate value independent of y
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the value of y coordinate
     */

    public int getY() {
        return y;
    }

    /**
     * @param y sets the y coordinate value independent of x
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * String representation of the object
     * @return the value of x and y coordinates in string format
     */
    public String toString() {
        return "x: " + getX() + " y: " + getY();
    }

    /**
     * Returns this - otherPosition
     *
     * @param otherPosition the vector to subtract from
     * @return the result of the subtraction
     */
    public Position minus(Position otherPosition) {
        return new Position(getX() - otherPosition.getX(), getY() - otherPosition.getY());
    }

    /**
     * Returns this + otherPosition
     *
     * @param otherPosition the vector to add
     * @return the result of the addition
     */
    public Position plus(Position otherPosition) {
        return new Position(getX() + otherPosition.getX(), getY() + otherPosition.getY());
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj instanceof Position) {
            Position otherPos = (Position) otherObj;
            return otherPos.getX() == getX() && otherPos.getY() == getY();
        } else {
            return super.equals(otherObj);
        }
    }
}
