/*
 * Floats class
 * Author : Christina Le
 * Date : 12/20/2024
 * Abstract class to create floating object classes
 */

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;

public abstract class Floats extends Rectangle{

    private double dx = 0; // Horizontal velocity
    private double dy = 0; // Vertical velocity
    public double x,y,x2,y2; //coordinates of top left and bottom right points on the object 

    /*
     * constructor
     * @param width the width of the object
     * @param height height of the object
     */
    public Floats(int width, int height){
        
        super(width,height);
        
    }
    
    /*
     * Allows subclasses to set image of their objects
     * @param i picture to be set as object image
     */
    public void setImage(Image i){

        ImagePattern imagePattern = new ImagePattern(i);
        this.setFill(imagePattern);

    }

    /*
     * Helps the spawn position of each object -- unique for each floating object type
     */
    public abstract void setPosition();

    /*
     * Sets direction/speed of object
     * @param dx the speed in the horizontal direction
     * @param dy the speed in the vertical direction
     */
    public void setDirection(double dx, double dy){

        this.dx=dx;
        this.dy=dy;

    }

    /*
     * changes the position of the object while tracking the coordinates of the object
     */
    public void updatePosition() {
        
        //translates object
        setTranslateX(getTranslateX() + dx); // Apply horizontal velocity
        setTranslateY(getTranslateY() + dy); // Apply vertical velocity   

        //sets coordinate points
        x+=dx;
        y+=dy;
        x2=x+this.getWidth();
        y2=y+this.getHeight();

        //System.out.printf("X: %s Y: %s %n",x,y);
    }
    
    
}
