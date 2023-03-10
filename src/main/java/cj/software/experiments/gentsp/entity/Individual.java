package cj.software.experiments.gentsp.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class Individual implements Serializable {
    private static final long serialVersionUID = 1L;

    private int[] chromosome;

    private double distanceSum;

    private double fitnessValue;

    private Individual() {
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
    public static Builder builder() {
        return new Builder();
    }

    public void setGene(int index, int value) {
        chromosome[index] = value;
    }

    public int getGene(int index) {
        return chromosome[index];
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int chromosomeLength = this.chromosome.length;
        for (int chromo = 0; chromo < chromosomeLength - 1; chromo++)
        {
            sb.append(this.chromosome[chromo]).append(",");
        }
        sb.append(this.chromosome[chromosomeLength - 1]);
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("order", sb);
        String result = builder.build();
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
    }
}