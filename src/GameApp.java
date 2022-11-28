import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

//divide seeding into two parts
class Background extends GameObject{
    int backgroundWidth = 400;
    int backgroundHeight = 800;
    public Background() {
        Image backgroundImage = new Image("3095.jpg");
        ImageView background = new ImageView();
        background.setImage(backgroundImage);
        background.setFitHeight(backgroundHeight);
        background.setFitWidth(backgroundWidth);
        background.setScaleY(-1);
        add(background);
    }
}
class Pond extends GameObject{
    Ellipse Pond;
    Random rand = new Random();
    private static double fillCounter;
    Text PondFillText;
    int rand_intY = rand.nextInt(200,800);
    int rand_intX = rand.nextInt(0,400);
    int randRadandFill = rand.nextInt(15,30);
    public Pond(){
        fillCounter = randRadandFill;

        Pond = new Ellipse(randRadandFill,randRadandFill);
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
        if(fillCounter<100) {
            Pond.setRadiusX(fillCounter);
            Pond.setRadiusY(fillCounter);
        }
    }

    public void setPondFillText(double delta){
        if(fillCounter < 101) {
            if (Game.cloud.getpCounter() > 30) {
                fillCounter = fillCounter + (delta);
                PondFillText.setText(String.format("%.0f", fillCounter));
                fillPond();
            }
        }
        if(fillCounter>=100) {
            Game.setWinCondition();
        }
    }
    public double getfillCounter(){
        return fillCounter;
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

    public void incrementCloudPrecipitation(){
        if(pCounter < 100) {
            pCounter = pCounter + 1;
            precipitation.setText(String.format("%.0f", pCounter));
        }
    }

    public Ellipse getCloud(){
        return Cloud;
    }

    public void seeding() {
        Game.cloud.getCloud().setFill(Color.rgb((int) (255-pCounter),
                (int) (255-pCounter),(int) (255-pCounter)));
        incrementCloudPrecipitation();
    }
    @Override
    public void update() {
    }
    public void decrementPrecipitation(double delta) {
        if(pCounter > 0) {
            pCounter = pCounter - (delta);//Dont worry abt timeing YET
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

    public Helipad getHelipad(){
        return this;
    }

}
class Helicopter extends GameObject {
    private static final double HELI_TIP_LENGTH = 25;
    private static final double HELI_TIP_WIDTH = 5;
    Rectangle HelicopterTip;
    HelicopterBody HelicopterBase;
    HelicopterBlade HeliBlade;
    Text fuel;
    private static double SPEED;
    private static double FUEL;
    private static double ANGLE;
    private static double HEADING;
    boolean ignitionPress;


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
                SPEED -= 0.2;
            }
        }
    }

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
        HEADING = 0;
        FUEL = 100;
        HelicopterBase = new HelicopterBody();
        HeliBlade = new HelicopterBlade();
        HeliBlade.setRotate(90);

        fuel = new Text("F:" + FUEL);
        fuel.setFill(Color.YELLOW);
        fuel.setX(200);
        fuel.setY(30);
        fuel.setScaleY(-1);


        add(fuel);
        add(HelicopterBase);
        add(HeliBlade);
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
        FUEL = FUEL - 1;
        fuel.setText("F:"+ FUEL);
        if(FUEL<=0) {
            Game.setLoseCondition();
        }
    }

    public double calculateAng(){
        double absAngle = (450-ANGLE)%360;
        return Math.toRadians(absAngle);
    }

    public Helicopter getHeli(){
        return this;
    }
}
interface Updatable {
    void update();
}

class HelicopterBody extends Group{
    Ellipse HelicopterBase;

    Rectangle HeliBlock;
    Rectangle HeliLandGuard;
    Rectangle HeliLandGuardTwo;
    Rectangle ConnectOne;
    Rectangle ConnectTwo;
    Rectangle ConnectThree;
    Rectangle ConnectFour;

    Rectangle HeliTale;

    Rectangle BackBlade;

