package org.techvalleyhigh.frc5881.steamworks.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.vision.PegUtil;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.TrigUtil;

/**
 * Created by ksharpe on 2/2/2017.
 */
//TODO: Organize this mess better
public class AutonomousCommand extends CommandGroup {
    @Override
    protected boolean isFinished() {
        return false;
    }

    public AutonomousCommand CommandGroup;

    PegUtil pegSnapShot;

    /**
     * Much of the "Score Gear" autonomous code repeats for each starting position
     */
    private void scoreGear() {
        //Save a snapshot into memory
        //Find peg
        //Debug for testing
        //Calculate distance and degrees to turn (accounting for camera displacement)
        //Turn Degrees
        //Drive Distance
        //Turn -Degrees
        //Take new snapshot
        //Recalculate Degrees && Turn
        //Recalculate Distance && Drive, if the bot is too close it will start getting wrong readings
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
                //Test arc pathing
                addSequential(new ArcPathing(36, 36, 0.65));
            }
        }
    }
}
