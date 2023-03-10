package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.Individual;
import cj.software.experiments.gentsp.entity.Population;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PopulationFactory {
    @Autowired
    private IndividualFactory individualFactory;

    public Population create(int cycleCounter, int numIndividuals, int chromosomeLength) {
        Individual[] individuals = new Individual[numIndividuals];
        for (int i = 0; i < numIndividuals; i++) {
            individuals[i] = individualFactory.create(cycleCounter, i, chromosomeLength);
        }
        Population result = Population.builder()
                .withIndividuals(individuals)
                .build();
        return result;
    }
}
