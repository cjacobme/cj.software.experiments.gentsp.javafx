package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndividualFactory {
    @Autowired
    private RandomUtil randomUtil;
    public Individual create(int chromosomeLength) {
        int[] chromosomes = createChromosomesArray(chromosomeLength);
        for (int i = 0; i < chromosomeLength; i++) {
            int pos1 = randomUtil.nextRandomValue(chromosomeLength);
            int pos2 = randomUtil.nextRandomValue(chromosomeLength);
            int swap = chromosomes[pos1];
            chromosomes[pos1] = chromosomes[pos2];
            chromosomes[pos2] = swap;
        }
        Individual result = Individual.builder()
                .withChromosomes(chromosomes)
                .build();
        return result;
    }

    int[] createChromosomesArray(int chromosomeLength) {
        int[] result = new int[chromosomeLength];
        for (int i = 0; i < chromosomeLength; i++) {
            result[i] = i;
        }
        return result;
    }
}
