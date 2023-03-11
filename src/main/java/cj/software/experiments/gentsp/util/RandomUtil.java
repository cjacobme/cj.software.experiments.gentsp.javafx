package cj.software.experiments.gentsp.util;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomUtil {
    private final Random random = new Random();

    public int nextRandomValue(int max) {
        return random.nextInt(max);
    }
}
