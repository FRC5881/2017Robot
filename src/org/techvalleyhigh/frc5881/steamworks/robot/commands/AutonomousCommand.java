package org.techvalleyhigh.frc5881.steamworks.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by ksharpe on 2/2/2017.
 */

public class AutonomousCommand extends CommandGroup {
    /**
     * Auto speed drive speed
     */
    private static double driveSpeed = 0.5;
    private static double turnSpeed = 0.25;

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
                addSequential(new AssistedDrive(10, 0, driveSpeed));
            }
            /* else if (routine == "Blue Left") {
                addSequential(new AssistedDrive(1, 0, driveSpeed));
                addSequential(new AssistedDrive(0, 65, turnSpeed));
                addSequential(new AssistedDrive(109d/12d, 0, driveSpeed));
                addSequential(new AssistedDrive(0,-90, turnSpeed));
                addSequential(new AssistedDrive(10, 0, driveSpeed));
            } else if(routine == "Blue Right") {
                addSequential(new AssistedDrive(7.5, 0, driveSpeed));
                addSequential(new AssistedDrive(0, 60, turnSpeed));
                addSequential(new AssistedDrive(10,0, driveSpeed));
            } else if(routine == "Red Left") {
                addSequential(new AssistedDrive(9, 0, driveSpeed));
                addSequential(new AssistedDrive(0, -60, turnSpeed));
                addSequential(new AssistedDrive(10,0, driveSpeed));
            } else if(routine == "Red Right") {
                addSequential(new AssistedDrive(1, 0, driveSpeed));
                addSequential(new AssistedDrive(0, -65, turnSpeed));
                addSequential(new AssistedDrive(109d/12d, 0, driveSpeed));
                addSequential(new AssistedDrive(0,90, turnSpeed));
                addSequential(new AssistedDrive(10, 0, driveSpeed));
            }*/
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
