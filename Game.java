package com.group8project;

import com.group8project.characters.Character;
import com.group8project.characters.*;
import com.group8project.common.Position;
import com.group8project.grid.Floor;
import com.group8project.grid.MapGrid;
import com.group8project.grid.MapGridFactory;
import com.group8project.grid.Tile;
import com.group8project.objects.Money;

import javax.swing.*;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The high level class that contains all of the game state
 */
public class Game {
    private static final long DEFAULT_TICK_TIME = 500;
    /**
     * Number of subticks in a tick, used to update the time remaining until the next tick.
     * Set to 10 for over 60 FPS
     */
    private static final int SUBTICK_RESOLUTION = 100;
    /**
     * The score given for reaching the end tile with all documents
     */
    private static final int REACHED_END_SCORE = 1000;
    /**
     * A lock that is locked when the game state is being updated or when the game is being rendered.
     * Prevents a Game from being rendered while its state is inconsistent in the middle of a tick update
     */
    public Lock gameLock;
    /**
     * A timer that activates every subtick
     */
    public Timer tickTimer;
    /**
     * The system time in nanoseconds at the start of the last tick. Used for animations
     */
    public long startOfTickTime = 0;
    /**
     * The time until the next tick in milliseconds
     */
    private long timeUntilTick;
    /**
     * The current game tick as an increasing number since the first game tick of 0
     */
    private long tick;
    /**
     * Whether the game has ended
     */
    private boolean gameOver;
    /**
     * The number of documents required to win the game
     */
    private int requiredDocuments;
    /**
     * The number of keys on the map
     */
    private int requiredKeys;
    /**
     * The player of the game
     */
    private Player player;
    /**
     * The controller of the player
     */
    private CharacterController playerController;
    /**
     * A list of all characters
     */
    private List<Character> characters;
    /**
     * The controllers of all characters
     */
    private List<CharacterController> controllers;
    /**
     * A list of all money spawned on the map. Kept for despawning purposes
     */
    private List<Money> moneyList;
    /**
     * The RNG object used to randomly spawn money
     */
    private Random moneyRNG;
    /**
     * A list of all listeners that need to be called when the game ends
     */
    private List<GameOverListener> gameOverListeners;
    /**
     * The primary MapGrid of the game
     */
    private MapGrid grid;

    public Game() {
        this(true, new MapGridFactory());
    }

    public Game(boolean tickTimerEnabled, MapGridFactory mapGridFactory) {
        gameLock = new ReentrantLock();
        characters = new ArrayList<>();
        controllers = new ArrayList<>();
        moneyList = new ArrayList<>();
        moneyRNG = new Random();
        gameOverListeners = new ArrayList<>();
        gameOver = false;

        grid = mapGridFactory.makeMapGrid();

        requiredDocuments = grid.getDocumentCount();
        requiredKeys = grid.getKeyCount();

        // creates the player and guards based off of their spawn locations
        createPlayer();
        createGuards();

        if (tickTimerEnabled) {
            // starts the tick timer
            tickTimer = new Timer();
            tickTimer.schedule(new TickTask(DEFAULT_TICK_TIME), 0, SUBTICK_RESOLUTION);
        }
    }

    /**
     * Processes one tick of the game logic
     */
    public void tick() {
        try {
            gameLock.lock();
            if (!gameOver) {
                startOfTickTime = System.nanoTime();
                // loses the game if any loss conditions are met
                if (player.isDead() || player.getScore() < 0) {
                    playerLost();
                    return;
                }

                // wins the game if any loss conditions are met
                if (player.getTile() == grid.getEndCell() && player.getDocumentCount() >= requiredDocuments) {
                    player.addScore(REACHED_END_SCORE);
                    playerWon();
                    return;
                }

                // performs actions for all characters
                for (var controller : controllers) {
                    controller.performAction((int) tick);
                }

                // ticks down money despawn timer and despawns money
                for (var money : moneyList) {
                    money.tick();
                }
                // remove from money list
                moneyList.removeIf(money -> money.getParentTile() == null);

                maybeSpawnMoney();
                // score decreases every tick
                player.addScore(-5);

                tick++;
            }
        } finally {
            gameLock.unlock();
        }
    }

    public void setMoneyRNG(Random random) {
        moneyRNG = random;
    }

    /**
     * Spawns money on the MapGrid probabilistically
     */
    private void maybeSpawnMoney() {
        // 1 in 120 chance of money spawning
        if (moneyRNG.nextInt(60) == 1) {
            spawnMoneyAtDistance(moneyRNG.nextInt(3) + 2);
        }
    }

    public void spawnMoneyAtDistance(int distFromPlayer) {
        // money spawns from 2 to 4 tiles away from the player
        Position playerPos = getPlayer().getPosition();

        // get all of the possible spawns that are distFromPlayer away from the player
        List<Floor> possibleSpawns = getFloorsAtDistance(playerPos, distFromPlayer);

        // don't spawn money if there is nowhere to spawn it
        if (possibleSpawns.size() == 0) {
            return;
        }

        Floor floorToSpawnIn = possibleSpawns.get(moneyRNG.nextInt(possibleSpawns.size()));
        spawnMoneyOnFloor(floorToSpawnIn);
    }

