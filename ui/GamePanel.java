package com.group8project.ui;

import com.group8project.Game;
import com.group8project.GameOverListener;
import com.group8project.characters.Character;
import com.group8project.characters.Player;
import com.group8project.common.Position;
import com.group8project.common.RangeTileIterator;
import com.group8project.grid.*;
import com.group8project.items.Item;
import com.group8project.items.Key;
import com.group8project.objects.Camera;
import com.group8project.objects.Money;
import com.group8project.objects.StaticItemPickup;
import com.group8project.objects.StaticObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class GamePanel extends JPanel {
    /**
     * The color of the map's exit tile
     */
    private static Color ENDTILE_COLOR = new Color(0, 255, 0, 50);
    /**
     * The instance of the Game to render
     */
    private Game game;
    /**
     * The GamePanel's parent. Used to switch to the end screen on win/loss.
     */
    private ScreenManager screenManager;
    /**
     * The background image for the UI
     */
    private BufferedImage UIBackgroundImage;
    /**
     * The number of keys on the map
     */
    private int requiredKeys;
    /**
     * The number of documents required to win the game
     */
    private int requiredDocuments;
    /**
     * The height of the tiles on screen in game coordinates
     */
    private int renderWidth = 21;
    /**
     * The width of the tiles on the screen in game coordinates
     */
    private int renderHeight = 12;
    /**
     * The time since the last tick in nanoseconds. Used for animations
     */
    private long timeSinceLastTick;

    /**
     * Creates a new GamePanel
     * @param game the game to render
     * @param screenManager the parent ScreenManager of this GamePanel object
     */
    GamePanel(Game game, ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.game = game;
        this.UIBackgroundImage = screenManager.UIBackgroundImage;
        this.requiredKeys = game.getRequiredKeyCount();
        this.requiredDocuments = game.getRequiredDocumentCount();

        game.addGameOverListener(new GameOverHandler());

        // makes the window focusable so that key bindings work
        setFocusable(true);
        screenManager.setContentPane(this);

        // starts a new thread to repaint the canvas at 60 FPS
        Thread t = new Thread(new Repainter());
        t.start();
    }

    /**
     * Paints the GamePanel with the rendered game
     * @param g The graphics object on which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        if (game.gameLock.tryLock()) {
            try {
                // sets time since the last tick at the beginning of rendering so that animations are consistent
                timeSinceLastTick = System.nanoTime() - game.startOfTickTime;

                // draws the different parts of the game in layers
                drawBackground(g);
                drawMapGrid(g);
                drawStaticObjects(g);
                drawCharacters(g);
                drawUI(g);
            } finally {
                game.gameLock.unlock();
            }
        }
    }

    /**
     * Draws the UI onto the screen
     *
     * @param g the graphics object on which to draw the UI
     */
    private void drawUI(Graphics g) {
        g.drawImage(UIBackgroundImage, 0, 0, this);
        g.setColor(new java.awt.Color(206, 206, 206));
        // Time
        g.setFont(new Font("Monospaced", Font.BOLD, 34));
        g.drawString(game.getTimeElapsedStr(), 668, 46);
        // Score
        g.setFont(new Font("Monospaced", Font.BOLD, 28));
        g.drawString("Score:" + game.getScore(), 325, 38);
        // KeyCount
        g.drawString(game.getKeyCount() + "/" + this.requiredKeys, 955, 38);
        // DocumentCount
        g.drawString(game.getDocumentCount() + "/" + this.requiredDocuments, 1125, 38);
    }

    /**
     * Draws the static objects onto the screen
     *
     * @param g the graphics object on which to draw the UI
     */
    private void drawStaticObjects(Graphics g) {
        for (RangeTileIterator i = getTilesToRender(); i.hasNext(); ) {
            Tile currTile = i.next();
            Optional<StaticObject> maybeObject = currTile.getObject();

            if (maybeObject.isPresent()) {
                StaticObject object = maybeObject.get();
                drawStaticObject(g, object);
            }
        }
    }

    /**
     * Draws a single static object onto the screen
     * @param g the graphics object to draw onto
     * @param object the static object to be drawn
     */
    private void drawStaticObject(Graphics g, StaticObject object) {
        Position screenPos = toScreenPosition(object.getPosition());
        SpriteManager sm = SpriteManager.getInstance();
        Image sprite;

        int screenPosX = screenPos.getX();
        int screenPosY = screenPos.getY();
        int width = getTileWidth();
        int height = getTileHeight();
        if (object instanceof StaticItemPickup) {
            // Draw Item pickups based on the class of the item that would get picked up
            StaticItemPickup itemPickup = (StaticItemPickup) object;
            Item itemToSpawn = itemPickup.createItem();
            if (itemToSpawn instanceof Key) {
                sprite = getSprite((Key) itemToSpawn);
            } else {
                sprite = sm.getSprite("document").getAnimFrame(0);
            }
        } else if (object instanceof Camera) {
            Camera camera = (Camera) object;
            if (camera.isActivated()) {
                sprite = sm.getSprite("camera_red").getAnimFrame(0);
            } else {
                sprite = sm.getSprite("camera").getAnimFrame(0);
            }
        } else if (object instanceof Money) {
            sprite = sm.getSprite("money").getAnimFrame(0);
            // TODO: Put back of camera against wall if possible
        } else {
            sprite = sm.getSprite("box").getAnimFrame(0);
        }

        if (object instanceof StaticItemPickup || object instanceof Money) {
            // money and item pickups shouldn't take up the whole tile
            int borderSize = getTileWidth() / 4;
            screenPosX += borderSize;
            screenPosY += borderSize;
            width -= borderSize * 2;
            height -= borderSize * 2;
        }

        g.drawImage(sprite,
                screenPosX,
                screenPosY,
                width,
                height, this);
    }

    /**
     * Returns the sprite for a key based on its lock ID
     * @param key the key to get the sprite for
     * @return the sprite for the given key
     */
    private Image getSprite(Key key) {
        SpriteManager sm = SpriteManager.getInstance();
        Image sprite;
        if (key.getLockID() == 0) {
            sprite = sm.getSprite("keydot0").getAnimFrame(0);
        } else if (key.getLockID() == 1) {
            sprite = sm.getSprite("keydot1").getAnimFrame(0);
        } else if (key.getLockID() == 2) {
            sprite = sm.getSprite("keydot2").getAnimFrame(0);
        } else if (key.getLockID() == 3) {
            sprite = sm.getSprite("keydot3").getAnimFrame(0);
        } else if (key.getLockID() == 4) {
            sprite = sm.getSprite("keydot4").getAnimFrame(0);
        } else if (key.getLockID() == 5) {
            sprite = sm.getSprite("keydot5").getAnimFrame(0);
        } else {
            // any key ID above 5 is defaulted to render as key 6
            sprite = sm.getSprite("keydot6").getAnimFrame(0);
        }
        return sprite;
    }

    /**
     * Draw the characters onto the screen
     *
     * @param g the graphics object on which to draw the characters
     */
    private void drawCharacters(Graphics g) {
        for (RangeTileIterator i = getTilesToRender(); i.hasNext(); ) {
            Tile currTile = i.next();
            Optional<Character> maybeCharacter = currTile.getCharacter();

            if (maybeCharacter.isPresent()) {
                Character character = maybeCharacter.get();
                drawCharacter(g, character);
            }
        }
    }

    /**
     * Draw a single Character onto the graphics object g
     * @param g the graphics object to draw onto
     * @param character the character to draw onto g
     */
    private void drawCharacter(Graphics g, Character character) {
        Position screenPos = getCharacterPosition(character);
        Image sprite = getSprite(character);

        g.drawImage(sprite, screenPos.getX(), screenPos.getY(), getTileWidth(), getTileHeight(), this);
    }

    /**
     * Gets the sprite for a character based on its animation status.
     * @param character the Character to get a sprite for
     * @return the sprite for a character
     */
    private Image getSprite(Character character) {
        Image sprite;
        SpriteManager sm = SpriteManager.getInstance();
        long quarterOfTick = getTickTimeNs() / 4;
        long animTick = timeSinceLastTick / quarterOfTick;
        if (game.getTick() - 1 == character.getLastMovedTick() && animTick < 2) {
            // if the character moved recently, get a running animation
            AnimatedSprite animatedSprite;
            if (character instanceof Player) {
                animatedSprite = sm.getSprite("playerrun");
            } else {
                animatedSprite = sm.getSprite("cop1run");
            }
            sprite = getSpriteAtAnimTick(animatedSprite, animTick);
        } else {
            // if the character didn't move just get an idle sprite
            if (character instanceof Player) {
                sprite = sm.getSprite("playeridle").getAnimFrame(0);
            } else {
                sprite = sm.getSprite("copidle").getAnimFrame(0);
            }
        }

        return sprite;
    }

    /**
     * Gets a sprite based on the animation tick
     * @param animSprite the sprite's animation object
     * @param animTick the animation tick, which there are 4 of every regular game tick
     * @return the animSprite's sprite at tick animTick
     */
    private Image getSpriteAtAnimTick(AnimatedSprite animSprite, long animTick) {
        return animSprite.getAnimFrame((int) animTick % animSprite.getAnimLength());
    }

    /**
     * Draws a background onto g
     *
     * @param g the graphics object on which to draw the the background
     */
    private void drawBackground(Graphics g) {
        Position minPos = getMinOnScreen();
        Position maxPos = getMaxOnScreen();
        for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
            for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
                drawGrassAt(g, new Position(x, y));
            }
        }
    }

    /**
     * Draws grass onto at the given gamePos. The gamePos doesn't have to be in the MapGrid or on the screen.
     * @param g The graphics object onto which to draw the grass
     * @param gamePos The position in game coordinates where to draw the grass
     */
    private void drawGrassAt(Graphics g, Position gamePos) {
        Position screenPos = toScreenPosition(gamePos);

        SpriteManager sm = SpriteManager.getInstance();
        Image grassSprite = sm.getSprite("grass").getAnimFrame(0);
        g.drawImage(grassSprite, screenPos.getX(), screenPos.getY(), getTileWidth(), getTileHeight(), this);
    }

    /**
     * @return The center of the screen in game coordinates
     */
    private Position getCenter() {
        return game.getPlayer().getPosition();
    }

    private Position getCenterOffset() {
        int yOffset = 0;
        if (renderHeight % 2 == 0)
            yOffset = getTileHeight() / 2;

        int xOffset = 0;
        if (renderWidth % 2 == 0)
            xOffset = getTileWidth() / 2;

        // centers the screen even if the width and/or height is even
        Position evenOffset = new Position(xOffset, yOffset);

        Position posDiff = game.getPlayer().getPosition().minus(game.getPlayer().getLastPosition());
        // the amount the player will move at the end of the current tick in screen coordinates
        Position posDiffScreen = new Position(posDiff.getX() * getTileWidth(), posDiff.getY() * getTileHeight());
        float t = getT();
        if (game.getTick() - 1 == game.getPlayer().getLastMovedTick() && t <= 1 && t >= 0) {
            // animates the screen motion by linearly interpolating based on tick
            return lerp(posDiffScreen, new Position(), t).minus(evenOffset);
        } else {
            // if the player hasn't moved we don't need any animation
            return new Position().minus(evenOffset);
        }
    }

    /**
     * @return the minimum game position thats both on the screen and in the game's mapgrid
     */
    private Position getMinOnScreenInGrid() {
        Position minOnBackground = getMinOnScreen();
        return new Position(Math.max(minOnBackground.getX(), 0), Math.max(minOnBackground.getY(), 0));
    }

    /**
     * @return the maximum position thats both on the screen and in the game's mapgrid
     */
    private Position getMaxOnScreenInGrid() {
        MapGrid grid = game.getGrid();
        Position maxOnBackground = getMaxOnScreen();
        return new Position(Math.min(maxOnBackground.getX(), grid.getWidth() - 1), Math.min(maxOnBackground.getY(), grid.getHeight() - 1));
    }

    /**
     * @return the minimum game position thats on the screen. Can be outside of the mapgrid
     */
    private Position getMinOnScreen() {
        return new Position(getCenter().getX() - getWidthRadius() - 2, getCenter().getY() - getHeightRadius() - 2);
    }

    /**
     * @return the maximum position thats on the screen. Can be outside of the mapgrid
     */
    private Position getMaxOnScreen() {
        return new Position(getCenter().getX() + getWidthRadius() + 1, getCenter().getY() + getHeightRadius() + 1);
    }

    /**
     * @return the renderWidth as a radius
     */
    private int getWidthRadius() {
        return renderWidth / 2;
    }

    /**
     * @return the renderWidth as a radius
     */
    private int getHeightRadius() {
        return renderHeight / 2;
    }

    /**
     * @return the renderWidth of the map
     */
    public int getRenderWidth() {
        return renderWidth;
    }

    /**
     * @return the renderHeight of the map
     */
    public int getRenderHeight() {
        return renderHeight;
    }

    /**
     * Draws the part of the MapGrid surrounding center
     *
     * @param g The graphics object onto which to draw the MapGrid
     */
    private void drawMapGrid(Graphics g) {
        for (RangeTileIterator tileIt = getTilesToRender(); tileIt.hasNext(); ) {
            Tile tile = tileIt.next();
            drawTile(g, tile);
        }
    }

    /**
     * Draws the tile at the given game position onto g
     *
     * @param g    The graphics object onto which to draw the tile
     * @param tile The tile to draw
     */
    private void drawTile(Graphics g, Tile tile) {
        Position screenPos = toScreenPosition(tile.getPosition());

        Image sprite;
        SpriteManager sm = SpriteManager.getInstance();
        if (tile instanceof Floor) {
            sprite = sm.getSprite("ground").getAnimFrame(0);
        } else if (tile instanceof Door) {
            Door door = (Door) tile;
            sprite = getSprite(door);
        } else {
            sprite = sm.getSprite("wallgrey").getAnimFrame(0);
        }

        g.drawImage(sprite, screenPos.getX(), screenPos.getY(), getTileWidth(), getTileHeight(), this);
        g.setColor(Color.BLACK);
        g.drawRect(screenPos.getX(), screenPos.getY(), getTileWidth(), getTileHeight());

        if (tile == game.getGrid().getEndCell()) {
            g.setColor(ENDTILE_COLOR);
            g.fillRect(screenPos.getX(), screenPos.getY(), getTileWidth(), getTileHeight());
        }
    }

    /**
     * Gets the sprite for a door based on its lockID
     * @param door the door to get the sprite for
     * @return the sprite for the door
     */
    private Image getSprite(Door door) {
        Image sprite;
        SpriteManager sm = SpriteManager.getInstance();
        if (door.isOpen()) {
            // open doors are drawn as a ground tile for simplicity
            sprite = sm.getSprite("ground").getAnimFrame(0);
        } else {
            if (door instanceof LockableDoor) {
                // lockable doors need to have a sprite based on their lock ID
                LockableDoor lockableDoor = (LockableDoor) door;
                int id = lockableDoor.getLockID();
                if (id == 0) {
                    sprite = sm.getSprite("door0").getAnimFrame(0);
                } else if (id == 1) {
                    sprite = sm.getSprite("door1").getAnimFrame(0);
                } else if (id == 2) {
                    sprite = sm.getSprite("door2").getAnimFrame(0);
                } else if (id == 3) {
                    sprite = sm.getSprite("door3").getAnimFrame(0);
                } else if (id == 4) {
                    sprite = sm.getSprite("door4").getAnimFrame(0);
                } else if (id == 5) {
                    sprite = sm.getSprite("door5").getAnimFrame(0);
                } else {
                    sprite = sm.getSprite("door6").getAnimFrame(0);
                }
            } else {
                // regular doors all look the same
                sprite = sm.getSprite("dooropened").getAnimFrame(0);
            }
        }
        return sprite;
    }

    /**
     * Get the screen position of the character
     *
     * @param character the character to get the position of
     * @return the screen position of character
     */
    private Position getCharacterPosition(Character character) {
        if (game.getTick() - 1 == character.getLastMovedTick()) {
            Position currPos = toScreenPosition(character.getPosition());
            Position lastPos = toScreenPosition(character.getLastPosition());
            return lerp(lastPos, currPos, getT());
        } else {
            return toScreenPosition(character.getPosition());
        }
    }

    /**
     * @return The time of each game tick in nanoseconds
     */
    private long getTickTimeNs() {
        return game.getTickTime() * 1000000;
    }

    /**
     * @return the t used for linear interpolation in animations
     */
    private float getT() {
        return (float) timeSinceLastTick / (getTickTimeNs() / 2);
    }

    /**
     * Linearly interpolates between pos1 and pos2 based on t
     *
     * @param pos1 position at t = 0
     * @param pos2 position at t = 1
     * @param t    a float 0 <= t <= 1
     * @return a position linearly interpolated between pos1 and pos2
     */
    private Position lerp(Position pos1, Position pos2, float t) {
        return new Position(lerp(pos1.getX(), pos2.getX(), t), lerp(pos1.getY(), pos2.getY(), t));
    }

    /**
     * Linearly interpolates between i1 and i2 based on t
     *
     * @param i1 i1 at t = 0
     * @param i2 i2 at t = 1
     * @param t  a float 0 <= t <= 1
     * @return an int linearly interpolated between i1 and i2
     */
    private int lerp(int i1, int i2, float t) {
        t = Math.min(Math.max(t, 0), 1);
        return (int) (t * (float) (i2 - i1) + i1);
    }

    /**
     * @param gamePosition a position in Game coordinates
     * @return the gamePosition in screen coordinates
     */
    private Position toScreenPosition(Position gamePosition) {
        int xDistFromCenter = -getCenter().getX() + gamePosition.getX();
        int yDistFromCenter = -getCenter().getY() + gamePosition.getY();

        Position screenPosition = new Position();
        // offsets created to center game in case screen resolution/aspect ratio doesn't match
        int xOffset = (getWidth() - getTileWidth() * renderWidth) / 2;
        int yOffset = (getHeight() - getTileHeight() * renderHeight) / 2;
        Position centerOffset = getCenterOffset();
        screenPosition.setX(getTileWidth() * (xDistFromCenter + renderWidth / 2) + xOffset + centerOffset.getX());
        screenPosition.setY(getTileHeight() * (yDistFromCenter + renderHeight / 2) + yOffset + centerOffset.getY());

        return screenPosition;
    }

    /**
     * @return the minimum of the tile width or height
     */
    private int getMinWidthHeight() {
        return Math.min(getWidth() / renderWidth, getHeight() / renderHeight);
    }

    /**
     * @return the width of a game tile in screen coordinates
     */
    private int getTileWidth() {
        return getMinWidthHeight();
    }

    /**
     * @return the height of a game tile in screen coordinates
     */
    private int getTileHeight() {
        return getMinWidthHeight();
    }

    private RangeTileIterator getTilesToRender() {
        return new RangeTileIterator(game.getGrid(), getMinOnScreenInGrid(), getMaxOnScreenInGrid());
    }

    /**
     * A runnable that repeatedly repaints the game screen at roughly 60 FPS
     */
    private class Repainter implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    // 16 ms ~= 60 FPS
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }
    }

    /**
     * Handles what happens to the game renderer when the game ends
     */
    private class GameOverHandler implements GameOverListener {

        /**
         * Handles a game loss by calling the endScreenRenderer
         */
        @Override
        public void lost() {
            screenManager.endScreenRender(false, game.getScore(), game.getTimeElapsedInMs());
        }

        /**
         * Handles a game win by calling the endScreenRenderer
         */
        @Override
        public void won() {
            screenManager.endScreenRender(true, game.getScore(), game.getTimeElapsedInMs());
        }
    }
}
