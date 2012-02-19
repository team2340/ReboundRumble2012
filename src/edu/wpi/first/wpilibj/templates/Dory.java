/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.networktables.NetworkListener;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.*;
import team2340.Direction;
import team2340.LogitechF310;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Dory extends SimpleRobot {

    public static final String DIFFERENTIAL = "PID: Differential";
    public static final String INTEGRAL = "PID: Integral";
    public static final String PROPORTIONAL = "PID: Proportional";
    public static final String RPM = "Wheel RPM";
    LogitechF310 controller1 = new LogitechF310(1);
    LogitechF310 controller2 = new LogitechF310(2);
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
    private PIDController driveController;
    private double differential;
    private double integral;
    private double proportional;
    // private ShootingData shootingData;
    private CANJaguar shooterCan2;
    private CANJaguar shooterCan1;
    static int ENCODER_TICKS1 = 360;
    static int ENCODER_TICKS2 = 250;
    private double RPMCONSTANT = 990.0;
    double shootingValue = 0.0;
    private int wheelRPMs = 0;
    private AnalogChannel sonarSensor;
    //private Victor finController = new Victor (10);
    private CANJaguar finOnJag;
    boolean finValue =controller1.getLT();
     
    

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
            proportional = SmartDashboard.getDouble(PROPORTIONAL);
            integral = SmartDashboard.getDouble(INTEGRAL);
            differential = SmartDashboard.getDouble(DIFFERENTIAL);
        } catch (NetworkTableKeyNotDefined ex) {
            ex.printStackTrace();
        }


        try {
            //sonarSensor = new AnalogChannel(1,3);
            shooterCan1 = new CANJaguar(7);
            shooterCan2 = new CANJaguar(8);

            rightFrontCan = initializeCanDriver(5, ENCODER_TICKS1);
            rightRearCan = initializeCanDriver(3, ENCODER_TICKS1);
            //leftFrontCan = initializeCanDriver(6, ENCODER_TICKS2);
            finOnJag = new CANJaguar(6);
           
           leftRearCan = initializeCanDriver(4, ENCODER_TICKS2);









        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }

        System.out.println( "starting encoder test");  
        while (isEnabled() && isOperatorControl()) {

            //DriverTest();
            
            //EncoderTest(leftFrontCan,leftRearCan, rightFrontCan, rightRearCan);
           // EncoderTest(rightFrontCan);
           // EncoderTest(leftRearCan);
           //EncoderTest(rightRearCan);
            //ShooterTest();
            // windowMotorTest();
            //MeccanumTest();
            //PIDControllerTest();
            
            //double sonarValue = sonarSensor.getVoltage();
            //System.out.println("sonar sensor reading: " + sonarValue);
            //sonarValue /= 0.0097; // convert to inches
            //System.out.println("sonar distance (in): " + sonarValue);
            try {
                final double finSpeed = controller1.getRightStick().getX();
               // if (controller1.getRT()) {
                     //finController.set(SmartDashboard.getDouble("Fin Voltage"));
                     //finOnJag.setX(SmartDashboard.getDouble("Fin Voltage"));
                     
                    
                         
                         finOnJag.setX(finSpeed);
                         System.out.println("Fin Spreed:" + finSpeed);
                         
                         
                    
                     
                     
                  
               // }
            
            } catch (CANTimeoutException ex) {
                        ex.printStackTrace();
            }
            
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
         */
        SmartDashboard.putInt("Control_Mode", 0);
        /*
         * SmartDashboard.putDouble("KPercentVbus_mode", 0);
         * SmartDashboard.putDouble("KCurrent_mode", 0);
         * SmartDashboard.putDouble("KSpeed_mode", 0);
         * SmartDashboard.putDouble("KPostion_mode", 0);
         * SmartDashboard.putDouble("KVoltage_mode", 0);
         * SmartDashboard.putInt("joystick_mode", 1);
         */
        SmartDashboard.putDouble(PROPORTIONAL, proportional);

        SmartDashboard.putDouble(INTEGRAL, integral);

        SmartDashboard.putDouble(DIFFERENTIAL, differential);
        SmartDashboard.putInt(RPM, wheelRPMs);
        SmartDashboard.putInt("joystick_motor", 1);
        SmartDashboard.putDouble("Shooter Input", 0.0);
        SmartDashboard.putDouble("Fin Voltage", 0.09);
        

    }

    private void JaguarMotorTest(CANJaguar selectedMotor) {

        try {



            int conrolMode = SmartDashboard.getInt("Control_Mode", 0);
            System.out.println("Control Mode: " + conrolMode + "\n");
            if (conrolMode == 0) {
                selectedMotor.changeControlMode(CANJaguar.ControlMode.kPercentVbus);

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

    private void MeccanumTest() {
        try {
            double rotation = 0.0;
            if (controller1.getRB()) {
                rotation = -1.0;
            } else if (controller1.getLB()) {
                rotation = 1.0;
            } else {
                rotation = 0.0;
            }
            SmartDashboard.putDouble("Mag", controller1.getMag());
            SmartDashboard.putDouble("Deg", controller1.getDeg());
            SmartDashboard.putDouble("rotation", rotation);
            drive.mecanumDrive_Polar(controller1.getMag(), controller1.getDeg(), rotation);

        } catch (Exception e) {
            System.out.println("MeccanumTest Exception");
            System.out.println(e);
        }
    }

    private void DriverTest() {

        controller1.printState();
        try {
            double yValue = controller1.getRightStick().getY();
            double xValue = controller1.getRightStick().getX();


            double[] values = RangeCheck(xValue, yValue);
            xValue = values[0];
            yValue = values[1];
            double mag = Math.sqrt(yValue * yValue + xValue * xValue);
            //proportional = SmartDashboard.get

            SmartDashboard.putDouble("Driving xValue", xValue);

            SmartDashboard.putDouble("Driving yValue", yValue);

            if (yValue > 0 && xValue == 0) {
                // Forward

                rightFrontCan.setX(yValue);
                rightFrontCan.setPID(proportional, integral, differential);
                // rightFrontCan.
                leftFrontCan.setX(-1 * yValue);
                rightRearCan.setX(yValue);
                leftRearCan.setX(-1 * yValue);
                Timer.delay(0.25);

            } else if (yValue > 0 && xValue > 0) {
                // 45 diagional front right
                Watchdog.getInstance().feed();
                rightFrontCan.setX(0);
                leftFrontCan.setX(mag * -1);
                rightRearCan.setX(mag);
                leftRearCan.setX(0);
                Timer.delay(0.25);

            } else if (yValue == 0 && xValue > 0) {

                rightFrontCan.setX(xValue * -1);
                leftFrontCan.setX(-1 * xValue);
                rightRearCan.setX(xValue);
                leftRearCan.setX(xValue);
                Timer.delay(0.25);

            } else if (yValue < 0 && xValue > 0) {

                rightFrontCan.setX(mag);
                leftFrontCan.setX(0);
                rightRearCan.setX(0);
                leftRearCan.setX(mag * -1);
                Timer.delay(0.25);

            } else if (yValue < 0 && xValue == 0) {


                rightFrontCan.setX(yValue);
                leftFrontCan.setX(yValue * -1);
                rightRearCan.setX(yValue);
                leftRearCan.setX(yValue * -1);
                Timer.delay(0.25);

            } else if (yValue < 0 && xValue < 0) {



                rightFrontCan.setX(0);
                leftFrontCan.setX(mag * -1);
                rightRearCan.setX(mag);
                leftRearCan.setX(0);
                Timer.delay(0.25);

            } else if (yValue == 0 && xValue < 0) {



                rightFrontCan.setX(xValue * -1);
                leftFrontCan.setX(xValue * -1);
                rightRearCan.setX(xValue);
                leftRearCan.setX(xValue);
                Timer.delay(0.25);

            } else if (xValue < 0 && yValue > 0) {



                rightFrontCan.setX(mag * -1);
                leftFrontCan.setX(0);
                rightRearCan.setX(0);
                leftRearCan.setX(mag);
                Timer.delay(0.25);

            } else if (controller1.getDPad().getX() > 0) {

                rightFrontCan.setX(-1);
                leftFrontCan.setX(-1);
                rightRearCan.setX(-1);
                leftRearCan.setX(-1);
                Timer.delay(0.25);


            } else if (controller1.getDPad().getX() < 0) {

                rightFrontCan.setX(1);
                leftFrontCan.setX(1);
                rightRearCan.setX(1);
                leftRearCan.setX(1);
                Timer.delay(0.25);


            } else {



                //   System.out.println("STOP !!");

                rightFrontCan.setX(0);
                leftFrontCan.setX(0);
                rightRearCan.setX(0);
                leftRearCan.setX(0);
                Timer.delay(0.25);

            }


            if (controller1.getRB()) {

                rightFrontCan.configNeutralMode(CANJaguar.NeutralMode.kBrake);
                leftFrontCan.configNeutralMode(CANJaguar.NeutralMode.kBrake);
                rightRearCan.configNeutralMode(CANJaguar.NeutralMode.kBrake);
                leftRearCan.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            } else {
                rightFrontCan.configNeutralMode(CANJaguar.NeutralMode.kCoast);
                leftFrontCan.configNeutralMode(CANJaguar.NeutralMode.kCoast);
                rightRearCan.configNeutralMode(CANJaguar.NeutralMode.kCoast);
                leftRearCan.configNeutralMode(CANJaguar.NeutralMode.kCoast);

            }

        } catch (Exception e) {
        }





    }

    private void ShooterTest() {

        try {
            //double smartDashboardShooterValue = controller1.getRightStick().getX();
            if (controller1.getDPad().getY() > 0) {
                shootingValue += .01;
            } else if (controller1.getDPad().getY() < 0) {

                shootingValue -= .01;
            }
            if (controller1.getLB()) {
                shootingValue = 0.0;
            }

            //     if (smartDashboardShooterValue > 0) {
//                SmartDashboard.putDouble("Shooter Value", smartDashboardShooterValue);
            //SmartDashboard.putString("Shooter Target", controller1.getTargetButtons());

            if (shootingValue > 1.0) {
                shootingValue = 1.0;
            } else if (shootingValue < -1.0) {
                shootingValue = -1.0;
            }
            System.out.println("Shooter Value: " + shootingValue);

            shooterCan1.setX(-1 * shootingValue);
            shooterCan2.setX(shootingValue);
            //  Timer.delay(.25);

            //   }
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    private void windowMotorTest() {
        try {
            if (controller1.getA()) {
                rightRearCan.setX(0.25);
            }
            if (controller1.getB()) {
                rightRearCan.setX(-0.25);
            }
        } catch (Exception e) {
            System.out.println("window test motor exception");
            e.printStackTrace();
        }
    }

    private void PIDControllerTest() {
    }

    private double[] RangeCheck(double xValue, double yValue) {


        double[] listValues = new double[2];
        if (xValue == 0.0) {

            double noSlope = Math.tan(75.0);
            double zeroSlope = Math.tan(15.0);

            double calculatedSlope = (yValue / xValue);

            if (Math.abs(calculatedSlope) > noSlope) {
                listValues[0] = 0.0;
                listValues[1] = yValue;
            } else if (Math.abs(yValue / xValue) < zeroSlope) {
                listValues[0] = xValue;
                listValues[1] = 0.0;
            }


        } else {
            listValues[0] = xValue;
            listValues[1] = yValue;
        }
        return listValues;
    }

    private void EncoderTest(CANJaguar leftFrontCan1, CANJaguar leftRearCan1, CANJaguar rightFrontCan1, CANJaguar rightRearCan1) {
        double yValue = controller1.getRightStick().getY();
        double xValue = controller1.getRightStick().getX();
        // controller1.printState();
        try {

            
            
            double newP = SmartDashboard.getDouble(PROPORTIONAL);
            double newI = SmartDashboard.getDouble(INTEGRAL);
            double newD = SmartDashboard.getDouble(DIFFERENTIAL);
            int newRPM = SmartDashboard.getInt(RPM);

            if (newP != proportional || newI != integral || newD != differential || newRPM != wheelRPMs) {
                System.out.println("Got a new PID");
                leftFrontCan1.setPID(newP, newI, newD);
                rightFrontCan1.setPID(newP, newI, newD);
                leftRearCan1.setPID(newP, newI, newD);
                rightRearCan1.setPID(newP, newI, newD);
                proportional = newP;
                integral = newI;
                differential = newD;
                wheelRPMs = newRPM;
                System.out.println("P: " + newP + " I: " + newI + " D: " + newD + " W: " + wheelRPMs);
            }
           
            leftFrontCan1.setX(wheelRPMs);
            rightFrontCan1.setX(wheelRPMs);
            leftRearCan1.setX(wheelRPMs);
            rightRearCan1.setX(wheelRPMs);
            System.out.println("FR: " + rightFrontCan1.getPosition() +
                    " BR:" + rightRearCan1.getPosition() +
                    " FL:" + leftFrontCan1.getPosition() + 
                    " BL:" + leftRearCan1.getPosition());
       
            Timer.delay(.02);


        } catch (NetworkTableKeyNotDefined ex) {
            ex.printStackTrace();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    private CANJaguar initializeCanDriver(int canLocation, int encoderValue) {
        CANJaguar driveCan = null;
        try {
             driveCan = new CANJaguar(canLocation, CANJaguar.ControlMode.kSpeed);

            driveCan.configEncoderCodesPerRev(encoderValue);
            driveCan.setPID(proportional, integral, differential);
            //  driveCan.enableControl();
            driveCan.setSpeedReference(CANJaguar.SpeedReference.kEncoder);
            driveCan.changeControlMode(CANJaguar.ControlMode.kSpeed);
            driveCan.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
            driveCan.enableControl();
            driveCan.configNeutralMode(CANJaguar.NeutralMode.kCoast);
           
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return driveCan;
    }
}
