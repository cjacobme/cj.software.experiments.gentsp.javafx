package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.City;
import org.springframework.stereotype.Service;

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
}
