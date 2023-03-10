package cj.software.experiments.gentsp.util;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import cj.software.experiments.gentsp.entity.Individual;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {IndividualFactory.class})
class IndividualFactoryTest {
    @Autowired
    private IndividualFactory factory;

    @MockBean
    private RandomUtil randomUtil;

    @Test
    void createInitial3() {
        int[] returned = factory.createChromosomesArray(3);
        assertThat(returned).as("returned").isEqualTo(new int[]{0, 1, 2});
    }

    @Test
    void createInitial4() {
        int[] returned = factory.createChromosomesArray(4);
        assertThat(returned).as("returned").isEqualTo(new int[]{0, 1, 2, 3});
    }

    @Test
    void createWith3Chromosomes() {
        createIndividual(5, 1,3, new int[]{0, 1, 0, 2, 2, 0}, new int[]{1, 0, 2});
    }

    @Test
    void createWith5Chromosomes() {
        createIndividual(13, 2, 5, new int[]{0, 3, 1, 0, 1, 1, 4, 1, 2, 3}, new int[]{1, 4, 0, 2, 3});
    }

    private void createIndividual(
            int cycleCounter,
            int populationCounter,
            int chromosomeLength,
            int[] swapPositions,
            int[] expChromosomes) {
        // mock beans
        OngoingStubbing<Integer> stubbing = when(randomUtil.nextRandomValue(chromosomeLength)).thenReturn(swapPositions[0]);
        for (int i = 1; i < swapPositions.length; i++) {
            stubbing = stubbing.thenReturn(swapPositions[i]);
        }

        // invoke
        Individual individual = factory.create(cycleCounter, populationCounter, chromosomeLength);

        // checks
        assertThat(individual).as("individual").isNotNull();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(individual.getCycleCounter()).as("cycle counter").isEqualTo(cycleCounter);
        softy.assertThat(individual.getPopulationCounter()).as("population counter").isEqualTo(populationCounter);
        softy.assertThat(individual.getChromosome()).as("chromosomes").isEqualTo(expChromosomes);
        softy.assertThat(individual.getFitnessValue()).as("fitness values").isEqualTo(0.0);
        softy.assertThat(individual.getDistanceSum()).as("distance sum").isEqualTo(0.0);
        softy.assertAll();

        verify(randomUtil, times(swapPositions.length)).nextRandomValue(chromosomeLength);
    }
}
