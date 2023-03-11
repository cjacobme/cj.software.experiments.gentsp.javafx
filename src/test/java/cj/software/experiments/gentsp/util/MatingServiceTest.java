package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.Individual;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = MatingService.class)
class MatingServiceTest {
    @Autowired
    private MatingService matingService;

    @MockBean
    private RatingCalculator ratingCalculator;

    @MockBean
    private RandomUtil randomUtil;

    private Individual createIndividual(
            double distanceSum,
            double fitnessValue,
            int cycleCounter,
            int populationCounter,
            int... chromosomes) {
        Individual result = Individual.builder()
                .withChromosomes(chromosomes)
                .withDistanceSum(distanceSum)
                .withFitnessValue(fitnessValue)
                .withCycleCounter(cycleCounter)
                .withPopulationCounter(populationCounter)
                .build();
        return result;
    }

    @Test
    void shuffle1() {
        // setup data
        Individual ind0 = createIndividual(10.0, 15.0, 0, 0, 0, 1, 2, 3, 4);
        Individual ind1 = createIndividual(10.0, 10.0, 0, 1, 4, 3, 2, 1, 0);
        Individual ind2 = createIndividual(15.0, 20.0, 0, 2, 1, 3, 4, 0, 2);
        Individual ind3 = createIndividual(1.0, 2.0, 0, 3, 0, 2, 1, 3, 4);
        Individual ind4 = createIndividual(11.0, 12.0, 0, 4, 0, 4, 3, 2, 1);
        Individual[] individuals = new Individual[]{ind0, ind1, ind2, ind3, ind4};

        // configure mocks
        when(randomUtil.nextRandomValue(5)).thenReturn(2);
        when(randomUtil.nextRandomValue(4)).thenReturn(0);
        when(randomUtil.nextRandomValue(3)).thenReturn(1);
        when(randomUtil.nextRandomValue(2)).thenReturn(1);

        // invoke
        Individual[] shuffled = matingService.shuffle(individuals);

        // checks
        Individual[] expected = new Individual[]{ind3, ind4, ind1, ind0, ind2};
        assertThat(shuffled).as("shuffled").isEqualTo(expected);
    }

    @Test
    void shuffle2() {
        // setup data
        Individual ind0 = createIndividual(10.0, 15.0, 1, 0, 0, 1, 2, 3);
        Individual ind1 = createIndividual(20.0, 30.0, 1, 1, 3, 2, 1, 0);
        Individual ind2 = createIndividual(30.0, 35.0, 1, 2, 1, 2, 3, 0);
        Individual ind3 = createIndividual(40.0, 40.0, 1, 3, 2, 1, 3, 0);
        Individual[] individuals = new Individual[]{ind3, ind2, ind1, ind0};

        // configure mocks
        when(randomUtil.nextRandomValue(4)).thenReturn(2);
        when(randomUtil.nextRandomValue(3)).thenReturn(0);
        when(randomUtil.nextRandomValue(2)).thenReturn(0);

        // invoke
        Individual[] shuffled = matingService.shuffle(individuals);

        // checks
        Individual[] expected = new Individual[]{ind2, ind0, ind3, ind1};
        assertThat(shuffled).as("shuffled").isEqualTo(expected);
    }
}
