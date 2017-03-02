package org.techvalleyhigh.frc5881.steamworks.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.TrigUtil;

/**
 * Created by ksharpe on 2/2/2017.
 */
public class AutonomousCommand extends CommandGroup {
    private static int deadZone = 40;
    private static double pegCameraDisplacementX = 0;
    private static double pegCameraDisplacementY = 0;

    @Override
    protected boolean isFinished() {
        return false;
    }

    //gear center method
    private boolean isCenteredOnGear() {
        NetworkTable table = NetworkTable.getTable("GRIP/myCountours");

        double[] centerX = {-1};
        centerX = table.getNumberArray("centerX", centerX);

        if (centerX.length == 1 && centerX[0] == -1) {
            System.out.println("Unable to get center values");
            return false;
        }

        if (centerX.length != 2) {
            System.out.println("Got more than 2 contours!!! OMG HELP!");
            return false;
        }

        double avgCenterY = (centerX[0] + centerX[1]) / 2;

        // Image capture 640x480
        // Center == 240

        double offset = Math.abs(240 - avgCenterY);

        if (offset < 10) {
            return true;
        }

        return false;

    }

    // if boiler on opposite side as robot. robot backs up 50 inches and turns 50 degrees either pos or neg depending on side
    // if boiler on same side as robot. robot backs up about 20 inches
    // if starting in the middle. robot backs up about 20 inches and turns 90 degrees either pos or neg depending on boiler's side
    public AutonomousCommand CommandGroup;

    // TODO: Autonomous Plan
    // once centered. How far away is the robot to the object? does it need to go 5ft, 10 ft, 2ft, 20ft?
    // WAIT!!!
    // the robot needs to wait long enough for the person to pull up the lever with the gear on it
    // Drive to position/Align
    // where is the robot compared to the tape? Left, right, or centered?
    // if left what angle to the right does the robot need to go?
    // if right what angle to the left does the robot need to go?
    // push in
    // back up
    // turn to boiler
    // fire fuel

    /**
     * Much of the "Score Gear" autonomous code repeats for each starting position
     */
    private void scoreGear() {
        //Save a snapshot into memory
        pegSnapShot = new PegUtil();

        // Find peg
        double distanceToPeg = pegSnapShot.findDistanceToPeg();
        double angleToPeg = pegSnapShot.findAngleToPeg();

        // Calculate distance and degress to turn (accounting for camera displacement)
        double distanceToDrive = TrigUtil.findDistanceToLineUpWithGear(deadZone, distanceToPeg, angleToPeg, pegCameraDisplacementX, pegCameraDisplacementY);
        double degreesToTurn = TrigUtil.findAngleToTurnToLineUpWithGear(deadZone, distanceToPeg, angleToPeg, pegCameraDisplacementX, pegCameraDisplacementY);

        // Turn Degrees
        addSequential(new AssistedDrive(0, degreesToTurn));

        // Drive Distance
        addSequential(new AssistedDrive(distanceToDrive / 12), 0);

        // Turn -Degress
        addSequential(new AssistedDrive(0, -degreesToTurn));

        // Take new snapshot
        pegSnapShot = new PegUtil();

        // Recalculate Degrees
        addSequential(double angleToPeg = pegSnapShot.findAngleToPeg());

        // Turn
        addSequential(new AssistedDrive(0, angleToPeg));

        // Recalculate Distance
        addSequential(double distanceToGear = pegSnapShot.findDistanceToPeg());

        // Drive if the bot is too close it will start getting wrong readings
        addSequential(new AssistedDrive(distanceToPeg / 12 < deadZone ? distanceToPeg / 12 : deadZone, 0);
    }

    Pegutil pegSnapShot;

    public AutonomousCommand(String Autoroutine) {

        // Default is going to be null because we want to make sure drive team has to select an autonomous run
        if (Autoroutine != "null") {

            // x' == x" / 12

            if(Autoroutine == "Gear Retrieval Zone") {
                // Move to pass base line move into Neurtal Zone and turn (starting on retrevial side)
                // Distance is just an estimation (free to change with practice)
                addSequential(new AssistedDrive(18, 0));

                // Turn to aim towards peg, turn 60 degrees counter clockwise
                addSequential(new AssistedDrive(0, -60));

                //Score gear
                scoreGear();
            } else if(Autoroutine == "Gear Key Zone") {
                // Move to pass base line move into Neutral zone and turn (starting on key side)
                // Distance is just an estimation (free to change with practice)
                addSequential(new AssistedDrive(18, 0));

                // Turn to aim towards peg, turn 60 degrees clockwise
                addSequential(new AssistedDrive(0, 60));

                //Score Gear
                scoreGear();
            } else if(Autoroutine == "Gear Center") {
                //starting centered on the center gear run the score gear function
                scoreGear();

            } else if(Autoroutine == "Baseline") {
                // Crosses Baseline (10 feet just to be safe)
                addSequential(new AssistedDrive(10, 0));

            } else if (Autoroutine == "pos1-b") {
                // Backward 20" == 1.6667'
                addSequential(new AssistedDrive(1.6667, 0));

            } else if (Autoroutine == "pos1-o") {
                // Backward 50" == 4.1667'
                addSequential(new AssistedDrive(4.1667, 0));
                // Turn clockwise 50 degrees
                addSequential(new AssistedDrive(0, 50));

            } else if (Autoroutine == "pos2-l") {
                // Backward 20" == 1.6667'
                addSequential(new AssistedDrive(20, 0));
                //Turn counter-clockwise 90 degrees
                addSequential(new AssistedDrive(0, -90));

            } else if (Autoroutine == "pos2-r") {
                // Backward 20" == 1.6667'
                addSequential(new AssistedDrive(1.6667, 0));
                // Turn clockwise 90 degrees
                addSequential(new AssistedDrive(0, 90));

            } else if (Autoroutine == "pos3-b") {
                //  50" == 4.1667'
                addSequential(new AssistedDrive(4.1667, 0));

            } else if (Autoroutine == "pos3-0") {
                // Backward 50" == 4.1667'
                addSequential(new AssistedDrive(4.1667, 0));
                // Turn clockwise 90 degrees
                addSequential(new AssistedDrive(0, 90));
            }
        }

    }
}