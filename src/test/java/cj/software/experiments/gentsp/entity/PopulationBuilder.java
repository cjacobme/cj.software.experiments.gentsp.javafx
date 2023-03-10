package cj.software.experiments.gentsp.entity;

public class PopulationBuilder extends Population.Builder {
    public PopulationBuilder() {
        super.withIndividuals(
                        new IndividualBuilder().build(),
                        Individual.builder().withChromosomes(new int[]{22, 21, 20, 19}).build(),
                        Individual.builder().withChromosomes(new int[]{33, 32, 31, 30}).build())
                .withPopulationFitness(22.342);
    }
}
