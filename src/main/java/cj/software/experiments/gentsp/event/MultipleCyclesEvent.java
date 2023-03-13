package cj.software.experiments.gentsp.event;

import cj.software.experiments.gentsp.entity.Individual;
import cj.software.experiments.gentsp.entity.Population;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class MultipleCyclesEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Valid
    private List<Individual> individualsSorted;

    @NotNull
    private Integer cycleCounter;

    @NotNull
    private Double populationFitness;

    @NotNull
    @Valid
    private Population population;

    private MultipleCyclesEvent() {
    }

    public List<Individual> getIndividualsSorted() {
        return individualsSorted;
    }

    public Integer getCycleCounter() {
        return cycleCounter;
    }

    public Double getPopulationFitness() {
        return populationFitness;
    }

    public Population getPopulation() {
        return population;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected MultipleCyclesEvent instance;

        protected Builder() {
            instance = new MultipleCyclesEvent();
        }

        public MultipleCyclesEvent build() {
            MultipleCyclesEvent result = instance;
            instance = null;
            return result;
        }

        public Builder withCycleCounter(Integer cycleCounter) {
            instance.cycleCounter = cycleCounter;
            return this;
        }

        public Builder withIndividualsSorted(List<Individual> individuals) {
            instance.individualsSorted = individuals;
            return this;
        }

        public Builder withPopulationFitness(Double populationFitness) {
            instance.populationFitness = populationFitness;
            return this;
        }

        public Builder withPopulation(Population population) {
            instance.population = population;
            return this;
        }
    }
}