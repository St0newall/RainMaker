import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.Random;

class Pond extends GameObject implements Updatable{
    Ellipse Pond;
    Random rand = new Random();

    private static double fillCounter;

    Text PondFillText;
    int rand_intY = rand.nextInt(200,800);
    int rand_intX = rand.nextInt(0,400);

    public Pond(){
        Pond = new Ellipse(30,30);
        Pond.setTranslateY(rand_intY);
        Pond.setTranslateX(rand_intX);
        Pond.setFill(Color.BLUE);


        PondFillText = new Text(String.format("%.0f",  fillCounter));
        PondFillText.setFill(Color.WHITE);
        PondFillText.setX(Pond.getTranslateX());
        PondFillText.setY(Pond.getTranslateY());
        PondFillText.setScaleY(-1);



        add(Pond);
        add(PondFillText);
    }

    public void fillPond() {
    }

    public void setPondFillText(double delta){
        if(Game.cloud.getpCounter() > 30) {
            fillCounter = fillCounter + delta * 2;
            PondFillText.setText(String.format("%.0f", fillCounter));
        }
    }

    @Override
    public void update() {

    }


}
class Cloud extends GameObject implements Updatable{
    Ellipse Cloud;
    Random rand = new Random();
    private static double pCounter;
    Text precipitation;
    int rand_intY = rand.nextInt(200,800);
    int rand_intX = rand.nextInt(0,400);

    public Cloud(){
        pCounter = 0;

        Cloud = new Ellipse(45,45);
        Cloud.setTranslateY(rand_intY);
        Cloud.setTranslateX(rand_intX);
        Cloud.setFill(Color.WHITE);


        precipitation = new Text (String.format("%.0f", pCounter));
        precipitation.setFill(Color.BLUE);
        precipitation.setX(Cloud.getTranslateX());
        precipitation.setY(Cloud.getTranslateY());
        precipitation.setScaleY(-1);

        add(Cloud);
        add(precipitation);
    }

    public void setpCounter(int PrecCount){
        if(pCounter<100)
        this.pCounter = PrecCount;
    }

    public double getpCounter(){
        return this.pCounter;
    }

    public void incrementCloudPrecipitation(double delta){
        if(pCounter < 100) {
            pCounter = pCounter + delta * 15;
            precipitation.setText(String.format("%.0f", pCounter));
        }
    }


    public Ellipse getCloud(){
        return Cloud;
    }
    @Override
    public void update() {
        if(pCounter>30) {
            Cloud.setFill(Color.rgb((int) (255-pCounter),
                    (int) (255-pCounter),(int) (255-pCounter)));
        }
    }

    public void decrementPrecipitation(double delta) {
        if(pCounter > 0) {
            pCounter = pCounter - delta * 2;
            precipitation.setText(String.format("%.0f", pCounter));
        }
        if(pCounter < 0) {
            pCounter = 0;
            precipitation.setText(String.format("%.0f", pCounter));
        }
    }
}
class Helipad extends GameObject{
    private static final double HELI_PAD_LENGTH = 80;
    Rectangle heliPadSquare;
    Rectangle innerHelipadSquare;
    Ellipse helipadCircle;
    Ellipse innerhelipadCircle;
    private static final double HELI_PAD_STARTING_X = 170; //looks center
    private static final double HELI_PAD_STARTING_Y = 10;
    private  static final double HELI_PAD_CIRCLE_DIA = 35;
    private static final double HELI_PAD_STARTING_CIRCLE_X = 210;
    private static final double HELI_PAD_STARTING_CIRCLE_Y = 50;


