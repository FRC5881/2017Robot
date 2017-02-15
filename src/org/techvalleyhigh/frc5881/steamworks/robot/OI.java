package org.techvalleyhigh.frc5881.steamworks.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Intake;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Shoot;

//TODO: Comment Here
public class OI {

    //TODO: Comment for each of these -- what is it, what does it do. Use JavaDoc comment / ** format (w/o space)
    public GenericHID xboxController;
    public JoystickButton aButton;
    public JoystickButton bButton;
    public JoystickButton xButton;
    public JoystickButton yButton;
    public JoystickButton backButton;
    public JoystickButton startButton;
    public JoystickButton leftBumper;
    public JoystickButton rightBumper;

    public Shoot robotShoot;

    /**
     * Controls Left Tank Drive
     */
    public static int LeftYAxis = 1;
    /**
     * Controls Right Tank Drive
     */
    public static int RightY = 5;

    public static int BUTTON_A = 0;
    public static int BUTTON_B = 1;
    public static int BUTTON_X = 2;
    public static int BUTTON_Y = 3;
    public static int BUTTON_LEFT_BUMPER = 4;
    public static int BUTTON_RIGHT_BUMPER = 5;
    public static int BUTTON_BACK = 6;
    public static int BUTTON_START = 7;


    public OI () {
        xboxController = new Joystick(0);

        // Button 0 == A Button
        // Button 1 == B Button
        // Button 2 == X Button
        // Button 3 == Y Button
        // Button 4 == leftBumper
        // Button 5 == rightBumper
        // Button 6 == backButton
        // Button 7 == startButton


        aButton = new JoystickButton(xboxController, BUTTON_A);
        bButton = new JoystickButton(xboxController, BUTTON_B);
        xButton = new JoystickButton(xboxController, BUTTON_X);
        yButton = new JoystickButton(xboxController, BUTTON_Y);
        leftBumper = new JoystickButton(xboxController, BUTTON_LEFT_BUMPER);
        rightBumper = new JoystickButton(xboxController, BUTTON_RIGHT_BUMPER);
        backButton = new JoystickButton(xboxController, BUTTON_BACK);
        startButton = new JoystickButton(xboxController, BUTTON_START);

        //A toggles intake
        aButton.toggleWhenPressed(new Intake());

        //Bumpers toggle shooter
        //Right toggles on Left toggles off

        rightBumper.whenPressed(robotShoot);
        leftBumper.cancelWhenPressed(robotShoot);
    }
}
