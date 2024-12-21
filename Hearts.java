/*
 * Hearts class
 * Author : Christina Le
 * Date : 12/20/2024
 * Class to create floating heart objects in the cat game using abstract class inheritance
 */

import javafx.scene.image.Image;
import java.util.Random;
import javafx.scene.*;

public class Hearts extends Floats{

    Image c = new Image("heart.png"); //image of heart
    Random rand = new Random(); //random object to set spawn position
    Scene scene;

    /*
     * constructor
     */
    public Hearts(Scene scene){
        
        super(25,25); //creates rectangle object for heart
        this.scene = scene;
        this.setImage(c); //sets image of heart
        this.setPosition(); //sets spawn point of heart   
        
    }

    /*
     * sets spawn position of the heart
     */
    public void setPosition(){
        //sets heart spawn point to a random area in the window, tracking the coordinates of the heart
        this.x = rand.nextInt(0,(int)(scene.getWidth()-this.getWidth()));
        this.y = rand.nextInt(0,(int)(scene.getHeight()-this.getHeight()));
        super.setX(x);
        super.setY(y);
        x2=x+this.getWidth();
        y2=y+this.getHeight();
    }

    //checks whether object is colliding with the user petcat object
    public boolean inBounds(PetCat p){
        return(p.x>this.x2||p.x2<this.x)||(p.y>this.y2||p.y2<this.y);
    }
}
