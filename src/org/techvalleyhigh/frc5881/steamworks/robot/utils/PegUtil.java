package org.techvalleyhigh.frc5881.steamworks.robot.utils;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by ksharpe on 2/19/2017.
 */
public class PegUtil {
    //NetworkTable table = NetworkTable.getTable("GRIP/myCountours");

    /*
     * made arrays as arrayLists to be able to use the .remove method later on
     */

    private ArrayList<Double> areas = new ArrayList<>();
    private ArrayList<Double> centerX = new ArrayList<>();
    private ArrayList<Double> centerY = new ArrayList<>();
    private ArrayList<Double> widths = new ArrayList<>();
    private ArrayList<Double> heights = new ArrayList<>();

    public PegUtil(NetworkTable contours) {
        areas.addAll(Arrays
                .stream(contours.getNumberArray("areas", new double[] {}))
                .boxed()
                .collect(Collectors.toList()));

        centerX.addAll(Arrays
                .stream(contours.getNumberArray("centerX", new double[] {}))
                .boxed()
                .collect(Collectors.toList()));

        centerY.addAll(Arrays
                .stream(contours.getNumberArray("centerY", new double[] {}))
                .boxed()
                .collect(Collectors.toList()));

        widths.addAll(Arrays
                .stream(contours.getNumberArray("width", new double[] {}))
                .boxed()
                .collect(Collectors.toList()));


        heights.addAll(Arrays
                .stream(contours.getNumberArray("height", new double[] {}))
                .boxed()
                .collect(Collectors.toList()));

        // do for all....

        validContourIndexes();
    }

    private static double horizontalQuality = 640;

    /**
     * Vertical Quality of the image
     */
    private static double verticalQuality = 480;

    /**
     * Horizontal Field of View of the camera
     */
    private static double horizontalFieldOfViewDegrees = 50.4;

    /*
     * Uses Network Tables values to find distance to target in pixels, using the function
     * 1587.3 * (area) ^ -0.467 = distance
     * But there's to areas so we input into the function with both and then find the average
     *
     */

    /**
     * takes values from arrayList areas, checks to see if there are 2
     * if there are 2 values, gets average and returns average
     * takes average and runs through getDistanceBasedOnArea function, returns value
     * @return (TODO) -1 indicates an error
     */
    public double findDistanceToPeg() {
        // Is there 0, 1, 2, or more valid contours...
        // 0 == bail, >2 == bail
        // 1 = return the one
        // 2 = average them

        if (areas.size() == 1) {
            return getDistanceBasedOnArea(areas.get(0));
        } else if(areas.size() == 2) {
            return getDistanceBasedOnArea((areas.get(0) + areas.get(1)) / 2);
        } else {
            return -1;
        }
    }

    /**
     * multiplies 1587.3 by area to the -0.467th power
     * @param area
     * @return
     */
    private double getDistanceBasedOnArea(double area) {
        return 1587.3 * Math.pow(area, -0.467);
    }

    /**
     * Returns an estimate of the relative angle difference of the camera. Finds field of view per pixel estimate
     * calculates average center of each square, calculates distance of the average center from the center of the image
     * and returns field of view per pixel estimate times this pixel distance to get an estimate angle difference.
     * @return getAngleBasedOnCenterX of centerX
     */
    public double findAngleToPeg() {
        // Rewrite to use centerX

        if (centerX.size() == 1) {
            return getAngleBasedOnCenterX(centerX.get(0));
        } else if (centerX.size() == 2) {
            return getAngleBasedOnCenterX(centerX.get(0) + centerX.get(1) / 2);
        } else {
            return -1;
        }
    }

    /*
     * takes centerX and finds the angle needed to turn by subtracting
     * the center between the contours from the center of view
     * @param centerX
     * @return
     */
    public double getAngleBasedOnCenterX(double centerX) {

        double fieldOfViewPerPixel = horizontalFieldOfViewDegrees / horizontalQuality;
        double centerOfView = horizontalQuality / 2;

        double distanceFromCenterOfView = centerX - centerOfView;

        double rangeOfScreenPixels = distanceFromCenterOfView - centerOfView;

        return fieldOfViewPerPixel * distanceFromCenterOfView;
    }

   /*
    * makes sure the contours are good or not, if they aren't then
    * it will remove them from the arrayList
    */
    public ArrayList<Integer> validContourIndexes() {
        // Change this to remove invalid countours from all the double[] arrays

        ArrayList<Integer> ints = new ArrayList<>();

        if (heights.size() == 0) {
            // oh crap
            return ints;
        }

        for (int i = 0; i < heights.size(); i++) {
            double width = widths.get(i);
            double height = heights.get(i);

            // Do math - does ratio of w/h match expected w/in margin of error
            if (width / height >= 2.3 && width / height <= 2.7) { // Fix this conditional
                ints.add(i);
            }
            else{
                // Remove from each array
                widths.remove(i);
                heights.remove(i);
                centerX.remove(i);
                centerY.remove(i);
                areas.remove(i);
            }
        }
        return ints;
    }

    public ArrayList<Double> getAreas() {
        return areas;
    }

    public ArrayList<Double> getCenterX() {
        return centerX;
    }

    public ArrayList<Double> getCenterY() {
        return centerY;
    }

    public ArrayList<Double> getWidths() {
        return widths;
    }

    public ArrayList<Double> getHeights() {
        return heights;
    }
}