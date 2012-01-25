/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team2340;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    
    //SmartDashboard.putData("", controller1);
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {

        boolean gettingThis = false;
        int balls = 2;
        SmartDashboard.putInt(SharedKeyDefinitions.NUM_BALLS_ONBOARD_INT, balls);
        while(isEnabled() && isOperatorControl())
        {
            
                if(controller1.getA())
                    SmartDashboard.putInt(SharedKeyDefinitions.NUM_BALLS_ONBOARD_INT, 123);
                else if(controller1.getB())
                    SmartDashboard.putInt(SharedKeyDefinitions.NUM_BALLS_ONBOARD_INT, 456);
                
                boolean _gettingThis = 
                        SmartDashboard.getBoolean("Are you getting this?", false);
               
                 
                
                if(gettingThis != _gettingThis)
                {
                    gettingThis = _gettingThis;
                    System.out.println("Are you getting this?: " + gettingThis);
                }
                
                AxisCamera camera = AxisCamera.getInstance("10.23.40.11");
                if(camera.freshImage()) {
                    try {
                        ColorImage color = camera.getImage();
                        BinaryImage binary = color.thresholdRGB(0, 240, 210, 255, 75, 255);
                        color.free();
                        BinaryImage convexHulled = binary.convexHull(true);
                        binary.free();
                        CriteriaCollection cc = new CriteriaCollection();
                        cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_RATIO_OF_EQUIVALENT_RECT_SIDES, 1.0f, 1.2f, false);
                        cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_ORIENTATION, 175, 179, false);
                        BinaryImage filtered = convexHulled.particleFilter(cc);
                        convexHulled.free();
                        ParticleAnalysisReport[] reports = filtered.getOrderedParticleAnalysisReports();
                        filtered.free();
                        System.out.println("found " + reports.length + " particles");
                        double ratio = 24.0 / 18.0;
                        for(int index = 0; index < reports.length; ++index) {
                            ParticleAnalysisReport report = reports[index];
                            double width = report.boundingRectWidth;
                            double height = report.boundingRectHeight;
                            double thisRatio = width / height;
                            if(thisRatio < (ratio + 0.075) && thisRatio > (ratio - 0.075)) {
                                System.out.println("found a rectangle with good ratio!" + 
                                        " - (left, top): (" + report.boundingRectLeft + ", " + report.boundingRectTop + ")" +
                                        " (height, width): (" + report.boundingRectHeight + ", " + report.boundingRectWidth + ")");
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
