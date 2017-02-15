package org.techvalleyhigh.frc5881.steamworks.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

// TODO Need Javadoc comment
public class RobotMap {

    public static ADXRS450_Gyro digitalGyro;
    public static Encoder driveControlRightEncoder;
    public static Encoder driveControlLeftEncoder;

    //Talons for tank drive
    public static CANTalon talonFrontLeft;
    public static CANTalon talonBackLeft;
    public static CANTalon talonFrontRight;
    public static CANTalon talonBackRight;

    // Talons (and encoders) for the shooter
    public static CANTalon shooterBottomTalon;
    public static CANTalon shooterTopTalon;
    public static Encoder shooterTopEncoder;
    public static Encoder shooterBottomEncoder;


    // TODO: Define the talon for intake
    // TODO: Define the encoder for intake ??

    // TODO: Define ultrasonic for testing

    public static void init() {
        //Tank drive
        talonFrontLeft = new CANTalon(1);
        LiveWindow.addActuator("Front Left", "CANTALON", talonFrontLeft);
        talonBackLeft = new CANTalon(2);
        LiveWindow.addActuator("Back Left", "CANTALON", talonBackLeft);
        talonFrontRight = new CANTalon(3);
        LiveWindow.addActuator("Front Right", "CANTALON", talonFrontRight);
        talonBackRight = new CANTalon(4);
        LiveWindow.addActuator("Back Right", "CANTALON", talonBackRight);


        //Shooter
        shooterTopTalon = new CANTalon(5);
        LiveWindow.addActuator("Shooter Top", "CANTalon", shooterTopTalon);
        shooterBottomTalon = new CANTALON(6);
        Livewindow.addActuator("Shooter Bottom", "CANTalon", shooterBottomTalon);

        shooterTopEncoder = new Encoder(4, 5);
        LiveWindow.addSensor("Shooter", "Encoder", shooterTopEncoder);
        shooterBottomEncoder = new Encoder(6, 7);
        LiveWindow.addSensor("Shooter", "Encoder", shooterBottomEncoder);

        //Gyro
        digitalGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
        LiveWindow.addSensor("Drive Control", "DigitalGyro", digitalGyro);

        //Drive encoders
        driveControlLeftEncoder = new Encoder(0, 1);
        driveControlRightEncoder = new Encoder(2, 3);

        //pulse per rotation = 1440, Circumference = 18.84954
        driveControlLeftEncoder.setDistancePerPulse((18.84954d / 1440d)*4);
        LiveWindow.addSensor("Drive Control", "Left Encoder", driveControlLeftEncoder);
        driveControlRightEncoder.setDistancePerPulse((18.84954d / 1440d)*4);
        LiveWindow.addSensor("Drive Control", "Right Encoder", driveControlRightEncoder);
    }
}