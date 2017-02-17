package org.techvalleyhigh.frc5881.steamworks.robot.utils;

import java.lang.reflect.Array;

/**
 * Created by CMahoney on 2/17/2017.
 */
public class Vision {
    /**
     * Horizontal Quality of the image
     */
    private static double horizontalQuality = 480;

    /**
     * Vertical Quality of the image
     */
    private static double verticalQuality = 640;

    /**
     * Horizontal Field of View of the camera
     */
    private static double horizontalFieldOfView = 50.4;

    /**
     * Uses Network Tables values to find distance to target in pixels, using the function
     * 1587.3 * (area) ^ -0.467 = distance
     * But there's to areas so we input into the function with both and then find the average
     * @param areas Array of areas of each target in pixels
     * @return double
     */
    public static double findDistanceToTarget(double[] areas) {
        double distance1 = 1587.3 * Math.pow(areas[0], -0.467);
        double distance2 = 1587.3 * Math.pow(areas[1], -0.467);
        return (distance1 + distance2) / 2;
    }

    /**
     * Returns an estimate of the relative angle difference of the camera. Finds field of view per pixel estimate
     * calculates average center of each square, calculates distance of the average center from the center of the image
     * and returns field of view per pixel estimate times this pixel distance to get an estimate angle difference.
     * @param centerXs array from networks camera holding the centers of each rectangle on the x axis
     * @return double
     */
    public static double findAngleToTarget(double[] centerXs) {
        double fieldOfViewPerPixel = horizontalFieldOfView / horizontalQuality;

        double averageCenter = (centerXs[0] + centerXs[1]) / 2;
        double center = horizontalQuality / 2;

        double distanceFromCenter = averageCenter - center;
 
        return fieldOfViewPerPixel * distanceFromCenter;
    }

    public static double sanityTestGears(double[] lengths, double[] widths) {
        return 0;
    }
}