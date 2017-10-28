package org.techvalleyhigh.frc5881.steamworks.robot.utils.vision;

/**
 * Created by cmahoney on 10/28/2018.
 *
 * This helper class is used to store helpful information about vision cameras
 */
public class VisionCamera {
    /**
     * Horizontal Quality of the image
     */
    public double horizontalQuality;

    /**
     * Vertical Quality of the image
     */
    public double verticalQuality;

    /**
     * Horizontal Field of View of the camera
     */
    public double horizontalFieldOfViewDegrees;

    /**
     * Horizontal Field of View of the camera
     */
    public double verticalFieldOfViewDegrees;

    /**
     * Displacements see Camera Util.png in pictures
     */
    public double displacementX;
    public double displacementY;

    /**
     * Field of view per pixel
     */
    public double horizontalDegreesPerPixel;
    public double verticalDegreesPerPixel;

    /**
     * This class stores A LOT of information
     * @param horizontalQuality
     * @param verticalQuality
     * @param horizontalFieldOfViewDegrees
     * @param verticalFieldOfViewDegrees
     * @param displacementX
     * @param displacementY
     */
    public VisionCamera(double horizontalQuality, double verticalQuality, double horizontalFieldOfViewDegrees, double verticalFieldOfViewDegrees,
                        double displacementX, double displacementY) {
        this.horizontalQuality = horizontalQuality;
        this.verticalQuality = verticalQuality;
        this.horizontalFieldOfViewDegrees = horizontalFieldOfViewDegrees;
        this.verticalFieldOfViewDegrees = verticalFieldOfViewDegrees;
        this.displacementX = displacementX;
        this.displacementY = displacementY;
        this.horizontalFieldOfViewDegrees = this.findHorizontalDegreesPerPixel();
        this.verticalFieldOfViewDegrees = this.findVerticalDegreesPerPixel();
    }

    /**
     * Calculates the horizontal degrees per pixel
     * @return
     */
    private double findHorizontalDegreesPerPixel() {
        return horizontalFieldOfViewDegrees / horizontalQuality;
    }

    /**
     * Calculates the vertical degrees per pixel
     * @return
     */
    private double findVerticalDegreesPerPixel() {
        return verticalFieldOfViewDegrees / horizontalQuality;
    }
}
