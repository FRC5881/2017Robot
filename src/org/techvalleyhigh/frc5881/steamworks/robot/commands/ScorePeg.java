package org.techvalleyhigh.frc5881.steamworks.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.steamworks.robot.Robot;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.vision.PegUtil;

import static org.techvalleyhigh.frc5881.steamworks.robot.Robot.driveControl;

public class ScorePeg extends Command {
    private PegUtil lastSnapShot;

    private boolean hasTurned = false;
    private boolean startedDriving = false;

    private PIDController leftDrivePIDController;
    private PIDController rightDrivePIDController;

    private double leftDrivePIDOutput = 0;
    private double rightDrivePIDOutput = 0;

    private PIDController gyroPID;
    private double gyroPIDOutput = 0;

    ScorePeg() {
        requires(Robot.vision);
        requires(Robot.driveControl);
        driveControl = Robot.driveControl;
        initialize();
    }

    protected void initialize() {
        lastSnapShot = Robot.vision.takePegSnapShot();
        lastSnapShot.setIsUsable(true);

        RobotMap.driveControlLeftEncoder.reset();
        RobotMap.driveControlRightEncoder.reset();

        leftDrivePIDController = new PIDController(driveControl.getLeftPIDKp(), driveControl.getLeftPIDKi(),
                driveControl.getLeftPIDKd(), RobotMap.driveControlLeftEncoder,
                output -> leftDrivePIDOutput = output);
        rightDrivePIDController = new PIDController(driveControl.getRightPIDKp(), driveControl.getRightPIDKi(),
                driveControl.getRightPIDKd(), RobotMap.driveControlRightEncoder,
                output -> rightDrivePIDOutput = output);

        // Limit the PID output range to valid motor control values
        leftDrivePIDController.setOutputRange(-0.5, 0.5);
        rightDrivePIDController.setOutputRange(-0.5, 0.5);

        // Set a 1" tolerance
        leftDrivePIDController.setAbsoluteTolerance(3);
        rightDrivePIDController.setAbsoluteTolerance(3);

        gyroPID = new PIDController(driveControl.getGyroPIDKp(), driveControl.getGyroPIDKi(),
                driveControl.getGyroPIDKd(), RobotMap.digitalGyro, output -> gyroPIDOutput = output);

        // Limit the gyro output to a small number used to simulate a turn on the joystick.
        gyroPID.setOutputRange(-1, 1);

        // Set the angle to keep at in degrees
        gyroPID.setSetpoint(0);

        // Apply the dashboard tolerance
        gyroPID.setAbsoluteTolerance(driveControl.getAutoGyroTolerance());

        gyroPID.enable();
    }

    protected void execute() {
        PegUtil snapShot = Robot.vision.takePegSnapShot();
        System.out.println("Taking Snap Shot");
        if(!hasTurned) {
            if ((snapShot.isReasonable() && (!lastSnapShot.isReasonable() || lastSnapShot.getAngleToPeg() != snapShot.getAngleToPeg())) || lastSnapShot.getIsUsable()) {
                gyroPID.setSetpoint(driveControl.getGyroAngle() + snapShot.getAngleToPeg());
                lastSnapShot = snapShot;
            } else {
                System.out.println("Vision snap shot is not reasonable!!!");
                end();
            }
            if(gyroPID.onTarget() && lastSnapShot.isCenteredOnGear(5)) {
                hasTurned = true;
            }
        } else if(!startedDriving) {
            double distance = snapShot.getDistanceToPeg();
            rightDrivePIDController.setSetpoint(distance);
            leftDrivePIDController.setSetpoint(distance);
            rightDrivePIDController.enable();
            leftDrivePIDController.enable();
            startedDriving = true;
        }
    }

    protected boolean isFinished() {
        //System.out.println("Is Finished");
        return hasTurned && rightDrivePIDController.onTarget() && leftDrivePIDController.onTarget();
    }

    protected void end() {
        System.out.println("Ending Turn To Peg");
    }

    protected void interrupted() {
        end();
    }
}
