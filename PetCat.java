/*
 * PetCat class
 * Author : Christina Le
 * Date : 12/20/2024
 * Class to create user moveable object
 */

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class PetCat extends Rectangle {

    Image catFloat = new Image("catuser.png"); //image of user sprite
    ImagePattern imagePattern = new ImagePattern(catFloat);

    Scene scene; //scene of user player

    //x and y coordinates of top left and bottom right corner of player
    double x = 350.0;
    double y = 200.0;
    double x2; 
    double y2;

    //speeds of object
    private double dx = 0; // Horizontal velocity
    private double dy = 0; // Vertical velocity
    
    /*
     * constructor
     * @param scene the scene where the cat is placed
     */
    public PetCat(Scene scene){
        super(40,40); //creates rectangle object 
        this.scene=scene;

        //sets coordinates and location of cat
        this.setX(x);
        this.setY(y);
        x2=x+40;
        y2=y+40;

        setImage(catFloat); //sets user image
    }

    /*
     * sets image of object
     * @param i the image to be set 
     */
    public void setImage(Image i){
        ImagePattern imagePattern = new ImagePattern(i);
        this.setFill(imagePattern);
    }

    /*
     * Sets speed of the x value
     * @param the speed 
     */
    public void setXDirection(double dx) {
        
        this.dx = dx;

    }
    /*
     * Sets speed of the y value
     * @param the speed 
     */
    public void setYDirection(double dy) {

        this.dy = dy;

    }

    /*
     * updates the position of the cat user
     */
    public void updatePosition() {
        
        //translates petcat
        setTranslateX(getTranslateX() + dx); 
        setTranslateY(getTranslateY() + dy); 

        //sets coordinate values
        x +=dx;
        y +=dy;

        //System.out.printf("X: %s Y: %s %n",x,y);

        //resets movement changes if the cat is going to go out of bounds
        if(x < 0){
            setTranslateX(getTranslateX()-dx);
            x-=dx;
        }
        if(x > scene.getWidth() - this.getWidth()){
            setTranslateX(getTranslateX()-dx);
            x-=dx;
        }
        if(y < 0){
            setTranslateY(getTranslateY() - dy);
            y-=dy;
        }
        if(y > scene.getHeight() - this.getWidth()){
            setTranslateY(getTranslateY() - dy);
            y-=dy;
        }
        
        //sets coordinates of the bottom right corner
        x2=x+40;
        y2=y+40;
    }
}
