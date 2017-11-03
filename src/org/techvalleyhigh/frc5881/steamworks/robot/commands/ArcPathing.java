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

    //Drive controller
    private DriveControl driveControl;

    //PID controllers
    private PIDController leftDrivePIDController;
    private PIDController rightDrivePIDController;

    //Starting PID outputs
    private double rightDrivePIDOutput, leftDrivePIDOutput = 0;

    /**
     *
     * @param x Distance in feet
     * @param y Distance in feet
     * @param speed
     */
    ArcPathing(double x, double y, double speed) {
        this.x = x * 12;
        this.y = y * 12;
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

        System.out.println("New Curve Pathing Horizontal Distance " + x + " inches Forward Distance " + y);
        System.out.println("Set point x " + setPoints[0] + " Set point y " + setPoints[1]);
    }

    @Override
    protected void execute() {
        //System.out.println(rightDrivePIDOutput + " " + leftDrivePIDOutput);
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
          System.out.println("Is Finshed?");
          System.out.println(rightDrivePIDController.onTarget());
          System.out.println(leftDrivePIDController.onTarget());

        return rightDrivePIDController.onTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("END");
        leftDrivePIDController.disable();
        rightDrivePIDController.disable();

        Robot.driveControl.stopDrive();
        //Robot.shooter.spinStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("Interrupted");
        end();
    }


    /**
     * Find the necessary percentages to send to near and far side motors (farSide will be greater than 100%)
     * To turn an arc described by a right triangle x, y
     * Then finds the this distances each set of wheels needs to travel.
     * If x is positive the robot turns towards the right, if x is negative the robot turns towards the left.
     * See Curve Path Math.png in root folder for geometry
     * @return [right side setpoint, left side setpoint]
     */
    private double[] findSetPoints() {
        double rightSide, leftSide, r, d;

        if (x == y) {
            r = x;
            d = r * Math.PI / 2;
        } else {
            //Geometry
            double h = Math.sqrt(this.x * this.x + this.y * this.y);
            System.out.println("h " + h);
            double alpha = Math.atan(this.y / this.x);
            System.out.println("Alpha " + alpha);
            double C = Math.PI - (2 * alpha);
            System.out.println("C " + C);
            r = Math.abs(Math.sin(alpha) * h / Math.cos(C));
            System.out.println("r " + r);
            d = C * r; //Arc length
            System.out.println("d " + d);
        }
        rightSide = (r - (botWidth / 24)) / r;
        leftSide = (r + (botWidth / 24)) / r;

        System.out.println(leftSide + " <---left , right--->" + rightSide);
        System.out.println(leftSide * d + " <--- left, right ---> " + rightSide * d);

        return new double[]{-rightSide * d, -leftSide * d};
    }
}