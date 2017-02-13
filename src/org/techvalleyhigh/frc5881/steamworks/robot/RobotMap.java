package org.techvalleyhigh.frc5881.steamworks.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class RobotMap {

    public static ADXRS450_Gyro digitalGyro;

    // TODO: Define the talons for drive

    // TODO: Define the talons for the shooter

    // TODO: Define the talon for intake
    // TODO: Define the encoder for intake ??

    // TODO: Define ultrasonic for testing

    public static void init() {
        digitalGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
        LiveWindow.addSensor("Drive Control", "DigitalGyro", digitalGyro);
    }
}
