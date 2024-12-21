/*
 * CatGame Main Class 
 * Author : Christina Le
 * Date : 12/20/2024
 * Cat Game is a game where the user floats around the sky, collecting hearts. 
 * The user has 5 lives, which are lost when the user crashes into a plane or the heart despawns withut being collected
 * CatGame.java is the main class which runs the GUI
 */

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.beans.value.ChangeListener;
import javafx.scene.shape.Rectangle;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import javafx.scene.control.Label;

public class CatGame extends Application {

    private Stage stage; //main stage
	private Scene menuScene; //scene for main menu
	private Scene gameScene; //scene for game
    private Scene overScene; //scene once game is ended
    private AnimationTimer timer; //animates objects
    
    private int difficultyLevel=2; //difficulty level that affects how frequently heart spawns
    private int lives = 5; //amount of lives that users have

    Pane game; //game pane

    //collections of floating objects
    ArrayList <Cloud> clouds = new ArrayList<Cloud>(); 
    ArrayList <Hearts> hearts = new ArrayList<Hearts>();
    ArrayList <Plane> planes = new ArrayList<Plane>();

    //how long program waits in seconds before spawning a new floating object
    private double cloudSpawnInterval = 10.0;  
    private double heartSpawnInterval;  
    private double planeSpawnInterval;  
    
    //tracks last time each floating object was spawned
    private double cloudLastSpawnTime = 0;    
    private double heartLastSpawnTime = 0;    
    private double planeLastSpawnTime = 0;   

    //speed of cat user
    private double speed = 3;