    public Helipad() {
        heliPadSquare = new Rectangle(HELI_PAD_LENGTH, HELI_PAD_LENGTH,
                Color.YELLOW);
        heliPadSquare.setTranslateX(HELI_PAD_STARTING_X);
        heliPadSquare.setTranslateY(HELI_PAD_STARTING_Y);

        innerHelipadSquare = new Rectangle(HELI_PAD_LENGTH-5,
                HELI_PAD_LENGTH-5);//make it tiny bit smaller than the outer
        // square ( padding? )
        innerHelipadSquare.setFill(Color.BLACK);
        innerHelipadSquare.setTranslateX(HELI_PAD_STARTING_X+2.5); // half of
        // padding to make fit?
        innerHelipadSquare.setTranslateY(HELI_PAD_STARTING_Y+2.5); //magic
        // numbers for now until I get everything out. looking right.

        helipadCircle = new Ellipse(HELI_PAD_CIRCLE_DIA,
                HELI_PAD_CIRCLE_DIA);
        helipadCircle.setFill(Color.YELLOW);
        helipadCircle.setTranslateX(HELI_PAD_STARTING_CIRCLE_X);
        helipadCircle.setTranslateY(HELI_PAD_STARTING_CIRCLE_Y);

        innerhelipadCircle = new Ellipse(HELI_PAD_CIRCLE_DIA-2.5,
                HELI_PAD_CIRCLE_DIA-2.5);
        innerhelipadCircle.setFill(Color.BLACK);
        innerhelipadCircle.setTranslateX(HELI_PAD_STARTING_CIRCLE_X);
        innerhelipadCircle.setTranslateY(HELI_PAD_STARTING_CIRCLE_Y);

        add(heliPadSquare);
        add(innerHelipadSquare);
        add(helipadCircle);
        add(innerhelipadCircle);
    }
}
class Helicopter extends GameObject{
    private static final double HELI_BASE_DIA = 15;
    private static final double HELI_TIP_LENGTH = 25;
    private static final double HELI_TIP_WIDTH = 5;
    Ellipse HelicopterBase;
    Rectangle HelicopterTip;
    Text fuel;
    private static double SPEED;
    private static double FUEL;
    private static double ANGLE;
    private static double HEADING;
    boolean ignitionPress;

    private static boolean SEEDING;

    public void increaseAcceleration(){
        if(ignitionPress) {
            if (SPEED <= 10) {
                SPEED += 0.1;
            }
        }
    }
    public void decreaseAcceleration(){
        if(ignitionPress) {
            if (SPEED >= -2) {
                SPEED -= 0.1;
            }
        }
    }
    public double getSpeed() {
        return SPEED;
    }

    public void setFuel(double fuel){
        this.FUEL = fuel;
    }
    public double getFuel() {return this.FUEL;}

    boolean isIgnitionPress(){
        return ignitionPress;
    }

    public void setIgitionPress(){
        if(-.1<SPEED && SPEED<.1) {
            ignitionPress = !ignitionPress;
        }
    }

    public Helicopter(){
        ignitionPress = false;
        SPEED = 0;
        ANGLE = 0;
        FUEL = 2500;

        HelicopterBase = new Ellipse(HELI_BASE_DIA, HELI_BASE_DIA);
        HelicopterBase.setFill(Color.YELLOW);

        HelicopterBase.setTranslateX(210);
        HelicopterBase.setTranslateY(50);
        HelicopterTip = new Rectangle(HELI_TIP_WIDTH, HELI_TIP_LENGTH);
        HelicopterTip.setFill(Color.YELLOW);

        HelicopterTip.setTranslateX(207.5);
        HelicopterTip.setTranslateY(60);

        fuel = new Text("F:" + FUEL);
        fuel.setFill(Color.YELLOW);
        fuel.setX(200);
        fuel.setY(30);
        fuel.setScaleY(-1);

        add(fuel);
        add(HelicopterTip);
        add(HelicopterBase);
    }

    public void update() {
        myTranslate.setY(myTranslate.getY()+deltaY());
        myTranslate.setX(myTranslate.getX()+deltaX());
    }

    public double getMyRotation() {
        return myRotation.getAngle();
    }
    public void setPivot(double currX, double currY) {
        myRotation.setPivotX(currX+210);
        myRotation.setPivotY(currY+50);
    }
    public void increaseRotationLeft() {
            if(SPEED>.1 || SPEED <- .1) {
                HEADING += 15;
                ANGLE -= 15;
                Game.heli.rotate(HEADING);
            }
    }

    public void increateRotationRight() {
            if(SPEED>.1 || SPEED < -.1) {
                HEADING -= 15;
                ANGLE += 15;
                Game.heli.rotate(HEADING);
            }
    }

    public double deltaX() {
        return SPEED * Math.cos(calculateAng());
    }
    public double deltaY() {
        return SPEED * Math.sin(calculateAng());
    }

    public void decreaseFuel(){
        fuel.setText("F:"+ FUEL);
    }

