package circlemovement;

import java.util.Vector;
import javafx.util.Duration;
import javafx.scene.paint.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.beans.binding.*;

public class CircleMover extends Pane {
    Vector<Point> clickList;
    private enum States {
        INITIAL, CLICKED, ANIMATING;
    }

    private States curState = States.INITIAL;

    private double firstX, firstY, secondX, secondY;

    private Circle animCircle;

    public CircleMover() {
        clickList = new Vector<Point>();
        this.setOnMouseClicked(
            (MouseEvent event) -> handleClick(event)
        );

        this.animCircle = new Circle();
        animCircle.setVisible(false);
        animCircle.setRadius(50.0);
        animCircle.setFill(Color.RED);
        this.getChildren().add(animCircle);

        this.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

        
        Button sb = new Button("Start");
        sb.translateXProperty().bind(Bindings.subtract(this.widthProperty(), Bindings.add(sb.widthProperty(), 15)));
        sb.translateYProperty().bind(Bindings.subtract(this.heightProperty(), Bindings.add(sb.heightProperty(), 15)));
        sb.setOnAction(
            (event) -> {
                startAnimation();
                curState = States.ANIMATING;
            }
        );
        this.getChildren().add(sb);
        
    }

    private void handleClick(MouseEvent me) {
        System.out.println(curState);

        if (curState == States.INITIAL) {
            clickList.add(new Point(me.getX(), me.getY()));
            Rectangle rekt = new Rectangle();
            rekt.relocate(me.getX()-25, me.getY()-25);
            rekt.setWidth(50.0);
            rekt.setHeight(50.0);
            rekt.setFill(Color.BLUE);
            this.getChildren().add(rekt);
//            firstX = me.getX();
//            firstY = me.getY();
//            curState == States.CLICKED;
        } /*else if (curState == States.CLICKED) {
            secondX = me.getX();
            secondY = me.getY();

            curState = States.ANIMATING;

            startAnimation();
        }*/
        // ignore clicks during animation
    }

    private void startAnimation() {
        Timeline tl = new Timeline();
        int timer = 1000;
        for (Point p : clickList) {
            tl.getKeyFrames().add(new KeyFrame(Duration.millis(timer), 
                    new KeyValue(animCircle.translateXProperty(), p.getX()), 
                    new KeyValue(animCircle.translateYProperty(), p.getY())));
            timer+=1000;
        }
//        KeyFrame start = new KeyFrame(Duration.millis(0),
//                                      new KeyValue(animCircle.translateXProperty(), firstX),
//                                      new KeyValue(animCircle.translateYProperty(), firstY));
//
//        KeyFrame end = new KeyFrame(Duration.millis(5000),
//                                    new KeyValue(animCircle.translateXProperty(), secondX),
//                                    new KeyValue(animCircle.translateYProperty(), secondY));
//        
//        KeyFrame middle = new KeyFrame(Duration.millis(2000),
//                                    new KeyValue(animCircle.translateXProperty(), 0.0));

//        tl.getKeyFrames().addAll(start, end, middle);

        tl.setOnFinished(
            (ActionEvent ev) -> {
                curState = States.INITIAL;
                animCircle.setVisible(false);
            }
        );

        animCircle.setVisible(true);
        tl.play();
    }
}

class Point {
    double x;
    double y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    double getX() {
        return x;
    }
    double getY() {
        return y;
    }
}