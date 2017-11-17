package org.techvalleyhigh.frc5881.steamworks.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTablesJNI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.AutonomousCommand;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Drive;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.Chassis;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.DriveControl;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.Shooter;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.Vision;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static OI oi;
    public static DriveControl driveControl;
    //public static Shooter shooter;
    public static Vision vision;
    public static Chassis chassis;
    public static Drive driveCommand;
    public static SendableChooser autoChooser;
    private Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        RobotMap.init();

        //Create Subsystems
        driveControl = new DriveControl();
        //shooter = new Shooter();
        chassis = new Chassis();

        //Give vision the Vision cameras defined in robot map
        vision = new Vision(RobotMap.visionCamera);


        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        autonomousCommand = null;

        driveCommand = new Drive();

        autoChooser = new SendableChooser();
        autoChooser.addDefault("Do Nothing", new AutonomousCommand("null"));
        autoChooser.addObject("Blue TURN Left", new AutonomousCommand("Blue Left"));
        autoChooser.addObject("Blue TURN Right", new AutonomousCommand("Blue Right"));
        autoChooser.addObject("Red TURN Left" , new AutonomousCommand("Red Left"));
        autoChooser.addObject("Red TURN Right", new AutonomousCommand("Red Right"));
        autoChooser.addObject("Baseline", new AutonomousCommand("Baseline"));

        SmartDashboard.putData("Autonomous Mode Selection", autoChooser);

        SmartDashboard.putData(Scheduler.getInstance());

        CameraServer.getInstance().startAutomaticCapture();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        if (autoChooser.getSelected() != null) {
            autonomousCommand = (Command) autoChooser.getSelected();
            autonomousCommand.start();
        } else {
            System.out.println("Null Auto Chooser");
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();

        if (driveCommand != null) {
            driveCommand.start();
        } else {
            System.err.println("teleopInit() Failed to start Drive command due to null");
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}