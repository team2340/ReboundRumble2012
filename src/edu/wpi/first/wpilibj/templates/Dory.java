/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2340.LogitechF310;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */


public class Dory extends SimpleRobot {
    
    LogitechF310 controller1 = new LogitechF310(1);
    LogitechF310 controller2 = new LogitechF310(2);
    LogitechF310 controller3 = new LogitechF310(3);
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {

        AxisCamera camera = AxisCamera.getInstance("10.23.40.11");
        AxisCamera.ResolutionT res = camera.getResolution();
        double xRes = res.width;
        double fovAngleInRad = (24.5 * Math.PI) / 180.0;
        SmartDashboard.putInt("Hue Low", 0);
        SmartDashboard.putInt("Hue High", 255);
        SmartDashboard.putInt("Sat Low", 0);
        SmartDashboard.putInt("Sat High", 255);
        SmartDashboard.putInt("Intensity Low", 0);
        SmartDashboard.putInt ("Intensity High", 255);
        while(isEnabled() && isOperatorControl())
        {
           
           if(camera.freshImage()) 
            {
                try {
                    ColorImage color = camera.getImage();
                    int hueLow = SmartDashboard.getInt("Hue Low", 0);
                    int hueHigh = SmartDashboard.getInt("Hue High", 255);
                    int satLow = SmartDashboard.getInt("Sat Low", 0);
                    int satHigh = SmartDashboard.getInt("Sat High", 255);
                    int intensityLow = SmartDashboard.getInt("Intensity Low", 0);
                    int intensityHigh = SmartDashboard.getInt("Intensity High", 255);
                    System.out.println(hueLow+ ", " +hueHigh+ ", " +satLow+ ", " +satHigh+ ", " +intensityLow+ "," +intensityHigh);
                    BinaryImage binary = color.thresholdHSI(hueLow, hueHigh, satLow, satHigh, intensityLow, intensityHigh);
                    color.free();
                    BinaryImage hulled = binary.convexHull(true);
                    binary.free();
                    CriteriaCollection cc = new CriteriaCollection();
                    cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA_BY_IMAGE_AREA, 6.0f, 7.0f, true);
                    cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_RATIO_OF_EQUIVALENT_RECT_SIDES, 0f, 1.1f, true);
                    BinaryImage filtered = hulled.particleFilter(cc);
                    hulled.free();
                    ParticleAnalysisReport[] reports = filtered.getOrderedParticleAnalysisReports();
                    filtered.free();
                    float idealRatio = 24.0f / 18.0f;
                    
                    System.out.println("found " + reports.length + " shapes");
                    for(int i = 0; i < reports.length; ++i) {
                        ParticleAnalysisReport report = reports[i];
                        float width = report.boundingRectWidth;
                        float height = report.boundingRectHeight;
                        float ratio = width / height;
                        System.out.println("ratio = " + ratio);
                        if(((ratio < (idealRatio + 0.2)) &&
                                (ratio > (idealRatio - 0.2)))) {
                            double fovFT = (2.0 / width) / xRes;
                            double distFromTarget = (fovFT / 2.0) / Math.tan(fovAngleInRad);
                            System.out.println("dist from target = " + distFromTarget);
                        }
                    }
                    
                    
                } catch (AxisCameraException ex) {
                    ex.printStackTrace();
                } catch (NIVisionException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
