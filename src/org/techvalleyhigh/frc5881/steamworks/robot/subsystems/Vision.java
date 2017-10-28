package org.techvalleyhigh.frc5881.steamworks.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.vision.BoilerUtil;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.vision.PegUtil;
import org.techvalleyhigh.frc5881.steamworks.robot.utils.vision.VisionCamera;

/**
 * Created by cmahoney on 10/28/2018
 */
public class Vision extends Subsystem {
    /**
     * String used for SmartDashboard key for peg dead zone
     */
    public static String PEG_DEAD_ZONE = "Peg Dead Zone";

    /**
     * How close the robot can be to the peg
     * and still accurately calculate distances and angles
     * (in inches)
     */
    public static double PEG_DEAD_ZONE_DEFAULT = 40;

    /**
     * String used for SmartDashboard key for peg dead zone
     */
    public static String BOILER_DEAD_ZONE = "Boiler Dead Zone";

    /**
     * How close the robot can be to the boiler
     * and still accurately calculate distance and angles
     * (in inches)
     */
    public static double BOILER_DEAD_ZONE_DEFAULT = 40;


    //We can totally have more than one camera up here
    /**
     * Holds the vision camera
     */
    public VisionCamera camera;

    public Vision(VisionCamera camera) {
        super();
        init();

        //Define each camera
        this.camera = camera;
    }

    public Vision(String name, VisionCamera camera) {
        super(name);
        init();

        //Define each camera
        this.camera = camera;
    }

    public void init() {
        SmartDashboard.putNumber(PEG_DEAD_ZONE, 40);
        SmartDashboard.putNumber(BOILER_DEAD_ZONE, 40);
    }

    @Override
    protected void initDefaultCommand() {
        // No commands, this is a read-only subsystem
    }

    //TODO: Make sure myCountours is not a typo
    /**
     * Returns a PegUtil with all the peg data
     * @return
     */
    public PegUtil takePegSnapShot() {
        return new PegUtil(NetworkTable.getTable("GRIP/myCountours"), camera);
    }

    public BoilerUtil takeBoilerSnapShot() {
        return new BoilerUtil(NetworkTable.getTable("GRIP/myCountours"), camera);
    }
}
