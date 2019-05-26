package com.yeyoan.blackjack.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Animation {

    private static int Y = 68;
    private static int ON_SCREEN_X = 50;
    private static int OFF_SCREEN_X = 1000;
    private static int SLIDE_SPEED = 1000;
    private static int DEPTH_SPEED = 5;

    public static void enter(Node node, HBox parent) {
        enter(node, parent, () -> { });
    }
    
    public static void enter(Node node, HBox parent, Runnable action) {
        Path path = new Path(new MoveTo(OFF_SCREEN_X, Y), new LineTo(ON_SCREEN_X, Y));
        PathTransition transition = new PathTransition(Duration.millis(SLIDE_SPEED), path, node);
        transition.setOnFinished(event -> {
            lower(node, 19);
            action.run();
        });
        transition.play();
        parent.getChildren().add(node);
    }

    public static void leave(int i, HBox parent, Runnable action) {
        if (i == parent.getChildren().size()) {
            return;
        }
        Path path = new Path(new MoveTo(ON_SCREEN_X, Y), new LineTo(-OFF_SCREEN_X, Y));
        Node node = parent.getChildren().get(i);
        PathTransition transition = new PathTransition(Duration.millis(SLIDE_SPEED), path, node);
        if (i == parent.getChildren().size()-1) {
            transition.setOnFinished(event -> {
                action.run();
            });
        }
        raise(node, 6, () -> transition.play());
        leave(i+1, parent, action);
    }

    public static void lower(Node node, int radius) {
        if (radius < DepthManager.LOW) {
            return;
        }
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(DEPTH_SPEED),
            new KeyValue(node.styleProperty(), DepthManager.createDepth(radius))
        ));
        timeline.setOnFinished(next -> lower(node, radius-1));
        timeline.play();
    }

    public static void raise(Node node, int radius, Runnable action) {
        if (radius > DepthManager.HIGH) {
            return;
        }
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(DEPTH_SPEED),
            new KeyValue(node.styleProperty(), DepthManager.createDepth(radius))
        ));
        timeline.setOnFinished(next -> {
            if (radius == DepthManager.HIGH) {
                action.run();
            }
            raise(node, radius+1, action);
        });
        timeline.play();
    }
}