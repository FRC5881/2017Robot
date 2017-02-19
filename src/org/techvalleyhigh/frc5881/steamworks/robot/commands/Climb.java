package org.techvalleyhigh.frc5881.steamworks.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;

/**
 * Created by HHoyt on 2/19/2017.
 */
public class Climb extends Command {
    public Climb() {
        super();
    }

    public Climb(String name) {
        super(name);
    }

    @Override
    protected void execute() {
        RobotMap.climbTalon.set(1);
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        RobotMap.climbTalon.set(0);
    }
}

