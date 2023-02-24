package com.group8project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsScreen extends JPanel implements KeyListener {
    /**
     * The SettingsScreen's parent. Used to switch back to the main menu
     */
    private ScreenManager screenManager;
    /**
     * The JLabel used to display the background image holding the game instructions
     */
    private JLabel backgroundLabel;
    /**
     * The JLabel used to display the back button to go back to the welcome screen
     */
    private JLabel backBtnLabel;
    /**
     * The JLabel used to display the status of the background music (On/Off)
     */
    private JLabel toggleText;
    /**
     * The JLabel used to display the toggle button for the background music
     */
    private JToggleButton musicToggle;

    private ImageIcon soundOnicon;
    private ImageIcon soundOfficon;

    /**
     * Creates a new SettingsScreen
     * @param screenManager the parent ScreenManager of this SettingsScreen object
     */
    SettingsScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(ScreenManager.width, ScreenManager.height));

        // makes the window focusable so that key bindings work
        setFocusable(true);

        // Add a key listener to listen to the backspace key binding to go back
        addKeyListener(this);

        // Initialize the components on the SettingsScreen
        initComponents();
    }

    /**
     * Initialize the components to be displayed on the SettingsScreen
     */
    private void initComponents() {
        // Initialize and prefetch the background music buttons
        soundOnicon = new ImageIcon(getClass().getResource("/assets/buttons/SoundON.png"));
        soundOfficon = new ImageIcon(getClass().getResource("/assets/buttons/SoundOFF.png"));

        // Initialize the JLabels that hold the components of the SettingsScreen
        backgroundLabel = new JLabel();
        backBtnLabel = new JLabel();
        toggleText = new JLabel();
        musicToggle = new JToggleButton();

        // Set the Icon/image for the background
        backgroundLabel.setIcon(new ImageIcon(getClass().getResource("/assets/background/background_settings.jpg")));
        backgroundLabel.setBounds(0, 0, ScreenManager.width, ScreenManager.height);

        // Set the Icon/image for the back button
        backBtnLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_back_bef.png")));
        // Add mouse listener to make the button react to mouse actions
        backBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtnLabelMouseExited(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtnLabelMouseEntered(evt);
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backBtnLabelMouseClicked(evt);
            }
        });
        backBtnLabel.setBounds(65, 40, 75, 75);

        // Display the current status of the background music (On/Off)
        showSoundStatus();

        // Set the Icon/image for the music toggle
        if(screenManager.musicState) {
            musicToggle.setIcon(soundOnicon);
        }
        else {
            musicToggle.setIcon(soundOfficon);
        }
        musicToggle.setBorderPainted(false);
        musicToggle.setContentAreaFilled(false);
        // Add mouse listener to make the button react to mouse actions
        musicToggle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                musicToggleActionPerformed(evt);
            }
        });
        musicToggle.setBounds(875, 570, 100, 100);

        // Set the positioning and look of the toggle text
        toggleText.setFont(new java.awt.Font("Trebuchet MS", 0, 20));
        toggleText.setForeground(new java.awt.Color(153, 153, 153));
        toggleText.setBounds(1000, 605, 350, 31);

        // Attach each JLabel component to the JPanel object
        this.add(toggleText);
        this.add(musicToggle);
        this.add(backBtnLabel);
        this.add(backgroundLabel);
    }

    /**
     * Handles the behaviour of the button when mouse is hovered over them
     * @param evt corresponds a mouse event
     */
    private void backBtnLabelMouseEntered(java.awt.event.MouseEvent evt) {
        backBtnLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/after/button_back_af.png")));
    }

    /**
     * Handles the behaviour of the button when mouse exits the hover state
     * @param evt corresponds a mouse event
     */
    private void backBtnLabelMouseExited(java.awt.event.MouseEvent evt) {
        backBtnLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_back_bef.png")));
    }

    /**
     * Handles the behaviour of the buttons when clicked
     * @param evt corresponds a mouse event
     */
    private void backBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {
        this.screenManager.mainMenuRender();
    }

    /**
     * Handles the behaviour of the toggle when clicked
     * @param evt corresponds a mouse event
     */
    private void musicToggleActionPerformed(MouseEvent evt) {
        AbstractButton abstractButton = (AbstractButton) evt.getSource();
        boolean selected = abstractButton.getModel().isSelected();
        this.screenManager.musicState = !this.screenManager.musicState;

        // Toggle the state of the background music when the toggle is clicked
        if (this.screenManager.musicState) {
            this.screenManager.playMusic();
            musicToggle.setIcon(soundOnicon);
        } else {
            this.screenManager.stopMusic();
            musicToggle.setIcon(soundOfficon);
        }
        showSoundStatus();

        // Make the JPanel get focus from the JButtonToggle
        this.requestFocus();
    }

    /**
     * Display the current status of the background music (On/Off)
     */
    private void showSoundStatus() {
        if (this.screenManager.musicState) {
            toggleText.setText("SOUND: ON");
        } else {
            toggleText.setText("SOUND: OFF");
        }
    }

    /**
     * Handles the behaviour of the backspace key is pressed
     * @param keyEvent corresponds a key event
     */
    public void keyPressed(KeyEvent keyEvent) {
        int inputKey = keyEvent.getKeyCode();
        if (inputKey == KeyEvent.VK_BACK_SPACE) {
            this.screenManager.mainMenuRender();
        }
    }
    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }
}