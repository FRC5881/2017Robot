package org.techvalleyhigh.frc5881.steamworks.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.techvalleyhigh.frc5881.steamworks.robot.Robot;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Drive;
import org.techvalleyhigh.frc5881.steamworks.robot.OI;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon;
import com.ctre.CANTalon.*;

public class DriveControl extends Subsystem {
    private static final String AUTO_GYRO_TOLERANCE = "Auto Gyro Tolerance (+- Deg)";

    /**
     * String used for SmartDashboard key for Joystick Y-Axis Deadzone
     */
    private static final String JOYSTICK_DEADZONE_Y = "Joystick Y-Axis Deadzone";

    //Talons
    private static CANTalon talonFrontLeft = RobotMap.talonFrontLeft;
    private static CANTalon talonBackLeft = RobotMap.talonBackLeft;
    private static CANTalon talonFrontRight = RobotMap.talonFrontRight;
    private static CANTalon talonBackRight = RobotMap.talonBackRight;

    //PId controllers
    //private static final PIDController leftDrivePIDController = new PIDController(.2d, .02d, 0, RobotMap.driveControlLeftEncoder, null);
    //private static final PIDController rightDrivePIDController = new PIDController(.2d, .02d, 0, RobotMap.driveControlRightEncoder, null);
    //private static final PIDController gyroPID = new PIDController(7, 2, 0, RobotMap.digitalGyro, null);

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

    // TODO
    //private static final RobotDrive robotDrive = RobotMap.driveControlRobotDrive;


    /**
     * Object for access to the 2016 First Choice 1-axis Gyro on the RoboRIO SPI Port.
     */
    private ADXRS450_Gyro digitalGyro = RobotMap.digitalGyro;

    /**
     * Create the subsystem with a default name.
     */
    public DriveControl() {
        super();
        initSmartDashboard();
    }

    /**
     * Create the subsystem with the given name.
     */
    public DriveControl(String name) {
        super(name);
        initSmartDashboard();
    }

    public void stopDrive() {
        talonBackLeft.set(0);
        talonBackRight.set(0);
        talonFrontLeft.set(0);
        talonFrontRight.set(0);
    }

    /**
     * Initialize the SmartDashboard values.
     */
    private void initSmartDashboard() {
        calibrateGyro();

        // Gryo tolerance - used in auto to provide non-perfect directions
        SmartDashboard.putNumber(AUTO_GYRO_TOLERANCE, 5);
        SmartDashboard.putNumber(JOYSTICK_DEADZONE_Y, 0.1);

        // TODO Comment This
        talonFrontLeft.changeControlMode(TalonControlMode.PercentVbus);
        talonBackLeft.changeControlMode((TalonControlMode.PercentVbus));
        talonFrontRight.changeControlMode((TalonControlMode.PercentVbus));
        talonBackRight.changeControlMode(TalonControlMode.PercentVbus);

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

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Drive(10));
    }

    public void calibrateGyro() {
        digitalGyro.calibrate();
    }

    /**
     * Gets the current angle as reported by the 1-axis Gyro.
     *
     * @return Current Gyro angle. No drift correction is applied.
     */
    public double getGyroAngle() {
        return digitalGyro.getAngle();
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
        talonFrontLeft.set(leftDrive);
        talonBackLeft.set(leftDrive);
        talonFrontRight.set(rightDrive);
        talonBackRight.set(rightDrive);
    }
}