    private static final double HELI_BASE_DIA = 17;
    public HelicopterBody(){
        HelicopterBase = new Ellipse(HELI_BASE_DIA, HELI_BASE_DIA);
        HelicopterBase.setFill(Color.MOCCASIN);
        HelicopterBase.setTranslateX(210);
        HelicopterBase.setTranslateY(70);

        HeliBlock = new Rectangle(34, 7);
        HeliBlock.setFill(Color.MOCCASIN);
        HeliBlock.setTranslateX(193);
        HeliBlock.setTranslateY(53);

        HeliLandGuard = new Rectangle(5, 40);
        HeliLandGuard.setFill(Color.GRAY);
        HeliLandGuard.setTranslateX(183);
        HeliLandGuard.setTranslateY(43);

        HeliLandGuardTwo = new Rectangle(5, 40);
        HeliLandGuardTwo.setFill(Color.GRAY);
        HeliLandGuardTwo.setTranslateX(233);
        HeliLandGuardTwo.setTranslateY(43);

        ConnectOne = new Rectangle(20,2);
        ConnectOne.setFill(Color.BLACK);
        ConnectOne.setTranslateX(183);
        ConnectOne.setTranslateY(70);


        ConnectTwo = new Rectangle(20, 2);
        ConnectTwo.setFill(Color.BLACK);
        ConnectTwo.setTranslateX(183);
        ConnectTwo.setTranslateY(55);

        ConnectThree = new Rectangle(20,2);
        ConnectThree.setFill(Color.BLACK);
        ConnectThree.setTranslateX(213);
        ConnectThree.setTranslateY(55);

        ConnectFour = new Rectangle(20,2);
        ConnectFour.setFill(Color.BLACK);
        ConnectFour.setTranslateX(213);
        ConnectFour.setTranslateY(70);

        HeliTale = new Rectangle(6,30);
        HeliTale.setFill(Color.MOCCASIN);
        HeliTale.setTranslateY(25);
        HeliTale.setTranslateX(207);

        BackBlade = new Rectangle(2,10 );
        BackBlade.setFill(Color.GRAY);
        BackBlade.setTranslateX(214);
        BackBlade.setTranslateY(20);


        this.getChildren().add(BackBlade);
        this.getChildren().add(HeliTale);
        this.getChildren().add(ConnectFour);
        this.getChildren().add(ConnectThree);
        this.getChildren().add(ConnectTwo);
        this.getChildren().add(ConnectOne);
        this.getChildren().add(HeliLandGuardTwo);
        this.getChildren().add(HeliLandGuard);
        this.getChildren().add(HeliBlock);
        this.getChildren().add(HelicopterBase);

    }
}

class HelicopterBlade extends GameObject{
    Rectangle HelicopterBlade;
    public HelicopterBlade() {
        HelicopterBlade = new Rectangle(3, 70);
        HelicopterBlade.setFill(Color.GRAY);
        HelicopterBlade.setTranslateX(209);
        HelicopterBlade.setTranslateY(20);
        this.getChildren().add(HelicopterBlade);

        myRotation.setPivotX(210.5);
        myRotation.setPivotY(55);
    }

    @Override
    public void update() {

    }
    public void rotateBlade(double rotation) {
        myRotation.setAngle(rotation);
    }
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
    static Helipad pad;
    static Cloud cloud;
    static Pond pond;
    Background background;
    static boolean winCondition;
    static boolean loseCondition;
    static String winMsg = "Congratulations, You have won. Play" +
            "Agin?";
    String loseMsg = "You lost. Play Again?";
    private static Alert alert;

    public Game() {
        super.setScaleY(-1);
        AnimationTimer loop = new AnimationTimer() {
            double oldTime = 0;
            double elapsedTime = 0;
            double frameTime = 0;
            int limit = 0;
            public void handle(long now) {
                if (oldTime <= 0) oldTime = now;
                double delta = (now - oldTime) / 1e9;
                frameTime = (1 / (1 / delta)) * 1e3;
                oldTime = now;
                elapsedTime += delta;

                if (heli.isIgnitionPress())  {
                    heli.HeliBlade.rotateBlade(limit);
                    heli.decreaseFuel();
                    limit++;
                }
                heli.update();
                pond.setPondFillText(delta);
                cloud.decrementPrecipitation(delta);
                heli.setPivot(heli.myTranslate.getX(),
                        heli.myTranslate.getY());


            }
        };
        loop.start();
    }

    public static boolean setWinCondition(){
        return !winCondition;
    }

    public static boolean setLoseCondition(){
        return !loseCondition;
    }

    public static boolean isHelicopterinsideHeliPad(){
        return pad.getHelipad().getBoundsInParent().intersects(
                heli.getHeli().getBoundsInParent());
    }

    public static boolean isHelicopterCollidingWithCloud(){
        return cloud.getCloud().getBoundsInParent().intersects(heli.getHeli()
                .getBoundsInParent());
    }

    public void init(){
        super.getChildren().clear();
        super.getChildren().setAll(
                background = new Background(),
                pad = new Helipad(),
                pond = new Pond(),
                cloud = new Cloud(),
                heli = new Helicopter()
        );
    }

    public void winOrLose(){

        if(pond.getfillCounter()>100){
            alert = new Alert(Alert.AlertType.CONFIRMATION, winMsg,
                    ButtonType.YES, ButtonType.NO);
            alert.setOnHidden(evt -> {
                if (alert.getResult() == ButtonType.YES) {
                    init();
                }
                if(alert.getResult() == ButtonType.NO) {
                    System.exit(0);
                }
            });
            alert.show();
        }

        if(loseCondition == false){
            alert = new Alert(Alert.AlertType.CONFIRMATION,loseMsg,
                    ButtonType.YES, ButtonType.NO);
            alert.setOnHidden(evt -> {
                if (alert.getResult() == ButtonType.YES) {
                    init();
                }
                if(alert.getResult() == ButtonType.NO) {
                    System.exit(0);
                }
            });
            alert.show();
        }

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
            if(Game.isHelicopterinsideHeliPad()) {
                if (e.getCode() == KeyCode.I) {
                    Game.heli.setIgitionPress();
                }
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
            if (e.getCode() == KeyCode.SPACE) {
                if(Game.isHelicopterCollidingWithCloud()){
                    Game.cloud.seeding();
                }
            }

        });

    }
}
