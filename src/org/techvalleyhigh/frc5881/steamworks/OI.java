package org.techvalleyhigh.frc5881.steamworks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

    public GenericHID xboxController;
    public JoystickButton aButton;
    public JoystickButton bButton;
    public JoystickButton xButton;
    public JoystickButton yButton;
    public JoystickButton backButton;
    public JoystickButton startButton;
    public Joystick joyStickLeft;
    public Joystick joyStickRight;
    public static int LeftY = 1;
    public static int RightY = 5;
    public static int LeftTrigger = 2;
    public static int RightTrigger = 3;

    public OI () {

        xboxController = new Joystick(0);

        // Button 1 == A Button
        // Button 2 == B Button
        // Button 3 == X Button
        // Button 4 == Y Button
        // Button 7 == backButton
        // Button 8 == startButton
        // Button 9 == leftTrigger
        // Button 10 == rightTrigger

        aButton = new JoystickButton(xboxController, 1);
        bButton = new JoystickButton(xboxController, 2);
        xButton = new JoystickButton(xboxController, 3);
        yButton = new JoystickButton(xboxController, 4);
        backButton = new JoystickButton(xboxController, 7);
        startButton = new JoystickButton(xboxController, 8);

    }
}
