package org.techvalleyhigh.frc5881.steamworks.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Drive;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.Chassis;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.DriveControl;
import org.techvalleyhigh.frc5881.steamworks.robot.subsystems.Shooter;

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
    public static Shooter shooter;
    public static Chassis chassis;
    public static Drive driveCommand;
    public static SendableChooser autoChooser;
    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        RobotMap.init();
        driveControl = new DriveControl();
        chassis = new Chassis();
        shooter = new Shooter() {
            @Override
            protected void initDefaultCommand() {

            }
        };
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        autonomousCommand = null;

        driveCommand = new Drive(10);

        // TODO: Need to set some parameters for autonomous. Are we aiming for center or L/R pegs, is boiler behind, left, right, or opposite side?
        autoChooser = new SendableChooser();
        /*
        autoChooser.addDefault("Reach Defense", new AutonomousCommand("reach"));
        autoChooser.addObject("Gun It & Breech Defense", new AutonomousCommand("gunit"));
        autoChooser.addObject("Gun it & Breech Moat", new AutonomousCommand("gunit-moat"));
        autoChooser.addObject("Gun it & Breech Rock Wall", new AutonomousCommand("gunit-rockwall"));
        autoChooser.addObject("Spy-Bot Low Score", new AutonomousCommand("spyscore"));
        autoChooser.addObject("Breech Defense to Left", new AutonomousCommand("breechleft"));
        autoChooser.addObject("Breech Defense to Right", new AutonomousCommand("breechright"));
        autoChooser.addObject("NONE", new AutonomousCommand("null"));

        SmartDashboard.putData("Autonomous Mode Selection", autoChooser);

        SmartDashboard.putData(Scheduler.getInstance());
        */
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