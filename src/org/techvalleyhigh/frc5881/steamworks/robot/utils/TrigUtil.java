package org.techvalleyhigh.frc5881.steamworks.robot.utils;

/**
 * Created by ksharpe on 2/11/2017.
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
     * @return Distance to travel, in inches, to align to center of the peg.
     */
    public static double findDistanceToLineUpWithGear(double deadZone, double distanceGiven, double angleGiven) {
        /* Given a triangle, formed with a hypotenuse defined as distanceGiven, an external angle, complement of the
         * right angle of angleGiven, form a triangle with sides a and b. Then reduce the length of the a side by
         * the deadZone value (to stop short) and recompute the hypotenuse and inside angles.
         */
        double theta = 90 - angleGiven;
        double a = TrigUtil.getOppositeFromAngleAndHypotenuse(distanceGiven, theta);
        double a1 = a - deadZone;
        double b = TrigUtil.getSideFromHypotenuseAndSide(distanceGiven, a);
        return TrigUtil.getHypotenuseFromSides(b, a1); //Distance to travel
    }

    /**
     * Find the angle to run to, inclusive of the dead zone to shop short from, to align to the peg.
     *
     * @param deadZone Distance to stop short of the peg by, robot should be center to peg at this distance when travel
     *                 is complete.
     * @param distanceGiven Current distance from the robot to the center of the vision target.
     * @param angleGiven Current angle, measured from center of bot as 0 degrees, to the target, positive angle to the
     *                   right.
     * @return Angle to turn, measured from center of robot as 0 degrees, with a positive value turning right,
     *         prior to travel, to align to the peg. At the end of travel the negated value of this angle should
     *         result in the bot facing directly at the peg.
     */
    public static double findAngleToTurnToLineUpWithGear(double deadZone, double distanceGiven, double angleGiven) {
        /* Given a triangle, formed with a hypotenuse defined as distanceGiven, an external angle, complement of the
         * right angle of angleGiven, form a triangle with sides a and b. Then reduce the length of the a side by
         * the deadZone value (to stop short) and recompute the hypotenuse and inside angles.
         */
        double theta = 90 - angleGiven;
        double a = TrigUtil.getOppositeFromAngleAndHypotenuse(distanceGiven, theta);
        double a1 = a - deadZone;
        double b = TrigUtil.getSideFromHypotenuseAndSide(distanceGiven, a);
        return Math.atan(a1 / b);
    }
}
