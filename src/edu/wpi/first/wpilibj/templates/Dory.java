/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
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
//    LogitechF310 controller2 = new LogitechF310(2);
//    LogitechF310 controller3 = new LogitechF310(3);
    CANJaguar leftRearCan;
    CANJaguar rightRearCan;
    CANJaguar leftFrontCan;
    CANJaguar rightFrontCan;
    private double vBus = 0.0;
    private double kCurr = 0.0;
    private double kSpeed = 0.0;
    private double kPos = 0.0;
    private double kVolt = 0.0;
    private RobotDrive drive;

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        SetDashboardData();
        try {
            rightFrontCan = new CANJaguar(6);
            leftFrontCan = new CANJaguar(5);
            rightRearCan = new CANJaguar(4);

            leftRearCan = new CANJaguar(3);
            drive = new RobotDrive(leftFrontCan, leftRearCan, rightFrontCan, rightRearCan);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }

        while (isEnabled() && isOperatorControl()) {

            DriverTest();
            /*
             * System.out.println("while loop \n"); if
             * (SmartDashboard.getInt("joystick_mode") == 1) {
             * System.out.println("Joystick Mode : " +
             * SmartDashboard.getInt("joystick_mode") + " \n"); int motorSel =
             * SmartDashboard.getInt("joystick_motor"); if (motorSel == 1) {
             *
             * JaguarMotorTest(leftRearCan); } else if (motorSel == 2) {
             * JaguarMotorTest(rightRearCan); } else if (motorSel == 3) {
             * JaguarMotorTest(leftFrontCan); } else if (motorSel == 4) {
             * JaguarMotorTest(rightFrontCan); } } else if
             * (SmartDashboard.getInt("joystick_mode") == 2) {
             *
             * }
             * // double mode = SmartDashboard.getDouble("Comtrol_Mode"); //
             * System.out.println("mode =" + mode);
             *
             */
        }
    }

    private void SetDashboardData() {
        /*
         * AxisCamera camera = AxisCamera.getInstance("10.23.40.11");
         * AxisCamera.ResolutionT res = camera.getResolution(); double xRes =
         * res.width; double fovAngleInRad = (24.5 * Math.PI) / 180.0;
         */
        System.out.println("Sending dashboard data \n");
        /*
         * SmartDashboard.putInt("Hue Low", 0); SmartDashboard.putInt("Hue
         * High", 255); SmartDashboard.putInt("Sat Low", 0);
         * SmartDashboard.putInt("Sat High", 255);
         * SmartDashboard.putInt("Intensity Low", 0);
         * SmartDashboard.putInt("Intensity High", 255);
         */ SmartDashboard.putInt("Control_Mode", 0);
        SmartDashboard.putDouble("KPercentVbus_mode", 0);
        SmartDashboard.putDouble("KCurrent_mode", 0);
        SmartDashboard.putDouble("KSpeed_mode", 0);
        SmartDashboard.putDouble("KPostion_mode", 0);
        SmartDashboard.putDouble("KVoltage_mode", 0);
        SmartDashboard.putInt("joystick_mode", 1);
        SmartDashboard.putInt("joystick_motor", 1);
    }

    private void JaguarMotorTest(CANJaguar selectedMotor) {
        /*
         * if(camera.freshImage()) { try { ColorImage color = camera.getImage();
         * int hueLow = SmartDashboard.getInt("Hue Low", 0); int hueHigh =
         * SmartDashboard.getInt("Hue High", 255); int satLow =
         * SmartDashboard.getInt("Sat Low", 0); int satHigh =
         * SmartDashboard.getInt("Sat High", 255); int intensityLow =
         * SmartDashboard.getInt("Intensity Low", 0); int intensityHigh =
         * SmartDashboard.getInt("Intensity High", 255);
         * System.out.println(hueLow+ ", " +hueHigh+ ", " +satLow+ ", "
         * +satHigh+ ", " +intensityLow+ "," +intensityHigh); BinaryImage binary
         * = color.thresholdHSI(hueLow, hueHigh, satLow, satHigh, intensityLow,
         * intensityHigh); color.free(); BinaryImage hulled =
         * binary.convexHull(true); binary.free(); CriteriaCollection cc = new
         * CriteriaCollection();
         * cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA_BY_IMAGE_AREA,
         * 6.0f, 7.0f, true);
         * cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_RATIO_OF_EQUIVALENT_RECT_SIDES,
         * 0f, 1.1f, true); BinaryImage filtered = hulled.particleFilter(cc);
         * hulled.free(); ParticleAnalysisReport[] reports =
         * filtered.getOrderedParticleAnalysisReports(); filtered.free(); float
         * idealRatio = 24.0f / 18.0f;
         *
         * System.out.println("found " + reports.length + " shapes"); for(int i
         * = 0; i < reports.length; ++i) { ParticleAnalysisReport report =
         * reports[i]; float width = report.boundingRectWidth; float height =
         * report.boundingRectHeight; float ratio = width / height;
         * System.out.println("ratio = " + ratio); if(((ratio < (idealRatio +
         * 0.2)) && (ratio > (idealRatio - 0.2)))) { double fovFT = (2.0 /
         * width) / xRes; double distFromTarget = (fovFT / 2.0) /
         * Math.tan(fovAngleInRad); System.out.println("dist from target = " +
         * distFromTarget); } }
         *
         *
         * } catch (AxisCameraException ex) { ex.printStackTrace(); } catch
         * (NIVisionException ex) { ex.printStackTrace(); } } }
         */
        try {



            int conrolMode = SmartDashboard.getInt("Control_Mode", 0);
            System.out.println("Control Mode: " + conrolMode + "\n");
            if (conrolMode == 0) {
                selectedMotor.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
                //if (vBus != SmartDashboard.getDouble("KPercentVbus_mode", 0)) {

                //}
                vBus = SmartDashboard.getDouble("KPercentVbus_mode", 0);
                System.out.println("CANJag in KPercentVBus: " + vBus);
                selectedMotor.setX(vBus);



            } else if (conrolMode == 1) {
                selectedMotor.changeControlMode(CANJaguar.ControlMode.kCurrent);
                //if (kCurr != SmartDashboard.getDouble("KCurrent_mode", 0)) {

                //}
                kCurr = SmartDashboard.getDouble("KCurrent_mode", 0);
                System.out.println("CANJag in KCurrent_mode: " + kCurr);
                selectedMotor.setX(kCurr);


            } else if (conrolMode == 2) {
                selectedMotor.changeControlMode(CANJaguar.ControlMode.kSpeed);
                if (kSpeed != SmartDashboard.getDouble("KSpeed_mode", 0)) {
                    System.out.println("CANJag in KSpeed_mode: " + kSpeed);
                }
                kCurr = SmartDashboard.getDouble("KSpeed_mode", 0);
                selectedMotor.setX(kSpeed);


            } else if (conrolMode == 3) {
                selectedMotor.changeControlMode(CANJaguar.ControlMode.kPosition);
                if (kPos != SmartDashboard.getDouble("KPostion_mode", 0)) {
                    System.out.println("CANJag in KPostion_mode: " + kPos);
                }
                kCurr = SmartDashboard.getDouble("KPostion_mode", 0);
                selectedMotor.setX(kPos);

            } else if (conrolMode == 4) {
                selectedMotor.changeControlMode(CANJaguar.ControlMode.kVoltage);
                if (kVolt != SmartDashboard.getDouble("KVoltage_mode", 0)) {
                    System.out.println("CANJag in KVoltage_mode: " + kVolt);
                }
                kCurr = SmartDashboard.getDouble("KVoltage_mode", 0);
                selectedMotor.setX(kVolt);

            }


            // leftRearCan.
            //Encoder motorOneEncoder = new Encoder
        } catch (CANTimeoutException ex) {
            System.out.println("CANJaguar Exception");
            ex.printStackTrace();
        }
    }

    private void DriverTest() {
        //System.out.println("in TankDrive mode");
        controller1.printState();
        try {
            double yValue = controller1.getLeftStick().getY();
            double xValue = controller1.getLeftStick().getX();

            double mag = Math.sqrt(yValue * yValue + xValue * xValue);
            if (yValue > 0 && xValue == 0) {
                // Forward
                Watchdog.getInstance().feed();
                rightFrontCan.setX(yValue);
                leftFrontCan.setX(-1 * yValue);
                rightRearCan.setX(yValue);
                leftRearCan.setX(-1 * yValue);

            } else if (yValue > 0 && xValue > 0) {
                // 45 diagional front right
                Watchdog.getInstance().feed();
                rightFrontCan.setX(0);
                leftFrontCan.setX(mag * -1);
                rightRearCan.setX(mag);
                leftRearCan.setX(0);

            } else if (yValue == 0 && xValue > 0) {
                // Right
                Watchdog.getInstance().feed();
                rightFrontCan.setX(xValue * -1);
                leftFrontCan.setX(-1 * xValue);
                rightRearCan.setX(xValue);
                leftRearCan.setX(xValue);

            } else if (yValue < 0 && xValue > 0) {
                Watchdog.getInstance().feed();


                rightFrontCan.setX(mag);
                leftFrontCan.setX(0);
                rightRearCan.setX(0);
                leftRearCan.setX(mag * -1);

            } else if (yValue < 0 && xValue == 0) {
                Watchdog.getInstance().feed();


                rightFrontCan.setX(yValue);
                leftFrontCan.setX(yValue * -1);
                rightRearCan.setX(yValue);
                leftRearCan.setX(yValue * -1);

            } else if (yValue < 0 && xValue < 0) {
                Watchdog.getInstance().feed();


                rightFrontCan.setX(0);
                leftFrontCan.setX(mag * -1);
                rightRearCan.setX(mag);
                leftRearCan.setX(0);

            } else if (yValue == 0 && xValue < 0) {
                Watchdog.getInstance().feed();


                rightFrontCan.setX(xValue * -1);
                leftFrontCan.setX(xValue * -1);
                rightRearCan.setX(xValue);
                leftRearCan.setX(xValue);

            } else if (xValue < 0 && yValue > 0) {
                Watchdog.getInstance().feed();


                rightFrontCan.setX(mag * -1);
                leftFrontCan.setX(0);
                rightRearCan.setX(0);
                leftRearCan.setX(mag);

            } else if (controller1.getDPad().getX() > 0) {
                Watchdog.getInstance().feed();
                rightFrontCan.setX(-1);
                leftFrontCan.setX(-1);
                rightRearCan.setX(-1);
                leftRearCan.setX(-1);


            } else if (controller1.getDPad().getX() < 0) {
                Watchdog.getInstance().feed();
                rightFrontCan.setX(1);
                leftFrontCan.setX(1);
                rightRearCan.setX(1);
                leftRearCan.setX(1);


            } else {
                Watchdog.getInstance().feed();


                System.out.println("STOP !!");

                rightFrontCan.setX(0);
                leftFrontCan.setX(0);
                rightRearCan.setX(0);
                leftRearCan.setX(0);

            }



        } catch (Exception e) {
        }

        Watchdog.getInstance().feed();


        Watchdog.getInstance().feed();
        //drive.tankDrive(controller1.getLeftStick().getY(), controller1.getRightStick().getY());
        //drive.mecanumDrive_Cartesian(controller1.getLeftStick().getY(), controller1.getRightStick().getY(), 0, 0);

        Watchdog.getInstance().feed();



    }
}
