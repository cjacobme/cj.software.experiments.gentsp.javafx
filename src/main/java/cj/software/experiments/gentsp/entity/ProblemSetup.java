package cj.software.experiments.gentsp.entity;

import java.io.Serializable;

public class ProblemSetup implements Serializable {
    static final long serialVersionUID = 1L;

    private final int numCities;

    private final int populationSize;

    private final int maxGenerations;

    private final int elitismCount;

    private final double crossoverRate;

    private final int tournamentSize;

    private final double mutationRate;

    public ProblemSetup(
            int numCities,
            int populationSize,
            int maxGenerations,
            int elitismCount,
            double crossoverRate,
            int tournamentSize,
            double mutationRate) {
        this.numCities = numCities;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.elitismCount = elitismCount;
        this.crossoverRate = crossoverRate;
        this.tournamentSize = tournamentSize;
        this.mutationRate = mutationRate;
    }

    public int getNumCities() {
        return numCities;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getMaxGenerations() {
        return maxGenerations;
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
