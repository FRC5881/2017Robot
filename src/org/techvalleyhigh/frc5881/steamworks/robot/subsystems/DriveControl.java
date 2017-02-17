package org.techvalleyhigh.frc5881.steamworks.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.techvalleyhigh.frc5881.steamworks.robot.Robot;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Drive;
import org.techvalleyhigh.frc5881.steamworks.robot.OI;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon.*;

public class DriveControl extends Subsystem {
    private static final String AUTO_GYRO_TOLERANCE = "Auto Gyro Tolerance (+- Deg)";

    /**
     * String used for SmartDashboard key for Joystick Y-Axis Deadzone
     */
    private static final String JOYSTICK_DEADZONE_Y = "Joystick Y-Axis Deadzone";

    //Talons

    //PId controllers
    private static PIDController leftDrivePIDController;
    private static PIDController rightDrivePIDController;
    private static PIDController gyroPID;

    //PID values
    private static final String LEFT_DRIVE_PID_KP = "Left Drive PID Kp";
    private static final double LEFT_DRIVE_PID_KP_DEFAULT = 0.013;
    private static final String LEFT_DRIVE_PID_KI = "Left Drive PID Ki";
    private static final double LEFT_DRIVE_PID_KI_DEFAULT = 0.001;
    private static final String LEFT_DRIVE_PID_KD = "Left Drive PID Kd";
    private static final double LEFT_DRIVE_PID_KD_DEFAULT = 0.012;

    private static final String RIGHT_DRIVE_PID_KP = "Right Drive PID Kp";
    private static final double RIGHT_DRIVE_PID_KP_DEFAULT = 0.013;
    private static final String RIGHT_DRIVE_PID_KI = "Right Drive PID Ki";
    private static final double RIGHT_DRIVE_PID_KI_DEFAULT = 0.001;
    private static final String RIGHT_DRIVE_PID_KD = "Right Drive PID Kd";
    private static final double RIGHT_DRIVE_PID_KD_DEFAULT = 0.012;

    private static final String GYRO_PID_KP = "Gyro PID Kp";
    private static final double GYRO_PID_KP_DEFAULT = 0.14;
    private static final String GYRO_PID_KI = "Gyro PID Ki";
    private static final double GYRO_PID_KI_DEFAULT = 0.002;
    private static final String GYRO_PID_KD = "Gyro PID Kd";
    private static final double GYRO_PID_KD_DEFAULT = 0.045;

    private double leftDrivePIDOutput;
    private double rightDrivePIDOutput;
    private double gyroPIDOutput;

    // TODO
    //private static final RobotDrive robotDrive = RobotMap.driveControlRobotDrive;

    /**
     * Create the subsystem with a default name.
     */
    public DriveControl() {
        super();
        init();
    }

    /**
     * Create the subsystem with the given name.
     */
    public DriveControl(String name) {
        super(name);
        init();
    }

    public void stopDrive() {
        RobotMap.talonBackLeft.set(0);
        RobotMap.talonBackRight.set(0);
        RobotMap.talonFrontLeft.set(0);
        RobotMap.talonFrontRight.set(0);
    }

    /**
     * Initialize the SmartDashboard and other local variables.
     */
    private void init() {
        calibrateGyro();

        // Gryo tolerance - used in auto to provide non-perfect directions
        SmartDashboard.putNumber(AUTO_GYRO_TOLERANCE, 5);
        SmartDashboard.putNumber(JOYSTICK_DEADZONE_Y, 0.1);

        // TODO Comment This
        RobotMap.talonFrontLeft.changeControlMode(TalonControlMode.PercentVbus);
        RobotMap.talonBackLeft.changeControlMode((TalonControlMode.PercentVbus));
        RobotMap.talonFrontRight.changeControlMode((TalonControlMode.PercentVbus));
        RobotMap.talonBackRight.changeControlMode(TalonControlMode.PercentVbus);

        //PID values
        SmartDashboard.putNumber(LEFT_DRIVE_PID_KP, LEFT_DRIVE_PID_KP_DEFAULT);
        SmartDashboard.putNumber(LEFT_DRIVE_PID_KI, LEFT_DRIVE_PID_KI_DEFAULT);
        SmartDashboard.putNumber(LEFT_DRIVE_PID_KD, LEFT_DRIVE_PID_KD_DEFAULT);

        SmartDashboard.putNumber(RIGHT_DRIVE_PID_KP, RIGHT_DRIVE_PID_KP_DEFAULT);
        SmartDashboard.putNumber(RIGHT_DRIVE_PID_KI, RIGHT_DRIVE_PID_KI_DEFAULT);
        SmartDashboard.putNumber(RIGHT_DRIVE_PID_KD, RIGHT_DRIVE_PID_KD_DEFAULT);

        SmartDashboard.putNumber(GYRO_PID_KP, GYRO_PID_KP_DEFAULT);
        SmartDashboard.putNumber(GYRO_PID_KI, GYRO_PID_KI_DEFAULT);
        SmartDashboard.putNumber(GYRO_PID_KD, GYRO_PID_KD_DEFAULT);

        leftDrivePIDController = new PIDController(getLeftPIDKp(), getLeftPIDKi(), getLeftPIDKd(),
                RobotMap.driveControlLeftEncoder, output -> leftDrivePIDOutput = output);
        rightDrivePIDController = new PIDController(getRightPIDKp(), getRightPIDKi(), getRightPIDKd(),
                RobotMap.driveControlRightEncoder, output -> rightDrivePIDOutput = output);
        gyroPID = new PIDController(getGyroPIDKp(), getGyroPIDKi(), getGyroPIDKd(), RobotMap.digitalGyro,
                output -> gyroPIDOutput = output);
    }

