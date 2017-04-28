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
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;

/**
 * Created by CMahoney on 2/4/2017.
 */

public class Shooter extends Subsystem {

    /**
     * String used for testing the shooter at different speeds in the smart dashboard
     */
    private static final String SHOOTER_SPEED = "Shooter Speed";

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

    /**
     * String used for SmartDashboard key for shooter angle tolerance
     */
    private static final String SHOOTER_ANGLE_TOLERANCE = "Shooter angle tolerance";

    // TODO: Find Minium and Maxium distances and angle tolerance (make the shooter)
    /**
     * Initialize shooter SmartDashboard values
     */
    private void initSmartDashboard() {
        SmartDashboard.putNumber(MAX_SHOOTER_DISTANCE, 10);
        SmartDashboard.putNumber(MIN_SHOOTER_DISTANCE, 10);
        SmartDashboard.putNumber(RPM_TOLERANCE, 50);
        SmartDashboard.putNumber(SHOOTER_ANGLE_TOLERANCE, 5);
        SmartDashboard.putNumber(SHOOTER_SPEED, 1);
    }

    // TODO: Velocity PID

    /**
     * Given a distance and angle difference outputs true or false on wether
     * or not the robot is "clear" to shoot based on min and max distance and
     * angle tolerance
     * @param distance Horizontal gistance to goal
     * @param angleDifference Absolute angle difference to the goal
     * @return true or false
     */
    public boolean readyToShootPosition(double distance, double angleDifference) {
       return    (distance < SmartDashboard.getNumber(MAX_SHOOTER_DISTANCE, 10)
               && distance > SmartDashboard.getNumber(MIN_SHOOTER_DISTANCE, 10)
               && angleDifference < SmartDashboard.getNumber(SHOOTER_ANGLE_TOLERANCE, 5));
    }

    /**
     * True or False bottom motor is at required speed
     * @param currentRPM Current Rotations Per Minute
     * @param requiredRPM Required Rotations Per Minute
     * @return boolean
     */
    public boolean readyToShootBottomMotor(double currentRPM, double requiredRPM) {
        return (Math.abs(currentRPM - requiredRPM) < SmartDashboard.getNumber(RPM_TOLERANCE, 50));
    }

    // TODO: Calculate RPM required to score and add TrigUtil function to add for camera displacement?

    /**
     * Calculates the RPM required to score, with pyshics, including camera displacement
     * @param distance Distance from camera to target
     * @return
     */

    /*
    public double rpmToScore(double distance) {

    }
     */

    /**
     * Spins the motors to score
     */
    public void spinToScore() {
        //Call vision to find distance and angle difference
        //Call rpmToScore to rpm required
        //Translate RPM's to motor speed (between 0 - 1)
        //Spin bottom motor to speed
        //Spin second motor to speed once bottom motor reachs speed

        double distance;
        double angle;
        double rpmToScore;
        double value;

        /*
        if(readyToShootPosition(distance, angle)) {

            if(shooterBottomTalon.get() > 0) {
                if (readyToShootBottomMotor()) { //finish
                    shooterTopTalon.set(value)
                } else {
                    shooterBottomTalon.set(value)
                }
            }
        } else {
           //Call Assisted Drive To Line Up Shot
        }
        */

        /*if(RobotMap.shooterBottomTalon.get() > 0) {
            RobotMap.shooterTopTalon.set(3000);
        } else {
            RobotMap.shooterBottomTalon.set(3000);
        }

        SmartDashboard.putNumber("TopShooterVoltage", RobotMap.shooterTopTalon.getOutputVoltage()/RobotMap.shooterTopTalon.getBusVoltage());
        SmartDashboard.putNumber("TopShooterSpeed", RobotMap.shooterTopTalon.getSpeed());
        SmartDashboard.putNumber("BottomShooterVoltage", RobotMap.shooterBottomTalon.getOutputVoltage()/RobotMap.shooterBottomTalon.getBusVoltage());
        SmartDashboard.putNumber("BottomShooterSpeed", RobotMap.shooterBottomTalon.getSpeed());
    */}


    public void shoot() {

    
        //setMotorSpeed
    }
    public void spinStop() {
        //RobotMap.shooterBottomTalon.set(0);
        //RobotMap.shooterTopTalon.set(0);
    }

    @Override
    protected void initDefaultCommand() {

    }
}