	@Override
	public void start(Stage primaryStage) throws Exception {

		stage = primaryStage;
		stage.setTitle("Cat Game"); //sets title for window

        //Creates scenes
		menuScene = createSceneOne(); 
		gameScene = createSceneTwo();
        overScene = createSceneThree();

        //stops the timer 
        timer.stop();

        //sets window to the main menu
		stage.setScene(menuScene);
        
        //stops user from resizing window
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
        });

        //event handler
        //checks if window is changed
        gameScene.windowProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends javafx.stage.Window> observable, 
                                javafx.stage.Window oldWindow, 
                                javafx.stage.Window newWindow) {
                if (newWindow != null) { // Scene is added to a Stage
                    timer.start(); // Start the timer
                } else { // Scene is removed from the Stage
                    timer.stop(); // Stop the timer
                }
            }
        });

		stage.show(); //shows the window
	}

    /*
     * Creates/sets up main menu
     */
	private Scene createSceneOne() {

        VBox startMenu = new VBox(); //vertical holder for nodes

        startMenu.setPadding(new Insets(40)); //brings the vbox 40 from the top
        startMenu.setSpacing(30); //sets spacing between nodes of the vbox
        startMenu.setAlignment(Pos.TOP_CENTER); //aligns the vbox to the center
        startMenu.setStyle("-fx-background-color: #D8BFD8;"); //sets background of the holder to pink

        //creating+formatting title of main menu
        Text title = new Text("Cat Game"); 
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
    
        //creates button to switch to the gameplay
		Button playButton = new Button("Play Game");
		playButton.setOnAction(e -> switchScenes(gameScene)); 

        //slider that detects user input and sets difficulty accordingly
        Slider difficulty = new Slider(1,4,2);
        difficulty.setShowTickMarks(true);
        difficulty.setMajorTickUnit(4);
        difficulty.setSnapToTicks(true);

        //shows value of slider 
        Label difficultyValue = new Label("Difficulty: "+ (int) difficulty.getValue());

        //event handler
        //checks/updates slider values
        difficulty.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
                    difficultyLevel = new_val.intValue();
                    //System.out.println(difficultyLevel);
                    difficultyValue.setText(String.format("Difficulty: %.0f", new_val));
            }
        });
        VBox.setMargin(difficultyValue, new Insets(-40, 0, 0, 0)); //formatting label to be closer to slider
        
        //images to display on main menu
        Rectangle r = new Rectangle (150,150);
        Image catRight = new Image("catstart.png");
        Image catRight2 = new Image("catstart2.png");
        ImagePattern imagePattern = new ImagePattern(catRight);
        ImagePattern imagePattern2 = new ImagePattern(catRight2);
        r.setFill(imagePattern);
        
        //text description of game
        Text description = new Text(
            "Use WASD to move your cat\nCollect randomly spawning hearts before they despawn!\nAvoid crashing into planes\nLose lives by crashing or not capturing a heart\nHave fun!"
        );
        description.setFont(new Font(8));
        description.setTextAlignment(TextAlignment.CENTER);
        description.setLineSpacing(6);

        //adds all nodes to holder
        startMenu.getChildren().addAll(r,title,playButton,difficulty,difficultyValue,description);

        //creates menu with vbox holder
		menuScene = new Scene(startMenu, 350, 500);

        //event handler
        //checks whether mouse click is within bounds of the image and changes the image depending on the user's click location
        menuScene.setOnMouseClicked(e -> {
            if((e.getX()>r.getLayoutX()&&e.getX()<r.getLayoutX()+r.widthProperty().getValue()) //checking for horizontal bounds
            &&(e.getY()>r.getLayoutY()&&e.getY()<r.getLayoutX()+r.heightProperty().getValue()) //checking for vertical bounds
            ){
                r.setFill(imagePattern2);
            } else { 
                r.setFill(imagePattern);
            }
        });

		return menuScene;
	}
    

    /*
     * Creates/sets up game
     */
	private Scene createSceneTwo() {

        //initializing and coloring game pane
        game = new Pane();
        game.setStyle("-fx-background-color:rgb(163, 217, 234);");
        
        //creating+formatting labels describing game stats
        Label livesLeft = new Label("Lives left : "+lives);
        livesLeft.setLayoutX(5);
        Label DifficultyStat = new  Label("Difficulty: "+difficultyLevel);
        DifficultyStat.setLayoutY(20); 
        DifficultyStat.setLayoutX(5);

        //initializing scene
		gameScene = new Scene(game, 1000, 600);

        //creating user object
        PetCat userCat = new PetCat(gameScene);

        //adding user and stats to the pane
        game.getChildren().addAll(userCat,livesLeft,DifficultyStat);
        
        //animation for game
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                heartSpawnInterval = 5.0-difficultyLevel; //sets frequency of heart spawns depending on difficulty selected
                planeSpawnInterval = 2.5-(difficultyLevel/2); //sets frequency of plane spawns depending on difficulty

                double currentTime = now / 1_000_000_000.0; //gets the time elapsed in seconds

                //checks whether a floating object is due to be spawned (elapsed time between spawns is higher than the spawn interval), then spawns one if necessary
                //cloud spawning
                if (currentTime - cloudLastSpawnTime >= cloudSpawnInterval) {
                    spawnRandomCloud(gameScene);    

                    cloudLastSpawnTime = currentTime;  
                }
                //heart spawning
                if (currentTime - heartLastSpawnTime >= heartSpawnInterval) {
                    spawnRandomHeart();
                    heartLastSpawnTime = currentTime;  
                }
                //plane spawning
                if (currentTime - planeLastSpawnTime >= planeSpawnInterval) {
                    spawnRandomPlane(gameScene);
                    planeLastSpawnTime = currentTime;  
                }
                
                //updates positions of game objects
                userCat.updatePosition();
                updateClouds();
                updateHearts(userCat);
                updatePlanes(userCat);

                //changes game stats for user to see
                livesLeft.setText(String.format("Lives: %d", lives));
                DifficultyStat.setText(String.format("Difficulty: %d", difficultyLevel));

                //ends game if lives reaches 0
                if(lives<=0){
                    switchScenes(overScene);
                    timer.stop();
                }
            }
        };
        timer.start();

        //event handler for user key input
        //moves/stops user depending on WASD keys
        gameScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> userCat.setYDirection(-speed);   // Move up
                case S -> userCat.setYDirection(speed);     // Move down
                case A -> userCat.setXDirection(-speed);    // Move left
                case D -> userCat.setXDirection(speed);    // Move right
            }
        });
        gameScene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W-> userCat.setYDirection(0);   // Move up
                case S -> userCat.setYDirection(0);     // Move down
                case A -> userCat.setXDirection(0);    // Move left
                case D -> userCat.setXDirection(0);    // Move right
            }
        });
        
		return gameScene;
	}

    /*
     * Spawns a cloud randomly
     * @param scene the game scene where the object must be added to
     */
    public void spawnRandomCloud(Scene scene){
        Cloud cl;
        cl=new Cloud(scene);
        game.getChildren().add(cl);
        clouds.add(cl);
    }
    /*
     * Updates the position of the clouds
     */
    public void updateClouds(){
        for(Cloud c:clouds){ //checks all clouds in game
            c.updatePosition(); //updates cloud location
            //checks if cloud is in bounds of pane and removes cloud if it is not
            if(c.inBounds()==false){ 
                game.getChildren().remove(c); 
                clouds.remove(c);
            }
        }
    }

    /*
     * Spawns a heart randomly
     */
    public void spawnRandomHeart(){
        Hearts h;
        h=new Hearts();
        game.getChildren().add(h);
        hearts.add(h);
    }
    /*
     * updates position of hearts
     * @param p the user cat that will be checked for collisions
     */
    public void updateHearts(PetCat p){
        for(Hearts c:hearts){ //checks all existing hearts

            //checks if hearts have collided with user cat and removes them if there is a collision
            if(c.inBounds(p)==false){
                game.getChildren().remove(c);
                hearts.remove(c);
            }

            //removes oldest heart and depletes one life if the number of hearts exceeds the maximum
            if(hearts.size()>2){
                game.getChildren().remove(c);
                hearts.remove(c);
                lives--;
            }
        }
    }

    /*
     * Spawns a plane randomly
     * @param scene the game scene where the object must be added to
     */
    public void spawnRandomPlane(Scene scene){
        Plane p;
        p=new Plane(scene);
        game.getChildren().add(p);
        planes.add(p);
    }
    /*
     * updates position of planes
     * @param p the user cat that will be checked for collisions
     */
    public void updatePlanes(PetCat p){
        for(Plane c:planes){ //checks all existing planes
            c.updatePosition(); //updates plane positions

            //checks if plane is within pane and removes it if plane travels out of bounds
            if(c.inBounds()==false){
                game.getChildren().remove(c);
                planes.remove(c);
            }
            //removes plane and depletes a life if user collides with plane
            if(c.collided(p)==false){
                game.getChildren().remove(c);
                planes.remove(c);
                lives--;
            }
            //System.out.printf("x: %s y%s %n",c.x,c.y);
        }
    }

    /*
     * creates game ending window (not interactable)
     */
    private Scene createSceneThree() {

        //creates and formats end scene
        VBox endMenu = new VBox();
        endMenu.setPadding(new Insets(100));
        endMenu.setSpacing(30);
        endMenu.setAlignment(Pos.TOP_CENTER);
        endMenu.setStyle("-fx-background-color: #D8BFD8;");

        //title of ending window
        Text title = new Text("Game Over!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        //ending window image
        Rectangle r = new Rectangle (150,150);
        Image catRight = new Image("catover.png");
        ImagePattern imagePattern = new ImagePattern(catRight);
        r.setFill(imagePattern);

        //adds nodes to ending window
        endMenu.getChildren().addAll(r,title);
		overScene = new Scene(endMenu, 350, 500);

		return overScene;
	}
        
	/*
    * Switch Scenes in single Stage
    * @param scene the scene to be switched to 
    */
	public void switchScenes(Scene scene) {
		stage.setScene(scene);
        stage.centerOnScreen();
        System.out.println("Scene switched");
	}

    //main program
    public static void main(String[] args) {
        launch(args);
    }
}
