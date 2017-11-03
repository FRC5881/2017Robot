package org.techvalleyhigh.frc5881.steamworks.robot.utils;

import com.ctre.CANTalon;

/**
 * Created by CMhahoney on 2/15/2017.
 * -------- WARNING NOT TESTED AT ALL --------
 */
public class PhysicsUtil {
    /**
     * gravitation acceleration (earth)
     */
    private static double g = 9.807;

    /**
     * launchAngle on the robot
     */
    private static double launchAngle = 70;

    // initial velocity with known projectile position (x,y) and known launch angle
    //          /---------------------------------
    //         /              x^2 * g
    // v0 =   /  ----------------------------------
    //      \/  x*sin(2*theta) - 2y * cos^2(theta)

    /**
     * initial velocity with known projectile position (x,y) and known launch angle
     * @param x (Distance 2
     * @param y
     * @param launchAngle
     * @return
     */
    public static double initialVelocityCalc(double x, double y, double launchAngle) {
        return Math.sqrt((x * x * g) / (x * Math.sin(2 * launchAngle) - 2 * y * Math.cos(launchAngle) * Math.cos(launchAngle)));
    }
}