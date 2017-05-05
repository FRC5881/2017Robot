package org.techvalleyhigh.frc5881.steamworks.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.steamworks.robot.Robot;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;

/**
 * Created by CMahoney on 2/15/2017.
 */
public class Shoot extends Command {
    public Shoot() {
        super();
    }

    public Shoot(String name) {
        super(name);
    }

    @Override
    protected void execute() {
        RobotMap.shooterTopTalon.setPID(1, 0, 0);
    }

    @Override
    protected void interrupted() {
        super.interrupted();
        end();
    }

    @Override
    protected void initialize() {

        //super.initialize();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        //Robot.shooter.spinStop();
    }
}
