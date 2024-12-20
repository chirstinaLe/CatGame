import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class PetCat extends Rectangle {

    Image catFloat = new Image("catuser.png");

    ImagePattern imagePattern = new ImagePattern(catFloat);

    Scene scene;

    Double x = 350.0;
    Double y = 200.0;
    Double x2; 
    Double y2;
    

    public PetCat(Scene scene){
        super(40,40);
        this.scene=scene;
        this.setX(x);
        this.setY(y);
        x2=x+40;
        y2=y+40;
        setImage(catFloat);
    }

    public void setImage(Image i){
        ImagePattern imagePattern = new ImagePattern(i);
        this.setFill(imagePattern);
    }

    private double dx = 0; // Horizontal velocity
    private double dy = 0; // Vertical velocity

    public void setXDirection(double dx) {
        
        this.dx = dx;

    }
    public void setYDirection(double dy) {

        this.dy = dy;

    }

    public void updatePosition() {
        
        setTranslateX(getTranslateX() + dx); // Apply horizontal velocity
        x +=dx;
        setTranslateY(getTranslateY() + dy); // Apply vertical velocity
        y +=dy;

        //System.out.printf("X: %s Y: %s %n",x,y);

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
        
        x2=x+40;
        y2=y+40;
    }
}
