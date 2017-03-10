package org.techvalleyhigh.frc5881.steamworks.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.steamworks.robot.Robot;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.DriveControl;

/**
 * Created by CMahoney on 2/13/2017.
 */
public class AssistedDrive extends Command {
    //TODO assisted driving and turning

    private double distanceInFeet;
    private double relativeBearing;

    private DriveControl driveControl;

    private PIDController leftDrivePIDController;
    private PIDController rightDrivePIDController;

    private static double drivePower = 0.5;

    /**
     * gyrpPID outputs a value that needs to be applied to the power of drive motors to adjust heading
     * -1 * output to left and output to right
     */
    private PIDController gyroPID;

    private double leftDrivePIDOutput = 0;
    private double rightDrivePIDOutput = 0;
    private double gyroPIDOutput = 0;

    public                                                                          AssistedDrive(double distanceInFeet, double relativeBearing) {

        if(distanceInFeet == 0 && relativeBearing == 0) {
            return;
        }
        this.distanceInFeet = distanceInFeet;
        this.relativeBearing = relativeBearing;

        requires(Robot.driveControl);
        driveControl = Robot.driveControl;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        RobotMap.driveControlLeftEncoder.reset();
        RobotMap.driveControlRightEncoder.reset();

        // Set the absolute bearing based on the relative bearing of travel added to the
        // current actual heading of the robot.
        double absBearing = Robot.driveControl.getGyroAngle() + relativeBearing;

        leftDrivePIDController = new PIDController(driveControl.getLeftPIDKp(), driveControl.getLeftPIDKi(),
                driveControl.getLeftPIDKd(), RobotMap.driveControlLeftEncoder,
                output -> leftDrivePIDOutput = output);
        rightDrivePIDController = new PIDController(driveControl.getRightPIDKp(), driveControl.getRightPIDKi(),
                driveControl.getRightPIDKd(), RobotMap.driveControlRightEncoder,
                new PIDOutput() {
                    @Override
                    public void pidWrite(double output) {
                        rightDrivePIDOutput = output;
                    }
                });


        // Limit the PID output range to valid motor control values
        leftDrivePIDController.setOutputRange(-1 * drivePower, drivePower);
        rightDrivePIDController.setOutputRange(-1 * drivePower, drivePower);

        // Set the distance to travel in inches, left is negative
        leftDrivePIDController.setSetpoint(12 * distanceInFeet * -1);
        rightDrivePIDController.setSetpoint(12 * distanceInFeet);

        // Set a 1" tolerance
        leftDrivePIDController.setAbsoluteTolerance(1);
        rightDrivePIDController.setAbsoluteTolerance(1);

        gyroPID = new PIDController(driveControl.getGyroPIDKp(), driveControl.getGyroPIDKi(),
                driveControl.getGyroPIDKd(), RobotMap.digitalGyro, output -> gyroPIDOutput = output);

        // Limit the gyro output to a small number used to simulate a turn on the joystick.
        gyroPID.setOutputRange(-1, 1);

        // Set the angle to keep at in degrees
        gyroPID.setSetpoint(absBearing);

        // Apply the dashboard tolerance
        gyroPID.setAbsoluteTolerance(Robot.driveControl.getAutoGyroTolerance());

        leftDrivePIDController.enable();
        rightDrivePIDController.enable();
        gyroPID.enable();

        // Debugging output very helpful. DS needs a console setting change to see it.

       System.out.println("Assisted Drive Leg - " + distanceInFeet + "ft at power " + drivePower
        + " bearing " + relativeBearing + " rel deg " + absBearing);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //driveControl.rawDrive(rightDrivePIDOutput, -1 * gyroPIDOutput);

        SmartDashboard.putNumber("Gyro PID Output", gyroPIDOutput);
        SmartDashboard.putNumber("Left PID Output", leftDrivePIDOutput);
        SmartDashboard.putNumber("Right PID Output", rightDrivePIDOutput);

        SmartDashboard.putNumber("Right Encoder", RobotMap.driveControlRightEncoder.getDistance());
        SmartDashboard.putNumber("Left Encoder", RobotMap.driveControlLeftEncoder.getDistance());

        SmartDashboard.putNumber("Left Target", leftDrivePIDController.getSetpoint());
        SmartDashboard.putNumber("Right Target", rightDrivePIDController.getSetpoint());

        SmartDashboard.putNumber("Left Error", leftDrivePIDController.getError());
        SmartDashboard.putNumber("Right Error", rightDrivePIDController.getError());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return rightDrivePIDController.onTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        leftDrivePIDController.disable();
        rightDrivePIDController.disable();
        gyroPID.disable();

        Robot.driveControl.stopDrive();
        Robot.shooter.spinStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}