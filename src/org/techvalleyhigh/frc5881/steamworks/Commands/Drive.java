package org.techvalleyhigh.frc5881.steamworks.Commands;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.Robot;

/**
 * Drive command to take joystick input at a given scale and move the robot.
 */
public class Drive extends Command {

    private int m_SensitivityScale;

    public Drive(int SensitivityScale) {

        m_SensitivityScale = SensitivityScale;

        requires(Robot.driveControl);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.driveControl.takeJoystickInputs(m_SensitivityScale);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.driveControl.stopDrive();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
