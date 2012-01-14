/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team2340;

/**
 *
 * @author NAZareX Robotics
 */
public class Direction {
    double x;
    double y;
    
    public Direction (double x, double y){
        this.x=x;
        this.y=y;
             
    }
    
    public void setX (double x){
        this.x=x;
    }
    public void setY (double y){
        this.y=y;
    }
                
    public double getX (){
        return x;
             
    }
    public double getY(){
        return y;
    }
    
}
