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
    public static int XBOX_LEFT_STICK_Y_AXIS = 2;
    public static int XBOX_RIGHT_STICK_Y_AXIS = 5;

    public OI() {

        xboxController = new Joystick(0) {
        };

        // Button 1 == A Button
        // Button 2 == B Button
        // Button 3 == X Button
        // Button 4 == Y Button
        // Button 7 == backButton
        // Button 8 == startButton

        aButton = new JoystickButton(xboxController, 1);
        bButton = new JoystickButton(xboxController, 2);
        xButton = new JoystickButton(xboxController, 3);
        yButton = new JoystickButton(xboxController, 4);
        backButton = new JoystickButton(xboxController, 7);
        startButton = new JoystickButton(xboxController, 8);

    }

    public GenericHID getXboxController() {return xboxController;}

}
