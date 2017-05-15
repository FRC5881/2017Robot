package org.techvalleyhigh.frc5881.steamworks.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.steamworks.robot.RobotMap;

/**
 * Created by ijensen on 5/12/2017.
 */
public class Agitate extends Command {
    public class Shoot extends Command {
        public Shoot() {
            super();
        }

        public Shoot(String name) {
            super(name);
        }

        @Override
        protected void execute() {
            RobotMap.agitatorTalon.setPID(1, 0, 0);
        }

        @Override
        protected void interrupted() {
            super.interrupted();
            end();
        }

        @Override
        protected void initialize() {

            //super.initialize();
        }

        @Override
        protected boolean isFinished() {
            return false;
        }

        @Override
        protected void end() {
            //Robot.Agitate.spinStop();
        }
    }


}
