package cj.software.experiments.gentsp.util;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import cj.software.experiments.gentsp.entity.Individual;
import cj.software.experiments.gentsp.entity.Population;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = PopulationFactory.class)
class PopulationFactoryTest {
    @Autowired
    private PopulationFactory populationFactory;

    @MockBean
    private IndividualFactory individualFactory;
    @Test
    void create5With2() {
        create(223, 5, 2);
    }

    @Test
    void create6With10() {
        create(157, 6, 10);
    }

    private void create(int cycleCounter, int numIndividuals, int chromosomeLength) {
        // mock beans
        Individual individual = mock(Individual.class);

        // configure mocks
        for (int i = 0; i < numIndividuals; i++) {
            when(individualFactory.create(cycleCounter, i, chromosomeLength)).thenReturn(individual);
        }

        // invoke
        Population created = populationFactory.create(cycleCounter, numIndividuals, chromosomeLength);

        // checks
        assertThat(created).as("created population").isNotNull();
        Individual[] individuals = created.getIndividuals();
        SoftAssertions softy = new SoftAssertions();
        softy.assertThat(individuals).as("individuals").hasSize(numIndividuals);
        softy.assertThat(created.getPopulationFitness()).as("population fitness").isEqualTo(0.0);
        softy.assertAll();
        for (int i = 0; i < numIndividuals; i++) {
            verify(individualFactory).create(cycleCounter, i, chromosomeLength);
        }
        softy = new SoftAssertions();
        for (int i = 0; i < numIndividuals; i++) {
            softy.assertThat(individuals[i]).as("individual [%d]", i).isNotNull();
        }
        softy.assertAll();
    }
}
