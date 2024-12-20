import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;

public abstract class Floats extends Rectangle{

    private double dx = 0; // Horizontal velocity
    private double dy = 0; // Vertical velocity
    public double x,y,x2,y2;

    public Floats(int width, int height){
        super(width,height);
    }
    
    public void setImage(Image i){
        ImagePattern imagePattern = new ImagePattern(i);
        this.setFill(imagePattern);
    }

    public abstract void setPosition();

    public void setDirection(double dx, double dy){
        this.dx=dx;
        this.dy=dy;
        
    }

    public void updatePosition() {
        
        setTranslateX(getTranslateX() + dx); // Apply horizontal velocity
        setTranslateY(getTranslateY() + dy); // Apply vertical velocity   
        x+=dx;
        y+=dy;
        
        x2=x+this.getWidth();
        y2=y+this.getHeight();
        //System.out.printf("X: %s Y: %s %n",x,y);
    }
    
    
}
