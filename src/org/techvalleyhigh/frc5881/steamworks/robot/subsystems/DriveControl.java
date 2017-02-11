package org.techvalleyhigh.frc5881.steamworks.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.techvalleyhigh.frc5881.steamworks.robot.Robot;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Drive;
import org.techvalleyhigh.frc5881.steamworks.robot.OI;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon;
import com.ctre.CANTalon.*;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.TrigUtil;

import java.math.*;

public class DriveControl extends Subsystem {
    private static final String AUTO_GYRO_TOLERANCE = "Auto Gyro Tolerance (+- Deg)";

    /**
     * String used for SmartDashboard key for Joystick Y-Axis Deadzone
     */
    private static final String JOYSTICK_DEADZONE_Y = "Joystick Y-Axis Deadzone";
/* (uncomment when fixed)
    private static final PIDController leftDrivePIDController = new PIDController(.2d, .02d, 0, RobotMap.driveControlLeftEncoder, null);
    private static final PIDController rightDrivePIDController = new PIDController(.2d, .02d, 0, RobotMap.driveControlRightEncoder, null);
    private static final PIDController gyroPID = new PIDController(7, 2, 0, RobotMap.driveControlDigitalGyro, null);

    private static final RobotDrive robotDrive = RobotMap.driveControlRobotDrive;
*/

    /**
     * Object for access to the 2016 First Choice 1-axis Gyro on the RoboRIO SPI Port.
     */
    private ADXRS450_Gyro digitalGyro;
    /**
     * Chooser for SmartDashboard to select teh autonomous drive speed.
     */
    private SendableChooser autoSpeedChooser;

    private CANTalon talonFrontLeft = new CANTalon(1);
    private CANTalon talonBackLeft = new CANTalon(2);
    private CANTalon talonFrontRight = new CANTalon(3);
    private CANTalon talonBackRight = new CANTalon(4);


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

    }

    /**
     * Initialize the SmartDashboard values.
     */
    private void initSmartDashboard() {
        calibrateGyro();

        // Gryo tolerance - used in auto to provide non-perfect directions
        SmartDashboard.putNumber(AUTO_GYRO_TOLERANCE, 5);
        SmartDashboard.putNumber(JOYSTICK_DEADZONE_Y, 0.1);

        talonFrontLeft.changeControlMode(TalonControlMode.PercentVbus);
        talonBackLeft.changeControlMode((TalonControlMode.PercentVbus));
        talonFrontRight.changeControlMode((TalonControlMode.PercentVbus));
        talonBackRight.changeControlMode(TalonControlMode.PercentVbus);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Drive(10));
    }

    public void calibrateGyro() {
        digitalGyro = RobotMap.digitalGyro;
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

    public void takeJoystickInputs(int scaleFactor) {
        tankDrive(Robot.oi.xboxController);
    }

    /**
     * Getting Joy Stick values
     */
    public void tankDrive(GenericHID xboxController) {
        double leftDrive = xboxController.getRawAxis(OI.LeftY);
        double rightDrive = xboxController.getRawAxis(OI.RightY);
        talonFrontLeft.set(leftDrive);
        talonBackLeft.set(leftDrive);
        talonFrontRight.set(rightDrive);
        talonBackRight.set(rightDrive);
    }

}