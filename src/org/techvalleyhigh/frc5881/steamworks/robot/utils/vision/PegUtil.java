package org.techvalleyhigh.frc5881.steamworks.robot.utils.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.Vision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by ksharpe on 2/19/2017.
 *
 * This class calculates and store and deal with vision data for the Gear Pegs
 * in autonomous or teleop, it validates the targets and runs calculations
 * to find the angle and distance needed to go to the target and stores them
 * for public access
 *
 */
public class PegUtil {
    /*
     * Make arrays as arrayLists to be able to use the .remove method later on
     */
    private ArrayList<Double> areas = new ArrayList<>();
    private ArrayList<Double> centerX = new ArrayList<>();
    private ArrayList<Double> centerY = new ArrayList<>();
    private ArrayList<Double> widths = new ArrayList<>();
    private ArrayList<Double> heights = new ArrayList<>();

    /**
     * Holds vision camera data
     */
    private VisionCamera camera;

    //Distance and angle data
    public double distanceToPeg;
    public double angleToPeg;

    /**
     * Create arrays that work with NetworkTables
     * Calculate and Store angle and distance data
     * @param contours
     */
    public PegUtil(NetworkTable contours, VisionCamera camera) {
        //Set Network Table contours
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

        //Holds the camera
        this.camera = camera;

        //Store distance and angle values
        this.distanceToPeg = this.findDistanceToPeg();
        this.angleToPeg = this.findAngleToPeg();
    }

    /**
     * gets the distance to the gear target based on the area, the formula takes area to the -0.467th power and multiplies
     * it by 1587.3
     * Forumla was found with some statistical analyzes in the from of a power regression
     * @param area
     * @return
     */
    private double getDistanceBasedOnArea(double area) {
        return 1587.3 * Math.pow(area, -0.467);
    }

    /**
     * takes values from arrayList areas, checks to see if there are 2
     * if there are 2 values, gets average and returns average
     * takes average and runs through getDistanceBasedOnArea function, returns value
     * @return -1 which indicates an error
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
     * takes centerX and finds the angle needed to turn by subtracting
     * the center between the contours from the center of view
     * NEGATIVE IS COUNTERCLOCKWISE
     * @param centerX
     * @return
     */
    public double getAngleBasedOnCenterX(double centerX) {
        double centerOfView = camera.horizontalQuality / 2;
        double distanceFromCenterOfView = centerX - centerOfView;

        return camera.horizontalDegreesPerPixel * distanceFromCenterOfView;
    }

    /**
     * verifies that there are two contours, if there are 2 then it will call the getAngleBasedOnCenterX function
     * using centerX as a parameter.
     * @return double containing the angle to the peg, or if only one contour was found, angle to it's center.
     *         Returns Double.MIN_VALUE if an error occurs. (Eg. no contours, or more than 2 contours)
     */
    public double findAngleToPeg() {
        if (centerX.size() == 1) {
            return getAngleBasedOnCenterX(centerX.get(0));
        } else if (centerX.size() == 2) {
            return getAngleBasedOnCenterX(centerX.get(0) + centerX.get(1) / 2);
        } else {
            return Double.MIN_VALUE;
        }
    }

    /**
     * Returns true of false if the bot is centered with the peg +/- given tolerance
     * @param tolerance
     * @return
     */
    private boolean isCenteredOnGear(double tolerance) {
        return (Math.abs(findAngleToPeg()) < tolerance);
    }

   /**
    * makes sure the contours are good or not, if they aren't then
    * it will remove them from the arrayList
    */
    public void validContourIndexes() {
        // Change this to remove invalid contours from all the double[] arrays

        for (int i = heights.size(); i > 0; i--) {
            double width = widths.get(i);
            double height = heights.get(i);

            // Do math - does ratio of w/h match expected w/in margin of error
            // Ratio of tested values has a range of 0.43 < x < 0.45
            // (Room for error)
            if (!(width / height >= 0.41 && width / height <= 0.47)) {
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
