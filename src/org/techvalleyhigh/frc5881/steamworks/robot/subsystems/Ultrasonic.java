package org.techvalleyhigh.frc5881.steamworks.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;

/**
 * Created by brianr on 2/17/17.
 */
public class Ultrasonic extends Subsystem {
    /*
     * HRLV-MaxSonar-EZ MB1013 - On Analog port, Voltage input. per spec 4.88mV/5mm distance
     */

    public Ultrasonic() {
        super();
        init();
    }

    public Ultrasonic(String name) {
        super(name);
        init();
    }

    private void init() {
    }

    @Override
    protected void initDefaultCommand() {
        // No commands, this is a read-only subsystem
    }

    public double getDistance() {
        return RobotMap.ultrasonic.getVoltage();
    }
}
