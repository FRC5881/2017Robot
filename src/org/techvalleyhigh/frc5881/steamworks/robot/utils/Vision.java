package org.techvalleyhigh.frc5881.steamworks.robot.utils;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by CMahoney on 2/17/2017.
 */
public class Vision {

    NetworkTable table = NetworkTable.getTable("GRIP/myCountours");

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

    public static double findDistanceToPegTarget(double area1, double area2) {
        double distance1 = 1587.3 * Math.pow(area1, -0.467);
        double distance2 = 1587.3 * Math.pow(area2, -0.467);
        return (distance1 + distance2) / 2;
    }

    /**
     * Returns an estimate of the relative angle difference of the camera. Finds field of view per pixel estimate
     * calculates average center of each square, calculates distance of the average center from the center of the image
     * and returns field of view per pixel estimate times this pixel distance to get an estimate angle difference.
     * @param centerX1 array from networks camera holding the centers of each rectangle on the x axis
     * @param centerX2 array from networks camera holding the centers of each rectangle on the x axis
     * @return double
     */
    public static double findAngleToPegTarget(double centerX1, double centerX2) {
        double fieldOfViewPerPixel = horizontalFieldOfViewDegrees / horizontalQuality;

        double averageCenterX = (centerX1 + centerX2) / 2;
        double centerOfView = horizontalQuality / 2;

        double distanceFromCenterOfView = averageCenterX - centerOfView;

        double rangeOfScreenPixels = distanceFromCenterOfView - centerOfView;

        return fieldOfViewPerPixel * distanceFromCenterOfView;
    }

    public static double sanityTestGears(double[] lengths, double[] widths) {
        return 0;
    }

    /*
    public double findAreasOfContours() {
        double[] areas = {-1};
        areas = table.getNumberArray("areas", areas);

        if (areas.length == 1 && areas[0] == -1) {
            // oh crap
        }


    }
    */
// Check each contour to see if it's valid
    public ArrayList<Integer> validContourIndexes() {
        ArrayList<Integer> ints = new ArrayList<>();

        double[] contourHeights = {-1};
        double[] contourWidths = {-1};

        contourHeights = table.getNumberArray("heights", contourHeights);
        // widths
        contourWidths = table.getNumberArray("widths", contourWidths);
        if (contourHeights.length == 1 && contourHeights[0] == -1) {
            // oh crap
            return ints;
        }

        for (int i = 0; i < contourHeights.length; i++) {
            double width = contourWidths[i];
            double height = contourHeights[i];

            // Do math - does ratio of w/h match expected w/in margin of error
            if (width/height >= 2.3 && width/height <= 2.7) { // Fix this conditional
                ints.add(i);
            }
        }

        return ints;
    }
    
}