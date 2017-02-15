package org.techvalleyhigh.frc5881.steamworks.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
import com.ctre.CANTalon;
import com.ctre.CANTalon.*;

/**
 * Created by HHoyt on 2/4/2017.
 */
public abstract class Shooter extends Subsystem {

    /**
     * String used for SmartDashboard key for max shooter distance
     */
    private static final String MAX_SHOOTER_DISTANCE = "Max Shooter Distance";

    /**
     * String used for SmartDashboard key for min shooter distance
     */
    private static final String MIN_SHOOTER_DISTANCE = "Min Shooter Distance";

    /**
     * String used for SmartDashboard key for Absulute RPM Tolerance
     */
    private static final String RPM_TOLERANCE = "RPM Tolerance";

    // Motor controllers
    private static final CANTalon shooterTopTalon = RobotMap.shooterTopTalon;
    private static final CANTalon shooterBottomTalon = RobotMap.shooterBottomTalon;

    // TODO: Find Minium and Maxium distances and angle tolerance (make the shooter)
    /**
     * Initialize shooter SmartDashboard values
     */
    private void initSmartDashboard() {
        SmartDashboard.putNumber(MAX_SHOOTER_DISTANCE, 10);
        SmartDashboard.putNumber(MIN_SHOOTER_DISTANCE, 10);
        SmartDashboard.putNumber(RPM_TOLERANCE, 50);
    }

    // TODO: Velocity PID

    /**
     * Given a distance and angle difference outputs true or false on wether
     * or not the robot is "clear" to shoot based on min and max distance and
     * angle tolerance
     * @param distance Horizontal gistance to goal
     * @param angleDifference Absolute angle difference to the goal
     * @param bottomMotorRPM the RPM of the bottom motor
     * @param topMotorRPM the RPM of the top motor
     * @return true or false
     */
    public boolean readyToShootPosition(double distance, double angleDifference) {
       return    (distance < SmartDashboard.getNumber(MAX_SHOOTER_DISTANCE, 10)
               && distance > SmartDashboard.getNumber(MIN_SHOOTER_DISTANCE, 10)
               && angleDifference < smartDashboard.getNumber(SHOOT_ANGLE_TOLERANCE, 5));
    }

    /**
     * True or False bottom motor is at required speed
     * @param currentRPM Current Rotations Per Minute
     * @param requiredRPM Required Rotations Per Minute
     * @return boolean
     */
    public boolean readyToShootBottomMotor(double currentRPM, double requiredRPM) {
        return (Math.abs(currentRPM - requiredRPM) < SmartFashboard.getNumber(RPM_TOLERANCE. 50))
    }

    // TODO: Calculate RPM required to score and add TrigUtil function to add for camera displacement?

    /**
     * Calculates the RPM required to score, with pyshics, including camera displacement
     * @param distance Distance from camera to target
     * @return
     */
    public double rpmToScore(double distance) {

    }

    /**
     * Spins the motors to score
     */
    public void spinToScore() {
        //Call vision to find distance and angle difference
        //Call rpmToScore to rpm required
        //Tranlate RPM's to motor speed (bewteen 0 - 1)
        //Spin bottom motor to speed
        //Spin second motor to speed once bottom motor reachs speed

        double distance;
        double angle;
        double rpmToScore;
        double value;

        if(readyToShootPosition(distance, angle)) {
            if(shooterBottomTalon.get() > 0) {
                if (readyToShootBottomMotor(bottomTalon.getRPMs(), rpmToScore)) {
                    shooterTopTalon.set(value)
                } else {
                    shooterBottomTalon.set(value)
                }
            }
        } else {
           //Call Assisted Drive To Line Up Shot
        }
    }

    // TODO: Need to make function to stop shooting
    public void spinStop() {
        shooterBottomTalon.set(0);
        shooterTopTalon.set(0);
    }
}