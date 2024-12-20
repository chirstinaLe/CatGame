import javafx.scene.image.Image;
import java.util.Random;
import javafx.scene.*;

public class Plane extends Floats{

    Image c = new Image("plane.png");
    Random rand = new Random();
    Scene scene;

    public Plane(Scene scene){
        
        super(100,50);
        this.setImage(c);
        this.scene=scene;
        this.setPosition();
        this.setDirection(rand.nextInt(5,10),0);
        
    }

    public void setPosition(){
        this.x=0-this.getWidth();
        super.setX(x);
        this.y = rand.nextInt(0,(int)scene.getHeight());
        super.setY(y);
       
        x2=x+this.getWidth();
        y2=y+this.getHeight();
    }

    public boolean collided(PetCat p){
        return (p.x>this.x2||p.x2<this.x)||(p.y>this.y2||p.y2<this.y);
    }

    public boolean inBounds(){
        if(this.x>scene.getWidth()){
            return false;
        }
        return true;
    }
}
