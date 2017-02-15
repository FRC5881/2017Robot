package org.techvalleyhigh.frc5881.steamworks.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

// TODO Need Javadoc comment
public class RobotMap {

    //Gyro
    public static ADXRS450_Gyro digitalGyro;

    //Talons and encoders for tank drive
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

    // TODO: Define ultrasonic for testing

    public static void init() {
        //Tank drive
        talonFrontLeft = new CANTalon(1);
        LiveWindow.addActuator("Drive Control", "Front Left Talon", talonFrontLeft);
        talonBackLeft = new CANTalon(2);
        LiveWindow.addActuator("Drive Control", "Bottom Left Talon", talonBackLeft);
        talonFrontRight = new CANTalon(3);
        LiveWindow.addActuator("Drive Control", "Front Right Talon", talonFrontRight);
        talonBackRight = new CANTalon(4);
        LiveWindow.addActuator("Drive Control", "Bottom Right Talon" , talonBackRight);

        //Shooter
        shooterTopTalon = new CANTalon(5);
        LiveWindow.addActuator("Shooter", "CANTalon", shooterTopTalon);
        shooterBottomTalon = new CANTalon(6);
        LiveWindow.addActuator("Shooter", "CANTalon", shooterBottomTalon);

        //Gyro
        digitalGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
        LiveWindow.addSensor("Drive Control", "DigitalGyro", digitalGyro);

        //Drive encoders
        driveControlLeftEncoder = new Encoder(0, 1);
        driveControlRightEncoder = new Encoder(2, 3);

        //Intake
        intakeEncoder = new Encoder(4, 5);
        LiveWindow.addSensor("Intake", "Encoder", intakeEncoder);
        intakeTalon = new CANTalon(7);
        LiveWindow.addActuator("Intake", "Talon", intakeTalon);

        //pulse per rotation = 1440, Circumference = 18.84954
        driveControlLeftEncoder.setDistancePerPulse((18.84954d / 1440d)*4);
        LiveWindow.addSensor("Drive Control", "Left Encoder", driveControlLeftEncoder);
        driveControlRightEncoder.setDistancePerPulse((18.84954d / 1440d)*4);
        LiveWindow.addSensor("Drive Control", "Right Encoder", driveControlRightEncoder);
    }
}