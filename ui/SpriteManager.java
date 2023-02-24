package com.group8project.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * A singleton that is used to load sprites from the filesystem and
 * retrieve them when needed.
 */
public class SpriteManager {
    private static SpriteManager instance;

    private Map<String, AnimatedSprite> sprites;

    private SpriteManager() {
    }

    /**
     * @return the Singleton instance of SpriteManager
     */
    public static SpriteManager getInstance() {
        if (instance == null) {
            instance = new SpriteManager();
        }
        return instance;
    }

    /**
     * Loads sprites from files into memory for drawing onto the screen
     */
    public void loadSprites() {
        sprites = new HashMap<>();
        try {
            Path path = Paths.get("src", "main", "resources", "sprites");
            // filter for png files
            Stream<Path> pngFiles = Files.walk(path).filter(Files::isRegularFile).filter(x -> x.toString().endsWith(".png"));

            Map<String, SortedMap<Integer, Image>> animMap = new HashMap<>();
            Pattern animPattern = Pattern.compile("(.*)_anim_f(\\d).*");

            // iterate through each image and load it
            for (var filePath : (Iterable<Path>) pngFiles::iterator) {
                try {
                    Image image = ImageIO.read(filePath.toFile());
                    String fileName = filePath.getFileName().toString();
                    fileName = fileName.substring(0, fileName.length() - 4);

                    Matcher animMatcher = animPattern.matcher(fileName);

                    // Add to an animation group if its an animation, otherwise create a new animation
                    // with just the image
                    if (animMatcher.matches() && animMatcher.groupCount() == 2) {
                        String animName = animMatcher.group(1);
                        int animIndex = Integer.parseInt(animMatcher.group(2));
                        SortedMap<Integer, Image> mapToInsert;
                        if (animMap.containsKey(animName)) {
                            mapToInsert = animMap.get(animName);
                        } else {
                            mapToInsert = new TreeMap<>();
                            animMap.put(animName, mapToInsert);
                        }
                        mapToInsert.put(animIndex, image);
                    } else {
                        SortedMap<Integer, Image> newMap = new TreeMap<>();
                        newMap.put(0, image);
                        animMap.put(fileName, newMap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // creates AnimatedSprite's from each image
            for (var animEntry : animMap.entrySet()) {
                String animName = animEntry.getKey();
                SortedMap<Integer, Image> animation = animEntry.getValue();
                sprites.put(animName, new AnimatedSprite(new ArrayList<>(animation.values())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a sprite based on its name
     * @param name the name of the sprite to get
     * @return the sprite with name name
     */
    public AnimatedSprite getSprite(String name) {
        return sprites.get(name);
    }
}
