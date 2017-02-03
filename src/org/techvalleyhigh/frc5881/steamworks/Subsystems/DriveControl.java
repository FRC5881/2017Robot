package org.techvalleyhigh.frc5881.steamworks.Subsystems;

import org.techvalleyhigh.frc5881.steamworks.Util.RobotMap;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveControl {

    private static final String AUTO_GYRO_TOLERANCE = "Auto Gyro Tolerance (+- Deg)";

    /**
     * String used for SmartDashboard key for Full Power 10' Time
     */
    private static final String FULL_POWER_TIME = "Full Power 10' Time";

    /**
     * String used for SmartDashboard value for Full Speed
     */
    private static final String FULL_SPEED_VALUE = "FULL";

    /**
     * String used for SmartDashboard key for Half Power 10' Time
     */
    private static final String HALF_POWER_TIME = "Half Power 10' Time";

    /**
     * String used for SmartDashboard value for Half Speed
     */
    private static final String HALF_SPEED_VALUE = "HALF";

    /**
     * String used for SmartDashboard key for One-Third Power 10' Time
     */
    private static final String ONETHIRD_POWER_TIME = "One-Third Power 10' Time";

    /**
     * String used for SmartDashboard value for 1/3rd Speed
     */
    private static final String ONETHIRD_SPEED_VALUE = "THIRD";

    /**
     * String used for SmartDashboard key for Joystick X-Axis Deadzone
     */
    private static final String JOYSTICK_DEADZONE_X = "Joystick X-Axis Deadzone";

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

    /**
     * Initialize the SmartDashboard values.
     */
    private void initSmartDashboard() {
        calibrateGyro();

        // Timing settings. These are timed numbers measured as the amount of time it takes
        // the robot to move 10' at the given power level.
        SmartDashboard.putNumber(FULL_POWER_TIME, 1.1);
        SmartDashboard.putNumber(HALF_POWER_TIME, 2.2);
        SmartDashboard.putNumber(ONETHIRD_POWER_TIME, 3.3);

        // Gryo tolerance - used in auto to provide non-perfect directions
        SmartDashboard.putNumber(AUTO_GYRO_TOLERANCE, 5);

        autoSpeedChooser = new SendableChooser();
        autoSpeedChooser.addDefault("Full Speed", FULL_SPEED_VALUE);
        autoSpeedChooser.addObject("Half-Speed", HALF_SPEED_VALUE);
        autoSpeedChooser.addObject("1/3rd Speed", ONETHIRD_SPEED_VALUE);
        SmartDashboard.putData("Autonomous Speed Selection", autoSpeedChooser);

        SmartDashboard.putNumber(JOYSTICK_DEADZONE_X, 0.1);
        SmartDashboard.putNumber(JOYSTICK_DEADZONE_Y, 0.1);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new Drive(10));
    }

    public void calibrateGyro() {
        digitalGyro = RobotMap.driveControlDigitalGyro;
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
        SmartDashboard.putNumber("Gyro PID Output", gyroPID.get());
        SmartDashboard.putNumber("Left PID Output", leftDrivePIDController.get());
        SmartDashboard.putNumber("Right PID Output", rightDrivePIDController.get());
    }


}
