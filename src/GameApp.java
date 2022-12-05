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
    int backgroundWidth = 800;
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
    private static final double HELI_PAD_STARTING_X = 370; //looks center
    private static final double HELI_PAD_STARTING_Y = 10;
    private  static final double HELI_PAD_CIRCLE_DIA = 35;
    private static final double HELI_PAD_STARTING_CIRCLE_X = 410;
    private static final double HELI_PAD_STARTING_CIRCLE_Y = 50;


    public Helipad() {
        heliPadSquare = new Rectangle(HELI_PAD_LENGTH, HELI_PAD_LENGTH);
        heliPadSquare.setStroke(Color.YELLOW);
        heliPadSquare.setTranslateX(HELI_PAD_STARTING_X);
        heliPadSquare.setTranslateY(HELI_PAD_STARTING_Y);

        helipadCircle = new Ellipse(HELI_PAD_CIRCLE_DIA,
                HELI_PAD_CIRCLE_DIA);
        helipadCircle.setStroke(Color.YELLOW);
        helipadCircle.setTranslateX(HELI_PAD_STARTING_CIRCLE_X);
        helipadCircle.setTranslateY(HELI_PAD_STARTING_CIRCLE_Y);

        add(heliPadSquare);

        add(helipadCircle);
    }

    public Helipad getHelipad(){
        return this;
    }

}
class Helicopter extends GameObject {
    private static final double HELI_TIP_LENGTH = 25;
    private static final double HELI_TIP_WIDTH = 5;
    HelicopterBody HelicopterBase;
    HelicopterBlade HeliBlade;
    Text fuel;
    private static double SPEED;
    private static double FUEL;
    private static double ANGLE;
    private static double HEADING;
    boolean ignitionPress;
    private State state;


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
        this.state = new Off(this);
        ignitionPress = false;
        SPEED = 0;
        ANGLE = 0;
        HEADING = 0;
        FUEL = 1000;
        HelicopterBase = new HelicopterBody();
        HeliBlade = new HelicopterBlade();
        HeliBlade.setRotate(90);

