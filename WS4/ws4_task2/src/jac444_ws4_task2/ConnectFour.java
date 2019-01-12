

package jac444_ws4_task2;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.util.*;
import javafx.geometry.Point2D;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectFour extends Application {
    private static final int TITLE_SIZE = 80;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private boolean redMove = true;
    private Disc[][] grid = new Disc[COLUMNS][ROWS];
    private Pane discRoot = new Pane();

    /** Create Pane **/
    private Parent createContent(){
        Pane root = new Pane();
        root.getChildren().add(discRoot);

        Shape gridShape = makeGrid();
        root.getChildren().add(gridShape);
        root.getChildren().addAll(makeColumns());

        return root;
    }

    /** Make Grid in the Pane **/
    private Shape makeGrid(){
        Shape shape = new Rectangle((COLUMNS + 1) * TITLE_SIZE, (ROWS + 1) * TITLE_SIZE);
        for(int y = 0; y < ROWS; y++){
            for(int x = 0; x < COLUMNS; x++){
                Circle circle = new Circle(TITLE_SIZE/2);
                circle.setCenterX(TITLE_SIZE/2);
                circle.setCenterY(TITLE_SIZE/2);
                circle.setTranslateX(x * (TITLE_SIZE + 5) + TITLE_SIZE/4);
                circle.setTranslateY(y * (TITLE_SIZE + 5) + TITLE_SIZE/4);

                shape  = Shape.subtract(shape, circle);
            }
        }
        Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0); // to set azimuth (방위각)
        light.setElevation(30.0); // to set elevation (고도)

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);

        shape.setFill(Color.GRAY);
        shape.setEffect(lighting);

        return shape;
    }

    /** Make Columns in the Pane **/
    private List<Rectangle> makeColumns(){
        List<Rectangle> list = new ArrayList<>();

        for(int x = 0; x < COLUMNS; x++){
            Rectangle rect = new Rectangle(TITLE_SIZE, (ROWS + 1) * TITLE_SIZE);
            rect.setTranslateX(x * (TITLE_SIZE + 5) + TITLE_SIZE/4);
            rect.setFill(Color.TRANSPARENT);
            // lambda expression (event)
            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            // lambda expression (event)
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            final int column = x;
            // lambda expression (event - when mouse is cliked, place the disck (red or yellow it depends on who it turns)
            rect.setOnMouseClicked(e -> placeDisc(new Disc(redMove), column));

            list.add(rect);
        }

        return list;

    }

    /** Place Disc in the Pane **/
    private void placeDisc(Disc disc, int column){
        // when one disc is placed, one row should be subtracted from total number of current row
        int row = ROWS-1;
        do {
            if(!getDisc(column, row).isPresent())
                break;
            row--;
        } while(row >= 0);

        if(row < 0)
            return;

        grid[column][row] = disc;
        discRoot.getChildren().add(disc);
        disc.setTranslateX(column * (TITLE_SIZE + 5) + TITLE_SIZE/4);

        final int currentRow = row;

        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
        animation.setToY(row * (TITLE_SIZE + 5) + TITLE_SIZE/4);
        animation.setOnFinished(e -> {
            if(gameEnded(column, currentRow)){
                gameOver();
            }
            redMove = !redMove;
        });

        animation.play();
    }

    /** Hardest part */
    // The Point2D class defines a point representing a location in (x,y) coordinate space.
    private boolean gameEnded(int col, int row){
        List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3)
                .mapToObj(r -> new Point2D(col, r))
                .collect(Collectors.toList());

        List<Point2D> horizontal = IntStream.rangeClosed(col - 3, col + 3)
                .mapToObj(c -> new Point2D(c, row))
                .collect(Collectors.toList());

        Point2D topLeft = new Point2D(col - 3, row - 3);
        List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> topLeft.add(i, i))
                .collect(Collectors.toList());

        Point2D botLeft = new Point2D(col - 3, row - 3);
        List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> botLeft.add(i, -i))
                .collect(Collectors.toList());

        return checkRange(vertical) || checkRange(horizontal)
                || checkRange(diagonal1) || checkRange(diagonal2);

    }
    private boolean checkRange(List<Point2D> points){
        int chain = 0;

        for(Point2D p : points) {
            int column = (int)p.getX();
            int row = (int)p.getY();

            Disc disc = getDisc(column, row).orElse(new Disc(!redMove));
            if(disc.red == redMove) {
                chain++;
                if (chain == 4) {
                    return true;
                }
            } else {
                chain = 0;
            }
        }

        return false;
    }

    private void gameOver(){
        System.out.println("Winner: " + (redMove ? "RED" : "YELLOW"));
    } // conditional operation

    private Optional<Disc> getDisc(int col, int row){
        if(col < 0 || col >= COLUMNS || row < 0 || row >= ROWS)
            return Optional.empty();

        return Optional.ofNullable(grid[col][row]);
    }

    /** Make disc for the game **/
    private static class Disc extends Circle {
        private final boolean red;
        public Disc(boolean red){
            super(TITLE_SIZE/2, red ? Color.RED : Color.YELLOW);
            this.red = red;

            setCenterX(TITLE_SIZE/2);
            setCenterY(TITLE_SIZE/2);
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Connect Four");
        stage.setScene(new Scene(createContent()));
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
