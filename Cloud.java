import javafx.scene.image.Image;
import java.util.Random;
import javafx.scene.*;

public class Cloud extends Floats{

    Image c = new Image("cloud1.png");
    Random rand = new Random();
    Scene scene;

    public Cloud(Scene scene){
        super(800,600);
        this.setImage(c);
        this.scene=scene;
 
        this.setPosition();
        this.setDirection(0.5,0);
        
    }

    public void setPosition(){
        this.x=0-this.getWidth();
        super.setX(x);
        this.y = rand.nextInt(0,300);
        super.setY(y);
       
        x2=x+this.getWidth();
        y2=y+this.getHeight();
    }

    public boolean inBounds(){
        if(this.x>scene.getWidth()){
            return false;
        }
        return true;
    }
}
