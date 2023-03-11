package cj.software.experiments.gentsp.entity;

import java.io.Serializable;

public class ProblemSetup implements Serializable {
    static final long serialVersionUID = 1L;

    private final int width;

    private final int height;

    private final int numCities;

    private final int populationSize;

    private final int elitismCount;

    private final double crossoverRate;

    private final int tournamentSize;

    private final double mutationRate;

    @SuppressWarnings("squid:S107")
    public ProblemSetup(
            int width,
            int height,
            int numCities,
            int populationSize,
            int elitismCount,
            double crossoverRate,
            int tournamentSize,
            double mutationRate) {
        this.width = width;
        this.height = height;
        this.numCities = numCities;
        this.populationSize = populationSize;
        this.elitismCount = elitismCount;
        this.crossoverRate = crossoverRate;
        this.tournamentSize = tournamentSize;
        this.mutationRate = mutationRate;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumCities() {
        return numCities;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getElitismCount() {
        return elitismCount;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public double getMutationRate() {
        return mutationRate;
    }
}
