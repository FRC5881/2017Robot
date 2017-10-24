package org.techvalleyhigh.frc5881.steamworks.robot.commands;

public class ArcPathing extends Command {
    /**
     * Width of the bot (from wheel edge to wheel edge)
     * In inches
     */
    private static double botWidth = 30;

    /**
     * Horizontal component of curve
     */
    public double x;

    /**
     * Vertical component of curve
     */
    public double y;

    /**
     * Target speed of the bot while pathing.
     */
    public double speed;

    private DriveControl driveControl;

    private PIDController leftDrivePIDController;
    private PIDController rightDrivePIDController;


    public ArcPathing(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        requires(Robot.driveControl);
        driveControl = Robot.driveControl;
    }

    @Override
    protected void initialize() {
        //Reset the encoders
        RobotMap.driveControlLeftEncoder.reset();
        RobotMap.driveControlRightEncoder.reset();

        //Set up PID controllers
        leftDrivePIDController = new PIDController(driveControl.getLeftPIDKp(), driveControl.getLeftPIDKi(),
                driveControl.getLeftPIDKd(), RobotMap.driveControlLeftEncoder,
                new PIDOutput() {
                    @Override
                    public void pidWrite(double output) {
                        leftDrivePIDOutput = output;
                    }
                });
        rightDrivePIDController = new PIDController(driveControl.getRightPIDKp(), driveControl.getRightPIDKi(),
                driveControl.getRightPIDKd(), RobotMap.driveControlRightEncoder,
                new PIDOutput() {
                    @Override
                    public void pidWrite(double output) {
                        rightDrivePIDOutput = output;
                    }
                });
        //Limit PID output range to motor control values
        leftDrivePIDController.setOutputRange(-1 * drivePower, drivePower);
        rightDrivePIDController.setOutputRange(-1 * drivePower, drivePower);

        //Set distances to travel in inches
        double[] setPoints = this.findSetPoints();
        rightDrivePIDController.setSetpoint(setPoints[0]);
        leftDrivePIDController.setSetpoint(setPoints[1]);

        //Enable
        leftDrivePIDController.enable();
        rightDrivePIDController.enable();
    }

    /**
     * Find the nessaccary percentages to send to near and far side motors (farSide will be greater than 100%)
     * To turn an arc described by a right trianle x, y
     * Then finds the this distances each set of wheels needs to travel.
     * If x is positive the robot turns towards the right, if x is negative the robot turns towards the left.
     * See Curve Path Math.png in root folder for geometry
     * @param x
     * @param y
     * @return [right side setpoint, left side setpoint]
     */
    private double[] findSetPoints() {
        //Geometry
        double h = Math.sqrt(this.x * this.x + this.y * this.y);
        double alpha = Math.atan(this.y / this.x);
        double C = Math.PI - 2 * alpha;
        double r = Math.sin(alpha) * h / Math.cos(C);
        double d = C * r; //Arc length

        if(x > 0) {
            double rightSide = (r - this.botWidth / 2) / r;
            double leftSide = (r + this.botWidth / 2) / r;
        } else {
            double leftSide = (r - this.botWidth / 2) / r;
            double rightSide = (r + this.botWidth / 2) / r;
        }

        double[] out = [rightSide, leftSide];
        return out;
    }
}