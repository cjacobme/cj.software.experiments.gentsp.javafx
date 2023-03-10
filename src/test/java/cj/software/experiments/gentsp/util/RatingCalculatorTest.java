package cj.software.experiments.gentsp.util;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import cj.software.experiments.gentsp.entity.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringJUnitConfig(classes = RatingCalculator.class)
class RatingCalculatorTest {
    @Autowired
    private RatingCalculator ratingCalculator;

    @MockBean
    private GeometryUtil geometryUtil;

    @Test
    void calcFitness1() {
        calcIndividualFitness(20.0, 0.05);
    }

    @Test
    void calcFitness2() {
        calcIndividualFitness(4.0, 0.25);
    }

    private void calcIndividualFitness(double distanceSum, double expectedFitness) {
        // setup data
        Individual individual = new IndividualBuilder().build();
        City city = City.builder().withX(13).withY(14).build();
        List<City> cities = List.of(city);
        Map<CityPair, Double> existingDistances = new HashMap<>();

        // configure mocks
        when(geometryUtil.calcTotalDistance(individual, cities, existingDistances)).thenReturn(distanceSum);

        // invoke
        double returned = ratingCalculator.calcFitness(individual, cities, existingDistances);

        // checks
        assertThat(returned).as("returned value").isEqualTo(expectedFitness);
        assertThat(individual.getFitnessValue()).as("fitness value in individual").isEqualTo(expectedFitness);
        verify(geometryUtil).calcTotalDistance(individual, cities, existingDistances);
    }

    @Test
    void calcPopulationFitness1() {
        Population population = new PopulationBuilder().build();
        City city1 = City.builder().withX(1).withY(1).build();
        City city2 = City.builder().withX(2).withY(2).build();
        City city3 = City.builder().withX(3).withY(3).build();
        List<City> cities = List.of(city1, city2, city3);

        calcPopulationFitness(population, cities, 1.08333, 3.0, 2.0, 4.0);
    }

    @Test
    void calcPopulationFitness2() {
        int numIndividuals = 5;
        Individual[] individuals = new Individual[numIndividuals];
        for (int i = 0; i < numIndividuals; i++) {
            individuals[i] = mock(Individual.class);
        }
        Population population = Population.builder().withIndividuals(individuals).build();
        List<City> cities = List.of(City.builder().withX(2).withY(3).build());

        calcPopulationFitness(
                population,
                cities,
                1.34286,
                5.0,
                2.0, 4.0, 7.0, 4.0);
    }

    private void calcPopulationFitness(
            Population population,
            List<City> cities,
            double expectedValue,
            Double firstDistanceSum,
            Double... furtherDistanceSums) {
        // setup data
        Map<CityPair, Double> existingDistances = new HashMap<>();

        // mock beans
        when(geometryUtil.calcTotalDistance(any(Individual.class), eq(cities), eq(existingDistances)))
                .thenReturn(firstDistanceSum, furtherDistanceSums);

        // invoke
        double totalFitness = ratingCalculator.calcPopulationFitness(population, cities, existingDistances);
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(totalFitness).as("total fitness").isEqualTo(expectedValue, offset(0.0001));
        softy.assertThat(population.getPopulationFitness()).as("in population").isEqualTo(expectedValue, offset(0.0001));
        softy.assertAll();
        verify(geometryUtil, times(population.getIndividuals().length)).calcTotalDistance(
                any(Individual.class), eq(cities), eq(existingDistances));
    }

    @Test
    void sort1() {
        Individual ind1 = new IndividualBuilder().withFitnessValue(1.0).build();
        Individual ind2 = new IndividualBuilder().withFitnessValue(2.0).build();
        Individual ind3 = new IndividualBuilder().withFitnessValue(11.0).build();
        Population population = new PopulationBuilder().withIndividuals(ind1, ind2, ind3).build();
        List<Individual> expectedOrder = List.of(ind3, ind2, ind1);
        sort(population, expectedOrder);
    }

    @Test
    void sort2() {
        Individual ind1 = new IndividualBuilder().withFitnessValue(15.0).build();
        Individual ind2 = new IndividualBuilder().withFitnessValue(12.0).build();
        Population population = new PopulationBuilder().withIndividuals(ind1, ind2).build();
        List<Individual> expectedOrder = List.of(ind1, ind2);
        sort(population, expectedOrder);
    }

    private void sort(Population population, List<Individual> expectedOrder) {
        List<Individual> sorted = ratingCalculator.sortFitness(population);
        assertThat(sorted).as("sorted").isEqualTo(expectedOrder);
    }
}
