package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.City;
import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GeometryUtil {
    public double calcDistance(City city1, City city2) {
        int deltaX = city2.getX() - city1.getX();
        int deltaY = city2.getY() - city1.getY();
        int deltaX2 = deltaX * deltaX;
        int deltaY2 = deltaY * deltaY;
        int sum = deltaX2 + deltaY2;
        double result = Math.sqrt(sum);
        return result;
    }

    public double calcMinDistance(City toBeChecked, Collection<City> collection) {
        double result = Double.MAX_VALUE;
        for (City city : collection) {
            double distance = calcDistance(toBeChecked, city);
            result = Math.min(result, distance);
        }
        return result;
    }

    public Point2D transformToPoint2D(City city, double widthRatio, double heightRatio) {
        int cityX = city.getX();
        int cityY = city.getY();
        double x = cityX * widthRatio;
        double y = cityY * heightRatio;
        Point2D result = new Point2D(x, y);
        return result;
    }
}
