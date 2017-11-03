package org.techvalleyhigh.frc5881.steamworks.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by ksharpe on 2/2/2017.
 */
//TODO: Organize this mess better
public class AutonomousCommand extends CommandGroup {
    /**
     * Auto speed
     */
    private static double speed = 0.5;

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
                addSequential(new AssistedDrive(10, 0, speed));

            } else if (routine == "Left Peg") {
                //While we don't see the
                addSequential(new AssistedDrive(7.5, 0, 0.5));
                addSequential(new AssistedDrive(0, 150, 0.5));

            } else if (routine == "Right Peg") {
                addSequential(new AssistedDrive(7.5,0,0.5));
                System.out.println("Done Going Straight");
                addSequential(new AssistedDrive(0, -150,0.25));
                System.out.println("Done turning");
                addSequential(new ScorePeg());
                System.out.println("Scored Peg");
            } else if (routine == "Test") {
                addSequential(new ScorePeg());
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