    public double calculateAng(){
        double absAngle = (450-ANGLE)%360;
        return Math.toRadians(absAngle);
    }

    public void setSEEDING(){
        SEEDING = !SEEDING;
    }

    public void setSEEDINGFALSE(){
        SEEDING = false;
    }
    public static boolean isSEEDING() {
        return SEEDING;
    }

    public Helicopter getHeli(){
        return this;
    }
}
interface Updatable {
    void update();
}

abstract class GameObject extends Group implements Updatable{
    protected Translate myTranslate;
    protected Rotate myRotation;
    protected Scale myScale;
    public GameObject() {
        this.setManaged(false);
        myTranslate = new Translate();
        myRotation = new Rotate();
        myScale = new Scale();
        this.getTransforms().addAll( myRotation, myTranslate,myScale);
    }

    public void rotate(double degrees) {
        myRotation.setAngle(degrees);
    }
    public void translate(double x, double y) {
        myTranslate.setX(x);
        myTranslate.setY(y);
    }
    public void scale(double x, double y) {
        myScale.setX(x);
        myScale.setY(y);
    }

    public double getMyRotation() {
        return myRotation.getAngle();
    }

    public void update() {
        for (Node n : getChildren()) {
            if (n instanceof Updatable)
                ((Updatable) n).update();
        }
    }
    void add(Node node) {
        this.getChildren().add(node); // call this in each object that extend
        // this to add it to the root Group. (IN THE CONSTRUCTOR)
    }
}
class Game extends Pane{
    static Helicopter heli;
    Helipad pad;
    static Cloud cloud;
    static Pond pond;

    public Game() {
        super.setScaleY(-1);
    }

    public static boolean isHelicopterCollidingWithCloud(){
        return cloud.getCloud().getBoundsInParent().intersects(heli.getHeli()
                .getBoundsInParent());
    }
    static AnimationTimer loop = new AnimationTimer() {
        double oldTime = 0;
        double elapsedTime = 0;
        double frameTime = 0;

        public void handle(long now) {
            if (oldTime <= 0) oldTime = now;
            double delta = (now - oldTime) / 1e9;
            frameTime = (1 / (1 / delta)) * 1e3;
            oldTime = now;
            elapsedTime += delta;

            if(!heli.isSEEDING()){
                cloud.decrementPrecipitation(delta);
            }

            if(heli.isSEEDING()){
                cloud.incrementCloudPrecipitation(delta);

            }

            if (heli.isIgnitionPress())  {
                heli.decreaseFuel();
                heli.setFuel(heli.getFuel() - 1);
            }

            heli.update();
            cloud.update();

            pond.setPondFillText(delta);

            heli.setPivot(heli.myTranslate.getX(), heli.myTranslate.getY());

        }
    };
    public void init(){
        super.getChildren().clear();
        super.getChildren().setAll(
                new Helipad(),
                cloud = new Cloud(),
                pond = new Pond(),
                heli = new Helicopter()
        );
    }

}

public class GameApp extends Application {
    private int SCENE_WIDTH = 400;
    private int SCENE_HEIGHT = 800;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Game root = new Game();//Game extends Pane
        root.setScaleY(-1);
        root.init();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        scene.setFill(Color.BLACK);
        primaryStage.setResizable(false);
        primaryStage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.I) {
                Game.heli.setIgitionPress();
            }
            if (e.getCode() == KeyCode.UP) {
                Game.heli.increaseAcceleration();
            }
            if (e.getCode() == KeyCode.DOWN) {
                Game.heli.decreaseAcceleration();
            }
            if (e.getCode() == KeyCode.LEFT) {
                Game.heli.increaseRotationLeft();
            }
            if (e.getCode() == KeyCode.RIGHT) {
                Game.heli.increateRotationRight();
            }
            if (e.getCode() == KeyCode.R) {
                root.init();
            }

            if (Game.isHelicopterCollidingWithCloud()) {
                Game.heli.setSEEDINGFALSE();
                if (e.getCode() == KeyCode.SPACE) {
                    Game.heli.setSEEDING();
                }
            } else
                Game.heli.setSEEDINGFALSE();
        });
        scene.setOnKeyReleased(e -> {
            Game.heli.setSEEDINGFALSE();
        });
        Game.loop.start();
    }

}
