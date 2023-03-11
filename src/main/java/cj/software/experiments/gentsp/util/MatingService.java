package cj.software.experiments.gentsp.util;

import cj.software.experiments.gentsp.entity.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MatingService {
    @Autowired
    private RandomUtil randomUtil;

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
}
