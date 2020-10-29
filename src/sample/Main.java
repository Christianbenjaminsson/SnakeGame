package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    static int speed = 5;
    static int foodColor = 0;
    static int width = 20;
    static int height = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornerSize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;
    static Random rand = new Random();


    public enum Dir {
        left, right, up, down
    }

    public static class Corner {
        int x;
        int y;

        public Corner (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            newFood();

            VBox root = new VBox();
            Canvas c = new Canvas(width*cornerSize, height*cornerSize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle (long now) {
                    if (lastTick == 0) {
                        lastTick = now;

                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                    }
                }
            }.start();

            Scene scene = new Scene(root, width*cornerSize, height*cornerSize);
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.I) {
                    direction = Dir.up;
                }

                if (key.getCode() == KeyCode.J) {
                    direction = Dir.left;
                }

                if (key.getCode() == KeyCode.K) {
                    direction = Dir.down;
                }

                if (key.getCode() == KeyCode.L) {
                    direction = Dir.right;
                }
            });
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);

            for (Corner c: snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }

            foodColor = rand.nextInt(5);
            speed++;
            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
