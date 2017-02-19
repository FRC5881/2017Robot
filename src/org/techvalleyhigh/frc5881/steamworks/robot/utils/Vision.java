package org.techvalleyhigh.frc5881.steamworks.robot.utils;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.ArrayList;

/**
 * Created by CMahoney on 2/17/2017.
 */
public class Vision {

    /**
     * Horizontal Quality of the image
     */

    private static double horizontalQuality = 640;

    /**
     * Vertical Quality of the image
     */
    private static double verticalQuality = 480;

    /**
     * Horizontal Field of View of the camera
     */
    private static double horizontalFieldOfViewDegrees = 50.4;

    /**
     * Uses Network Tables values to find distance to target in pixels, using the function
     * 1587.3 * (area) ^ -0.467 = distance
     * But there's to areas so we input into the function with both and then find the average
     * @param area1 Array of areas of each target in pixels
     * @param area2 Array of areas of each target in pixels
     * @return double
     */
}