        fuel = new Text("F:" + FUEL);
        fuel.setFill(Color.YELLOW);
        fuel.setX(400);
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
        myRotation.setPivotX(currX+410);
        myRotation.setPivotY(currY+50);

    }
    public void increaseRotationLeft() {
            if(SPEED>.1 || SPEED <- .1) {
                HEADING += 15;
                ANGLE -= 15;
                Game.getInstance().heli.rotate(HEADING);
            }
    }

    public void increateRotationRight() {
            if(SPEED>.1 || SPEED < -.1) {
                HEADING -= 15;
                ANGLE += 15;
                Game.getInstance().heli.rotate(HEADING);
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
    public double getFuel(){
        return this.FUEL;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public Helicopter getHeli(){
        return this;
    }
}
interface Updatable {
    void update();
}

abstract class State{
    Helicopter Heli;

    State(Helicopter Heli){
        this.Heli = Heli;
    }

    public abstract void ignitionStart();
    public abstract void Seeding();


}

class Off extends State{


    public Off(Helicopter Heli) {
        super(Heli);
    }

    @Override
    public void ignitionStart() {
        Heli.changeState(new Starting(Heli));
    }

    @Override
    public void Seeding() {

    }
}
class Starting extends State {

    public Starting(Helicopter Heli) {
        super(Heli);
    }

    @Override
    public void ignitionStart() {
        Heli.changeState(new Stopping(Heli));
        //STARTING CAN GO TO
    }

    @Override
    public void Seeding() {

    }
}
class Stopping extends State{

    Stopping(Helicopter Heli) {
        super(Heli);
    }

    @Override
    public void ignitionStart() {
        Heli.changeState(new Starting(Heli));
        //STOPPING CAN GO TO STARTING AND OFF
    }

    @Override
    public void Seeding() {

    }
}

class Ready extends State{

    Ready(Helicopter Heli) {
        super(Heli);
    }

    @Override
    public void ignitionStart() {
        Heli.changeState(new Stopping(Heli));
    }

    @Override
    public void Seeding() {

    }
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
        HelicopterBase.setTranslateX(410);
        HelicopterBase.setTranslateY(70);

        HeliBlock = new Rectangle(34, 7);
        HeliBlock.setFill(Color.MOCCASIN);
        HeliBlock.setTranslateX(393);
        HeliBlock.setTranslateY(53);

        HeliLandGuard = new Rectangle(5, 40);
        HeliLandGuard.setFill(Color.GRAY);
        HeliLandGuard.setTranslateX(383);
        HeliLandGuard.setTranslateY(43);

        HeliLandGuardTwo = new Rectangle(5, 40);
        HeliLandGuardTwo.setFill(Color.GRAY);
        HeliLandGuardTwo.setTranslateX(433);
        HeliLandGuardTwo.setTranslateY(43);

        ConnectOne = new Rectangle(20,2);
        ConnectOne.setFill(Color.BLACK);
        ConnectOne.setTranslateX(383);
        ConnectOne.setTranslateY(70);


        ConnectTwo = new Rectangle(20, 2);
        ConnectTwo.setFill(Color.BLACK);
        ConnectTwo.setTranslateX(383);
        ConnectTwo.setTranslateY(55);

        ConnectThree = new Rectangle(20,2);
        ConnectThree.setFill(Color.BLACK);
        ConnectThree.setTranslateX(413);
        ConnectThree.setTranslateY(55);

        ConnectFour = new Rectangle(20,2);
        ConnectFour.setFill(Color.BLACK);
        ConnectFour.setTranslateX(413);
        ConnectFour.setTranslateY(70);

        HeliTale = new Rectangle(6,30);
        HeliTale.setFill(Color.MOCCASIN);
        HeliTale.setTranslateY(25);
        HeliTale.setTranslateX(407);

        BackBlade = new Rectangle(2,10 );
        BackBlade.setFill(Color.GRAY);
        BackBlade.setTranslateX(414);
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
    double rotatingDegree;
    public HelicopterBlade() {
        HelicopterBlade = new Rectangle(3, 70);
        HelicopterBlade.setFill(Color.GRAY);
        HelicopterBlade.setTranslateX(409);
        HelicopterBlade.setTranslateY(20);
        this.getChildren().add(HelicopterBlade);

        myRotation.setPivotX(410.5);
        myRotation.setPivotY(55);
    }

    public void Spin(double degree){
        myRotation.setAngle(degree);
    }

    public void WindingUp(double delta){
        if(Game.getInstance().heli.getState() instanceof Starting) {
            rotatingDegree += delta;
        }
        Spin(getMyRotation() + rotatingDegree);

    }

    public void WindingDown(double delta){
        rotatingDegree -= delta;
        Spin(getMyRotation() + rotatingDegree);

    }

    @Override
    public void update() {

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

    Helicopter heli;
    static Helipad pad;
    static Cloud cloud;
    AnimationTimer loop;
    AnimationTimer heliloop;
    Pond pond;
    Background background;
    static boolean winCondition;
    static boolean loseCondition;
    static String winMsg = "Congratulations, You have won. Play" +
            "Again?";
    String loseMsg = "You lost. Play Again?";

    private static Alert alert;

    private static Game Game = new Game();

    private Game() {
        super.setScaleY(-1);


        heliloop = new AnimationTimer(){
            double oldTime = 0;
            double elapsedTime = 0;
            double frameTime = 0;
            public void handle(long now) {
                if (oldTime <= 0) oldTime = now;
                double delta = (now - oldTime) / 1e9;
                frameTime = (1 / (1 / delta)) * 1e3;
                oldTime = now;
                elapsedTime += delta;




                if(heli.getState() instanceof Starting) {
                    heli.HeliBlade.WindingUp(delta);
                    System.out.println(heli.HeliBlade.rotatingDegree);
                    if(heli.HeliBlade.rotatingDegree >= 5) {
                        heli.changeState(new Ready(heli));
                    }
                }

                if(heli.getState() instanceof Ready) {
                    heli.HeliBlade.WindingUp(delta);
                    System.out.println(heli.HeliBlade.rotatingDegree);
                }

                if(heli.getState() instanceof Stopping) {
                    heli.HeliBlade.WindingDown(delta);
                }


            }
        };

        loop = new AnimationTimer() {
            double oldTime = 0;
            double elapsedTime = 0;
            double frameTime = 0;

            public void handle(long now) {
                if (oldTime <= 0) oldTime = now;
                double delta = (now - oldTime) / 1e9;
                frameTime = (1 / (1 / delta)) * 1e3;
                oldTime = now;
                elapsedTime += delta;

                if(heli.getState() instanceof Starting
                        || heli.getState() instanceof Ready
                        || heli.getState() instanceof Starting){
                    heli.decreaseFuel();
                }

                if(pond.getfillCounter()>=100){
                    gameWin();
                }
                if(heli.getFuel() < 0) {
                    gameLose();
                }
                heli.update();
                pond.setPondFillText(delta);
                cloud.decrementPrecipitation(delta);
                heli.setPivot(heli.myTranslate.getX(),
                        heli.myTranslate.getY());

            }
        };
        loop.start();
        heliloop.start();
    }

    public static Game getInstance(){
        if (Game == null)
            Game = new Game();
        return Game;
    }

    private void gameWin() {
            alert = new Alert(Alert.AlertType.CONFIRMATION,winMsg,
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

    private void gameLose() {
        loop.stop();
            alert = new Alert(Alert.AlertType.CONFIRMATION, loseMsg,
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

    public static boolean setWinCondition(){
        return !winCondition;
    }

    public static boolean setLoseCondition(){
        return !loseCondition;
    }

    public static boolean isHelicopterinsideHeliPad(){
        return pad.getHelipad().getBoundsInParent().intersects(
                getInstance().heli.getHeli().getBoundsInParent());
    }

    public static boolean isHelicopterCollidingWithCloud(){
        return cloud.getCloud().getBoundsInParent().intersects(Game.getInstance().heli.getHeli()
                .getBoundsInParent());
    }
    public void init(){
        winCondition =false;
        loseCondition =false;
        super.getChildren().clear();
        super.getChildren().setAll(
                background = new Background(),
                pad = new Helipad(),
                pond = new Pond(),
                pond = new Pond(),
                pond = new Pond(),
                cloud = new Cloud(),
                heli = new Helicopter()

        );
    }



}

public class GameApp extends Application {
    private int SCENE_WIDTH = 800;
    private int SCENE_HEIGHT = 800;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Game.getInstance().setScaleY(-1);
        Game.getInstance().init();
        Scene scene = new Scene(Game.getInstance(), SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        scene.setFill(Color.BLACK);
        primaryStage.setResizable(false);
        primaryStage.show();
        scene.setOnKeyPressed(e -> {
            if(Game.isHelicopterinsideHeliPad()) {
                if (e.getCode() == KeyCode.I) {
                    Game.getInstance().heli.getState().ignitionStart();
                }
            }
            if (e.getCode() == KeyCode.UP) {
                Game.getInstance().heli.increaseAcceleration();
            }
            if (e.getCode() == KeyCode.DOWN) {
                Game.getInstance().heli.decreaseAcceleration();
            }
            if (e.getCode() == KeyCode.LEFT) {
                Game.getInstance().heli.increaseRotationLeft();
            }
            if (e.getCode() == KeyCode.RIGHT) {
                Game.getInstance().heli.increateRotationRight();
            }
            if (e.getCode() == KeyCode.R) {
                Game.getInstance();
            }
            if (e.getCode() == KeyCode.SPACE) {
                if(Game.isHelicopterCollidingWithCloud()){
                    Game.cloud.seeding();
                }
            }

        });

    }
}
