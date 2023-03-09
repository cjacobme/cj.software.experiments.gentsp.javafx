package cj.software.experiments.gentsp.javafx.control;

import cj.software.experiments.gentsp.entity.City;
import cj.software.experiments.gentsp.entity.World;
import cj.software.experiments.gentsp.util.GeometryUtil;
import cj.software.experiments.gentsp.util.WorldFactory;
import cj.software.experiments.gentsp.util.spring.SpringContext;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;

public class WorldPane extends Region {
    private final Canvas canvas = new Canvas();

    private World world;

    public WorldPane() {
        getChildren().add(canvas);
        canvas.widthProperty().addListener(observable -> draw());
        canvas.heightProperty().addListener(observable -> draw());
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
        draw();
    }
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double width = getWidth();
        double height = getHeight();
        Insets insets = getInsets();
        double contentX = insets.getLeft();
        double contentY = insets.getRight();
        double contentWith = Math.max(0, width - (insets.getLeft() + insets.getRight()));
        double contentHeight = Math.max(0, height - (insets.getTop() + insets.getBottom()));
        canvas.relocate(contentX, contentY);
        canvas.setWidth(contentWith);
        canvas.setHeight(contentHeight);
    }
    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double paneWidth = canvas.getWidth();
        double paneHeight = canvas.getHeight();
        gc.clearRect(0, 0, paneWidth, paneHeight);
        Paint oldFill = gc.getFill();
        gc.setFill(Color.LIGHTGRAY);
        try {
            gc.fillRect(0, 0, paneWidth, paneHeight);
            int worldWidth = world.getWidth();
            int worldHeight = world.getHeight();
            double widthRatio = paneWidth / worldWidth;
            double heightRatio = paneHeight / worldHeight;
            drawWorld(gc, widthRatio, heightRatio);
        } finally {
            gc.setFill(oldFill);
        }
    }

    private void drawWorld(GraphicsContext gc, double widthRatio, double heightRatio) {
        GeometryUtil geometryUtil = SpringContext.getBean(GeometryUtil.class);
        List<City> cities = world.getCities();
        gc.setFill(Color.BLACK);
        double lengthHalf = WorldFactory.DIAMETER / 2.0;
        double length = WorldFactory.DIAMETER;
        for (City city : cities) {
            Point2D point2D = geometryUtil.transformToPoint2D(city, widthRatio, heightRatio);
            gc.fillRect(point2D.getX() - lengthHalf, point2D.getY() - lengthHalf, length, length);
        }
    }
}
