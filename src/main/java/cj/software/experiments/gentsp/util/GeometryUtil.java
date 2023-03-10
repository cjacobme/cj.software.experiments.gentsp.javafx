package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.City;
import cj.software.experiments.gentsp.entity.CityPair;
import cj.software.experiments.gentsp.entity.Individual;
import javafx.geometry.Point2D;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    public double calcDistance(
            List<City> cities,
            Map<CityPair, Double> existingDistances,
            int index1,
            int index2) {
        City city1 = cities.get(index1);
        City city2 = cities.get(index2);
        CityPair cityPair = CityPair.builder().withCity1(city1).withCity2(city2).build();
        double result;
        if (existingDistances.containsKey(cityPair)) {
            result = existingDistances.get(cityPair);
        } else {
            result = calcDistance(city1, city2);
            existingDistances.put(cityPair, result);
        }
        return result;
    }

    public double calcTotalDistance(Individual individual, List<City> cities, Map<CityPair, Double> existingDistances) {
        double result = 0;
        int numCities = cities.size();
        int[] chromosome = individual.getChromosome();
        for (int iCity = 0; iCity < numCities - 1; iCity++)
        {
            int index1 = chromosome[iCity];
            int index2 = chromosome[iCity + 1];
            double distance = calcDistance(cities, existingDistances, index1, index2);
            result += distance;
        }
        int index1 = chromosome[numCities - 1];
        int index2 = chromosome[0];
        double distance = calcDistance(cities, existingDistances, index1, index2);
        result += distance;
        individual.setDistanceSum(result);
        return result;
    }
}
