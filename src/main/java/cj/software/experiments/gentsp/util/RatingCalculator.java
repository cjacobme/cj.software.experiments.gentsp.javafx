package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.City;
import cj.software.experiments.gentsp.entity.CityPair;
import cj.software.experiments.gentsp.entity.Individual;
import cj.software.experiments.gentsp.entity.Population;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class RatingCalculator {
    @Autowired
    private GeometryUtil geometryUtil;
    public double calcFitness(Individual individual, List<City> cities, Map<CityPair, Double> existingDistances) {
        double distanceSum = geometryUtil.calcTotalDistance(individual, cities, existingDistances);
        double result = 1.0 / distanceSum;
        individual.setFitnessValue(result);
        return result;
    }

    public double calcPopulationFitness(
            Population population,
            List<City> cities,
            Map<CityPair, Double> existingDistances) {
        double result = 0.0;
        for (Individual individual : population.getIndividuals()) {
            double individualFitness = calcFitness(individual, cities, existingDistances);
            result += individualFitness;
        }
        population.setPopulationFitness(result);
        return result;
    }

    public List<Individual> sortFitness(Population population) {
        List<Individual> result = Arrays.asList(population.getIndividuals());
        result.sort(new Comparator<>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                CompareToBuilder builder = new CompareToBuilder()
                        .append(o2.getFitnessValue(), o1.getFitnessValue());
                int result = builder.build();
                return result;
            }
        });
        return result;
    }
}
