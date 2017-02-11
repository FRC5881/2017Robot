package org.techvalleyhigh.frc5881.steamworks.robot.utils;

import java.math.BigDecimal;

/**
 * Created by ksharpe on 2/11/2017.
 */
public class TrigUtil {

    
    public static double getHypotenuseFromSides(double a, double b) {

        return Math.sqrt(a*a + b*b);
    }

    public static double getSideFromHypotenuseAndSide(double c, double a) {

        return Math.sqrt(a*a - c*c);
    }

    public static double getOppositeFromAngleAndHypotenuse(double c, double theta) {

        double sinOfTheta = Math.sin(theta);

            return c * sinOfTheta;
        }

    public double angleGiven;
    public double distanceGiven;
    public double deadZone = 40;
    double theta = 90 - angleGiven;
    double a = TrigUtil.getOppositeFromAngleAndHypotenuse(distanceGiven, angleGiven);
    double a1 = a - deadZone;
    double b = TrigUtil.getSideFromHypotenuseAndSide(distanceGiven, a);

    public double findDistanceToLineUpWithGear(double deadZone, double distanceGiven, double angleGiven) {

        return TrigUtil.getHypotenuseFromSides(b, a1); //Distance to travel
    }

    public double findAngleToTurnToLineUpWithGear(double deadZone, double distanceGiven, double angleGiven) {

        return Math.atan(a1 / b);
    }
    }
