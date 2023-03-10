package cj.software.experiments.gentsp.entity;

import java.io.Serializable;

public class Population implements Serializable {
    private static final long serialVersionUID = 1L;

    private Individual[] individuals;

    private double populationFitness;

    private Population() {
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public double getPopulationFitness() {
        return populationFitness;
    }

    public void setPopulationFitness(double populationFitness) {
        this.populationFitness = populationFitness;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected Population instance;

        protected Builder() {
            instance = new Population();
        }

        public Population build() {
            Population result = instance;
            instance = null;
            return result;
        }

        public Builder withIndividuals(Individual... individuals) {
            instance.individuals = individuals;
            return this;
        }

        public Builder withPopulationFitness(double populationFitness) {
            instance.populationFitness = populationFitness;
            return this;
        }
    }
}