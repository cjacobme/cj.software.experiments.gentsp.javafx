package cj.software.experiments.gentsp.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder().append(individuals);
        int result = builder.build();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if (obj instanceof Population) {
            Population other = (Population) obj;
            EqualsBuilder builder = new EqualsBuilder()
                    .append(this.individuals, other.individuals);
            result = builder.build();
        } else {
            result = false;
        }
        return result;
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