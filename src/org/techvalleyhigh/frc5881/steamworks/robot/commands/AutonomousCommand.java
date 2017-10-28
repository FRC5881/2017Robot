package org.techvalleyhigh.frc5881.steamworks.robot.commands;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.PegUtil;
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

    PegUtil pegSnapShot;

    /**
     * Much of the "Score Gear" autonomous code repeats for each starting position
     */
    private void scoreGear() {
        //Save a snapshot into memory
        this.pegSnapShot = new PegUtil(NetworkTable.getTable("GRIP/myCountours"));

        // Find peg
        double distanceToPeg = this.pegSnapShot.findDistanceToPeg();
        double angleToPeg = this.pegSnapShot.findAngleToPeg();

        // Calculate distance and degrees to turn (accounting for camera displacement)
        double distanceToDrive = TrigUtil.findDistanceToLineUpWithGear(deadZone, distanceToPeg, angleToPeg, pegCameraDisplacementX, pegCameraDisplacementY);
        double degreesToTurn = TrigUtil.findAngleToTurnToLineUpWithGear(deadZone, distanceToPeg, angleToPeg, pegCameraDisplacementX, pegCameraDisplacementY);

        // Turn Degrees
        addSequential(new AssistedDrive(0, degreesToTurn < 2.5 ? 0 : degreesToTurn));

        // Drive Distance
        addSequential(new AssistedDrive(distanceToDrive / 12, 0));

        // Turn -Degrees
        addSequential(new AssistedDrive(0, -degreesToTurn));

        // Take new snapshot
        pegSnapShot = new PegUtil(NetworkTable.getTable("GRIP/myCountours"));

        // Recalculate Degrees && Turn
        addSequential(new AssistedDrive(0, pegSnapShot.findAngleToPeg() < 2.5 ? 0 : pegSnapShot.findAngleToPeg()));

        // Recalculate Distance && Drive, if the bot is too close it will start getting wrong readings
        addSequential(new AssistedDrive(pegSnapShot.findDistanceToPeg() / 12 < deadZone ? pegSnapShot.findDistanceToPeg() / 12 : deadZone, 0));
    }

    public AutonomousCommand(String Autoroutine) {

        // Default is going to be null because we want to make sure drive team has to select an autonomous run
        if (Autoroutine != "null") {

            // x' == x" / 12

            if(Autoroutine == "Gear Boiler") {
                // Move to pass base line move out of the key
                addSequential(new AssistedDrive(60/12, 0));

                //Score gear
                scoreGear();
            } else if(Autoroutine == "Gear Not Boiler") {
                // Starting anywhere other than the key
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

            } else if (Autoroutine == "Curve") {
                addSequential(new ArcPathing(36, 36, 0.65));
            }
        }
    }
}
