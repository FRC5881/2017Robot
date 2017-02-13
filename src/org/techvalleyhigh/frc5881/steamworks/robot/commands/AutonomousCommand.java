package org.techvalleyhigh.frc5881.steamworks.robot.commands;

import org.techvalleyhigh.frc5881.steamworks.robot.utils.TrigUtil;

/**
 * Created by ksharpe on 2/2/2017.
 */
public class AutonomousCommand {
    private static int deadZone = 40;
    private static int cameraDisplacementX = 0;
    private static int cameraDisplacementY = 0;

    /** In the end calling this fuction will just score the gear
     * @param distanceToGear returned by vision with the distance of the robot to the gear
     * @param angleToGear returned by vision that tells the robots angler displacement
     */
    public void scoreGear(double distanceToGear, double angleToGear) {
        double distanceToDrive = TrigUtil.findDistanceToLineUpWithGear(deadZone, distanceToGear, angleToGear, cameraDisplacementX, cameraDisplacementY);
        double degreesToTurn = TrigUtil.findAngleToTurnToLineUpWithGear(deadZone, distanceToGear, angleToGear, cameraDisplacementX, cameraDisplacementY);

        //turn degreesToTurn
        //drive distanceToDrive
        //turn -degreesToTurn
        //drive deadZone +/- a bit

    }
}