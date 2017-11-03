package org.techvalleyhigh.frc5881.steamworks.robot.utils;

/**
 * Created by Cmahoney on 2/11/2017.
 */
public class TrigUtil {
    public static double getHypotenuseFromSides(double a, double b) {

        return Math.sqrt(a * a + b * b);
    }

    public static double getSideFromHypotenuseAndSide(double c, double a) {

        return Math.sqrt(a * a - c * c);
    }

    public static double getOppositeFromAngleAndHypotenuse(double c, double theta) {

        double sinOfTheta = Math.sin(theta);

        return c * sinOfTheta;
    }

    /**
     * Find the distance to travel to align before a final push into the gear peg.
     *
     * @param deadZone Distance to stop short of the peg by, robot should be center to peg at this distance when travel
     *                 is complete.
     * @param distanceGiven Current distance from the robot to the center of the vision target.
     * @param angleGiven Current angle, measured from center of bot as 0 degrees, to the target, positive angle to the
     *                   right.
     * @param cameraDisplacementX Displacement of the camera across X axis from center
     * @param cameraDisplacementY Displacement of the camera across Y axis from center
     * @return Distance to travel, in inches, to align to center of the peg.
     */
    public static double findDistanceToLineUpWithGear(double deadZone, double distanceGiven, double angleGiven, double cameraDisplacementX, double cameraDisplacementY) {
        /* Creates a vector using distanceGiven as the mangitude and angleGiven as the angle,
         * from this vector we then subtract the camera displacement vector
         * subtract the deadzone from the y-axis magnitude of this new vector
         * and return the magnitude of the final vector
         */

        double cDeltaX = Math.sin(angleGiven) * distanceGiven;
        double cDeltaY = Math.cos(angleGiven) * distanceGiven;
        double vectorX = cDeltaX - cameraDisplacementX;
        double vectorY = cDeltaY - cameraDisplacementY - deadZone;
        return TrigUtil.getHypotenuseFromSides(vectorX, vectorY); //Distance to drive

    }

    /**
     * Find the angle to run to, inclusive of the dead zone to shop short from, to align to the peg.
     *
     * @param deadZone Distance to stop short of the peg by, robot should be center to peg at this distance when travel
     *                 is complete.
     * @param distanceGiven Current distance from the robot to the center of the vision target.
     * @param angleGiven Current angle, measured from center of bot as 0 degrees, to the target, positive angle to the
     *
     * @param cameraDisplacementX Displacement of the camera across X axis from center
     * @param cameraDisplacementY Displacement of the camera across Y axis from center
     *                   right.
     * @return Angle to turn, measured from center of robot as 0 degrees, with a positive value turning right,
     *         prior to travel, to align to the peg. At the end of travel the negated value of this angle should
     *         result in the bot facing directly at the peg.
     */
    public static double findAngleToTurnToLineUpWithGear(double deadZone, double distanceGiven, double angleGiven, double cameraDisplacementX, double cameraDisplacementY) {
        /* Creates a vector using distanceGiven as the mangitude and angleGiven as the angle,
         * from this vector we then subtract the camera displacement vector
         * subtract the dead zone from the y-axis magnitude of this new vector
         * and return the angle of the final vector and vertical
         */

        double cDeltaX = Math.sin(angleGiven) * distanceGiven;
        double cDeltaY = Math.cos(angleGiven) * distanceGiven;
        double vectorX = cDeltaX - cameraDisplacementX;
        double vectorY = cDeltaY - cameraDisplacementY - deadZone;
        return Math.atan(vectorY / vectorX); //Angle to turn
    }
}
