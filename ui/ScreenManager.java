package com.group8project.ui;

import com.group8project.Game;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ScreenManager extends JFrame {
    /**
     * Holds the dimensions of the window that displays the game
     */
    public static final int width = 1400;
    public static final int height = 800;
    /**
     * Sets the name of the frame that the game runs in
     */
    public static final String screenTitle = "Escape - Prison Adventures";
    /**
     * Sets the path that contains the background music
     */
    public static final String musicFile = "src/main/resources/assets/music/background_music.wav";
    /**
     * Holds the current state of the background music (On/Off)
     */
    public boolean musicState;
    /**
     * Holds the background image for the HUD
     */
    public BufferedImage UIBackgroundImage;
    /**
     * Holds the background music clip
     */
    private Clip soundByte;
    /**
     * Holds the path that contains the frame icon
     */
    private URL iconURL;
    /**
     * Holds the frame icon to be displayed in the taskbar and window
     */
    private ImageIcon frameIcon;

    /**
     * Creates a new ScreenManager
     */
    public ScreenManager() {
        // Sets the title of the JFrame to display the name of the game
        super(screenTitle);

        // Enables the use of absolute positioning to absolutely position components on the frame
        this.setLocationRelativeTo(null);

        // Sets the behviour of the frame when 'X' button is clicked
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sets the dimensions of the window
        this.setSize(width, height);

        // Sets the path of the window frame icon
        this.iconURL = getClass().getResource("/assets/buttons/frameIcon.png");
        this.frameIcon = new ImageIcon(iconURL);
        this.setIconImage(frameIcon.getImage());

        // Displays the WelcomeScreen as the default screen
        welcomeRender();

        // Sets the current state of the background music to off
        musicState = true;

        // Plays the background music
        playMusic();

        // PackS the contents of the JFrame and makes them visible
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    /**
     * ScreenManager's method to invoke a new WelcomeScreen
     */
    public void welcomeRender() {
        // Preload the HUD image to avoid repeated repainting on the GamePanel screen
        try {
            UIBackgroundImage = ImageIO.read(getClass().getResourceAsStream("/assets/background/background_HUD.png"));
        } catch (IOException e) { }
        paintContent(new WelcomeScreen(this));
    }

    /**
     * ScreenManager's method to invoke a new MenuScreen
     */
    public void mainMenuRender() {
        paintContent(new MenuScreen(this));
    }

    /**
     * ScreenManager's method to invoke a new game through the GamePanel
     */
    public void newGameRender(Game newGame) {
        GamePanel gamePanel = new GamePanel(newGame, this);
        newGame.attachPanelToPlayer(gamePanel);
        paintContent(gamePanel);
    }

    /**
     * ScreenManager's method to invoke a new HowToPlayScreen
     */
    public void howToPlayRender() {
        paintContent(new HowToPlayScreen(this));
    }

    /**
     * ScreenManager's method to invoke a new SettingsScreen
     */
    public void settingsRender() {
        paintContent(new SettingsScreen(this));
    }

    /**
     * ScreenManager's method to invoke a new EndScreen
     */
    public void endScreenRender(boolean isAwin, int finalScore, long timeElapsed) {
        paintContent(new EndScreen(this, isAwin, finalScore, timeElapsed));
    }

    /**
     * Method that imports and plays the background music in a loop
     */
    public void playMusic() {
        File f = new File(ScreenManager.musicFile);
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(f);
            this.soundByte = AudioSystem.getClip();
            this.soundByte.open(audio);

            // Control the volume of the backgroound music
            FloatControl gainControl = (FloatControl) soundByte.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-35.0f);

            // Start the background music
            this.soundByte.start();
            this.soundByte.loop(soundByte.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    /**
     * Method that stops the background music from playing
     */
    public void stopMusic() {
        this.soundByte.stop();
    }

    /**
     * Method that stops the background music from playing
     * @param inputPanel the current JPanel that needs to be painted
     */
    public void paintContent(JPanel inputPanel) {
        // Removes any exising JPanel object being displayed and invalidate it
        getContentPane().removeAll();
        getContentPane().invalidate();

        // Sets the inputPanel as the panel to be displayed
        setContentPane(inputPanel);

        // Revalidates and repaints the new inputPanel
        getContentPane().revalidate();
        getContentPane().repaint();

        // Sets the focus of panel in the JFrame to the inputPanel
        inputPanel.grabFocus();
        inputPanel.requestFocusInWindow();
    }
}
