package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.Individual;
import cj.software.experiments.gentsp.entity.Population;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Individual mate(int cycleCounter, int populationCounter, Individual parent1, Individual parent2) {
        int[] parent1Chromosomes = parent1.getChromosome();
        int[] parent2Chromosomes = parent2.getChromosome();
        int chromosomeLength = parent2Chromosomes.length;
        int[] offspringChromosome  = new int[chromosomeLength];
        Arrays.fill(offspringChromosome, -1);

        int pos1 = randomUtil.nextRandomValue(chromosomeLength);
        int pos2 = randomUtil.nextRandomValue(chromosomeLength);
        int end = Math.max(pos1, pos2);
        int start = Math.min(pos1, pos2);
        Set<Integer> usedGenes = new HashSet<>();

        for (int iGene1 = start; iGene1 < end; iGene1++) {
            int gene = parent1Chromosomes[iGene1];
            usedGenes.add(gene);
            offspringChromosome[iGene1] = gene;
        }

        for (int iGene2 = 0; iGene2 < chromosomeLength; iGene2++) {
            int index2 = iGene2 + end;
            if (index2 >= chromosomeLength) {
                index2 -= chromosomeLength;
            }
            int gene = parent2Chromosomes[index2];
            if (! usedGenes.contains(gene)) {
                for (int pos = 0; pos < chromosomeLength; pos++) {
                    if (offspringChromosome[pos] == -1) {
                        offspringChromosome[pos] = gene;
                        break;
                    }
                }
                usedGenes.add(gene);
            }
        }

        Individual result = Individual.builder()
                .withCycleCounter(cycleCounter)
                .withPopulationCounter(populationCounter)
                .withChromosomes(offspringChromosome)
                .build();
        return result;
    }
}
