package cj.software.experiments.gentsp.event;

import cj.software.experiments.gentsp.entity.Individual;
import cj.software.experiments.gentsp.entity.IndividualBuilder;
import cj.software.experiments.gentsp.entity.PopulationBuilder;

import java.util.List;

public class MultipleCyclesEventBuilder extends MultipleCyclesEvent.Builder{
    public MultipleCyclesEventBuilder() {
        super.withPopulationFitness(52.12)
                .withCycleCounter(42)
                .withPopulation(new PopulationBuilder().build())
                .withIndividualsSorted(List.of(new IndividualBuilder().build(),
                        Individual.builder().withChromosomes(new int[]{3,2,0,1})
                                .withCycleCounter(13)
                                .withPopulationCounter(4)
                                .withFitnessValue(0.023)
                                .withDistanceSum(143.5)
                                .build()));
    }
}
