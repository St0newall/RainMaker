import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

interface Updateable{
    // clouds?
    // pond?
}
class Pond implements Updateable{

}
class Cloud implements Updateable{

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
        //PICK UP HERE.

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


}

abstract class GameObject extends Group {
    void add(Node node) {
        this.getChildren().add(node); // call this in each object that extend
        // this to add it to the root Group. (IN THE CONSTRUCTOR)
    }
}

class Game{
    Pane root;
    public Game(Pane root) {
        this.root = root;


    }


    //static AnimationTimer loop = new AnimationTimer() {
    //public void handle(long now) {

    //}
    //};
}

public class GameApp extends Application {
    private int SCENE_WIDTH = 400;
    private int SCENE_HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();//root has to be pane not group? to invert. But
        // WHY?
        root.setScaleY(-1); //Professorr states that I will run into issue
        // later if I do not invert.

        Scene scene = new Scene(root,SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        Helipad pad = new Helipad();
        Helicopter heli = new Helicopter();



        root.getChildren().addAll(pad);
        root.getChildren().addAll(heli);

        heli.setTranslateY(10);

        Game gameStart = new Game(root);
        scene.setFill(Color.BLACK);


        primaryStage.setResizable(false);
        primaryStage.show();
    }
}