    //Getters for PID
    public double getLeftPIDKp() {
        return SmartDashboard.getNumber(LEFT_DRIVE_PID_KP, LEFT_DRIVE_PID_KP_DEFAULT);
    }

    public double getLeftPIDKi() {
        return SmartDashboard.getNumber(LEFT_DRIVE_PID_KI, LEFT_DRIVE_PID_KI_DEFAULT);
    }

    public double getLeftPIDKd() {
        return SmartDashboard.getNumber(LEFT_DRIVE_PID_KD, LEFT_DRIVE_PID_KD_DEFAULT);
    }

    public double getRightPIDKp() {
        return SmartDashboard.getNumber(RIGHT_DRIVE_PID_KP, RIGHT_DRIVE_PID_KP_DEFAULT);
    }

    public double getRightPIDKi() {
        return SmartDashboard.getNumber(RIGHT_DRIVE_PID_KI, RIGHT_DRIVE_PID_KI_DEFAULT);
    }

    public double getRightPIDKd() {
        return SmartDashboard.getNumber(RIGHT_DRIVE_PID_KD, RIGHT_DRIVE_PID_KD_DEFAULT);
    }

    public double getGyroPIDKp() {
        return SmartDashboard.getNumber(GYRO_PID_KP, GYRO_PID_KP_DEFAULT);
    }

    public double getGyroPIDKi() {
        return SmartDashboard.getNumber(GYRO_PID_KI, GYRO_PID_KI_DEFAULT);
    }

    public double getGyroPIDKd() {
        return SmartDashboard.getNumber(GYRO_PID_KD, GYRO_PID_KD_DEFAULT);
    }

    public double getLeftDrivePIDOutput() {
        return leftDrivePIDOutput;
    }

    public double getRightDrivePIDOutput() {
        return rightDrivePIDOutput;
    }

    public double getGyroPIDOutput() {
        return gyroPIDOutput;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Drive(10));
    }

    public void calibrateGyro() {
        RobotMap.digitalGyro.calibrate();
    }

    /**
     * Gets the current angle as reported by the 1-axis Gyro.
     *
     * @return Current Gyro angle. No drift correction is applied.
     */
    public double getGyroAngle() {
        return RobotMap.digitalGyro.getAngle();
    }

    /**
     * Gets the currently set Gyro Tolerance for Autonomous.
     *
     * @return Number of degrees of tolerance +-
     */
    public int getAutoGyroTolerance() {
        return (int) SmartDashboard.getNumber(AUTO_GYRO_TOLERANCE, 5);
    }

    /**
     * Update the SmartDashboard with current values.
     */
    private void updateDashboard() {
        SmartDashboard.putNumber("Gyro Heading", getGyroAngle());

    }

    // TODO: Need to scale inputs or ditch the scale factor
    public void takeJoystickInputs(int scaleFactor) {
        tankDrive(Robot.oi.xboxController);
    }

    /**
     * Getting Joy Stick values
     */
    public void tankDrive(GenericHID xboxController) {
        double leftDrive = xboxController.getRawAxis(OI.LeftYAxis);
        double rightDrive = xboxController.getRawAxis(OI.RightY);
        RobotMap.talonFrontLeft.set(leftDrive);
        RobotMap.talonBackLeft.set(leftDrive);
        RobotMap.talonFrontRight.set(rightDrive);
        RobotMap.talonBackRight.set(rightDrive);
    }
}