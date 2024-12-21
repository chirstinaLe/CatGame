/*
 * Cloud class
 * Author : Christina Le
 * Date : 12/20/2024
 * Class to create floating cloud objects in the cat game using abstract class inheritance
 */

import javafx.scene.image.Image;
import java.util.Random;
import javafx.scene.*;

public class Cloud extends Floats{

    Image c = new Image("cloud1.png"); //image of cloud
    Random rand = new Random(); //random object to obtain random numbers
    Scene scene; //scene of object
    private double speed = 0.5;

    /*
     * Cloud constructor
     * @param scene the scene where the object is spawned
     */
    public Cloud(Scene scene){
        super(800,600); //creates rectangle with 800 by 600 dimensions
        this.setImage(c); //displays image onto object
        this.scene=scene; //sets the scene
 
        this.setPosition(); //sets the object at the right place
        this.setDirection(speed,0); //sets speed of object
    }

    /*
     * sets position of cloud to a random y coordinate on the left side of the screen
     */
    public void setPosition(){
        //sets x coordinate to left of window
        this.x=0-this.getWidth(); 
        super.setX(x);

        //sets y coordinate to random ara in window where cloud will be visible
        this.y = rand.nextInt(1-(int)this.getHeight(),(int)scene.getHeight()-1);
        super.setY(y);
       
        //sets other points of rectangle
        x2=x+this.getWidth();
        y2=y+this.getHeight();
    }

    /*
     * checks whether cloud has gone past window boundaries
     */
    public boolean inBounds(){
        if(this.x>scene.getWidth()){
            return false;
        }
        return true;
    }
}
