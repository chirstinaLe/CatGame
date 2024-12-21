/*
 * Plane class
 * Author : Christina Le
 * Date : 12/20/2024
 * Class to create plane obstacles in the cat game using abstract class inheritance
 */

import javafx.scene.image.Image;
import java.util.Random;
import javafx.scene.*;

public class Plane extends Floats{

    Image c = new Image("plane.png"); //plane image
    Random rand = new Random(); //random object to set position
    Scene scene; //scene of plane

    /*
     * constructor
     * @param scene the scene of the plane
     */
    public Plane(Scene scene){
        
        super(100,50); //creates rectangle object
        this.setImage(c); //sets image of plane
        this.scene=scene; //sets scene
        this.setPosition(); //sets spawn position
        this.setDirection(rand.nextInt(5,10),0); //sets speed of plane
        
    }

    /*
     * sets spawn position of plane
     */
    public void setPosition(){
        this.x=0-this.getWidth(); //spawns to the left of the window
        this.y = rand.nextInt(0,(int)(scene.getHeight()-this.getHeight())); //spawns at a random height in the window

        //sets location
        super.setX(x); 
        super.setY(y);
       
        //sets coordinates of object
        x2=x+this.getWidth();
        y2=y+this.getHeight();
    }

    /*
     * checks whether a collision has occured between object and user cat
     * @param p the user's player rectangle
     */
    public boolean collided(PetCat p){
        return (p.x>this.x2||p.x2<this.x)||(p.y>this.y2||p.y2<this.y);
    }

    /*
     * checks whether the plane is in the window
     * returns false if the plane is not in the window
     */
    public boolean inBounds(){
        if(this.x>scene.getWidth()){
            return false;
        }
        return true;
    }
}
