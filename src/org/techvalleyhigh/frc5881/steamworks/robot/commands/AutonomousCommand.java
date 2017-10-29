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
    private String routine;
    public AutonomousCommand(String Autoroutine) {
        routine = Autoroutine;
        run();
    }

    private void run() {
        // Default is going to be null because we want to make sure drive team has to select an autonomous run
        if (routine != "null") {
            if(routine == "Baseline") {
                // Crosses Baseline (10 feet just to be safe)
                addSequential(new AssistedDrive(10, 0));

            } else if (routine == "Left Peg") {
                //While we don't see the
            } else if (routine == "Right Peg") {

            } else if (routine == "Testing") {
                //Test arc pathing
                addSequential(new ArcPathing(36, 36, 0.65));

            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    private void scoreGear(boolean turnLeft) {

    }
}
