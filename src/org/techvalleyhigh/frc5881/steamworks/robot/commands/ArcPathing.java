package org.techvalleyhigh.frc5881.steamworks.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.steamworks.robot.Robot;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.DriveControl;

public class ArcPathing extends Command {
    /**
     * Width of the bot (from wheel edge to wheel edge)
     * In inches
     */
    private static double botWidth = 30;

    /**
     * Horizontal component of curve
     */
    public double x;

    /**
     * Vertical component of curve
     */
    public double y;

    /**
     * Target speed of the bot while pathing.
     */
    public double speed ;

    private DriveControl driveControl;

    private PIDController leftDrivePIDController;
    private PIDController rightDrivePIDController;

    private double rightDrivePIDOutput, leftDrivePIDOutput = 0;

    public ArcPathing(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        requires(Robot.driveControl);
        driveControl = Robot.driveControl;
    }

    @Override
    protected void initialize() {
        //Reset the encoders
        RobotMap.driveControlLeftEncoder.reset();
        RobotMap.driveControlRightEncoder.reset();

        //Set up PID controllers
        leftDrivePIDController = new PIDController(driveControl.getLeftPIDKp(), driveControl.getLeftPIDKi(),
                driveControl.getLeftPIDKd(), RobotMap.driveControlLeftEncoder,
                output -> leftDrivePIDOutput = output);
        rightDrivePIDController = new PIDController(driveControl.getRightPIDKp(), driveControl.getRightPIDKi(),
                driveControl.getRightPIDKd(), RobotMap.driveControlRightEncoder,
                output -> rightDrivePIDOutput = output);

        //Limit PID output range to motor control values
        leftDrivePIDController.setOutputRange(-1 * this.speed, this.speed);
        rightDrivePIDController.setOutputRange(-1 * this.speed, this.speed);

        //Set distances to travel in inches
        double[] setPoints = this.findSetPoints();
        rightDrivePIDController.setSetpoint(setPoints[0]);
        leftDrivePIDController.setSetpoint(setPoints[1]);

        //Tolerance
        leftDrivePIDController.setAbsoluteTolerance(1);
        rightDrivePIDController.setAbsoluteTolerance(1);

        //Enable
        leftDrivePIDController.enable();
        rightDrivePIDController.enable();
    }

    @Override
    protected void execute() {
        driveControl.tankDrive(rightDrivePIDOutput, leftDrivePIDOutput);

        SmartDashboard.putNumber("Left PID Output", leftDrivePIDOutput);
        SmartDashboard.putNumber("Right PID Output", rightDrivePIDOutput);

        SmartDashboard.putNumber("Right Encoder", RobotMap.driveControlRightEncoder.getDistance());
        SmartDashboard.putNumber("Left Encoder", RobotMap.driveControlLeftEncoder.getDistance());

        SmartDashboard.putNumber("Left Target", leftDrivePIDController.getSetpoint());
        SmartDashboard.putNumber("Right Target", rightDrivePIDController.getSetpoint());

        SmartDashboard.putNumber("Left Error", leftDrivePIDController.getError());
        SmartDashboard.putNumber("Right Error", rightDrivePIDController.getError());
    }

    @Override
    protected boolean isFinished() {
        return rightDrivePIDController.onTarget() || leftDrivePIDController.onTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        leftDrivePIDController.disable();
        rightDrivePIDController.disable();

        Robot.driveControl.stopDrive();
        //Robot.shooter.spinStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }


    /**
     * Find the nessaccary percentages to send to near and far side motors (farSide will be greater than 100%)
     * To turn an arc described by a right trianle x, y
     * Then finds the this distances each set of wheels needs to travel.
     * If x is positive the robot turns towards the right, if x is negative the robot turns towards the left.
     * See Curve Path Math.png in root folder for geometry
     * @return [right side setpoint, left side setpoint]
     */
    private double[] findSetPoints() {
        //Geometry
        double h = Math.sqrt(this.x * this.x + this.y * this.y);
        double alpha = Math.atan(this.y / this.x);
        double C = Math.PI - 2 * alpha;
        double r = Math.sin(alpha) * h / Math.cos(C);
        double d = C * r; //Arc length

        double rightSide = 0;
        double leftSide = 0;

        if(x > 0) {
            rightSide = (r - this.botWidth / 2) / r;
            leftSide = (r + this.botWidth / 2) / r;
        } else {
            leftSide = (r - this.botWidth / 2) / r;
            rightSide = (r + this.botWidth / 2) / r;
        }

        double[] out = {rightSide * d, leftSide * d};
        return out;
    }
}