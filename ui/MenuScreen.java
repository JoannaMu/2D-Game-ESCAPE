package com.group8project.ui;

import com.group8project.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuScreen extends JPanel implements KeyListener {
    /**
     * The MenuScreen's parent. Used to switch between screens in the main menu
     */
    private ScreenManager screenManager;
    /**
     * The JLabel used to display the background image holding the game instructions
     */
    private JLabel backgroundLabel;
    /**
     * The JLabel used to display the new game button to start the game
     */
    private JLabel newGameLabel;
    /**
     * The JLabel used to display the how to play button
     */
    private JLabel howToLabel;
    /**
     * The JLabel used to display the settings button for sound settings
     */
    private JLabel settingsLabel;
    /**
     * The JLabel used to display the back button to go back to the welcome screen
     */
    private JLabel backBtnLabel;

    /**
     * Creates a new MenuScreen
     * @param screenManager the parent ScreenManager of this MenuScreen object
     */
    MenuScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(ScreenManager.width, ScreenManager.height));

        // makes the window focusable so that key bindings work
        setFocusable(true);

        // Add a key listener to listen to the backspace key binding to go back
        addKeyListener(this);

        // Initialize the components on the MenuScreen
        initComponents();
    }

    /**
     * Initialize the components to be displayed on the MenuScreen
     */
    private void initComponents() {
        // Initialize the JLabels that hold the components of the HowToPlayScreen
        backgroundLabel = new JLabel();
        newGameLabel = new JLabel();
        howToLabel = new JLabel();
        settingsLabel = new JLabel();
        backBtnLabel = new JLabel();

        // Set the Icon/image for the background
        backgroundLabel.setIcon(new ImageIcon(getClass().getResource("/assets/background/background_menu.jpg")));
        backgroundLabel.setBounds(0, 0, ScreenManager.width, ScreenManager.height);

        // Set the Icon/image for the new game button
        newGameLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_newgame_bef.png")));
        // Add mouse listener to make the button react to mouse actions
        newGameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(MouseEvent evt) {
                newGameLabelMouseExited(evt);
            }

            public void mouseEntered(MouseEvent evt) {
                newGameLabelMouseEntered(evt);
            }

            public void mouseClicked(MouseEvent evt) {
                newGameLabelMouseClicked(evt);
            }
        });
        newGameLabel.setBounds(840, 390, 286, 74);

        // Set the Icon/image for the how to play button
        howToLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_howto_bef2.png")));
        // Add mouse listener to make the button react to mouse actions
        howToLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(MouseEvent evt) {
                howToLabelMouseExited(evt);
            }

            public void mouseEntered(MouseEvent evt) {
                howToLabelMouseEntered(evt);
            }

            public void mouseClicked(MouseEvent evt) {
                howToLabelMouseClicked(evt);
            }
        });
        howToLabel.setBounds(840, 480, 286, 74);

        // Set the Icon/image for the settings button
        settingsLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_sett_bef.png")));
        // Add mouse listener to make the button react to mouse actions
        settingsLabel.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent evt) {
                settingsLabelMouseExited(evt);
            }

            public void mouseEntered(MouseEvent evt) {
                settingsLabelMouseEntered(evt);
            }

            public void mouseClicked(MouseEvent evt) {
                settingsLabelMouseClicked(evt);
            }
        });
        settingsLabel.setBounds(840, 565, 286, 74);

        // Set the Icon/image for the back button
        backBtnLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_back_bef.png")));
        // Add mouse listener to make the button react to mouse actions
        backBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(MouseEvent evt) {
                backBtnLabelMouseExited(evt);
            }

            public void mouseEntered(MouseEvent evt) {
                backBtnLabelMouseEntered(evt);
            }

            public void mouseClicked(MouseEvent evt) {
                backBtnLabelMouseClicked(evt);
            }
        });
        backBtnLabel.setBounds(65, 40, 75, 75);

        // Attach each JLabel component to the JPanel object
        this.add(settingsLabel);
        this.add(howToLabel);
        this.add(newGameLabel);
        this.add(backBtnLabel);
        this.add(backgroundLabel);
    }

    /**
     * Handles the behaviour of the button when mouse is hovered over them
     * @param evt corresponds a mouse event
     */
    private void newGameLabelMouseEntered(java.awt.event.MouseEvent evt) {
        newGameLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/after/button_newgame_af.png")));
    }

    private void howToLabelMouseEntered(java.awt.event.MouseEvent evt) {
        howToLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/after/button_howto_af2.png")));
    }

    private void settingsLabelMouseEntered(java.awt.event.MouseEvent evt) {
        settingsLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/after/button_sett_af.png")));
    }

    private void backBtnLabelMouseEntered(java.awt.event.MouseEvent evt) {
        backBtnLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/after/button_back_af.png")));
    }

    /**
     * Handles the behaviour of the button when mouse exits the hover state
     * @param evt corresponds a mouse event
     */
    private void newGameLabelMouseExited(java.awt.event.MouseEvent evt) {
        newGameLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_newgame_bef.png")));
    }

    private void howToLabelMouseExited(java.awt.event.MouseEvent evt) {
        howToLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_howto_bef2.png")));
    }

    private void settingsLabelMouseExited(java.awt.event.MouseEvent evt) {
        settingsLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_sett_bef.png")));
    }

    private void backBtnLabelMouseExited(java.awt.event.MouseEvent evt) {
        backBtnLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_back_bef.png")));
    }

    /**
     * Handles the behaviour of the buttons when clicked
     * @param evt corresponds a mouse event
     */
    private void newGameLabelMouseClicked(MouseEvent evt) {
        this.screenManager.newGameRender(new Game());
    }

    private void howToLabelMouseClicked(MouseEvent evt) {
        this.screenManager.howToPlayRender();
    }

    private void settingsLabelMouseClicked(MouseEvent evt) {
        this.screenManager.settingsRender();
    }

    private void backBtnLabelMouseClicked(MouseEvent evt) {
        this.screenManager.welcomeRender();
    }

    /**
     * Handles the behaviour of the backspace key is pressed
     * @param keyEvent corresponds a key event
     */
    public void keyPressed(KeyEvent evt) {
        int inputKey = evt.getKeyCode();
        if (inputKey == KeyEvent.VK_BACK_SPACE) {
            this.screenManager.welcomeRender();
        }
    }

    public void keyTyped(KeyEvent keyEvent) { }

    public void keyReleased(KeyEvent keyEvent) { }

}