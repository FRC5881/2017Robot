package org.techvalleyhigh.frc5881.steamworks.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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

    //Intake
    public static CANTalon intakeTalon;
    public static Encoder intakeEncoder;

    // Climber
    public static PWMSpeedController climbTalon1;
    public static PWMSpeedController climbTalon2;

    //Drive Control
    public static RobotDrive robotDrive;

    // Ultrasonic
    public static AnalogInput ultrasonic;

    // TODO: Define ultrasonic for testing

    public static void init() {
        // Talons First

        // Tank drive
        talonFrontLeft = new CANTalon(5);
        LiveWindow.addActuator("Drive Control", "Front Left Drive Talon", talonFrontLeft);
        talonBackLeft = new CANTalon(6);
        LiveWindow.addActuator("Drive Control", "Back Left Drive Talon", talonBackLeft);
        talonFrontRight = new CANTalon(3);
        LiveWindow.addActuator("Drive Control", "Front Right Drive Talon", talonFrontRight);
        talonBackRight = new CANTalon(4);
        LiveWindow.addActuator("Drive Control", "Back Right Drive Talon" , talonBackRight);

        // Shooter
        shooterTopTalon = new CANTalon(1);
        LiveWindow.addActuator("Shooter", "Top Talon", shooterTopTalon);
        shooterBottomTalon = new CANTalon(2);
        LiveWindow.addActuator("Shooter", "Bottom Talon", shooterBottomTalon);

        // Intake
        intakeTalon = new CANTalon(7);
        LiveWindow.addActuator("Intake", "Intake Talon", intakeTalon);

        // Climber
        climbTalon1 = new TalonSRX(1);
        climbTalon2 = new TalonSRX(2);

        // Encoders

        // Drive encoders
        driveControlLeftEncoder = new Encoder(0, 1);
        driveControlRightEncoder = new Encoder(2, 3);
        //pulse per rotation = 1440, Circumference = 18.84954
        driveControlLeftEncoder.setDistancePerPulse((18.84954d / 1440d)*4);
        LiveWindow.addSensor("Drive Control", "Left Drive Encoder", driveControlLeftEncoder);
        driveControlRightEncoder.setDistancePerPulse((18.84954d / 1440d)*4);
        LiveWindow.addSensor("Drive Control", "Right Drive Encoder", driveControlRightEncoder);

        //Intake
        intakeEncoder = new Encoder(4, 5);
        LiveWindow.addSensor("Intake", "Intake Encoder", intakeEncoder);

        // Gyro
        digitalGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
        LiveWindow.addSensor("Drive Control", "DigitalGyro", digitalGyro);

        // Ultrasonic
        ultrasonic = new AnalogInput(0);
        LiveWindow.addSensor("Ultrasonic", "Ultrasonic", ultrasonic);

    }
}