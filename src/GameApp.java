import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


class Pond implements Updatable{

    @Override
    public void update() {

    }
}
class Cloud implements Updatable{

    @Override
    public void update() {

    }
}

class Helipad extends GameObject{
    private static final double HELI_PAD_LENGTH = 80; //LGTM

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
    private static final double HELI_TIP_LENGTH = 15;
    private static final double HELI_TIP_WIDTH = 5;
    Ellipse HelicopterBase;
    Rectangle HelicopterTip;
    private static double SPEED = 0;

    public void setSpeed(double acceleration){
        this.SPEED = acceleration;
    }
    public static double getSpeed() {
        return SPEED;
    }

    public Helicopter(){

        HelicopterBase = new Ellipse(HELI_BASE_DIA, HELI_BASE_DIA);
        HelicopterBase.setFill(Color.YELLOW);

        HelicopterBase.setTranslateX(210);
        HelicopterBase.setTranslateY(50);

        HelicopterTip = new Rectangle(HELI_TIP_WIDTH, HELI_TIP_LENGTH);
        HelicopterTip.setFill(Color.YELLOW);

        HelicopterTip.setTranslateX(207.5);
        HelicopterTip.setTranslateY(60);

        add(HelicopterTip);
        add(HelicopterBase);


    }
    public void update(double acceleration) {
        myTranslate.setY(myTranslate.getY()+acceleration);

    }

    public double getMyRotation() {
        return myRotation.getAngle();

    }
    public void setPivot(double currX, double currY) {
        myRotation.setPivotX(currX);
        myRotation.setPivotY(currY);
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
    static double constantTest;

    public Game(Helipad pad, Helicopter heli) {
        //TODO
        this.pad = pad;
        this.heli = heli;
    }

    static AnimationTimer loop = new AnimationTimer() {
        double constant;
    public void handle(long now) {
        heli.update(heli.getSpeed());
        // heli.setPivot(heli.myTranslate.getX(),heli.myTranslate.getY());
        //System.out.println(heli.myTranslate.getX()+ " " +heli.myTranslate
        // .getY());

        heli.setPivot(210, heli.myTranslate.getY()+50);
        //heli.rotate(constantTest);
        //System.out.println(heli.myRotation.getPivotX() + "  " + heli
        // .myRotation.getPivotY());
       // heli.myRotation.rotate(constantTest, heli.myTranslate.getX(),
        //  heli.myTranslate.getY());
       // heli.getTransforms();
        constantTest++;
        }
    };

}

public class GameApp extends Application {
    private int SCENE_WIDTH = 400;
    private int SCENE_HEIGHT = 800;
    private double accel;
    private double rotation;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        Helipad pad = new Helipad();
        Helicopter heli = new Helicopter();
        Game root = new Game(pad, heli);//Game extends Pane

        root.setScaleY(-1);

        Scene scene = new Scene(root,SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setScene(scene);

        root.getChildren().addAll(pad);
        root.getChildren().addAll(heli);


        scene.setFill(Color.BLACK);
        primaryStage.setResizable(false);
        primaryStage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                System.out.println("W");
                if (accel <= 10) {
                    heli.setSpeed(accel);
                    accel = accel + 0.1;
                }

            }
            if(e.getCode() == KeyCode.DOWN) {
                if (accel >= -2) {
                    heli.setSpeed(accel);
                    accel = accel - 0.1;
                }
            }
            if(e.getCode() == KeyCode.LEFT) {
                heli.rotate(rotation);
                rotation = rotation+15;

            }
            if(e.getCode() == KeyCode.RIGHT) {
                heli.rotate((rotation));
                rotation = rotation-15;

            }

        });
        Game.loop.start();
    }

}
