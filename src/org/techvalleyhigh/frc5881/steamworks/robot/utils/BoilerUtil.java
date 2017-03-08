package org.techvalleyhigh.frc5881.steamworks.robot.utils;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by CMahoney on 3/3/2017.
 */
public class BoilerUtil {
    /**
     * this class contains the calculations that deal with vision for the boiler in autonomous or teleop, it validates
     * the targets and runs calculations to find the angle and distance needed to go to the target.
     * AND returns RPM required to score using physics
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

    /*
     * made arrays as arrayLists to be able to use the .remove method later on
     */
    private ArrayList<Double> areas = new ArrayList<>();
    private ArrayList<Double> centerX = new ArrayList<>();
    private ArrayList<Double> centerY = new ArrayList<>();
    private ArrayList<Double> widths = new ArrayList<>();
    private ArrayList<Double> heights = new ArrayList<>();

    public BoilerUtil(NetworkTable contours) {
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

        validContourIndexes();
    }

    /**
     * gets the distance to the gear target based on the area
     * the formula takes the sum of the areas and then puts them into this function
     * distance = 4398.71e^(0.001(area))
     * @param area
     * @return
     */
    private double getDistanceBasedOnArea(double area) {
        return 4398.71 * Math.pow(Math.E, 0.001 * area);
    }

    /**
     * takes values from arrayList areas, checks to see if there are 2
     * if there are 2 values, gets average and returns average
     * takes average and runs through getDistanceBasedOnArea function, returns value
     * @return -1 which indicates an error
     */
    public double findDistanceToBoiler() {
        //If there is not two areas, something terrible has happened
        if(areas.size() == 2) {
            return getDistanceBasedOnArea(areas.get(0) + areas.get(1));
        } else {
            return -1;
        }
    }

    /**
     * takes centerX and finds the angle needed to turn by subtracting
     * the center between the contours from the center of view
     * @param centerX
     * @return
     */
    public double getAngleBasedOnCenterX(double centerX) {

        double fieldOfViewPerPixel = horizontalFieldOfViewDegrees / horizontalQuality;
        double centerOfView = horizontalQuality / 2;

        double distanceFromCenterOfView = centerX - centerOfView;

        return fieldOfViewPerPixel * distanceFromCenterOfView;
    }

    /**
     * verifies that there are two contours, if there are 2 then it will call the getAngleBasedOnCenterX function
     * using centerX as a parameter.
     * @return double containing the angle to the peg, or if only one contour was found, angle to it's center.
     *         Returns Double.MIN_VALUE if an error occurs. (Eg. no contours, or more than 2 contours)
     */
    public double findAngleToBoiler() {
        //If there is not two centers, something terrible has happened
        if (centerX.size() == 2) {
            return getAngleBasedOnCenterX(centerX.get(0) + centerX.get(1) / 2);
        } else {
            return Double.MIN_VALUE;
        }
    }

    /**
     * Returns true or false on weather or not we are centered with the gear
     * @return
     */
    private boolean isCenteredOnBoiler() {
        return (findAngleToBoiler() < 10);
    }

    /**
     * makes sure the contours are good or not, if they aren't then
     * it will remove them from the arrayList
     */
    public void validContourIndexes() {
        for(int i = widths.size(); i > 0; i--) {
            double width = widths.get(i);
            double height = heights.get(i);
            double ratio = width / height;

            // Check Ratios there's two different possible ranges (one piece of tape is wider)
            // With a little bit of wiggle room these are the tested values
            if(!((ratio > 1.75 && ratio < 2.5) || (ratio > 2.75 && ratio < 4.25))) {
                // Remove from each array
                widths.remove(i);
                heights.remove(i);
                centerX.remove(i);
                centerY.remove(i);
                areas.remove(i);
            }
        }
    }

    /**
     * retrieves values from the areas arrayList
     * @return values of areas arrayList
     */
    public ArrayList<Double> getAreas() {
        return areas;
    }

    /**
     * retrieves values from centerX arrayList
     * @return values of centerX arrayList
     */
    public ArrayList<Double> getCenterX() {
        return centerX;
    }

    /**
     * retrieves values from centerY arrayList
     * @return values from centerY arrayList
     */
    public ArrayList<Double> getCenterY() {
        return centerY;
    }

    /**
     * retrieves values from widths arrayList
     * @return values from widths arrayList
     */
    public ArrayList<Double> getWidths() {
        return widths;
    }

    /**
     * retrieves values from heights arrayList
     * @return values from heights arrayList
     */
    public ArrayList<Double> getHeights() {
        return heights;
    }
}
