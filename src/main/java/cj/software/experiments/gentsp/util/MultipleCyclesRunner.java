package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.*;
import cj.software.experiments.gentsp.event.MultipleCyclesEvent;
import cj.software.experiments.gentsp.event.MultipleCyclesListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleCyclesRunner implements Runnable {

    private final ProblemSetup problemSetup;

    private Population population;

    private final int numCycles;

    private final MatingService matingService;

    private final RatingCalculator ratingCalculator;

    private final List<MultipleCyclesListener> multipleCyclesListeners = new ArrayList<>();

    private final List<City> cities;

    private final Logger logger = LogManager.getFormatterLogger();

    public MultipleCyclesRunner(
            MatingService matingService,
            RatingCalculator ratingCalculator,
            ProblemSetup problemSetup,
            Population population,
            List<City> cities,
            int numCycles) {
        this.matingService = matingService;
        this.ratingCalculator = ratingCalculator;
        this.problemSetup = problemSetup;
        this.numCycles = numCycles;
        this.cities = cities;
        this.population = population;
    }

    public synchronized void addMultipleCyclesListener(MultipleCyclesListener listener) {
        this.multipleCyclesListeners.add(listener);
    }

    public void run() {
        logger.info("start to run for %d cycles...", numCycles);
        for (int iCycle = 0; iCycle < numCycles; iCycle++) {
            logger.info("cycle #%d", iCycle);
            int totalCycleCounter = problemSetup.incCycleCounter();
            double crossoverRate = problemSetup.getCrossoverRate();
            int elitismCount = problemSetup.getElitismCount();
            int tournamentSize = problemSetup.getTournamentSize();
            double mutationRate = problemSetup.getMutationRate();
            population = matingService.crossOver(
                    population,
                    crossoverRate,
                    elitismCount,
                    tournamentSize,
                    totalCycleCounter,
                    mutationRate);
            Map<CityPair, Double> existingDistances = new HashMap<>();
            double populationFitness = ratingCalculator.calcPopulationFitness(population, cities, existingDistances);
            List<Individual> individuals = ratingCalculator.sortFitness(population);
            Individual best = individuals.get(0);
            logger.info("best individual has dist sum  %8.2f and fitness %8.8f", best.getDistanceSum(), best.getFitnessValue());
            Individual worst = individuals.get(individuals.size() - 1);
            logger.info("worst individual has dist sum %8.2f and fitness %8.8f", worst.getDistanceSum(), worst.getFitnessValue());
            logger.info("population has fitness sum                         %8.8f", population.getPopulationFitness());
            fireMultipleCyclesEvent(population, individuals, totalCycleCounter, populationFitness);
        }
        logger.info("finished");
        fireAllCyclesFinished();
    }

    private void fireMultipleCyclesEvent(
            Population population,
            List<Individual> individualsSorted,
            int totalCycleCounter,
            double populationFitness) {
        List<MultipleCyclesListener> clone = cloneListeners();
        MultipleCyclesEvent event = MultipleCyclesEvent.builder()
                .withPopulation(population)
                .withIndividualsSorted(individualsSorted)
                .withCycleCounter(totalCycleCounter)
                .withPopulationFitness(populationFitness)
                .build();
        for (MultipleCyclesListener listener : clone) {
            listener.nextCycleFinished(event);
        }
    }

    private List<MultipleCyclesListener> cloneListeners() {
        List<MultipleCyclesListener> result;
        synchronized (this) {
            result = new ArrayList<>(this.multipleCyclesListeners);
        }
        return result;
    }

    private void fireAllCyclesFinished() {
        List<MultipleCyclesListener> clone = cloneListeners();
        for (MultipleCyclesListener listener : clone) {
            listener.allCyclesFinished();
        }
    }
}
