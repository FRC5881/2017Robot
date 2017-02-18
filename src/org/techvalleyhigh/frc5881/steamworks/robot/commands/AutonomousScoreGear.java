package org.techvalleyhigh.frc5881.steamworks.robot.commands;



import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.TrigUtil;

/**
 * Created by ksharpe on 2/2/2017.
 */
public class AutonomousScoreGear extends Command {
    private static int deadZone = 40;
    private static double cameraDisplacementX = 0;
    private static double cameraDisplacementY = 0;

    /**
     * In the end calling this fuction will just score the gear
     *
     * @param distanceToGear returned by vision with the distance of the robot to the gear
     * @param angleToGear    returned by vision that tells the robots angler displacement
     */
    public void scoreGear(double distanceToGear, double angleToGear) {
        double distanceToDrive = TrigUtil.findDistanceToLineUpWithGear(deadZone, distanceToGear, angleToGear, cameraDisplacementX, cameraDisplacementY);
        double degreesToTurn = TrigUtil.findAngleToTurnToLineUpWithGear(deadZone, distanceToGear, angleToGear, cameraDisplacementX, cameraDisplacementY);

        //turn degreesToTurn
        //drive distanceToDrive
        //turn -degreesToTurn
        //drive deadZone +/- a bit

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

// TODO: Autonomous Plan
    // Drive to position/Align
    // where is the robot compared to the tape? Left, right, or centered?
    // if left what angle to the right does the robot need to go?
    // if right what angle to the left does the robot need to go?
    // push in
    // once centered. How far away is the robot to the object? does it need to go 5ft, 10 ft, 2ft, 20ft?
    // WAIT!!!
    // the robot needs to wait long enough for the person to pull up the lever with the gear on it
    // back up
    // turn to boiler
    // fire fuel

    //gear center method
    private boolean isCenteredOnGear() {
        NetworkTable table = NetworkTable.getTable("GRIP/myCountours");

        double[] centerY = {-1};
        centerY = table.getNumberArray("centerY", centerY);

        if (centerY.length == 1 && centerY[0] == -1) {
            System.out.println("Unable to get center values");
            return false;
        }

        if (centerY.length != 2) {
            System.out.println("Got more than 2 contours!!! OMG HELP!");
            return false;
        }

        double avgCenterY = (centerY[0] + centerY[1]) / 2;

        // Image capture 640x480
        // Center == 240

        double offset = Math.abs(240-avgCenterY);

        if (offset < 10) {
            return true;
        }

        return false;
    }

}
