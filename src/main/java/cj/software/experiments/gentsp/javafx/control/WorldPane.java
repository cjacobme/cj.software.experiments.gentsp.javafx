package cj.software.experiments.gentsp.javafx.control;

import cj.software.experiments.gentsp.entity.City;
import cj.software.experiments.gentsp.entity.Individual;
import cj.software.experiments.gentsp.entity.World;
import cj.software.experiments.gentsp.util.GeometryUtil;
import cj.software.experiments.gentsp.util.spring.SpringContext;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    private final ObjectProperty<World> worldProperty = new SimpleObjectProperty<>();

    private Individual currentIndividual;

    public WorldPane() {
        getChildren().add(canvas);
        canvas.widthProperty().addListener(observable -> draw());
        canvas.heightProperty().addListener(observable -> draw());
    }

    public void setWorld(World world) {
        worldProperty.set(world);
        this.currentIndividual = null;
        draw();
    }

    public World getWorld() {
        return worldProperty.get();
    }

    public ObjectProperty<World> worldProperty() {
        return worldProperty;
    }

    public void setCurrentIndividual(Individual currentIndividual) {
        this.currentIndividual = currentIndividual;
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
            World world = getWorld();
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
        World world = getWorld();
        List<City> cities = world.getCities();
        gc.setFill(Color.BLACK);
        double lengthHalf = City.DIAMETER / 2.0;
        double length = City.DIAMETER;
        for (City city : cities) {
            Point2D point2D = geometryUtil.transformToPoint2D(city, widthRatio, heightRatio);
            gc.fillRect(point2D.getX() - lengthHalf, point2D.getY() - lengthHalf, length, length);
        }
        if (currentIndividual != null) {
            drawCurrentIndividual(gc, widthRatio, heightRatio, geometryUtil, cities);
        }
    }

    private void drawCurrentIndividual(
            GraphicsContext gc,
            double widthRatio,
            double heightRatio,
            GeometryUtil geometryUtil,
            List<City> cities) {
        Paint currentStroke = gc.getStroke();
        gc.setStroke(Color.YELLOW);
        try {
            gc.beginPath();
            try {
                int[] chromosome = currentIndividual.getChromosome();
                int index = chromosome[0];
                City city = cities.get(index);
                Point2D transformed = geometryUtil.transformToPoint2D(city, widthRatio, heightRatio);
                gc.moveTo(transformed.getX(), transformed.getY());
                int numCities = cities.size();
                for (int iCity = 1; iCity < numCities; iCity++) {
                    lineToCity(gc, widthRatio, heightRatio, geometryUtil, cities, chromosome, iCity);
                }
                lineToCity(gc, widthRatio, heightRatio, geometryUtil, cities, chromosome, 0);
            } finally {
                gc.closePath();
            }
        } finally {
            gc.setStroke(currentStroke);
        }
    }

    private void lineToCity(
            GraphicsContext gc,
            double withRatio,
            double heightRatio,
            GeometryUtil geometryUtil,
            List<City> cities,
            int[] chromosome,
            int index) {
        int iCity = chromosome[index];
        City city = cities.get(iCity);
        Point2D transformed = geometryUtil.transformToPoint2D(city, withRatio, heightRatio);
        gc.lineTo(transformed.getX(), transformed.getY());
        gc.stroke();
    }
}
