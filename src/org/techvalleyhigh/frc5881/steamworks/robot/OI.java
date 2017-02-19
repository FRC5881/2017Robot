package org.techvalleyhigh.frc5881.steamworks.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Intake;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Shoot;
import org.techvalleyhigh.frc5881.steamworks.robot.commands.Climb;

//TODO: Comment Here
public class OI {

    public GenericHID xboxController;
    /**
     * a button is toggle on/off for intake
     */
    public JoystickButton aButton;

    /**
     * b button is not being used as of 2/19
     */
    public JoystickButton bButton;

    /**
     * x button is not being used as of 2/19
     */
    public JoystickButton xButton;

    /**
     * y button is being used for climber. Hold down y to climb
     */
    public JoystickButton yButton;

    /**
     * back button is not being used as of 2/19
     */
    public JoystickButton backButton;

    /**
     * start button is not being used as of 2/19
     */
    public JoystickButton startButton;

    /**
     * left bumper is not being used as og 2/19
     */
    public JoystickButton leftBumper;

    /**
     * right bumper is toggle on/off for shooter
     */
    public JoystickButton rightBumper;

    /**
     * Controls Left joystick, forward/backward for Arcade Drive
     */
    public static int LeftYAxis = 1;
    /**
     * Controls right joystick, Turning For Arcade Drive
     */
    public static int RightXAxis = 4;

    public static int BUTTON_A = 1;
    public static int BUTTON_B = 2;
    public static int BUTTON_X = 3;
    public static int BUTTON_Y = 4;
    public static int BUTTON_LEFT_BUMPER = 5;
    public static int BUTTON_RIGHT_BUMPER = 6;
    public static int BUTTON_BACK = 7;
    public static int BUTTON_START = 8;


    public OI () {
        xboxController = new Joystick(0);

        // Button 1 == A Button
        // Button 2 == B Button
        // Button 3 == X Button
        // Button 4 == Y Button
        // Button 5 == leftBumper
        // Button 6 == rightBumper
        // Button 7 == backButton
        // Button 8 == startButton


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

        //Right Bumper toggles shooter

        rightBumper.toggleWhenPressed(new Shoot());

        // Y when pressed climber
        yButton.whileHeld(new Climb());
    }
}
