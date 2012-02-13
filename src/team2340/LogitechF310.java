/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2340;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 * @author NAZareX Robotics
 */
public class LogitechF310 {

    int port;
    Joystick joystick;
    double min = 0.04;
    private String buttonPressed = "NO";

    public LogitechF310(int port) {
        this.port = port;
        joystick = new Joystick(port);

    }

    public boolean getX() {
        return joystick.getRawButton(1);

    }

    public boolean getY() {
        return joystick.getRawButton(4);


    }

    public boolean getA() {
        return joystick.getRawButton(2);


    }

    public boolean getB() {
        return joystick.getRawButton(3);


    }

    public boolean getLB() {
        return joystick.getRawButton(5);


    }

    public boolean getRB() {
        return joystick.getRawButton(6);


    }

    public boolean getLT() {
        return joystick.getRawButton(7);


    }

    public boolean getRT() {
        return joystick.getRawButton(8);


    }

    public boolean getBack() {
        return joystick.getRawButton(9);


    }

    public boolean getStart() {
        return joystick.getRawButton(10);


    }

    public Direction getDPad() {
        return new Direction(limit(joystick.getRawAxis(5)),
                (-1 * limit(joystick.getRawAxis(6))));

    }

    public Direction getLeftStick() {
        return new Direction(limit(joystick.getRawAxis(1)),
                (-1 * limit(joystick.getRawAxis(2))));
    }
    
    public double getMag() {
        return joystick.getMagnitude();
    }
    
    public double getDeg() {
        return joystick.getDirectionDegrees();
    }

    public Direction getRightStick() {
        return new Direction(limit(joystick.getRawAxis(3)),
                (-1 * limit(joystick.getRawAxis(4))));
    }

    private double limit(double value) {
        if ((value > 0 && value < 0.04) || 
                (value < 0 && value > -0.04)) {
            return 0.0;
        }
        return value;
    }
    
    

    public void printState() {
        if (getX()) {
            System.out.println("F310 on " + port + " - X is pushed");
        }
        if (getY()) {
            System.out.println("F310 on " + port + " - Y is pushed");
        }
        Watchdog.getInstance().feed();
        if (getA()) {
            System.out.println("F310 on " + port + " - A is pushed");
        }
        if (getB()) {
            System.out.println("F310 on " + port + " - B is pushed");
        }
        if (getLB()) {
            System.out.println("F310 on " + port + " - LB is pushed");
        }
        if (getRB()) {
            System.out.println("F310 on " + port + " - RB is pushed");
        }
        if (getLT()) {
            System.out.println("F310 on " + port + " - LT is pushed");
        }
        Watchdog.getInstance().feed();
        if (getRT()) {
            System.out.println("F310 on " + port + " - RT is pushed");
        }
        Watchdog.getInstance().feed();
        if (getBack()) {
            System.out.println("F310 on " + port + " - Back is pushed");
        }
        Watchdog.getInstance().feed();
        if (getStart()) {
            System.out.println("F310 on " + port + " - Start is pushed");
        }
        Watchdog.getInstance().feed();
        Direction dPadDir = getDPad();
        if (dPadDir.getX() != 0 || dPadDir.getY() != 0) {
            System.out.println("F310 on " + port + " - DPad x=" + dPadDir.getX() + ", y=" + dPadDir.getY());
        }
        Watchdog.getInstance().feed();
        Direction leftDir = getLeftStick();
        if (leftDir.getX() != 0 || leftDir.getY() != 0) {
            System.out.println("F310 on " + port + " - left stick x=" + leftDir.getX() + ", y=" + leftDir.getY());
        }
        Watchdog.getInstance().feed();
        Direction rightDir = getRightStick();
        if (rightDir.getX() != 0 || rightDir.getY() != 0) {
            System.out.println("F310 on " + port + " - right stick x=" + rightDir.getX() + ", y=" + rightDir.getY());
        }
    }

   

    public String getTargetButtons() {
       //buttonPressed = "NO";
        if (getA())
            buttonPressed = "A";
        else if (getB())
            buttonPressed = "B";
        else if (getX())
            buttonPressed = "X";
        else if (getY())
            buttonPressed = "Y";
//        else
//            buttonPressed = "NO";
        return buttonPressed;
            
    }
}