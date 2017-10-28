package org.techvalleyhigh.frc5881.steamworks.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.vision.VisionCamera;

// TODO Need Javadoc comment
public class RobotMap {
    //Gyro
    public static ADXRS450_Gyro digitalGyro;

    //Talons and encoders for arcade drive
    public static CANTalon talonFrontLeft;
    public static CANTalon talonBackLeft;
    public static CANTalon talonFrontRight;
    public static CANTalon talonBackRight;
    public static Encoder driveControlRightEncoder;
    public static Encoder driveControlLeftEncoder;

    //Talons for the shooter
    public static CANTalon shooterBottomTalon;
    public static CANTalon shooterTopTalon;

    //Exhaust
    public static TalonSRX exhaustTalon;
    public static Encoder exhaustEncoder;

    // Climber
    public static CANTalon climbTalon1;
    public static CANTalon climbTalon2;

    //Drive Control
    public static RobotDrive robotDrive;

    //Ultrasonic
    public static AnalogInput ultrasonic;

    //Vision Camera
    public static VisionCamera visionCamera;

    public static void init() {
        // Talons First

        // Tank drive
        talonFrontLeft = new CANTalon(4);
        LiveWindow.addActuator("Drive Control", "Front Left Drive Talon", talonFrontLeft);
        talonBackLeft = new CANTalon(5);
        LiveWindow.addActuator("Drive Control", "Back Left Drive Talon", talonBackLeft);
        talonFrontRight = new CANTalon(2);
        LiveWindow.addActuator("Drive Control", "Front Right Drive Talon", talonFrontRight);
        talonBackRight = new CANTalon(3);
        LiveWindow.addActuator("Drive Control", "Back Right Drive Talon" , talonBackRight);


        // Shooter
        shooterTopTalon = new CANTalon(1);
        shooterTopTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        shooterTopTalon.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterTopTalon.reverseSensor(false);
        shooterTopTalon.configNominalOutputVoltage(0f, 0f);
        shooterTopTalon.configPeakOutputVoltage(12f, -12f);
        shooterTopTalon.setProfile(0);
        shooterTopTalon.setPID(1, 0, 0);
        shooterTopTalon.setCloseLoopRampRate(48);
        shooterTopTalon.enableControl();
        LiveWindow.addActuator("Shooter", "Top Talon", shooterTopTalon);
        shooterBottomTalon = new CANTalon(2);
        shooterBottomTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        shooterBottomTalon.changeControlMode(CANTalon.TalonControlMode.Speed);
        shooterBottomTalon.reverseSensor(true);
        shooterBottomTalon.configNominalOutputVoltage(0f, 0f);
        shooterBottomTalon.configPeakOutputVoltage(12f, -12f);
        shooterBottomTalon.setProfile(0);
        shooterBottomTalon.setPID(1, 0, 0);
        shooterBottomTalon.setCloseLoopRampRate(48);
        shooterBottomTalon.enableControl();
        LiveWindow.addActuator("Shooter", "Bottom Talon", shooterBottomTalon);

        // Exhaust
        exhaustTalon = new TalonSRX(0);
        LiveWindow.addActuator("Exhaust", "Exhaust Talon", exhaustTalon);

        // Climber
        climbTalon1 = new CANTalon(1);
        LiveWindow.addActuator("Climber", "Climber 1", climbTalon1);
        climbTalon2 = new CANTalon(6);
        LiveWindow.addActuator("Climber", "Climber 2", climbTalon2);

        // Encoders

        // Drive encoders
        driveControlLeftEncoder = new Encoder(0, 1);
        driveControlRightEncoder = new Encoder(2, 3);
        //pulse per rotation = 1440, Circumference = 18.84954
        driveControlLeftEncoder.setDistancePerPulse((18.84954d / 1440d)*4); // adjust factor
        driveControlRightEncoder.setReverseDirection(true);
        LiveWindow.addSensor("Drive Control", "Left Drive Encoder", driveControlLeftEncoder);
        driveControlRightEncoder.setDistancePerPulse((18.84954d / 1440d)*4);
        LiveWindow.addSensor("Drive Control", "Right Drive Encoder", driveControlRightEncoder);

        //Exhaust
        exhaustEncoder = new Encoder(4, 5);
        LiveWindow.addSensor("Exhaust", "Exhaust Encoder", exhaustEncoder);

        //Gyro
        digitalGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
        LiveWindow.addSensor("Drive Control", "DigitalGyro", digitalGyro);

        //Ultrasonic
        ultrasonic = new AnalogInput(0);
        LiveWindow.addSensor("Ultrasonic", "Ultrasonic", ultrasonic);

        //Vision Camera(s) see Camera Util.png for displacement
        //TODO: Find Vertical Field of View
        visionCamera = new VisionCamera(
                640, 480,
                50.4, 100,
                0,0
        );
    }
}