    public void spawnMoneyOnFloor(Floor floorToSpawnIn) {
        Money money = new Money(floorToSpawnIn, Money.DEFAULT_SCORE, Money.DEFAULT_TICKS);
        floorToSpawnIn.setObject(money);
        moneyList.add(money);
    }

    public List<Floor> getFloorsAtDistance(Position pos, int distance) {
        List<Floor> floors = new ArrayList<>();
        int xStart = pos.getX() - distance;
        int xEnd = pos.getX() + distance;
        int yStart = pos.getY() - distance;
        int yEnd = pos.getY() + distance;
        for (int x = xStart; x <= xEnd; x++) {
            for (int y = yStart; y <= yEnd; y++) {
                if (Math.abs(x - pos.getX()) + Math.abs(y - pos.getY()) == distance) {
                    // (x, y) is distFromPlayer away from the player
                    if (x < getGrid().getWidth() && x >= 0 && y < getGrid().getHeight() && y >= 0) {
                        // (x, y) is within the mapgrid
                        Tile tile = getGrid().getTile(x, y);
                        if (tile instanceof Floor) {
                            Floor floor = (Floor) tile;
                            if (floor.getObject().isEmpty()) {
                                // tile doesn't have any other objects
                                floors.add(floor);
                            }
                        }
                    }
                }
            }
        }

        return floors;
    }

    public void addGameOverListener(GameOverListener listener) {
        gameOverListeners.add(listener);
    }

    private void playerLost() {
        gameOver = true;
        for (var listener : gameOverListeners) {
            listener.lost();
        }
    }

    private void playerWon() {
        gameOver = true;
        for (var listener : gameOverListeners) {
            listener.won();
        }
    }

    /**
     * @return the amount of time in milliseconds until the next tick
     */
    public long getMsUntilTick() {
        return timeUntilTick;
    }

    /**
     * @return the total amount of time each tick lasts for (in milliseconds)
     */
    public long getTickTime() {
        return DEFAULT_TICK_TIME;
    }

    /**
     * @return the amount of ticks that have passed by since the Game has started
     */
    public long getTick() {
        return tick;
    }

    /**
     * Binds the player input to a JPanel
     *
     * @param panel panel to bind input to
     */
    public void attachPanelToPlayer(JPanel panel) {
        if (playerController instanceof UserInputController) {
            panel.addKeyListener((UserInputController) playerController);
        }
    }

    /**
     * @return the player object of the player who is playing the game
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the Game's primary MapGrid
     */
    public MapGrid getGrid() {
        return grid;
    }

    /**
     * @return the player's score
     */
    public int getScore() {
        return player.getScore();
    }

    /**
     * @return the number of documents the player has
     */
    public int getDocumentCount() {
        return player.getDocumentCount();
    }

    /**
     * @return the number of keys the player has
     */
    public int getKeyCount() {
        return player.getKeyCount();
    }

    /**
     * @return the number of documents required to win the game
     */
    public int getRequiredDocumentCount() {
        return requiredDocuments;
    }

    /**
     * @return the number of keys on the mapgrid
     */
    public int getRequiredKeyCount() {
        return requiredKeys;
    }

    /**
     * @return the number of keys on the mapgrid
     */
    public boolean isGameOver() {
        return gameOver;
    }


    /**
     * @return the total time elapsed since the game start in milliseconds
     */
    public long getTimeElapsedInMs() {
        return tick * DEFAULT_TICK_TIME;
    }

    /**
     * @return a string representation of the time since the game started in minutes and seconds
     */
    public String getTimeElapsedStr() {
        return millisecondsToStr(getTimeElapsedInMs());
    }

    /**
     * @return a string representation of the time given in minutes and seconds
     */
    public String millisecondsToStr(long timeInMs) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeInMs), TimeUnit.MILLISECONDS.toSeconds(timeInMs) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMs)));
    }

    /**
     * Creates guards based on the mapgrid's spawn list
     */
    private void createGuards() {
        for (Tile guardTile : grid.getGuardSpawns()) {
            Guard guard = new Guard(guardTile);
            guardTile.enter(guard);
            RandomController controller = new TargetController(guard, player, TargetController.DEFAULT_PROBABILITY);

            characters.add(guard);
            controllers.add(controller);
        }
    }

    /**
     * Creates the player based on the starting location
     */
    private void createPlayer() {
        player = new Player(grid.getStartCell());
        player.addScore(500);
        grid.getStartCell().enter(player);

        playerController = new UserInputController(player);

        characters.add(player);
        controllers.add(playerController);
    }

    /**
     * @return the list of characters in the game
     */
    public List<Character> getCharacters() {
        return characters;
    }

    public List<Money> getMoneyList() {
        return moneyList;
    }

    private class TickTask extends TimerTask {
        long subTicks = 0;
        long subTicksRequired;

        TickTask(long time) {
            subTicksRequired = time / SUBTICK_RESOLUTION;
        }

        @Override
        public void run() {
            timeUntilTick = (subTicks % subTicksRequired) * SUBTICK_RESOLUTION;
            if (subTicks % subTicksRequired == 0) {
                tick();
            }
            subTicks++;
        }
    }
}
