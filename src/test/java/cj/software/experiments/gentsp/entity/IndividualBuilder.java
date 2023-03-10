package cj.software.experiments.gentsp.entity;

public class IndividualBuilder extends Individual.Builder{
    public IndividualBuilder() {
        super.withChromosomes(new int[]{33, 22, 15, 78})
                .withDistanceSum(53576.3)
                .withFitnessValue(0.43746);
    }
}
