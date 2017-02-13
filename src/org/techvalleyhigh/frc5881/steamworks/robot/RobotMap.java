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

    // TODO: Define the talons for drive

    // TODO: Define the talons for the shooter

    // TODO: Define the talon for intake
    // TODO: Define the encoder for intake ??

    // TODO: Define ultrasonic for testing

    public static void init() {
        digitalGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
        LiveWindow.addSensor("Drive Control", "DigitalGyro", digitalGyro);

        driveControlLeftEncoder = new Encoder(0, 1);
        driveControlRightEncoder = new Encoder(2, 3);

        // TODO: Comment these values - what are they -- where did we get them
        driveControlLeftEncoder.setDistancePerPulse((18.84954d / 1440d)*4);
        LiveWindow.addSensor("Drive Control", "Left Encoder", driveControlLeftEncoder);
        driveControlRightEncoder.setDistancePerPulse((18.84954d / 1440d)*4);
        LiveWindow.addSensor("Drive Control", "Right Encoder", driveControlRightEncoder);
    }
}
