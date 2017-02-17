package org.techvalleyhigh.frc5881.steamworks.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.steamworks.robot.*;

/**
 * Created by CMahoney on 2/15/2017.
 */
public class Intake extends Command {
    public Intake() {
        super();
    }

    public Intake(String name) {
        super(name);
    }

    @Override
    protected void execute() {
        RobotMap.intakeTalon.set(1);
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
        RobotMap.intakeTalon.set(0);
    }
}