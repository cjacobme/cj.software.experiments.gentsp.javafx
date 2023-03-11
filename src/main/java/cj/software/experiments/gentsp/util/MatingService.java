package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.Individual;
import cj.software.experiments.gentsp.entity.Population;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MatingService {

    @Autowired
    private RandomUtil randomUtil;

    @Autowired
    private RatingCalculator ratingCalculator;

    Individual[] shuffle(Individual[] individuals) {
        Individual[] result = Arrays.copyOf(individuals, individuals.length);
        for (int iInd = individuals.length - 1; iInd > 0; iInd--) {
            int index = randomUtil.nextRandomValue(iInd + 1);
            Individual individual = result[index];
            result[index] = result[iInd];
            result[iInd] = individual;
        }
        return result;
    }

    Individual selectParent(Population population, int tournamentSize) {
        Individual[] individuals = population.getIndividuals();
        Individual[] shuffled = shuffle(individuals);
        Individual[] tournament = new Individual[tournamentSize];
        System.arraycopy(shuffled, 0, tournament, 0, tournamentSize);
        Population temp = Population.builder().withIndividuals(tournament).build();
        List<Individual> rated = ratingCalculator.sortFitness(temp);
        Individual result = rated.get(0);
        return result;
    }
}
