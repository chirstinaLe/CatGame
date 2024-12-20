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
    private Scene scene3;
    private AnimationTimer timer;

    private int difficultyLevel=2;
    private int lives;

	@Override
	public void start(Stage primaryStage) throws Exception {

		stage = primaryStage;
		stage.setTitle("Cat Game");
		menuScene = createSceneOne();
		gameScene = createSceneTwo();
        scene3 = createSceneThree();
        timer.stop();

		stage.setScene(menuScene);
        
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
        });

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

		stage.show();
	}

	private Scene createSceneOne() {

        VBox startmenu = new VBox();

        startmenu.setPadding(new Insets(40));
        startmenu.setSpacing(30);
        startmenu.setAlignment(Pos.TOP_CENTER);
        startmenu.setStyle("-fx-background-color: #D8BFD8;");

        Text title = new Text("Cat Game");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        //title.setFill(Color.WHITE);
    
		Button playButton = new Button("Play Game");
		playButton.setOnAction(e -> switchScenes(gameScene)); 

        
        Slider difficulty = new Slider(1,4,2);
        difficulty.setShowTickMarks(true);
        difficulty.setMajorTickUnit(4);
        difficulty.setSnapToTicks(true);

        Label difficultyValue = new Label("Difficulty: "+ (int) difficulty.getValue());
        difficulty.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
                    difficultyLevel = new_val.intValue();
                    //System.out.println(difficultyLevel);
                    difficultyValue.setText(String.format("Difficulty: %.0f", new_val));
            }
        });
        VBox.setMargin(difficultyValue, new Insets(-40, 0, 0, 0));
        

        Rectangle r = new Rectangle (150,150);
        Image catRight = new Image("catstart.png");
        Image catRight2 = new Image("catstart2.png");
        ImagePattern imagePattern = new ImagePattern(catRight);
        ImagePattern imagePattern2 = new ImagePattern(catRight2);
        r.setFill(imagePattern);

        Text description = new Text(
            "Use WASD to move your cat\nCollect randomly spawning hearts before they despawn!\nAvoid crashing into planes\nLose lives by crashing or not capturing a heart\nHave fun!"
        );
        description.setFont(new Font(8));
        description.setTextAlignment(TextAlignment.CENTER);
        description.setLineSpacing(6);


        startmenu.getChildren().addAll(r,title,playButton,difficulty,difficultyValue,description);
		menuScene = new Scene(startmenu, 350, 500);

        menuScene.setOnMouseClicked(e -> {
            //System.out.println(e.getX() + " " + e.getY());
            if(r.contains(e.getX(), e.getY())){
                r.setFill(imagePattern2);
            } else { 
                r.setFill(imagePattern);
            }
        });

		return menuScene;
	}
    
    Pane game;
    ArrayList <Cloud> clouds = new ArrayList<Cloud>();
    ArrayList <Hearts> hearts = new ArrayList<Hearts>();
    ArrayList <Plane> planes = new ArrayList<Plane>();
    private double spawnIntervalc = 10.0;  // Time interval between spawns in seconds
    private double lastSpawnTimec = 0;    // Last time a rectangle was spawned
    private double spawnIntervalh;  // Time interval between spawns in seconds
    private double lastSpawnTimeh = 0;    // Last time a rectangle was spawned
    private double spawnIntervalp = 2;  // Time interval between spawns in seconds
    private double lastSpawnTimep = 0;    // Last time a rectangle was spawned
    int score =0;

	private Scene createSceneTwo() {
        System.out.println(difficultyLevel);
        game = new Pane();
        game.setStyle("-fx-background-color:rgb(163, 217, 234);");
        
        lives=5;
        
        Label livesLeft = new Label("Lives left : "+lives);
        livesLeft.setLayoutX(5);
        Label DifficultyStat = new  Label("Difficulty: "+difficultyLevel);
        DifficultyStat.setLayoutY(20); 
        DifficultyStat.setLayoutX(5);


		gameScene = new Scene(game, 1000, 600);
        PetCat p = new PetCat(gameScene);
        game.getChildren().addAll(p,livesLeft,DifficultyStat);
        double speed = 3;
        gameScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> p.setYDirection(-speed);   // Move up
                case S -> p.setYDirection(speed);     // Move down
                case A -> p.setXDirection(-speed);    // Move left
                case D -> p.setXDirection(speed);    // Move right
            }
        });
        gameScene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W-> p.setYDirection(0);   // Move up
                case S -> p.setYDirection(0);     // Move down
                case A -> p.setXDirection(0);    // Move left
                case D -> p.setXDirection(0);    // Move right
            }
        });
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                spawnIntervalh = 5.5-difficultyLevel;
                double currentTime = now / 1_000_000_000.0;
                if (currentTime - lastSpawnTimec >= spawnIntervalc) {
                    spawnRandomCloud(gameScene);    // Spawn a new rectangle

                    lastSpawnTimec = currentTime;  // Update the last spawn time
                }
                if (currentTime - lastSpawnTimeh >= spawnIntervalh) {
                    spawnRandomHeart();
                    lastSpawnTimeh = currentTime;  // Update the last spawn time
                }
                if (currentTime - lastSpawnTimep >= spawnIntervalp) {
                    spawnRandomPlane();
                    lastSpawnTimep = currentTime;  // Update the last spawn time
                }
                
                p.updatePosition();
                updateClouds();
                updateHearts(p);
                updatePlanes(p);
                //System.out.println(lives);
                livesLeft.setText(String.format("Lives: %d", lives));
                DifficultyStat.setText(String.format("Difficulty: %d", difficultyLevel));

                if(lives<=0){
                    switchScenes(scene3);
                    timer.stop();
                }
            }
        };
        timer.start();
        
		return gameScene;
	}

    private Scene createSceneThree() {

        VBox startmenu = new VBox();

        startmenu.setPadding(new Insets(40));
        startmenu.setSpacing(30);
        startmenu.setAlignment(Pos.TOP_CENTER);
        startmenu.setStyle("-fx-background-color: #D8BFD8;");

        Text title = new Text("Game Over!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        
        Slider difficulty = new Slider(1,4,2);
        difficulty.setShowTickMarks(true);
        difficulty.setMajorTickUnit(4);
        difficulty.setSnapToTicks(true);

        Label difficultyValue = new Label("Difficulty: "+ (int) difficulty.getValue());
        difficulty.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
                    difficultyLevel = new_val.intValue();
                    difficultyValue.setText(String.format("Difficulty: %.0f", difficultyValue));
            }
        });
        VBox.setMargin(difficultyValue, new Insets(-40, 0, 0, 0));
        

        Rectangle r = new Rectangle (150,150);
        Image catRight = new Image("catover.png");
        ImagePattern imagePattern = new ImagePattern(catRight);
        r.setFill(imagePattern);

        Text description = new Text(
            "Use WASD to move your cat\nCollect randomly spawning hearts before they despawn!\nAvoid crashing into planes\nLose lives by crashing or not capturing a heart\nHave fun!"
        );
        description.setFont(new Font(8));
        description.setTextAlignment(TextAlignment.CENTER);
        description.setLineSpacing(6);

        Button playButton = new Button("Play Again?");
		playButton.setOnAction(e -> {lives = 5;switchScenes(gameScene);}); 


        startmenu.getChildren().addAll(r,title,playButton,difficulty,difficultyValue,description);
		scene3 = new Scene(startmenu, 350, 500);

		return scene3;
	}

    public void spawnRandomCloud(Scene s){
        Cloud cl;
        cl=new Cloud(s);
        game.getChildren().add(cl);
        clouds.add(cl);
    }
    public void updateClouds(){
        for(Cloud c:clouds){
            c.updatePosition();
            if(c.inBounds()==false){
                game.getChildren().remove(c);
                clouds.remove(c);
            }
        }
    }
    
    public void spawnRandomHeart(){
        Hearts h;
        h=new Hearts();
        game.getChildren().add(h);
        hearts.add(h);
    }
    public void updateHearts(PetCat p){
        for(Hearts c:hearts){
            if(c.inBounds(p)==false){
                game.getChildren().remove(c);
                hearts.remove(c);
            }
            if(hearts.size()>2){
                game.getChildren().remove(c);
                hearts.remove(c);
                lives--;
            }
        }
        
        
    }

    public void spawnRandomPlane(){
        Plane p;
        p=new Plane(gameScene);
        game.getChildren().add(p);
        planes.add(p);
    }
    public void updatePlanes(PetCat p){
        for(Plane c:planes){
            c.updatePosition();
            if(c.inBounds()==false){
                game.getChildren().remove(c);
                planes.remove(c);
            }
            if(c.collided(p)==false){
                game.getChildren().remove(c);
                planes.remove(c);
                lives--;
            }
            //System.out.printf("x: %s y%s %n",c.x,c.y);
        }
    }
        



	// Switch Scenes in single Stage
	public void switchScenes(Scene scene) {
		stage.setScene(scene);
        stage.centerOnScreen();
        System.out.println("Scene switched");
	}

    public static void main(String[] args) {
        launch(args);
    }
}