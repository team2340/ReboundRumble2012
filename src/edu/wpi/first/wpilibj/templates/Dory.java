/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.SimpleRobot;
import team2340.LogitechF310;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

//nate test NETBEANS push
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

        while(isEnabled() && isOperatorControl())
        {
            controller1.printState();
            controller2.printState();
            controller3.printState();
        }
    }
}
