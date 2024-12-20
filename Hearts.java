import javafx.scene.image.Image;
import java.util.Random;

public class Hearts extends Floats{

    Image c = new Image("heart.png");
    Random rand = new Random();

    public Hearts(){
        super(25,25);
        this.setImage(c);
 
        this.setPosition();       
        
    }

    public void setPosition(){
        this.x = rand.nextInt(0,650);
        this.y = rand.nextInt(0,350);
        super.setX(x);
        super.setY(y);
        x2=x+this.getWidth();
        y2=y+this.getHeight();
    }


    public boolean inBounds(PetCat p){
        return(p.x>this.x2||p.x2<this.x)||(p.y>this.y2||p.y2<this.y);
    }
}
