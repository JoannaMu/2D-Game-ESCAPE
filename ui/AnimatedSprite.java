package com.group8project.ui;

import java.awt.*;
import java.util.List;

/**
 * Contains an animated sprite which is composed of a list of images
 * representing the frames of the animation
 */
public class AnimatedSprite {
    private List<Image> animList;

    AnimatedSprite(List<Image> animList) {
        this.animList = animList;
    }

    /**
     * Gets the image corresponding to the frame index
     *
     * @param frameIndex The frame index for which to fetch the image
     * @return the image corresponding to the frame index
     */
    public Image getAnimFrame(int frameIndex) {
        return animList.get(frameIndex);
    }

    /**
     * Gets the length of the animation in frames
     *
     * @return the length of the animation in frames
     */
    public int getAnimLength() {
        return animList.size();
    }
}
