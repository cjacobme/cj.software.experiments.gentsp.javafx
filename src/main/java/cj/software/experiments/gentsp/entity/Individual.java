package cj.software.experiments.gentsp.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class Individual implements Serializable {
    private static final long serialVersionUID = 1L;

    private int cycleCounter;

    private int populationCounter;

    private int[] chromosome;

    private double distanceSum;

    private double fitnessValue;

    private Individual() {
    }

    public int getCycleCounter() {
        return cycleCounter;
    }

    public int getPopulationCounter() {
        return populationCounter;
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public double getDistanceSum() {
        return distanceSum;
    }

    public void setDistanceSum(double distanceSum) {
        this.distanceSum = distanceSum;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public void setChromosome(int index, int value) {
        chromosome[index] = value;
    }

    public int getChromosome(int index) {
        return chromosome[index];
    }

    public int getChromosomeLength() {
        return chromosome.length;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString()
    {
        String id = String.format("(%d,%d)", cycleCounter, populationCounter);
        StringBuilder sb = new StringBuilder();
        if (this.chromosome != null) {
            int chromosomeLength = this.chromosome.length;
            for (int chromo = 0; chromo < chromosomeLength - 1; chromo++) {
                sb.append(this.chromosome[chromo]).append(",");
            }
            sb.append(this.chromosome[chromosomeLength - 1]);
        }
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("order", sb);
        String result = builder.build();
        return result;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder().append(cycleCounter).append(populationCounter);
        int result = builder.build();
        return result;
    }

    @Override
    public boolean equals (Object otherObject) {
        boolean result;
        if (otherObject instanceof Individual) {
            Individual other = (Individual) otherObject;
            EqualsBuilder builder = new EqualsBuilder()
                    .append(this.cycleCounter, other.cycleCounter)
                    .append(this.populationCounter, other.populationCounter);
            result = builder.build();
        } else {
            result = false;
        }
        return result;
    }

    public static class Builder {
        protected Individual instance;

        protected Builder() {
            instance = new Individual();
        }

        public Individual build() {
            Individual result = instance;
            instance = null;
            return result;
        }

        public Builder withChromosomes(int[] chromosomes) {
            instance.chromosome = chromosomes;
            return this;
        }

        public Builder withDistanceSum(double distanceSum) {
            instance.setDistanceSum(distanceSum);
            return this;
        }

        public Builder withFitnessValue(double fitnessValue) {
            instance.setFitnessValue(fitnessValue);
            return this;
        }

        public Builder withCycleCounter(int cycleCounter) {
            instance.cycleCounter = cycleCounter;
            return this;
        }

        public Builder withPopulationCounter(int populationCounter) {
            instance.populationCounter = populationCounter;
            return this;
        }
    }
}